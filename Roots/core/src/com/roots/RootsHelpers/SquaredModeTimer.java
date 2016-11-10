package com.roots.RootsHelpers;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.roots.GameWorld.GameWorld;

public class SquaredModeTimer {
	
	private int totalTime = 30;
	private GameWorld myWorld;
	 
	 
	public SquaredModeTimer(GameWorld myWorld){
		this.myWorld = myWorld;
	}
	
	
	
	public void start(){
		Timer.schedule(new Task(){
			@Override
			public void run(){
				totalTime --;
				if(myWorld.getAudioOn()){
					playTimerNoise(totalTime);
				}
				if(totalTime <= 0){
					Timer.instance().clear();
					totalTime = 30;
					myWorld.setSquaredMode(false);
					myWorld.resetBg();
				}
		}
		}, .6f,1.0f);
	}
	
	public void reset(){
		totalTime = 30;
		Timer.instance().clear();
	}
	
	public int getTime(){
		
		return totalTime;
	}
	public void setTime(int t){
		if(t<= 30 && t > 0){
			totalTime = t;
		}
	}
	public void addTime( int t)
	{
		totalTime += 5;
		if(totalTime > 30){
			totalTime = 30;
		}
	}
	
	private void playTimerNoise(int time)
	{
		if(time > 19){
			AssetLoader.timerOne.play();
		}
		else if(time > 9){
			AssetLoader.timerTwo.play();
		}
		else if(time > 4){
			AssetLoader.timerThree.play();
		}
		else if(time > 0){
			AssetLoader.timerFour.play();
		}
		else if(time == 0)
		{
			AssetLoader.squaredEnd.play();
		}
	}
	
	
}


