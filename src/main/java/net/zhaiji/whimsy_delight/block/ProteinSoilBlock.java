package net.zhaiji.whimsy_delight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

/**
 * 蛋白沃土：独立的完整方块，六个面统一贴图。
 * - 所有"不需要耕地"的植物（PlantType 非 CROP）都能种在上面
 * - 牛乳菌（CROP 类型特例）只能种在它上面
 * - isFertile=true：给周边原版作物提供等同原版湿透耕地的生长加成
 * - 挖掉后掉落自身，可重复放置利用
 */
public class ProteinSoilBlock extends Block {
    public ProteinSoilBlock() {
        super(BlockBehaviour.Properties.of()
            .mapColor(MapColor.SNOW)
            .strength(0.6F)
            .sound(SoundType.GRAVEL)
        );
    }

    // 放行所有不需要耕地的植物；牛乳菌是 CROP 类型，作为本模组特例放行
    @Override
    public boolean canSustainPlant(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection, IPlantable pPlantable) {
        if (pPlantable.getPlantType(pLevel, pPos) != PlantType.CROP) {
            return true;
        }
        return pPlantable instanceof MilkFungusCropBlock;
    }

    // 等同原版湿透耕地：为 CropBlock 的 getGrowthSpeed 提供沃土级加成
    @Override
    public boolean isFertile(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }
}
