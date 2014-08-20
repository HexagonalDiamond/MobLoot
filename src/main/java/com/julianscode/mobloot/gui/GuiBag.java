package com.julianscode.mobloot.gui;

import com.julianscode.mobloot.MobLoot;
import com.julianscode.mobloot.tileentity.TileEntityBag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBag extends GuiContainer{

	public final ResourceLocation rl = new ResourceLocation(MobLoot.MODID, "textures/gui/baggui.png");
	
	public GuiBag(InventoryPlayer i, TileEntityBag bag) {
		super(new ContainerBag(i, bag));
		this.xSize = 176;
		this.ySize = 123;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int p_146976_2_, int p_146976_3_) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(rl);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
