package com.woloactivityapp.connection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;

import com.woloactivityapp.views.ProfileActivity;

public class UserAPI
{
	public static final String APP_ENGINE_SERVER_URL					= "http://woloapp.appspot.com" ;
	
	public static final String RESPONSE_FAIL							= "FAIL" ;
	public static final String RESPONSE_OK								= "OK" ;
	
	public static final String GLOBAL_SERVICE_URL 						= APP_ENGINE_SERVER_URL + "/global" ;
	public static final String CHAT_SERVICE_URL 						= APP_ENGINE_SERVER_URL + "/chatservice" ;
	public static final String USER_SERVICE_URL 						= APP_ENGINE_SERVER_URL + "/userservice" ;
	public static final String CONTEST_SERVICE_URL						= APP_ENGINE_SERVER_URL + "/contestservice" ;
	
	public static final String ACTION_GLOBAL_DELETE_ALLDATA 			= "deleteall";
	
	public static final String ACTION_USER_LOGIN 						= "action_user_login";
	public static final String ACTION_USER_REGISTER 					= "action_user_register";
	public static final String ACTION_USER_GETPHOTO 					= "action_user_getphoto";
	public static final String ACTION_USER_GETPROFILE 					= "action_user_getprofile";
	public static final String ACTION_USER_UPDATEPROFILE 				= "action_user_updateprofile";
	public static final String ACTION_USER_UPDATELOCATION 				= "action_user_updatelocation";
	public static final String ACTION_USER_PASSWORDRECOVER 				= "action_user_passwordrecover";
	public static final String ACTION_USER_GETUSERS 					= "action_user_getusers";
	public static final String ACTION_USER_GETALLUSERS 					= "action_user_getallusers";
	public static final String ACTION_USER_UPDATEEMAIL 					= "action_user_updateemail";
	
	public static final String ACTION_CONTEST_CREATENEW 				= "action_contest_createnew";
	public static final String ACTION_CONTEST_EDIT						= "action_contest_edit";
	public static final String ACTION_CONTEST_GETCONTESTS 				= "action_contest_getcontests";
	public static final String ACTION_CONTEST_CLICKTOJOIN 				= "action_contest_clicktojoin";
	public static final String ACTION_CONTEST_REQUESTTOJOIN 			= "action_contest_requesttojoin";
	public static final String ACTION_CONTEST_ACCEPTTOJOIN 				= "action_contest_accepttojoin";
	public static final String ACTION_CONTEST_REJECTTOJOIN 				= "action_contest_rejecttojoin";
	public static final String ACTION_CONTEST_GETJOINEDCONTESTS 		= "action_contest_getjoinedcontests";
	public static final String ACTION_CONTEST_GETREQUESTEDCONTESTS 		= "action_contest_getrequstedcontests";
	public static final String ACTION_CONTEST_GETCREATEDCONTESTS 		= "action_contest_getcreatedcontests";
	public static final String ACTION_CONTEST_LEAVE 					= "action_contest_leave";
	public static final String ACTION_CONTEST_DELETE					= "action_contest_delete";
	
