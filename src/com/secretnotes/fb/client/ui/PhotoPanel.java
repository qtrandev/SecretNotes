package com.secretnotes.fb.client.ui;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.secretnotes.fb.client.data.Photo;

public class PhotoPanel extends FlowPanel {
	
	private String photoId;

	public PhotoPanel() {
		super();
		init();
		resetPanel();
	}
	
	private void init() {
		setStyleName("photoPanel");
	}
	
	public void displayPhoto(Photo photo) {
		this.photoId = photo.getPhotoId();
		resetPanel();
		Anchor anchor = new Anchor();
		anchor.setTarget("_blank");
		anchor.setHref(photo.getSource());
		anchor.getElement().setInnerHTML("<img src='"+photo.getPicture()+"'/>");
		add(anchor);
	}
	
	public void resetPanel() {
		clear();
	}
	
	public String getPhotoId() {
		return photoId;
	}
}
