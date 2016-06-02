package com.woloactivityapp.views;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woloactivityapp.adapters.SearchLocationAdapter;
import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.GooglePlaceInfo;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.httpclient.HttpConnectionUtil;
import com.woloactivityapp.main.R;
import com.woloactivityapp.utils.XmlPareser;

public class SearchActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	private ImageView						graybarImageView_ = null;
	private EditText						searchEditText_ = null;
	private ImageView						searchImageView_ = null;
	private float						    screenScale_ = 0.0f;
	private ListView						searchListView_ = null;
	private TextView						hintTextView_ = null;
	public static SearchActivity 			searchActivity = null;
	public ArrayList<GooglePlaceInfo>		placeList_ = new ArrayList<GooglePlaceInfo>();
	private boolean							manualFlag_ = false;
	private String 							whereAddress = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        searchActivity = this;
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        setListener();
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
		
		hintTextView_ = (TextView) findViewById(R.id.hintTextView);
		 
    	searchImageView_ = (ImageView) findViewById(R.id.searchImageView);
      	param = (RelativeLayout.LayoutParams) searchImageView_.getLayoutParams();
    	param.setMargins(0, (int) (125 * screenScale_), (int) (30 * screenScale_), 0);
    	param.width = (int) (50 * screenScale_); 
    	param.height = (int) (50 * screenScale_); 
    	searchImageView_.setLayoutParams(param);
    	
    	searchListView_ = (ListView) findViewById(R.id.searchListView);
    }
    public void setListener(){
    	searchImageView_.setOnClickListener(this);
        searchEditText_.addTextChangedListener(textWatcherInput);
    	searchEditText_.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    	    @Override
    	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    	        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
    	            performSearch();
    	            return true;
    	        }
    	        return false;
    	    }
    	});
    }
    public void initData(){
    	manualFlag_ = getIntent().getBooleanExtra("manual", false);
    	boolean flag = getIntent().getBooleanExtra("pin", false);
    	if (manualFlag_ == false) {
    		searchEditText_.setHint("Search nearby gps address");
    		if (flag)
    			searchEditText_.setHint("pin my location");
    	}else {
    		searchEditText_.setHint("Search nearby manual address");
    		whereAddress = getIntent().getStringExtra("where");
    	}
    		
    }
    public void performSearch() {
    	GetPlacesTask task = new GetPlacesTask();
    	task.execute();
    }
    public void onClick(View view) {
    	int viewId = view.getId();
		switch (viewId) {
		case R.id.searchImageView: {
			performSearch();
		}
		    break;
		default:
			break;
		}
    }
    private class GetPlacesTask extends AsyncTask<Void, Void, String>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( String result )
		{
			mProgressDialog.dismiss() ;
			SearchLocationAdapter searchLocationAdapter = new SearchLocationAdapter(SearchActivity.this, new ArrayList<GooglePlaceInfo>());
			searchListView_.setAdapter(searchLocationAdapter);
			searchLocationAdapter.notifyDataSetChanged();
			if (placeList_ != null) {
				searchLocationAdapter = new SearchLocationAdapter(SearchActivity.this, placeList_);
				searchListView_.setAdapter(searchLocationAdapter);
				searchLocationAdapter.notifyDataSetChanged();
			}
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog(SearchActivity.this) ;
			mProgressDialog.setIndeterminate( true ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected String doInBackground( Void... params )
		{
			// your network operation
			User user = Constants.getCurrentUser(SearchActivity.this);
			if (manualFlag_ == false) {
				GetGoogleSearch(searchEditText_.getText().toString(), user.getLatitude() + "", user.getLongitude() + "");	
			} else {
				String latitude = user.getLatitude() + "";
				String longitude = user.getLongitude() + "";
				List<Address> foundGeocode = null;
				try {
					/* find the addresses  by using getFromLocationName() method with the given address*/
					foundGeocode = new Geocoder(SearchActivity.this).getFromLocationName(whereAddress, 1);
					latitude = foundGeocode.get(0).getLatitude() + ""; //getting latitude
					longitude = foundGeocode.get(0).getLongitude() + "";//getting longitude
				} catch (Exception e) {}
				GetGoogleSearch(searchEditText_.getText().toString(), latitude, longitude);	
			}
			
			return null;
		}
	}

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
	    		hintTextView_.setVisibility(View.GONE);
	    	 }
	    	
        }
    };
    public void GetGoogleSearch(String name, String latitude, String longitude)
	{
		HttpConnectionUtil conn = new HttpConnectionUtil();
		XmlPareser xmlPareser = new XmlPareser(conn);
		try{
			placeList_ = xmlPareser.getGooglePlaces(name, (Constants.getFloatValue(this, "radius") * 1609.34f) + "", latitude, longitude);
		} catch (IOException e) {
			e.printStackTrace();
		
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
    public void setWidth(int width) {
		SharedPreferences.Editor editor = this.getSharedPreferences("com.woloactivityapp.main", 0).edit();
		editor.putInt("width", width);
		editor.commit();
	}

	public int getWidth() {
		SharedPreferences pref = this.getSharedPreferences("com.woloactivityapp.main", 0);
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