package com.woloactivityapp.views;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.common.DateTimePicker;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.Contest;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;

public class CreateActivity extends NavigationBaseActivity {
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	private Button							backButton_ = null;
	private Button							addFriendButton_ = null;
	private EditText 						nameEditText_ = null;
	private EditText						descriptionEditText_ = null;
	private Button							whenButton_ = null;
	private TextView						whereValTextView_ = null;
	private EditText						maxParticipantsEditText_ = null;
	private EditText						minimumAgeEditText_ = null;
	private Button							clickButton_ = null;
	private Button							requestButton_ = null;
	private RadioButton						clickRadioButton_ = null;
	private RadioButton						requestRadioButton_ = null;
	private Button							createButton_ = null;
	private Button							cancelButton_ = null;
	private RelativeLayout					rootlayout = null;
	private Date							selDate_ = new Date();
	private float						    screenScale_ = 0.0f;
	private String							whereLatitude_ = "";
	private String							whereLongitude_ = "";
	private final int						REQUEST_WHERE = 0;

	public static ArrayList<String> 		emailList = new ArrayList<String>();
	public static CreateActivity			createActivity_ = null;
	private Contest							currentContest_ = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setLayoutId(this,R.layout.activity_create);
        super.onCreate(savedInstanceState);
        createActivity_ = this;
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        setListener();
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
		
		addFriendButton_ = (Button) findViewById(R.id.addFriendButton);
		addFriendButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		addFriendButton_.setTypeface(regularTypeface_);
		
		nameEditText_ = (EditText) findViewById(R.id.nameEditText);
		nameEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		LinearLayout.LayoutParams param1 = (LinearLayout.LayoutParams) nameEditText_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		nameEditText_.setLayoutParams(param1);
		nameEditText_.setTypeface(regularTypeface_);

