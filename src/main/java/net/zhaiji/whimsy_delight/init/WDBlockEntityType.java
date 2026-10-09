package net.zhaiji.whimsy_delight.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.block.blockentity.CraftingStationBlockEntity;
import net.zhaiji.whimsy_delight.block.blockentity.WDBaseCropBlockEntity;

public class WDBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE,
        WhimsyDelight.MOD_ID
    );

    // 大五花
    public static final RegistryObject<BlockEntityType<WDBaseCropBlockEntity>> MEAT_RAFFLESIA = BLOCK_ENTITY_TYPE.register(
        "meat_rafflesia",
        () -> BlockEntityType.Builder.of(WDBaseCropBlockEntity::new, WDBlocks.MEAT_RAFFLESIA.get()).build(null)
    );

    // 工作站
    public static final RegistryObject<BlockEntityType<CraftingStationBlockEntity>> CRAFTING_STATION = BLOCK_ENTITY_TYPE.register(
        "crafting_station",
        () -> BlockEntityType.Builder.of(CraftingStationBlockEntity::new, WDBlocks.CRAFTING_STATION.get()).build(null)
    );
}
