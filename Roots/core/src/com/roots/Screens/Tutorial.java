package com.roots.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.roots.RootsHelpers.AssetLoader;
import com.roots.Rootsgame.RootsGame;

public class Tutorial implements Screen, InputProcessor{
	
	private int currentImg;
	private SpriteBatch batcher;
	private OrthographicCamera cam;
	private float width,height;
	private RootsGame game;
	private ShapeRenderer shapeRenderer;
	
	public Tutorial(RootsGame game){
		float height = 1920;
		float ppu = Gdx.graphics.getHeight()/height;
		float width = Gdx.graphics.getWidth()/ppu;
		this.height =height;
		this.width = width;
		this.game = game;
		currentImg = 0;
	//	images = AssetLoader.getTutorial();
		cam = new OrthographicCamera();
		cam.position.set(width / 2, height / 2, 0);
		cam.setToOrtho(true,width,height);
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		
		Gdx.input.setInputProcessor(this);
		
		Gdx.input.setCatchBackKey(true);
		//AssetLoader.getTutorial();
		//System.out.println("get tut called from class");
		invertTextBoxes();
	}

	private void invertTextBoxes() {
		for (int i = 0; i < AssetLoader.tutorialTextInstructions.size(); i++){
			
			AssetLoader.tutorialTextInstructions.get(i).flip(false, true);
		}
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK){
			this.game.setScreen(new GameScreen(this.game));

			
		}
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
		currentImg ++;
		if(currentImg >= AssetLoader.tutorialImages.size())
		{
			this.game.setScreen(new GameScreen(this.game));
			AssetLoader.disposeTutorial();

			
		}
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

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
		batcher.draw(AssetLoader.bgOne,0,0,width,height);
		batcher.draw(AssetLoader.tutorialImages.get(currentImg),width/2-387,1920/2-688,774,1375);
		batcher.draw(AssetLoader.tutorialTextInstructions.get(currentImg),width/2-420,10,840,220);
		batcher.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.rect(width/2-387,1920/2-688,774,1375);
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		System.out.println("show tut");
		
	}

	@Override
	public void hide() {
		System.out.println("hide called tut");
		
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
		System.out.println("disp called tut");
		batcher.dispose();
		
	}

}
