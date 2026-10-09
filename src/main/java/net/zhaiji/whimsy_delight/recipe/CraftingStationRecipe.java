package net.zhaiji.whimsy_delight.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zhaiji.whimsy_delight.init.WDRecipes;

public class CraftingStationRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient input1;
    private final Ingredient input2;
    private final ItemStack output;
    private final int cookingTime;

    public CraftingStationRecipe(ResourceLocation id, Ingredient input1, Ingredient input2, ItemStack output, int cookingTime) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (container.getContainerSize() < 2) {
            return false;
        }

        ItemStack stack1 = container.getItem(0);
        ItemStack stack2 = container.getItem(1);

        // 检查两个输入槽是否匹配配方（不考虑顺序）
        boolean match1 = input1.test(stack1) && input2.test(stack2);
        boolean match2 = input1.test(stack2) && input2.test(stack1);

        return match1 || match2;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WDRecipes.CRAFTING_STATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return WDRecipes.CRAFTING_STATION_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.input1);
        list.add(this.input2);
        return list;
    }

    public Ingredient getInput1() {
        return this.input1;
    }

    public Ingredient getInput2() {
        return this.input2;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }
}
