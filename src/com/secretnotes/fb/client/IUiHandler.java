package com.secretnotes.fb.client;

import java.util.ArrayList;

import com.secretnotes.fb.client.data.Album;
import com.secretnotes.fb.client.data.Photo;

public interface IUiHandler {
	
	public void initPanels();
	public void showInProgressText();
	public void showPage(String token, boolean defaultDisplay);
	public void showLoading(boolean show);
	public void setFriend(String id);
	public void processUploadedPhotos(ArrayList<Photo> photos);
	public void addAlbum(Album album);
	public void refreshPhotos(Photo photo);
	public void addAlbumPhotos(String albumId, ArrayList<Photo> photos);
	public void refreshNoteSelection(String userId, String[] notes);
	public String getCurrentPage();
}
