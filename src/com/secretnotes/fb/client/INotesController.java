package com.secretnotes.fb.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.secretnotes.fb.client.data.User;

public interface INotesController {
	public void persistNotes(String userId, String userName, String[] notes, String ownerId, boolean showAlert);
	public void persistNotes(HashMap<String, String[]> userNotesMap, HashMap<String, String> userNamesMap, String ownerId);
	public void requestNotes(String userId);
	public void requestPhotos(String userId);
	public void requestAlbums(String userId);
	public void requestPhotoLink(String photoId);
	public void requestAlbumPhotos(String albumId);
	public User getUser();
	public ArrayList<String> getFriendNames();
	public String getIdFromName(String name);
	public User getFriendFromList(String userId);
	public ArrayList<String> getFriendUserIds();
}
