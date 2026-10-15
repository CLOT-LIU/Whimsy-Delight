package net.zhaiji.whimsy_delight.block;

import net.minecraft.world.level.ItemLike;
import net.zhaiji.whimsy_delight.init.WDItems;

/**
 * 鹅蛋茄作物：与蛋茄逻辑相同，使用蛋茄的阶段1~4贴图，阶段5使用鹅蛋茄专属贴图。
 */
public class GooseEggplantCropBlock extends EggplantCropBlock {
    public GooseEggplantCropBlock() {
        super();
    }

    @Override
    protected ItemLike getFruit() {
        return WDItems.GOOSE_EGGPLANT.get();
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return WDItems.GOOSE_EGGPLANT_SEEDS.get();
    }
}
