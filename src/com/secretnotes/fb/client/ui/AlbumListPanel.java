package com.secretnotes.fb.client.ui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Album;
import com.secretnotes.fb.client.Photo;

public class AlbumListPanel extends FlowPanel {
	
	private Label titleLabel;
	private ArrayList<AlbumPhotoPanel> albumPhotoPanels;

	public AlbumListPanel() {
		super();
		init();
		resetPanel();
	}
	
	private void init() {
		titleLabel = new Label("Albums");
		titleLabel.setStyleName("albumTitle");
		getAlbumPhotoPanels();
	}
	
	public void addAlbum(Album album, ClickHandler clickHandler) {
		AlbumPhotoPanel albumPhotoPanel = new AlbumPhotoPanel(album);
		albumPhotoPanel.setClickHandler(clickHandler);
		albumPhotoPanel.getElement().setId("albumPhotoPanel");
		albumPhotoPanel.setTitleLabel(album.getName());
		getAlbumPhotoPanels().add(albumPhotoPanel);
		add(albumPhotoPanel);
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
		add(getTitleLabel());
		getAlbumPhotoPanels().clear();
	}
	
	private Label getTitleLabel() {
		return titleLabel;
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
}
