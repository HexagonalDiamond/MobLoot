package com.julianscode.mobloot.gui;

import com.julianscode.mobloot.tileentity.TileEntityBag;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerBag extends Container {

	public ContainerBag(InventoryPlayer i, TileEntityBag bag) {
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
