package com.roots.Rootsgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.roots.Rootsgame.RootsGame;
import com.roots.Services.DesktopGoogleServices;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		 config.title = "Roots";
	        config.width = 400;
	        config.height = 711;
		new LwjglApplication(new RootsGame(new DesktopGoogleServices()), config);
	}
}
