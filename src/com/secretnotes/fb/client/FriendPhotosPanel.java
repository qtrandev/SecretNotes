package com.secretnotes.fb.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.data.IDataContainer;
import com.secretnotes.fb.client.ui.AlbumListPanel;
import com.secretnotes.fb.client.ui.PhotoListPanel;

public class FriendPhotosPanel extends DataRequestPanel {
	
	private String title;
	private AlbumListPanel albumListPanel;
	private PhotoListPanel photosPanel;
	private User currentFriend;
	private Label friendNameLabel;
	
	public FriendPhotosPanel(IDataContainer dataContainer) {
		super(dataContainer);
		init();
		resetPanel();
	}
	
	private void init() {
		title = "Photos";
		getFriendNameLabel();
		getAlbumListPanel();
		getPhotosPanel();
		currentFriend = null;
	}
	
	public void processUploadedPhotos(ArrayList<Photo> photos) {
		resetPanel();
		getFriendNameLabel().setText(currentFriend.getName());
		getPhotosPanel().displayPhotos(photos);
	}
	
	public void addAlbum(final Album album) {
		ClickHandler clickHandler = new ClickHandler() {
			public void onClick(ClickEvent event) {
				getServer().requestAlbumPhotos(album.getId());
			}
		};
		getAlbumListPanel().addAlbum(album, clickHandler);
	}
	
	public void refreshPhotos(Photo photo) {
		getAlbumListPanel().refreshPhotos(photo);
	}
	
	public void addAlbumPhotos(String albumId, ArrayList<Photo> photos) {
		Album album = getAlbumListPanel().getAlbum(albumId);
		PhotoListPanel photoListPanel = new PhotoListPanel();
		photoListPanel.setStyleName("photoListPanel");
		photoListPanel.setDisplayTitle(album.getName());
		photoListPanel.displayPhotos(photos);
		add(photoListPanel);
	}
	
	public void resetPanel() {
		clear();
		add(getFriendNameLabel());
		getPhotosPanel().resetPanel();
		add(getPhotosPanel());
		getAlbumListPanel().resetPanel();
		add(getAlbumListPanel());
	}
	
	public void setFriend(String userId) {
		currentFriend = getDataContainer().getFriendFromList(userId);
	}
	
	private PhotoListPanel getPhotosPanel() {
		if (photosPanel == null) {
			photosPanel = new PhotoListPanel();
			photosPanel.setStyleName("photoListPanel");
			photosPanel.setDisplayTitle("Uploaded Photos");
		}
		return photosPanel;
	}
	
	private AlbumListPanel getAlbumListPanel() {
		if (albumListPanel == null) {
			albumListPanel = new AlbumListPanel();
			albumListPanel.getElement().setId("albumListPanel");
		}
		return albumListPanel;
	}
	
	public String getDisplayTitle() {
		return title;
	}
	
	private Label getFriendNameLabel() {
		if (friendNameLabel == null) {
			friendNameLabel = new Label();
			friendNameLabel.setStyleName("friendNameLabel");
		}
		return friendNameLabel;
	}
}
