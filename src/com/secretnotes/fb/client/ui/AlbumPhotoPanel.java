package com.secretnotes.fb.client.ui;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Photo;

public class AlbumPhotoPanel extends FlowPanel {
	
	private ClickHandler clickHandler;
	private HTML photoLabel;
	private Label titleLabel;

	public AlbumPhotoPanel() {
		super();
		titleLabel = new Label();
		add(titleLabel);
		setStyleName("album_photo_panel");
	}
	
	public void displayPhoto(Photo photo) {
		resetPanel();
		titleLabel = new Label();
		photoLabel = new HTML("<img src='"+photo.getPicture()+"'>");
		photoLabel.addClickHandler(clickHandler);
		add(titleLabel);
		add(photoLabel);
	}
	
	private void resetPanel() {
		clear();
	}
	
	public void setClickHandler(ClickHandler clickHandler) {
		this.clickHandler = clickHandler;
	}
	
	public void setTitleLabel(String text) {
		titleLabel.setText(text);
	}
}
