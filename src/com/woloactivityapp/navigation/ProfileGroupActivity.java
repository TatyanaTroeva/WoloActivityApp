package com.woloactivityapp.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.woloactivityapp.views.ProfileActivity;

public class ProfileGroupActivity extends NavigationGroupActivity {
	public static ProfileGroupActivity profilegroupactivity = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		Intent intent = new Intent(this, ProfileActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		View view = getLocalActivityManager().startActivity(
				"ProfileActivity", intent).getDecorView();
		replaceView(view, "ProfileActivity");
		profilegroupactivity = this;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		ProfileActivity.profileActivity.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onBackPressed() { 
		super.onBackPressed();
	}
}
