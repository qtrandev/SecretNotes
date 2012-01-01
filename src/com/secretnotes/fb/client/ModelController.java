package com.secretnotes.fb.client;

import java.util.ArrayList;

import com.secretnotes.fb.client.data.Album;
import com.secretnotes.fb.client.data.IDataContainer;
import com.secretnotes.fb.client.data.Photo;
import com.secretnotes.fb.client.data.User;

public class ModelController implements IModelController {

	private IDataContainer dataContainer;
	
	public ModelController(IDataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}
	
	public User getUser() {
		return getDataContainer().getUser();
	}
	
	public void setUser(User user) {
		getDataContainer().setUser(user);
	}

	public void resetUser() {
		getDataContainer().resetUser(); // Reset user since may have logged out
	}
	
	public boolean checkUserInfoExists() {
		return getDataContainer().getUser() != null;
	}
	
	private IDataContainer getDataContainer() {
		return dataContainer;
	}

	public void resetLookupList() {
		getDataContainer().resetLookupList();
	}

	public void insertLookupList(String name, String id) {
		getDataContainer().insertLookupList(name, id);
	}
	
	public ArrayList<String> getFriendNames() {
		return getDataContainer().getFriendNames();
	}
	
	public void addToFriendList(User friend) {
		getDataContainer().addToFriendList(friend);
	}
	
	public void addAlbum(String userId, Album album) {
		getDataContainer().addAlbum(userId, album);
	}
	
	public void addPhoto(Photo photo) {
		getDataContainer().addPhoto(photo);
	}
	
	public String getNameFromId(String id) {
		return getDataContainer().getNameFromId(id);
	}
	
	public void updateNotesInFriendsList(String userId, String[] notes) {
		getDataContainer().updateNotesInFriendsList(userId, notes);
	}
	
	public User getFriendFromList(String userId) {
		return getDataContainer().getFriendFromList(userId);
	}

	public String getIdFromName(String name) {
		return getDataContainer().getIdFromName(name);
	}
	
	public ArrayList<String> getFriendUserIds() {
		return getDataContainer().getFriendUserIds();
	}
}
