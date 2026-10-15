package net.zhaiji.whimsy_delight.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.zhaiji.whimsy_delight.client.render.MeatRafflesiaBlockEntityRenderer;
import net.zhaiji.whimsy_delight.client.screen.CraftingStationScreen;
import net.zhaiji.whimsy_delight.init.WDBlockEntityType;
import net.zhaiji.whimsy_delight.init.WDBlocks;
import net.zhaiji.whimsy_delight.init.WDMenuTypes;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void handlerEntityRenderersEvent$RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(WDBlockEntityType.MEAT_RAFFLESIA.get(), MeatRafflesiaBlockEntityRenderer::new);
    }

    public static void handlerClientSetup(FMLClientSetupEvent event) {
        // 静态作物方块使用 cutout 渲染，使透明像素正常显示
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.CHICKEN_TURNIP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.EGGPLANT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.GOOSE_EGGPLANT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.DUCK_EGGPLANT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.MILK_FUNGUS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.BONEBUSH_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.BONEBUSH_LEAVES.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.SALMON_ROEBERRY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(WDBlocks.STURGEON_ROEBERRY.get(), RenderType.cutout());

            // 注册工作站 GUI
            MenuScreens.register(WDMenuTypes.CRAFTING_STATION.get(), CraftingStationScreen::new);
        });
    }
}
