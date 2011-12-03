package com.secretnotes.fb.client.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.secretnotes.fb.client.User;

public interface IDataContainer {
	
	public User getUser();
	public void setUser(User user);
	public ArrayList<User> getFriendList();
	public User getFriendFromList(String userId);
	public void setFriendList(ArrayList<User> friendList);
	public HashMap<String,String> getIdList();
	public HashMap<String,String> getNameList();
}
