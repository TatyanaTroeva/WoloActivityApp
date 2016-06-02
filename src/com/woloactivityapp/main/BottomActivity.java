package com.woloactivityapp.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

import com.woloactivityapp.channel.ChannelAPI;
import com.woloactivityapp.channel.ChannelListener;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.navigation.MessagesGroupActivity;
import com.woloactivityapp.navigation.ProfileGroupActivity;
import com.woloactivityapp.navigation.SettingGroupActivity;
import com.woloactivityapp.navigation.TagboxGroupActivity;
import com.woloactivityapp.views.AdsActivity;
import com.woloactivityapp.views.TagboxActivity;

public class BottomActivity extends TabActivity  {
	public static TabHost tabHost;
	public static Location 					my_location;
	public LocationManager 					locationManager;
	TabHost.TabSpec spec;
	TabWidget tabWidget;
	private int index = 0;
	final static int arrIcons[] = { R.drawable.tab_1, R.drawable.tab_2, R.drawable.tab_3,R.drawable.tab_4};

	final static String arrTabLabel[] = { "Home", "Profile", "Contact",
		"Partners","More" };

	MyTabView arrTabs[] = new MyTabView[5];
	public static BottomActivity gInstance = null;

	public ChannelListener 			chatListener = new ChannelListener();
	public ChannelAPI 				channel = null;
	
	
	// final int TEXT_ID = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		gInstance = this;
		initialize();


	}

	public void onStop() {
		super.onStop();
		if (locationManager != null) {
			locationManager.removeUpdates(gpsLocationListener);
			locationManager.removeUpdates(netLocationListener);
		}
		
	}
	public void onStart() {
		super.onStart();
		connectFunc();
		getLocation();
	}
	public void connectFunc() {
		ConnectTask connectTask = new ConnectTask();
		connectTask.execute();
	}
    public void onPause() {
    	super.onPause();
    }
	public void onDestroy() {
		super.onDestroy();
		
	}
	private void initialize() {
				
		index = getIntent().getIntExtra("index", 0);
		tabHost = getTabHost();
		tabWidget = getTabWidget();
		
		FrameLayout.LayoutParams  lp = new FrameLayout.LayoutParams(tabWidget.getLayoutParams());
		lp.height = (111 * getScreenWidth()) / (180 * 4);		
		tabWidget.setLayoutParams(lp);
		
		Intent intent;
		intent = new Intent().setClass(this, ProfileGroupActivity.class);
		arrTabs[0] = new MyTabView(this, 0, arrTabLabel[0]);
		spec = tabHost.newTabSpec(arrTabLabel[0]).setIndicator(arrTabs[0]).setContent(intent);
		tabHost.addTab(spec);

	
		intent = new Intent().setClass(this, TagboxGroupActivity.class);
		arrTabs[1] = new MyTabView(this, 1, arrTabLabel[1]);
		spec = tabHost.newTabSpec(arrTabLabel[1]).setIndicator(arrTabs[1]).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, MessagesGroupActivity.class);
		arrTabs[2] = new MyTabView(this, 2, arrTabLabel[2]);
		spec = tabHost.newTabSpec(arrTabLabel[2]).setIndicator(arrTabs[2]).setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, SettingGroupActivity.class);
		arrTabs[3] = new MyTabView(this, 3, arrTabLabel[3]);
		spec = tabHost.newTabSpec(arrTabLabel[3]).setIndicator(arrTabs[3]).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setOnTabChangedListener(mTabChanged);
		tabHost.setCurrentTab(index);
		// TextView tv = (TextView)
		// tabHost.getCurrentTabView().findViewById(TEXT_ID); //for Selected Tab
		// tv.setTextColor(Color.parseColor("#51b6dc"));
		
	/*	tabHost.getTabWidget().getChildAt(4).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View v) {
				tabHost.setCurrentTab(4);
				if(MapGroupActivity.mapgroupactivity != null)
				{
					MapGroupActivity.mapgroupactivity.changeView("MapViewActivity");
				}
				
			}
			});
		tabHost.getTabWidget().getChildAt(3).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View v) {
				tabHost.setCurrentTab(3);
				if(BrandsGroupActivity.brandsgroupactivity != null)
				{
					BrandsGroupActivity.brandsgroupactivity.changeView("BrandsListActivity");
				}
				
			}
			});
		tabHost.getTabWidget().getChildAt(2).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View v) {
				tabHost.setCurrentTab(2);
				if(AboutGroupActivity.aboutgroupactivity != null)
				{
					AboutGroupActivity.aboutgroupactivity.changeView("AboutusActivity");
				}
				
			}
			});
		tabHost.getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View v) {
				tabHost.setCurrentTab(1);
				if(SpecialGroupActivity.specialgroupactivity != null)
				{
					SpecialGroupActivity.specialgroupactivity.changeView("SpecialsListActivity");
				}
				
			}
			});*/
		tabHost.getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View v) {
				tabHost.setCurrentTab(1);
				if (TagboxActivity.tagboxActivity != null)
					TagboxActivity.tagboxActivity.initData();
			}
		});
		tabHost.getTabWidget().getChildAt(3).setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View v) {
				//tabHost.setCurrentTab(3);
				Intent intent = new Intent(BottomActivity.this, AdsActivity.class);
		    	startActivity(intent);
			}
		});
		
	}
	
	public void setCurrentTab(int nIdx)
	{
		tabHost.setCurrentTab(nIdx);
	}

	private class MyTabView extends LinearLayout {
		int nIdx = -1;

		// TextView tv;

		public MyTabView(Context c, int drawableIdx, String label) {
			super(c);
			ImageView iv = new ImageView(c);
			nIdx = drawableIdx;
//			iv.setImageResource(arrIcons[nIdx]);
			iv.setBackgroundResource(arrIcons[nIdx]);
			setId(0x100 + nIdx);
			// tv = new TextView(c);
			// tv.setText(label);
			// tv.setGravity(Gravity.CENTER_HORIZONTAL);
			// tv.setTextSize(11.0f);
			// tv.setId(TEXT_ID);
			LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(getScreenWidth() / 4, 110 * getScreenWidth() / 720);
			layout.setMargins(0, 0, 0, 0);
			iv.setLayoutParams(layout);
			// layout.setMargins(0, 0, 0, 2);
			// tv.setLayoutParams(layout);

			setOrientation(LinearLayout.VERTICAL);
			setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			addView(iv);
			// addView(tv);
		}
	}

	OnTabChangeListener mTabChanged = new OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {
		}
	};

	public void reSelect()
	{
		int idx = tabHost.getCurrentTabView().getId();
		if (idx == 1)
		{
			tabHost.setCurrentTab(0);
			tabHost.setCurrentTab(1);
		}
	}	

	public int getScreenWidth() {
		SharedPreferences pref = this.getSharedPreferences("com.woloactivityapp.main", 0);
		return pref.getInt("width", 800);

	}

	public int getScreenHeight() {
		SharedPreferences pref = this.getSharedPreferences("com.woloactivityapp.main",	0);
		return pref.getInt("height", 1280);

	}
	private class ConnectTask extends AsyncTask<Void, Void, String>
	{
		public ProgressDialog	baseProgress = null;
		public ConnectTask() {
			super();
		}
		@Override
		protected void onPostExecute( String result )
		{
		}

		@Override
		protected void onPreExecute()
		{

		}

		@Override
		protected String doInBackground( Void... params )
		{
			// your network operation
			try {
				if (chatListener == null)
					chatListener = new ChannelListener();
				if (channel == null) {
					channel = new ChannelAPI("http://woloapp.appspot.com/chatservice", Constants.getCurrentUser(BottomActivity.this).getEmail(), chatListener);
					channel.open();
					
				}
				
			} catch (Exception e) {
				Log.e("chatting", e.toString());
			}
			return null;
		}
	}
	private void getLocation() {
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);
		
		//String provider = locationManager.getBestProvider(criteria, true);
		
		if ( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
			if(chkGpsService())
				getLocation();
		}
		else {
			my_location = locationManager.getLastKnownLocation("gps");
			
			if (my_location == null) {
				my_location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}			
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000,
					100, gpsLocationListener);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000,
					100, netLocationListener);
			if (my_location != null) {
				User user = Constants.getCurrentUser(this);
				user.setLatitude(my_location.getLatitude());
				user.setLongitude(my_location.getLongitude());
				Constants.setCurrentUser(this, user);
				UpdateLocationTask updateLocationTask = new UpdateLocationTask();
				updateLocationTask.execute(user.getEmail(), my_location.getLatitude() + "", my_location.getLongitude() + "");
			}

		}
		
	}
    private boolean chkGpsService() {
		String gs = android.provider.Settings.Secure.getString(getContentResolver(), 
				android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		
		Log.i("chkGpsService", "get GPS Service");
		
		if ( gs.indexOf("gps", 0) < 0 ) {
			AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
			gsDialog.setTitle("GPS Status Off");
			gsDialog.setMessage("Turn on GPS");
			gsDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					startActivity(intent);
				}
			}).create().show();
			return false;
		}
		else {
			return true;
		}
	}
    private LocationListener netLocationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
			Log.e("LOCATION", "onStatusChanged");
		}

		@Override
		public void onProviderEnabled(String provider) {
			
			Log.e("LOCATION", "onProviderEnabled");
		}

		@Override
		public void onProviderDisabled(String provider) {
			
			Log.e("LOCATION", "onProviderDisabled");
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.e("LOCATION", "onLocationChanged");
			if (my_location == null)
				return;
			my_location.set(location);
			User user = Constants.getCurrentUser(BottomActivity.this);
			user.setLatitude(my_location.getLatitude());
			user.setLongitude(my_location.getLongitude());
			Constants.setCurrentUser(BottomActivity.this, user);
			UpdateLocationTask updateLocationTask = new UpdateLocationTask();
			updateLocationTask.execute(user.getEmail(), my_location.getLatitude() + "", my_location.getLongitude() + "");
			
		}
	};
	
	private LocationListener gpsLocationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
			Log.e("LOCATION", "onStatusChanged");
		}

		@Override
		public void onProviderEnabled(String provider) {
			
			Log.e("LOCATION", "onProviderEnabled");
		}

		@Override
		public void onProviderDisabled(String provider) {
			
			Log.e("LOCATION", "onProviderDisabled");
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.e("LOCATION", "onLocationChanged");
			if (my_location == null)
				return;
			my_location.set(location);
			User user = Constants.getCurrentUser(BottomActivity.this);
			user.setLatitude(my_location.getLatitude());
			user.setLongitude(my_location.getLongitude());
			Constants.setCurrentUser(BottomActivity.this, user);
			UpdateLocationTask updateLocationTask = new UpdateLocationTask();
			updateLocationTask.execute(user.getEmail(), my_location.getLatitude() + "", my_location.getLongitude() + "");
			
		}
	};
	private class UpdateLocationTask extends AsyncTask<String, Void, Boolean>
	{
		@Override
		protected void onPostExecute( Boolean result )
		{

		}

		@Override
		protected Boolean doInBackground( String... params )
		{
			// your network operation
			
			return UserAPI.updateUserLocation(params[ 0 ], params[1], params[2] ) ;
		}
	}

}
