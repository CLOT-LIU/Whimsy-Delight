package net.zhaiji.whimsy_delight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.zhaiji.whimsy_delight.init.WDBlocks;

/**
 * 筒骨树苗：可骨粉催熟，长成后手动放置一棵筒骨树（不使用原版 TreeGrower/ConfiguredFeature）。
 * 树干 4~5 格原木，顶部三层树叶（两层半径2、顶层半径1，去掉四角）。
 */
public class BonebushSaplingBlock extends SaplingBlock {
    public BonebushSaplingBlock() {
        // treeGrower 传 null：advanceTree 已完全重写，不会用到
        super(
            null,
            Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)
        );
    }

    @Override
    public void advanceTree(ServerLevel pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        int height = 4 + pRandom.nextInt(2); // 树干 4~5 格

        // 放置树干
        BlockState logState = WDBlocks.BONEBUSH_LOG.get().defaultBlockState()
            .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y);
        for (int i = 0; i < height; i++) {
            pLevel.setBlock(pPos.above(i), logState, 3);
        }

        // 三层树叶：height-2、height-1 半径2（去四角），height 顶层半径1
        for (int dy = height - 2; dy <= height; dy++) {
            int radius = dy >= height ? 1 : 2;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (radius == 2 && Math.abs(dx) == 2 && Math.abs(dz) == 2) {
                        continue; // 去掉四角
                    }
                    BlockPos leafPos = pPos.offset(dx, dy, dz);
                    BlockState existing = pLevel.getBlockState(leafPos);
                    if (existing.isAir() || existing.canBeReplaced()) {
                        // flag 19 = 通知邻居 + 同步客户端 + 已知形状，树叶距离会自动重算
                        pLevel.setBlock(leafPos, WDBlocks.BONEBUSH_LEAVES.get().defaultBlockState(), 19);
                    }
                }
            }
        }
    }
}
