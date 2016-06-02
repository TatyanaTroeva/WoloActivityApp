package com.woloactivityapp.views;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woloactivityapp.adapters.TagboxAdapter;
import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.Contest;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;
import com.woloactivityapp.utils.JSONParser;

public class TagboxActivity extends NavigationBaseActivity {
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	private ImageView						graybarImageView_ = null;
	private EditText						searchEditText_ = null;
	private ImageView						searchImageView_ = null;
	private Button							menuButton_ = null;
	private Button							plusButton_ = null;
	private float						    screenScale_ = 0.0f;
	private ListView						activityListView_ = null;
	public static TagboxActivity 			tagboxActivity = null;
	private Button							settingButton_ = null;
	private ArrayList<Contest>				contestList_ = new ArrayList<Contest>();
	private Dialog  						dialog = null; 
	private final int						REQUEST_WHERE = 1;
	private String 							whereLatitude = "";
	private String 							whereLongitude = "";
	private String							whereAddress = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setLayoutId(this,R.layout.activity_tagbox);
        super.onCreate(savedInstanceState);
        tagboxActivity = this;
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
		
		plusButton_ = (Button) findViewById(R.id.plusButton);
    	param = (RelativeLayout.LayoutParams) plusButton_.getLayoutParams();
		param.setMargins((int) (20 * screenScale_), (int) (20 * screenScale_), 0, 0);
		param.width = (int) (60 * screenScale_); 
		param.height = (int) (60 * screenScale_); 
		plusButton_.setLayoutParams(param);
		
		menuButton_ = (Button) findViewById(R.id.menuButton);
    	param = (RelativeLayout.LayoutParams) menuButton_.getLayoutParams();
		param.setMargins(0, (int) (20 * screenScale_), (int) (20 * screenScale_), 0);
		param.width = (int) (60 * screenScale_); 
		param.height = (int) (60 * screenScale_); 
		menuButton_.setLayoutParams(param);
		
		settingButton_ = (Button) findViewById(R.id.settingButton);
    	param = (RelativeLayout.LayoutParams) settingButton_.getLayoutParams();
		param.setMargins(0, (int) (10 * screenScale_), (int) (20 * screenScale_), 0);
		param.width = (int) (80 * screenScale_); 
		param.height = (int) (80 * screenScale_); 
		settingButton_.setLayoutParams(param);
		
    	graybarImageView_ = (ImageView) findViewById(R.id.graybarImageView);
    	param = (RelativeLayout.LayoutParams) graybarImageView_.getLayoutParams();
		param.setMargins(0, (int) (100 * screenScale_), 0, 0);
		param.width = getWidth();
		param.height = (int) (100 * screenScale_); 
		graybarImageView_.setLayoutParams(param);
		
    	searchEditText_ = (EditText) findViewById(R.id.searchEditText);
    	param = (RelativeLayout.LayoutParams) searchEditText_.getLayoutParams();
		param.setMargins((int) (20 * screenScale_), (int) (110 * screenScale_), (int) (20 * screenScale_), 0);
		param.width = getWidth();
		param.height = (int) (80 * screenScale_); 
		searchEditText_.setLayoutParams(param);
		searchEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		searchEditText_.setTypeface(regularTypeface_);
		
    	searchImageView_ = (ImageView) findViewById(R.id.searchImageView);
      	param = (RelativeLayout.LayoutParams) searchImageView_.getLayoutParams();
    	param.setMargins(0, (int) (125 * screenScale_), (int) (30 * screenScale_), 0);
    	param.width = (int) (50 * screenScale_); 
    	param.height = (int) (50 * screenScale_); 
    	searchImageView_.setLayoutParams(param);
    	
