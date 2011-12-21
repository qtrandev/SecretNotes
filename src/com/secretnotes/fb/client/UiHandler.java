package com.secretnotes.fb.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.secretnotes.fb.client.data.Album;
import com.secretnotes.fb.client.data.IDataContainer;
import com.secretnotes.fb.client.data.Photo;
import com.secretnotes.fb.client.data.User;

public class UiHandler implements IUiHandler {

	private IDataContainer dataContainer;
	// UI components
	private FlowPanel mainPanel;
	private SimplePanel contentPanel;
	private FlowPanel headerPanel;
	private FlowPanel navPanel;
	private FlowPanel homePanel;
	private FriendsContainerPanel friendsContainerPanel;
	private FriendsPanel friendsPanel;
	private FriendPhotosPanel friendProfilePanel;
	private FlowPanel queryPanel;
	private NotesPanel notesDisplay;
	private HTML welcomeHtml;
	private String currentPage = Util.PAGE_HOME;
	private Element loadingGif;
	
	public UiHandler(IDataContainer dataContainer) {
		this.dataContainer = dataContainer;
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
		
		initHomePanel();
        initQueryPanel();
        getFriendsContainerPanel().add(getFriendsPanel(), "Friends");
        getFriendsContainerPanel().setHeight("600px");
        getFriendsPanel().getElement().setId("friendsPanel");
        getFriendsContainerPanel().add(getFriendProfilePanel(), getFriendProfilePanel().getDisplayTitle());
        getFriendProfilePanel().getElement().setId("friendProfilePanel");
        showHomePanel();
	}
	
	private void initHomePanel() {
		if (Util.LOG) GWT.log("Initialize default home panel.");
		getHomePanel().clear();
        welcomeHtml = new HTML(Util.MESSAGES.hello_basic());
        Anchor sourceLink = new Anchor("Facebook Link");
        sourceLink.addStyleName("sourceLink");
        sourceLink.setTarget("blank");
        sourceLink.setHref("http://www.facebook.com");
        getHomePanel().add(welcomeHtml);
        getHomePanel().add(sourceLink);
	}
	
	private void initQueryPanel() {
		getQueryPanel().add(new HTML(Util.MESSAGES.page_welcome("Query")));
		getQueryPanel().add(new HTML("Nothing here yet"));
		getQueryPanel().add(new HTML("<hr/>More to come"));
	}
	
	public void showPage(String page, boolean defaultDisplay) {
		if (Util.LOG) GWT.log("currentPage is: "+getCurrentPage()+", showPage called with: "+page);

		if (page.endsWith(Util.PAGE_HOME)) {
			if (defaultDisplay) {
				initHomePanel();
			} else {
				initHomePanel();
				populateHomePanel();
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
			showQueryPanel();
		} else if (page.endsWith(Util.PAGE_NOTES)) {
			if (defaultDisplay) {
				getNotesDisplay().initPanels();
			}
			else {
				getNotesDisplay().refreshPanel();
				ServerRequest.getServer().requestNotes(getDataContainer().getUser().getUserId());
				int i=0;
				for (User friend : getDataContainer().getFriendList()) { // Send out notes request
					ServerRequest.getServer().requestNotes(friend.getUserId());
					if (i++>20) break;
				}
			}
			showNotesPanel();
		} else {
			initHomePanel();
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
	
	private void showQueryPanel() {
		setCurrentPage(Util.PAGE_QUERY);
		getContentPanel().setWidget(getQueryPanel());
	}
	
	public void showInProgressText() {
		getFriendsPanel().showInProgress();
	}
	
	public void showLoading(boolean show) {
		loadingGif.getStyle().setVisibility(show?Visibility.VISIBLE:Visibility.HIDDEN);
	}
	
	private void populateHomePanel() {
		User user = getDataContainer().getUser();
		welcomeHtml.setHTML (Util.MESSAGES.hello(user.getName()) + 
				"ID: " + user.getUserId() + "<br>" +
				"FIRST NAME: " + user.getFirstName() + "<br>" +
				"LAST NAME: " + user.getLastName() + "<br>" +
				"LINK: " + user.getLink() + "<br>" +
				"GENDER: " + user.getGender() + "<br>" +
				"TIMEZONE: " + user.getTimezone() + "<br>" +
				"LOCALE: " + user.getLocale() + "<br>" +
				"VERIFIED: " + user.getVerified() + "<br>" +
				"UPDATED TIME: " + user.getUpdatedTime() + "<br>" +
				"TYPE: " + user.getType()
			);
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
			
			navPanel.add(homeLink);
			navPanel.add(friendsLink);
			navPanel.add(notesLink);
			navPanel.add(queryLink);
		}
		return navPanel;
	}
	
	private FlowPanel getHomePanel() {
		if (homePanel == null) {
			homePanel = new FlowPanel();
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
			friendsPanel = new FriendsPanel(getDataContainer());
		}
		return friendsPanel;
	}
	
	private FriendPhotosPanel getFriendProfilePanel() {
		if (friendProfilePanel == null) {
			friendProfilePanel = new FriendPhotosPanel(getDataContainer());
		}
		return friendProfilePanel;
	}
	
	private FlowPanel getQueryPanel() {
		if (queryPanel == null) {
			queryPanel = new FlowPanel();
		}
		return queryPanel;
	}
	
	private NotesPanel getNotesDisplay() {
		if (notesDisplay == null) {
			notesDisplay = new NotesPanel(getDataContainer());
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
		int tabIndex = getFriendsContainerPanel().getWidgetIndex(getFriendProfilePanel());
		getFriendsContainerPanel().setTabText(tabIndex, getDataContainer().getFriendFromList(id).getName());
		getFriendsContainerPanel().selectTab(getFriendProfilePanel());
		getFriendProfilePanel().setFriend(id);
	}
	
	public void processUploadedPhotos(ArrayList<Photo> photos) {
		getFriendProfilePanel().processUploadedPhotos(photos);
	}
	
	public void addAlbum(Album album) {
		getFriendProfilePanel().addAlbum(album);
	}
	
	public void refreshPhotos(Photo photo) {
		getFriendProfilePanel().refreshPhotos(photo);
	}
	
	public void addAlbumPhotos(String albumId, ArrayList<Photo> photos) {
		getFriendProfilePanel().addAlbumPhotos(albumId, photos);
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
}
