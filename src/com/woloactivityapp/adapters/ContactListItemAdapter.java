package com.woloactivityapp.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.woloactivityapp.constants.ContactInfo;
import com.woloactivityapp.main.R;
import com.woloactivityapp.views.CreateActivity;
import com.woloactivityapp.views.InviteFriendsActivity;

public class ContactListItemAdapter extends BaseAdapter {

	Context context;
	List<ContactInfo> contactList;
	boolean flag = true;

	public ContactListItemAdapter(Context context, List<ContactInfo> contactList, boolean flag) {
		this.context = context;
		this.contactList = contactList;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return this.contactList.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < 0 || position >= this.contactList.size())
			return null;
		else
			return this.contactList.get(position);
	}

	@Override
	public long getItemId(int position) {
		if (position < 0 || position >= this.contactList.size())
			return -1;
		ContactInfo contactInfo = this.contactList.get(position);
		return Long.parseLong(contactInfo.contactID);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position < 0 || position >= this.contactList.size())
			return null;
		
		LayoutInflater inflater = LayoutInflater.from(this.context);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.invitefrienditemlayout, null);
		
		final ContactInfo contactInfo = this.contactList.get(position);
        final String displayName = contactInfo.displayName;
        String phoneNumber = "";
        if (contactInfo.phoneNumbers != null && contactInfo.phoneNumbers.size() > 0) {
        	phoneNumber = contactInfo.phoneNumbers.get(0).phoneNumberValue;
        }

        TextView nameTextView = (TextView)convertView.findViewById(R.id.itemNameCustomTextView);
        TextView phoneTextView = (TextView)convertView.findViewById(R.id.itemPhoneCustomTextView);
    	final CheckBox selCheckBox = (CheckBox) convertView.findViewById(R.id.selCheckBox);
        nameTextView.setText(displayName);
        phoneTextView.setText(phoneNumber);
        if (flag == false) {
            if (InviteFriendsActivity.inviteSelList != null && InviteFriendsActivity.inviteSelList.contains(contactInfo)) {
            	selCheckBox.setChecked(true);
            } else {
            	selCheckBox.setChecked(false);
            }
        }

        selCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if (flag == false) {
					if (InviteFriendsActivity.inviteSelList == null) {
						InviteFriendsActivity.inviteSelList = new ArrayList<ContactInfo>();
					}
					if (selCheckBox.isChecked()) {
						InviteFriendsActivity.inviteSelList.add(contactInfo);
			        } else {
			        	InviteFriendsActivity.inviteSelList.remove(contactInfo);
			        }
					
				} else {
					if (CreateActivity.emailList == null) {
						CreateActivity.emailList = new ArrayList<String>();
					}
					if (selCheckBox.isChecked()) {
						CreateActivity.emailList.add(contactInfo.contactEmail);
					} else {
						CreateActivity.emailList.remove(contactInfo.contactEmail);
					}
				}
			}
		});
        
       
		return convertView;
	}

}
