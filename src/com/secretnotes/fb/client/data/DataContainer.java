package com.secretnotes.fb.client.data;

import java.util.ArrayList;
import java.util.HashMap;


public class DataContainer implements IDataContainer {
	
	private User user = null;
	private ArrayList<User> friendList = new ArrayList<User>();
	private HashMap<String,String> idList = new HashMap<String,String>();
	private HashMap<String,String> nameList = new HashMap<String,String>();
	private ArrayList<PhotoOwnership> photoOwnershipList = new ArrayList<PhotoOwnership>();
	private HashMap<String, Photo> photoMap = new HashMap<String, Photo>();
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void resetUser() {
		setUser(null);
		getFriendList().clear();
		getIdList().clear();
		getNameList().clear();
	}

	private ArrayList<User> getFriendList() {
		return friendList;
	}
	
	public ArrayList<String> getFriendNames() {
		ArrayList<String> result = new ArrayList<String>();
		for (User friend : getFriendList()) {
			result.add(friend.getName());
		}
		return result;
	}
	
	public String getFriendName(String id) {
		String result = null;
		for (User friend : getFriendList()) {
			if (friend.getUserId().equals(id)) {
				result = friend.getName();
				break;
			}
		}
		return result;
	}
	
	public String getFriendProfilePic(String id) {
		String result = null;
		for (User friend : getFriendList()) {
			if (friend.getUserId().equals(id)) {
				result = friend.getProfilePic();
				break;
			}
		}
		return result;
	}
	
	public ArrayList<String> getFriendUserIds() {
		ArrayList<String> result = new ArrayList<String>();
		for (User friend : getFriendList()) {
			result.add(friend.getUserId());
		}
		return result;
	}
	
	public void addFriend(User friend) {
		getFriendList().add(friend);
		getPhotoOwnershipList().add(new PhotoOwnership(friend));
	}
	
	public User getFriendFromList(String userId) {
		User result = null;
		for (User user:getFriendList()) {
			if (userId.equals(user.getUserId())) {
				result = user;
				break;
			}
		}
		return result;
	}
	
	public String getIdFromName(String name) {
		return getIdList().get(name);
	}
	
	public String getNameFromId(String id) {
		return getNameList().get(id);
	}
	
	public void resetLookupList() {
		getIdList().clear();
		getNameList().clear();
		getIdList().put(getUser().getName(), getUser().getUserId());
		getNameList().put(getUser().getUserId(), getUser().getName());
	}
	
	public void insertLookupList(String name, String id) {
		getIdList().put(name, id);
		getNameList().put(id, name);
	}
	
	public void addToFriendList(User friend) {
		getFriendList().add(friend);
	}
	
	private HashMap<String,String> getIdList() {
		return idList;
	}
	
	private HashMap<String,String> getNameList() {
		return nameList;
	}

	private ArrayList<PhotoOwnership> getPhotoOwnershipList() {
		return photoOwnershipList;
	}
	
	public void addAlbum(String userId, Album album) {
		User friend = getFriendFromList(userId);
		for (PhotoOwnership p : getPhotoOwnershipList()) {
			if (friend.getUserId().equals(p.getUser().getUserId())) {
				p.getPhotoManager().addAlbum(album);
				break;
			}
		}
	}
	
	public void addPhoto(Photo photo) {
		getPhotoMap().put(photo.getPhotoId(), photo);
	}
	
	public Photo getPhoto(String photoId) {
		return getPhotoMap().get(photoId);
	}
	
	private HashMap<String, Photo> getPhotoMap() {
		return photoMap;
	}
	
	
	public void updateNotesInFriendsList(String userId, String[] notes) {
		if (userId.equals(getUser().getUserId())) { // Handle main user
			getUser().setNotes(notes);
			return;
		}
		for (User user : getFriendList()) {
			if (userId.equals(user.getUserId())) {
				user.setNotes(notes);
				break;
			}
		}
	}
}
