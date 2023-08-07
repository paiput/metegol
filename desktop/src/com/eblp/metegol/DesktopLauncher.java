package com.eblp.metegol;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.eblp.metegol.Metegol;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Metegol");
		//config.setWindowSizeLimits(1280, 1024, 1920, 1080);
		//config.useVsync(true);
		new Lwjgl3Application(new Metegol(), config);
	}
}
