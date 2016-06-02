package com.woloactivityapp.constants;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

import com.woloactivityapp.utils.Utils;

public class ContactInfo {

	public static String ACTION_CONTACT_CHANGED = "com.contactnotes.intent.action.ContactChanged";

	//private static String CUSTOM_NOTE_TYPE = "vnd.android.cursor.item/customnote";

	public String contactID;
	public String displayName;
	public List<PhoneNumberInfo> phoneNumbers;
	public String photoUriString;
	public String contactNote;
	public String contactGroup = "";
	public String contactKeyword = "";
	public String contactEmail = "";
	public static List<ContactInfo> readContactList(Context context) {
		
		List<ContactInfo> contactList = new ArrayList<ContactInfo>();
		
		Uri uri = Contacts.CONTENT_URI;
		String[] fields = new String[] {
				Contacts._ID, 
				Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME,
				Utils.hasHoneycomb() ? Contacts.PHOTO_THUMBNAIL_URI : Contacts._ID,
		};
		String selection = (Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME) + "<>''";
		String sortOrder = (Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME) + " COLLATE LOCALIZED ASC";
		Cursor contactCursor = context.getContentResolver().query(uri, fields, selection, null, sortOrder);
		while (contactCursor.moveToNext()) {
			String contactID = contactCursor.getString(0);
			String displayName = contactCursor.getString(1);
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.contactID = contactID;
			contactInfo.displayName = displayName;
			
			String noteSelection = Email.CONTACT_ID + "=?";
			String[] noteSelectionArgs = new String[] { contactID};
			Cursor emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, noteSelection, noteSelectionArgs, null);
			String email = "";
			if (emailCursor == null)
				continue;
			if (emailCursor.moveToFirst())
				email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			emailCursor.close();
			contactInfo.contactEmail= email;
			
			Log.e("PhoneCallReceiver", "ID: " + contactID + ", Name: " + displayName + ", email: " + email);
			
			String[] phoneFields = new String[] { Phone.NUMBER, Phone.TYPE, Phone.LABEL };
			String phoneSelection = Data.CONTACT_ID + "=? AND " + Data.MIMETYPE + "=?";
			String[] phoneSelectionArgs = new String[] { contactID, Phone.CONTENT_ITEM_TYPE };
			Cursor phoneCursor = context.getContentResolver().query(Data.CONTENT_URI, phoneFields, phoneSelection, phoneSelectionArgs, null);
			if (phoneCursor.getCount() == 0) {
				phoneCursor.close();
				continue;
			}
			
			while (phoneCursor.moveToNext()) {
				PhoneNumberInfo phoneNumberInfo = contactInfo.new PhoneNumberInfo();
				int phoneNumberType = phoneCursor.getInt(1);
				switch (phoneNumberType) {
				case Phone.TYPE_HOME:
					phoneNumberInfo.phoneNumberLabel = "Home";
					break;
				case Phone.TYPE_MOBILE:
					phoneNumberInfo.phoneNumberLabel = "Mobile";
					break;
				case Phone.TYPE_WORK:
					phoneNumberInfo.phoneNumberLabel = "Work";
					break;
				case Phone.TYPE_FAX_WORK:
					phoneNumberInfo.phoneNumberLabel = "FaxWork";
					break;
				case Phone.TYPE_FAX_HOME:
					phoneNumberInfo.phoneNumberLabel = "FaxHome";
					break;
				case Phone.TYPE_PAGER:
					phoneNumberInfo.phoneNumberLabel = "Pager";
					break;
				case Phone.TYPE_OTHER:
					phoneNumberInfo.phoneNumberLabel = "Other";
					break;
				case Phone.TYPE_CALLBACK:
					phoneNumberInfo.phoneNumberLabel = "Callback";
					break;
				case Phone.TYPE_CAR:
					phoneNumberInfo.phoneNumberLabel = "Car";
					break;
				case Phone.TYPE_COMPANY_MAIN:
					phoneNumberInfo.phoneNumberLabel = "CompanyMain";
					break;
				case Phone.TYPE_ISDN:
					phoneNumberInfo.phoneNumberLabel = "ISDN";
					break;
				case Phone.TYPE_MAIN:
					phoneNumberInfo.phoneNumberLabel = "Main";
					break;
				case Phone.TYPE_OTHER_FAX:
					phoneNumberInfo.phoneNumberLabel = "OtherFax";
					break;
				case Phone.TYPE_RADIO:
					phoneNumberInfo.phoneNumberLabel = "Radio";
					break;
				case Phone.TYPE_TELEX:
					phoneNumberInfo.phoneNumberLabel = "Telex";
					break;
				case Phone.TYPE_TTY_TDD:
					phoneNumberInfo.phoneNumberLabel = "TTYTDD";
					break;
				case Phone.TYPE_WORK_MOBILE:
					phoneNumberInfo.phoneNumberLabel = "WorkMobile";
					break;
				case Phone.TYPE_WORK_PAGER:
					phoneNumberInfo.phoneNumberLabel = "WorkPager";
					break;
				case Phone.TYPE_ASSISTANT:
					phoneNumberInfo.phoneNumberLabel = "Assistant";
					break;
				case Phone.TYPE_MMS:
					phoneNumberInfo.phoneNumberLabel = "MMS";
					break;
				case Phone.TYPE_CUSTOM:
					phoneNumberInfo.phoneNumberLabel = phoneCursor.getString(2);
					break;
				default:
					phoneNumberInfo.phoneNumberLabel = "Unknown";
					break;
				}
				phoneNumberInfo.phoneNumberValue = phoneCursor.getString(0);
				contactInfo.phoneNumbers.add(phoneNumberInfo);
			}
			phoneCursor.close();
			
			contactList.add(contactInfo);
		}
		contactCursor.close();
		
