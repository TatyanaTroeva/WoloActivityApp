package com.woloactivityapp.views;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.woloactivityapp.adapters.ContactListItemAdapter;
import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.connection.UserAPI;
import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.ContactInfo;
import com.woloactivityapp.constants.User;
import com.woloactivityapp.main.NavigationBaseActivity;
import com.woloactivityapp.main.R;
import com.woloactivityapp.utils.JSONParser;

public class InviteFriendsActivity extends NavigationBaseActivity {
    /** Called when the activity is first created. */
	private CustomTextView 					titleCustomTextView_ = null;
	private Button							backButton_ = null;
	private ListView						friendsListView_ = null;
	private ListView						inviteListView_ = null;
	private ArrayList<User> 				userList_ = null;
	private List<ContactInfo>				contactList = null;
	private float						    screenScale_ = 0.0f;
	private ArrayList<ContactInfo> 			friendsList = new ArrayList<ContactInfo>();
	private	ArrayList<ContactInfo> 			inviteList = new ArrayList<ContactInfo>();
	public static ArrayList<ContactInfo> 	inviteSelList = new ArrayList<ContactInfo>();
	public static InviteFriendsActivity     inviteFriendActivity = null;
	private Button							sendButton_ = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setLayoutId(this,R.layout.activity_invitefriend);
        super.onCreate(savedInstanceState);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        setListener();
        inviteFriendActivity = this;
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

		sendButton_ = (Button) findViewById(R.id.sendButton);
		
		friendsListView_ = (ListView) findViewById(R.id.friendsListView);
		inviteListView_ = (ListView) findViewById(R.id.inviteListView);
    } 
    public void setListener(){
    	backButton_.setOnClickListener(this);
    	sendButton_.setOnClickListener(this);
    }
    
    public void onResume() {
    	super.onResume();

    }
    public void initData(){
//    	NotificationManager mNM = (NotificationManager) ProfileActivity.profileActivity.getApplicationContext().getSystemService( Context.NOTIFICATION_SERVICE ) ;
//		android.app.Notification notification = new android.app.Notification() ;
//		notification.flags = android.app.Notification.FLAG_ONGOING_EVENT ;
//		notification.icon = R.drawable.ic_launcher ;
//		notification.tickerText = "WoloActivity Notification" ;
//		notification.vibrate = new long[] { 100, 250, 100, 500 } ;
//		// The PendingIntent to launch our activity if the user selects this notification
//        PendingIntent contentIntent = PendingIntent.getActivity( ProfileActivity.profileActivity.getApplicationContext(), 0, new Intent( ProfileActivity.profileActivity.getApplicationContext(), BottomActivity.class ).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ), PendingIntent.FLAG_UPDATE_CURRENT ) ;
//        // Set the info for the views that show in the notification panel.
//        notification.setLatestEventInfo( ProfileActivity.profileActivity.getApplicationContext(), "WoloActivity", "test", contentIntent ) ;
//        // Send the notification.
//        // We use a layout id because it is a unique number.  We use it later to cancel.
//        mNM.notify( R.drawable.ic_launcher, notification ) ;
    	if (CreateActivity.emailList != null)
    		CreateActivity.emailList.clear();
    	refreshContactList();
    }
	private void refreshContactList() {
		GetAllUsersTask getAllUsersTask = new GetAllUsersTask();
		getAllUsersTask.execute();

	}
    public void onClick(View view) {
		super.onClick(view);
		int viewId = view.getId();
		switch (viewId) {
		case R.id.backButton: {
			finish();
		}
		    break;
		case R.id.sendButton: {
			String phoneNumber = "";
			String split = ";";
			String val = android.os.Build.VERSION.RELEASE;
			if (val.startsWith("4"))
				split = ",";
			if (inviteSelList != null) {
				for (int i=0; i < inviteSelList.size(); i++) {
					ContactInfo contactInfo = inviteSelList.get(i);
					String itemNumber = "";
					if (contactInfo.phoneNumbers != null && contactInfo.phoneNumbers.size() > 0) {
						itemNumber = contactInfo.phoneNumbers.get(0).phoneNumberValue;
			        }
					if (i == 0)
						phoneNumber = itemNumber;
					else
						phoneNumber += split + itemNumber;
				}
			}
			
			if (phoneNumber.length() > 0) {
				Uri uri = Uri.parse("smsto:" + phoneNumber); 
				Intent it = new Intent(android.content.Intent.ACTION_SENDTO, uri);  
				it.putExtra("address", phoneNumber);
				it.putExtra("sms_body", "Would you rather fight 100 duck sized horses or 1 horse sized duck? Let’s put down our phones, listen to some warm music, and schedule a tag box activity to find out! https://www.facebook.com/LetsWolo");   
	        	it.putExtra("body", "Would you rather fight 100 duck sized horses or 1 horse sized duck? Let’s put down our phones, listen to some warm music, and schedule a tag box activity to find out! https://www.facebook.com/LetsWolo");   
	        	startActivity(it);
			}
			
	        	
		}
		    break;
		default:
			break;
		}
    }
    public boolean containsList(ContactInfo info) {
    	if (userList_ == null)
    		return false;
    	for (int i = 0;i < userList_.size();i++) {
    		if (userList_.get(i).getEmail().equals(info.contactEmail))
    			return true;
    	}
    	return false;
    }
    private class GetAllUsersTask extends AsyncTask<String, Void, Object[]>
   	{
   		ProgressDialog mProgressDialog ;
   		@Override
   		protected void onPostExecute( Object[] result )
   		{
   			User currentUser = Constants.getCurrentUser(InviteFriendsActivity.this);
   			mProgressDialog.dismiss() ;
   			if (userList_ == null || userList_.size() == 0) {
   				inviteListView_.setAdapter(new ContactListItemAdapter(InviteFriendsActivity.this, contactList, false));
   			} else {
   				
   				for (int i=0;i<contactList.size();i++) {
   					ContactInfo info = contactList.get(i);
   					String itemNumber = "";
					if (info.phoneNumbers != null && info.phoneNumbers.size() > 0) {
						itemNumber = info.phoneNumbers.get(0).phoneNumberValue;
			        }
   					if (containsList(info) && !currentUser.getPhoneNumber().equals(itemNumber)) {
   						friendsList.add(info);
   					} else {
   						inviteList.add(info);
   					}
   				}
   				
   				inviteListView_.setAdapter(new ContactListItemAdapter(InviteFriendsActivity.this, inviteList, false));
   				friendsListView_.setAdapter(new ContactListItemAdapter(InviteFriendsActivity.this, friendsList, true));
   			}
   		}

   		@Override
   		protected void onPreExecute()
   		{
   			mProgressDialog = new ProgressDialog( InviteFriendsActivity.this) ;
   			mProgressDialog.setIndeterminate( true ) ;
   			mProgressDialog.setMessage("please wait...");
   			mProgressDialog.setCancelable(false);
   			mProgressDialog.show() ;
   		}

   		@Override
   		protected Object[] doInBackground( String... params )
   		{
   			// your network operation
   			Object[] list = UserAPI.getAllUsers();
   			if (list != null && list.length > 0) {
   				userList_ = JSONParser.parseUserListInfo((String)list[1]);
   			}
   			contactList = ContactInfo.readContactList(InviteFriendsActivity.this);
   			return null;
   		}
   	}
	
}