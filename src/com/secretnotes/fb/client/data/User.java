package com.secretnotes.fb.client.data;


public class User {

	private String userId;
	private String profilePic;
	private String name;
	private String firstName;
	private String lastName;
	private String link;
	private String gender;
	private String timezone;
	private String locale;
	private String verified;
	private String updatedTime;
	private String type;
	private String[] notes = new String[0];
	
	public User() {
		
	}
	
	public User(String userId, String profilePic, String name,
			String firstName, String lastName, String gender, String link, String timezone, String locale) {
		this.userId = userId;
		this.profilePic = profilePic;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.link = link;
		this.timezone = timezone;
		this.locale = locale;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getNotes() {
		return notes;
	}

	public void setNotes(String[] notes) {
		this.notes = notes;
	}

}
