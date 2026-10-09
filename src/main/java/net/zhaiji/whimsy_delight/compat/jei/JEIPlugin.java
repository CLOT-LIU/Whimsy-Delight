package net.zhaiji.whimsy_delight.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.init.WDBlocks;
import net.zhaiji.whimsy_delight.init.WDRecipes;
import net.zhaiji.whimsy_delight.recipe.CraftingStationRecipe;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final RecipeType<CraftingStationRecipe> CRAFTING_STATION_RECIPE_TYPE =
        RecipeType.create(WhimsyDelight.MOD_ID, "crafting_station", CraftingStationRecipe.class);

    private static final ResourceLocation UID = new ResourceLocation(WhimsyDelight.MOD_ID, "crafting_station");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CraftingStationRecipeCategory(
            registration.getJeiHelpers().getGuiHelper()
        ));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<CraftingStationRecipe> recipes = recipeManager
            .getAllRecipesFor(WDRecipes.CRAFTING_STATION_TYPE.get());
        registration.addRecipes(CRAFTING_STATION_RECIPE_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // 对着工作站按 U 键 → 显示所有工作站配方
        registration.addRecipeCatalyst(
            new ItemStack(WDBlocks.CRAFTING_STATION.get().asItem()),
            CRAFTING_STATION_RECIPE_TYPE
        );
    }
}
