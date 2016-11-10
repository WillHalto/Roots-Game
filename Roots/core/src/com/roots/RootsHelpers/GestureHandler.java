package com.roots.RootsHelpers;




import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.roots.GameWorld.GameRenderer;
import com.roots.GameWorld.GameWorld;
import com.roots.GameWorld.GameWorld.GameState;
import com.roots.Rootsgame.RootsGame;
import com.roots.Screens.Tutorial;
import com.roots.ui.SimpleButton;
import com.roots.ui.TextButton;

public class GestureHandler implements GestureListener{
	

	public GameWorld myWorld;
	
	private float prevX, prevY;
	private Rectangle oneBox, twoBox, threeBox, fourBox, targBox;
	
	private OrthographicCamera cam;
	private SimpleButton playButton,tutorialButton,leaderButton,moreGamesButton,audioButton;
	private TextButton exitCheckLeft, exitCheckRight, exitCheckBot,play,leaderboard;
	private List<SimpleButton> mainMenuButtons;
	private List<TextButton> textButtons;
	
	private boolean selectNums = false;

	private boolean midCross = false;
	
	
	public GestureHandler(GameRenderer rend,GameWorld world)
	{
		myWorld = world;
		//get camera from the renderer
		cam = rend.getCam();
		//all the boxes for checking touches
		oneBox = myWorld.getNumOne().getBox();
		twoBox = myWorld.getNumTwo().getBox();
		threeBox = myWorld.getNumThree().getBox();
		fourBox = myWorld.getNumFour().getBox();
		targBox = myWorld.getTarget().getBox();
		mainMenuButtons = new ArrayList<SimpleButton>();
		textButtons = new ArrayList<TextButton>();
		
		loadButtons();
		//solving the problem of the renderer needing the buttons by passing the lists back up via setters
		rend.setMainMenuButtons(mainMenuButtons);
		rend.setAudioButton(audioButton);
		rend.setExitCheckButtons(textButtons);
		
	}

	

	



	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Vector3 coords = new Vector3(x, y,0);
		//scale the touch input
		cam.unproject(coords);
		prevX = coords.x;
		prevY = coords.y;
		//check where its touched using scaled input
		audioButton.touchDown(coords.x, coords.y);
		if(myWorld.isMenu()&&!myWorld.exitCheckDisplayed()){
			for (SimpleButton sButton : mainMenuButtons) {
				sButton.touchDown(coords.x, coords.y);
				}
			}
		if(myWorld.exitCheckDisplayed()){
			for (TextButton myButton: textButtons) {
				myButton.touchDown(coords.x,coords.y);
			}
		}
		return true;
	}
	






// method to handle input from the input handler that's in the multiplexer
	public void handleTouchUp(float x, float y){
		midCross = false;
		Vector3 coords = new Vector3(x, y,0);
		cam.unproject(coords);
		if(audioButton.touchUp(coords.x, coords.y)){
			myWorld.toggleAudio();
			if(myWorld.getAudioOn()){
				audioButton.setUniversalImage(AssetLoader.audioOnIcon);
			}
			else{
				audioButton.setUniversalImage(AssetLoader.audioOffIcon);
			}
			//System.out.println("called clickSettings");
		}
		if(myWorld.isMenu()&&!myWorld.exitCheckDisplayed()){
			if(playButton.touchUp(coords.x, coords.y)){
				if(myWorld.getAudioOn()){
					AssetLoader.menuSelect.play();
				}
				myWorld.start();
				
			}
			else if(leaderButton.touchUp(coords.x, coords.y)){
				launchLeaderBoard();
				
			}
			else if(tutorialButton.touchUp(coords.x, coords.y)){
				if(myWorld.getAudioOn()){
					AssetLoader.menuSelect.play();
				}
				//if(AssetLoader.tutorialImages == null){
					AssetLoader.buildTutorialTextures();
					AssetLoader.getTutorial();
				//}
				launchTutorial();
				
			}
//			else if(moreGamesButton.touchUp(coords.x, coords.y)){
//				//launch more games
//			}
		}
		//displaying the back menu 
		if(myWorld.exitCheckDisplayed()){
			if(exitCheckLeft.touchUp(coords.x, coords.y)){
				exitCheckLeft.lockPressed();
				//AssetLoader.tutorialImages = null; //this makes sure we have to reload the textures next time we start the app
				//AssetLoader.tutorialTextures = null;
				Gdx.app.exit();
			}
			if(exitCheckRight.touchUp(coords.x, coords.y)){
				myWorld.clickBack();
			}
			if(exitCheckBot.touchUp(coords.x,coords.y)){
				myWorld.mainMenu();
				selectNums = false;
			}
		}
		
	 }
	
	
	
	
