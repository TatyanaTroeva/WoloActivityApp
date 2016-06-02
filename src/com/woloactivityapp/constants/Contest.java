package com.woloactivityapp.constants;

import java.io.Serializable;
import java.util.Date;


public class Contest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String 	name ;
	private String 	description ;
	private Date 	when ;
	private String	where ;
	private int		maxParticipants ;
	private int		minAge ;
	private String	creator ;
	private int		joinCategory ;
	private String	joinedUsers ;
	private String	requestedUsers ;
	private double  latitude;
	private double  longitude;

	public Contest(String name, String description, Date when, String where, int max_participants, int min_age, String creator, int joinCategory, String joinedUsers, String requestedUsers, double latitude, double longitude)
	{
		// TODO Auto-generated constructor stub
		this.name = name;
		this.description = description;
		this.when = when;
		this.where = where;
		this.maxParticipants = max_participants;
		this.minAge = min_age;
		this.creator = creator;
		this.joinCategory = joinCategory;
		this.joinedUsers = joinedUsers;
		this.requestedUsers = requestedUsers;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getName() { return name; }
	public void setName (String name) { this.name = name; }
	
	public String getDescription() { return description; }
	public void setDescription (String description) { this.description = description; }
	
	public int getMaxParticipants() { return maxParticipants; }
	public void setMaxParticipants (int max_participants) { this.maxParticipants = max_participants; }
	
	public int getMinAge() { return minAge; }
	public void setMinAge (int min_age) { this.minAge = min_age; }
	
	public double getLatitude() { return latitude; }
	public void setLatitude (double latitude) { this.latitude = latitude; }
	
	public double getLongitude() { return longitude; }
	public void setLongitude (double longitude) { this.longitude = longitude; }
	
	public Date getWhen() { return when; }
	public void setWhen (Date when) { this.when = when; }
	
	public String getWhere() { return where; }
	public void setWhere (String where) { this.where = where; }
	
	public String getCreator() { return creator; }
	public void setCreator (String creator) { this.creator = creator; }
	
	public int getJoinCategory() { return joinCategory; }
	public void setJoinCategory (int joinCategory) { this.joinCategory = joinCategory; }
	
	public String getJoinedUsers() { return joinedUsers; }
	public void setJoinedUsers (String joinedUsers) { this.joinedUsers = joinedUsers; }
	
	public String getRequestedUsers() { return requestedUsers; }
	public void setRequestedUsers (String requestedUsers) { this.requestedUsers = requestedUsers; }
}
