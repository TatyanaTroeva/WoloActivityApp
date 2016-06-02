package com.woloactivityapp.constants;

import java.io.Serializable;

public class GooglePlaceDetailInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name = null;
	private String formatted_phone_number = null;
	private String formatted_address = null;
	private String lat = null;
	private String lng = null;
	private String locality = null;
	public String getlat()
	{
		return lat;
	}
	public  void setlat(String val)
	{
		lat = val;
	}
	public String getlocality()
	{
		return locality;
	}
	public  void setlocality(String val)
	{
		locality = val;
	}
	public String getlng()
	{
		return lng;
	}
	public  void setlng(String val)
	{
		lng = val;
	}
	public String getname()
	{
		return name;
	}
	public  void setname(String val)
	{
		name = val;
	}
	public String getphone()
	{
		return formatted_phone_number;
	}
	public  void setphone(String val)
	{
		formatted_phone_number = val;
	}
	public String getaddress()
	{
		return formatted_address;
	}
	public  void setaddress(String val)
	{
		formatted_address = val;
	}
}
