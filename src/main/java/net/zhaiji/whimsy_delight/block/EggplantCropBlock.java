package net.zhaiji.whimsy_delight.block;

import net.minecraft.core.BlockPos;
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
import net.zhaiji.whimsy_delight.init.WDItems;

/**
 * 蛋茄作物：5 个生长阶段，使用静态方块模型（eggplant_stage_1~5）。
 * 类似甜浆果丛：成熟（age 4）时右键收获蛋茄，作物回到 age 3，可反复结果。
 */
public class EggplantCropBlock extends CropBlock {
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };

    public EggplantCropBlock() {
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

    @Override
    protected ItemLike getBaseSeedId() {
        return WDItems.EGGPLANT_SEEDS.get();
    }

    // 类似甜浆果丛：成熟时右键收获蛋茄，作物回到 age 2（阶段3），可反复结果
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int age = this.getAge(pState);
        if (age < MAX_AGE) {
            // 未成熟：交给骨粉等逻辑处理
            return InteractionResult.PASS;
        }
        // 成熟：掉落 1~2 个蛋茄
        int count = 1 + pLevel.random.nextInt(2);
        popResource(pLevel, pPos, new ItemStack(getFruit().asItem(), count));
        // 作物回到 age 2（阶段3），继续生长可再次收获
        pLevel.setBlock(pPos, pState.setValue(AGE, 2), 2);
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    // 供子类覆盖以返回对应的产物物品
    protected ItemLike getFruit() {
        return WDItems.EGGPLANT.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
