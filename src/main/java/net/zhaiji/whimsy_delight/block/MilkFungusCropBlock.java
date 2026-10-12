package net.zhaiji.whimsy_delight.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zhaiji.whimsy_delight.init.WDBlocks;
import net.zhaiji.whimsy_delight.init.WDItems;

/**
 * 牛乳菌作物：5 个生长阶段（milk_fungus_stage_1~5），cross 模型。
 * - 只能种在蛋白沃土（ProteinSoilBlock）上
 * - 类似蛋茄：成熟（age 4）右键收获牛乳菌囊 + 1~2 孢子，回到 age 2，可反复收获
 * - 骨粉每次只推进 1 个阶段
 * - 蛋白沃土上额外加速（在沃土级加成基础上速度再 x2）
 */
public class MilkFungusCropBlock extends CropBlock {
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D)
    };

    public MilkFungusCropBlock() {
        super(
            Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.CROP)
                .pushReaction(PushReaction.DESTROY)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    // 骨粉每次只推进 1 个阶段，保证每个阶段形态都能看到
    @Override
    protected int getBonemealAgeIncrease(Level pLevel) {
        return 1;
    }

    // 只能种在蛋白沃土上
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(WDBlocks.PROTEIN_SOIL.get());
    }

    // 类似原版 CropBlock：光照>=9 时生长；蛋白沃土上在沃土级加成基础上速度再 x2
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int i = this.getAge(pState);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, pLevel, pPos);
                if (pLevel.getBlockState(pPos.below()).is(WDBlocks.PROTEIN_SOIL.get())) {
                    f *= 2.0F;
                }
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, this.getStateForAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return WDItems.MILK_FUNGUS_SPORES.get();
    }

    // 类似蛋茄：成熟时右键收获牛乳菌囊 + 1~2 孢子，作物回到 age 2（阶段3），可反复收获
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int age = this.getAge(pState);
        if (age < MAX_AGE) {
            // 未成熟：交给骨粉等逻辑处理
            return InteractionResult.PASS;
        }
        // 成熟：掉落 1~3 个牛乳菌囊；孢子 50% 概率才出 1 个
        popResource(pLevel, pPos, new ItemStack(WDItems.MILK_FUNGUS_SAC.get(), 1 + pLevel.random.nextInt(3)));
        if (pLevel.random.nextFloat() < 0.5F) {
            popResource(pLevel, pPos, new ItemStack(WDItems.MILK_FUNGUS_SPORES.get()));
        }
        // 作物回到 age 2（阶段3），继续生长可再次收获
        pLevel.setBlock(pPos, pState.setValue(AGE, 2), 2);
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
