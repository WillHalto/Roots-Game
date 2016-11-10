package com.roots.Rootsgame;
import com.badlogic.gdx.Game;
import com.roots.RootsHelpers.AssetLoader;
import com.roots.Screens.GameScreen;
import com.roots.Screens.SplashScreen;
import com.roots.Services.IGoogleServices;

public class RootsGame extends Game{
	public static IGoogleServices googleServices;
	
	public RootsGame(IGoogleServices servs){
		super();
		RootsGame.googleServices = servs;
	}
	public RootsGame(){
		super();
	}
	@Override
	public void create() {
		//load some images, and go to the splash screen
		AssetLoader.loadPreMenu();
		setScreen(new SplashScreen(this));
		

		
	}
	
	@Override
	public void dispose(){
		super.dispose();
		AssetLoader.dispose();
	}
	
}
