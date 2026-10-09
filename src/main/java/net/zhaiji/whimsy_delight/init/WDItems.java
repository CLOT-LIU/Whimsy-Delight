package net.zhaiji.whimsy_delight.init;

import net.minecraft.core.registries.Registries;
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
