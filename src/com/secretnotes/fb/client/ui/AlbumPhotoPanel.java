package com.secretnotes.fb.client.ui;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Album;
import com.secretnotes.fb.client.Photo;

public class AlbumPhotoPanel extends FlowPanel {
	
	private ClickHandler clickHandler;
	private HTML photoLabel;
	private Label titleLabel;
	private Album album;

	public AlbumPhotoPanel(Album album) {
		super();
		this.album = album;
		init();
		resetPanel();
	}
	
	private void init() {
		titleLabel = new Label();
		setStyleName("album_photo_panel");
	}
	
	public void refreshPhotos(Photo photo) {
		resetPanel();
		photoLabel = new HTML("<img src='"+photo.getPicture()+"'>");
		photoLabel.addClickHandler(clickHandler);
		add(photoLabel);
	}
	
	public void resetPanel() {
		clear();
		add(titleLabel);
	}
	
	public void setClickHandler(ClickHandler clickHandler) {
		this.clickHandler = clickHandler;
	}
	
	public void setTitleLabel(String text) {
		titleLabel.setText(text);
	}
	
	public Album getAlbum() {
		return album;
	}
}
