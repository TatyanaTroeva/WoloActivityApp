package com.woloactivityapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.User;

public class SplashActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setLayoutId(this, R.layout.activity_splash);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics dispMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dispMetrics);
		setWidth(dispMetrics.widthPixels);
		setHeight(dispMetrics.heightPixels);
		Constants.fDensity = dispMetrics.density;

		User user = Constants.getCurrentUser(this);
		if (user != null && user.getEmail() != null && user.getEmail().length() > 0) {
			startActivity(new Intent(SplashActivity.this, BottomActivity.class));
			finish();
			return;
		}
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, LoginActivity.class));
				SplashActivity.this.finish();
			}
		}, 1000);
	}
}
