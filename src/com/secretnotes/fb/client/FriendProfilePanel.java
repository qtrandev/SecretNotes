package com.secretnotes.fb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.secretnotes.fb.client.data.IDataContainer;
import com.secretnotes.fb.client.ui.AlbumListPanel;

public class FriendProfilePanel extends DataRequestPanel {
	
	private FlowPanel photosPanel;
	private AlbumListPanel albumListPanel;
	private User currentFriend = null;
	private String title = "Photos";
	
	public FriendProfilePanel(IDataContainer dataContainer) {
		super(dataContainer);
		resetPanel();
	}
	
	public void processPhotosRequest(JavaScriptObject response) {
		JSOModel jso = response.cast();
		JsArray<JSOModel> photos = jso.getArray(Util.ARRAY_DATA);
		getPhotosPanel().add(new HTML("<h2>"+currentFriend.getName()+"</h2>"));
		getPhotosPanel().add(new HTML("Photos uploaded: "+photos.length()));
		Anchor anchor;
		String picture;
		for (int i=0; i<photos.length(); i++) {
			picture = photos.get(i).get(Util.PHOTO_PICTURE);
			anchor = new Anchor();
			anchor.setTarget("_blank");
			anchor.setHref(photos.get(i).get(Util.PHOTO_SOURCE));
			anchor.getElement().setInnerHTML("<img src='"+picture+"'/>");
			getPhotosPanel().add(anchor);
		}
	}
	
	public void addAlbum(Album album) {
		ClickHandler clickHandler = new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("CLICKED");
			}
		};
		getAlbumListPanel().addAlbum(album, clickHandler);
	}
	
	public void refreshPhotos(Photo photo) {
		getAlbumListPanel().refreshPhotos(photo);
	}
	
	private void layoutPanel() {
		add(getPhotosPanel());
		add(getAlbumListPanel());
	}
	
	private void resetPanel() {
		clear();
		getPhotosPanel().clear();
		layoutPanel();
	}
	
	public void setFriend(String userId) {
		currentFriend = getDataContainer().getFriendFromList(userId);
	}
	
	private FlowPanel getPhotosPanel() {
		if (photosPanel == null) {
			photosPanel = new FlowPanel();
		}
		return photosPanel;
	}
	
	private AlbumListPanel getAlbumListPanel() {
		if (albumListPanel == null) {
			albumListPanel = new AlbumListPanel();
			albumListPanel.getElement().setId("albumListPanel");
		}
		return albumListPanel;
	}
	
	public String getDisplayTitle() {
		return title;
	}
}
