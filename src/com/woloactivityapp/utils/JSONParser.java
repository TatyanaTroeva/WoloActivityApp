package com.woloactivityapp.utils;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.woloactivityapp.constants.Contest;
import com.woloactivityapp.constants.User;

public class JSONParser
{
	public static final String[] parseUserInfoResponse(Context context, String res )
	{
		String[] ret = new String[9] ;
		try
		{
			final JSONObject json = new JSONObject( res ) ;
			ret[0] = json.getString( "message" ) ;
			ret[1] = json.getString( "email" ) ;
			ret[2] = json.getString( "firstname" ) ;
			ret[3] = json.getString( "lastname" ) ;
			ret[4] = json.getString( "age" ) ;
			ret[5] = json.getString( "aboutme" ) ;
			ret[6] = json.getString( "latitude" ) ;
			ret[7] = json.getString( "longitude" ) ;
			ret[8] = json.getString( "phone_number" ) ;
		}
		catch( Exception e )
		{
			Log.e( "Alex", "Parsing Error : " + e.getMessage() ) ;
		}
		return ret ;
	}
	public static final ArrayList<Contest> parseContestListInfo( String res )
	{
		ArrayList<Contest> ret = new ArrayList<Contest>() ;
		try
		{
			JSONObject json = new JSONObject( res ) ;
			JSONArray array = json.getJSONArray( "contests" ) ;
			for ( int i = 0; i < array.length(); i++ )
			{
				JSONObject cell = array.getJSONObject( i ) ;

				String name 		= cell.getString( "name" ) ;
				String description 	= cell.getString( "description" ) ;
				String when 		= cell.getString( "when" ) ;
				String where 	= cell.getString( "where" ) ;
				int maxParticipants = cell.getInt( "maxParticipants" ) ;
				int minAge 			= cell.getInt( "minAge" ) ;
				int joinCategory 	= cell.getInt( "joinCategory" ) ;
				String creator 	= cell.getString( "creator" ) ;
				String joinedUsers 	= cell.getString( "joinedUsers" ) ;
				String requestedUsers 	= cell.getString( "requestedUsers" ) ;
				double latitude 			= cell.getDouble( "latitude" ) ;
				double longitude 	= cell.getDouble( "longitude" ) ;
				long whenVal = Long.parseLong(when);
				Date whenDate = new Date();
				whenDate = new Date(whenDate.getTime() + whenVal);
				Contest contest = new Contest( name, description, whenDate, where, maxParticipants, minAge, creator, joinCategory, joinedUsers, requestedUsers, latitude, longitude) ;
				
				ret.add( contest ) ;
			}
		}
		catch ( JSONException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
		return ret ;
	}
	public static final Contest parseContestInfo( String res )
	{
		
		try
		{
			JSONObject json = new JSONObject( res ) ;
			JSONObject cell = json.getJSONObject( "contest" ) ;
			String name 		= cell.getString( "name" ) ;
			String description 	= cell.getString( "description" ) ;
			String when 		= cell.getString( "when" ) ;
			String where 	= cell.getString( "where" ) ;
			int maxParticipants = cell.getInt( "maxParticipants" ) ;
			int minAge 			= cell.getInt( "minAge" ) ;
			int joinCategory 	= cell.getInt( "joinCategory" ) ;
			String creator 	= cell.getString( "creator" ) ;
			String joinedUsers 	= cell.getString( "joinedUsers" ) ;
			String requestedUsers 	= cell.getString( "requestedUsers" ) ;
			double latitude 			= cell.getDouble( "latitude" ) ;
			double longitude 	= cell.getDouble( "longitude" ) ;
			
			long whenVal = Long.parseLong(when);
			Date whenDate = new Date();
			whenDate = new Date(whenDate.getTime() + whenVal);
			
			Contest contest = new Contest( name, description, whenDate, where, maxParticipants, minAge, creator, joinCategory, joinedUsers, requestedUsers, latitude, longitude) ;
			return contest;
		}
		catch ( JSONException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
		return null ;
	}
	public static final ArrayList<User> parseUserListInfo( String res )
	{
		ArrayList<User> ret = new ArrayList<User>() ;
		try
		{
			JSONObject json = new JSONObject( res ) ;
			JSONArray array = json.getJSONArray( "users" ) ;
			for ( int i = 0; i < array.length(); i++ )
			{
				JSONObject cell = array.getJSONObject( i ) ;
				String email = cell.getString( "email" ) ;
				String firstname = cell.getString( "firstname" ) ;
				String lastname = cell.getString( "lastname" ) ;
				int age = cell.getInt("age");
				String aboutme = cell.getString( "aboutme" ) ;
				double latitude = cell.getDouble("latitude" ) ;
				double longitude = cell.getDouble("longitude" ) ;
				String phone_number = cell.getString("phone_number");
				User user = new User() ;
				user.setEmail(email);
				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setAge(age);
				user.setAboutme(aboutme);
				user.setLatitude(latitude);
				user.setLongitude(longitude);
				user.setPhoneNumber(phone_number);
				ret.add( user ) ;
			}
		}
		catch ( JSONException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
		return ret ;
	}
}
