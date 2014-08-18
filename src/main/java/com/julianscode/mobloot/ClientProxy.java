package com.julianscode.mobloot;

import net.minecraft.client.model.ModelBiped;
import com.julianscode.mobloot.EntityDeadMob;
import com.julianscode.mobloot.RenderDeadMob;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityDeadMob.class, new RenderDeadMob());
	}
}