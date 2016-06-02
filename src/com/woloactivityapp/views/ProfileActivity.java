package com.woloactivityapp.views;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.BottomActivity;
import com.woloactivityapp.main.LoginActivity;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;

import eu.janmuller.android.simplecropimage.CropImage;

public class ProfileActivity extends NavigationBaseActivity {
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	private ImageView 						profileImageView_ = null;
	private ProgressBar 					photoProgressBar_ = null;
	private TextView						tapTextView_ = null;
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
	private Button							editButton_ = null;
	private Button							saveButton_ = null;
	private Button							cancelButton_ = null;
	private Button							updateButton_ = null;
	private LinearLayout 					infoLinearLayout_ = null;
	private float						    screenScale_ = 0.0f;
	
	private final int						ACTIVITY_GETIMAGE = 1 ;
	private final int						REQUEST_CODE_CROP_IMAGE = 2;
	
	public static ProfileActivity			profileActivity = null;
	private File 							mTempFile_ = null;
	private String							filePath_ = null;
	private Button							logoutButton_ = null;
	private ArrayList<String> 				aboutmeVals = new ArrayList<String>();
   	private int 							index = 0;
    private Dialog 							dialog = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	setLayoutId(this,R.layout.activity_profile);
        super.onCreate(savedInstanceState);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    profileActivity = this;
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

    public void onResume() {
    	super.onResume();

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
     		
		editButton_ = (Button) findViewById(R.id.editButton);
		param = (RelativeLayout.LayoutParams) editButton_.getLayoutParams();
		param.setMargins(0, 0, 0, 0);
		param.width =  (int) (100 * screenScale_); 
		param.height = (int) (100 * screenScale_); 
		editButton_.setLayoutParams(param);
		
		updateButton_  = (Button) findViewById(R.id.updateButton);
		param = (RelativeLayout.LayoutParams) updateButton_.getLayoutParams();
		param.setMargins((int) (10 * screenScale_), (int) (10 * screenScale_), 0, 0);
		param.width =  (int) (80 * screenScale_); 
		param.height = (int) (80 * screenScale_); 
		updateButton_.setLayoutParams(param);
		
    	profileImageView_ = (ImageView) findViewById(R.id.profileImageView);
     	param = (RelativeLayout.LayoutParams) profileImageView_.getLayoutParams();
    	param.setMargins(0, (int) (30 * screenScale_), 0, 0);
    	profileImageView_.setScaleType(ScaleType.CENTER_CROP);
    	param.width = getWidth() - (int) (60 * screenScale_);
    	param.height = 2 * (getWidth() - (int) (60 * screenScale_)) / 3;
    	profileImageView_.setLayoutParams(param);
    	
    	photoProgressBar_ = (ProgressBar) findViewById(R.id.photoProgressBar);
    	param = (RelativeLayout.LayoutParams) photoProgressBar_.getLayoutParams();
    	param.setMargins(0, (getWidth() - (int) (90 * screenScale_)) / 3, 0, 0);
    	photoProgressBar_.setLayoutParams(param);
    	
    	tapTextView_ = (TextView) findViewById(R.id.tapTextView);
    	param = (RelativeLayout.LayoutParams) tapTextView_.getLayoutParams();
    	param.setMargins(0, (getWidth() - (int) (90 * screenScale_)) / 3, 0, 0);
    	tapTextView_.setLayoutParams(param);
    	tapTextView_.setVisibility(View.GONE);
    	
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
    	param1 = (LinearLayout.LayoutParams) lastnameLinearLayout_.getLayoutParams();
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
    	param1.height = getWidth() == 800 ? 200 :(int) (200 * screenScale_); 
    	aboutmeLinearLayout_.setPadding((int) (20 * screenScale_), 0, (int) (20 * screenScale_), 0);
    	aboutmeLinearLayout_.setLayoutParams(param1);
    	
    	aboutmeCustomTextView_ = (CustomTextView) findViewById(R.id.aboutmeCustomTextView);
    	aboutmeCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
    	aboutmeValCustomTextView_ = (CustomTextView) findViewById(R.id.aboutmeValCustomTextView);
    	aboutmeValCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);
    	aboutmeValCustomTextView_.setTypeface(regularTypeface_);
     	
    	saveButton_ = (Button) findViewById(R.id.saveButton);
    	param1 = (LinearLayout.LayoutParams) saveButton_.getLayoutParams();
    	param1.height = getWidth() == 800 ? 70 :(int) (80 * screenScale_); 
    	saveButton_.setLayoutParams(param1);
    	saveButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
    	saveButton_.setTypeface(regularTypeface_); 
    	
