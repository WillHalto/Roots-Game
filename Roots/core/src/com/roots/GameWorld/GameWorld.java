package com.roots.GameWorld;
import java.util.ArrayList;
import java.util.List;









import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.roots.GameObjects.ColumnNumber;
import com.roots.GameObjects.TargetNumber;
import com.roots.RootsHelpers.AssetLoader;
import com.roots.RootsHelpers.LevelGenerator;
import com.roots.RootsHelpers.SquaredModeTimer;
import com.roots.Rootsgame.RootsGame;
import com.roots.Screens.Tutorial;

public class GameWorld {
	private float height;
	private float width;
	
	//the margins for partial scoring
	private static final float scoreMargin = .10f;
	private RootsGame game;
	private int score,scoreAdded,addCount,background,rank,difficulty,winsAtRank;//moves was here
	
	//private static final int MOVES = 5;
	private ColumnNumber numOne, numTwo, numThree, numFour,currentSelection;
	
	private TargetNumber target;
	private LevelGenerator rootGen;
	private Tutorial tutorial;
	private List<ColumnNumber> column;
	private boolean displayScoreUpdate,squaredMode,exitCheck,audioON,squaredStart,tutorialLoaded,rankAdvance;
	private long pauseTime;;
	private SquaredModeTimer timer;
	private static final int RANK_ONE_WINS = 4,RANK_TWO_WINS = 8,RANK_THREE_WINS = 16;
	public enum GameState{
		MENU, RUNNING, GAMEOVER, HIGHSCORE
	};
	private GameState currentState;
	
	

