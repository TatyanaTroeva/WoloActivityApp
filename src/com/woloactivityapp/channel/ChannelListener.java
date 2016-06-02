package com.woloactivityapp.channel;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.main.BottomActivity;
import com.woloactivityapp.main.R;
import com.woloactivityapp.views.MessagesActivity;
import com.woloactivityapp.views.ProfileActivity;

public class ChannelListener implements ChannelService{
    /**
     * Called when we open a connection to the server
     */
	@Override
    public void onOpen() {
        System.out.println("Channel Opened!");
        System.out.println("This is the \"Defualt Class\" You Should Realy Implement Your Own Version of \"ChannelService\" :-)");
    }

	/**
     * Called when we receive a message from the server
     */
    @Override
    public void onMessage(String message) {
        System.out.println("Message: " + message);
        System.out.println("This is the \"Defualt Class\" You Should Realy Implement Your Own Version of \"ChannelService\" :-)");
        if (message != null) {
        	try {
        		JSONObject json = new JSONObject(message);
            	if (json.getInt("category") == 0) {
            		if (MessagesActivity.messagesActivity_ != null) {
                		Message msg = new Message();
                		msg.what = Constants.MSG_RECEIVE;
                		msg.obj = json.getString("content");
                		MessagesActivity.messagesActivity_.handler.sendMessage(msg);
                	}
            	} else if (json.getInt("category") == 1) {
            		NotificationManager nm = (NotificationManager) ProfileActivity.profileActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        			Notification notification = new Notification(R.drawable.ic_launcher,
        					"WoloActivity Notification", System.currentTimeMillis());
        			notification.vibrate = new long[] { 100, 250, 100, 500 } ;
        			Intent intent = new Intent(ProfileActivity.profileActivity.getApplicationContext(), BottomActivity.class).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        			notification.setLatestEventInfo(ProfileActivity.profileActivity, "WoloActivity Notification",
        							"Please accept user's request!" , PendingIntent.getActivity(
        									ProfileActivity.profileActivity.getBaseContext(), 0, intent,
        							PendingIntent.FLAG_UPDATE_CURRENT ));
        			notification.defaults |= Notification.DEFAULT_SOUND;
        			notification.flags |= Notification.FLAG_AUTO_CANCEL;
        			nm.notify(123456, notification);
            	}else if (json.getInt("category") == 2) {
            		
            		NotificationManager nm = (NotificationManager) ProfileActivity.profileActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        			Notification notification = new Notification(R.drawable.ic_launcher,
        					"WoloActivity Notification", System.currentTimeMillis());
        			notification.vibrate = new long[] { 100, 250, 100, 500 } ;
        			Intent intent = new Intent(ProfileActivity.profileActivity.getApplicationContext(), BottomActivity.class).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        			notification.setLatestEventInfo(ProfileActivity.profileActivity, "WoloActivity Notification",
        					json.getString("content") , PendingIntent.getActivity(
        									ProfileActivity.profileActivity.getBaseContext(), 0, intent,
        							PendingIntent.FLAG_UPDATE_CURRENT ));
        			notification.defaults |= Notification.DEFAULT_SOUND;
        			notification.flags |= Notification.FLAG_AUTO_CANCEL;
        			nm.notify(12345, notification);
            	}
            	
        	} catch (Exception e){}
        		
        }
        
    }
    
    /**
     * Called when we close the channel to the server
     */
    @Override
    public void onClose() {
        System.out.println("Channel Closed!");
        System.out.println("This is the \"Defualt Class\" You Should Realy Implement Your Own Version of \"ChannelService\" :-)");
        BottomActivity.gInstance.connectFunc();
    }

    /**
     * Called when we receive an error message from the server
     */
	@Override
	public void onError(Integer errorCode, String description) {
		System.out.println("Channel Error");
		System.out.println("Error Occured: " + description + " Error Code:" + errorCode);
		System.out.println("This is the \"Defualt Class\" You Should Realy Implement Your Own Version of \"ChannelService\" :-)");
		BottomActivity.gInstance.connectFunc();
	}
};
