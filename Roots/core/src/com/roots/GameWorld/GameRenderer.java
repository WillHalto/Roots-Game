package com.roots.GameWorld;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.roots.GameObjects.ColumnNumber;
import com.roots.GameObjects.Movable;
import com.roots.RootsHelpers.AssetLoader;
import com.roots.RootsHelpers.CoinManager;
import com.roots.ui.SimpleButton;
import com.roots.ui.TextButton;

public class GameRenderer {
	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	
	private SpriteBatch batcher;
	private float height;
	private float width;
	
	private float displayDelay,currentDelta,squareDelta,squareX,modeX;
	
	private int scoreUpdateHeight,scoreUpdateInc;
	private List<ColumnNumber> columnList;
	
	private TextureRegion bgFirst,scoreBacking,bgSecond,bgThird,bgFourth,bgFifth,coinMeterOne, coinMeterTwo,
	coinMeterThree,coin;
	private List<SimpleButton> mainMenuButtons;
	private SimpleButton audioButton,playButton,leaderButton,tutorialButton;
	private List<TextButton> exitCheckButtons;
	private BitmapFont font, uiFont,HUDfont;
	private Movable timer;
	private CoinManager coinManager;
	
	public GameRenderer(GameWorld world) {
		myWorld = world;
		height = myWorld.getHeight();
		width = myWorld.getWidth();
		displayDelay = .5f;
		scoreUpdateHeight = 200;
		scoreUpdateInc = 20;
		squareX = width/2-65*7;
		modeX = width/2 + 65;;
		//get the columnNumbers from the world
		columnList = myWorld.getColumn();
		//initialize camera and drawing equipment
		cam = new OrthographicCamera();
		cam.position.set(width / 2, height / 2, 0);
		cam.setToOrtho(true,width,height);
		
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		//sccale the sprite for the menu/back menu backing once we have screen set up
		AssetLoader.whiteSprite.setSize(myWorld.getWidth(),myWorld.getHeight());
		
		//load all the images we need to draw so we don't have to get them from assetloader 60 times per second
		initAssets();
		coinManager = new CoinManager(coinMeterOne,coinMeterTwo,coinMeterThree,coin);
	}
	//render the column and game if it's running, otherwise render the menu. Game over renders the regular game with a game over screen
	//on top
	public void render(float delta) {
		
		switch(myWorld.getState()){
		case RUNNING:
			gamePlayRender(delta);
			break;
		case MENU:
			menuRender();
			break;
		case GAMEOVER: case HIGHSCORE:
			gamePlayRender(delta);
			batcher.begin();
			font.draw(batcher, "GAME    OVER", width/2-410,550);
			font.setScale(.5f,-.5f);
			font.draw(batcher, "Tap to play again", width/2-290,710);
			font.setScale(1.0f,-1.0f);
			batcher.end();
			break;
		}
		
//		if(myWorld.settingsDisplayed()){
//			settingsRender();
//	}
		//renders the menu asking to exit if the back key is pressed
		if(myWorld.exitCheckDisplayed()){
			exitCheckRender();
		}
		
		
		
		
		
        
	}
	
	
	
//	private void settingsRender() {
//		batcher.begin();
//		AssetLoader.whiteSprite.draw(batcher);
//		audioButton.draw(batcher);
//		uiFont.draw(batcher,"Settings",myWorld.getWidth()/2 - 8*36,myWorld.getHeight()/4);
//		batcher.end();
//		
//	}
	
	
	
