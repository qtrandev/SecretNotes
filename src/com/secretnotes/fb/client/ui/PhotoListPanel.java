package com.secretnotes.fb.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Photo;

public class PhotoListPanel extends FlowPanel {
	
	private Label titleLabel;

	public PhotoListPanel() {
		super();
		init();
		resetPanel();
	}
	
	private void init() {
		setStyleName("photoListPanel");
		getTitleLabel();
	}
	
	public void displayPhotos(ArrayList<Photo> photos) {
		resetPanel();
		PhotoPanel photoPanel;
		for (Photo photo : photos) {
			photoPanel = new PhotoPanel();
			photoPanel.displayPhoto(photo);
			add(photoPanel);
		}
	}
	
	public void resetPanel() {
		clear();
		add(getTitleLabel());
	}
	
	private Label getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new Label();
			titleLabel.setStyleName("photoAlbumTitle");
		}
		return titleLabel;
	}
	
	public void setDisplayTitle(String text) {
		getTitleLabel().setText(text);
	}
}
