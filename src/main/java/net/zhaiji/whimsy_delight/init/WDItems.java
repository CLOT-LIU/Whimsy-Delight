package net.zhaiji.whimsy_delight.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.whimsy_delight.WhimsyDelight;

public class WDItems {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(Registries.ITEM, WhimsyDelight.MOD_ID);

    // 大五花种子
    public static final RegistryObject<Item> MEAT_RAFFLESIA_SEEDS = ITEM.register(
        "meat_rafflesia_seeds",
        () -> new ItemNameBlockItem(WDBlocks.MEAT_RAFFLESIA.get(), new Item.Properties())
    );

    // 大五花花苞
    public static final RegistryObject<Item> MEAT_RAFFLESIA_BUD = ITEM.register(
        "meat_rafflesia_bud",
        () -> new Item(new Item.Properties())
    );

    // 大五花
    public static final RegistryObject<Item> MEAT_RAFFLESIA = ITEM.register(
        "meat_rafflesia",
        () -> new Item(new Item.Properties())
    );

    // 鸡腿萝卜（产物 = 种子）
    public static final RegistryObject<Item> CHICKEN_TURNIP = ITEM.register(
        "chicken_turnip",
        () -> new ItemNameBlockItem(WDBlocks.CHICKEN_TURNIP.get(), new Item.Properties())
    );

    // 蛋茄种子
    public static final RegistryObject<Item> EGGPLANT_SEEDS = ITEM.register(
        "eggplant_seeds",
        () -> new ItemNameBlockItem(WDBlocks.EGGPLANT.get(), new Item.Properties())
    );

    // 蛋茄
    public static final RegistryObject<Item> EGGPLANT = ITEM.register(
        "eggplant",
        () -> new Item(new Item.Properties())
    );

    // 鹅蛋茄种子
    public static final RegistryObject<Item> GOOSE_EGGPLANT_SEEDS = ITEM.register(
        "goose_eggplant_seeds",
        () -> new ItemNameBlockItem(WDBlocks.GOOSE_EGGPLANT.get(), new Item.Properties())
    );

    // 鹅蛋茄（产物）
    public static final RegistryObject<Item> GOOSE_EGGPLANT = ITEM.register(
        "goose_eggplant",
        () -> new Item(new Item.Properties())
    );

    // 鸭蛋茄种子
    public static final RegistryObject<Item> DUCK_EGGPLANT_SEEDS = ITEM.register(
        "duck_eggplant_seeds",
        () -> new ItemNameBlockItem(WDBlocks.DUCK_EGGPLANT.get(), new Item.Properties())
    );

    // 鸭蛋茄（产物）
    public static final RegistryObject<Item> DUCK_EGGPLANT = ITEM.register(
        "duck_eggplant",
        () -> new Item(new Item.Properties())
    );

    // 牛乳菌孢子（右键种植，只能种在牛乳菌地面上）
    public static final RegistryObject<Item> MILK_FUNGUS_SPORES = ITEM.register(
        "milk_fungus_spores",
        () -> new ItemNameBlockItem(WDBlocks.MILK_FUNGUS.get(), new Item.Properties())
    );

    // 蛋白沃土（独立方块，挖掉掉自身）
    public static final RegistryObject<Item> PROTEIN_SOIL = ITEM.register(
        "protein_soil",
        () -> new BlockItem(WDBlocks.PROTEIN_SOIL.get(), new Item.Properties())
    );

    // 牛乳菌囊（收获物，可食用：饱食度2 饱和度1.0）
    public static final RegistryObject<Item> MILK_FUNGUS_SAC = ITEM.register(
        "milk_fungus_sac",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder().nutrition(2).saturationMod(0.25F).build()
        ))
    );

    // 芝士（可食用：饱食度5 饱和度6.0）
    public static final RegistryObject<Item> CHEESE = ITEM.register(
        "cheese",
        () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).build()
        ))
    );

    // 工作站
    public static final RegistryObject<Item> CRAFTING_STATION = ITEM.register(
        "crafting_station",
        () -> new BlockItem(WDBlocks.CRAFTING_STATION.get(), new Item.Properties())
    );

    // 筒骨木原木
    public static final RegistryObject<Item> BONEBUSH_LOG = ITEM.register(
        "bonebush_log",
        () -> new BlockItem(WDBlocks.BONEBUSH_LOG.get(), new Item.Properties())
    );

    // 筒骨树叶
    public static final RegistryObject<Item> BONEBUSH_LEAVES = ITEM.register(
        "bonebush_leaves",
        () -> new BlockItem(WDBlocks.BONEBUSH_LEAVES.get(), new Item.Properties())
    );

    // 筒骨树苗
    public static final RegistryObject<Item> BONEBUSH_SAPLING = ITEM.register(
        "bonebush_sapling",
        () -> new BlockItem(WDBlocks.BONEBUSH_SAPLING.get(), new Item.Properties())
    );

    // 大马哈鱼子莓（产物 = 种子，右键种植）
    public static final RegistryObject<Item> SALMON_ROEBERRY = ITEM.register(
        "salmon_roeberry",
        () -> new ItemNameBlockItem(WDBlocks.SALMON_ROEBERRY.get(), new Item.Properties())
    );

    // 鲟鱼子莓（产物 = 种子，右键种植）
    public static final RegistryObject<Item> STURGEON_ROEBERRY = ITEM.register(
        "sturgeon_roeberry",
        () -> new ItemNameBlockItem(WDBlocks.STURGEON_ROEBERRY.get(), new Item.Properties())
    );
}
