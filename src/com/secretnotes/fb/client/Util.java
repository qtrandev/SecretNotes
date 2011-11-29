package com.secretnotes.fb.client;

import com.google.gwt.core.client.GWT;

public class Util {
	
	public static boolean LOG = false;

	public static NotesConstants CONSTANTS = (NotesConstants)GWT.create(NotesConstants.class);
	public static NotesMessages MESSAGES = (NotesMessages)GWT.create(NotesMessages.class);
	
	public static final String PAGE_HOME = "home";
	public static final String PAGE_FRIENDS = "friends";
	public static final String PAGE_NOTES = "notes";
	public static final String PAGE_QUERY = "query";
	
	// Connascence of Name problem - changing this means changing constants files and updating stored DB values
	public static final String[] NOTES_CATEGORY = new String[] {
		"hot","cute","sexy","want_to_marry"
	};
	
	// LIST OF FACEBOOK GRAPH IDENTIFIERS
	// https://graph.facebook.com/me
	public static final String USER_ID = "id"; //ex: 100002923045666
	public static final String USER_NAME = "name"; //ex: Quyen Tran
	public static final String USER_FIRST_NAME = "first_name"; //ex: Quyen
	public static final String USER_LAST_NAME = "last_name"; //ex: Tran
	public static final String USER_LINK = "link"; //ex: http://www.facebook.com/profile.php?id=100002923045666
	public static final String USER_GENDER = "gender"; //ex: male
	public static final String USER_TIMEZONE = "timezone"; //ex: -4 // doesn't always appear
	public static final String USER_LOCALE = "locale"; //ex: en_US
	public static final String USER_VERIFIED = "verified"; //ex: true // doesn't always appear
	public static final String USER_UPDATED_TIME = "updated_time"; //ex: 2011-10-18T18:43:26+0000
	public static final String USER_TYPE = "type"; //ex: user
	public static final String PHOTO_ID = "id"; //ex: 10101325856340081
	public static final String PHOTO_FROM = "from"; //array
	public static final String PHOTO_TAGS = "tags"; //array
	public static final String PHOTO_NAME = "name"; //ex: This is a description.
	public static final String PHOTO_PICTURE = "picture"; //ex: http://photos-c.ak.fbcdn.net/hphotos-ak-ash4/x9165_s.jpg
	public static final String PHOTO_SOURCE = "source"; //ex: http://a3.sphotos.ak.fbcdn.net/hphotos-ak-ash4/s720x720/x5_n.jpg
	public static final String PHOTO_HEIGHT = "height"; //ex: 511
	public static final String PHOTO_WIDTH = "width"; //ex: 720
	public static final String PHOTO_IMAGES = "images"; //array
	public static final String PHOTO_LINK = "link"; //ex: http://www.facebook.com/photo.php?pid=82096327&id=100002923045666
	public static final String PHOTO_ICON = "icon"; //ex: http://static.ak.fbcdn.net/rsrc.php/v1/yk/r/3hBLgN5GxNc.gif
	public static final String PHOTO_CREATED_TIME = "created_time"; //ex: 2011-09-19T18:48:47+0000
	public static final String PHOTO_POSITION = "position"; //ex: 1
	public static final String PHOTO_UPDATED_TIME = "updated_time"; //ex: 2011-09-19T18:48:48+0000
	
	public static final String ARRAY_DATA = "data";
	public static final String PHOTO_COMMENTS = "comments"; //array
}
