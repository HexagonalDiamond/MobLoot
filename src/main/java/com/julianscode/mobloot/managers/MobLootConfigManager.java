package com.julianscode.mobloot.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public class MobLootConfigManager {
	public static Map<ItemStack, Integer> possibleLoot = new HashMap<ItemStack, Integer>();
	public static boolean shouldBeAffectedByLootingLevel = true;
	public static String serializeItemOfMap(ItemStack key, Integer value) {
		return Item.itemRegistry.getNameForObject(key.getItem()) + " - "+key.stackSize + " - "+value;
	}
	public static String[] serializeMap(Map<ItemStack, Integer> map) {
		ArrayList<String> out = new ArrayList<String>();
		for(ItemStack is: map.keySet()) {
			out.add(serializeItemOfMap(is, map.get(is)));
		}
		String[] returnVal = new String[out.size()];
		returnVal = out.toArray(returnVal);
		return returnVal;
	}
	public static ItemStack extractItemInfo(String in) {
		String[] splitUpString = in.split(" - ");
		Item i = (Item)Item.itemRegistry.getObject(splitUpString[0]);
		Integer stackSize = Integer.parseInt(splitUpString[1]);
		return new ItemStack(i, stackSize);
	}
	public static Integer extractChanceInfo(String in) {
		String[] splitUpString = in.split(" - ");
		return Integer.parseInt(splitUpString[2]);
	}
	public static Map<ItemStack, Integer> deserializeMap(String[] input) {
		Map<ItemStack, Integer> out = new HashMap<ItemStack, Integer>();
		for(String i:input) {
			out.put(extractItemInfo(i), extractChanceInfo(i));
		}
		return out;
	}
	public MobLootConfigManager(FMLPreInitializationEvent event) {
		possibleLoot.put(new ItemStack(Items.wooden_pickaxe), 500);
		possibleLoot.put(new ItemStack(Items.wooden_hoe), 500);
		possibleLoot.put(new ItemStack(Items.gold_ingot), 50);
		possibleLoot.put(new ItemStack(Items.iron_ingot), 100);
		possibleLoot.put(new ItemStack(Items.diamond), 20);
		possibleLoot.put(new ItemStack(Items.iron_pickaxe), 70);
		possibleLoot.put(new ItemStack(Items.diamond_hoe), 40);
		possibleLoot.put(new ItemStack(Items.diamond, 9), 1);
		possibleLoot.put(new ItemStack(Items.iron_ingot, 9), 1);
		possibleLoot.put(new ItemStack(Items.stick, 64), 10);
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		possibleLoot = deserializeMap(config.getStringList("Loot", config.CATEGORY_GENERAL, serializeMap(possibleLoot), "Format is 'Item - StackSize - Chance Out of 1000'"));
		shouldBeAffectedByLootingLevel = config.getBoolean("shouldBeAffectedByLootingLevel", config.CATEGORY_GENERAL, true, "Should be affected by sword looting level");
		config.save();
	}
	
}
