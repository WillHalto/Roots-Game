package com.roots.Services;

public class DesktopGoogleServices implements IGoogleServices {
	@Override
	public void signIn()
	{
		System.out.println("DesktopGoogleServices: signIn()");
	}

	@Override
	public void signOut()
	{
		System.out.println("DesktopGoogleServices: signOut()");
	}

	@Override
	public void rateGame()
	{
		System.out.println("DesktopGoogleServices: rateGame()");
	}

	@Override
	public void submitScore(long score)
	{
		System.out.println("DesktopGoogleServices: submitScore(" + score + ")");
	}

	@Override
	public void showScores()
	{
		System.out.println("DesktopGoogleServices: showScores()");
	}

	@Override
	public boolean isSignedIn()
	{
		System.out.println("DesktopGoogleServices: isSignedIn()");
		return false;
	}
	
}
