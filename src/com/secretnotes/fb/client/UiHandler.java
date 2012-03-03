package com.secretnotes.fb.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.secretnotes.fb.client.data.Album;
import com.secretnotes.fb.client.data.IDataContainer;
import com.secretnotes.fb.client.data.Photo;
import com.secretnotes.fb.client.ui.AboutPanel;
import com.secretnotes.fb.client.ui.HomePanel;
import com.secretnotes.fb.client.ui.QueryWelcomeContent;
import com.secretnotes.fb.client.ui.SandboxPanel;

public class UiHandler implements IUiHandler {

	private INotesController notesController;
	private IDataContainer dataContainer;
	// UI components
	private FlowPanel mainPanel;
	private SimplePanel contentPanel;
	private FlowPanel headerPanel;
	private FlowPanel navPanel;
	private HomePanel homePanel;
	private FriendsContainerPanel friendsContainerPanel;
	private FriendsPanel friendsPanel;
	private HashMap<String,FriendPhotosPanel> friendPhotosPanelMap;
	private SandboxPanel sandboxPanel;
	private AboutPanel aboutPanel;
	private NotesPanel notesDisplay;
	private String currentPage = Util.PAGE_HOME;
	private Element loadingGif;
	
	public UiHandler(IDataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}
	
	public void setNotesController(INotesController notesController) {
		this.notesController = notesController;
	}
	
	private INotesController getNotesController() {
		return notesController;
	}
	
	public void initPanels() {
		RootPanel root = RootPanel.get();
		getMainPanel().getElement().setId("mainPanel");
		getContentPanel().getElement().setId("contentPanel");
		getNavPanel().getElement().setId("navPanel");
		getMainPanel().add(getHeaderPanel());
		getMainPanel().add(getNavPanel());
		getMainPanel().add(getContentPanel());
		root.add(getMainPanel());
		
		loadingGif = DOM.getElementById("loadingPanel");
		showLoading(false);
		
        getFriendsContainerPanel().add(getFriendsPanel(), "Friends");
        getFriendsContainerPanel().setHeight("600px");
        getFriendsPanel().getElement().setId("friendsPanel");
        showHomePanel();
	}
	
	public void showPage(String page, boolean defaultDisplay) {
		if (Util.LOG) GWT.log("currentPage is: "+getCurrentPage()+", showPage called with: "+page);

		if (page.endsWith(Util.PAGE_HOME)) {
			if (defaultDisplay) {
				getHomePanel().showDefaultPanel();
			} else {
				getHomePanel().showUserWelcomePanel(getDataContainer().getUser());
			}
			showHomePanel();
		} else if (page.endsWith(Util.PAGE_FRIENDS)) {
			showFriendsPanel();
			if (defaultDisplay) {
				getFriendsPanel().initFriendsPanel();
			} else {
				getFriendsPanel().displayLoggedInFriendsPanel();
			}
		} else if (page.endsWith(Util.PAGE_QUERY)) {
			showSandboxPanel();
		} else if (page.endsWith(Util.PAGE_ABOUT)) {
			showAboutPanel();
		} else if (page.endsWith(Util.PAGE_NOTES)) {
			if (defaultDisplay) {
				getNotesDisplay().initPanels();
			}
			else {
				getNotesDisplay().refreshPanel();
				getNotesController().requestNotes(getDataContainer().getUser().getUserId());
				int i=0;
				for (String friendUserId : getDataContainer().getFriendUserIds()) { // Send out notes request
					getNotesController().requestNotes(friendUserId);
					if (i++>20) break;
				}
			}
			showNotesPanel();
		} else {
			showHomePanel();
		}
	}

	private void showHomePanel() {
		setCurrentPage(Util.PAGE_HOME);
		getContentPanel().setWidget(getHomePanel());
	}
	
	private void showNotesPanel() {
		setCurrentPage(Util.PAGE_NOTES);
		getContentPanel().setWidget(getNotesDisplay());
	}
	
	private void showFriendsPanel() {
		setCurrentPage(Util.PAGE_FRIENDS);
		getContentPanel().setWidget(getFriendsContainerPanel());
		getFriendsContainerPanel().selectTab(getFriendsPanel());
	}
	
	private void showSandboxPanel() {
		setCurrentPage(Util.PAGE_QUERY);
		getContentPanel().setWidget(getSandboxPanel());
	}
	
	private void showAboutPanel() {
		setCurrentPage(Util.PAGE_ABOUT);
		getContentPanel().setWidget(getAboutPanel());
	}
	
	public void showInProgressText() {
		getFriendsPanel().showInProgress();
	}
	
	public void showLoading(boolean show) {
		loadingGif.getStyle().setVisibility(show?Visibility.VISIBLE:Visibility.HIDDEN);
	}
	
