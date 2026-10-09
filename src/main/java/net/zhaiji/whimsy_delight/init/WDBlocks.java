package net.zhaiji.whimsy_delight.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.block.BonebushSaplingBlock;
import net.zhaiji.whimsy_delight.block.ChickenTurnipCropBlock;
import net.zhaiji.whimsy_delight.block.CraftingStationBlock;
import net.zhaiji.whimsy_delight.block.EggplantCropBlock;
import net.zhaiji.whimsy_delight.block.MeatRafflesiaCropBlock;
import net.zhaiji.whimsy_delight.block.RoeberryBushBlock;
import net.zhaiji.whimsy_delight.init.WDItems;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class WDBlocks {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(Registries.BLOCK, WhimsyDelight.MOD_ID);

    // 大五花
    public static final RegistryObject<Block> MEAT_RAFFLESIA = BLOCK.register(
        "meat_rafflesia",
        MeatRafflesiaCropBlock::new
    );

    // 鸡腿萝卜
    public static final RegistryObject<Block> CHICKEN_TURNIP = BLOCK.register(
        "chicken_turnip",
        ChickenTurnipCropBlock::new
    );

    // 蛋茄
    public static final RegistryObject<Block> EGGPLANT = BLOCK.register(
        "eggplant",
        EggplantCropBlock::new
    );

    // 工作站
    public static final RegistryObject<Block> CRAFTING_STATION = BLOCK.register(
        "crafting_station",
        CraftingStationBlock::new
    );

    // 筒骨木原木（白色筒骨侧面 + 红色骨髓截面）
    public static final RegistryObject<Block> BONEBUSH_LOG = BLOCK.register(
        "bonebush_log",
        () -> new RotatedPillarBlock(
            Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(2.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        )
    );

    // 筒骨树叶
    public static final RegistryObject<Block> BONEBUSH_LEAVES = BLOCK.register(
        "bonebush_leaves",
        () -> new LeavesBlock(
            Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isSuffocating((pState, pLevel, pPos) -> false)
                .isViewBlocking((pState, pLevel, pPos) -> false)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
        )
    );

    // 筒骨树苗
    public static final RegistryObject<Block> BONEBUSH_SAPLING = BLOCK.register(
        "bonebush_sapling",
        BonebushSaplingBlock::new
    );

    // 大马哈鱼子莓丛
    public static final RegistryObject<Block> SALMON_ROEBERRY = BLOCK.register(
        "salmon_roeberry",
        () -> new RoeberryBushBlock(WDItems.SALMON_ROEBERRY)
    );

    // 鲟鱼子莓丛
    public static final RegistryObject<Block> STURGEON_ROEBERRY = BLOCK.register(
        "sturgeon_roeberry",
        () -> new RoeberryBushBlock(WDItems.STURGEON_ROEBERRY)
    );
}
