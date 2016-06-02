package com.woloactivityapp.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.utils.JSONParser;

public class LoginActivity extends BaseActivity {

	private CustomTextView 				titleCustomTextView_ = null;
	private EditText					emailEditText_ = null;
	private EditText					passwordEditText_ = null;
	private Button						signinButton_ = null;
	private Button						registerButton_ = null;
	private CustomTextView				forgotCustomTextView_ = null;
	private float						screenScale_ = 0.0f;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setLayoutId(this, R.layout.activity_login);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		initView();
		setListener();
		initData();
	}

	public void setListener() {
		registerButton_.setOnClickListener(this);
		signinButton_.setOnClickListener(this);
		forgotCustomTextView_.setOnClickListener(this);
	}
	public void initData() {

	}
	
	@Override
	public void onClick(final View view) {
		int viewId = view.getId();
		switch (viewId) {
		case R.id.registerButton: {
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
			finish();
		}
		    break;
		case R.id.signinButton: {
			if (checkFields()) {
				LoginTask loginTask = new LoginTask();
				loginTask.execute();
			}
		}
		    break;
		case R.id.forgotTextView: {
			if (emailEditText_.getText().toString().length() == 0 || !Constants.validateEmail(emailEditText_.getText().toString())) {
				emailEditText_.setError(getResources().getString(R.string.error_email));
				return;
			}
			ForgotPasswordTask forgotPasswordTask = new ForgotPasswordTask();
			forgotPasswordTask.execute(emailEditText_.getText().toString());
		}
		    break;
		default:
			break;
		}
	}
	private boolean checkFields() {
		if (emailEditText_.getText().toString().length() == 0 || !Constants.validateEmail(emailEditText_.getText().toString())) {
			emailEditText_.setError(getResources().getString(R.string.error_email));
			return false;
		}
		if (passwordEditText_.getText().toString().length() == 0 ) {
			passwordEditText_.setError(getResources().getString(R.string.error_password));
			return false;
		}
		return true;
	}
	public void initView() {
		screenScale_ = getWidth() / 800.0f;
		
		titleCustomTextView_ = (CustomTextView) findViewById(R.id.titleTextView);
		RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) titleCustomTextView_.getLayoutParams();
		param.setMargins(0, 0, 0, 0);
		param.width = getWidth();
		param.height = (int) (100 * screenScale_); 
		titleCustomTextView_.setLayoutParams(param);
		titleCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60 * screenScale_);
		
		emailEditText_ = (EditText) findViewById(R.id.emailEditText);
		param = (RelativeLayout.LayoutParams) emailEditText_.getLayoutParams();
		param.setMargins((int) (50 * screenScale_), (int) (275 * screenScale_), (int) (50 * screenScale_), 0);
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		emailEditText_.setLayoutParams(param);
		emailEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		emailEditText_.setPadding((int) (20 * screenScale_), (int) (4 * screenScale_), (int) (20 * screenScale_), (int) (4 * screenScale_));
		emailEditText_.setTypeface(regularTypeface_);
		
		passwordEditText_ = (EditText) findViewById(R.id.passwordEditText);
		param = (RelativeLayout.LayoutParams) passwordEditText_.getLayoutParams();
		param.setMargins((int) (50 * screenScale_), (int) (400 * screenScale_), (int) (50 * screenScale_), 0);
		param.width = (int) (590 * screenScale_);
		param.height = (int) (100 * screenScale_); 
		passwordEditText_.setLayoutParams(param);
		passwordEditText_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		passwordEditText_.setPadding((int) (20 * screenScale_), (int) (4 * screenScale_), (int) (20 * screenScale_), (int) (4 * screenScale_));
		
		signinButton_ = (Button) findViewById(R.id.signinButton);
		param = (RelativeLayout.LayoutParams) signinButton_.getLayoutParams();
		param.setMargins((int) (50 * screenScale_), (int) (530 * screenScale_), (int) (50 * screenScale_), 0);
		param.width = (int) (590 * screenScale_);
		param.height = (int) (130 * screenScale_); 
		signinButton_.setLayoutParams(param);
		signinButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60 * screenScale_);
		signinButton_.setTypeface(regularTypeface_);
		
		registerButton_ = (Button) findViewById(R.id.registerButton);
		param = (RelativeLayout.LayoutParams) registerButton_.getLayoutParams();
		param.setMargins((int) (50 * screenScale_), (int) (725 * screenScale_), (int) (50 * screenScale_), 0);
		param.width = (int) (590 * screenScale_);
		param.height = (int) (130 * screenScale_); 
		registerButton_.setLayoutParams(param);
		registerButton_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 60 * screenScale_);
		registerButton_.setTypeface(regularTypeface_);
		
		forgotCustomTextView_ = (CustomTextView) findViewById(R.id.forgotTextView);
		param = (RelativeLayout.LayoutParams) forgotCustomTextView_.getLayoutParams();
		param.setMargins(0, (int) (930 * screenScale_), 0, 0);
		forgotCustomTextView_.setLayoutParams(param);
		forgotCustomTextView_.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * screenScale_);
		forgotCustomTextView_.setText(Html.fromHtml(getResources().getString(R.string.forgotpassword)));
	}
	private class LoginTask extends AsyncTask<Void, Void, Object[]>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( Object[] result )
		{
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this ) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle( "WOLO") ;
				builder.setMessage( getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this ) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle( "WOLO" ) ;
					builder.setMessage( JSONParser.parseUserInfoResponse(LoginActivity.this, ((String)result[ 1 ]) )[0] ) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					String[] list = JSONParser.parseUserInfoResponse(LoginActivity.this, (String)result[1]);
					Constants.setCurrentUser(LoginActivity.this, (String)list[ 1 ], (String)list[ 2], (String)list[ 3 ], (String)list[ 4], (String)list[ 5 ], (String)list[ 6 ], (String)list[ 7 ], (String)list[ 8 ]);
					startActivity(new Intent(LoginActivity.this, BottomActivity.class));
					LoginActivity.this.finish();
				}
			}
			mProgressDialog.dismiss() ;
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( LoginActivity.this) ;
			mProgressDialog.setIndeterminate( true ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected Object[] doInBackground( Void... params )
		{
			// your network operation
			return UserAPI.login( emailEditText_.getText().toString(), passwordEditText_.getText().toString() ) ;
		}
	}
	private class ForgotPasswordTask extends AsyncTask<String, Void, String[]>
	{
		ProgressDialog mProgressDialog ;
		@Override
		protected void onPostExecute( String[] result )
		{
			mProgressDialog.cancel();
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this ) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle( "WOLO" ) ;
				builder.setMessage( getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( result[ 0 ].equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this ) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle( "WOLO" ) ;
					builder.setMessage( result[ 1 ] ) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( result[ 0 ].equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					
					AlertDialog.Builder builder = new AlertDialog.Builder( LoginActivity.this ) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle(  "WOLO") ;
					builder.setMessage( result[ 1 ] ) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
					
				}
			}
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( LoginActivity.this) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected String[] doInBackground( String... params )
		{
			return UserAPI.recoverPassword(params[0]) ;
		}
	}
	
}
