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
	public void processUploadedPhotos(String id, ArrayList<Photo> photos);
	public void processTaggedPhotos(String id, ArrayList<Photo> photos);
	public void addAlbum(String id, Album album);
	public void refreshPhotos(String id, Photo photo);
	public void addAlbumPhotos(String id, String albumId, ArrayList<Photo> photos);
	public void refreshNoteSelection(String userId, String[] notes);
	public String getCurrentPage();
	public void setNotesController(INotesController notesController);
}
