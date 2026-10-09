package net.zhaiji.whimsy_delight.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.recipe.CraftingStationRecipe;
import net.zhaiji.whimsy_delight.recipe.CraftingStationRecipeSerializer;

public class WDRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, WhimsyDelight.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WhimsyDelight.MOD_ID);

    public static final RegistryObject<RecipeType<CraftingStationRecipe>> CRAFTING_STATION_TYPE = RECIPE_TYPES.register(
        "crafting_station",
        () -> new RecipeType<CraftingStationRecipe>() {
            @Override
            public String toString() {
                return "whimsy_delight:crafting_station";
            }
        }
    );

    public static final RegistryObject<RecipeSerializer<CraftingStationRecipe>> CRAFTING_STATION_SERIALIZER = RECIPE_SERIALIZERS.register(
        "crafting_station",
        CraftingStationRecipeSerializer::new
    );
}
