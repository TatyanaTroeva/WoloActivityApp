package com.woloactivityapp.views;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.Contest;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.BottomActivity;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;
import com.woloactivityapp.utils.JSONParser;

public class JoinedActivity extends NavigationBaseActivity {
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	private Button							backButton_ = null;
	private Button							groupChatButton_ = null;
	private CustomTextView					nameCustomTextView_ = null;
	private CustomTextView					descriptionCustomTextView_ = null;
	private CustomTextView					whenCustomTextView_ = null;
	private CustomTextView					whereCustomTextView_ = null;
	private CustomTextView 					nameValCustomTextView_ = null;
	private CustomTextView					descriptionValCustomTextView_ = null;
	private CustomTextView					whenValCustomTextView_ = null;
	private CustomTextView					whereValCustomTextView_ = null;
	private CustomTextView					maxCustomTextView_ = null;
	private CustomTextView					maxValCustomTextView_ = null;
	private CustomTextView					minCustomTextView_ = null;
	private CustomTextView					minValCustomTextView_ = null;
	private CustomTextView					participantsCustomTextView_ = null;
	private LinearLayout					joinedLinearLayout_ = null;
	private LinearLayout					requestedLinearLayout_ = null;
	private RelativeLayout					rootlayout = null;
	private float						    screenScale_ = 0.0f;
	public static Contest					currentContest_ = null;
	private int								index = 0;
	private Button							editButton_ = null;
	private Button							leaveButton_ = null;
	private Typeface						regularTypeface_ = null;
	public static JoinedActivity			joinedActivity = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setLayoutId(this,R.layout.activity_joinedactivity);
        super.onCreate(savedInstanceState);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        setListener();
        joinedActivity = this;
    	currentContest_ = (Contest)getIntent().getSerializableExtra("contest");
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
    	regularTypeface_ = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
    	
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

		editButton_ = (Button) findViewById(R.id.editButton);
		param = (RelativeLayout.LayoutParams) editButton_.getLayoutParams();
		param.setMargins(0, 0, 0, 0);
		param.width = (int) (100 * screenScale_); 
		param.height = (int) (100 * screenScale_); 
		editButton_.setLayoutParams(param);
		
		groupChatButton_ = (Button) findViewById(R.id.groupChatButton);
		groupChatButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		groupChatButton_.setTypeface(regularTypeface_);
		
		leaveButton_ = (Button) findViewById(R.id.leaveButton);
		leaveButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		leaveButton_.setTypeface(regularTypeface_);
		
		nameCustomTextView_ = (CustomTextView) findViewById(R.id.nameCustomTextView);
		nameCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		nameValCustomTextView_ = (CustomTextView) findViewById(R.id.nameValCustomTextView);
		nameValCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		LinearLayout.LayoutParams param1 = (LinearLayout.LayoutParams) nameValCustomTextView_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		nameValCustomTextView_.setLayoutParams(param1);
		nameValCustomTextView_.setTypeface(regularTypeface_);
		
