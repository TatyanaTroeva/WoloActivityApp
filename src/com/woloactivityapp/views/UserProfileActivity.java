package com.woloactivityapp.views;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;

public class UserProfileActivity extends NavigationBaseActivity {
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	private Button							backButton_ = null;
	private ImageView 						profileImageView_ = null;
	private ProgressBar 					photoProgressBar_ = null;
	private LinearLayout 					firstnameLinearLayout_ = null;
	private CustomTextView					firstnameCustomTextView_ = null;
	private EditText						firstnameEditText_ = null;
	private LinearLayout 					lastnameLinearLayout_ = null;
	private CustomTextView					lastnameCustomTextView_ = null;
	private EditText						lastnameEditText_ = null;
	private LinearLayout 					ageLinearLayout_ = null;
	private CustomTextView					ageCustomTextView_ = null;
	private EditText						ageEditText_ = null;
	private LinearLayout 					aboutmeLinearLayout_ = null;
	private CustomTextView					aboutmeCustomTextView_ = null;
	private CustomTextView					aboutmeValCustomTextView_ = null;
	
	private LinearLayout 					infoLinearLayout_ = null;
	
	private float						    screenScale_ = 0.0f;
	private User							currentUser_ = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	setLayoutId(this,R.layout.activity_userprofile);
        super.onCreate(savedInstanceState);
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
		
    	profileImageView_ = (ImageView) findViewById(R.id.profileImageView);
     	param = (RelativeLayout.LayoutParams) profileImageView_.getLayoutParams();
    	param.setMargins(0, (int) (130 * screenScale_), 0, 0);
    	profileImageView_.setScaleType(ScaleType.CENTER_CROP);
    	param.width = getWidth() - (int) (60 * screenScale_);
    	param.height = 2 * (getWidth() - (int) (90 * screenScale_)) / 3;
    	profileImageView_.setLayoutParams(param);
    	
    	photoProgressBar_ = (ProgressBar) findViewById(R.id.photoProgressBar);
    	param = (RelativeLayout.LayoutParams) photoProgressBar_.getLayoutParams();
    	param.setMargins(0, (getWidth() - (int) (90 * screenScale_)) / 3 + 100, 0, 0);
    	photoProgressBar_.setLayoutParams(param);
    	
    	infoLinearLayout_ = (LinearLayout) findViewById(R.id.infoLinearLayout);
     	param = (RelativeLayout.LayoutParams) infoLinearLayout_.getLayoutParams();
    	param.setMargins(0, (int) (30 * screenScale_), 0, 0);
    	infoLinearLayout_.setLayoutParams(param);

    	firstnameLinearLayout_ = (LinearLayout) findViewById(R.id.firstnameLinearLayout);
    	LinearLayout.LayoutParams param1 = (LinearLayout.LayoutParams) firstnameLinearLayout_.getLayoutParams();
    	param1.width = getWidth();
    	param1.height = getWidth() == 800 ? 50 :(int) (60 * screenScale_); 
    	firstnameLinearLayout_.setPadding((int) (20 * screenScale_), 0, (int) (20 * screenScale_), 0);
    	firstnameLinearLayout_.setLayoutParams(param1);
    	