	public GameWorld(float height, float width,RootsGame game){
		rootGen = new LevelGenerator();
		this.height = height;
		this.width = width;
		this.game = game;
		//we'll need a timer for squared mode
		timer = new SquaredModeTimer(this);
		
		currentState = GameState.MENU;
		
		
		setAudioON(true);
		//initialize the column and components
		target = new TargetNumber(0,width/2-180,240,360,240);
		numOne = new ColumnNumber(0,width/2-120,540,240,240);
		numTwo = new ColumnNumber(0,width/2-120,840,240,240);
		numThree = new ColumnNumber(0,width/2-120,1140,240,240);
		numFour = new ColumnNumber(0,width/2-120,1440,240,240);
		column = new ArrayList<ColumnNumber>();
		column.add(numOne);
		column.add(numTwo);
		column.add(numThree);
		column.add(numFour);
		// the background is denoted by an int from 1-5
		background = 1;
		rank =1;
		
	}
	//resets everything, and gets new level info from the generator
	private void newLevel() {
		rootGen.setRank(rank);
		rootGen.getLevel();
		rootGen.setDepth(difficulty);
		for (int i = 0; i < rank; i++) {
			column.get(i).setValue(rootGen.getStartNumAt(i));
		}
//		numOne.setValue(rootGen.getStartNumAt(0));
//		numTwo.setValue(rootGen.getStartNumAt(1));
//		numThree.setValue(rootGen.getStartNumAt(2));
//		numFour.setValue(rootGen.getStartNumAt(3));
		target.setValue(rootGen.getTargetValue());
		currentSelection = null;
		addCount = 0;
		for (int i = 0; i < rank; i++) {
			column.get(i).showInColumn(true);
			column.get(i).setSelected(false);
			column.get(i).resetBox();
		}
		if (rank < 4){
			for (int i = rank; i < 4; i++){
				column.get(i).showInColumn(false);
				column.get(i).setSelected(false);
				column.get(i).resetBox();
			}
		}
		
//		setMoves(MOVES);
//		//addcount tracks how many times we've added a column number, to display the right background
//		addCount = 0;
//		if(!squaredMode){
//			background = 1;
//		}
		
		
		
		
		
	}
	//scores the level, determines if it's squared mode, and gets a new one
	public void advance(){
		System.out.println("regular called");
		scoreAdded = computeScore();
		incrementScore(computeScore());
		if(scoreAdded == 0){
			endGame();
			if(audioON){
				AssetLoader.gameOver.play();
			}
		}
		else {
			
			winsAtRank ++;
			if((rank < 4) && winsAtRank >= getWinsForRank(rank) ){
				rankAdvance = true; //lets us know that we shouldn't advance until the player taps, and advance rank is called
			}
		
			else{
				checkSquaredMode();
				if(!squaredMode){
					setBackground(1);
			}
				if(!squaredStart&&!(currentState == GameState.GAMEOVER)){
						if(audioON){
							AssetLoader.advance.play();
					}
				
			}
				displayScore();
				if(!(currentState == GameState.GAMEOVER || currentState == GameState.HIGHSCORE)){
					newLevel();
				}
			}
		}
	}
		
		
	public void advanceRank() { //has the same functionality as the meat of advance, but lets us wait to call it
		System.out.println("rank called");
		rank++;
		winsAtRank = 0;
		rankAdvance = false;
		//checkSquaredMode();
		if(!squaredMode){
			setBackground(1);
	}
		if(!squaredStart&&!(currentState == GameState.GAMEOVER)){
				if(audioON){
					AssetLoader.advance.play();
			}
		
	}
		displayScore();
		if(!(currentState == GameState.GAMEOVER || currentState == GameState.HIGHSCORE)){
			newLevel();
		}
		
		
	}
	private int getWinsForRank(int i){
		switch(i){
		case 1:
			return RANK_ONE_WINS;
		case 2:
			return RANK_TWO_WINS;
		case 3:
			return RANK_THREE_WINS;
		default:
			return RANK_THREE_WINS;
		
		}
	}
	//checks if it should be squared mode, that is, if all the numbers were used to make the target number
	private void checkSquaredMode() {
		if(rank <= 3){
			return;
		}
		ColumnNumber closest = getClosestNum();
		if (closest.getValue() == target.getValue()){ //if we have the number
			for (ColumnNumber columnNumber : column) {
				if(columnNumber != closest && columnNumber.shouldShow())//if there is another number still there
				{
					return;
				}
			}
			if(! squaredMode){
				setSquaredMode(true);
				setBackground(5);
				//play the power up noise if squared mode is just starting
				if(audioON){
					AssetLoader.squaredModeStart.play();
				}
				squaredStart = true;
				timer.start();
				}
			else{
				timer.addTime(5);
			}
		}
		
	}
	
	
	
	private void displayScore(){
		displayScoreUpdate = true;
	}
	
	private void incrementScore(int roundScore) {
		score += roundScore;
		
	}
	
	public int getScore(){
		return score;
	}
	
	//determines the score for the round based on the predefined margins 
	//multiple scores for multiple numbers in the margins
	private int computeScore() {
		int finalAdded = 0;;
		float diff = scoreMargin*target.getValue();
		for ( ColumnNumber num: column) {
			//only score numbers that still exist
			if(num.shouldShow()){
				int scoreToAdd = 0;
				float playerDiff = Math.abs(target.getValue()-num.getValue());
				
				if(playerDiff <= diff){
					scoreToAdd = 2;
					
				}if(playerDiff ==0){
					scoreToAdd = 4;
				}
				
				if(squaredMode){
					scoreToAdd = scoreToAdd*scoreToAdd;
				}
				finalAdded += scoreToAdd;
			}
		}
		return finalAdded;
		
		
	}
	//gets the closest number to the target number from the column
	private ColumnNumber getClosestNum() {
		int minDiff = Integer.MAX_VALUE;
		ColumnNumber closest = null;
		for (ColumnNumber columnNumber : column) {
			if(columnNumber.shouldShow()){
				if(Math.abs(target.getValue()-columnNumber.getValue()) < minDiff ){
					minDiff = Math.abs(target.getValue()-columnNumber.getValue());
					closest = columnNumber;
				}
			}
		}
		return closest;
	}
	
	
	public void select(ColumnNumber num)
	{
		num.setSelected(true);
		currentSelection = num;
	}
	