private void launchTutorial() {
	myWorld.showTutorial();
}







private void launchLeaderBoard() {
		RootsGame.googleServices.signIn();
		RootsGame.googleServices.submitScore(AssetLoader.getHighScore());
		RootsGame.googleServices.showScores();
		
	}






	//Called from the input handler to handle the back button
	public void back(){
		myWorld.clickBack();
	}
	
	

	@Override
	public boolean tap(float x, float y, int count, int button) {;
		Vector3 coords = new Vector3(x, y,0);
		cam.unproject(coords);
		if(!myWorld.isMenu()&& !selectNums &&!myWorld.exitCheckDisplayed()){
			selectNums = true;
		}
		else if(!myWorld.isMenu()&&!myWorld.exitCheckDisplayed()){
			handleTap(coords.x,coords.y);
		}
		return true;
	}
//checking where the taps were. could use rect.contains, but this was an early method and I want the option to modify the width of the hit boxes
	private void handleTap(float x, float y) {
		if(myWorld.isGameOver()||myWorld.isHighScore())
		{
			myWorld.restart();
		}
		else if((x > oneBox.x)&&(x < oneBox.x+oneBox.width)&&(y > oneBox.y)&& (y < oneBox.y+oneBox.height)){
			 myWorld.clickBox(myWorld.getNumOne());
		 }
		 else if ((x > twoBox.x)&&(x < twoBox.x+twoBox.width)&&(y > twoBox.y)&& (y < twoBox.y+twoBox.height)) {
			myWorld.clickBox(myWorld.getNumTwo());
		}
		 else if ((x > threeBox.x)&&(x < threeBox.x+threeBox.width)&&(y > threeBox.y)&& (y < threeBox.y+threeBox.height)) {
			myWorld.clickBox(myWorld.getNumThree());
		}
		 else if ((x > fourBox.x)&&(x < fourBox.x+fourBox.width)&&(y > fourBox.y)&& (y < fourBox.y+fourBox.height)) {
			myWorld.clickBox(myWorld.getNumFour());
		}
		 else if ((x > targBox.x)&&
				 (x < targBox.x+targBox.width)&&
				 (y > targBox.y)&& 
				 (y < targBox.y+targBox.height)&&!myWorld.checkRankAdvance()) {
			myWorld.advance();
		}
		 else if(myWorld.checkRankAdvance()){
				myWorld.advanceRank();
			}
		 
		 
		
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}
//handles the swipe, squaring or rooting based on direction
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if(myWorld.getState()==GameState.GAMEOVER){
			return true;
		}
		if(myWorld.exitCheckDisplayed()){
			return true;
		}
		Vector3 coords = new Vector3(x, y,0);
		cam.unproject(coords);
		if((coords.x > myWorld.getWidth()/2)&&prevX<myWorld.getWidth()/2){
			handleRightSwipe(coords.y);
			midCross = true;
			
		}
		//make sure we cannot square or root multiple times without lifting the finger between them using midcross boolean
		if(coords.x > oneBox.x + oneBox.width && prevX < oneBox.x + oneBox.width && !midCross)
		{
			handleRightSwipe(coords.y);
			
		}
		else if((coords.x < myWorld.getWidth()/2)&&(prevX > myWorld.getWidth()/2)){
			handleLeftSwipe(coords.y);
			midCross = true;
		}
		if(coords.x < oneBox.x && prevX > oneBox.x && !midCross){
			handleLeftSwipe(coords.y);
		}
		prevX = coords.x;
		prevY = coords.y;
		
		
		return true;
	}
	
	private void handleLeftSwipe(float y)
	{	
		if((oneBox.y<y) && (y < oneBox.y+oneBox.height)){
			myWorld.rootNum(0);
		}
		else if((twoBox.y<y) && (y < twoBox.y+twoBox.height)){
			myWorld.rootNum(1);
		}
		else if ((threeBox.y<y) && (y < threeBox.y+threeBox.height)) {
			myWorld.rootNum(2);
		}
		else if ((fourBox.y<y) && (y < fourBox.y+fourBox.height)) {
			myWorld.rootNum(3);
		}
	}
	
	private void handleRightSwipe(float y)
	{
		if((oneBox.y<y) && (y < oneBox.y+oneBox.height)){
			myWorld.squareNum(0);
			
		}
		else if((twoBox.y<y) && (y < twoBox.y+twoBox.height)){
			myWorld.squareNum(1);
		}
		else if ((threeBox.y<y) && (y < threeBox.y+threeBox.height)) {
			myWorld.squareNum(2);
		}
		else if ((fourBox.y<y) && (y < fourBox.y+fourBox.height)) {
			myWorld.squareNum(3);
		}
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	//initializes the buttons and puts them in lists
	private void loadButtons() {
		float height = AssetLoader.playButtonDown.getRegionHeight();
		playButton = new SimpleButton(myWorld.getWidth()/2-AssetLoader.playButtonDown.getRegionWidth()/2,(float)1920/2-175,AssetLoader.playButtonDown.getRegionWidth(),240,AssetLoader.playButtonUp, 
				AssetLoader.playButtonDown);

		
		leaderButton = new SimpleButton(myWorld.getWidth()/2-AssetLoader.playButtonDown.getRegionWidth()/2,(float)1920/2-175 + height+60 ,AssetLoader.playButtonDown.getRegionWidth(),240,AssetLoader.playButtonUp, 
				AssetLoader.playButtonDown);
		tutorialButton = new SimpleButton(myWorld.getWidth()/2-AssetLoader.playButtonDown.getRegionWidth()/2,(float)1920/2-175 + (height+60)*2 ,AssetLoader.playButtonDown.getRegionWidth(),240,AssetLoader.playButtonUp, 
				AssetLoader.playButtonDown);
		//moreGamesButton = new SimpleButton(myWorld.getWidth()/2-AssetLoader.playButtonDown.getRegionWidth()*3/2,(float)1920/3 + height*12 ,AssetLoader.playButtonDown.getRegionWidth()*3,172,AssetLoader.playButtonUp, 
				//AssetLoader.playButtonDown);
		
		audioButton = new SimpleButton(myWorld.getWidth()-100,10,100,100,AssetLoader.audioOnIcon,AssetLoader.audioOnIcon);
		
		mainMenuButtons.add(playButton);
		mainMenuButtons.add(tutorialButton);
		mainMenuButtons.add(leaderButton);
		//mainMenuButtons.add(moreGamesButton);
		
		exitCheckLeft = new TextButton(myWorld.getWidth()/2-380,myWorld.getHeight()/2-100, 400, 200, "Yeah", AssetLoader.font,1);
		exitCheckRight = new TextButton(myWorld.getWidth()/2+10, myWorld.getHeight()/2-100,400,200, "Nope", AssetLoader.font,1);
		exitCheckBot = new TextButton(myWorld.getWidth()/2-215, myWorld.getHeight()/2+100,600,200, "Main Menu", AssetLoader.font,0.5f);
		textButtons.add(exitCheckLeft);
		textButtons.add(exitCheckRight);
		textButtons.add(exitCheckBot);
	}
	
	public List<SimpleButton> getMainMenuButtons(){
		return mainMenuButtons;
	}
	
	
	
	
	
	
	



}

