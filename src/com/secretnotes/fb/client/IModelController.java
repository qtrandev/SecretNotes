package com.secretnotes.fb.client;

import java.util.ArrayList;

import com.secretnotes.fb.client.data.Album;
import com.secretnotes.fb.client.data.Photo;
import com.secretnotes.fb.client.data.User;

public interface IModelController {
	public User getUser();
	public void setUser(User user);
	public void resetUser();
	public boolean checkUserInfoExists();
	public void resetLookupList();
	public void insertLookupList(String name, String id);
	public ArrayList<String> getFriendNames();
	public void addToFriendList(User friend);
	public void addAlbum(String userId, Album album);
	public void addPhoto(Photo photo);
	public String getNameFromId(String id);
	public void updateNotesInFriendsList(String userId, String[] notes);
	public String getIdFromName(String name);
	public User getFriendFromList(String userId);
	public ArrayList<String> getFriendUserIds();
}
