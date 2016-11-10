package com.roots.RootsHelpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CoinManager {
	private TextureRegion meterOne,meterTwo,meterThree,coin;
	private float coordForCoinBottom = 0.0f;
	
	public CoinManager(TextureRegion one,TextureRegion two,TextureRegion three,TextureRegion coin ){
		this.meterOne = one;
		this.meterTwo = two;
		this.meterThree = three;
		this.coin = coin;
	}
	
	
	public void draw(SpriteBatch batch, int rank){
		switch (rank){
		case 1:
			batch.draw(meterOne,60f,240,215f,230f);
			coordForCoinBottom = 240+(33)+90*1.1f;
			break;
		case 2:
			batch.draw(meterTwo,60f,240,215f,428f);
			coordForCoinBottom = 240+(33)+3*90*1.1f;
			break;
		case 3:
			batch.draw(meterThree, 60f,240,215f,824f);
			coordForCoinBottom = 240+(33)+7*90*1.1f;
			break;
		}
		
			
		
	}
	
	public void drawCoins(SpriteBatch batch, int rank, int wins){
		for (int i = 1; i <= wins; i++) {
			if (i%2 ==0){
				batch.draw(coin, (60+(24)+90*1.1f), coordForCoinBottom - 90*1.1f*((i/2)-1),(60*1.1f),(60*1.1f));
			}
			else{
				batch.draw(coin, (60+(24)), coordForCoinBottom - 90*1.1f*((i+1)/2-1),(60*1.1f),(60*1.1f));
			}
		}
	}
}
