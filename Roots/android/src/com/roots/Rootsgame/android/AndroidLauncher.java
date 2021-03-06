package com.roots.Rootsgame.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.roots.Rootsgame.RootsGame;
import com.roots.Services.IGoogleServices;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices {
	private GameHelper _gameHelper;
	private final static int REQUEST_CODE_UNUSED = 9002;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//create the gamehelper.
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);
		
		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInSucceeded()
			{
			}

			@Override
			public void onSignInFailed()
			{
			}
		};
		
		_gameHelper.setup(gameHelperListener);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new RootsGame(this), config);
	}

	@Override
	public void signIn() {
		if(!isSignedIn()){
			try{
				runOnUiThread(new Runnable(){
					//@Override
					public void run(){
						_gameHelper.beginUserInitiatedSignIn();
						}
					});
			}
			catch (Exception e){
				Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
			}
		}
		
	}

	@Override
	public void signOut() {
		try{
			runOnUiThread(new Runnable(){
				//@Override
				public void run(){
					_gameHelper.signOut();
					}
				});
		}
		catch (Exception e){
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
		
	}

	@Override
	public void rateGame() {
		// Replace the end of the URL with the package of your game
		String str ="";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
		}

	@Override
	public void submitScore(long score) {
		if (isSignedIn() == true){
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		}
		else
		{
		// Maybe sign in here then redirect to submitting score?
		}
		
	}

	@Override
	public void showScores() {
		if (isSignedIn() == true)
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		else{
			// Maybe sign in here then redirect to showing scores?
			
			}
		
	}

	@Override
	public boolean isSignedIn() {
		return _gameHelper.isSignedIn();

	}
	
	
	
	
	
	@Override
	protected void onStart(){
		super.onStart();
		_gameHelper.onStart(this);
	}

	@Override
	protected void onStop(){
		super.onStop();
		_gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}
}
