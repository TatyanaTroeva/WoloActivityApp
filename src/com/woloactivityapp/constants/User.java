package com.woloactivityapp.constants;

import java.io.Serializable;

import android.graphics.Bitmap;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String 	firstname ;
	private String 	lastname ;
	private String 	password ;
	private String 	phone_number;
	private int		age ;
	private String  aboutme ;
	private String	email ;
	private Bitmap 	photo ;
	private double	latitude;
	private double  longitude;

	public User()
	{
		// TODO Auto-generated constructor stub
		this.firstname = "";
		this.lastname = "";
		this.password = "";
		this.email = "";
		this.age = 0;
		this.phone_number = "";
		this.aboutme = "";
		this.email = "";
		this.photo = null;
		this.latitude = 0.0;
		this.longitude = 0.0;
	}
	
	public User( String firstname, String lastname, String password, String email)
	{
		// TODO Auto-generated constructor stub
		this.firstname = firstname;
		this.password = password;
		this.lastname = lastname;
		this.email = email;
		this.age = 0;
		this.aboutme = "";
		this.email = "";
		this.photo = null;
		this.latitude = 0.0;
		this.longitude = 0.0;
	}

	public String getFirstname() { return firstname; }
	public void setFirstname (String firstname) { this.firstname = firstname; }
	
	public String getPassword() { return password; }
	public void setPassword (String password) { this.password = password; }
	
	public String getLastname() { return lastname; }
	public void setLastname (String lastname) { this.lastname = lastname; }
	
	public String getPhoneNumber() { return phone_number; }
	public void setPhoneNumber(String phone_number) {this.phone_number = phone_number;}
	
	public int getAge() { return age; }
	public void setAge (int age) { this.age = age; }
	
	public String getAboutme() { return aboutme; }
	public void setAboutme (String aboutme) { this.aboutme = aboutme; }
	
	public String getEmail() { return email; }
	public void setEmail (String email) { this.email = email; }
	
	public double getLatitude() { return latitude; }
	public void setLatitude (double latitude) { this.latitude = latitude; }
	
	public double getLongitude() { return longitude; }
	public void setLongitude (double longitude) { this.longitude = longitude; }

	public Bitmap getPhoto()
	{
		return photo ;
	}

	public void deletePhoto()
	{
		if ( this.photo != null )
		{
			this.photo.recycle() ;
			this.photo = null ;
		}
	}
	
	public void setPhoto( Bitmap photo )
	{
		if ( this.photo != null )
		{
			this.photo.recycle() ;
			this.photo = null ;
		}
		this.photo = photo ;
	}
}
