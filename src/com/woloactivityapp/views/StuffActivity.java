package com.woloactivityapp.views;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;

public class StuffActivity extends NavigationBaseActivity {
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	public static StuffActivity				stuffActivity = null;
	private float 							screenScale_ = 1.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setLayoutId(this,R.layout.activity_stuff);
        super.onCreate(savedInstanceState);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        setListener();
        stuffActivity = this;
        handler = new Handler() {
    		@Override
    		public void handleMessage(Message msg) {
    			
    			super.handleMessage(msg);
    			baseProgress.hide();
    			switch (msg.what) {
    			
    			}
    		}

    	};
        initData();
        
       
        
    }

    public void initView(){
    	screenScale_ = getWidth() / 800.0f;
    	titleCustomTextView_ = (CustomTextView) findViewById(R.id.titleTextView);
    	LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) titleCustomTextView_.getLayoutParams();
		param.setMargins(0, 0, 0, 0);
		param.width = getWidth();
		param.height = (int) (100 * screenScale_); 
		titleCustomTextView_.setLayoutParams(param);
		titleCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60 * screenScale_);
	}
    public void setListener(){

    }
    public void onResume() {
    	super.onResume();

    }
    public void initData(){
    	
    }
    
    public void onClick(View view) {
		super.onClick(view);
		int viewId = view.getId();
		switch (viewId) {
		
		}
    }
 
}