package com.secretnotes.fb.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Album;
import com.secretnotes.fb.client.Photo;

public class AlbumPanel extends FlowPanel {

	private Label titleLabel;
	
	public AlbumPanel() {
		super();
		titleLabel = new Label();
	}
	
	public void displayPhotos(Album album, ArrayList<Photo> photos) {
		resetPanel();
		add(getTitleLabel());
		displayAlbumInfo(album);
		PhotoPanel photoPanel;
		for (Photo photo : photos) {
			photoPanel = new PhotoPanel();
			photoPanel.displayPhoto(photo);
			add(photoPanel);
		}
	}
	
	private void resetPanel() {
		clear();
	}
	
	private Label getTitleLabel() {
		return titleLabel;
	}
	
	private void displayAlbumInfo(Album album) {
		getTitleLabel().setText(album.getName());
	}
}
