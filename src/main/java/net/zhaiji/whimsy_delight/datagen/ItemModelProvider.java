package net.zhaiji.whimsy_delight.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.init.WDItems;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WhimsyDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(WDItems.MEAT_RAFFLESIA_SEEDS.get());
        basicItem(WDItems.MEAT_RAFFLESIA_BUD.get());
        basicItem(WDItems.MEAT_RAFFLESIA.get());
        basicItem(WDItems.CHICKEN_TURNIP.get());
        basicItem(WDItems.EGGPLANT_SEEDS.get());
        basicItem(WDItems.EGGPLANT.get());
        basicItem(WDItems.SALMON_ROEBERRY.get());
        basicItem(WDItems.STURGEON_ROEBERRY.get());
        // 原木、树叶、树苗的物品模型由 BlockStateProvider 的 simpleBlockItem 生成（继承方块模型）
    }
}
