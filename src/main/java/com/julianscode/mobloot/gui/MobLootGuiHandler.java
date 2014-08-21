package com.julianscode.mobloot.gui;

import com.julianscode.mobloot.MobLoot;
import com.julianscode.mobloot.container.ContainerBag;
import com.julianscode.mobloot.tileentity.TileEntityBag;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class MobLootGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		switch(ID) {
			case MobLoot.bagGuiID:
				if(te instanceof TileEntityBag) {
					return new ContainerBag(player.inventory, (TileEntityBag) te);
				}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		switch(ID) {
			case MobLoot.bagGuiID:
				if(te instanceof TileEntityBag) {
					return new GuiBag(player.inventory, (TileEntityBag) te);
				}
		}
		return null;
	}

}
