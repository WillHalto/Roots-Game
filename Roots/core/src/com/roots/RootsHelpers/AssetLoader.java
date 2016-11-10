package com.roots.RootsHelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class AssetLoader {
	
	public static BitmapFont font,squareFont,altFont;
	public static Preferences prefs;
	public static List<TextureRegion> tutorialImages,tutorialTextInstructions;
	public static List<Texture> tutorialTextures;
	public static Texture bgOne,mainTexture,splashTexture,whiteRectTexture,bgTwo,bgThree,bgFour,bgFive;
	public static TextureRegion logo,bgFirst,playButtonUp, playButtonDown,audioOnIcon,audioOffIcon, splashSprite,test,numberBacking,
	targetBacking,scoreBacking,livesBacking,movesBacking,numberSelected,bgSecond,bgThird,bgFourth,bgFifth,timer,coinMeterOne,
	coinMeterTwo, coinMeterThree,coin;
	public static Sprite whiteSprite;
	static TextureAtlas atlas,tutorialAtlas;
	
	public static Sound firstAdd,secondAdd,thirdAddReg,squaredModeStart,menuBack,menuSelect,root,square,squaredEnd,
	timerOne,timerTwo,timerThree,timerFour,gameOver,advance;

	public static FreeTypeFontGenerator gen;
	
	
	public static void load(){

		
		
		//the fonts take forever, so load them here.


		
		//getTutorialTextures();
		loadGameFonts();
		loadGraphicsTwo();

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("Roots");

		// Provide default high score of 0
		if (!prefs.contains("highScore")) {
		    prefs.putInteger("highScore", 0);
		}
		loadSprites();
		loadAudio();
		loadSquareFont();
	}
	//load the assets for the splash screen, and the quicker ones for the game
	public static void loadPreMenu(){

		loadSplash();
		
		
		
		
		

		
		
		
	}
	public static void loadSplash(){
		splashTexture = new Texture(Gdx.files.internal("data/Splash-Screen.png"));
		splashTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		splashSprite = new TextureRegion(splashTexture, 0,0,1080,1920);
	}
	
	public static void loadAudio(){
		root = Gdx.audio.newSound(Gdx.files.internal("data/audio/Root.mp3"));
		square = Gdx.audio.newSound(Gdx.files.internal("data/audio/Square.mp3"));
		
		firstAdd = Gdx.audio.newSound(Gdx.files.internal("data/audio/Add 1.mp3"));
		secondAdd = Gdx.audio.newSound(Gdx.files.internal("data/audio/Add 2.mp3"));
		thirdAddReg = Gdx.audio.newSound(Gdx.files.internal("data/audio/Add 3.mp3"));
		squaredModeStart = Gdx.audio.newSound(Gdx.files.internal("data/audio/Squared Mode.mp3"));
		
		timerOne = Gdx.audio.newSound(Gdx.files.internal("data/audio/Timer 1.mp3"));
		timerTwo = Gdx.audio.newSound(Gdx.files.internal("data/audio/Timer 2.mp3"));
		timerThree = Gdx.audio.newSound(Gdx.files.internal("data/audio/Timer 3.mp3"));
		timerFour = Gdx.audio.newSound(Gdx.files.internal("data/audio/Timer 4.mp3"));
		
		squaredEnd = Gdx.audio.newSound(Gdx.files.internal("data/audio/Squared Mode Terminate.mp3"));
		gameOver =  Gdx.audio.newSound(Gdx.files.internal("data/audio/Game Over.mp3"));
		
		menuSelect = Gdx.audio.newSound(Gdx.files.internal("data/audio/Menu Select.mp3"));
		menuBack = Gdx.audio.newSound(Gdx.files.internal("data/audio/Menu Back.mp3"));
		advance = Gdx.audio.newSound(Gdx.files.internal("data/audio/Advance.mp3"));
	}
	public static void loadGraphicsOne(){
		atlas = new TextureAtlas(Gdx.files.internal("data/new_coin_meter.pack"));
		
		
		bgOne = new Texture(Gdx.files.internal("data/Roots-Background-State-1.png"));
		bgOne.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
//		mainTexture = new Texture(Gdx.files.internal("data/prelimpack3.png"));
//		mainTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		bgFirst = new TextureRegion(bgOne,0,0,1080,1920);
		bgFirst.flip(false, true);
		
		audioOnIcon = atlas.findRegion("soundon");
		audioOffIcon = atlas.findRegion("soundoff");
		playButtonUp = atlas.findRegion("MenuButton");
		playButtonDown = atlas.findRegion("MenuPressed");
		numberBacking = atlas.findRegion("Numbers-(Fixed)");
		numberSelected = atlas.findRegion("Numbers-(Filled)");
		targetBacking = atlas.findRegion("Goal-(Fixed)");
		scoreBacking = atlas.findRegion("Score (Fixed)");
		movesBacking = atlas.findRegion("Moves");
		timer = atlas.findRegion("Timer-(Small)");
		timer.flip(false,true);
		logo = atlas.findRegion("Roots-Final-Logo-(Light)");
		logo.flip(false, true);
		
		coinMeterOne = atlas.findRegion("4coin-0");
		coinMeterTwo = atlas.findRegion("8coin-0");
		coinMeterThree = atlas.findRegion("16coin-0");
		
		coin = atlas.findRegion("Coin");
	}
	public static void loadGraphicsTwo(){
		bgTwo = new Texture(Gdx.files.internal("data/Roots-Background-State-2.png"));
		bgThree = new Texture(Gdx.files.internal("data/Roots-Background-State-3.png"));
		bgFour = new Texture(Gdx.files.internal("data/Roots-Background-State-4.png"));
		bgFive = new Texture(Gdx.files.internal("data/Roots-Background-State-5.png"));
		bgTwo.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bgThree.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bgFour.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bgFive.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		bgSecond = new TextureRegion(bgTwo,0,0,1080,1920);
		bgSecond.flip(false, true);
		bgThird = new TextureRegion(bgThree,0,0,1080,1920);
		bgThird.flip(false, true);
		bgFourth = new TextureRegion(bgFour,0,0,1080,1920);
		bgFourth.flip(false, true);
		bgFifth = new TextureRegion(bgFive,0,0,1080,1920);
		bgFifth.flip(false, true);
	}
	
	public static void dispose(){
		font.dispose();
		altFont.dispose();
		squareFont.dispose();
		bgOne.dispose();
		bgTwo.dispose();
		bgThree.dispose();
		bgFour.dispose();
		bgFive.dispose();
		splashTexture.dispose();
		atlas.dispose();
		whiteRectTexture.dispose();
		//disposeTutorial();
		
	}
	
	// Receives an integer and maps it to the String highScore in prefs
	public static void setHighScore(int val) {
	    prefs.putInteger("highScore", val);
	    prefs.flush();
	}
	
	// Retrieves the current high score
	public static int getHighScore() {
	    return prefs.getInteger("highScore");
	}
	//sprite used for transparent menu backing
	private static void loadSprites(){
		whiteRectTexture = new Texture(Gdx.files.internal("data/Untitled.png"));
		whiteSprite = new Sprite(whiteRectTexture);
		whiteSprite.setColor(34/255.0f,25/255.0f,59/255.0f,0.7f);
		
		
		
	}
	//generate fonts to match the screen
	private static void loadGameFonts(){
		FileHandle fontFile = Gdx.files.internal("data/MonospaceTypewriter.ttf");
		FreeTypeFontGenerator gent =  new FreeTypeFontGenerator(fontFile);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		FileHandle fontFile2 = Gdx.files.internal("data/Akashi.ttf");
		FreeTypeFontGenerator gent2 =  new FreeTypeFontGenerator(fontFile2);
		//experimentally determined numbers
		parameter.size = 120;
		altFont = gent2.generateFont(parameter);
		altFont.setColor(255/255f, 255/255f, 255/255f, 1);
		altFont.setScale(1, -1);
		font = gent.generateFont(parameter);
		font.setColor(255/255f, 255/255f, 255/255f, 1);
		font.setScale(1, -1);
		gent.dispose();

	}
	
	private static void loadSquareFont(){
		FileHandle fontFile = Gdx.files.internal("data/repet___.ttf");
		FreeTypeFontGenerator gent =  new FreeTypeFontGenerator(fontFile);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = 128;
		squareFont = gent.generateFont(parameter);
		squareFont.setColor(255/255f, 255/255f, 255/255f, 1);
		squareFont.setScale(1f,-1f);
		
		
	}
	//build and get for tutorial are called from the GestureHandler corresponding to a
	//touch up on the tutorial button. The tutorial is built and disposed each time it is
	//accessed to prevent the textures getting all wonky from the resources not being released
	//properly
	public static void buildTutorialTextures(){
		tutorialTextures = new ArrayList<Texture>();
		for (int i = 1; i <= 18; i++) {
			tutorialTextures.add(new Texture(Gdx.files.internal("data/tutorial/small0"+i+".png")));
		}
	}
	public static void disposeTutorial(){
		for (Texture t : tutorialTextures) {
			t.dispose();
		}
		tutorialAtlas.dispose();
	}
	
	public static void  getTutorial(){
			tutorialAtlas = new TextureAtlas(Gdx.files.internal("data/tutorial/tutorial_text.pack"));
			tutorialTextInstructions = new ArrayList<TextureRegion>();
			for (int i = 1; i <= 18; i++ ){
				tutorialTextInstructions.add(tutorialAtlas.findRegion("text0"+ i));
			}
			tutorialImages = new ArrayList<TextureRegion>();
			for (int i = 1; i <= 18; i++) {
				 tutorialImages.add(new TextureRegion(tutorialTextures.get(i-1),0,0,774,1375));
			}
			for (int j = 0; j < tutorialImages.size(); j++) {
				tutorialImages.get(j).flip(false, true);
			}
		
		
	}
	
	
	
	
	
	
}

