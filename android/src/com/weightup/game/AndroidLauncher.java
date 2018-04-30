package com.weightup.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import android.widget.RelativeLayout;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;




public class AndroidLauncher extends AndroidApplication implements AdService,RateIntent {
	InterstitialAd mInterstitialAd;



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// Create the libgdx View

		View gameView = initializeForView(new WeightUP(this,this), config);
		MobileAds.initialize(this,
				"ca-app-pub-4413278369673308~7201910513");
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-4413278369673308/9600310314");
		mInterstitialAd.loadAd(new AdRequest.Builder().build());
		layout.addView(gameView);
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {}

			@Override
			public void onAdClosed() {
				loadIntersitialAd();
			}
		});

		loadIntersitialAd();

		// Hook it all up
		setContentView(layout);
	}

	private void loadIntersitialAd() {
		AdRequest interstitialRequest = new AdRequest.Builder().build();
		mInterstitialAd.loadAd(interstitialRequest);

	}
	@Override
	public void showInterstitial() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mInterstitialAd.isLoaded())
					mInterstitialAd.show();
				else
					loadIntersitialAd();
			}
		});
	}

	@Override
	public boolean isInterstitialLoaded() {
		return mInterstitialAd.isLoaded();
	}

	@Override
	public void startIntent() {
		runOnUiThread(new Runnable() {
			public void run() {
				Intent view = new Intent();
				view.setAction(Intent.ACTION_VIEW);
				view.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.weightup.game"));
				startActivity(view);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
