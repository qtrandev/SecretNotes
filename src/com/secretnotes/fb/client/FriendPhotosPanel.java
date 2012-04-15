package com.secretnotes.fb.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.data.Album;
import com.secretnotes.fb.client.data.Photo;
import com.secretnotes.fb.client.data.User;
import com.secretnotes.fb.client.ui.AlbumListPanel;
import com.secretnotes.fb.client.ui.PhotoListPanel;

public class FriendPhotosPanel extends DataRequestPanel {
	
	private String title;
	private AlbumListPanel albumListPanel;
	private PhotoListPanel photosPanel;
	private PhotoListPanel taggedPhotosPanel;
	private User currentFriend;
	private Label friendNameLabel;
	private Anchor photoLink;
	
	public FriendPhotosPanel(INotesController notesController) {
		super(notesController);
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
		String link = "http://www.facebook.com/profile.php?id="+currentFriend.getUserId()+"&sk=photos";
		getPhotoLink().setText(link);
		getPhotoLink().setHref(link);
		getPhotosPanel().displayPhotos(photos);
	}
	
	public void processTaggedPhotos(ArrayList<Photo> photos) {
		getTaggedPhotosPanel().displayPhotos(photos);
	}
	
	public void addAlbum(final String id, final Album album) {
		ClickHandler clickHandler = new ClickHandler() {
			public void onClick(ClickEvent event) {
				getNotesController().requestAlbumPhotos(id, album.getId());
			}
		};
		getAlbumListPanel().addAlbum(album, clickHandler);
	}
	
	public void refreshPhotos(Photo photo) {
		getAlbumListPanel().refreshPhotos(photo);
	}
	
	public void refreshTaggedPhotos(Photo photo) {
		getTaggedPhotosPanel().refreshPhoto(photo);
	}
	
	public void addAlbumPhotos(String albumId, ArrayList<Photo> photos) {
		Album album = getAlbumListPanel().getAlbum(albumId);
		PhotoListPanel photoListPanel = new PhotoListPanel();
		photoListPanel.setStyleName("photoListPanel");
		photoListPanel.setDisplayTitle(album.getName());
		photoListPanel.displayPhotos(photos);
		add(photoListPanel);
		photoListPanel.getElement().scrollIntoView();
	}
	
	public void resetPanel() {
		clear();
		add(getFriendNameLabel());
		add(getPhotoLink());
		getPhotosPanel().resetPanel();
		add(getPhotosPanel());
		getTaggedPhotosPanel().resetPanel();
		add(getTaggedPhotosPanel());
		getAlbumListPanel().resetPanel();
		add(getAlbumListPanel());
	}
	
	public void setFriend(String userId) {
		currentFriend = getNotesController().getFriendFromList(userId);
	}
	
	private PhotoListPanel getPhotosPanel() {
		if (photosPanel == null) {
			photosPanel = new PhotoListPanel();
			photosPanel.setStyleName("photoListPanel");
			photosPanel.setDisplayTitle("Uploaded Photos");
		}
		return photosPanel;
	}
	
	private PhotoListPanel getTaggedPhotosPanel() {
		if (taggedPhotosPanel == null) {
			taggedPhotosPanel = new PhotoListPanel();
			taggedPhotosPanel.setStyleName("photoListPanel");
			taggedPhotosPanel.setDisplayTitle("Tagged Photos");
		}
		return taggedPhotosPanel;
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
	
	private Anchor getPhotoLink() {
		if (photoLink == null) {
			photoLink = new Anchor();
			photoLink.setTarget("_blank");
			photoLink.setStyleName("photoLink");
		}
		return photoLink;
	}
}
