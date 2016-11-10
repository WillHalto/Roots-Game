package com.roots.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class TextButton {
	private float x,y, width, height,scale;
	private Rectangle bounds;
	private String stringValue;
	private boolean isPressed = false;
	private boolean lockPressed = false;
	private BitmapFont font;
	
	public TextButton(float x, float y, float width, float height,String value,BitmapFont font,float scale){
		this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.stringValue = value;
        this.font = font;
        this.scale = scale;
        bounds = new Rectangle(x,y,width,height);
	}
	
	
	public boolean isClicked(float screenX, float screenY) {
        return bounds.contains(screenX, screenY);
    }
	
	public void draw(SpriteBatch batcher) {
		font.setScale(scale,-scale);
        if (isPressed||lockPressed) {
        	font.setColor(0,0,0,1);
            font.draw(batcher,stringValue, x+40,y);
            font.setColor(1, 1, 1, 1);
        } else {
        	font.draw(batcher,stringValue, x+40,y);
        }
        font.setScale(1,-1);
    }
	
	public boolean touchDown(float screenX, float screenY) {

	        if (bounds.contains(screenX, screenY)) {
	            isPressed = true;
	            return true;
	        }

	        return false;
	    }
	
	
    public boolean touchUp(float screenX, float screenY) {
        
        // It only counts as a touchUp if the button is in a pressed state.
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }
        
        // Whenever a finger is released, we will cancel any presses.
        isPressed = false;
        return false;
    }
    
    
    public Boolean isPressed(){
    	return isPressed;
    }
    
    public Rectangle getBounds(){
    	return bounds;
    }
    public float getX(){
    	return x;
    }
    public float getY(){
    	return y;
    }
    public void lockPressed(){
    	lockPressed = true;
    }

    
}

