package com.roots.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Movable {

	
	
	private TextureRegion image;
	private float x,y,width,height,moveInc;
	private float baseX, baseY;
	
	public Movable(float x, float y,float width, float height,TextureRegion image,float moveInc){
		this.baseX = x;
		this.baseY = y;
		this.setX(x);
		this.setY(y);
		this.width = width;
		this.height = height;
		this.image = image;
		this.moveInc = moveInc;
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(image,getX(),getY(),width,height);
	}
	
	public void animateDown(){
		this.setY(this.getY() + moveInc);
	}
	
	public void stopAnimate(){
		this.setY(baseY);
	}

	private float getX() {
		return x;
	}

	private void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
