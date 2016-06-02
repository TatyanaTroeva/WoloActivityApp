package com.woloactivityapp.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.flurry.android.FlurryAdListener;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAdType;
import com.flurry.android.FlurryAds;
import com.flurry.android.FlurryAgent;
import com.woloactivityapp.main.R;

public class AdsActivity extends Activity implements
		FlurryAdListener {
	FrameLayout adLayout;
	private final String kLogTag = "FlurryAdServingAPI";
	public static String apiKey;
	private String adSpace;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	      requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mContext = AdsActivity.this;
		apiKey = getResources().getString(R.string.flurry_api_key);
		adSpace = getResources().getString(R.string.adSpaceName);
	}

	@Override
	public void onStart() {
		super.onStart();
		try {

			FlurryAds.setAdListener(this);
			FlurryAgent.onStartSession(mContext, apiKey);
			adLayout = new FrameLayout(AdsActivity.this);
			if (!FlurryAds.isAdReady(adSpace))
			FlurryAds.fetchAd(mContext, adSpace, adLayout,	FlurryAdSize.FULLSCREEN);
		} catch (Exception e) {
			Log.e(kLogTag, e.getMessage());
		}
	}

	public void spaceDidReceiveAd(String adSpace) {
		// called when the ad has been prepared, ad can be displayed:
		Log.d(kLogTag, "spaceDidReceiveAd( " + adSpace + " )");

		FlurryAds.displayAd(mContext, adSpace, adLayout);
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAds.removeAd(mContext, adSpace, adLayout);
		FlurryAgent.onEndSession(mContext);
	}

	@Override
	public void onAdClicked(String arg0) {
		Log.d(kLogTag, "onAdClicked( " + arg0 + " )");

	}

	@Override
	public void onAdClosed(String arg0) {
		Log.d(kLogTag, "onAdClosed( " + arg0 + " )");
	
	}

	@Override
	public void onAdOpened(String arg0) {
		Log.d(kLogTag, "onAdOpened( " + arg0 + " )");
	

	}

	@Override
	public void onApplicationExit(String arg0) {
		Log.d(kLogTag, "onApplicationExit( " + arg0 + " )");

	}

	@Override
	public void onRenderFailed(String arg0) {
		Log.d(kLogTag, "onRenderFailed( " + arg0 + " )");

	}

	@Override
	public void onVideoCompleted(String arg0) {
		Log.d(kLogTag, "onVideoCompleted( " + arg0 + " )");

	}

	@Override
	public boolean shouldDisplayAd(String arg0, FlurryAdType arg1) {
		AdsActivity.this.finish();
		return true;
	}

	@Override
	public void spaceDidFailToReceiveAd(String arg0) {
		Log.d(kLogTag, "spaceDidFailToReceiveAd(" + arg0 + " )");

	}

	@Override
	public void onRendered(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
