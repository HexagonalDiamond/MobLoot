package com.julianscode.mobloot.blocks;

import com.julianscode.mobloot.MobLoot;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class MobLootBlocks {

	public static Block bag;
	
	public static void registerAll() {
		initializeBlocks();
		registerBlocks();
	}
	
	public static void initializeBlocks() {
		bag = new BlockBag(Material.cloth).setBlockName("bag").setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public static void registerBlocks() {
		GameRegistry.registerBlock(bag, bag.getUnlocalizedName());
	}
}
