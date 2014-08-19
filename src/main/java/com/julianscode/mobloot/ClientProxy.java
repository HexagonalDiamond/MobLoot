package com.julianscode.mobloot;

import java.net.MalformedURLException;
import java.net.URL;

import com.jadarstudios.developercapes.DevCapes;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerConfigs() throws MalformedURLException {
		DevCapes.getInstance().registerConfig("https://raw.githubusercontent.com/jmeyer2k/MobLoot/master/capes.json");
	}
}
