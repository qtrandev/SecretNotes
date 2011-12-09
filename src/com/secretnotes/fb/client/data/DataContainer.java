package com.secretnotes.fb.client.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.secretnotes.fb.client.Album;
import com.secretnotes.fb.client.Photo;
import com.secretnotes.fb.client.User;

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

	public ArrayList<User> getFriendList() {
		return friendList;
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
	
	public HashMap<String,String> getIdList() {
		return idList;
	}
	
	public HashMap<String,String> getNameList() {
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
}
