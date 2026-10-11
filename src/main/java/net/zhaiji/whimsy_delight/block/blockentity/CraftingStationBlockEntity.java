package net.zhaiji.whimsy_delight.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zhaiji.whimsy_delight.init.WDBlockEntityType;
import net.zhaiji.whimsy_delight.init.WDItems;
import net.zhaiji.whimsy_delight.init.WDRecipes;
import net.zhaiji.whimsy_delight.menu.CraftingStationMenu;
import net.zhaiji.whimsy_delight.recipe.CraftingStationRecipe;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * 工作站方块实体
 * 机制：放入原料+燃料 → 进度走完 → 一次性消耗原料和燃料，产出结果
 * 中途拿出原料或燃料 → 进度清零
 *
 * 槽位：0=原料1，1=原料2，2=燃料，3=产物
 * 数据索引：0=cookingProgress，1=cookingTotalTime
 */
public class CraftingStationBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_INPUT_2 = 1;
    public static final int SLOT_FUEL = 2;
    public static final int SLOT_OUTPUT = 3;
    public static final int SLOT_COUNT = 4;

    public static final int DATA_COOKING_PROGRESS = 0;
    public static final int DATA_COOKING_TOTAL_TIME = 1;
    public static final int NUM_DATA_VALUES = 2;

    private static final int DEFAULT_COOKING_TIME = 200; // 10秒

    // 漏斗交互面：上方进原料，侧面进燃料，下方取产物
    private static final int[] SLOTS_FOR_UP = {SLOT_INPUT_1, SLOT_INPUT_2};
    private static final int[] SLOTS_FOR_DOWN = {SLOT_OUTPUT};
    private static final int[] SLOTS_FOR_SIDES = {SLOT_FUEL};

    private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);

    private int cookingProgress;
    private int cookingTotalTime = DEFAULT_COOKING_TIME;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_COOKING_PROGRESS -> cookingProgress;
                case DATA_COOKING_TOTAL_TIME -> cookingTotalTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case DATA_COOKING_PROGRESS -> cookingProgress = value;
                case DATA_COOKING_TOTAL_TIME -> cookingTotalTime = value;
            }
        }

        @Override
        public int getCount() {
            return NUM_DATA_VALUES;
        }
    };

    public CraftingStationBlockEntity(BlockPos pos, BlockState state) {
        super(WDBlockEntityType.CRAFTING_STATION.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.whimsy_delight.crafting_station");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new CraftingStationMenu(containerId, playerInventory, this, this.dataAccess);
    }

    // ========== 容器基础 ==========

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.items) {
            if (!itemStack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack itemStack = ContainerHelper.removeItem(this.items, slot, amount);
        if (!itemStack.isEmpty()) this.setChanged();
        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        this.setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == SLOT_OUTPUT) return false;
        return slot != SLOT_FUEL || isFuel(stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.level != null && this.level.getBlockEntity(this.worldPosition) == this
            && player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        // 保持列表槽位数量不变，逐槽置空（clear() 会使底层列表 size 变为 0，导致后续访问越界）
        for (int i = 0; i < this.items.size(); i++) {
            this.items.set(i, ItemStack.EMPTY);
        }
    }

    // ========== 漏斗交互（WorldlyContainer） ==========

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) return SLOTS_FOR_DOWN;
        return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return slot == SLOT_OUTPUT;
    }

    // ========== NBT ==========

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        this.cookingProgress = tag.getShort("CookTime");
        this.cookingTotalTime = tag.getShort("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putShort("CookTime", (short) this.cookingProgress);
        tag.putShort("CookTimeTotal", (short) this.cookingTotalTime);
    }

    // ========== 燃料 ==========

    /**
     * 工作站燃料判断：大五花花苞
     * （燃料只在进度走完时一次性消耗，不使用 Forge 燃烧时间系统）
     */
    public static boolean isFuel(ItemStack stack) {
        return stack.is(WDItems.MEAT_RAFFLESIA_BUD.get());
    }

    // ========== 配方与加工 ==========

    @Nullable
    private CraftingStationRecipe getCurrentRecipe() {
        if (this.level == null) return null;
        return this.level.getRecipeManager()
            .getRecipeFor(WDRecipes.CRAFTING_STATION_TYPE.get(), this, this.level)
            .orElse(null);
    }

    /** 检查原料匹配且输出槽可接收产物 */
    private boolean canBurn(@Nullable CraftingStationRecipe recipe) {
        if (recipe == null) return false;
        if (this.items.get(SLOT_INPUT_1).isEmpty() || this.items.get(SLOT_INPUT_2).isEmpty()) return false;

        ItemStack result = recipe.getResultItem(this.level.registryAccess());
        if (result.isEmpty()) return false;

        ItemStack output = this.items.get(SLOT_OUTPUT);
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItem(output, result)) return false;
        int newCount = output.getCount() + result.getCount();
        return newCount <= this.getMaxStackSize() && newCount <= output.getMaxStackSize();
    }

    /** 消耗原料，产出产物（燃料在 serverTick 完成时单独消耗） */
    private boolean burn(CraftingStationRecipe recipe) {
        if (!this.canBurn(recipe)) return false;

        ItemStack result = recipe.getResultItem(this.level.registryAccess());
        ItemStack output = this.items.get(SLOT_OUTPUT);

        if (output.isEmpty()) {
            this.items.set(SLOT_OUTPUT, result.copy());
        } else {
            output.grow(result.getCount());
        }

        this.consumeInput(SLOT_INPUT_1);
        this.consumeInput(SLOT_INPUT_2);
        return true;
    }

    /** 消耗一个输入槽：有容器剩余物（如奶桶→空桶）则返还，否则直接扣减 */
    private void consumeInput(int slot) {
        ItemStack input = this.items.get(slot);
        if (input.hasCraftingRemainingItem()) {
            this.items.set(slot, input.getCraftingRemainingItem());
        } else {
            input.shrink(1);
            if (input.isEmpty()) {
                this.items.set(slot, ItemStack.EMPTY);
            }
        }
    }

    /**
     * 工作站 tick 逻辑：
     * - 有原料+燃料+配方匹配 → 推进进度
     * - 进度满 → 一次性消耗原料和燃料，产出
     * - 原料或燃料没了 → 进度清零（用户选择）
     */
    public static void serverTick(Level level, BlockPos pos, BlockState state, CraftingStationBlockEntity be) {
        boolean dirty = false;

        ItemStack input1 = be.items.get(SLOT_INPUT_1);
        ItemStack input2 = be.items.get(SLOT_INPUT_2);
        ItemStack fuel = be.items.get(SLOT_FUEL);

        boolean hasInputs = !input1.isEmpty() && !input2.isEmpty();
        boolean hasFuel = !fuel.isEmpty();

        if (hasInputs && hasFuel) {
            CraftingStationRecipe recipe = be.getCurrentRecipe();

            if (be.canBurn(recipe)) {
                // 推进进度
                ++be.cookingProgress;
                if (be.cookingProgress >= be.cookingTotalTime) {
                    // 完成加工：一次性消耗原料和燃料，产出
                    be.cookingProgress = 0;
                    be.cookingTotalTime = recipe != null ? recipe.getCookingTime() : DEFAULT_COOKING_TIME;
                    if (recipe != null && be.burn(recipe)) {
                        // 消耗燃料
                        if (fuel.hasCraftingRemainingItem()) {
                            be.items.set(SLOT_FUEL, fuel.getCraftingRemainingItem());
                        } else {
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                be.items.set(SLOT_FUEL, ItemStack.EMPTY);
                            }
                        }
                    }
                    dirty = true;
                }
            } else {
                // 配方不匹配：进度清零
                if (be.cookingProgress > 0) {
                    be.cookingProgress = 0;
                    dirty = true;
                }
            }
        } else {
            // 原料或燃料没了：进度清零（用户选择 A）
            if (be.cookingProgress > 0) {
                be.cookingProgress = 0;
                dirty = true;
            }
        }

        if (dirty) {
            setChanged(level, pos, state);
        }
    }
}
