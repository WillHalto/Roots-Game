package com.roots.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.roots.GameWorld.GameRenderer;
import com.roots.GameWorld.GameWorld;
import com.roots.RootsHelpers.GestureHandler;
import com.roots.RootsHelpers.InputHandler;
import com.roots.Rootsgame.RootsGame;


public class GameScreen implements Screen{
	private GameWorld world;
	private GameRenderer renderer;
	
	private GestureHandler handler;


	
	public GameScreen(RootsGame game){
		System.out.println("GameScreen Attached");
		float height = 1920;
		float ppu = Gdx.graphics.getHeight()/height;
		float width = Gdx.graphics.getWidth()/ppu;
		world = new GameWorld(height,width,game);
		renderer = new GameRenderer(world);
		handler = new GestureHandler(renderer,world);
		
		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(new InputHandler(handler));
		im.addProcessor(new GestureDetector(handler));
		
		Gdx.input.setInputProcessor(im);
		
	}
	
	
	
	@Override
	public void render(float delta) {
        world.update(delta);
        renderer.render(delta);
		
	}

	@Override
	public void resize(int width, int height) {
//		System.out.println("GameScreen - resizing");
//		float nheight = 1920;
//		float ppu = Gdx.graphics.getHeight()/height;
//		float nwidth = Gdx.graphics.getWidth()/ppu;
//		renderer.resize(nwidth);
		
	}

	@Override
	public void show() {
		System.out.println("GameScreen - show called");
//		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide() {
		System.out.println("GameScreen - hide called");
		
	}

	@Override
	public void pause() {
		System.out.println("GameScreen - pause called");
		world.pause();
		
	}

	@Override
	public void resume() {
		System.out.println("GameScreen - resume called");
		world.resume();
		
	}

	@Override
	public void dispose() {
		System.out.println("gs disp called");
		
	}

}

