package net.zhaiji.whimsy_delight.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.init.WDBlocks;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WhimsyDelight.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        craftStation();
        meatRafflesia();
        chickenTurnip();
        eggplant();
        bonebushLog();
        bonebushLeaves();
        bonebushSapling();
        salmonRoeberry();
        sturgeonRoeberry();
    }

    private void craftStation() {
        Block block = WDBlocks.CRAFTING_STATION.get();
        ModelFile modelFile = models().getExistingFile(modLoc("block/crafting_station"));
        horizontalBlock(block, modelFile);
        simpleBlockItem(block, modelFile);
    }

    private void meatRafflesia() {
        Block block = WDBlocks.MEAT_RAFFLESIA.get();
        ModelFile modelFile = models().getExistingFile(modLoc("block/meat_rafflesia"));
        for (int age = 0; age <= 3; age++) {
            getVariantBuilder(block)
                .partialState()
                .with(BlockStateProperties.AGE_3, age)
                .modelForState()
                .modelFile(modelFile)
                .addModel();
        }
    }

    private void chickenTurnip() {
        Block block = WDBlocks.CHICKEN_TURNIP.get();
        for (int age = 0; age <= 3; age++) {
            ModelFile modelFile = models().getExistingFile(modLoc("block/chicken_turnip_stage_" + (age + 1)));
            getVariantBuilder(block)
                .partialState()
                .with(BlockStateProperties.AGE_3, age)
                .modelForState()
                .modelFile(modelFile)
                .addModel();
        }
    }

    private void eggplant() {
        Block block = WDBlocks.EGGPLANT.get();
        for (int age = 0; age <= 4; age++) {
            ModelFile modelFile = models().getExistingFile(modLoc("block/eggplant_stage_" + (age + 1)));
            getVariantBuilder(block)
                .partialState()
                .with(BlockStateProperties.AGE_4, age)
                .modelForState()
                .modelFile(modelFile)
                .addModel();
        }
    }

    private void bonebushLog() {
        RotatedPillarBlock block = (RotatedPillarBlock) WDBlocks.BONEBUSH_LOG.get();
        ModelFile vertical = models().cubeColumn(
            "bonebush_log",
            modLoc("block/bonebush_log"),
            modLoc("block/bonebush_log_top")
        );
        ModelFile horizontal = models().cubeColumnHorizontal(
            "bonebush_log_horizontal",
            modLoc("block/bonebush_log"),
            modLoc("block/bonebush_log_top")
        );
        getVariantBuilder(block)
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(vertical).addModel()
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(horizontal).rotationX(90).addModel()
            .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
        simpleBlockItem(block, vertical);
    }

    private void bonebushLeaves() {
        Block block = WDBlocks.BONEBUSH_LEAVES.get();
        ModelFile model = models().singleTexture(
            "block/bonebush_leaves",
            mcLoc("block/leaves"),
            "all",
            modLoc("block/bonebush_leaves")
        );
        // distance 1~7 × persistent true/false 全部指向同一模型
        for (int distance = 1; distance <= 7; distance++) {
            for (boolean persistent : new boolean[]{false, true}) {
                getVariantBuilder(block)
                    .partialState()
                    .with(LeavesBlock.DISTANCE, distance)
                    .with(LeavesBlock.PERSISTENT, persistent)
                    .modelForState()
                    .modelFile(model)
                    .addModel();
            }
        }
        simpleBlockItem(block, model);
    }

    private void bonebushSapling() {
        Block block = WDBlocks.BONEBUSH_SAPLING.get();
        ModelFile model = models().getExistingFile(modLoc("block/bonebush_sapling"));
        // stage 0/1 都指向同一模型
        getVariantBuilder(block)
            .partialState().with(SaplingBlock.STAGE, 0).modelForState().modelFile(model).addModel()
            .partialState().with(SaplingBlock.STAGE, 1).modelForState().modelFile(model).addModel();
        simpleBlockItem(block, model);
    }

    private void salmonRoeberry() {
        Block block = WDBlocks.SALMON_ROEBERRY.get();
        // 阶段 0~2 共用贴图，阶段 3 用大马哈专属
        String[] stageModels = {"roeberry_stage_1", "roeberry_stage_2", "roeberry_stage_3", "salmon_roeberry_stage_4"};
        for (int age = 0; age <= 3; age++) {
            ModelFile modelFile = models().getExistingFile(modLoc("block/" + stageModels[age]));
            getVariantBuilder(block)
                .partialState()
                .with(BlockStateProperties.AGE_3, age)
                .modelForState()
                .modelFile(modelFile)
                .addModel();
        }
    }

    private void sturgeonRoeberry() {
        Block block = WDBlocks.STURGEON_ROEBERRY.get();
        String[] stageModels = {"roeberry_stage_1", "roeberry_stage_2", "roeberry_stage_3", "sturgeon_roeberry_stage_4"};
        for (int age = 0; age <= 3; age++) {
            ModelFile modelFile = models().getExistingFile(modLoc("block/" + stageModels[age]));
            getVariantBuilder(block)
                .partialState()
                .with(BlockStateProperties.AGE_3, age)
                .modelForState()
                .modelFile(modelFile)
                .addModel();
        }
    }
}