    	firstnameCustomTextView_ = (CustomTextView) findViewById(R.id.firstnameCustomTextView);
    	firstnameCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);

    	firstnameEditText_ = (EditText) findViewById(R.id.firstnameEditText);
    	firstnameEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
    	firstnameEditText_.setTypeface(regularTypeface_);
    	
    	lastnameLinearLayout_ = (LinearLayout) findViewById(R.id.lastnameLinearLayout);
    	param1 = (LinearLayout.LayoutParams) firstnameLinearLayout_.getLayoutParams();
    	param1.width = getWidth();
    	param1.height = getWidth() == 800 ? 50 :(int) (60 * screenScale_); 
    	lastnameLinearLayout_.setPadding((int) (20 * screenScale_), 0, (int) (20 * screenScale_), 0);
    	lastnameLinearLayout_.setLayoutParams(param1);
    	
    	lastnameCustomTextView_ = (CustomTextView) findViewById(R.id.lastnameCustomTextView);
    	lastnameCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);

    	lastnameEditText_ = (EditText) findViewById(R.id.lastnameEditText);
    	lastnameEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
    	lastnameEditText_.setTypeface(regularTypeface_);
		
    	ageLinearLayout_ = (LinearLayout) findViewById(R.id.ageLinearLayout);
    	param1 = (LinearLayout.LayoutParams) ageLinearLayout_.getLayoutParams();
    	param1.width = getWidth();
    	param1.height = getWidth() == 800 ? 50 :(int) (60 * screenScale_); 
    	ageLinearLayout_.setPadding((int) (20 * screenScale_), 0, (int) (20 * screenScale_), 0);
    	ageLinearLayout_.setLayoutParams(param1);
    	
    	ageCustomTextView_ = (CustomTextView) findViewById(R.id.ageCustomTextView);
    	ageCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
    	ageEditText_ = (EditText) findViewById(R.id.ageEditText);
    	ageEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
    	ageEditText_.setTypeface(regularTypeface_);
    	
    	aboutmeLinearLayout_ = (LinearLayout) findViewById(R.id.aboutmeLinearLayout);
    	param1 = (LinearLayout.LayoutParams) aboutmeLinearLayout_.getLayoutParams();
    	param1.width = getWidth();
    	param1.height = getWidth() == 800 ? 50 :(int) (60 * screenScale_); 
    	aboutmeLinearLayout_.setPadding((int) (20 * screenScale_), 0, (int) (20 * screenScale_), 0);
    	aboutmeLinearLayout_.setLayoutParams(param1);
    	
    	aboutmeCustomTextView_ = (CustomTextView) findViewById(R.id.aboutmeCustomTextView);
    	aboutmeCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
    	aboutmeValCustomTextView_ = (CustomTextView) findViewById(R.id.aboutmeValCustomTextView);
    	aboutmeValCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
    	aboutmeValCustomTextView_.setTypeface(regularTypeface_);
    	
    	
    }
    public void setListener(){
    	backButton_.setOnClickListener(this);
    }
    public void initData(){
    	currentUser_ = (User) getIntent().getSerializableExtra("user");
    	titleCustomTextView_.setText(currentUser_.getFirstname() + " " + currentUser_.getLastname());
    	firstnameEditText_.setText(currentUser_.getFirstname());
    	lastnameEditText_.setText(currentUser_.getLastname());
		ageEditText_.setText(currentUser_.getAge() + "");
		aboutmeCustomTextView_.setText(currentUser_.getAboutme());
		GetPhotoTask photoTask = new GetPhotoTask();
    	photoTask.execute(currentUser_.getEmail());
    	
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
	private class GetPhotoTask extends AsyncTask<String, Void, byte[]>
	{
		@Override
		protected void onPostExecute( byte[] result )
		{
			photoProgressBar_.setVisibility(View.GONE);
			if (result == null)
				return;
			//Bitmap bitmap = getRegionBitmapFromArray(result);
			Bitmap bitmap = BitmapFactory.decodeByteArray( result, 0, result.length ) ;
			recycleImageView(profileImageView_);
			profileImageView_.setImageBitmap( bitmap ) ;
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
	public Bitmap getRegionBitmapFromSdcard(String filePath) {
		InputStream istream =   null;
		File file = new File(filePath);
		if (file == null || !file.exists())
			return null;
		try {
    		BitmapFactory.Options o = new BitmapFactory.Options();
    		o.inJustDecodeBounds=true;
    		BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            istream = getParent().getContentResolver().openInputStream(Uri.fromFile(file));
            BitmapRegionDecoder decoder = null;
            decoder = BitmapRegionDecoder.newInstance(istream, false);
            Bitmap bMap = decoder.decodeRegion(new Rect(0,0, o.outWidth, o.outHeight), null);    
    	    return bMap;
        } catch (Exception e1) {
         e1.printStackTrace();
        }
        return null;
	}
	public Bitmap getRegionBitmapFromArray(byte[] photo) {
		InputStream istream =   null;

		try {
    		BitmapFactory.Options o = new BitmapFactory.Options();
    		o.inJustDecodeBounds=true;
    		BitmapFactory.decodeByteArray( photo, 0, photo.length ) ;
    		
            istream = new ByteArrayInputStream(photo); 
            BitmapRegionDecoder decoder = null;
            decoder = BitmapRegionDecoder.newInstance(istream, false);
            Bitmap bMap = decoder.decodeRegion(new Rect(0,0, o.outWidth, o.outHeight), null);    
    	    return bMap;
        } catch (Exception e1) {
         e1.printStackTrace();
        }
        return null;
	}
	
	
}