package com.secretnotes.fb.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.secretnotes.fb.client.data.IDataContainer;

/** Main execution class 
 * @author Quyen Tran
 */
public class NotesController implements ValueChangeHandler<String> {

	// Facebook-related initialization
	private String APP_ID = "259802324063697"; // GOOGLE APP ENGINE TESTING - CHANGE 2 USES OF THIS FOR YOUR APP!
	private FBCore fbCore = GWT.create(FBCore.class);
	private FBEvent fbEvent = GWT.create(FBEvent.class);
	private boolean status = true;
	private boolean xfbml = true;
	private boolean cookie = true;
	
	// UI components
	private FlowPanel mainPanel;
	private SimplePanel contentPanel;
	private FlowPanel headerPanel;
	private FlowPanel navPanel;
	private FlowPanel homePanel;
	private FriendsContainerPanel friendsContainerPanel;
	private FriendsPanel friendsPanel;
	private FriendProfilePanel friendProfilePanel;
	private FlowPanel queryPanel;
	private NotesPanel notesDisplay;
	private HTML welcomeHtml;
	private String currentPage = Util.PAGE_HOME;
	private String pendingPage = null;
	private Element loadingGif;
	
	// Model components
	private IDataContainer dataContainer;
	private ArrayList<String> persistSuccess = new ArrayList<String>();
	
	public NotesController(IDataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}
	
	public void loadModule() {
		if (!GWT.isProdMode()) {
			APP_ID = "2402961527"; // APP ID FOR LOCAL TESTING - CHANGE THIS FOR YOUR LOCAL APP
			Util.LOG = true; // Change to false to disable GWT.log() calls
		}
		History.addValueChangeHandler(this);
		ServerRequest.setSecretNotes(this);
		
		initFacebook();
		initPanels();
	}
	
	private void initFacebook() {
		fbCore.init(APP_ID, status, cookie, xfbml);
	}
	
	private void initPanels() {
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
        getFriendsContainerPanel().add(getFriendProfilePanel(), "Photos");
        getFriendProfilePanel().getElement().setId("friendProfilePanel");
        showHomePanel();
        
        callFacebook();
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
	
	private void callFacebook() {
		//
		// Callback used when session status is changed
		//
		class SessionChangeCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				// Make sure cookie is set so we can use the non async method
				if (Util.LOG) GWT.log("Session change detected. Calling checkSession().");
				if (!checkSession(currentPage)) {
					getDataContainer().setUser(null); // Reset user since may have logged out
					getDataContainer().getFriendList().clear();
					getDataContainer().getIdList().clear();
					getDataContainer().getNameList().clear();
					if (Util.LOG) GWT.log("User logged out.");
					showPage(currentPage);
				}
			}
		}

		//
		// Get notified when user session is changed
		//
		SessionChangeCallback sessionChangeCallback = new SessionChangeCallback();
		fbEvent.subscribe("auth.sessionChange", sessionChangeCallback);

