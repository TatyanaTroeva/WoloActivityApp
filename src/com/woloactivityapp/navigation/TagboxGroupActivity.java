package com.woloactivityapp.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.woloactivityapp.views.CreateActivity;
import com.woloactivityapp.views.TagboxActivity;

public class TagboxGroupActivity extends NavigationGroupActivity {
	public static TagboxGroupActivity tagboxgroupactivity = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		Intent intent = new Intent(this, TagboxActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		View view = getLocalActivityManager().startActivity(
				"TagboxActivity", intent).getDecorView();
		replaceView(view, "TagboxActivity");
		tagboxgroupactivity = this;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == 0) {
			CreateActivity.createActivity_.onActivityResult(requestCode, resultCode, data);
		} else if (requestCode == 1) {
			TagboxActivity.tagboxActivity.onActivityResult(requestCode, resultCode, data);
		}
	
	}

	@Override
	public void onBackPressed() { 
		super.onBackPressed();
	}
}
