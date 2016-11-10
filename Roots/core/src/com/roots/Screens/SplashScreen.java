package com.roots.Screens;

//import aurelienribon.tweenengine.BaseTween;
//import aurelienribon.tweenengine.Tween;
//import aurelienribon.tweenengine.TweenCallback;
//import aurelienribon.tweenengine.TweenEquations;
//import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roots.Rootsgame.RootsGame;
import com.roots.RootsHelpers.AssetLoader;
//import com.roots.TweenAccessors.SpriteAccessor;

public class SplashScreen implements Screen,InputProcessor{
	
	//private TweenManager manager;
	private SpriteBatch batcher;
	private Sprite sprite;
	private RootsGame game;
	private boolean loaded = false;
	private float loadTime;
//	private boolean canAdvance = false;
//	private boolean clickAgain = true;
//	private boolean fading;
//	public static Preferences prefs;
	
	public SplashScreen(RootsGame game){
		this.game = game;
//		prefs = Gdx.app.getPreferences("Roots");
//		if(!prefs.contains("splashSkip")){
//			prefs.putBoolean("splashSkip", false);
//		}
		System.out.println("render");
		Gdx.input.setInputProcessor(this);
	}
	
	
	

	@Override
	public void render(float delta) {
		//manager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        sprite.draw(batcher);
        batcher.end();
        loadTime += delta;
        if(!loaded&&loadTime > .75){
        	AssetLoader.loadGraphicsOne();
        	AssetLoader.load();
        	game.setScreen(new GameScreen(game));
        	loaded = true;
        }        
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		sprite = new Sprite(AssetLoader.splashSprite);
		//sprite.setColor(1,1,1,0);
		
		float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float desiredWidth = width;
        float scale = desiredWidth / sprite.getWidth();
		
		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height / 2)
                - (sprite.getHeight() / 2));
        //setupTween();
        batcher = new SpriteBatch();
		
	}


//	private void listenForClick(){
//		canAdvance = true;
//	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}




	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

