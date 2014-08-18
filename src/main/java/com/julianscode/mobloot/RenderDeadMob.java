package com.julianscode.mobloot;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderDeadMob extends Render {
	
	Render display;
	
	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_,
			double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		EntityDeadMob deadmob = (EntityDeadMob) p_76986_1_;
		this.display = RenderManager.instance.getEntityRenderObject(deadmob.entityToDisplay);
		this.display.doRender(deadmob.entityToDisplay, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
	
}
