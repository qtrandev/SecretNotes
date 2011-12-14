package com.secretnotes.fb.client.data;

import java.util.ArrayList;
import java.util.HashMap;


public interface IDataContainer {
	
	public User getUser();
	public void setUser(User user);
	public ArrayList<User> getFriendList();
	public User getFriendFromList(String userId);
	public HashMap<String,String> getIdList();
	public HashMap<String,String> getNameList();
	public void addFriend(User friend);
	public void addAlbum(String userId, Album album);
	public void addPhoto(Photo photo);
	public Photo getPhoto(String photoId);
}
