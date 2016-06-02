package com.woloactivityapp.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.User;

public class RegisterActivity extends BaseActivity {

	private CustomTextView 				titleCustomTextView_ = null;
	private EditText					firstnameEditText_ = null;
	private EditText					lastnameEditText_ = null;
	private EditText					passwordEditText_ = null;
	private EditText					confirmPasswordEditText_ = null;
	private EditText					phoneEditText_ = null;
	private EditText					emailEditText_ = null;
	private Button						submitButton_ = null;
	private TextView					termsTitleTextView_ = null;
	private WebView						webView_ = null;
	private CheckBox					acceptCheckBox_ = null;
	private ScrollView					parentScrollView_ = null;
	private float						screenScale_ = 0.0f;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setLayoutId(this, R.layout.activity_register);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		initView();
		setListener();
		initData();
	}

	public void setListener() {
		submitButton_.setOnClickListener(this);
	}
	public void initData() {
		
	}
	@Override
	public void onClick(final View view) {
		int viewId = view.getId();
		switch (viewId) {
		case R.id.submitButton: {
			if (checkFields()) {
				SignupTask signupTask = new SignupTask();
				signupTask.execute();
			}
		}
		    break;
		default:
			break;
		}
	}
	public void onBackPressed() {
		startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
		finish();
	}
	public void initView() {
		screenScale_ = getWidth() / 800.0f;
		
		parentScrollView_ = (ScrollView) findViewById(R.id.parentScrollView);
		
		titleCustomTextView_ = (CustomTextView) findViewById(R.id.titleTextView);
		LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) titleCustomTextView_.getLayoutParams();
		param.width = getWidth();
		param.height = (int) (100 * screenScale_); 
		titleCustomTextView_.setLayoutParams(param);
		titleCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60 * screenScale_);
		
		firstnameEditText_ = (EditText) findViewById(R.id.firstnameEditText);
		param = (LinearLayout.LayoutParams) firstnameEditText_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		firstnameEditText_.setLayoutParams(param);
		firstnameEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		firstnameEditText_.setPadding((int) (20 * screenScale_), (int) (4 * screenScale_), (int) (20 * screenScale_), (int) (4 * screenScale_));
		firstnameEditText_.setTypeface(regularTypeface_);
		
		lastnameEditText_ = (EditText) findViewById(R.id.lastnameEditText);
		param = (LinearLayout.LayoutParams) lastnameEditText_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		lastnameEditText_.setLayoutParams(param);
		lastnameEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		lastnameEditText_.setPadding((int) (20 * screenScale_), (int) (4 * screenScale_), (int) (20 * screenScale_), (int) (4 * screenScale_));
		lastnameEditText_.setTypeface(regularTypeface_);
		
		phoneEditText_ = (EditText) findViewById(R.id.phoneEditText);
		param = (LinearLayout.LayoutParams) phoneEditText_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		phoneEditText_.setLayoutParams(param);
		phoneEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		phoneEditText_.setPadding((int) (20 * screenScale_), (int) (4 * screenScale_), (int) (20 * screenScale_), (int) (4 * screenScale_));
		phoneEditText_.setTypeface(regularTypeface_);
		
		emailEditText_= (EditText) findViewById(R.id.emailEditText);
		param = (LinearLayout.LayoutParams) emailEditText_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		emailEditText_.setLayoutParams(param);
		emailEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		emailEditText_.setPadding((int) (20 * screenScale_), (int) (4 * screenScale_), (int) (20 * screenScale_), (int) (4 * screenScale_));
		emailEditText_.setTypeface(regularTypeface_);
		
		passwordEditText_ = (EditText) findViewById(R.id.passwordEditText);
		param = (LinearLayout.LayoutParams) passwordEditText_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		passwordEditText_.setLayoutParams(param);
		passwordEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		passwordEditText_.setPadding((int) (20 * screenScale_), (int) (4 * screenScale_), (int) (20 * screenScale_), (int) (4 * screenScale_));
		
		confirmPasswordEditText_ = (EditText) findViewById(R.id.confirmPasswordEditText);
		param = (LinearLayout.LayoutParams) confirmPasswordEditText_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		confirmPasswordEditText_.setLayoutParams(param);
		confirmPasswordEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		confirmPasswordEditText_.setPadding((int) (20 * screenScale_), (int) (4 * screenScale_), (int) (20 * screenScale_), (int) (4 * screenScale_));
		
		termsTitleTextView_ = (TextView) findViewById(R.id.termsTitleTextView);
		param = (LinearLayout.LayoutParams) termsTitleTextView_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		termsTitleTextView_.setLayoutParams(param);
		termsTitleTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		
		webView_ = (WebView) findViewById(R.id.webView);
		param = (LinearLayout.LayoutParams) webView_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (500 * screenScale_); 
		webView_.setLayoutParams(param);
		
		webView_.loadUrl("file:///android_asset/terms_conditions.html");

		acceptCheckBox_ = (CheckBox) findViewById(R.id.acceptCheckBox);
		param = (LinearLayout.LayoutParams) acceptCheckBox_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		acceptCheckBox_.setLayoutParams(param);
		acceptCheckBox_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * screenScale_);

		submitButton_ = (Button) findViewById(R.id.submitButton);
		param = (LinearLayout.LayoutParams) submitButton_.getLayoutParams();
		param.width = (int) (590 * screenScale_);
		param.height = (int) (130 * screenScale_); 
		submitButton_.setLayoutParams(param);
		submitButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60 * screenScale_);
		submitButton_.setTypeface(regularTypeface_);
		
		parentScrollView_.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event)
			{
				webView_.getParent().requestDisallowInterceptTouchEvent(false);
					return false;
				}
			});
			webView_.setOnTouchListener(new View.OnTouchListener() {
		
			public boolean onTouch(View v, MotionEvent event)
			{
			
				// Disallow the touch request for parent scroll on touch of
				// child view
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
	}
	private boolean checkFields() {
		if (firstnameEditText_.getText().toString().length() == 0 ) {
			firstnameEditText_.setError(getResources().getString(R.string.error_firstname));
			return false;
		}
		if (lastnameEditText_.getText().toString().length() == 0 ) {
			lastnameEditText_.setError(getResources().getString(R.string.error_lastname));
			return false;
		}
		if (phoneEditText_.getText().toString().length() == 0 ) {
			phoneEditText_.setError(getResources().getString(R.string.error_phone));
			return false;
		}
		if (passwordEditText_.getText().toString().length() == 0 ) {
			passwordEditText_.setError(getResources().getString(R.string.error_password));
			return false;
		}
		if (confirmPasswordEditText_.getText().toString().length() == 0 || !passwordEditText_.getText().toString().equals(confirmPasswordEditText_.getText().toString())) {
			confirmPasswordEditText_.setError(getResources().getString(R.string.error_password));
			return false;
		}
		if (emailEditText_.getText().toString().length() == 0 || !Constants.validateEmail(emailEditText_.getText().toString())) {
			emailEditText_.setError(getResources().getString(R.string.error_email));
			return false;
		}
		if (!acceptCheckBox_.isChecked()) {
			acceptCheckBox_.setError("Please accept terms of services.");
			return false;
		}
		return true;
	}
	private class SignupTask extends AsyncTask<Void, Void, String[]>
	{
		ProgressDialog mProgressDialog ;
		User user = new User();
		@Override
		protected void onPostExecute( String[] result )
		{
			mProgressDialog.cancel();
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder( RegisterActivity.this ) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle( "WOLO") ;
				builder.setMessage( getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( result[ 0 ].equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( RegisterActivity.this ) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle( "WOLO") ;
					builder.setMessage( result[ 1 ] ) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( result[ 0 ].equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					
					AlertDialog.Builder builder = new AlertDialog.Builder( RegisterActivity.this ) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle(  "WOLO" ) ;
					builder.setMessage( result[ 1 ] ) ;
					builder.setPositiveButton( R.string.ok, new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick( DialogInterface dialog, int which )
						{
							// TODO Auto-generated method stub 
							startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
							RegisterActivity.this.finish();
						}
					}) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
					
				}
			}
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( RegisterActivity.this) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected String[] doInBackground( Void... params )
		{
			user.setFirstname(firstnameEditText_.getText().toString());
			user.setLastname(lastnameEditText_.getText().toString());
			user.setPassword(passwordEditText_.getText().toString());
			user.setEmail(emailEditText_.getText().toString());
			return UserAPI.signup( firstnameEditText_.getText().toString(), lastnameEditText_.getText().toString(), passwordEditText_.getText().toString(), emailEditText_.getText().toString(), phoneEditText_.getText().toString()) ;
		}
	}
}
