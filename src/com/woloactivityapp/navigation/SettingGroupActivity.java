package com.woloactivityapp.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.woloactivityapp.views.StuffActivity;

public class SettingGroupActivity extends NavigationGroupActivity {
	public static SettingGroupActivity settingsgroupactivity = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		Intent intent = new Intent(this, StuffActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		View view = getLocalActivityManager().startActivity(
				"SettingActivity", intent).getDecorView();
		replaceView(view, "SettingActivity");
		settingsgroupactivity = this;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{

	}
	@Override
	public void onBackPressed() { 
		super.onBackPressed();
	}
}

