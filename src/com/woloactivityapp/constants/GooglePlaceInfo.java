package com.woloactivityapp.constants;

import java.io.Serializable;

public class GooglePlaceInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name = null;
	private String reference = null;
	private String lat = null;
	private String lng = null;
	private String formatted_address = null;
	
	public String getname()
	{
		return name;
	}
	public  void setname(String val)
	{
		name = val;
	}
	public String getreference()
	{
		return reference;
	}
	public  void setreference(String val)
	{
		reference = val;
	}
	public String getlat()
	{
		return lat;
	}
	public  void setlat(String val)
	{
		lat = val;
	}
	public String getlng()
	{
		return lng;
	}
	public  void setlng(String val)
	{
		lng = val;
	}
	public String getformatted_address()
	{
		return formatted_address;
	}
	public  void setformatted_address(String val)
	{
		formatted_address = val;
	}
}