	private FlowPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new FlowPanel();
		}
		return mainPanel;
	}
	
	private SimplePanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new SimplePanel();
		}
		return contentPanel;
	}
	
	private FlowPanel getHeaderPanel() {
		if (headerPanel == null) {
			headerPanel = new FlowPanel();
			headerPanel.getElement().setId("headerPanel");
			SimplePanel logoPanel = new SimplePanel();
			logoPanel.getElement().setId("logoPanel");
			logoPanel.add(new Image("logo.png"));
			headerPanel.add(logoPanel);
			
			FlowPanel fbPanel = new FlowPanel();
			fbPanel.getElement().setId("fbPanel");
			
			SimplePanel loadingPanel = new SimplePanel();
			loadingPanel.getElement().setId("loadingPanel");
			Image loadingGif = new Image("loading.gif");
			loadingGif.getElement().setId("loadingGif");
			loadingPanel.add(loadingGif);
			
			HTMLPanel fbButton = new HTMLPanel(getLoginHTML());
			fbButton.getElement().setId("fbButton");
			fbPanel.add(loadingPanel);
			fbPanel.add(fbButton);
			headerPanel.add(fbPanel);
		}
		return headerPanel;
	}
	
	private FlowPanel getNavPanel() {
		if (navPanel == null) {
			navPanel = new FlowPanel();
			
			Anchor homeLink = new Anchor();
			homeLink.setHref("#"+Util.PAGE_HOME);
			homeLink.getElement().setId("homeLink");
			
			Anchor friendsLink = new Anchor();
			friendsLink.setHref("#"+Util.PAGE_FRIENDS);
			friendsLink.getElement().setId("friendsLink");
			
			Anchor notesLink = new Anchor();
			notesLink.setHref("#"+Util.PAGE_NOTES);
			notesLink.getElement().setId("notesLink");
			
			Anchor queryLink = new Anchor();
			queryLink.setHref("#"+Util.PAGE_QUERY);
			queryLink.getElement().setId("queryLink");
			
			Anchor aboutLink = new Anchor();
			aboutLink.setHref("#"+Util.PAGE_ABOUT);
			aboutLink.getElement().setId("aboutLink");
			
			navPanel.add(homeLink);
			navPanel.add(friendsLink);
			navPanel.add(notesLink);
			navPanel.add(queryLink);
			navPanel.add(aboutLink);
		}
		return navPanel;
	}
	
	private HomePanel getHomePanel() {
		if (homePanel == null) {
			homePanel = new HomePanel();
		}
		return homePanel;
	}
	
	private FriendsContainerPanel getFriendsContainerPanel() {
		if (friendsContainerPanel == null) {
			friendsContainerPanel = new FriendsContainerPanel();
		}
		return friendsContainerPanel;
	}
	
	private FriendsPanel getFriendsPanel() {
		if (friendsPanel == null) {
			friendsPanel = new FriendsPanel(getNotesController());
		}
		return friendsPanel;
	}
	
	private HashMap<String,FriendPhotosPanel> getFriendPhotosPanelMap() {
		if (friendPhotosPanelMap == null) {
			friendPhotosPanelMap = new HashMap<String,FriendPhotosPanel>();
		}
		return friendPhotosPanelMap;
	}
	
	private SandboxPanel getSandboxPanel() {
		if (sandboxPanel == null) {
			sandboxPanel = new SandboxPanel();
		}
		return sandboxPanel;
	}
	
	private AboutPanel getAboutPanel() {
		if (aboutPanel == null) {
			aboutPanel = new AboutPanel();
		}
		return aboutPanel;
	}
	
	private NotesPanel getNotesDisplay() {
		if (notesDisplay == null) {
			notesDisplay = new NotesPanel(getNotesController());
		}
		return notesDisplay;
	}
	
	private IDataContainer getDataContainer() {
		return dataContainer;
	}
	
	private String getLoginHTML() {
		return "<fb:login-button autologoutlink='true' "+
			"scope='publish_stream,read_stream,user_photos,friends_photos'></fb:login-button>";
	}
	
	public void setFriend(String id) {
		getFriendsContainerPanel().selectTab(getFriendPhotosPanel(id));
	}
	
	public void processUploadedPhotos(String id, ArrayList<Photo> photos) {
		getFriendPhotosPanel(id).processUploadedPhotos(photos);
	}
	
	public void addAlbum(String id, Album album) {
		getFriendPhotosPanel(id).addAlbum(id, album);
	}
	
	public void refreshPhotos(String id, Photo photo) {
		getFriendPhotosPanel(id).refreshPhotos(photo);
	}
	
	public void addAlbumPhotos(String id, String albumId, ArrayList<Photo> photos) {
		getFriendPhotosPanel(id).addAlbumPhotos(albumId, photos);
	}
	
	public void refreshNoteSelection(String userId, String[] notes) {
		getNotesDisplay().refreshNoteSelection(userId, notes);
	}
	
	public String getCurrentPage() {
		return currentPage;
	}
	
	private void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
	private FriendPhotosPanel getFriendPhotosPanel(String id) {
		FriendPhotosPanel friendPhotosPanel = getFriendPhotosPanelMap().get(id);
		if (friendPhotosPanel == null) {
			friendPhotosPanel = new FriendPhotosPanel(getNotesController());
			getFriendPhotosPanelMap().put(id, friendPhotosPanel);
			TabCloseHeader tabCloseHeader = new TabCloseHeader(getDataContainer().getFriendFromList(id).getName(), id);
			getFriendsContainerPanel().add(friendPhotosPanel, tabCloseHeader);
			friendPhotosPanel.setFriend(id);
			friendPhotosPanel.getElement().setId("friendProfilePanel");
		}
		return friendPhotosPanel;
	}
	
	private void closeTab(String id) {
		getFriendsContainerPanel().remove(getFriendPhotosPanel(id));
		getFriendPhotosPanelMap().remove(id);
	}
	
	private class TabCloseHeader extends FlowPanel {
		private String tabUserId;
		
		public TabCloseHeader(String title, String id) {
			this.setStyleName("tabCloseHeader");
			tabUserId = id;
			Label label = new Label(title);
			label.setStyleName("tabCloseHeaderLabel");
			add(label);
			Button x = new Button("x");
			x.setStyleName("tabCloseHeaderButton");
			x.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					closeTab(tabUserId);
				}
			});
			add(x);
		}
	}
}
