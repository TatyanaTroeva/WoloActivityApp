package com.woloactivityapp.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.Contest;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.BottomActivity;
import com.woloactivityapp.main.R;
import com.woloactivityapp.views.MyactivitiesActivity;
import com.woloactivityapp.views.TagboxActivity;
public class TagboxAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	ArrayList<Contest> contestsList;
	TagboxActivity tagboxActivity;
	OnClickListener peopleOnClickListener;
	Typeface regularTypeface_;
	public TagboxAdapter(Context c, ArrayList<Contest> contests) {
		contestsList = contests;
		tagboxActivity = (TagboxActivity)c;
		mInflater = LayoutInflater.from(c);
		regularTypeface_ = Typeface.createFromAsset(tagboxActivity.getAssets(), "fonts/Roboto_Regular.ttf");
	}

	@Override
	public int getCount() {
		return contestsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return contestsList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.tagboxitemlayout, null);
			holder = new ViewHolder();
			holder.title = (CustomTextView) convertView.findViewById(R.id.itemTitleCustomTextView);
			holder.date = (CustomTextView) convertView.findViewById(R.id.itemDateCustomTextView);
			holder.distance = (CustomTextView) convertView.findViewById(R.id.itemMileCustomTextView);
			holder.joinButton = (Button) convertView.findViewById(R.id.itemJoinButton);
			holder.joinButton.setTypeface(regularTypeface_);
			holder.rootlayout = (RelativeLayout) convertView.findViewById(R.id.rootlayout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		User user = Constants.getCurrentUser(tagboxActivity);
		final Contest contest = contestsList.get(position);
		holder.title.setText(contest.getName());
		SimpleDateFormat format = new SimpleDateFormat("MMMM d yyyy, hh:mm a");
		holder.date.setText(format.format(contest.getWhen()));
		holder.distance.setText(Constants.GetDistance(BottomActivity.my_location.getLatitude(), BottomActivity.my_location.getLongitude(), contest.getLatitude(), contest.getLongitude()) + " mi");

		if (contest.getCreator().equals(user.getEmail())) {
			holder.joinButton.setVisibility(View.GONE);
		}
		else if (contest.getJoinCategory() == 1) {
			holder.joinButton.setVisibility(View.VISIBLE);
			holder.joinButton.setBackgroundResource(R.drawable.yellowbtn);
			holder.joinButton.setText(tagboxActivity.getResources().getString(R.string.requestjoin));
			holder.rootlayout.setBackgroundResource(R.color.darkcolor);
			holder.joinButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					RequestToClickTask requestToClickTask = new RequestToClickTask();
					requestToClickTask.execute(contest.getName());
				}
			});
		} else {
			holder.joinButton.setVisibility(View.VISIBLE);
			holder.joinButton.setBackgroundResource(R.drawable.greenbtn);
			holder.joinButton.setText(tagboxActivity.getResources().getString(R.string.clickjoin));
			holder.rootlayout.setBackgroundResource(R.color.backgroundcolor1);
			holder.joinButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					JoinToClickTask joinToClickTask = new JoinToClickTask();
					joinToClickTask.execute(contest.getName());
				}
			});
		}
		
		return convertView;
	}

	static class ViewHolder {
		CustomTextView title;
		CustomTextView date;
		Button joinButton;
		CustomTextView distance;
		RelativeLayout rootlayout;
	}
	private class JoinToClickTask extends AsyncTask<String, Void, Object[]>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( Object[] result )
		{
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(tagboxActivity.getParent()) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle( R.string.app_name ) ;
				builder.setMessage( tagboxActivity.getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( tagboxActivity.getParent()) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle( R.string.app_name ) ;
					builder.setMessage((String)result[ 1 ]) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					tagboxActivity.initData();
					Intent intent = new Intent(tagboxActivity, MyactivitiesActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					tagboxActivity.goNextHistory("MyactivitiesActivity", intent);
				}
			}
			mProgressDialog.dismiss() ;
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( tagboxActivity.getParent()) ;
			mProgressDialog.setIndeterminate( true ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected Object[] doInBackground( String... params )
		{
			// your network operation
			User user = Constants.getCurrentUser(tagboxActivity);
			return UserAPI.clickToJoin(user.getEmail(), params[0]);
		}
	}
	private class RequestToClickTask extends AsyncTask<String, Void, Object[]>
	{
		ProgressDialog mProgressDialog ;
		
		@Override
		protected void onPostExecute( Object[] result )
		{
			if ( result == null )
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(tagboxActivity.getParent()) ;
				builder.setIcon( R.drawable.ic_launcher ) ;
				builder.setTitle( R.string.app_name ) ;
				builder.setMessage( tagboxActivity.getString( R.string.fail_connect ) ) ;
				builder.setPositiveButton( R.string.ok, null ) ;
				builder.setCancelable( false ) ;
				builder.create().show() ;
			}
			else
			{
				if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_FAIL ) )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( tagboxActivity.getParent()) ;
					builder.setIcon( R.drawable.ic_launcher ) ;
					builder.setTitle( R.string.app_name ) ;
					builder.setMessage((String)result[ 1 ]) ;
					builder.setPositiveButton( R.string.ok, null ) ;
					builder.setCancelable( false ) ;
					builder.create().show() ;
				}
				else if ( ((String)result[ 0 ]).equalsIgnoreCase( UserAPI.RESPONSE_OK ) )
				{
					tagboxActivity.initData();
					Intent intent = new Intent(tagboxActivity, MyactivitiesActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					tagboxActivity.goNextHistory("MyactivitiesActivity", intent);
				}
			}
			mProgressDialog.dismiss() ;
		}

		@Override
		protected void onPreExecute()
		{
			mProgressDialog = new ProgressDialog( tagboxActivity.getParent()) ;
			mProgressDialog.setIndeterminate( true ) ;
			mProgressDialog.setMessage("please wait...");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show() ;
		}

		@Override
		protected Object[] doInBackground( String... params )
		{
			// your network operation
			User user = Constants.getCurrentUser(tagboxActivity);
			return UserAPI.requestToJoin(user.getEmail(), params[0]);
		}
	}
}