		descriptionEditText_ = (EditText) findViewById(R.id.descriptionEditText);
		descriptionEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) descriptionEditText_.getLayoutParams();
		param1.height = (int) (250 * screenScale_);
		descriptionEditText_.setLayoutParams(param1);
		descriptionEditText_.setTypeface(regularTypeface_);
		
		whenButton_ = (Button) findViewById(R.id.whenButton);
		whenButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) whenButton_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		whenButton_.setLayoutParams(param1);
		whenButton_.setTypeface(regularTypeface_);

		whereValTextView_ = (TextView) findViewById(R.id.whereValTextView);
		whereValTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		RelativeLayout.LayoutParams param2 = (RelativeLayout.LayoutParams) whereValTextView_.getLayoutParams();
		param2.height = (int) (80 * screenScale_);
		whereValTextView_.setLayoutParams(param2);
		whereValTextView_.setTypeface(regularTypeface_);
		
		clickButton_ = (Button) findViewById(R.id.clickButton);
		clickButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) clickButton_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		param1.width = (int) (200 * screenScale_);
		clickButton_.setLayoutParams(param1);
		clickButton_.setTypeface(regularTypeface_);
		
		requestButton_ = (Button) findViewById(R.id.requestButton);
		requestButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) requestButton_.getLayoutParams();
		param1.height = (int) (80 * screenScale_);
		param1.width = (int) (200 * screenScale_);
		requestButton_.setLayoutParams(param1);
		requestButton_.setTypeface(regularTypeface_);
		
		createButton_ = (Button) findViewById(R.id.createButton);
		createButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) createButton_.getLayoutParams();
		param1.height = (int) (70 * screenScale_);
		param1.width = (int) (200 * screenScale_);
		createButton_.setLayoutParams(param1);
		createButton_.setTypeface(regularTypeface_);
		
		cancelButton_ = (Button) findViewById(R.id.cancelButton);
		cancelButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		param1 = (LinearLayout.LayoutParams) cancelButton_.getLayoutParams();
		param1.height = (int) (70 * screenScale_);
		param1.width = (int) (200 * screenScale_);
		cancelButton_.setLayoutParams(param1);
		cancelButton_.setTypeface(regularTypeface_);
		
		clickRadioButton_ = (RadioButton) findViewById(R.id.clickRadioButton);
		requestRadioButton_ = (RadioButton) findViewById(R.id.requestRadioButton);
		rootlayout = (RelativeLayout) findViewById(R.id.rootlayout);
		
		clickRadioButton_.setChecked(true);
		requestRadioButton_.setChecked(false);
		
		maxParticipantsEditText_ = (EditText) findViewById(R.id.maxParticipantsEditText);
		maxParticipantsEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		//maxParticipantsEditText_.setText(Constants.getLongValue(CreateActivity.this, "maxparticipants") + "");
		minimumAgeEditText_  = (EditText) findViewById(R.id.minimumAgeEditText);
		minimumAgeEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
		
    }
	public void onResume() {
		super.onResume();
		
		if (rootlayout != null)
			rootlayout.postInvalidate();
	}
    public void setListener(){
    	addFriendButton_.setOnClickListener(this);
    	whereValTextView_.setOnClickListener(this);
    	backButton_.setOnClickListener(this);
    	cancelButton_.setOnClickListener(this);
    	createButton_.setOnClickListener(this);
    	whenButton_.setOnClickListener(this);
    	clickRadioButton_.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
    		@Override 
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 

	    		if (buttonView.isChecked()) { 
	    			//checked
	    			clickRadioButton_.setChecked(true);
	    			requestRadioButton_.setChecked(false);
	    		} 
    		}
   		});
    	requestRadioButton_.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
    		@Override 
    		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 

	    		if (buttonView.isChecked()) { 
	    			//checked
	    			clickRadioButton_.setChecked(false);
	    			requestRadioButton_.setChecked(true);
	    		} 
    		}
   		});
    	//whereValEditText_.addTextChangedListener(textWatcherInput);
    }
    public void initData(){
    	if ( Constants.getStringValue(CreateActivity.this, "whereAddress").length() > 0)
    		whereValTextView_.setText("search near " + Constants.getStringValue(CreateActivity.this, "whereAddress"));
    	else
    		whereValTextView_.setText("");
    	whereLatitude_ = Constants.getStringValue(CreateActivity.this, "whereLatitude");
    	whereLongitude_ = Constants.getStringValue(CreateActivity.this, "whereLongitude");
    	currentContest_ = (Contest) getIntent().getSerializableExtra("contest");
    	if (currentContest_ != null) {
    		titleCustomTextView_.setText("EDIT ACTIVITY");
        	createButton_.setText("Save");
        	nameEditText_.setText(currentContest_.getName());
        	nameEditText_.setEnabled(false);
        	descriptionEditText_.setText(currentContest_.getDescription());
        	SimpleDateFormat format = new SimpleDateFormat("MMMM d yyyy, hh:mm a");
    		whenButton_.setText(format.format(currentContest_.getWhen()));
    		selDate_ = currentContest_.getWhen();
    		whereValTextView_.setText("search near " + currentContest_.getWhere());
        	whereLatitude_ = currentContest_.getLatitude() + "";
        	whereLongitude_ = currentContest_.getLongitude() + "";
        	maxParticipantsEditText_.setText(currentContest_.getMaxParticipants() + "");
        	minimumAgeEditText_.setText(currentContest_.getMinAge() + "");
        	if (currentContest_.getJoinCategory() == 0) {
        		clickRadioButton_.setChecked(true); 
        		requestRadioButton_.setChecked(false);
        	} else {
        		clickRadioButton_.setChecked(false); 
        		requestRadioButton_.setChecked(true);
        	}
        	clickRadioButton_.setEnabled(false);
        	requestRadioButton_.setEnabled(false);
        	maxParticipantsEditText_.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if (!hasFocus && currentContest_!=null) {
						int val = Integer.parseInt(maxParticipantsEditText_.getText().toString());
						if (val < currentContest_.getMaxParticipants()) {
							maxParticipantsEditText_.setError("Please input bigger value");
							maxParticipantsEditText_.setText(currentContest_.getMaxParticipants() + "");
						}
					}
				}
			});
        	minimumAgeEditText_.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if (!hasFocus && currentContest_!= null) {
						int val = Integer.parseInt(minimumAgeEditText_.getText().toString());
						if (val > currentContest_.getMinAge()) {
							minimumAgeEditText_.setError("Please input smaller value");
							minimumAgeEditText_.setText(currentContest_.getMinAge() + "");
						}
					}
				}
			});
    	}
    		
    }
    public void onClick(View view) {
		super.onClick(view);
		int viewId = view.getId();
		switch (viewId) {
		case R.id.addFriendButton: {
			Intent intent = new Intent(CreateActivity.this, InviteFriendsActivity.class);
			startActivity(intent);
		}
		    break;
		case R.id.whereValTextView: {
			Intent intent = new Intent(CreateActivity.this, SearchActivity.class);
			if (whereValTextView_.getText().toString().length() == 0) {
				intent.putExtra("manual", false);
			} else {
				intent.putExtra("manual", true);
				intent.putExtra("where", whereValTextView_.getText().toString());
			}
			getParent().startActivityForResult(intent, REQUEST_WHERE);
		}
		    break;
		case R.id.whenButton: {
			showDateTimeDialog();
		}
		    break;
		case R.id.cancelButton:
		case R.id.backButton: {
			onBackPressed();
		}
		    break;
		case R.id.createButton: {
			if (checkFields()) {
				CreateActivtyTask createActivityTask = new CreateActivtyTask();
				createActivityTask.execute();
			}
		}
		    break;
		default:
			break;
		}
    }
    private boolean checkFields() {
		if (nameEditText_.getText().toString().length() == 0 ) {
			nameEditText_.setError(getResources().getString(R.string.error_field));
			return false;
		}
		if (descriptionEditText_.getText().toString().length() == 0 ) {
			descriptionEditText_.setError(getResources().getString(R.string.error_field));
			return false;
		}
		if (whenButton_.getText().toString().length() == 0 ) {
			whenButton_.setError(getResources().getString(R.string.error_field));
			return false;
		}
		if (whereValTextView_.getText().toString().length() == 0 ) {
			whereValTextView_.setError(getResources().getString(R.string.error_field));
			return false;
		}
		if (maxParticipantsEditText_.getText().toString().length() == 0 ) {
			maxParticipantsEditText_.setError(getResources().getString(R.string.error_field));
			return false;
		}
		if (minimumAgeEditText_.getText().toString().length() == 0 ) {
			minimumAgeEditText_.setError(getResources().getString(R.string.error_field));
			return false;
		}
		if (currentContest_ != null) {
			int val = Integer.parseInt(maxParticipantsEditText_.getText().toString());
			if (val < currentContest_.getMaxParticipants()) {
				maxParticipantsEditText_.setError("Please input bigger value");
				maxParticipantsEditText_.setText(currentContest_.getMaxParticipants() + "");
				return false;
			}
			val = Integer.parseInt(minimumAgeEditText_.getText().toString());
			if (val > currentContest_.getMinAge()) {
				minimumAgeEditText_.setError("Please input smaller value");
				minimumAgeEditText_.setText(currentContest_.getMinAge() + "");
				return false;
			}
		}
		
		return true;
	}
    private void showDateTimeDialog() {
		// Create the dialog
		final Dialog mDateTimeDialog = new Dialog(getParent());
		// Inflate the root layout
		final RelativeLayout mDateTimeDialogView = (RelativeLayout) getLayoutInflater().inflate(R.layout.date_time_dialog, null);
		// Grab widget instance
		final DateTimePicker mDateTimePicker = (DateTimePicker) mDateTimeDialogView.findViewById(R.id.DateTimePicker);
		// Check is system is set to use 24h time (this doesn't seem to work as expected though)
		final String timeS = android.provider.Settings.System.getString(getContentResolver(), android.provider.Settings.System.TIME_12_24);
		final boolean is24h = !(timeS == null || timeS.equals("12"));
		
		// Update demo TextViews when the "OK" button is clicked 
		((Button) mDateTimeDialogView.findViewById(R.id.SetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mDateTimePicker.clearFocus();

				selDate_ = new Date(mDateTimePicker.get(Calendar.YEAR) - 1900, mDateTimePicker.get(Calendar.MONTH), mDateTimePicker.get(Calendar.DAY_OF_MONTH),mDateTimePicker.get(Calendar.HOUR_OF_DAY),mDateTimePicker.get(Calendar.MINUTE));
				SimpleDateFormat format = new SimpleDateFormat("MMMM d yyyy, hh:mm a");
				whenButton_.setText(format.format(selDate_));
				mDateTimeDialog.dismiss();
			}
		});

		// Cancel the dialog when the "Cancel" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.CancelDialog)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDateTimeDialog.cancel();
			}
		});

		// Reset Date and Time pickers when the "Reset" button is clicked
		((Button) mDateTimeDialogView.findViewById(R.id.ResetDateTime)).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDateTimePicker.reset();
			}
		});
		
		// Setup TimePicker
		mDateTimePicker.setIs24HourView(is24h);
		// No title on the dialog window
		mDateTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set the dialog content view
		mDateTimeDialog.setContentView(mDateTimeDialogView);
		// Display the dialog
		mDateTimeDialog.show();
	}
    private class CreateActivtyTask extends AsyncTask<Void, Void, String[]>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( String[] result )
		{
			mProgressDialog.cancel();
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder( getParent() ) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle(  "WOLO" ) ;
				builder.setMessage( getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( result[ 0 ].equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( getParent() ) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle(  "WOLO" ) ;
					builder.setMessage( result[ 1 ] ) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( result[ 0 ].equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					onBackPressed();
					if (JoinedActivity.joinedActivity != null)
						JoinedActivity.joinedActivity.onBackPressed();
					if (TagboxActivity.tagboxActivity != null)
						TagboxActivity.tagboxActivity.initData();
					Intent intent = new Intent(CreateActivity.this, MyactivitiesActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					CreateActivity.this.goNextHistory("MyactivitiesActivity", intent);
					
				}
			}
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( getParent()) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected String[] doInBackground( Void... params )
		{
			Date currentDate = new Date();
			long duration = selDate_.getTime() - currentDate.getTime();
			User user = Constants.getCurrentUser(getParent());
			String emails = "";
			for (int i=0;i<emailList.size();i++) {
				if (i==0)
					emails = emailList.get(0);
				else
					emails += "," + emailList.get(0);
			}
			
			if (whereLatitude_.length() == 0 || whereLongitude_.length() == 0) {
				whereLatitude_ = user.getLatitude() + "";
				whereLongitude_ = user.getLongitude() + "";
				List<Address> foundGeocode = null;
				try {
					/* find the addresses  by using getFromLocationName() method with the given address*/
					foundGeocode = new Geocoder(CreateActivity.this).getFromLocationName(whereValTextView_.getText().toString(), 1);
					whereLatitude_ = foundGeocode.get(0).getLatitude() + ""; //getting latitude
					whereLongitude_ = foundGeocode.get(0).getLongitude() + "";//getting longitude
				} catch (Exception e) {}
				
			}
			int maxNum = 0;
			try{
				maxNum = Integer.parseInt(maxParticipantsEditText_.getText().toString());
			} catch (Exception e){}
			if (maxParticipantsEditText_.getText().toString().equals("0")) {
				maxNum = (int) (Constants.getLongValue(CreateActivity.this, "maxparticipants"));
			}
			if (currentContest_ != null) {
				
				return UserAPI.editContest(	 nameEditText_.getText().toString(),
					     descriptionEditText_.getText().toString(), 
					     duration + "",
					     whereValTextView_.getText().toString(), 
					     maxNum,
					     Integer.parseInt(minimumAgeEditText_.getText().toString()),
					     user.getEmail(),
					     clickRadioButton_.isChecked()?0:1,
					     whereLatitude_,
					     whereLongitude_,
					     emails) ;
			}
			return UserAPI.createContest(nameEditText_.getText().toString(),
									     descriptionEditText_.getText().toString(), 
									     duration + "",
									     whereValTextView_.getText().toString(), 
									     maxNum,
									     Integer.parseInt(minimumAgeEditText_.getText().toString()),
									     user.getEmail(),
									     clickRadioButton_.isChecked()?0:1,
									     whereLatitude_,
									     whereLongitude_,
									     emails) ;
		}
	}
	public void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		// TODO Auto-generated method stub
		switch( requestCode )
		{
		case REQUEST_WHERE: {
			if (data == null)
				return;
			whereValTextView_.setText(data.getStringExtra("name"));
			whereLatitude_ = data.getStringExtra("lat");
			whereLongitude_ = data.getStringExtra("lng");
		}
		    break;
		default :
		{
			super.onActivityResult( requestCode, resultCode, data ) ;
			break ;
		}
		}
	}
//	 TextWatcher textWatcherInput = new TextWatcher() {
//	        
//         @Override
//         public void onTextChanged(CharSequence s, int start, int before, int count) {
//           
//         }
//	     @Override
//	     public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//	          
//	     }
//	     @Override
//	     public void afterTextChanged(Editable s) {
//	    	 // TODO Auto-generated method stub
//	    	
//	    	 String someName = whereValEditText_.getText().toString();
//	    	 if (someName.length() > 0) {
//	    		 whereLatitude_ = ""; 
//	    		 whereLongitude_ = "";
//	    	 } 
//	    	
//         }
//     };
}