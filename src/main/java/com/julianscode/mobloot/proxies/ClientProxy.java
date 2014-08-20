package com.julianscode.mobloot.proxies;

import java.net.MalformedURLException;
import java.net.URL;

import com.jadarstudios.developercapes.DevCapes;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerConfigs() {
		DevCapes.getInstance().registerConfig("https://dl.dropboxusercontent.com/s/5yz6q8mze86re86/capes.txt?dl=0");
	}
}
