package com.julianscode.mobloot.managers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.julianscode.mobloot.MobLoot;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class MobLootGenerator {
	public static Map<ItemStack, Integer> possibleLoot;
	public static void loadFromConfig() {
		possibleLoot = MobLoot.instance.config.possibleLoot;
	}
	public static ArrayList<ItemStack> generateRandomItems(int lootingLevel) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		Random r = new Random();
		float adjustedLootingLevel = 1.0f;
		if(MobLoot.config.shouldBeAffectedByLootingLevel) {
			adjustedLootingLevel = lootingLevel*0.05f + 1.0f;
		}
		for(ItemStack is: possibleLoot.keySet()) {
			float randNum = r.nextFloat()*1000.0f;
			if(possibleLoot.get(is)*adjustedLootingLevel>=randNum) {
				items.add(is);
			}
		}
		return items;
	}
}
