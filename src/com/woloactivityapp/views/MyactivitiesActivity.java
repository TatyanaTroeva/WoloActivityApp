package com.woloactivityapp.views;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.Contest;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;
import com.woloactivityapp.utils.JSONParser;

public class MyactivitiesActivity extends NavigationBaseActivity {
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	private Button							backButton_ = null;
	private CustomTextView					joinedCustomTextView_ = null;
	private CustomTextView					createdCustomTextView_ = null;
	private CustomTextView					requestedCustomTextView_ = null;
	private LinearLayout					joinedLinearLayout_ = null;
	private LinearLayout					createdLinearLayout_ = null;
	private LinearLayout					requestedLinearLayout_ = null;
	public static MyactivitiesActivity      myactivities = null;
	private float						    screenScale_ = 0.0f;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setLayoutId(this,R.layout.activity_myactivities);
        super.onCreate(savedInstanceState);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        setListener();
        myactivities = this;
        handler = new Handler() {
    		@Override
    		public void handleMessage(Message msg) {
    			
    			super.handleMessage(msg);
    			baseProgress.hide();
    			switch (msg.what) {
    			
    			}
    		}
    	};

        
    }
    
    public void initView(){
    	screenScale_ = getWidth() / 800.0f;
    	titleCustomTextView_ = (CustomTextView) findViewById(R.id.titleTextView);
    	RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) titleCustomTextView_.getLayoutParams();
		param.setMargins(0, 0, 0, 0);
		param.width = getWidth();
		param.height = (int) (100 * screenScale_); 
		titleCustomTextView_.setLayoutParams(param);
		titleCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60 * screenScale_);
		
		backButton_ = (Button) findViewById(R.id.backButton);
		param = (RelativeLayout.LayoutParams) backButton_.getLayoutParams();
		param.setMargins(0, 0, 0, 0);
		param.width = (int) (100 * screenScale_); 
		param.height = (int) (100 * screenScale_); 
		backButton_.setLayoutParams(param);

		joinedCustomTextView_ = (CustomTextView) findViewById(R.id.joinedCustomTextView);
		LinearLayout.LayoutParams param1 = (LinearLayout.LayoutParams) joinedCustomTextView_.getLayoutParams();
		param1.setMargins(0, 0, 0, 0);
		param1.width = getWidth();
		param1.height = (int) (100 * screenScale_); 
		joinedCustomTextView_.setLayoutParams(param1);
		joinedCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		
		createdCustomTextView_ = (CustomTextView) findViewById(R.id.createdCustomTextView);
		param1 = (LinearLayout.LayoutParams) createdCustomTextView_.getLayoutParams();
		param1.setMargins(0, 0, 0, 0);
		param1.width = getWidth();
		param1.height = (int) (100 * screenScale_); 
		createdCustomTextView_.setLayoutParams(param1);
		createdCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		
		requestedCustomTextView_ = (CustomTextView) findViewById(R.id.requestedCustomTextView);
		param1 = (LinearLayout.LayoutParams) requestedCustomTextView_.getLayoutParams();
		param1.setMargins(0, 0, 0, 0);
		param1.width = getWidth();
		param1.height = (int) (100 * screenScale_); 
		requestedCustomTextView_.setLayoutParams(param1);
		requestedCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		
		requestedLinearLayout_ = (LinearLayout) findViewById(R.id.requestedLinearLayout);
		joinedLinearLayout_ = (LinearLayout) findViewById(R.id.joinedLinearLayout);
		createdLinearLayout_ = (LinearLayout) findViewById(R.id.createdLinearLayout);
    }
    public void setListener(){
    	backButton_.setOnClickListener(this);
    }
    public void addContestsToLayout(int category, final List<Contest> list) {
    	LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
    	if (category == 0) {
    		if (list == null || list.size() == 0) {
    			joinedCustomTextView_.setVisibility(View.GONE);
    			return;
    		}
    		joinedCustomTextView_.setVisibility(View.VISIBLE);
    		joinedLinearLayout_.removeAllViews();
    		for (int i = 0; i < list.size(); i++) {
        		View itemView = layoutInflater.inflate(R.layout.activtyitemlayout, null);
        		CustomTextView itemTitleCustomTextView = (CustomTextView) itemView.findViewById(R.id.itemTitleCustomTextView);
        		itemTitleCustomTextView.setText(list.get(i).getName());
        		RelativeLayout rootlayout = (RelativeLayout) itemView.findViewById(R.id.rootlayout);
        		if (i % 2 == 0)
        			rootlayout.setBackgroundResource(R.color.backgroundcolor1);
        		else
        			rootlayout.setBackgroundResource(R.color.darkcolor);
        		itemView.setTag(i);
        		itemView.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					Integer tag = (Integer)v.getTag();
    					Intent intent = new Intent(MyactivitiesActivity.this, JoinedActivity.class);
    					intent.putExtra("index", 0);
    					intent.putExtra("contest", list.get(tag));
    					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					MyactivitiesActivity.this.goNextHistory("MyactivitiesActivity", intent);
    				}
    			});
        		itemTitleCustomTextView.setTag(i);
        		itemTitleCustomTextView.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					Integer tag = (Integer)v.getTag();
    					Intent intent = new Intent(MyactivitiesActivity.this, JoinedActivity.class);
    					intent.putExtra("index", 0);
    					intent.putExtra("contest", list.get(tag));
    					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					MyactivitiesActivity.this.goNextHistory("MyactivitiesActivity", intent);
    				}
    			});
        		joinedLinearLayout_.addView(itemView);
        	}
    	} else if (category == 1) {
    		if (list == null || list.size() == 0) {
    			createdCustomTextView_.setVisibility(View.GONE);
    			return;
    		}
    		createdCustomTextView_.setVisibility(View.VISIBLE);
    		createdLinearLayout_.removeAllViews();
    		for (int i = 0; i < list.size(); i++) {
        		View itemView = layoutInflater.inflate(R.layout.activtyitemlayout, null);
        		CustomTextView itemTitleCustomTextView = (CustomTextView) itemView.findViewById(R.id.itemTitleCustomTextView);
        		itemTitleCustomTextView.setText(list.get(i).getName());
        		RelativeLayout rootlayout = (RelativeLayout) itemView.findViewById(R.id.rootlayout);
        		if (i % 2 == 0)
        			rootlayout.setBackgroundResource(R.color.backgroundcolor1);
        		else
        			rootlayout.setBackgroundResource(R.color.darkcolor);
        		itemView.setTag(i);
        		itemView.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					Integer tag = (Integer)v.getTag();
    					Intent intent = new Intent(MyactivitiesActivity.this, JoinedActivity.class);
    					intent.putExtra("index", 1);
    					intent.putExtra("contest", list.get(tag));
    					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					MyactivitiesActivity.this.goNextHistory("MyactivitiesActivity", intent);
    				}
    			});
        		Button trashButton = (Button) itemView.findViewById(R.id.trashButton);
        		trashButton.setVisibility(View.VISIBLE);
        		trashButton.setTag(i);
        		trashButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Integer tag = (Integer)v.getTag();
						showMessage(Constants.getCurrentUser(MyactivitiesActivity.this).getEmail(), list.get(tag).getName());

					}
				});
        		itemTitleCustomTextView.setTag(i);
        		itemTitleCustomTextView.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					Integer tag = (Integer)v.getTag();
    					Intent intent = new Intent(MyactivitiesActivity.this, JoinedActivity.class);
    					intent.putExtra("index", 1);
    					intent.putExtra("contest", list.get(tag));
    					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					MyactivitiesActivity.this.goNextHistory("MyactivitiesActivity", intent);
    				}
    			});
        		createdLinearLayout_.addView(itemView);
        	}
    	} else {
    		if (list == null || list.size() == 0) {
    			requestedCustomTextView_.setVisibility(View.GONE);
    			return;
    		}
    		requestedCustomTextView_.setVisibility(View.VISIBLE);
    		requestedLinearLayout_.removeAllViews();
    		for (int i = 0; i < list.size(); i++) {
        		View itemView = layoutInflater.inflate(R.layout.activtyitemlayout, null);
        		CustomTextView itemTitleCustomTextView = (CustomTextView) itemView.findViewById(R.id.itemTitleCustomTextView);
        		itemTitleCustomTextView.setText(list.get(i).getName());
        		RelativeLayout rootlayout = (RelativeLayout) itemView.findViewById(R.id.rootlayout);
        		if (i % 2 == 0)
        			rootlayout.setBackgroundResource(R.color.backgroundcolor1);
        		else
        			rootlayout.setBackgroundResource(R.color.darkcolor);
        		itemView.setTag(i);
        		itemView.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					Integer tag = (Integer)v.getTag();
    					Intent intent = new Intent(MyactivitiesActivity.this, JoinedActivity.class);
    					intent.putExtra("index", 2);
    					intent.putExtra("contest", list.get(tag));
    					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					MyactivitiesActivity.this.goNextHistory("MyactivitiesActivity", intent);
    				}
    			});
        		itemTitleCustomTextView.setTag(i);
        		itemTitleCustomTextView.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					Integer tag = (Integer)v.getTag();
    					Intent intent = new Intent(MyactivitiesActivity.this, JoinedActivity.class);
    					intent.putExtra("index", 2);
    					intent.putExtra("contest", list.get(tag));
    					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					MyactivitiesActivity.this.goNextHistory("MyactivitiesActivity", intent);
    				}
    			});
        		requestedLinearLayout_.addView(itemView);
        	}
    	}
    }
    public void onResume() {
    	super.onResume();
    	initData();
    }
    
    public void initData(){
    	GetMyActivityTask getMyActivityTask = new GetMyActivityTask();
    	getMyActivityTask.execute();
//    	NotificationManager mNM = (NotificationManager) ProfileActivity.profileActivity.getApplicationContext().getSystemService( Context.NOTIFICATION_SERVICE ) ;
//		android.app.Notification notification = new android.app.Notification() ;
//		notification.flags = android.app.Notification.FLAG_ONGOING_EVENT ;
//		notification.icon = R.drawable.ic_launcher ;
//		notification.tickerText = "WoloActivity Notification" ;
//		notification.vibrate = new long[] { 100, 250, 100, 500 } ;
//		// The PendingIntent to launch our activity if the user selects this notification
//        PendingIntent contentIntent = PendingIntent.getActivity( ProfileActivity.profileActivity.getApplicationContext(), 0, new Intent( ProfileActivity.profileActivity.getApplicationContext(), BottomActivity.class ).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ), PendingIntent.FLAG_UPDATE_CURRENT ) ;
//        // Set the info for the views that show in the notification panel.
//        notification.setLatestEventInfo( ProfileActivity.profileActivity.getApplicationContext(), "WoloActivity", "test", contentIntent ) ;
//        // Send the notification.
//        // We use a layout id because it is a unique number.  We use it later to cancel.
//        mNM.notify( R.drawable.ic_launcher, notification ) ;
    }
    public void onClick(View view) {
		super.onClick(view);
		int viewId = view.getId();
		switch (viewId) {
		case R.id.backButton: {
			onBackPressed();
		}
		    break;
		default:
			break;
		}
    }
    
    private class GetMyActivityTask extends AsyncTask<Void, Void, Object[]>
	{
		ProgressDialog mProgressDialog ;
		ArrayList<Contest> requestedContests = new ArrayList<Contest>();
		ArrayList<Contest> createdContests = new ArrayList<Contest>();
		ArrayList<Contest> joinedContests = new ArrayList<Contest>();
		@Override
		protected void onPostExecute( Object[] result )
		{
			mProgressDialog.dismiss() ;
			
			joinedLinearLayout_.removeAllViews();
			createdLinearLayout_.removeAllViews();
			requestedLinearLayout_.removeAllViews();
			addContestsToLayout(0, joinedContests);
			addContestsToLayout(1, createdContests);
			addContestsToLayout(2, requestedContests);
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( getParent()) ;
			mProgressDialog.setIndeterminate( true ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected Object[] doInBackground( Void... params )
		{
			// your network operation
			User user = Constants.getCurrentUser(getParent());
			Object[] list = UserAPI.getJoinedContests(user.getEmail());
			if (list != null && list.length > 0) {
				joinedContests = JSONParser.parseContestListInfo((String)list[1]);
			}
			list = UserAPI.getRequestedContests(user.getEmail());
			if (list != null && list.length > 0) {
				requestedContests = JSONParser.parseContestListInfo((String)list[1]);
			}
			list = UserAPI.getCreatedContests(user.getEmail());
			if (list != null && list.length > 0) {
				createdContests = JSONParser.parseContestListInfo((String)list[1]);
			}
			return null;
		}
	}
	private class DeleteActivityTask extends AsyncTask<String, Void, String[]>
	{
		ProgressDialog mProgressDialog ;
		ArrayList<Contest> requestedContests = new ArrayList<Contest>();
		ArrayList<Contest> createdContests = new ArrayList<Contest>();
		ArrayList<Contest> joinedContests = new ArrayList<Contest>();
		@Override
		protected void onPostExecute( String[] result )
		{
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder( getParent() ) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle(  "WOLO") ;
				builder.setMessage( getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				initData();
				if (TagboxActivity.tagboxActivity != null)
					TagboxActivity.tagboxActivity.initData();
			}

			mProgressDialog.dismiss() ;
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( getParent()) ;
			mProgressDialog.setIndeterminate( true ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected String[] doInBackground( String... params )
		{
			// your network operation
			
			return UserAPI.deleteActivity(params[0], params[1]);
		}
	}
	public void showMessage(final String email,final String name) {
		Builder builder = new AlertDialog.Builder(getParent());
		builder.setTitle("");
		builder.setMessage("Are you sure to delete activity?");
		builder.setNegativeButton("Ok",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					DeleteActivityTask deleteActivityTask = new DeleteActivityTask();
					deleteActivityTask.execute(email, name);
				}
			});
		builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		Dialog dialog = builder.create();
		dialog.show();
	}
}