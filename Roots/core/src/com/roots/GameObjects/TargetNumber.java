package com.roots.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.roots.RootsHelpers.AssetLoader;

public class TargetNumber {
	private int value;
	private Rectangle box;
	private Sprite button;
	
	
	public TargetNumber(int value, float x, float y, float width, float height){
		this.value = value;
		box = new Rectangle(x,y,width,height);
		button = new Sprite(AssetLoader.targetBacking);
		button.setBounds(box.x, box.y, box.width, box.height);
	}
	
	public int getValue()
	{
		return value;
	}
	public void setValue(int value){
		this.value = value;
	}
	
	public Rectangle getBox(){
		return box;
	}
	
	public String getStringValue(){
		return ""+value;
	}
	public void draw(SpriteBatch batch){
		button.draw(batch);
	}
	
	//This class probably could be a parent of columnNumber, but i liked having it independent. 
	//Just a container for a number and location and drawing information
}

