package com.woloactivityapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.woloactivityapp.constants.Constants;
import com.woloactivityapp.constants.GooglePlaceInfo;
import com.woloactivityapp.httpclient.HttpConnectionUtil;

public class XmlPareser {
	private HttpConnectionUtil conn;
	
	public XmlPareser(HttpConnectionUtil conn)
	{
		this.conn = conn;
	}
	
	public ArrayList<GooglePlaceInfo> getGooglePlaces(String name, String radius, String lat, String lng) throws IOException, XmlPullParserException
	{
		ArrayList<GooglePlaceInfo> list = new ArrayList<GooglePlaceInfo>();
		GooglePlaceInfo item = null;
		String url = "";
		name = name.replaceAll(" ", "%20");
		url = String.format("%s?location=%s,%s&radius=%s&query=%s&sensor=false&key=%s", Constants.GOOGLE_URL, lat, lng, radius, name, Constants.API_KEY);
		url = url.replaceAll(" ", "%20");
		
		InputStream is = conn.getInputStream(url);	
		if(is!=null)
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "utf-8");
			int type = parser.getEventType();
						
			while(type!=XmlPullParser.END_DOCUMENT)
			{
				switch (type)
	            {
	            	case XmlPullParser.START_TAG:
	                	String tagName = parser.getName().trim();
	                	if("result".equals(tagName)){
	                		item = new GooglePlaceInfo();
	                		list.add(item);
	                	}
	                	else if("name".equals(tagName)){
	                   		item.setname(parser.nextText());
	                	}
	                	else if("lat".equals(tagName)){
	                   		item.setlat(parser.nextText());
	                	}
	                	else if("lng".equals(tagName)){
	                   		item.setlng(parser.nextText());
	                	}
	                	else if("formatted_address".equals(tagName)){
	                   		item.setformatted_address(parser.nextText());
	                	}
	                	else if("reference".equals(tagName)){
	                   		item.setreference(parser.nextText());
	                	}
	                	
	             }				
				 type = parser.next();
			}
			is.close();
			
		}
		
		return list;
	}

}