		return contactList;
	}
	
public static ContactInfo readContactInfo(Context context, String contactID) {
		
		Uri uri = Contacts.CONTENT_URI;
		String[] fields = new String[] {
				Contacts._ID,
				Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME,
				Utils.hasHoneycomb() ? Contacts.PHOTO_THUMBNAIL_URI : Contacts._ID,
		};
		String selection = (Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME) + "<>''" + " AND " + Contacts.IN_VISIBLE_GROUP + "=1" + " AND " + Contacts._ID + "=" + contactID;
		String sortOrder = (Utils.hasHoneycomb() ? Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME) + " COLLATE LOCALIZED ASC";
		Cursor contactCursor = context.getContentResolver().query(uri, fields, selection, null, sortOrder);
		if (contactCursor.moveToFirst() == false)
			return null;
		String displayName = contactCursor.getString(1);
		String photoUriString = contactCursor.getString(2);
		
		String noteSelection = Email.CONTACT_ID + "=?";
		String[] noteSelectionArgs = new String[] { contactID};
		Cursor emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, noteSelection, noteSelectionArgs, null);
		String email = "";
		if (emailCursor.moveToFirst())
			email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			
		emailCursor.close();
		
		String[] phoneFields = new String[] { Phone.NUMBER, Phone.TYPE, Phone.LABEL };
		String phoneSelection = Data.CONTACT_ID + "=? AND " + Data.MIMETYPE + "=?";
		String[] phoneSelectionArgs = new String[] { contactID, Phone.CONTENT_ITEM_TYPE };
		Cursor phoneCursor = context.getContentResolver().query(Data.CONTENT_URI, phoneFields, phoneSelection, phoneSelectionArgs, null);
		if (phoneCursor.getCount() == 0)
			return null;
		
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.contactID = contactID;
		contactInfo.displayName = displayName;
		contactInfo.photoUriString = photoUriString;
		contactInfo.contactEmail= email;

		while (phoneCursor.moveToNext()) {
			PhoneNumberInfo phoneNumberInfo = contactInfo.new PhoneNumberInfo();
			int phoneNumberType = phoneCursor.getInt(1);
			switch (phoneNumberType) {
			case Phone.TYPE_HOME:
				phoneNumberInfo.phoneNumberLabel = "Home";
				break;
			case Phone.TYPE_MOBILE:
				phoneNumberInfo.phoneNumberLabel = "Mobile";
				break;
			case Phone.TYPE_WORK:
				phoneNumberInfo.phoneNumberLabel = "Work";
				break;
			case Phone.TYPE_FAX_WORK:
				phoneNumberInfo.phoneNumberLabel = "FaxWork";
				break;
			case Phone.TYPE_FAX_HOME:
				phoneNumberInfo.phoneNumberLabel = "FaxHome";
				break;
			case Phone.TYPE_PAGER:
				phoneNumberInfo.phoneNumberLabel = "Pager";
				break;
			case Phone.TYPE_OTHER:
				phoneNumberInfo.phoneNumberLabel = "Other";
				break;
			case Phone.TYPE_CALLBACK:
				phoneNumberInfo.phoneNumberLabel = "Callback";
				break;
			case Phone.TYPE_CAR:
				phoneNumberInfo.phoneNumberLabel = "Car";
				break;
			case Phone.TYPE_COMPANY_MAIN:
				phoneNumberInfo.phoneNumberLabel = "CompanyMain";
				break;
			case Phone.TYPE_ISDN:
				phoneNumberInfo.phoneNumberLabel = "ISDN";
				break;
			case Phone.TYPE_MAIN:
				phoneNumberInfo.phoneNumberLabel = "Main";
				break;
			case Phone.TYPE_OTHER_FAX:
				phoneNumberInfo.phoneNumberLabel = "OtherFax";
				break;
			case Phone.TYPE_RADIO:
				phoneNumberInfo.phoneNumberLabel = "Radio";
				break;
			case Phone.TYPE_TELEX:
				phoneNumberInfo.phoneNumberLabel = "Telex";
				break;
			case Phone.TYPE_TTY_TDD:
				phoneNumberInfo.phoneNumberLabel = "TTYTDD";
				break;
			case Phone.TYPE_WORK_MOBILE:
				phoneNumberInfo.phoneNumberLabel = "WorkMobile";
				break;
			case Phone.TYPE_WORK_PAGER:
				phoneNumberInfo.phoneNumberLabel = "WorkPager";
				break;
			case Phone.TYPE_ASSISTANT:
				phoneNumberInfo.phoneNumberLabel = "Assistant";
				break;
			case Phone.TYPE_MMS:
				phoneNumberInfo.phoneNumberLabel = "MMS";
				break;
			case Phone.TYPE_CUSTOM:
				phoneNumberInfo.phoneNumberLabel = phoneCursor.getString(2);
				break;
			default:
				phoneNumberInfo.phoneNumberLabel = "Unknown";
				break;
			}
			phoneNumberInfo.phoneNumberValue = phoneCursor.getString(0);
			contactInfo.phoneNumbers.add(phoneNumberInfo);
		}
		phoneCursor.close();
		contactCursor.close();
		
		return contactInfo;
	}

	public static void updateContactNote(Context context, String contactID, String contactNote) {

		String[] rawContactFields = new String[] { Data.RAW_CONTACT_ID, RawContacts.ACCOUNT_NAME, RawContacts.ACCOUNT_TYPE };
		String rawContactSelection = Data.CONTACT_ID + "=?";
		String[] rawContactSelectionArgs = new String[] { contactID };
		Cursor rawContactCursor = context.getContentResolver().query(Data.CONTENT_URI, rawContactFields, rawContactSelection, rawContactSelectionArgs, null);
		String rawContactID = "";
		String accountName = "";
		String accountType = "";
		if (rawContactCursor.moveToFirst()) {
			rawContactID = rawContactCursor.getString(0);
			accountName = rawContactCursor.getString(1);
			accountType = rawContactCursor.getString(2);
		}
		rawContactCursor.close();
		if (rawContactID.equals(""))
			return;
		
		Log.e("PhoneCallReceiver", "accountName: " + accountName + ", accountType: " + accountType);
		
		String[] noteFields = new String[] { Note.NOTE };
		String noteSelection = Data.RAW_CONTACT_ID + "=? AND " + Data.MIMETYPE + "=?";
		String[] noteSelectionArgs = new String[] { rawContactID, Note.CONTENT_ITEM_TYPE };
		Cursor noteCursor = context.getContentResolver().query(Data.CONTENT_URI, noteFields, noteSelection, noteSelectionArgs, null);
		boolean noteIsExist = noteCursor.getCount() > 0 ? true : false;
		noteCursor.close();
		
		if (noteIsExist == false) {
			ContentValues values = new ContentValues();
			values.put(Data.RAW_CONTACT_ID, rawContactID);
			values.put(Data.MIMETYPE, Note.CONTENT_ITEM_TYPE);
			values.put(Note.NOTE, contactNote);
			context.getContentResolver().insert(Data.CONTENT_URI, values);
		} else {
			ContentValues values = new ContentValues();
			values.put(Note.NOTE, contactNote);
			context.getContentResolver().update(Data.CONTENT_URI, values, noteSelection, noteSelectionArgs);
		}
		AccountManager accountMgr = (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);
		Account[] accounts = accountMgr.getAccounts();
		Account account = null;
		for (int i = 0; i < accounts.length; i ++) {
			if (accounts[i].name.equals(accountName) &&
				accounts[i].type.equals(accountType)) {
				account = accounts[i];
				break;
			}
		}
		Bundle bundle = new Bundle();
		bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
		bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
		bundle.putBoolean(ContentResolver.SYNC_EXTRAS_IGNORE_SETTINGS, true);
		ContentResolver.requestSync(account, ContactsContract.AUTHORITY, bundle);
		
		Intent intent = new Intent();
		intent.setAction(ACTION_CONTACT_CHANGED);
		context.sendBroadcast(intent);
	}
	
	public static String getNoteFromPhoneNumber(Context context, String phoneNumber) {
		
		String[] phoneFields = new String[] { Data.CONTACT_ID, Phone.NUMBER };
		String phoneSelection = Data.MIMETYPE + "=?";
		String[] phoneSelectionArgs = new String[] { Phone.CONTENT_ITEM_TYPE };
		Cursor phoneCursor = context.getContentResolver().query(Data.CONTENT_URI, phoneFields, phoneSelection, phoneSelectionArgs, null);
		String contactID = "";
		phoneNumber = numbersStringWithPhoneNumber(phoneNumber);
		while (phoneCursor.moveToNext()) {
			String rawDbPhoneNumber = phoneCursor.getString(1);
			String dbPhoneNumber = numbersStringWithPhoneNumber(rawDbPhoneNumber);
			Log.e("PhoneCallReceiver", "Converting '" + rawDbPhoneNumber + "' -> '" + dbPhoneNumber + "'");
			if (phoneNumber.equals(dbPhoneNumber)) {
				contactID = phoneCursor.getString(0);
				break;
			}
		}
		phoneCursor.close();
		if (contactID.equals(""))
			return "";
		
		String[] noteFields = new String[] { Note.NOTE };
		String noteSelection = Data.CONTACT_ID + "=? AND " + Data.MIMETYPE + "=?";
		String[] noteSelectionArgs = new String[] { contactID, Note.CONTENT_ITEM_TYPE };
		Cursor noteCursor = context.getContentResolver().query(Data.CONTENT_URI, noteFields, noteSelection, noteSelectionArgs, null);
		String contactNote = "";
		if (noteCursor.moveToFirst())
			contactNote = noteCursor.getString(0);
		noteCursor.close();
		
		return contactNote;
	}
	public static ContactInfo getContactFromEmail(Context context, String email) {
		
		String noteSelection = ContactsContract.CommonDataKinds.Email.DATA + "=?";
		String[] noteSelectionArgs = new String[] { email };
		Cursor emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, noteSelection, noteSelectionArgs, null);
		emailCursor.moveToFirst();
		while(emailCursor.moveToNext()) {
			String contactId = emailCursor.getString(emailCursor.getColumnIndex(Email.CONTACT_ID));
			emailCursor.close();
			return readContactInfo(context, contactId);
		}
		emailCursor.close();
		return null;
	}
	private static String numbersStringWithPhoneNumber(String phoneNumber) {
		String numbersString = "";
		for (int i = 0; i < phoneNumber.length(); i ++) {
			char ch = phoneNumber.charAt(i);
			if (ch >= '0' && ch <= '9')
				numbersString = numbersString + ch;
		}
		return numbersString;
	}

	private ContactInfo() {
		contactID = "";
		displayName = "";
		phoneNumbers = new ArrayList<PhoneNumberInfo>();
		contactNote = "";
		photoUriString = "";
	}
	
	public String toString() {
		String contactString = "{ContactID: " + contactID + ", DisplayName: " + displayName + ", PhotoUri: " + photoUriString + ", Note: " + contactNote + ", {";
		for (int i = 0; i < phoneNumbers.size(); i ++) {
			PhoneNumberInfo phoneNumberInfo = phoneNumbers.get(i);
			if (i > 0)
				contactString += ", ";
			contactString += "{Type: " + phoneNumberInfo.phoneNumberLabel + ", Number: " + phoneNumberInfo.phoneNumberValue + "}";
		}
		contactString += "}}";
		
		return contactString;
	}
	
	public class PhoneNumberInfo {
		public String phoneNumberLabel;
		public String phoneNumberValue;
		
		private PhoneNumberInfo() {
			phoneNumberLabel = "";
			phoneNumberValue = "";
		}
	}

}