	//pretty obvious, renders the background and the buttons on top
	private void menuRender() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
		drawBackGround();
		batcher.draw(AssetLoader.logo,width/2-485,height/5-75,950f,466f);
		for (SimpleButton button : mainMenuButtons) {
			button.draw(batcher);
		}
//		batcher.draw(AssetLoader.test,myWorld.getWidth()/2-AssetLoader.test.getRegionWidth()/2,
//				myWorld.getHeight()/8,AssetLoader.test.getRegionWidth(),AssetLoader.test.getRegionHeight());
		audioButton.draw(batcher);
		HUDfont.draw(batcher, "Play", myWorld.getWidth()/2-36*4, playButton.getY()+60);
		HUDfont.setScale(.68f, -.68f);
		HUDfont.draw(batcher, "Leaderboard", myWorld.getWidth()/2-280, leaderButton.getY()+75);
		HUDfont.draw(batcher, "Tutorial",myWorld.getWidth()/2-175, tutorialButton.getY()+75);
		HUDfont.setScale(1,-1);
		batcher.end();
		
	}

	//renders background, numbers,and HUD. it displays the column numbers in square font if it's squared mode
	private void gamePlayRender(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
		batcher.disableBlending();
		drawBackGround();
		batcher.enableBlending();
		myWorld.getTarget().draw(batcher);
		if(myWorld.getSquaredMode()){
			this.font = AssetLoader.squareFont;
		}
		else{
			this.font = AssetLoader.font;
		}
		drawBackings(delta);
		 drawNumbers();
		 drawScoreAndUpdates(delta);
		 
		 drawSquaredMode(delta);
		 
		drawMovesLivesAndHighScore();
		
		audioButton.draw(batcher);
		if(myWorld.checkRankAdvance()){
			drawAdvanceRank();
		}
		batcher.end();
		
	}
	
	
	

	
	//draws a menu background,and a confirmation to exit
	private void exitCheckRender(){
		batcher.begin();
		AssetLoader.whiteSprite.draw(batcher);
		audioButton.draw(batcher);
		for (TextButton myTB : exitCheckButtons) {
			myTB.draw(batcher);
		}
		uiFont.draw(batcher,"Exit Game?",myWorld.getWidth()/2 - 340,myWorld.getHeight()/2-300);;
		batcher.end();
		
	}
	
	
	
	
	
	
	
	
	
	

	private void initAssets(){
		bgFirst = AssetLoader.bgFirst;

	
		scoreBacking = AssetLoader.scoreBacking;
	
		bgSecond = AssetLoader.bgSecond;
		bgThird = AssetLoader.bgThird;
		bgFourth = AssetLoader.bgFourth;
		bgFifth = AssetLoader.bgFifth;
		
		coinMeterOne = AssetLoader.coinMeterOne;
		coinMeterTwo = AssetLoader.coinMeterTwo;
		coinMeterThree = AssetLoader.coinMeterThree;
		coin = AssetLoader.coin;
		
		uiFont = AssetLoader.font;
		HUDfont = AssetLoader.altFont;
		timer = new Movable((myWorld.getWidth()/2-150),-240f,300,240,AssetLoader.timer,15);
		
	}
	//shouldn't be called after initialization, but sets up camera
	public void resize(float nwidth)
	{
		
		cam.position.set(nwidth / 2, height / 2, 0);
		cam.setToOrtho(true,nwidth,height);
	}
	
	public OrthographicCamera getCam ()
	{
		return cam;
	}
	
	
	//draws the backings, either by having the column number draw a sprite or by drawing it as a shape. 
	private void drawBackings(float delta){
		//target box
//		shapeRenderer.rect(myWorld.getTarget().getBox().x, myWorld.getTarget().getBox().y, myWorld.getTarget().getBox().width,
//				myWorld.getTarget().getBox().height);
		//drawing the column boxes
//		for (ColumnNumber columnNumber : columnList) {
//			if(columnNumber.shouldShow()){
//				if(columnNumber.getSelected()){
//					shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 255 / 255.0f, 1);
//				}
//				shapeRenderer.rect(columnNumber.getBox().x, columnNumber.getBox().y, columnNumber.getBox().width,
//						columnNumber.getBox().height);
//				shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1);
//			}
//		}
		for (ColumnNumber columnNumber : columnList) {
			if(columnNumber.shouldShow()){
				columnNumber.draw(batcher);
			}
		}
		
		//batcher.draw(movesBacking, 0, 240, 360, 240);
		batcher.draw(scoreBacking,myWorld.getWidth()-300,240,240,240);
		if(myWorld.getRank()<4){
			coinManager.draw(batcher,myWorld.getRank());                       //draw the coin stack
			coinManager.drawCoins(batcher,myWorld.getRank(),myWorld.getWins());//fill in the coins
		}
		
	}
	
	
	//this renders the numbers in text. If the number is large, its font size is scaled down to stay in the box
	private void drawNumbers(){
		font.draw(batcher,myWorld.getTarget().getStringValue(),width/2-(35*myWorld.getTarget().getStringValue().length()),myWorld.getTarget().getBox().y+70);
		
		 for (ColumnNumber columnNumber2 : columnList) {
			if(columnNumber2.shouldShow()){
				if(String.valueOf(columnNumber2.getValue()).length() >= 5){
					font.setScale(.62f,-.62f);
					font.draw(batcher,columnNumber2.getStringValue(),width/2-(22*columnNumber2.getStringValue().length()),
							columnNumber2.getY()+88);
					font.setScale(1.0f,-1.0f);;
					}
				else if(String.valueOf(columnNumber2.getValue()).length() >= 3){
					
					font.setScale(.75f,-.75f);
					font.draw(batcher,columnNumber2.getStringValue(),width/2-(27*columnNumber2.getStringValue().length()),
							columnNumber2.getY()+85);
					font.setScale(1.0f,-1.0f);
					}
				else{
				font.draw(batcher,columnNumber2.getStringValue(),width/2-(36*columnNumber2.getStringValue().length()),
						columnNumber2.getY()+70);
				}
				
			}
		}
	}
	//called if the update (+4, etc) should be diplayed. Adds up the runtimes to time how long it should be displayed
	private void drawScoreAndUpdates(float delta){
		int score = myWorld.getScore();
		if(score > 99999){
			
			uiFont.setScale(.45f,-.45f);
		}
		else if(score > 9999){
			
			uiFont.setScale(.50f,-.50f);
		}
		else if(score > 999){
			uiFont.setScale(.65f,-.65f);
		}
		else if(score > 99){
			uiFont.setScale(.75f,-.75f);
		}

		uiFont.draw(batcher, ""+score, (myWorld.getWidth()-180)-(37*uiFont.getScaleX()*String.valueOf(score).length()),255 );
		
		uiFont.setScale(1,-1);
		if(myWorld.getDisplayScoreUpdate()){
			 animateScoreUpdate(delta);
		 }
	}
	private void animateScoreUpdate(float delta){
		currentDelta += delta;
		 uiFont.setScale(.5f,-.5f);
		 uiFont.draw(batcher, "+"+myWorld.getScoreAdded()+"", width-200, scoreUpdateHeight);
		 uiFont.setScale(1,-1);
		 scoreUpdateHeight -= scoreUpdateInc;
		 scoreUpdateInc -= 2;
		 if(scoreUpdateInc <= 1){
			 scoreUpdateInc = 1;
		 }
		 if(scoreUpdateHeight < 150){
			 scoreUpdateHeight = 150;
			 
		 }
		 if (currentDelta >= displayDelay){
			 currentDelta = 0;
			 myWorld.setDisplayScoreUpdate(false);
			 scoreUpdateHeight = 200;
			 scoreUpdateInc = 20;
		 }
	}
	private void drawSquaredMode(float delta){
		if(myWorld.getSquaredStart()){
			
			animateSquaredMode(delta);
			
		}
		if(myWorld.getSquaredMode()&&!myWorld.getSquaredStart()){
			timer.draw(batcher);
			drawTime();
		 }
		if(!myWorld.getSquaredMode()){
			timer.setY(-240);
		}
	}
	
	private void animateSquaredMode(float delta) {
		font.draw(batcher, "SQUARED", squareX, 30);
		font.draw(batcher, "MODE!",modeX,30);
		timer.draw(batcher);
		drawTime();
		squareDelta += delta;
		if(squareDelta > .75){
			squareX -= 20;
			modeX += 15;
			timer.animateDown();
			if(timer.getY()>=0){
				timer.setY(0);
			}
		}
		if(squareDelta >= 1.0){
			myWorld.setSquaredStart(false);
			squareDelta = 0;
			squareX = width/2-65*7;
			modeX = width/2 + 65;
			timer.setY(0);
		}
		
	}
	private void drawAdvanceRank(){
		HUDfont.setScale(.7f,-.7f);
		HUDfont.draw(batcher,"Rank "+myWorld.getRank()+" Complete!",width/2-370,30);
		HUDfont.setScale(.55f,-.55f);
		if(myWorld.getRank() < 3){
			HUDfont.draw(batcher, "Tap to advance", width/2-270, 140);
		}
		else{
			HUDfont.draw(batcher, "Go for a high score!", width/2-345, 140);
		}
		HUDfont.setScale(1,-1);
	}
	
	private void drawTime(){
		font.setScale(.75f,-.75f);
		font.draw(batcher, ""+myWorld.getTimer().getTime(),myWorld.getWidth()/2-28*String.valueOf(myWorld.getTimer().getTime()).length(),timer.getY()+95);
		font.setScale(1,-1);
	}
	private void drawMovesLivesAndHighScore(){
		//uiFont.draw(batcher, ""+myWorld.getMoves(), 135, 310);
		uiFont.setScale(.45f,-.45f);
		
		uiFont.draw(batcher, "high:",width - 280,365);
		uiFont.draw(batcher,""+AssetLoader.getHighScore(),width - 280,420);
		uiFont.setScale(1,-1);

	}
	
	//renders the correct background, to accommodate it changing for squared mode and adding numbers
	private void drawBackGround(){
		switch(myWorld.getBG()){
		case 1:
			batcher.draw(bgFirst, 0, 0, width,1920);
			break;
		case 2:
			batcher.draw(bgSecond,0,0,width,1920);
			break;
		case 3:
			batcher.draw(bgThird,0,0,width,1920);
			break;
		case 4:
			batcher.draw(bgFourth,0,0,width,1920);
		case 5:
			batcher.draw(bgFifth,0,0,width,1920);
			break;
		
		
		}
	}
	
	
	
	//a kind of hacky way to give the renderer access to all the buttons to draw them, despite the buttons being created and handled 
	//in the gesture handler. It simply calls these after it makes the buttons to pass them over
	public void setMainMenuButtons(List<SimpleButton> buttons){
		mainMenuButtons = buttons;
		playButton = buttons.get(0);
		tutorialButton = buttons.get(1);
		leaderButton = buttons.get(2);
	}
	public void setAudioButton(SimpleButton button){
		audioButton = button;
	}
	public void setExitCheckButtons(List<TextButton> buttons){
		this.exitCheckButtons = buttons;
	}
	public void setFont(BitmapFont font){
		this.font = font;
	}
	
	

}

