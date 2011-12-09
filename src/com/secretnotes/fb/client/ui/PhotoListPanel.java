package com.secretnotes.fb.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlowPanel;
import com.secretnotes.fb.client.Photo;

public class PhotoListPanel extends FlowPanel {

	public PhotoListPanel() {
		super();
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
	
	private void resetPanel() {
		clear();
	}
}
