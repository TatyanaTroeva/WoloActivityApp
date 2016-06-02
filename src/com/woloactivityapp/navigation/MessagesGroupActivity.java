package com.woloactivityapp.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.woloactivityapp.views.MessagesActivity;

public class MessagesGroupActivity extends NavigationGroupActivity {
	public static MessagesGroupActivity messagesgroupactivity = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		Intent intent = new Intent(this, MessagesActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		View view = getLocalActivityManager().startActivity(
				"MessagesActivity", intent).getDecorView();
		replaceView(view, "MessagesActivity");
		messagesgroupactivity = this;
	}
	
	@Override
	public void onBackPressed() { 
		super.onBackPressed();
	}
}
