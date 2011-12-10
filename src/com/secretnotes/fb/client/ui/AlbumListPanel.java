package com.secretnotes.fb.client.ui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
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
		titleLabel = new Label("Albums");
		titleLabel.setStyleName("albumTitle");
		add(titleLabel);
		albumPhotoPanels = new ArrayList<AlbumPhotoPanel>();
	}
	
//	public void displayPhotos(ArrayList<Photo> photos) {
//		resetPanel();
//		add(getTitleLabel());
//		AlbumPhotoPanel albumPhotoPanel;
//		for (Photo photo : photos) {
//			albumPhotoPanel = new AlbumPhotoPanel();
//			albumPhotoPanel.setClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent event) {
//					
//				}
//			});
//			albumPhotoPanel.displayPhoto(photo);
//			add(albumPhotoPanel);
//		}
//	}
	
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
	
	private void resetPanel() {
		clear();
	}
	
	private Label getTitleLabel() {
		return titleLabel;
	}

	public ArrayList<AlbumPhotoPanel> getAlbumPhotoPanels() {
		return albumPhotoPanels;
	}
}
