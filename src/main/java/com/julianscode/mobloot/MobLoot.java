package com.julianscode.mobloot;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import scala.reflect.internal.Trees.This;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = MobLoot.MODID, version = MobLoot.VERSION)
public class MobLoot
{
    public static final String MODID = "mobloot";
    public static final String VERSION = "1.0";
    @Instance(MODID)
    public static MobLoot instance;
    @SidedProxy(modId=MODID, clientSide="com.julianscode.mobloot.ClientProxy")
    public static CommonProxy proxy;
    public static MobLootConfigManager config;
    public static MobLootGenerator lootGen;
    @EventHandler
    public void preinit(FMLPreInitializationEvent event) throws MalformedURLException {
    	config = new MobLootConfigManager(event);
    	lootGen = new MobLootGenerator();
    	lootGen.loadFromConfig();
    	proxy.registerConfigs();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {    	
    	//initialize logger
    	
    	//initialize event handler
    	MinecraftForge.EVENT_BUS.register(this);
    }
    public static void spawnChest(World w, double x, double y, double z, ArrayList<ItemStack> arrayList, int lootingLevel) {
    	Vec3 block = findNearestOpenBlock(w, x, y, z, 5);
    	w.setBlock((int)block.xCoord, (int)block.yCoord, (int)block.zCoord, Blocks.chest);
    	fillChestWithLoot(w, (int)block.xCoord, (int)block.yCoord, (int)block.zCoord, arrayList, lootingLevel);
    }
    
    public static void fillChestWithLoot(World w, int x, int y, int z, ArrayList<ItemStack> arrayList, int lootingLevel) {
    	for(ItemStack is: arrayList) {
    		placeInRandomSlot(w, x, y, z, is);
    	}
    	ArrayList<ItemStack> randomLoot = lootGen.generateRandomItems(lootingLevel);
    	for(ItemStack loot: randomLoot) {
	    	placeInRandomSlot(w, x, y, z, loot);
    	}
    }
    
    public static void placeInRandomSlot(World w, int x, int y, int z, ItemStack is) {
    	IInventory te = (IInventory) w.getTileEntity(x, y, z);
    	Random r = new Random();
    	int slot = r.nextInt(27);
    	if(te.getStackInSlot(slot)==null) {
    		te.setInventorySlotContents(r.nextInt(27), is);
    	} else {
    		placeInRandomSlot(w, x, y, z, is);
    	}
    }
    
    public static Vec3 findNearestOpenBlock(World w, double x, double y, double z, double maxRange) {
    	int ix = (int) x;
    	int iy = (int) y;
    	int iz = (int) z;
    	for(int distance = 0; distance < maxRange; distance++) {
    		for(int checkX = ix - distance; checkX < ix + distance; checkX++) {
    			for(int checkY = iy - distance; checkY < iy + distance; checkY++) {
    				for(int checkZ = iz - distance; checkZ < iz + distance; checkZ++) {
    					if(canPlaceLoot(w, checkX, checkY, checkZ)) {
    						return Vec3.createVectorHelper(checkX, checkY, checkZ);
    					}
    				}
    			}
    		}
    	}
		return null;
    }
    
    public static boolean canPlaceLoot(World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z)) return false;

		Block block = world.getBlock(x, y, z);
		return (block.isAir(world, x, y, z) || block.isReplaceable(world, x, y, z));
	}
    
    public static void registerEntity(Class entityClass, String name) {
    	int entityID = EntityRegistry.findGlobalUniqueEntityId();
    	long seed = name.hashCode();
    	Random rand = new Random(seed);

    	EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
    	EntityRegistry.registerModEntity(entityClass, name, entityID, instance, 64, 1, true);
    }
    
    public static ArrayList<ItemStack> convertToItemStack(ArrayList<EntityItem> al) {
    	ArrayList<ItemStack> is = new ArrayList<ItemStack>();
    	for(EntityItem ei: al) {
    		is.add(ei.getEntityItem());
    	}
		return is;
    }
    
    @SubscribeEvent
    public void onDropsEvent(LivingDropsEvent event) {
    	event.setCanceled(true);
    	if(event.source.getEntity() instanceof EntityPlayer && event.entity instanceof EntityLiving) {
    		this.spawnChest(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, convertToItemStack(event.drops), event.lootingLevel);
    	}
    }
}
