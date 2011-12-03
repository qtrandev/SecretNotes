package com.secretnotes.fb.client.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.secretnotes.fb.client.User;

public class DataContainer implements IDataContainer {
	
	private User user = null;
	private ArrayList<User> friendList = new ArrayList<User>();
	private HashMap<String,String> idList = new HashMap<String,String>();
	private HashMap<String,String> nameList = new HashMap<String,String>();
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<User> getFriendList() {
		return friendList;
	}
	
	public void setFriendList(ArrayList<User> friendList) {
		this.friendList = friendList;
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
}