    	cancelButton_ = (Button) findViewById(R.id.cancelButton);
    	param1 = (LinearLayout.LayoutParams) cancelButton_.getLayoutParams();
    	param1.height = getWidth() == 800 ? 70 :(int) (80 * screenScale_); 
    	cancelButton_.setLayoutParams(param1);
    	cancelButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
    	cancelButton_.setTypeface(regularTypeface_);
    	
    	logoutButton_ = (Button) findViewById(R.id.logoutButton);
    	param1 = (LinearLayout.LayoutParams) logoutButton_.getLayoutParams();
    	param1.height = getWidth() == 800 ? 70 :(int) (80 * screenScale_); 
    	logoutButton_.setLayoutParams(param1);
    	logoutButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
    	logoutButton_.setTypeface(regularTypeface_);
    }
    public void setListener(){
    	updateButton_.setOnClickListener(this);
    	logoutButton_.setOnClickListener(this);
    	editButton_.setOnClickListener(this);
    	profileImageView_.setOnClickListener(this);
    	aboutmeValCustomTextView_.setOnClickListener(this);
    	saveButton_.setOnClickListener(this);
    	cancelButton_.setOnClickListener(this);
    	ageEditText_.setOnEditorActionListener(new OnEditorActionListener() 
    	{
    	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
    	    {
    	        if (actionId == EditorInfo.IME_ACTION_NEXT) 
    	        {
    	        	if (tapTextView_.getVisibility() == View.GONE)
    					return true;
    				launchHashtagCustomDialog();
    	            return true;
    	        }
    	        return false;
    	    }
    	});
    }
    public void initData(){
    	
    	User user = Constants.getCurrentUser(getParent());
    	firstnameEditText_.setText(user.getFirstname());
    	lastnameEditText_.setText(user.getLastname());
		ageEditText_.setText(user.getAge() <= 0 ? "" : user.getAge() + "");
		aboutmeValCustomTextView_.setText(user.getAboutme());
		if (user.getAboutme() == null || user.getAboutme().length() == 0 || !user.getAboutme().contains("#"))
			aboutmeVals = new ArrayList<String>();
		else {
			String val = user.getAboutme();
			val = val.replaceFirst("#", "");
			Collections.addAll(aboutmeVals, val.split("#"));
		}
			
		GetPhotoTask photoTask = new GetPhotoTask();
    	photoTask.execute(user.getEmail());
    	
    	String state = Environment.getExternalStorageState();
    	
    	if (Environment.MEDIA_MOUNTED.equals(state)) {
    		File file = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/wolo");
    		if (!file.exists())
    			file.mkdir();
    		mTempFile_ = new File(file.getAbsolutePath() + "/temp_photo");
    	}
    	else {
    		File file = new File (getFilesDir().getAbsolutePath() + "/wolo");
    		if (!file.exists())
    			file.mkdir();
    		mTempFile_ = new File(file.getAbsolutePath() + "/temp_photo");
    	}
    }
 
    public String getPath(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
    public void saveFile(Bitmap bmp, String filename) {
    	try {
    	       FileOutputStream out = new FileOutputStream(filename);
    	       bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
    	       out.close();
    	} catch (Exception e) {
    	       e.printStackTrace();
    	}
    }
	public void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		// TODO Auto-generated method stub
		switch( requestCode )
		{
			case REQUEST_CODE_CROP_IMAGE: {
				if (data == null) return;
				filePath_ = data.getStringExtra(CropImage.IMAGE_PATH);
                if (filePath_ == null) return;
                Bitmap bitmap = getRegionBitmapFromSdcard(filePath_);
		    	recycleImageView(profileImageView_);
		    	profileImageView_.setImageBitmap( bitmap ) ;
			}
			    break;
			case ACTIVITY_GETIMAGE :
			{
				if ( resultCode == RESULT_OK ) 
				{
					try {

	                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
	                    FileOutputStream fileOutputStream = new FileOutputStream(mTempFile_);
	                    copyStream(inputStream, fileOutputStream);
	                    fileOutputStream.close();
	                    inputStream.close();

	                    startCropImage(mTempFile_.getPath());

	                } catch (Exception e) {

	                }
				}
				break ;
			}
			case Constants.MSG_CHOOSE_PHOTO: {
				if ( resultCode == RESULT_OK ) 
				{
					try {

	                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
	                    FileOutputStream fileOutputStream = new FileOutputStream(mTempFile_);
	                    copyStream(inputStream, fileOutputStream);
	                    fileOutputStream.close();
	                    inputStream.close();

	                    startCropImage(mTempFile_.getPath());

	                } catch (Exception e) {

	                }
				}
			}
			    break;
			case Constants.MSG_TAKE_PHOTO: {
				String captureString = data.getStringExtra("captureString");
		    	if (captureString == null || captureString.length() == 0)
		    		return;
		    	startCropImage(captureString);
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
		case R.id.updateButton: {
			launchUpdateDialog();
		}
		    break;
		case R.id.logoutButton: {
			Constants.setCurrentUser(getParent(), new User());
			startActivity(new Intent(getParent(), LoginActivity.class));
			getParent().finish();
		}
		    break;
		case R.id.aboutmeValCustomTextView: {
			if (tapTextView_.getVisibility() == View.GONE)
				return;
			launchHashtagCustomDialog();
		}
		    break;
		case R.id.profileImageView :{
			if (tapTextView_.getVisibility() == View.GONE)
				return;
			launchSelCustomDialog();
//			Intent intent = new Intent() ;
//   	        intent.setAction( Intent.ACTION_GET_CONTENT ) ;  
//   	        intent.setType( "image/*" ) ;
//   	        getParent().startActivityForResult( intent, ACTIVITY_GETIMAGE ) ; 
		}
		    break;
		case R.id.saveButton:{
			tapTextView_.setVisibility(View.GONE);
			firstnameEditText_.setEnabled(false);
			lastnameEditText_.setEnabled(false);
			ageEditText_.setEnabled(false);

			editButton_.setVisibility(View.VISIBLE);
			saveButton_.setVisibility(View.INVISIBLE);
			cancelButton_.setVisibility(View.INVISIBLE);
			
			if (BottomActivity.my_location == null) {
				return;
			}
				
			User user = Constants.getCurrentUser(getParent());
			UpdateProfileTask updateProfileTask = new UpdateProfileTask();
			updateProfileTask.execute(user.getEmail(), 
									  firstnameEditText_.getText().toString(), 
									  lastnameEditText_.getText().toString(), 
									  ageEditText_.getText().toString(), 
									  aboutmeValCustomTextView_.getText().toString(), 
									  BottomActivity.my_location.getLatitude() + "",
									  BottomActivity.my_location.getLongitude() + "",
									  filePath_);
		}
		    break;
		case R.id.cancelButton: {
			editButton_.setVisibility(View.VISIBLE);
			saveButton_.setVisibility(View.INVISIBLE);
			cancelButton_.setVisibility(View.INVISIBLE);
			tapTextView_.setVisibility(View.GONE);
			firstnameEditText_.setEnabled(false);
			lastnameEditText_.setEnabled(false);
			ageEditText_.setEnabled(false);
			initData();
		}
		    break;
		case R.id.editButton: {
			tapTextView_.setVisibility(View.VISIBLE);
			firstnameEditText_.setEnabled(true);
			lastnameEditText_.setEnabled(true);
			ageEditText_.setEnabled(true);
			firstnameEditText_.requestFocus();
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			if(imm != null){
			        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
			}
			editButton_.setVisibility(View.GONE);
			saveButton_.setVisibility(View.VISIBLE);
			cancelButton_.setVisibility(View.VISIBLE);
		}
		default:
			break;
		}
    }
    public void launchSelCustomDialog() {
    	
		if (dialog == null) {
			dialog = new Dialog(getParent());
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.selphoto_dialog);
		}

		dialog.show();
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));

		Button cancelbtn = (Button) dialog.findViewById(R.id.cancelButton);
		if (cancelbtn != null) {
			cancelbtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
		}
		Button takebtn = (Button) dialog.findViewById(R.id.takeButton);
		if (takebtn != null) {

			takebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				    Intent intent = new Intent(ProfileActivity.this, AndroidCamera.class);
				    getParent().startActivityForResult(intent, Constants.MSG_TAKE_PHOTO);
					dialog.cancel();
				}
			});
		}
		Button choosebtn = (Button) dialog.findViewById(R.id.pickButton);
		if (choosebtn != null) {
			choosebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
		        	intent.setAction(android.content.Intent.ACTION_GET_CONTENT);
		        	intent.setType("image/*");
		        	getParent().startActivityForResult(intent, Constants.MSG_CHOOSE_PHOTO);
					dialog.cancel();
				}
			});
		}

	}
    public void launchUpdateDialog(){
    	final Dialog dialog = new Dialog(getParent());
   	 	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 

   	 	dialog.setContentView(R.layout.profile_dialog);
   	 	dialog.show();

   	  
   	 	final EditText emailEditText = (EditText) dialog.findViewById(R.id.emailEditText);
   	 	final EditText passwordEditText = (EditText) dialog.findViewById(R.id.passwordEditText);
   	 	final EditText phoneEditText = (EditText) dialog.findViewById(R.id.phoneEditText);
   	 	
   	 	User user = Constants.getCurrentUser(ProfileActivity.this);
   	 	emailEditText.setText(user.getEmail());
   	 	passwordEditText.setText("") ;
   	 	phoneEditText.setText(user.getPhoneNumber());
   	 
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
   	 	Button updatebtn = (Button)dialog.findViewById(R.id.updateButton);
   	 	if(updatebtn != null)
   	 	{
   	 		updatebtn.setOnClickListener(new OnClickListener() {
   	 			@Override
   	 			public void onClick(View v) {
   	 				
   	 				if (!Constants.validateEmail(emailEditText.getText().toString())){
   	 					emailEditText.setError(getResources().getString(R.string.error_email));
   	 					return;
   	 				}
	   	 			if (phoneEditText.getText().toString().length() == 0 ) {
		   	 			phoneEditText.setError(getResources().getString(R.string.error_phone));
		   	 			return;
	   	 			}
		   	 		if (passwordEditText.getText().toString().length() == 0 ) {
		   	 			passwordEditText.setError(getResources().getString(R.string.error_password));
		   	 			return;
		   	 		}
		   	 		User user = Constants.getCurrentUser(ProfileActivity.this);
		   	 		UpdateEmailTask updateEmailTask = new UpdateEmailTask();
		   	 		updateEmailTask.execute(user.getEmail(), emailEditText.getText().toString(), passwordEditText.getText().toString(), phoneEditText.getText().toString());
   	 				dialog.hide();
   	 			}
   	 		});
   	 	}
    }
	private class GetPhotoTask extends AsyncTask<String, Void, byte[]>
	{
		@Override
		protected void onPostExecute( byte[] result )
		{
			photoProgressBar_.setVisibility(View.GONE);
			User user = Constants.getCurrentUser(ProfileActivity.this);
			if (result == null) {
				String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wolo/" + user.getFirstname() + user.getLastname();
				File file = new File(path);
				if (file.exists()) {
					profileImageView_.setImageBitmap( BitmapFactory.decodeFile(path));
				}
				return;
			}
			//Bitmap bitmap = getRegionBitmapFromArray(result);
			Bitmap bitmap = BitmapFactory.decodeByteArray( result, 0, result.length ) ;
			recycleImageView(profileImageView_);
			profileImageView_.setImageBitmap( bitmap ) ;
			saveFile(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath() + "/wolo/" + user.getFirstname() + user.getLastname());
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
	private class UpdateProfileTask extends AsyncTask<String, Void, Boolean>
	{
		ProgressDialog mProgressDialog ;
		private User user = null;
		@Override
		protected void onPostExecute( Boolean result )
		{
			mProgressDialog.dismiss() ;
			if (!result) {
				AlertDialog.Builder builder = new AlertDialog.Builder( getParent() ) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle( "WOLO" ) ;
				builder.setMessage( "Failed to update") ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			} else {
				Constants.setCurrentUser(getParent(), user);
				
			}
		}
		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog(getParent() ) ;
			mProgressDialog.setIndeterminate( true ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}
		@Override
		protected Boolean doInBackground( String... params )
		{
			// your network operation
			user = Constants.getCurrentUser(getParent());
			user.setEmail(params[0]);
			user.setFirstname(params[ 1 ]);
			user.setLastname(params[ 2 ]);
			user.setAge( params[ 3 ].length() > 0 ? Integer.parseInt(params[ 3 ]) : 0);
			user.setAboutme(params[ 4 ]);
			return UserAPI.updateUserProfile( params[ 0 ], params[ 1 ], params[ 2 ], params[ 3 ], params[ 4 ], params[ 5 ], params[ 6 ], params[ 7 ] ) ;
		}
	}
	private class UpdateEmailTask extends AsyncTask<String, Void, Boolean>
	{
		ProgressDialog mProgressDialog ;
		private User user = null;
		@Override
		protected void onPostExecute( Boolean result )
		{
			mProgressDialog.dismiss() ;
			if (!result) {
				AlertDialog.Builder builder = new AlertDialog.Builder( getParent() ) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle( "WOLO" ) ;
				builder.setMessage( "Failed to update") ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			} else {
				Constants.setCurrentUser(getParent(), user);
				
			}
		}
		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog(getParent() ) ;
			mProgressDialog.setIndeterminate( true ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}
		@Override
		protected Boolean doInBackground( String... params )
		{
			// your network operation
			user = Constants.getCurrentUser(getParent());
			user.setEmail(params[1]);
			user.setPassword(params[ 2 ]);
			user.setPhoneNumber(params[ 3 ]);
			return UserAPI.updateUserEmail( params[ 0 ], params[ 1 ], params[ 2 ], params[ 3 ]) ;
		}
	}
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
    private void startCropImage(String path) {

        Intent intent = new Intent(this, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, path);
        intent.putExtra(CropImage.SCALE, true);

        intent.putExtra(CropImage.ASPECT_X, 3);
        intent.putExtra(CropImage.ASPECT_Y, 2);

        getParent().startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
    public void createDialog(final String[] vals, int index, final TextView textView) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
		builder.setSingleChoiceItems(vals, index, new DialogInterface.OnClickListener() {
		     @Override
		     public void onClick(DialogInterface dialog, int which) {
		    	 textView.setText(vals[which]);
		    	 dialog.dismiss();
		     }
		});
		AlertDialog dialog = builder.create();
		  
		Window mWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.show();
    }
    public void launchHashtagCustomDialog(){
    	index = 0;
    	aboutmeVals = new ArrayList<String>();
    	String val = aboutmeValCustomTextView_.getText().toString();
    	if (val.contains("#")) {
    		val = val.replaceFirst("#", "");
    		Collections.addAll(aboutmeVals, val.split("#"));
    	}

    	final String originalText = aboutmeValCustomTextView_.getText().toString();
		
	   	final Dialog hashtagDialog = new Dialog(getParent());
	   	hashtagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	   	hashtagDialog.setContentView(R.layout.hashtag_dialog);
	   	hashtagDialog.show();
	   	
		final EditText hashtagEditText = (EditText) hashtagDialog.findViewById(R.id.hashtagEditText);
	   	if (aboutmeVals != null && aboutmeVals.size() > 0)
	   		hashtagEditText.setText(aboutmeVals.get(0));
	   	
	    Button cancelButton = (Button)hashtagDialog.findViewById(R.id.cancelButton);
	   	if(cancelButton != null)
	   	{
	   		cancelButton.setOnClickListener(new OnClickListener() {
	   			@Override
	   			public void onClick(View v) {
	   				aboutmeValCustomTextView_.setText(originalText);
	   				hashtagDialog.hide();
	   			}
	   	    });
	   	}
	    Button previousButton = (Button)hashtagDialog.findViewById(R.id.previousButton);
	   	if(previousButton != null)
	   	{
	   		previousButton.setOnClickListener(new OnClickListener() {
	   			@Override
	   			public void onClick(View v) {
	   				doneFunc(hashtagEditText);
	   				if (--index < 0)
	   					index = 0;

	   				hashtagEditText.setText(aboutmeVals.get(index));
	   			}
	   	    });
	   	}
	    Button nextButton = (Button)hashtagDialog.findViewById(R.id.nextButton);
	   	if(nextButton != null)
	   	{
	   		nextButton.setOnClickListener(new OnClickListener() {
	   			@Override
	   			public void onClick(View v) {
	   				doneFunc(hashtagEditText);
	   				if (++index >= aboutmeVals.size()) {
	   					hashtagEditText.setText("");
	   					index = aboutmeVals.size();
	   				}
	   				else {
	   					hashtagEditText.setText( aboutmeVals.get(index));
	   				}

	   			}
	   	    });
	   	}
	    Button doneButton = (Button)hashtagDialog.findViewById(R.id.doneButton);
	   	if(doneButton != null)
	   	{
	   		doneButton.setOnClickListener(new OnClickListener() {
	   			@Override
	   			public void onClick(View v) {
	   				doneFunc(hashtagEditText);
	   				hashtagDialog.hide();
	   			}
	   	    });
	   	}
	   
	   	
    }
    public void doneFunc(EditText hashtagEditText) {
    	String val = hashtagEditText.getText().toString();
		if (val.startsWith("#"))
			val = val.replaceFirst("#", "");
		
		if (aboutmeVals.size() <= index) {
			if (!val.isEmpty()) 
				aboutmeVals.add(val);
		} else {
			if (!val.isEmpty())
				aboutmeVals.set(index, val);
			else
				aboutmeVals.remove(index);
		}
		
		String val1 = "";
		for (int i=0;i<aboutmeVals.size();i++)  {
			val1 += "#" + aboutmeVals.get(i);
		}
		aboutmeValCustomTextView_.setText(val1);
    }
}