package net.zhaiji.whimsy_delight.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.init.WDBlocks;
import net.zhaiji.whimsy_delight.init.WDItems;
import net.zhaiji.whimsy_delight.recipe.CraftingStationRecipe;

/**
 * JEI 工作站配方类别
 * 使用用户提供的 113x42 背景图
 * 槽位坐标根据工作站 GUI 的相对位置确定：
 *   输入1(3,3)  输入2(23,3)
 *   燃料(13,23)
 *   箭头(42,14) 47x12
 *   输出(94,15)
 */
public class CraftingStationRecipeCategory implements IRecipeCategory<CraftingStationRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(WhimsyDelight.MOD_ID, "crafting_station");

    // 用户提供的 JEI 背景图（113x42）
    private static final ResourceLocation BG_TEX =
        new ResourceLocation(WhimsyDelight.MOD_ID, "textures/gui/jei_crafting_station.png");

    // 工作站 GUI 贴图（用于箭头素材 47x12，贴图底部 y=178）
    private static final ResourceLocation WORKSTATION_TEX =
        new ResourceLocation(WhimsyDelight.MOD_ID, "textures/gui/crafting_station.png");

    // 工作站方块贴图（用于 JEI 标签页图标）
    private static final ResourceLocation BLOCK_TEX =
        new ResourceLocation(WhimsyDelight.MOD_ID, "textures/block/crafting_station.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrowFilled;  // 彩色箭头（动画填充，灰色底已融合进背景）
    private final Component title;

    @SuppressWarnings("removal")
    public CraftingStationRecipeCategory(IGuiHelper guiHelper) {
        // 背景：用户提供的 113x42 图
        this.background = guiHelper.drawableBuilder(BG_TEX, 0, 0, 113, 42)
            .setTextureSize(113, 42)
            .build();

        // 图标：工作站方块的 ItemStack（像物品栏一样3D渲染）
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(WDBlocks.CRAFTING_STATION.get().asItem()));

        // 工作站贴图底部彩色箭头素材（47x12，位于贴图 (0, 178)），动画从左到右填充
        this.arrowFilled = guiHelper.drawableBuilder(WORKSTATION_TEX, 0, 178, 47, 12)
            .setTextureSize(176, 200)
            .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);

        this.title = Component.translatable("container.whimsy_delight.crafting_station");
    }

    @Override
    public RecipeType<CraftingStationRecipe> getRecipeType() {
        return JEIPlugin.CRAFTING_STATION_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @SuppressWarnings("removal")
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(CraftingStationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // 箭头位置：燃料槽(13,23) + 工作站偏移(29,-8) = (42,15)
        // 灰色底已融合进背景，只画彩色填充
        this.arrowFilled.draw(guiGraphics, 42, 15);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CraftingStationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 3, 3)
            .addIngredients(recipe.getInput1());
        builder.addSlot(RecipeIngredientRole.INPUT, 23, 3)
            .addIngredients(recipe.getInput2());
        builder.addSlot(RecipeIngredientRole.CATALYST, 13, 23)
            .addItemStack(new ItemStack(WDItems.MEAT_RAFFLESIA_BUD.get()));
        // 输出槽：箭头右端(42+47=89) + 间隔5 = 94, y=15-2=13（上移2像素）
        builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 13)
            .addItemStack(recipe.getOutput());
    }
}
