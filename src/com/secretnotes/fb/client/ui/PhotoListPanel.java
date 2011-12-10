package com.secretnotes.fb.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Photo;

public class PhotoListPanel extends FlowPanel {
	
	private Label titleLabel;

	public PhotoListPanel() {
		super();
		titleLabel = new Label();
		titleLabel.setStyleName("photoAlbumTitle");
		setStyleName("photoListPanel");
	}
	
	public void displayPhotos(ArrayList<Photo> photos) {
		resetPanel();
		add(titleLabel);
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
	
	public void setDisplayTitle(String text) {
		getTitleLabel().setText(text);
	}
}
