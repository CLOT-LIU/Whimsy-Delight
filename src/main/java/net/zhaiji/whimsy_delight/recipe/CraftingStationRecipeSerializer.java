package net.zhaiji.whimsy_delight.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class CraftingStationRecipeSerializer implements RecipeSerializer<CraftingStationRecipe> {
    @Override
    public CraftingStationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        Ingredient input1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input1"));
        Ingredient input2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input2"));
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        int cookingTime = GsonHelper.getAsInt(json, "cookingTime", 200);

        return new CraftingStationRecipe(recipeId, input1, input2, output, cookingTime);
    }

    @Override
    public CraftingStationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient input1 = Ingredient.fromNetwork(buffer);
        Ingredient input2 = Ingredient.fromNetwork(buffer);
        ItemStack output = buffer.readItem();
        int cookingTime = buffer.readVarInt();

        return new CraftingStationRecipe(recipeId, input1, input2, output, cookingTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, CraftingStationRecipe recipe) {
        recipe.getInput1().toNetwork(buffer);
        recipe.getInput2().toNetwork(buffer);
        buffer.writeItem(recipe.getOutput());
        buffer.writeVarInt(recipe.getCookingTime());
    }
}