    	activityListView_ = (ListView) findViewById(R.id.activityListView);
    }
    public void setListener(){
    	menuButton_.setOnClickListener(this);
    	settingButton_.setOnClickListener(this);
    	plusButton_.setOnClickListener(this);
    	searchEditText_.setOnTouchListener(touchListener);
        searchEditText_.addTextChangedListener(textWatcherInput);
    }
    public void initData(){
       	GetContestsTask getContestsTask = new GetContestsTask();
    	getContestsTask.execute();
    	
    }
    public void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		// TODO Auto-generated method stub
		switch( requestCode )
		{
			case REQUEST_WHERE: {
				if (data == null)
					return;
				if (dialog != null) {
					TextView pinTextView = (TextView) dialog.findViewById(R.id.pinTextView);
					pinTextView.setText(data.getStringExtra("name"));
				}
				whereAddress = data.getStringExtra("name");
				whereLatitude = data.getStringExtra("lat");
				whereLongitude = data.getStringExtra("lng");
			}
			    break;
			default :
			{
				super.onActivityResult( requestCode, resultCode, data ) ;
				break ;
			}
		}
	}
    public void onClick(View view) {
		super.onClick(view);
		int viewId = view.getId();
		switch (viewId) {
		case R.id.settingButton: {
			launchSelCustomDialog();
		}
		    break;
		case R.id.menuButton: {
			Intent intent = new Intent(TagboxActivity.this, MyactivitiesActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			TagboxActivity.this.goNextHistory("MyactivitiesActivity", intent);
		}
		    break;
		case R.id.plusButton: {
			Intent intent = new Intent(TagboxActivity.this, CreateActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			TagboxActivity.this.goNextHistory("CreateActivity", intent);
		}
		    break;
		default:
			break;
		}
    }
    private class GetContestsTask extends AsyncTask<Void, Void, Object[]>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( Object[] result )
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
				contestList_.clear();
				User user = Constants.getCurrentUser(getParent());
				ArrayList<Contest> list = JSONParser.parseContestListInfo((String)result[1]);
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						if (!isContained(list.get(i).getJoinedUsers(), user.getEmail()) && 
							!isContained(list.get(i).getRequestedUsers(), user.getEmail()) &&
							list.get(i).getMinAge() <= user.getAge())
							contestList_.add(list.get(i));
					}
				}
				if (contestList_.size() > 0) {
					TagboxAdapter tagboxAdapter = new TagboxAdapter(TagboxActivity.this, contestList_);
			    	activityListView_.setAdapter(tagboxAdapter);
			    	tagboxAdapter.notifyDataSetChanged();
				} else {
					TagboxAdapter tagboxAdapter = new TagboxAdapter(TagboxActivity.this, new ArrayList<Contest>());
			    	activityListView_.setAdapter(tagboxAdapter);
			    	tagboxAdapter.notifyDataSetChanged();
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
		protected Object[] doInBackground( Void... params )
		{
			// your network operation
			User user = Constants.getCurrentUser(getParent());
			return UserAPI.getContests(user.getEmail(), Constants.getFloatValue(TagboxActivity.this, "radius"));
		}
	}

    public boolean isContained(String list, String id) {
    	if (list == null || list.length() == 0)
    		return false;
    	if (!list.contains(",") && !list.equals(id))
    		return false;
    	else if (!list.contains(",")  && list.equals(id))
    		return true;
    	String[] vallist = list.split(",");
    	for (int i=0;i<vallist.length; i++) {
    		if (vallist[i].equals(id))
    			return true;
    	}
    	return false;
    	
    }
    OnTouchListener touchListener = new OnTouchListener() {
    	  
        @Override
        public boolean onTouch(View v, MotionEvent event) {
        	// TODO Auto-generated method stub
        	int eventAction = event.getAction();
        	switch (eventAction) {
        	case MotionEvent.ACTION_DOWN:
        		int viewId = v.getId();
        		if (viewId == R.id.searchEditText) {
        			searchEditText_.setText(""); 
        		}
        		break;
        	case MotionEvent.ACTION_UP:
        		break;
         }
         return false;
        }
    };
    TextWatcher textWatcherInput = new TextWatcher() {
        
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
           
         }
	     @Override
	     public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	          
	     }
	     @Override
	     public void afterTextChanged(Editable s) {
	    	 // TODO Auto-generated method stub
	    	
	    	 String someName = searchEditText_.getText().toString();
	    	 if (someName.length() > 0) {
	    		 plusButton_.setVisibility(View.VISIBLE);
	    	 } else {
	    		 plusButton_.setVisibility(View.GONE);
	    	 }
	    	 ArrayList<Contest> filteredEventList = new ArrayList<Contest>(); 
	    	 for (int i=0; i < contestList_.size(); i++) {
        	 
	    		 String contestName = contestList_.get(i).getName().toLowerCase();
	    		 String contestDescription = contestList_.get(i).getDescription().toLowerCase();
	    		 if ((contestName != null && contestName.contains(someName.toLowerCase())) || (contestDescription !=null && contestDescription.contains(someName.toLowerCase())) ) {
	    			 filteredEventList.add(contestList_.get(i));
	    		 }
	    	 }
	    	 TagboxAdapter tagboxAdapter = new TagboxAdapter(TagboxActivity.this, filteredEventList);
	    	 activityListView_.setAdapter(tagboxAdapter);
	    	 tagboxAdapter.notifyDataSetChanged();
         }
     };
     public void launchSelCustomDialog(){
    	 dialog = new Dialog(getParent());
    	 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 

    	 dialog.setContentView(R.layout.setting_dialog);
    	 dialog.show();

    	  
     	 final EditText radiusEditText = (EditText) dialog.findViewById(R.id.radiusEditText);
     	 final EditText maxEditText = (EditText) dialog.findViewById(R.id.maxEditText);
     	 final EditText clockEditText = (EditText) dialog.findViewById(R.id.clockEditText);
     	 
     	 if (Constants.getFloatValue(TagboxActivity.this, "radius") == 0)
     		 Constants.setFloatValue(TagboxActivity.this, "radius", 5.0f);
     	 if (Constants.getLongValue(TagboxActivity.this, "maxparticipants") == 0)
     		 Constants.setLongValue(TagboxActivity.this, "maxparticipants", 10);
     	 if (Constants.getLongValue(TagboxActivity.this, "timepost") == 0)
     		 Constants.setLongValue(TagboxActivity.this, "timepost", 48);
     	 radiusEditText.setText(Constants.getFloatValue(this, "radius") + "");
     	 maxEditText.setText(Constants.getLongValue(this, "maxparticipants") + "") ;
     	 clockEditText.setText(Constants.getLongValue(this, "timepost") + "");
     	
    	 radiusEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(11)});
    	 maxEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
    	 clockEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12)});
    	 
    	 Button cancelbtn = (Button)dialog.findViewById(R.id.cancelButton);
    	 if(cancelbtn != null)
    	 {
    		 cancelbtn.setOnClickListener(new OnClickListener() {
    			 @Override
    			 public void onClick(View v) {
    	     
    				 dialog.hide();
    			 }
    	     });
    	 }
    	 Button savebtn = (Button)dialog.findViewById(R.id.saveButton);
    	 if(savebtn != null)
    	 {
    		 savebtn.setOnClickListener(new OnClickListener() {
    			 @Override
    			 public void onClick(View v) {
    				 Constants.setFloatValue(TagboxActivity.this, "radius", radiusEditText.getText().toString().length() == 0?0.5f:Float.parseFloat(radiusEditText.getText().toString()));
    				 Constants.setLongValue(TagboxActivity.this, "maxparticipants",maxEditText.getText().toString().length()==0?10:Long.parseLong(maxEditText.getText().toString()));
    				 Constants.setLongValue(TagboxActivity.this, "timepost", clockEditText.getText().toString().length()==0?12:Long.parseLong(clockEditText.getText().toString()));
    		    	 Constants.setStringValue(TagboxActivity.this, "whereAddress", whereAddress);
    		    	 Constants.setStringValue(TagboxActivity.this, "whereLatitude", whereLatitude);
    		    	 Constants.setStringValue(TagboxActivity.this, "whereLongitude", whereLongitude);
    		    	 dialog.hide();
    		    	 initData();
    			 }
    		 });
    	 }
    	 
    	 final TextView pinTextView = (TextView) dialog.findViewById(R.id.pinTextView);
    	 pinTextView.setText(Constants.getStringValue(TagboxActivity.this, "whereAddress"));
    	 pinTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				launchOptionCustomDialog(pinTextView.getText().toString());
				
			}
    	 });

    }
     public void launchOptionCustomDialog(final String address){
    	 final Dialog optionDialog = new Dialog(getParent());
    	 optionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 

    	 optionDialog.setContentView(R.layout.option_dialog);
    	 optionDialog.show();
     	 Button gpsbtn = (Button)optionDialog.findViewById(R.id.gpsButton);
    	 if(gpsbtn != null)
    	 {
    		 gpsbtn.setOnClickListener(new OnClickListener() {
    			 @Override
    			 public void onClick(View v) {
    				 Constants.setStringValue(TagboxActivity.this, "whereAddress", "");
    				 if (dialog != null) {
    						TextView pinTextView = (TextView) dialog.findViewById(R.id.pinTextView);
    						pinTextView.setText("");
    					}
    				 optionDialog.hide();
    			 }
    	     });
    	 }
    	 Button pinbtn = (Button)optionDialog.findViewById(R.id.pinButton);
    	 if(pinbtn != null)
    	 {
    		 pinbtn.setOnClickListener(new OnClickListener() {
    			 @Override
    			 public void onClick(View v) {
    				 Intent intent = new Intent(TagboxActivity.this, SearchActivity.class);
    					if (address.length() == 0) {
    						intent.putExtra("pin", true);
    						intent.putExtra("manual", false);
    					} else {
    						intent.putExtra("manual", true);
    						intent.putExtra("where", address);
    					}
    					getParent().startActivityForResult(intent, REQUEST_WHERE);
    				 optionDialog.hide();
    			 }
    		 });
    	 }
    }
}