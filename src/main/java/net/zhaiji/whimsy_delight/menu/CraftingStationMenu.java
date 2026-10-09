package net.zhaiji.whimsy_delight.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.whimsy_delight.block.blockentity.CraftingStationBlockEntity;
import net.zhaiji.whimsy_delight.init.WDMenuTypes;

/**
 * 工作站 Menu，仿照原版 FurnaceMenu
 * 槽位坐标：
 *   输入1(25,26)  输入2(45,26)  输出(116,35)
 *   燃料槽(35,46)
 */
public class CraftingStationMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;

    public CraftingStationMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory,
            (CraftingStationBlockEntity) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()),
            new SimpleContainerData(CraftingStationBlockEntity.NUM_DATA_VALUES));
    }

    public CraftingStationMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(WDMenuTypes.CRAFTING_STATION.get(), containerId);
        this.container = container;
        this.data = data;

        checkContainerSize(container, CraftingStationBlockEntity.SLOT_COUNT);
        container.startOpen(playerInventory.player);

        // 工作站槽位（坐标匹配贴图：输入1(25,24)、输入2(45,24)、燃料(35,44)、输出(116,34)）
        this.addSlot(new Slot(container, CraftingStationBlockEntity.SLOT_INPUT_1, 25, 24));
        this.addSlot(new Slot(container, CraftingStationBlockEntity.SLOT_INPUT_2, 45, 24));
        this.addSlot(new FurnaceFuelSlot(container, CraftingStationBlockEntity.SLOT_FUEL, 35, 44));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, container, CraftingStationBlockEntity.SLOT_OUTPUT, 116, 34));

        // 玩家背包 3x9（与原版熔炉一致）
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        // 玩家快捷栏（与原版熔炉一致）
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        addDataSlots(data);
    }

    // ========== 进度数据 ==========

    /** 加工进度 0.0 ~ 1.0 */
    public float getCookingProgress() {
        int total = this.data.get(CraftingStationBlockEntity.DATA_COOKING_TOTAL_TIME);
        if (total == 0) total = 200;
        return net.minecraft.util.Mth.clamp(
            (float) this.data.get(CraftingStationBlockEntity.DATA_COOKING_PROGRESS) / total, 0.0F, 1.0F);
    }

    // ========== 原版 quickMoveStack（仿 FurnaceMenu） ==========

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.copy();
            if (index == CraftingStationBlockEntity.SLOT_OUTPUT) {
                // 从产物槽移到背包
                if (!this.moveItemStackTo(slotStack, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, result);
            } else if (index != CraftingStationBlockEntity.SLOT_FUEL
                    && index != CraftingStationBlockEntity.SLOT_INPUT_1
                    && index != CraftingStationBlockEntity.SLOT_INPUT_2) {
                // 从背包移到工作站
                if (CraftingStationBlockEntity.isFuel(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, CraftingStationBlockEntity.SLOT_FUEL, CraftingStationBlockEntity.SLOT_FUEL + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // 原料尝试放入两个输入槽
                    if (!this.moveItemStackTo(slotStack, CraftingStationBlockEntity.SLOT_INPUT_1, CraftingStationBlockEntity.SLOT_INPUT_2 + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(slotStack, 4, 40, false)) {
                // 从工作站移到背包
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    // ========== 原版 FurnaceFuelSlot ==========

    private static class FurnaceFuelSlot extends Slot {
        public FurnaceFuelSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return CraftingStationBlockEntity.isFuel(stack);
        }
    }

    // ========== 原版 FurnaceResultSlot ==========

    private static class FurnaceResultSlot extends Slot {
        private final Player player;

        public FurnaceResultSlot(Player player, Container container, int slot, int x, int y) {
            super(container, slot, x, y);
            this.player = player;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            super.onTake(player, stack);
        }
    }
}
