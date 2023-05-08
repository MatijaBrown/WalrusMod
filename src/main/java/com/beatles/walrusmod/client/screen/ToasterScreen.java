package com.beatles.walrusmod.client.screen;

import com.beatles.walrusmod.WalrusMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ToasterScreen extends AbstractContainerScreen<ToasterMenu> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(WalrusMod.MODID, "textures/gui/toaster_gui.png");
	
	public ToasterScreen(ToasterMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}
	
	@Override
	protected void init() {
		super.init();
	}
	
	private void renderProgressArrow(PoseStack poseStack, int x, int y) {
		if (menu.isCrafting()) {
			blit(poseStack, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
		}
	}

	@Override
	protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		
		blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
		 
		renderProgressArrow(poseStack, x, y);
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, delta);
		renderTooltip(poseStack, mouseX, mouseY);
	}
	
}
