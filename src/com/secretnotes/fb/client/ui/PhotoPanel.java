package com.secretnotes.fb.client.ui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.secretnotes.fb.client.Photo;

public class PhotoPanel extends FlowPanel {

	public PhotoPanel() {
		super();
	}
	
	public void displayPhoto(Photo photo) {
		resetPanel();
		add(new HTML("<img src='"+photo.getPicture()+"'>"));
	}
	
	private void resetPanel() {
		clear();
	}
}
