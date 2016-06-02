package com.woloactivityapp.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class BaseActivity extends Activity implements OnClickListener {
    private int layoutId;
    public Handler handler = null;
    public ProgressDialog baseProgress = null;
    public Typeface regularTypeface_;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutId);
        if (baseProgress == null) {
            baseProgress = new ProgressDialog(this);
            baseProgress.setMessage("Please wait...");
            baseProgress.setCancelable(false);
        }
        regularTypeface_ = Typeface.createFromAsset(getAssets() , "fonts/Roboto_Regular.ttf");
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    protected void setLayoutId(Context context, final int layoutId) {
        this.layoutId = layoutId;
    }
    @Override
    public void onClick(final View view) {
    }
    
	public void setWidth(int width) {
		SharedPreferences.Editor editor = this.getSharedPreferences("com.woloactivityapp.main", 0).edit();
		editor.putInt("width", width);
		editor.commit();
	}

	public int getWidth() {
		SharedPreferences pref = BaseActivity.this.getSharedPreferences("com.woloactivityapp.main", 0);
		return pref.getInt("width", 480);

	}

	public void setHeight(int height) {
		SharedPreferences.Editor editor = this.getSharedPreferences("com.woloactivityapp.main", 0).edit();
		editor.putInt("height", height);
		editor.commit();
	}

	public int getHeight() {
		SharedPreferences pref = this.getSharedPreferences("com.woloactivityapp.main",	0);
		return pref.getInt("height", 800);

	}
}
