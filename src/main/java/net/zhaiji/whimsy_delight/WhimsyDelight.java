package net.zhaiji.whimsy_delight;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zhaiji.whimsy_delight.event.CommonEventManager;
import net.zhaiji.whimsy_delight.init.WDBlockEntityType;
import net.zhaiji.whimsy_delight.init.WDBlocks;
import net.zhaiji.whimsy_delight.init.WDCreativeModeTab;
import net.zhaiji.whimsy_delight.init.WDItems;
import net.zhaiji.whimsy_delight.init.WDMenuTypes;
import net.zhaiji.whimsy_delight.init.WDRecipes;
import software.bernie.geckolib.GeckoLib;

@Mod(WhimsyDelight.MOD_ID)
public class WhimsyDelight {
    public static final String MOD_ID = "whimsy_delight";

    public WhimsyDelight() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WhimsyDelightConfig.SPEC);

        GeckoLib.initialize();

        WDItems.ITEM.register(modBus);
        WDBlocks.BLOCK.register(modBus);
        WDBlockEntityType.BLOCK_ENTITY_TYPE.register(modBus);
        WDMenuTypes.MENU_TYPES.register(modBus);
        WDRecipes.RECIPE_TYPES.register(modBus);
        WDRecipes.RECIPE_SERIALIZERS.register(modBus);
        WDCreativeModeTab.CREATIVE_MODE_TAB.register(modBus);

        CommonEventManager.init(modBus, forgeBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> WhimsyDelightClient.init(modBus, forgeBus));
    }
}
