package com.secretnotes.fb.client.data;

import java.util.ArrayList;


public interface IDataContainer {
	
	public User getUser();
	public void setUser(User user);
	public User getFriendFromList(String userId);
	public void addToFriendList(User friend);
	public ArrayList<String> getFriendNames();
	public String getIdFromName(String name);
	public String getNameFromId(String id);
	public void addFriend(User friend);
	public void addAlbum(String userId, Album album);
	public void addPhoto(Photo photo);
	public Photo getPhoto(String photoId);
	public void resetUser();
	public void resetLookupList();
	public void insertLookupList(String name, String id);
	public ArrayList<String> getFriendUserIds();
	public void updateNotesInFriendsList(String userId, String[] notes);
	public String getFriendName(String id);
	public String getFriendProfilePic(String id);
}
