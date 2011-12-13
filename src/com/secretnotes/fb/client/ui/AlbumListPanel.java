package com.secretnotes.fb.client.ui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Album;
import com.secretnotes.fb.client.Photo;

public class AlbumListPanel extends FlowPanel {
	
	private Label titleLabel;
	private Label albumCountLabel;
	private int albumCount;
	private ArrayList<AlbumPhotoPanel> albumPhotoPanels;

	public AlbumListPanel() {
		super();
		init();
		resetPanel();
	}
	
	private void init() {
		getTitleLabel();
		getAlbumCountLabel();
		albumCount = 0;
		getAlbumPhotoPanels();
	}
	
	public void addAlbum(Album album, ClickHandler clickHandler) {
		AlbumPhotoPanel albumPhotoPanel = new AlbumPhotoPanel(album);
		albumPhotoPanel.setClickHandler(clickHandler);
		albumPhotoPanel.getElement().setId("albumPhotoPanel");
		albumPhotoPanel.setTitleLabel(album.getName());
		getAlbumPhotoPanels().add(albumPhotoPanel);
		add(albumPhotoPanel);
		albumCount++;
		setAlbumCountDisplay(albumCount);
	}
	
	public void refreshPhotos(Photo photo) {
		for (AlbumPhotoPanel albumPhotoPanel : getAlbumPhotoPanels()) {
			if (albumPhotoPanel.getAlbum().getCoverPhotoId().equals(photo.getPhotoId())) {
				albumPhotoPanel.refreshPhotos(photo);
				break;
			}
		}
	}
	
	public void resetPanel() {
		clear();
		albumCount = 0;
		setAlbumCountDisplay(albumCount);
		add(getTitleLabel());
		add(getAlbumCountLabel());
		getAlbumPhotoPanels().clear();
	}
	
	private Label getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new Label("Albums");
			titleLabel.setStyleName("albumTitle");
		}
		return titleLabel;
	}
	
	private Label getAlbumCountLabel() {
		if (albumCountLabel == null) {
			albumCountLabel = new Label();
		}
		return albumCountLabel;
	}

	public ArrayList<AlbumPhotoPanel> getAlbumPhotoPanels() {
		if (albumPhotoPanels == null) {
			albumPhotoPanels = new ArrayList<AlbumPhotoPanel>();
		}
		return albumPhotoPanels;
	}
	
	public Album getAlbum(String albumId) {
		for (AlbumPhotoPanel albumPhotoPanel : getAlbumPhotoPanels()) {
			if (albumPhotoPanel.getAlbum().getId().equals(albumId)) {
				return albumPhotoPanel.getAlbum();
			}
		}
		return null;
	}
	
	private void setAlbumCountDisplay(int count) {
		getAlbumCountLabel().setText("Number of albums: "+count);
	}
}
