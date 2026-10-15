package net.zhaiji.whimsy_delight.block;

import net.minecraft.world.level.ItemLike;
import net.zhaiji.whimsy_delight.init.WDItems;

/**
 * 鸭蛋茄作物：与蛋茄逻辑相同，使用蛋茄的阶段1~4贴图，阶段5使用鸭蛋茄专属贴图。
 */
public class DuckEggplantCropBlock extends EggplantCropBlock {
    public DuckEggplantCropBlock() {
        super();
    }

    @Override
    protected ItemLike getFruit() {
        return WDItems.DUCK_EGGPLANT.get();
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return WDItems.DUCK_EGGPLANT_SEEDS.get();
    }
}