	public static String[] signup( String firstname, String lastname, String password, String email, String phone_number )
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_REGISTER ) ;
		httpPost.addHeader( "firstname", firstname ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "password", password ) ;
		httpPost.addHeader( "lastname", lastname ) ;
		httpPost.addHeader( "phone_number", phone_number ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			
			if ( success.equals("1"))
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result } ;
			}
			else
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result } ;
			}
		}
		catch ( Exception e )
		{
			
		}
		return null ;
	}
	public static Object[] sendMessage(String name, String email, String message )
	{
		HttpPost httpPost = new HttpPost( CHAT_SERVICE_URL ) ;
		
		httpPost.addHeader( "name", name ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "message", message ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			
		}
		catch ( IOException e )
		{
			Log.e( "Alex", "Call Http Error : " + e.getMessage() ) ;
		}
		return null ;
	}
	public static Object[] login( String email, String password )
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_LOGIN ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "password", password ) ;
		httpPost.addHeader( "platform", "android" ) ;
		httpPost.addHeader( "devicetoken", "" ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
	        
				return new Object[] { RESPONSE_OK, result} ;
			}
			else 
			{
				String result = new JSONObject(ret).getString("result");
		        
				return new Object[] { RESPONSE_FAIL, result} ;
			}
		}
		catch ( Exception e )
		{
			Log.e("ERROR", e.toString());
		}
		return null ;
	}
	public static Object[] userinfo( String email )
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_GETPROFILE ) ;
		httpPost.addHeader( "email", email ) ;
		
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if (success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new Object[] { RESPONSE_OK, result} ;
			}
		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static String[] recoverPassword( String email )
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_PASSWORDRECOVER ) ;
		httpPost.addHeader( "email", email ) ;
		
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result } ;
			}
		}
		catch (Exception e )
		{
		}
		return null ;
	}
	public static byte[] getUserPhoto( String email )
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_GETPHOTO ) ;
		httpPost.addHeader( "email", email ) ;
		
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity() ;
			if ( response.getStatusLine().getStatusCode() == HttpStatus.SC_OK )
			{
				return EntityUtils.toByteArray( entity ) ;
			}
		}
		catch ( IOException e )
		{
		}
		return null ;
	}

	public static boolean updateUserProfile( String email, String firstname, String lastname, String age, String aboutme, String latitude, String longitude, String path)
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_UPDATEPROFILE ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "firstname", firstname ) ;
		httpPost.addHeader( "lastname", lastname ) ;
		httpPost.addHeader( "age", age ) ;
		httpPost.addHeader( "aboutme", aboutme ) ;
		httpPost.addHeader( "latitude", latitude ) ;
		httpPost.addHeader( "longitude", longitude ) ;
		File file = new File(path);
		ContentBody cBody = new FileBody(file);
		try
		{
//			Bitmap bitmap = null ;
//			if ( path != null )
//			{
//				bitmap = getRegionBitmapFromSdcard(path);
//				if ( bitmap.getWidth() > 600 || bitmap.getHeight() > 400 )
//				{
//					bitmap = Bitmap.createScaledBitmap( bitmap, 600, 400, true ) ;
//				}
//				ByteArrayOutputStream baos = new ByteArrayOutputStream() ; 
//				bitmap.compress( Bitmap.CompressFormat.PNG, 80, baos ) ;
//				
//				httpPost.setEntity( new ByteArrayEntity( baos.toByteArray() ) ) ;

//			}
			MultipartEntity multipartContent = new MultipartEntity();
			multipartContent.addPart("file", cBody);
			httpPost.setEntity(multipartContent);
			
			HttpResponse response = HttpManager.execute( httpPost ) ;
			
//			if ( bitmap != null )
//			{
//				bitmap.recycle() ; bitmap = null ;
//			}
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				return true ;
			}

		}
		catch ( Exception e )
		{

		}
		return false ;
	}
	public static boolean updateUserEmail( String email, String newemail, String newpassword, String newphonenumber)
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_UPDATEEMAIL ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "newemail", newemail ) ;
		httpPost.addHeader( "newpassword", newpassword ) ;
		httpPost.addHeader( "newphonenumber", newphonenumber ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				return true ;
			}
		}
		catch ( Exception e ) { }
		return false ;
	}
	public static boolean updateUserLocation( String email, String latitude, String longitude)
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_UPDATELOCATION ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "latitude", latitude ) ;
		httpPost.addHeader( "longitude", longitude ) ;
		
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				return true ;
			}

		}
		catch ( Exception e )
		{
		}
		return false ;
	}
	public static Object[] getContests( String email, double radius)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_GETCONTESTS) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "radius", radius + "" ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new Object[] { RESPONSE_OK, result} ;
			}
			
		}
		catch ( Exception e )
		{

		}
		return null ;
	}
	public static Object[] getJoinedContests( String email)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_GETJOINEDCONTESTS) ;
		httpPost.addHeader( "email", email ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new Object[] { RESPONSE_OK, result} ;
			}
		}
		catch ( Exception e )
		{
			
		}
		return null ;
	}
	public static Object[] getRequestedContests( String email)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_GETREQUESTEDCONTESTS) ;
		httpPost.addHeader( "email", email ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new Object[] { RESPONSE_OK, result} ;
			}
			
		}
		catch ( Exception e )
		{
			
		}
		return null ;
	}
	public static Object[] getCreatedContests( String email)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_GETCREATEDCONTESTS) ;
		httpPost.addHeader( "email", email ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if (success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new Object[] { RESPONSE_OK, result} ;
			}
		}
		catch ( Exception e )
		{

		}
		return null ;
	}
	public static Object[] getUsers( String emails)
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_GETUSERS) ;
		httpPost.addHeader( "email", emails ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new Object[] { RESPONSE_OK, result} ;
			}
		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static Object[] getAllUsers()
	{
		HttpPost httpPost = new HttpPost( USER_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_USER_GETALLUSERS) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new Object[] { RESPONSE_OK, result} ;
			}
		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static String[] createContest( String name, String description, String when, String where, int maxParticipants, int minAge, String creator, int  joinCategory, String latitude, String longitude, String emails)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_CREATENEW ) ;
		httpPost.addHeader( "name", name ) ;
		httpPost.addHeader( "description", description ) ;
		httpPost.addHeader( "when", when ) ;
		httpPost.addHeader( "where", where ) ;
		httpPost.addHeader( "maxParticipants", maxParticipants + "" ) ;
		httpPost.addHeader( "minAge", minAge + "" ) ;
		httpPost.addHeader( "creator", creator ) ;
		httpPost.addHeader( "joinCategory", joinCategory + "" ) ;
		httpPost.addHeader( "latitude", latitude ) ;
		httpPost.addHeader( "longitude", longitude ) ;
		httpPost.addHeader( "emails", emails ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if (success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else 
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result} ;
			}
		}
		catch ( Exception e )
		{

		}
		return null ;
	}
	public static String[] editContest( String name, String description, String when, String where, int maxParticipants, int minAge, String creator, int  joinCategory, String latitude, String longitude, String emails)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_EDIT ) ;
		httpPost.addHeader( "name", name ) ;
		httpPost.addHeader( "description", description ) ;
		httpPost.addHeader( "when", when ) ;
		httpPost.addHeader( "where", where ) ;
		httpPost.addHeader( "maxParticipants", maxParticipants + "" ) ;
		httpPost.addHeader( "minAge", minAge + "" ) ;
		httpPost.addHeader( "creator", creator ) ;
		httpPost.addHeader( "joinCategory", joinCategory + "" ) ;
		httpPost.addHeader( "latitude", latitude ) ;
		httpPost.addHeader( "longitude", longitude ) ;
		httpPost.addHeader( "emails", emails ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if (success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else 
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result} ;
			}
		}
		catch ( Exception e )
		{

		}
		return null ;
	}
	public static String[] clickToJoin( String email, String name)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_CLICKTOJOIN ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "name", name ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else 
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result} ;
			}

		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static String[] leaveActivity( String email, String name)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_LEAVE ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "name", name ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else 
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result} ;
			}

		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static String[] deleteActivity( String email, String name)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_DELETE ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "name", name ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else 
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result} ;
			}

		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static String[] requestToJoin( String email, String name)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_REQUESTTOJOIN ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "name", name ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result } ;
			}
		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static String[] acceptToJoin( String email, String name)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_ACCEPTTOJOIN ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "name", name ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result } ;
			}
		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static String[] rejectToJoin( String email, String name)
	{
		HttpPost httpPost = new HttpPost( CONTEST_SERVICE_URL ) ;
		
		httpPost.addHeader( "action", ACTION_CONTEST_REJECTTOJOIN ) ;
		httpPost.addHeader( "email", email ) ;
		httpPost.addHeader( "name", name ) ;
		try
		{
			HttpResponse response = HttpManager.execute( httpPost ) ;
			HttpEntity entity = response.getEntity();
			String ret = EntityUtils.toString(entity);
			String success = new JSONObject(ret).getString("success");
			if ( success.equals("1") )
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_OK, result} ;
			}
			else
			{
				String result = new JSONObject(ret).getString("result");
				return new String[] { RESPONSE_FAIL, result} ;
			}
		}
		catch ( Exception e )
		{
		}
		return null ;
	}
	public static Bitmap getRegionBitmapFromSdcard(String filePath) {
		InputStream istream =   null;
		File file = new File(filePath);
		if (file == null || !file.exists())
			return null;
		try {
    		BitmapFactory.Options o = new BitmapFactory.Options();
    		o.inJustDecodeBounds=true;
    		BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            istream = ProfileActivity.profileActivity.getParent().getContentResolver().openInputStream(Uri.fromFile(file));
            BitmapRegionDecoder decoder = null;
            decoder = BitmapRegionDecoder.newInstance(istream, false);
            Bitmap bMap = decoder.decodeRegion(new Rect(0,0, o.outWidth, o.outHeight), null);    
    	    return bMap;
        } catch (Exception e1) {
         e1.printStackTrace();
        }
        return null;
	}
}