package com.roots.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.roots.RootsHelpers.AssetLoader;

public class ColumnNumber{
	

	private int value;
	private boolean selected , shown, shouldAnimateRoot, shouldAnimateSquare;
	private Sprite button,buttonPressed;
	
	private float width, height,x,y;
	private static final float BOX_SCALE = 16;
	private Rectangle box;
	
	public ColumnNumber(int value, float x, float y, float width, float height) {
		this.value = value;
		box = new Rectangle(x,y,width,height);
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		button = new Sprite(AssetLoader.numberBacking);
		buttonPressed = new Sprite(AssetLoader.numberSelected);
		shown = false;
	}
	
	
	
	//squares the number that this box holds, checking to make sure it's not too large
	//makes sure a number doesn't stay selected by tap if its squared
	public boolean square()
	{
		if(value <= 256 && value > 1)
		{
			value = value*value;
			selected = false;
			shouldAnimateSquare = true;
			return true;
			
		}
		selected = false;
		return false;
	}
	
	//roots a number, making sure its not still selected and its not less than one
	public boolean root()
	{
		if(isPerfectSquare(value)&& value > 1)
		{
			value = (int)Math.sqrt(value);
			selected = false;
			shouldAnimateRoot = true;
			return true;
		}
		selected = false;
		return false;
	}
	
	
	public void setSelected(boolean value){
		selected = value;
	}
	public boolean getSelected(){
		return selected;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	//distinguishes if this number should appear in the column, or if its been added to another number and should vanish
	public void showInColumn(boolean value){
		
		this.shown = value;
	}
	
	public boolean shouldShow(){
		return shown;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public String getStringValue(){
		return ""+value;
	}
	public void setValue(int value){
		this.value = value;
	}
	
	public Rectangle getBox()
	{
		return box;
	
	}
	//checking if the number is a perfect square
    private boolean isPerfectSquare(Integer n)
    {
        if( n < 0)
        {
            return false;
        }
        switch(n%10)
        {
            //all possibilities for perfect square last digits
            case(0): case(1): case(4): case(5): case(6): case(9):
                int test = (int)Math.sqrt(n);
                return test*test == n; //check to see if the test squared is the original number
            default:
                return false;
        }
    }
    
    //handles the animation of the box growing or shrinking for a square or root, called from gameworld's update method
    public void update(float delta)
    {
    	//currentDelta += delta;
    	if(shouldAnimateRoot && box.width > 1080/7){
    		box.height -= BOX_SCALE; //using the experimentally found increment value to shrink or grow
    		box.width -= BOX_SCALE;
    		repositionBoxRoot();
    		//reset once it changes enough
    		if( box.width <= 1080/7){//all these numbers were determined by trail and error to find what looked good
    			box.width = width;
    			box.height = height;
    			box.x = x;
    			box.y = y;
    			shouldAnimateRoot = false;
    		}
    	}
    	else if(shouldAnimateSquare && box.width < 1080/3){
    		box.width += BOX_SCALE;
    		box.height += BOX_SCALE;
    		repositionBoxSquare();
    		if( box.width >= 1080/3){
    			box.width = width;
    			box.height = height;
    			box.x = x;
    			box.y = y;
    			shouldAnimateSquare = false;
    		}
    	}
    	//reposition our buttons correctly
		button.setBounds(box.x, box.y, box.width, box.height);
		buttonPressed.setBounds(box.x, box.y, box.width, box.height);
    }




	private boolean shouldAnimate() {
		return shouldAnimateRoot || shouldAnimateSquare;
	}
	
	//keep box centered when shrinking/growing
	private void repositionBoxRoot(){
		box.x += BOX_SCALE/2;
		box.y += BOX_SCALE/2;
	}
	private void repositionBoxSquare(){
		box.x -= BOX_SCALE/2;
		box.y -= BOX_SCALE/2;
	}
	//zeroes everything out
	public void resetBox(){
		box.width = width;
		box.height = height;
		box.x = x;
		box.y = y;
		shouldAnimateSquare = false;
		shouldAnimateRoot = false;
	}
	
	public void animateGrow(){
		shouldAnimateSquare = true;
	}
	
	public void draw(SpriteBatch batch){
		if(selected)
		{
			buttonPressed.draw(batch);
		}
		else{
			button.draw(batch);
		}
	}
	
	

}

