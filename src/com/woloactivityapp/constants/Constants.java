package com.woloactivityapp.constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences;

public class Constants {
	public final static int 			MSG_SUCCESS = 0;
	public final static int 			MSG_FAIL = 1;
    public static final int 			MSG_TAKE_PHOTO = 4;
    public static final int 			MSG_CHOOSE_PHOTO = 5;
	public static float 				fDensity = 1.0f;
	public final static int 			MSG_SEND = 10;
	public final static int 			MSG_RECEIVE = 11;
	public static String				FLURRY_API_KEY = "Y36M7ZVT5HXQ25JG3D4C";
	public static String				API_KEY = "AIzaSyA3v9hLZhFU7tEVyosM7eGC2CuZfZ6x1dk";
	public static String 				GOOGLE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/xml";
	
	public static boolean validateEmail( String email )
    {
        Pattern pattern ;
        Matcher matcher ;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" ;
        pattern = Pattern.compile( EMAIL_PATTERN ) ;
        matcher = pattern.matcher( email ) ;
        return matcher.matches() ;
    }
	public static void setStringValue(Context context, String key, String val) {
	    SharedPreferences.Editor editor = context.getSharedPreferences("com.woloactivityapp.main", 0).edit();
	    editor.putString(key, val);
	    editor.commit();
	}
	
	public static String getStringValue(Context context, String key) {
	    SharedPreferences pref = context.getSharedPreferences("com.woloactivityapp.main", 0);
	    return pref.getString(key, "");
	}
	public static void setLongValue(Context context, String key, long val) {
	    SharedPreferences.Editor editor = context.getSharedPreferences("com.woloactivityapp.main", 0).edit();
	    editor.putLong(key, val);
	    editor.commit();
	    
	}
	
	public static long getLongValue(Context context, String key) {
	    SharedPreferences pref = context.getSharedPreferences("com.woloactivityapp.main", 0);
	    return pref.getLong(key, 0);
	}
	public static void setFloatValue(Context context, String key, float val) {
	    SharedPreferences.Editor editor = context.getSharedPreferences("com.woloactivityapp.main", 0).edit();
	    editor.putFloat(key, val);
	    editor.commit();
	}
	public static double getFloatValue(Context context, String key) {
	    SharedPreferences pref = context.getSharedPreferences("com.woloactivityapp.main", 0);
	    return pref.getFloat(key, 5.0f);
	}
	public static final void setCurrentUser(Context context, User user)
	{
		setStringValue(context, "email", user.getEmail());
		setStringValue(context, "firstname", user.getFirstname());
		setStringValue(context, "lastname", user.getLastname());
		setStringValue(context, "age", user.getAge() + "");
		setStringValue(context, "aboutme", user.getAboutme());
		setStringValue(context, "latitude", user.getLatitude() + "");
		setStringValue(context, "longitude", user.getLongitude() + "");
		setStringValue(context, "phone_number", user.getPhoneNumber());
	}
	public static final void setCurrentUser(Context context, String email, String firstname, String lastname, String age, String aboutme, String latitude, String longitude, String phone_number)
	{
		setStringValue(context, "email", email);
		setStringValue(context, "firstname", firstname);
		setStringValue(context, "lastname", lastname);
		setStringValue(context, "age", age);
		setStringValue(context, "aboutme", aboutme);
		setStringValue(context, "latitude", latitude);
		setStringValue(context, "longitude", longitude);
		setStringValue(context, "phone_number", phone_number);
	}
	
	
	public static final User getCurrentUser(Context context)
	{
		User user = new User();
		user.setEmail(getStringValue(context, "email"));
		user.setFirstname(getStringValue(context, "firstname")); 
		user.setLastname(getStringValue(context, "lastname")); 
		String age = getStringValue(context, "age");
		if (age.length() > 0)
			user.setAge(Integer.parseInt(age)); 
		user.setPhoneNumber(getStringValue(context, "phone_number")); 
		user.setAboutme(getStringValue(context, "aboutme")); 
		String lat = getStringValue(context, "latitude");
		String lng = getStringValue(context, "longitude");
		if (lat.length() > 0)
			user.setLatitude(Double.parseDouble(lat)); 
		if (lng.length() > 0)
			user.setLongitude(Double.parseDouble(lng)); 
		
		return user;
	}
	private static double rad(double d)
	{
		return d * Math.PI / 180.0;
	}
	 
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
		
		double radiusEarth = 6371.0 / 1.609344; // in miles
	  
		double dlat = rad(lat1 - lat2);
		double dlon = rad(lng1 - lng2);
	  
		double a = Math.sin(dlat/2.0) * Math.sin(dlat/2.0) + Math.cos(rad(lat1)) * Math.cos(rad(lat2)) * Math.sin(dlon/2.0) * Math.sin(dlon/2.0);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
		double s = radiusEarth * c;
		s = (double)((int)(s * 10)) / 10;
		return s;
	}
}
