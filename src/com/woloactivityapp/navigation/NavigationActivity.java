package com.woloactivityapp.navigation;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class NavigationActivity extends Activity {
	int nIndex = 0;
	
	public void goNextHistory(String id, Intent intent) { 
		NavigationGroupActivity parent = ((NavigationGroupActivity) getParent());
		
		nIndex++;
		String strInsertID = id;
		
		strInsertID = strInsertID + nIndex;
		double nRand = Math.random();
		strInsertID = strInsertID + Double.toString(nRand);
		try{
			View view = parent.group.getLocalActivityManager().startActivity(strInsertID,intent).getDecorView();
			parent.group.replaceView(view, strInsertID);
		}catch (Exception e) {
			//goNextHistory(id, intent);
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		NavigationGroupActivity parent = ((NavigationGroupActivity) getParent());
		if(parent == null)
			finish();
		else
			parent.back();
	}
	
}
