package com.woloactivityapp.navigation;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class NavigationGroupActivity extends ActivityGroup {
	@Override
	protected void onDestroy() {
		history.clear();
		strID.clear();
		strAllID.clear();
		super.onDestroy();
	}

	ArrayList<View> history;
	ArrayList<String> strID;
	ArrayList<String> strAllID;
	NavigationGroupActivity group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setRequestedOrientation(1);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		history = new ArrayList<View>();
		strID = new ArrayList<String>();
		strAllID = new ArrayList<String>();
		group = this;
	}

	public void changeView(View v, String id) {
		
		strID.remove(history.size() - 1);
		history.remove(history.size() - 1);
		history.add(v);
		strID.add(id);
		setContentView(v);
	}
	public void changeView(String id) {
		while (strID!=null && strID.size()>0 && !id.equals(strID.get(strID.size()-1))) {			
			strID.remove(history.size() - 1);
			history.remove(history.size() - 1);
		}
		if(history!=null && history.size()>0)
			setContentView(history.get(history.size()-1));
	}
	public void replaceView(View v, String id) {
		
		Log.d("MK", "REPLACE VIEW...");
		strID.add(id);
		strAllID.add(id);
		history.add(v);
		setContentView(v);
	}

	public void back() {
		if (history.size() > 1) {
			group.getLocalActivityManager().destroyActivity(strID.get(strID.size() - 1), true);
			history.remove(history.size() - 1);
			strID.remove(strID.size() - 1);
			setContentView(history.get(history.size() - 1));
		} else {
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		group.back();
//		exitMessage();
		return;
	}

	public void exitMessage()
	{
    	Context context = getParent() == null ? this : getParent();
    	Builder builder = new AlertDialog.Builder(context);
    	builder.setTitle("");
		builder.setMessage("Do you want to exit?");
		builder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
							dialog.dismiss();
					}
				});
		
		builder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
							dialog.dismiss();
							System.exit(0);
							
						}
				});
		
		

		Dialog dialog = builder.create();
		dialog.show();
	}
}