		descriptionCustomTextView_ = (CustomTextView) findViewById(R.id.descriptionCustomTextView);
		descriptionCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		descriptionValCustomTextView_ = (CustomTextView) findViewById(R.id.descriptionValCustomTextView);
		descriptionValCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) descriptionValCustomTextView_.getLayoutParams();
		param1.height = (int) (250 * screenScale_);
		descriptionValCustomTextView_.setLayoutParams(param1);
		descriptionValCustomTextView_.setTypeface(regularTypeface_);
		
		whenCustomTextView_ = (CustomTextView) findViewById(R.id.whenCustomTextView);
		whenCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		whenValCustomTextView_ = (CustomTextView) findViewById(R.id.whenValCustomTextView);
		whenValCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) whenValCustomTextView_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		whenValCustomTextView_.setLayoutParams(param1);
		whenValCustomTextView_.setTypeface(regularTypeface_);
		
		whereCustomTextView_ = (CustomTextView) findViewById(R.id.whereCustomTextView);
		whereCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		whereValCustomTextView_ = (CustomTextView) findViewById(R.id.whereValCustomTextView);
		whereValCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) whereValCustomTextView_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		whereValCustomTextView_.setLayoutParams(param1);
		whereValCustomTextView_.setTypeface(regularTypeface_);
		
		maxCustomTextView_ = (CustomTextView) findViewById(R.id.maxCustomTextView);
		maxCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		maxValCustomTextView_ = (CustomTextView) findViewById(R.id.maxValCustomTextView);
		maxValCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) maxValCustomTextView_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		maxValCustomTextView_.setLayoutParams(param1);
		maxValCustomTextView_.setTypeface(regularTypeface_);
		
		minCustomTextView_ = (CustomTextView) findViewById(R.id.minCustomTextView);
		minCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		minValCustomTextView_ = (CustomTextView) findViewById(R.id.minValCustomTextView);
		minValCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) minValCustomTextView_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		minValCustomTextView_.setLayoutParams(param1);
		minValCustomTextView_.setTypeface(regularTypeface_);
		
		participantsCustomTextView_ = (CustomTextView) findViewById(R.id.participantsCustomTextView);
		participantsCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
				
		joinedLinearLayout_ = (LinearLayout) findViewById(R.id.joinedLinearLayout);
		requestedLinearLayout_ = (LinearLayout) findViewById(R.id.requestedLinearLayout);
		rootlayout = (RelativeLayout) findViewById(R.id.rootlayout);
    }
	public void onResume() {
		super.onResume();
        initData();
		if (rootlayout != null)
			rootlayout.postInvalidate();
		
	}
    public void setListener(){
    	editButton_.setOnClickListener(this);
    	backButton_.setOnClickListener(this);
    	leaveButton_.setOnClickListener(this);
    	groupChatButton_.setOnClickListener(this);
    }
    public void initData(){
    	index = getIntent().getIntExtra("index", 0);
    	if (index == 0) {
    		titleCustomTextView_.setText(getResources().getString(R.string.joined));
    		leaveButton_.setVisibility(View.VISIBLE);
    		editButton_.setVisibility(View.GONE);
    	} else if (index == 1){
    		titleCustomTextView_.setText(getResources().getString(R.string.created));
    		leaveButton_.setVisibility(View.GONE);
    		editButton_.setVisibility(View.VISIBLE);
    	} else {
    		titleCustomTextView_.setText(getResources().getString(R.string.requested));
    		editButton_.setVisibility(View.GONE);
    		leaveButton_.setVisibility(View.VISIBLE);
    	}

    	nameValCustomTextView_.setText(currentContest_.getName());
    	descriptionValCustomTextView_.setText(currentContest_.getDescription());
    	SimpleDateFormat format = new SimpleDateFormat("MMMM d yyyy, hh:mm a");
    	whenValCustomTextView_.setText(format.format(currentContest_.getWhen()));
    	whereValCustomTextView_.setText(currentContest_.getWhere());
    	maxValCustomTextView_.setText(currentContest_.getMaxParticipants() + "");
    	minValCustomTextView_.setText(currentContest_.getMinAge() + "");
    	
    	GetJoinedUsersTask getJoinedUsersTask = new GetJoinedUsersTask();
    	getJoinedUsersTask.execute(currentContest_.getJoinedUsers());
    
    }
    public void onClick(View view) {
		super.onClick(view);
		int viewId = view.getId();
		switch (viewId) {
		case R.id.leaveButton: {
			LeaveTask leaveTask = new LeaveTask();
			leaveTask.execute(Constants.getCurrentUser(JoinedActivity.this).getEmail(), currentContest_.getName());
		}
		    break;
		case R.id.editButton: {
			Intent intent = new Intent(JoinedActivity.this, CreateActivity.class);
			intent.putExtra("contest", currentContest_);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			JoinedActivity.this.goNextHistory("CreateActivity", intent);
		}
		    break;
		case R.id.backButton: {
			onBackPressed();
		}
		    break;
		case R.id.groupChatButton: {
			MessagesActivity.currentContest_ = currentContest_;
			BottomActivity.gInstance.setCurrentTab(2);
			
		} 
		    break;
		default:
			break;
		}
    }
    public void addJoinedUsersFunc(ArrayList<User> userslist) {
    	participantsCustomTextView_.setText(String.format("Participants(%d):", userslist.size()));
    	LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
    	joinedLinearLayout_.removeAllViews();
    	if (userslist != null && userslist.size() > 0) {
    		for (int i=0;i<userslist.size(); i++) {
    			final User currentUser = userslist.get(i);
       	    	View itemView = layoutInflater.inflate(R.layout.messageitemlayout, null);
       	    	joinedLinearLayout_.addView(itemView);
       	    	CustomTextView itemNameCustomTextView = (CustomTextView) itemView.findViewById(R.id.itemNameCustomTextView);
       	    	CustomTextView itemAddressCustomTextView = (CustomTextView) itemView.findViewById(R.id.itemAddressCustomTextView);
       	    	itemNameCustomTextView.setText(currentUser.getFirstname() + " " + currentUser.getLastname());
       	    	itemAddressCustomTextView.setText(currentUser.getAboutme());
       	    	RelativeLayout avatarRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.avatarRelativeLayout);
       	    	GetPhotoTask photoTask = new GetPhotoTask(avatarRelativeLayout);
       	    	photoTask.execute(currentUser.getEmail());
       	    	itemView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(JoinedActivity.this, UserProfileActivity.class);
						intent.putExtra("user", currentUser);
    					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					JoinedActivity.this.goNextHistory("MyactivitiesActivity", intent);
					}
				});
    		}

    	}

    }
    public void addRequestedUsersFunc(ArrayList<User> userslist) {
    	LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
    	requestedLinearLayout_.removeAllViews();
    	if (userslist != null && userslist.size() > 0) {
    		for (int i=0;i<userslist.size(); i++) {
    			final User currentUser = userslist.get(i);
       	    	View itemView = layoutInflater.inflate(R.layout.requestmessageitemlayout, null);
       	    	requestedLinearLayout_.addView(itemView);
       	    	CustomTextView itemNameCustomTextView = (CustomTextView) itemView.findViewById(R.id.itemNameCustomTextView);
       	    	CustomTextView itemAddressCustomTextView = (CustomTextView) itemView.findViewById(R.id.itemAddressCustomTextView);
       	    	Button itemAcceptButton = (Button) itemView.findViewById(R.id.itemAcceptButton);
       	    	Button itemRejectButton = (Button) itemView.findViewById(R.id.itemRejectButton);
       	    	itemAcceptButton.setTypeface(regularTypeface_);
       	    	itemRejectButton.setTypeface(regularTypeface_);
       	    	
       	    	itemNameCustomTextView.setText(currentUser.getFirstname() + " " + currentUser.getLastname());
       	    	itemAddressCustomTextView.setText(currentUser.getAboutme());
       	    	RelativeLayout avatarRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.avatarRelativeLayout);
       	    	GetPhotoTask photoTask = new GetPhotoTask(avatarRelativeLayout);
       	    	photoTask.execute(currentUser.getEmail());
       	    	itemView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(JoinedActivity.this, UserProfileActivity.class);
						intent.putExtra("user", currentUser);
    					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    					JoinedActivity.this.goNextHistory("MyactivitiesActivity", intent);
					}
				});
       	    	itemAcceptButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AcceptTask acceptTask = new AcceptTask();
						acceptTask.execute(currentUser.getEmail(), currentContest_.getName());
					}
				});
       	    	itemRejectButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						RejectTask rejectTask = new RejectTask();
						rejectTask.execute(currentUser.getEmail(), currentContest_.getName());
					}
				});
    		}

    	}

    }
    private class GetPhotoTask extends AsyncTask<String, Void, byte[]>
	{
		View view;
		ProgressBar avatarProgressBar;
		ImageView	avatarImageView;
		public GetPhotoTask(View view){
			this.view = view;
			avatarProgressBar = (ProgressBar) view.findViewById(R.id.avatarProgressBar);
			avatarImageView = (ImageView) view.findViewById(R.id.avatarImageView);
		}
		@Override
		protected void onPostExecute( byte[] result )
		{
			avatarProgressBar.setVisibility(View.GONE);
			if (result == null)
				return;

			Bitmap bitmap = BitmapFactory.decodeByteArray( result, 0, result.length ) ;
			recycleImageView(avatarImageView);
			avatarImageView.setImageBitmap( bitmap ) ;
		}
		@Override
   		protected void onPreExecute()
   		{
   			avatarProgressBar.setVisibility(View.VISIBLE);
   		}

		@Override
		protected byte[] doInBackground( String... params )
		{
			// your network operation
			return UserAPI.getUserPhoto( params[ 0 ] ) ;
		}
	}
	public void recycleImageView(ImageView imageView) {

		try {
			Drawable drawable = imageView.getDrawable();
			if (drawable != null && drawable instanceof BitmapDrawable) {
				BitmapDrawable bd = (BitmapDrawable) drawable;
				Bitmap bitmap = bd.getBitmap();
				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
				}
			}

		} catch (Exception e) {
		}
		imageView.setImageDrawable(null);

	}
    private class GetJoinedUsersTask extends AsyncTask<String, Void, Object[]>
   	{
   		ProgressDialog mProgressDialog ;
   		ArrayList<User> userslist = new ArrayList<User>();
   		@Override
   		protected void onPostExecute( Object[] result )
   		{
   			mProgressDialog.dismiss() ;
   			addJoinedUsersFunc(userslist);
   			if (index == 1) {
   	    		GetRequestedUsersTask getRequestedUsersTask = new GetRequestedUsersTask();
   	    		getRequestedUsersTask.execute(currentContest_.getRequestedUsers());
   	    	}
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
   		protected Object[] doInBackground( String... params )
   		{
   			// your network operation
   			Object[] list = UserAPI.getUsers(params[0]);
   			if (list != null && list.length > 0) {
   				userslist = JSONParser.parseUserListInfo((String)list[1]);
   			}
   			return null;
   		}
   	}
   	private class GetRequestedUsersTask extends AsyncTask<String, Void, Object[]>
   	{
   		ProgressDialog mProgressDialog ;
   		ArrayList<User> userslist = new ArrayList<User>();
   		@Override
   		protected void onPostExecute( Object[] result )
   		{
   			mProgressDialog.dismiss() ;
   			addRequestedUsersFunc(userslist);
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
   		protected Object[] doInBackground( String... params )
   		{
   			// your network operation
   			Object[] list = UserAPI.getUsers(params[0]);
   			if (list != null && list.length > 0) {
   				userslist = JSONParser.parseUserListInfo((String)list[1]);
   			}
   			return null;
   		}
   	}
   	private class AcceptTask extends AsyncTask<String, Void, Object[]>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( Object[] result )
		{
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(getParent()) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle(  "WOLO" ) ;
				builder.setMessage( getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( getParent()) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle(  "WOLO" ) ;
					builder.setMessage((String)result[ 1 ]) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					currentContest_ = JSONParser.parseContestInfo((String)result[ 1 ]);
					initData();	
				}
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
		protected Object[] doInBackground( String... params )
		{
			// your network operation
			return UserAPI.acceptToJoin(params[0], params[1]);
		}
	}
	private class LeaveTask extends AsyncTask<String, Void, Object[]>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( Object[] result )
		{
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(getParent()) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle(  "WOLO" ) ;
				builder.setMessage( getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( getParent()) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle(  "WOLO" ) ;
					builder.setMessage((String)result[ 1 ]) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					onBackPressed();
					MyactivitiesActivity.myactivities.initData();
					TagboxActivity.tagboxActivity.initData();
					
				}
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
		protected Object[] doInBackground( String... params )
		{
			// your network operation
			return UserAPI.leaveActivity(params[0], params[1]);
		}
	}
  	private class RejectTask extends AsyncTask<String, Void, Object[]>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( Object[] result )
		{
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(getParent()) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle(  "WOLO" ) ;
				builder.setMessage( getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( getParent()) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle( "WOLO") ;
					builder.setMessage((String)result[ 1 ]) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					currentContest_ = JSONParser.parseContestInfo((String)result[ 1 ]);
					initData();
				}
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
		protected Object[] doInBackground( String... params )
		{
			// your network operation
			return UserAPI.rejectToJoin(params[0], params[1]);
		}
	}
}