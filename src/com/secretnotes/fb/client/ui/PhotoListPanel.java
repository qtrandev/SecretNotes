package com.secretnotes.fb.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Photo;

public class PhotoListPanel extends FlowPanel {
	
	private Label titleLabel;
	private Label photoCountLabel;

	public PhotoListPanel() {
		super();
		init();
		resetPanel();
	}
	
	private void init() {
		setStyleName("photoListPanel");
		getTitleLabel();
		getPhotoCountLabel();
	}
	
	public void displayPhotos(ArrayList<Photo> photos) {
		resetPanel();
		getPhotoCountLabel().setText("Photos: "+photos.size());
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
		add(getPhotoCountLabel());
	}
	
	private Label getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new Label();
			titleLabel.setStyleName("photoListTitle");
		}
		return titleLabel;
	}
	
	private Label getPhotoCountLabel() {
		if (photoCountLabel == null) {
			photoCountLabel = new Label();
			photoCountLabel.setStyleName("photoListCount");
		}
		return photoCountLabel;
	}
	
	public void setDisplayTitle(String text) {
		getTitleLabel().setText(text);
	}
}
