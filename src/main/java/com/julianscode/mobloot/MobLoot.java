package com.julianscode.mobloot;

import java.util.Random;

import com.sun.istack.internal.logging.Logger;

import scala.reflect.internal.Trees.This;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = MobLoot.MODID, version = MobLoot.VERSION)
public class MobLoot
{
    public static final String MODID = "mobloot";
    public static final String VERSION = "1.0";
    public Logger log;
    @Instance(MODID)
    public static MobLoot instance;
    @SidedProxy(clientSide="com.julianscode.mobloot.ClientProxy", serverSide="com.julianscode.mobloot.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	//register dead mob class
    	registerEntity(EntityDeadMob.class, "entityDeadMob");
    	
    	//register renderers
    	proxy.registerRenderers();
    	
    	//initialize logger
    	log = Logger.getLogger("MobLoot", this.getClass());
    	
    	//initialize event handler
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void spawnDeadZombie(double x, double y, double z, World w, Entity e) {
    	EntityDeadMob deadMobEntity = new EntityDeadMob(w);
    	deadMobEntity.posX = x;
    	deadMobEntity.posY = y;
    	deadMobEntity.posZ = z;
    	deadMobEntity.entityToDisplay = e;
    	e.setAlive();
    	w.spawnEntityInWorld(deadMobEntity);
    }
    
    public static void registerEntity(Class entityClass, String name) {
    	int entityID = EntityRegistry.findGlobalUniqueEntityId();
    	long seed = name.hashCode();
    	Random rand = new Random(seed);

    	EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
    	EntityRegistry.registerModEntity(entityClass, name, entityID, instance, 64, 1, true);
    }
    
    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
    	
    	if(event.entityLiving instanceof EntityMob && event.source.getEntity() instanceof EntityPlayer) {
    		log.info("Killed Hostile Mob ");
    		double x = event.entityLiving.posX;
    		double y = event.entityLiving.posY;
    		double z = event.entityLiving.posZ;
    		World w = event.entityLiving.worldObj;
    		this.spawnDeadZombie(x, y, z, w, (Entity) event.entityLiving);
    	}
    }
}
