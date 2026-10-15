package net.zhaiji.whimsy_delight.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.whimsy_delight.block.ChickenTurnipCropBlock;
import net.zhaiji.whimsy_delight.block.EggplantCropBlock;
import net.zhaiji.whimsy_delight.block.MeatRafflesiaCropBlock;
import net.zhaiji.whimsy_delight.block.MilkFungusCropBlock;
import net.zhaiji.whimsy_delight.block.RoeberryBushBlock;
import net.zhaiji.whimsy_delight.init.WDBlocks;
import net.zhaiji.whimsy_delight.init.WDItems;

import java.util.Set;

public class BlockLootTable extends BlockLootSubProvider {
    public BlockLootTable() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // 大五花作物：按年龄分掉落
        // age 0 → 种子, age 1 → 种子, age 2 → 花苞, age 3 → 果实
        Block cropBlock = WDBlocks.MEAT_RAFFLESIA.get();
        LootItemBlockStatePropertyCondition.Builder age0 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MeatRafflesiaCropBlock.AGE, 0));
        LootItemBlockStatePropertyCondition.Builder age1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MeatRafflesiaCropBlock.AGE, 1));
        LootItemBlockStatePropertyCondition.Builder age2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MeatRafflesiaCropBlock.AGE, 2));

        LootTable.Builder lootTable = LootTable.lootTable()
            .withPool(
                applyExplosionCondition(
                    cropBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                            LootItem.lootTableItem(WDItems.MEAT_RAFFLESIA_SEEDS.get()).when(age0)
                                .otherwise(LootItem.lootTableItem(WDItems.MEAT_RAFFLESIA_SEEDS.get()).when(age1))
                                .otherwise(LootItem.lootTableItem(WDItems.MEAT_RAFFLESIA_BUD.get()).when(age2))
                                .otherwise(LootItem.lootTableItem(WDItems.MEAT_RAFFLESIA.get()))
                        )
                )
            );

        add(cropBlock, lootTable);

        // 鸡腿萝卜作物：产物 = 种子，未成熟掉1个，成熟掉1~2个
        Block turnipBlock = WDBlocks.CHICKEN_TURNIP.get();
        LootItemCondition.Builder turnipMature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(turnipBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChickenTurnipCropBlock.AGE, 3));

        LootTable.Builder turnipLoot = LootTable.lootTable()
            .withPool(
                applyExplosionCondition(
                    turnipBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WDItems.CHICKEN_TURNIP.get()))
                )
            )
            .withPool(
                applyExplosionCondition(
                    turnipBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WDItems.CHICKEN_TURNIP.get()))
                        .when(turnipMature)
                )
            );

        add(turnipBlock, turnipLoot);

        // 蛋茄作物：未成熟（age 0~3）掉种子，成熟（age 4）掉蛋茄
        Block eggplantBlock = WDBlocks.EGGPLANT.get();
        LootItemCondition.Builder eggNotMature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(eggplantBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 0))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(eggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 1)))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(eggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 2)))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(eggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 3)));

        LootTable.Builder eggplantLoot = LootTable.lootTable()
            .withPool(
                applyExplosionCondition(
                    eggplantBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                            LootItem.lootTableItem(WDItems.EGGPLANT_SEEDS.get()).when(eggNotMature)
                                .otherwise(LootItem.lootTableItem(WDItems.EGGPLANT.get()))
                        )
                )
            );

        add(eggplantBlock, eggplantLoot);

        // 鹅蛋茄作物：同蛋茄，成熟掉鹅蛋茄
        Block gooseEggplantBlock = WDBlocks.GOOSE_EGGPLANT.get();
        LootItemCondition.Builder gooseNotMature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(gooseEggplantBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 0))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(gooseEggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 1)))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(gooseEggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 2)))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(gooseEggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 3)));
        LootTable.Builder gooseLoot = LootTable.lootTable()
            .withPool(
                applyExplosionCondition(
                    gooseEggplantBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                            LootItem.lootTableItem(WDItems.GOOSE_EGGPLANT_SEEDS.get()).when(gooseNotMature)
                                .otherwise(LootItem.lootTableItem(WDItems.GOOSE_EGGPLANT.get()))
                        )
                )
            );
        add(gooseEggplantBlock, gooseLoot);

        // 鸭蛋茄作物：同蛋茄，成熟掉鸭蛋茄
        Block duckEggplantBlock = WDBlocks.DUCK_EGGPLANT.get();
        LootItemCondition.Builder duckNotMature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(duckEggplantBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 0))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(duckEggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 1)))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(duckEggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 2)))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(duckEggplantBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 3)));
        LootTable.Builder duckLoot = LootTable.lootTable()
            .withPool(
                applyExplosionCondition(
                    duckEggplantBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                            LootItem.lootTableItem(WDItems.DUCK_EGGPLANT_SEEDS.get()).when(duckNotMature)
                                .otherwise(LootItem.lootTableItem(WDItems.DUCK_EGGPLANT.get()))
                        )
                )
            );
        add(duckEggplantBlock, duckLoot);

        // 牛乳菌作物：未成熟（age 0~3）掉1孢子；成熟（age 4）掉1~3菌囊，孢子50%概率掉1个
        Block milkFungusBlock = WDBlocks.MILK_FUNGUS.get();
        LootItemCondition.Builder milkNotMature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(milkFungusBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MilkFungusCropBlock.AGE, 0))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(milkFungusBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MilkFungusCropBlock.AGE, 1)))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(milkFungusBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MilkFungusCropBlock.AGE, 2)))
            .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(milkFungusBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MilkFungusCropBlock.AGE, 3)));
        LootItemCondition.Builder milkMature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(milkFungusBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MilkFungusCropBlock.AGE, 4));

        LootTable.Builder milkFungusLoot = LootTable.lootTable()
            .withPool(
                applyExplosionCondition(
                    milkFungusBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                            LootItem.lootTableItem(WDItems.MILK_FUNGUS_SPORES.get()).when(milkNotMature)
                                .otherwise(LootItem.lootTableItem(WDItems.MILK_FUNGUS_SAC.get()))
                        )
                )
            )
            .withPool(
                applyExplosionCondition(
                    milkFungusBlock,
                    LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0.0F, 2.0F))
                        .add(LootItem.lootTableItem(WDItems.MILK_FUNGUS_SAC.get()))
                        .when(milkMature)
                )
            )
            .withPool(
                applyExplosionCondition(
                    milkFungusBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WDItems.MILK_FUNGUS_SPORES.get()))
                        .when(milkMature)
                        .when(LootItemRandomChanceCondition.randomChance(0.5F))
                )
            );

        add(milkFungusBlock, milkFungusLoot);

        // 大马哈鱼子莓丛：产物 = 种子，未成熟掉1个，成熟掉1~2个
        Block salmonBlock = WDBlocks.SALMON_ROEBERRY.get();
        LootItemCondition.Builder salmonMature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(salmonBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RoeberryBushBlock.AGE, 3));

        add(salmonBlock, LootTable.lootTable()
            .withPool(
                applyExplosionCondition(
                    salmonBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WDItems.SALMON_ROEBERRY.get()))
                )
            )
            .withPool(
                applyExplosionCondition(
                    salmonBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WDItems.SALMON_ROEBERRY.get()))
                        .when(salmonMature)
                )
            )
        );

        // 鲟鱼子莓丛：同上
        Block sturgeonBlock = WDBlocks.STURGEON_ROEBERRY.get();
        LootItemCondition.Builder sturgeonMature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(sturgeonBlock)
            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RoeberryBushBlock.AGE, 3));

        add(sturgeonBlock, LootTable.lootTable()
            .withPool(
                applyExplosionCondition(
                    sturgeonBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WDItems.STURGEON_ROEBERRY.get()))
                )
            )
            .withPool(
                applyExplosionCondition(
                    sturgeonBlock,
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(WDItems.STURGEON_ROEBERRY.get()))
                        .when(sturgeonMature)
                )
            )
        );

        // 筒骨树叶：剪刀/精准采集掉树叶本身，否则按概率掉树苗（与原版树叶一致）
        add(
            WDBlocks.BONEBUSH_LEAVES.get(),
            createLeavesDrops(
                WDBlocks.BONEBUSH_LEAVES.get(),
                WDBlocks.BONEBUSH_SAPLING.get(),
                NORMAL_LEAVES_SAPLING_CHANCES
            )
        );

        // 其他方块默认掉落自身
        WDBlocks.BLOCK.getEntries().stream()
            .map(RegistryObject::get)
            .filter(block -> block != WDBlocks.MEAT_RAFFLESIA.get())
            .filter(block -> block != WDBlocks.CHICKEN_TURNIP.get())
            .filter(block -> block != WDBlocks.EGGPLANT.get())
            .filter(block -> block != WDBlocks.MILK_FUNGUS.get())
            .filter(block -> block != WDBlocks.BONEBUSH_LEAVES.get())
            .filter(block -> block != WDBlocks.SALMON_ROEBERRY.get())
            .filter(block -> block != WDBlocks.STURGEON_ROEBERRY.get())
            .forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return WDBlocks.BLOCK.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