		// Callback used when checking login status
		class LoginStatusCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				showPage(Window.Location.getHash());
			}
		}
		LoginStatusCallback loginStatusCallback = new LoginStatusCallback();

		// Get login status
		//fbCore.getLoginStatus(loginStatusCallback);
        //showPage(Window.Location.getHash());
	}
	
	private void showPage(String token) {
		if (Util.LOG) GWT.log("currentPage is: "+currentPage+", showPage called with token: "+token);
		pendingPage = null;
		token = token.replace("#", "");

		if (token == null || "".equals(token) || "#".equals(token)) {
			token = Util.PAGE_HOME;
		}

		if (token.endsWith(Util.PAGE_HOME)) {
			if (!checkSession(Util.PAGE_HOME)) {
				initHomePanel();
			} else {
				initHomePanel();
				populateHomePanel();
			}
			showHomePanel();
		} else if (token.endsWith(Util.PAGE_FRIENDS)) {
			showFriendsPanel();
			if (checkSession(Util.PAGE_FRIENDS)) {
				getFriendsPanel().displayLoggedInFriendsPanel();
			} else {
				getFriendsPanel().initFriendsPanel();
			}
		} else if (token.endsWith(Util.PAGE_QUERY)) {
			showQueryPanel();
		} else if (token.endsWith(Util.PAGE_NOTES)) {
			if (checkSession(Util.PAGE_NOTES)) {
				getNotesDisplay().refreshPanel();
				ServerRequest.getServer().requestNotes(getDataContainer().getUser().getUserId());
				int i=0;
				for (User friend : getDataContainer().getFriendList()) { // Send out notes request
					ServerRequest.getServer().requestNotes(friend.getUserId());
					if (i++>20) break;
				}
			} else {
				getNotesDisplay().initPanels();
			}
			showNotesPanel();
		} else {
			if (Util.LOG) GWT.log("User entered unknown link: "+token);
			Window.alert("Unknown url " + token);
		}
	}
	
	/** 
	 * Check for a Facebook session.
	 * @return true if the session is valid.
	 */
	public boolean checkSession(String pendingPage) {
		if (Util.LOG) GWT.log("checkSession() called with pending page: "+pendingPage);
		boolean result = true;
		if ( fbCore.getSession() == null ) {
			if (Util.LOG) GWT.log("No session.");
			result = false;
        } else {
        	if (getDataContainer().getUser() == null) {
        		result = false;
        		if (Util.LOG) GWT.log("User is null. Calling requestLoggedInUserInfo().");
        		this.pendingPage = pendingPage; // Notify that we have to get data
        		if (Util.LOG) GWT.log("Pending has been set as: "+pendingPage);
        		showLoading(true);
        		requestLoggedInUserInfo();
        	}
        }
		return result;
	}
	
	private void requestLoggedInUserInfo() {
		class MeCallback extends Callback<JavaScriptObject> {
			public void onSuccess ( JavaScriptObject response ) {
				if (Util.LOG) GWT.log("Received response for \"\\me\" call. Showing user data.");
				handleLoggedInUserInfoResponse(response);
			}
		}
		fbCore.api("/me", new MeCallback());
	}
	
	private void requestFriendsList() {
		getFriendsPanel().showInProgress();
		class FriendsCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG)
					GWT.log("List of friends request came back.");
				handleFriendsListResponse(response);
				// Data retrieval process done.
				if (pendingPage != null) {
					showLoading(false);
					showPage(pendingPage);
				}
			}
		}
		if (Util.LOG) GWT.log("Sending out request for list of friends.");
		fbCore.api("/me/friends", new FriendsCallback());
	}
	
	private void handleFriendsListResponse(JavaScriptObject response) {
		JSOModel jso = response.cast();
		getDataContainer().getIdList().clear();
		getDataContainer().getIdList().put(getDataContainer().getUser().getName(), getDataContainer().getUser().getUserId());
		getDataContainer().getNameList().clear();
		getDataContainer().getNameList().put(getDataContainer().getUser().getUserId(), getDataContainer().getUser().getName());
		JsArray<JSOModel> friends = jso.getArray(Util.ARRAY_DATA);
		populateFriendList(friends);
		String friendName;
		String id;
		for (int i=0; i<friends.length(); i++) {
			friendName = friends.get(i).get(Util.USER_NAME);
			id = friends.get(i).get(Util.USER_ID);
			getDataContainer().getIdList().put(friendName,id);
			getDataContainer().getNameList().put(id,friendName);
		}
	}
	
	private void populateFriendList(JsArray<JSOModel> friends) {
		User friend;
		JSOModel item;
		for (int i=0; i<friends.length(); i++) {
			item = friends.get(i);
			friend = new User();
			friend.setName(item.get(Util.USER_NAME));
			friend.setUserId(item.get(Util.USER_ID));
			friend.setFirstName(item.get(Util.USER_FIRST_NAME));
			friend.setLastName(item.get(Util.USER_LAST_NAME));
			friend.setLink(item.get(Util.USER_LINK));
			friend.setProfilePic("http://graph.facebook.com/" + friend.getUserId() + "/picture?type=large");
			friend.setGender(item.get(Util.USER_GENDER));
			getDataContainer().getFriendList().add(friend);
		}
	}
	
	public void requestPhotos(final String id) {
		class PictureCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("Received photo request response. Showing photos.");
				getFriendsContainerPanel().selectTab(getFriendProfilePanel());
				getFriendProfilePanel().setFriend(id);
				getFriendProfilePanel().processPhotosRequest(response);
				requestAlbums(id);
			}
		}
		fbCore.api("/" +id+ "/photos", new PictureCallback());
	}
	
	public void requestAlbums(final String id) {
		class AlbumCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("Received albums request response. Showing albums.");
				processAlbumsRequest(id, response);
			}
		}
		fbCore.api("/" +id+ "/albums", new AlbumCallback());
	}
	
	public void processAlbumsRequest(String userId, JavaScriptObject response) {
		JSOModel jso = response.cast();
		JsArray<JSOModel> albums = jso.getArray(Util.ARRAY_DATA);
		Album album;
		HashMap<String,String> properties;
		for (int i=0; i<albums.length(); i++) {
			properties = new HashMap<String, String>();
			properties.put(Util.ALBUM_ID, albums.get(i).get(Util.ALBUM_ID));
			properties.put(Util.ALBUM_NAME, albums.get(i).get(Util.ALBUM_NAME));
			properties.put(Util.ALBUM_COUNT, albums.get(i).get(Util.ALBUM_COUNT));
			properties.put(Util.ALBUM_COVER_PHOTO_ID, albums.get(i).get(Util.ALBUM_COVER_PHOTO_ID));
			album = new Album(properties);
			getDataContainer().addAlbum(userId, album);
			requestPhotoLink(album.getCoverPhotoId());
		}
	}
	
	public void requestPhotoLink(String photoId) {
		class PhotoLinkCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("Received photo link request response. Processing photo link.");
				processPhotoLink(response);
			}
		}
		fbCore.api("/"+photoId, new PhotoLinkCallback());
	}
	
	public void processPhotoLink(JavaScriptObject response) {
		JSOModel jso = response.cast();
		
		HashMap<String,String> properties = new HashMap<String, String>();
		properties.put(Util.PHOTO_ID, jso.get(Util.PHOTO_ID));
		properties.put(Util.PHOTO_PICTURE, jso.get(Util.PHOTO_PICTURE));
		properties.put(Util.PHOTO_SOURCE, jso.get(Util.PHOTO_SOURCE));
		Photo photo = new Photo(properties);
		getDataContainer().addPhoto(photo);
		getFriendProfilePanel().add(new HTML("<img src='"+photo.getPicture()+"'>"));
	}
	
	public void requestAlbumPhotos(String id) {
		class PictureCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("Received albums request response. Showing albums.");
				getFriendProfilePanel().processAlbumPhotosRequest(response);
			}
		}
		fbCore.api("/" +id+ "/albums", new PictureCallback());
	}

	public void requestNotes(final String userId, NotesServiceAsync notesService) {
		if (Util.LOG) GWT.log("requestNotes called for "+getDataContainer().getNameList().get(userId)+" ("+userId+").");
		notesService.getNotes(userId, new AsyncCallback<String[]>() {
			public void onFailure(Throwable error) {
				if (Util.LOG) GWT.log("Received ERROR with requestNotes() for "+userId+".");
			}

			public void onSuccess(String[] notes) {
				String print = "";
				for (String note : notes) {
					print=print+note+", ";
				}
				if (Util.LOG) GWT.log("Received notes for "+userId+": "+print);
				updateNotesInFriendsList(userId, notes);
				getNotesDisplay().refreshNoteSelection(userId, notes);
			}
		});
	}
	
	public void persistNotes(final String userId, String userName, String[] notes, String ownerId, final boolean showAlert, NotesServiceAsync notesService) {
		if (Util.LOG) GWT.log("persistNotes called for "+getDataContainer().getNameList().get(userId)+" ("+userId+").");
		notesService.setNotes(userId, userName, ownerId, notes, new AsyncCallback<Void>() {
			  public void onFailure(Throwable error) {
				  if (Util.LOG) GWT.log("Received ERROR with persistNotes() for "+userId+".");
			  }
			  public void onSuccess(Void ignore) {
				  if (Util.LOG) GWT.log("Notes persisted successfully for "+userId+".");
				  if (showAlert) Window.alert("Notes saved for "+getDataContainer().getNameList().get(userId)+" ("+userId+")!");
			  }
		  });
	}
	
	public void persistNotes(
			final HashMap<String, String[]> userNotesMap, 
			HashMap<String, String> userNamesMap, 
			String ownerId,
			NotesServiceAsync notesService) {
		persistSuccess.clear();
		for (final String userId : userNotesMap.keySet()) {
			notesService.setNotes(userId, userNamesMap.get(userId), ownerId, userNotesMap.get(userId), new AsyncCallback<Void>() {
				  public void onFailure(Throwable error) {
					  Window.alert("ERROR: Only "+persistSuccess.size()+" friends' notes have been saved");
				  }
				  public void onSuccess(Void ignore) {
					  persistSuccess.add(userId);
					  if (persistSuccess.size() == userNotesMap.size()) {
						  Window.alert("All "+persistSuccess.size()+" friends' notes have been saved");
					  }
				  }
			  });
		}
	}
	
	private void updateNotesInFriendsList(String userId, String[] notes) {
		if (userId.equals(getDataContainer().getUser().getUserId())) { // Handle main user
			getDataContainer().getUser().setNotes(notes);
			return;
		}
		for (User user : getDataContainer().getFriendList()) {
			if (userId.equals(user.getUserId())) {
				user.setNotes(notes);
				break;
			}
		}
	}

	private void showHomePanel() {
		currentPage = Util.PAGE_HOME;
		getContentPanel().setWidget(getHomePanel());
	}
	
	private void showNotesPanel() {
		currentPage = Util.PAGE_NOTES;
		getContentPanel().setWidget(getNotesDisplay());
	}
	
	private void showFriendsPanel() {
		currentPage = Util.PAGE_FRIENDS;
		getContentPanel().setWidget(getFriendsContainerPanel());
		getFriendsContainerPanel().selectTab(getFriendsPanel());
	}
	
	private void showQueryPanel() {
		currentPage = Util.PAGE_QUERY;
		getContentPanel().setWidget(getQueryPanel());
	}
	
	private void handleLoggedInUserInfoResponse(JavaScriptObject response) {
		JSOModel jso = response.cast();
		getDataContainer().setUser(createUser(jso));
		requestFriendsList();
	}
	
	private void showLoading(boolean show) {
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
	
	private User createUser(JSOModel jso) {
		User user = new User();
		user.setUserId(jso.get(Util.USER_ID));
		user.setName(jso.get(Util.USER_NAME));
		user.setFirstName(jso.get(Util.USER_FIRST_NAME));
		user.setLastName(jso.get(Util.USER_LAST_NAME));
		user.setLink(jso.get(Util.USER_LINK));
		user.setGender(jso.get(Util.USER_GENDER));
		user.setTimezone(jso.get(Util.USER_TIMEZONE));
		user.setLocale(jso.get(Util.USER_LOCALE));
		user.setVerified(jso.get(Util.USER_VERIFIED));
		user.setUpdatedTime(jso.get(Util.USER_UPDATED_TIME));
		user.setType(jso.get(Util.USER_TYPE));
		user.setProfilePic("http://graph.facebook.com/"+user.getUserId()+"/picture?type=large");
		return user;
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
			
			HTMLPanel fbButton = new HTMLPanel("<fb:login-button autologoutlink='true' "+
					"perms='publish_stream,read_stream,user_photos,friends_photos'></fb:login-button>");
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
	
	private FriendProfilePanel getFriendProfilePanel() {
		if (friendProfilePanel == null) {
			friendProfilePanel = new FriendProfilePanel(getDataContainer());
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
	
	public IDataContainer getDataContainer() {
		return dataContainer;
	}
	
	public void onValueChange(ValueChangeEvent<String> event) {
		showPage(event.getValue());
	}

}
