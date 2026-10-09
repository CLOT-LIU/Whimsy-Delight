package net.zhaiji.whimsy_delight.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zhaiji.whimsy_delight.WhimsyDelight;
import net.zhaiji.whimsy_delight.menu.CraftingStationMenu;

/**
 * 工作站 GUI
 * 槽位布局：
 *   输入1(25,24)  输入2(45,24)  输出(116,34)
 *   燃料槽(35,44)
 * 进度箭头使用贴图底部素材（y=178：左彩色、右灰色，各 47x12）
 */
public class CraftingStationScreen extends AbstractContainerScreen<CraftingStationMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(WhimsyDelight.MOD_ID, "textures/gui/crafting_station.png");

    public CraftingStationScreen(CraftingStationMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 168;
        // 原版熔炉标题位置
        this.titleLabelX = 10;
        this.titleLabelY = 5;
        // 原版熔炉背包标签位置
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // 绘制工作站背景贴图（向上偏移 2 像素对齐槽位）
        RenderSystem.setShaderTexture(0, TEXTURE);
        guiGraphics.blit(TEXTURE, x, y - 2, 0, 0, this.imageWidth, this.imageHeight, 176, 200);

        // ========== 进度箭头（贴图底部素材：灰色做底，彩色按进度从左到右填充） ==========
        RenderSystem.setShaderTexture(0, TEXTURE);
        // 灰色箭头（覆盖贴图上自带的彩色图标）
        guiGraphics.blit(TEXTURE, x + 64, y + 35, 48, 178, 47, 12, 176, 200);
        // 彩色箭头按进度裁剪
        int progressWidth = (int) (this.menu.getCookingProgress() * 47); // 0~47
        if (progressWidth > 0) {
            guiGraphics.blit(TEXTURE, x + 64, y + 35, 0, 178, progressWidth, 12, 176, 200);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