	public void clearSelection(ColumnNumber num){
		num.setSelected(false);
		currentSelection = null;
	}
	
	//updates, the only time this changes is if it's running.
	public void update(float delta) { 
		
		switch (currentState) {
	        case RUNNING:
	            updateRunning(delta);
	            break;
	        default:
	        	break;
	        }

	    }
	
	
	//updates the column numbers (to show animation) and advances if the moves are all used up
	private void updateRunning(float delta) {
//		if(squaredMode){
//			timerTwo.update(delta);
//		}
		for (ColumnNumber columnNumber : column) {
			columnNumber.update(delta);
		}
//		if(moves == 0){
//			advance();
//		}
//		if(Gdx.input.isKeyPressed(Keys.BACK)){
//			advance();
//		}

	}
	
	//zeroes everything out, clears the timer, and checks for a high score
	private void endGame(){
		//moves = 0;
		currentState = GameState.GAMEOVER;
		Timer.instance().clear();
		timer.reset();
		setSquaredMode(false);
		if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
            currentState = GameState.HIGHSCORE;
        }
		setBackground(1);
		winsAtRank = 0;
		rankAdvance = false;
		
	}
	

	private void setBackground(int i) {
		this.background = i;
		
	}
	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}
	public boolean isHighScore() {
	    return currentState == GameState.HIGHSCORE;
	}
	
	
	//called when play game is pushed from the menu
	public void start(){
		currentState = GameState.RUNNING;
		score = 0;
		rank = 1;
		difficulty = 4;
		newLevel();
	}
	
	public void restart(){
		start();
	}
	
	
	
	//tells us to display the back menu
	public void clickBack(){
		exitCheck = !exitCheck;
		
	}

	
	
	
	
	

	
	
	public float getHeight()
	{
		return height;
	}
	public float getWidth()
	{
		return width;
	}
	
	public void setWidth(float width){
		this.width = width;
		System.out.println(width);
		
	}
	
	public TargetNumber getTarget()
	{
		return target;
	}
	public ColumnNumber getNumOne(){
		return numOne;
	}
	public ColumnNumber getNumTwo(){
		return numTwo;
	}
	public ColumnNumber getNumThree(){
		return numThree;
	}
	public ColumnNumber getNumFour(){
		return numFour;
	}
	
	public List<ColumnNumber> getColumn(){
		return column;
	}
	
	public boolean getDisplayScoreUpdate(){
		return displayScoreUpdate;
	}
	public void setDisplayScoreUpdate(boolean b){
		displayScoreUpdate = b;
	}
	
	public int getScoreAdded(){
		return scoreAdded;
	}
	
	public SquaredModeTimer getTimer(){
		return timer;
	}
	
	
	//handles adding and selection of column numbers
	public void clickBox(ColumnNumber num) {
		if((num.shouldShow() == false)||rankAdvance)//||moves <= 0)
		{
			return;
		}
		
		if(null == currentSelection){
			currentSelection = num;
			num.setSelected(true);
		}
		else if (num == currentSelection) {
			currentSelection = null;
			num.setSelected(false);
		}
		else{
			num.setValue(num.getValue()+currentSelection.getValue());
			num.animateGrow();
			currentSelection.showInColumn(false);
			currentSelection = null;
			//moves --;
			addCount ++;
			//if(!(//moves <= 0 && 
				//	computeScore() == 0)){
				if(audioON){
					playAddNoise(addCount);
				}
				if(!squaredMode){
					incrementBG();
				}
			}
			
			
			
		}
		
	
	
	//based on the index passed in, roots the appropriate number and plays noise
	public void rootNum(int i) {
		ColumnNumber num = column.get(i);
		if(!num.shouldShow()||rankAdvance)//||moves <= 0)
		{
			return;
		}
		if(num.root()){
			//moves --;
			if(audioON){
				AssetLoader.root.play();
			}
			if(null != currentSelection){
				currentSelection.setSelected(false);
				currentSelection = null;
			}
		}
		
	}
	//same as root, but for squaring
	public void squareNum(int i){
		ColumnNumber num = column.get(i);
		if(!num.shouldShow()||rankAdvance)//|| moves <= 0)
		{
			return;
		}
		if(num.square()){
			//moves --;
			if(audioON){
				AssetLoader.square.play();
			}
			if(null != currentSelection){
				currentSelection.setSelected(false);
				currentSelection = null;
			}
		}
	}
	
	
	
	
	public void setSquaredMode(boolean b) {
		squaredMode = b;
		
	}
	
	public boolean getSquaredMode(){
		return squaredMode;
	}
//	public int getMoves() {
//		return moves;
//	}
//	private void setMoves(int moves) {
//		this.moves = moves;
//	}
	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}
	public boolean isMenu(){
		return currentState == GameState.MENU;
	}
	public void resetBg(){
		background = 1;
	}
	
	public GameState getState(){
		return currentState;
	}
//	public boolean settingsDisplayed(){
//		return settingsUp;
//	}
	public boolean exitCheckDisplayed(){
		return exitCheck;
	}
	
	
	//called to go back to the menu
	public void mainMenu() {
		endGame();
		currentState = GameState.MENU;
		exitCheck = false;
	}
	
	//called when adding to play the progression of add tones in the right order
	private void playAddNoise(int addNum){
		switch(addNum){
		case 1:
			AssetLoader.firstAdd.play();
			break;		
		case 2:
			AssetLoader.secondAdd.play();
			break;
		case 3:
			AssetLoader.thirdAddReg.play();
		}
	}
	
	
	public int getBG(){
		return background;
	}
	private void incrementBG(){
		background ++;
		if(background > 5){
			background = 5;
		}
	}
	
	//store the time when we paused
	public void pause() {
		if(getSquaredMode()){
		pauseTime = TimeUtils.nanoTime();
		}
		
	}
	//if in squared mode, decrements the timer by the amount of time the game was hidden, so minimizing doesnt pause squared mode
	public void resume() {
		if(getSquaredMode()){
		int timeElapsed = (int)((TimeUtils.nanoTime() - pauseTime)/1000000000);
		if(timeElapsed >= timer.getTime()){
			timer.reset();
			setSquaredMode(false);
			resetBg();
		}
		else{
			timer.setTime(timer.getTime()-timeElapsed);
		}
		}
		
	}
	
	//audio control. all audio is played from this class except for the timer noises, which uses the getter to know if it should play sound.
	
	public void setAudioON(boolean b){
		audioON = b;
	}
	public boolean getAudioOn(){
		return audioON;
	}
	
	public void toggleAudio(){
		audioON = !audioON;
	}
	public boolean getSquaredStart(){
		return squaredStart;
	}
	public void setSquaredStart(boolean b){
		squaredStart = b;
	}
	public RootsGame getGame() {
		return  this.game;
	}
	public boolean getTutorialLoaded(){
		return tutorialLoaded;
	}
	public void setTutorialLoaded(boolean b){
		this.tutorialLoaded = b;
	}
	public void setTutorial (Tutorial t){
		this.tutorial = t;
	}
	public void showTutorial(){
		if(!getTutorialLoaded()){
			System.out.println("load new tut");
			Tutorial t = new Tutorial(getGame());
			getGame().setScreen(t);
			setTutorial(t);
			setTutorialLoaded(true);
			}
		else{
			getGame().setScreen(this.tutorial);
			System.out.println("already have a tut");
		}
	}
	public int getRank(){
		return rank;
	}
	public int getWins(){
		return winsAtRank;
	}
	public boolean checkRankAdvance(){
		return rankAdvance;
	}
	public void setRankAdvance(boolean b){
		this.rankAdvance = b;
	}
	
	
	
	

}

