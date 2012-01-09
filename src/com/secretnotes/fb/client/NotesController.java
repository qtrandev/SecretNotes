package com.secretnotes.fb.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.secretnotes.fb.client.data.Album;
import com.secretnotes.fb.client.data.Photo;
import com.secretnotes.fb.client.data.User;

/** Main execution class 
 * @author Quyen Tran
 */
public class NotesController implements INotesController,ValueChangeHandler<String> {

	private ICommunicationHandler commHandler;
	private IUiHandler uiHandler;
	private IModelController modelController;
	private NotesServiceAsync notesService;
	
	private ArrayList<String> persistSuccess = new ArrayList<String>();
	
	private String requestedPage = null;
	
	public NotesController(IModelController modelController, ICommunicationHandler commHandler, IUiHandler uiHandler,
			NotesServiceAsync notesService) {
		this.modelController = modelController;
		this.commHandler = commHandler;
		this.uiHandler = uiHandler;
		this.notesService = notesService;
	}
	
	public void loadModule() {
		if (!GWT.isProdMode()) {
			getCommunicationHandler().setCommunicationId("2402961527"); // APP ID FOR LOCAL TESTING - CHANGE THIS FOR YOUR LOCAL APP
			Util.LOG = true; // Change to false to disable GWT.log() calls
		}
		History.addValueChangeHandler(this);
		
		getCommunicationHandler().initCommunication();
		getUiHandler().initPanels();
        
        callFacebook();
	}
	
	private void callFacebook() {
		//
		// Callback used when session status is changed
		//
		class SessionChangeCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				// Make sure cookie is set so we can use the non async method
				if (Util.LOG) GWT.log("Session change detected. Calling checkSession().");
				if (!getCommunicationHandler().checkSession()) {
					getModelController().resetUser(); // Reset user since may have logged out
					if (Util.LOG) GWT.log("User logged out.");
				}
				requestPage(getUiHandler().getCurrentPage());
			}
		}

		//
		// Get notified when user session is changed
		//
		SessionChangeCallback sessionChangeCallback = new SessionChangeCallback();
		getCommunicationHandler().subscribeSessionChange(sessionChangeCallback);

		// Callback used when checking login status
		class LoginStatusCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("LoginStatusCallback detected - onSuccess(): requestPage() called with "+Window.Location.getHash());
				requestPage(getPageToShow(Window.Location.getHash()));
			}
		}
		LoginStatusCallback loginStatusCallback = new LoginStatusCallback();

		// Get login status
		getCommunicationHandler().registerLoginCallback(loginStatusCallback);
	}
	
	private void requestLoggedInUserInfo() {
		class MeCallback extends Callback<JavaScriptObject> {
			public void onSuccess ( JavaScriptObject response ) {
				if (Util.LOG) GWT.log("Received response for \"\\me\" call. Showing user data.");
				handleLoggedInUserInfoResponse(response);
			}
		}
		getCommunicationHandler().sendCurrentUserRequest(new MeCallback());
	}
	
	private void requestFriendsList() {
		getUiHandler().showInProgressText();
		class FriendsCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG)
					GWT.log("List of friends request came back.");
				handleFriendsListResponse(response);
				// Data retrieval process done.
				if (requestedPage != null) {
					requestPage(requestedPage);
				}
				getUiHandler().showLoading(false);
			}
		}
		if (Util.LOG) GWT.log("Sending out request for list of friends.");
		getCommunicationHandler().sendCurrentUserFriendsRequest(new FriendsCallback());
	}
	
	private void handleFriendsListResponse(JavaScriptObject response) {
		JSOModel jso = response.cast();
		getModelController().resetLookupList();
		JsArray<JSOModel> friends = jso.getArray(Util.ARRAY_DATA);
		populateFriendList(friends);
		String friendName;
		String id;
		for (int i=0; i<friends.length(); i++) {
			friendName = friends.get(i).get(Util.USER_NAME);
			id = friends.get(i).get(Util.USER_ID);
			getModelController().insertLookupList(friendName, id);
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
			getModelController().addToFriendList(friend);
		}
	}
	
	public void requestPhotos(final String id) {
		class PictureCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("Received photo request response. Showing photos.");
				getUiHandler().setFriend(id);
				processPhotosRequest(id, response);
				requestAlbums(id);
			}
		}
		getCommunicationHandler().sendUploadedPhotosRequest(id, new PictureCallback());
	}
	
	private void processPhotosRequest(String id, JavaScriptObject response) {
		JSOModel jso = response.cast();
		JsArray<JSOModel> photos = jso.getArray(Util.ARRAY_DATA);
		Photo photo;
		HashMap<String,String> properties;
		ArrayList<Photo> photoList = new ArrayList<Photo>();
		for (int i=0; i<photos.length(); i++) {
			properties = new HashMap<String, String>();
			properties.put(Util.PHOTO_ID, photos.get(i).get(Util.PHOTO_ID));
			properties.put(Util.PHOTO_PICTURE, photos.get(i).get(Util.PHOTO_PICTURE));
			properties.put(Util.PHOTO_SOURCE, photos.get(i).get(Util.PHOTO_SOURCE));
			photo = new Photo(properties);
			photoList.add(photo);
		}
		getUiHandler().processUploadedPhotos(id, photoList);
	}
	
	public void requestAlbums(final String id) {
		class AlbumCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("Received albums request response. Showing albums.");
				processAlbumsRequest(id, response);
			}
		}
		getCommunicationHandler().sendAlbumsRequest(id, new AlbumCallback());
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
			getModelController().addAlbum(userId, album);
			getUiHandler().addAlbum(userId, album);
			requestPhotoLink(userId, album.getCoverPhotoId());
		}
	}
	
	public void requestPhotoLink(final String id, String photoId) {
		class PhotoLinkCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("Received photo link request response. Processing photo link.");
				processPhotoLink(id, response);
			}
		}
		getCommunicationHandler().sendPhotoRequest(photoId, new PhotoLinkCallback());
	}
	
	public void processPhotoLink(String id, JavaScriptObject response) {
		JSOModel jso = response.cast();
		
		HashMap<String,String> properties = new HashMap<String, String>();
		properties.put(Util.PHOTO_ID, jso.get(Util.PHOTO_ID));
		properties.put(Util.PHOTO_PICTURE, jso.get(Util.PHOTO_PICTURE));
		properties.put(Util.PHOTO_SOURCE, jso.get(Util.PHOTO_SOURCE));
		
		
		Photo photo = new Photo(properties);
		getModelController().addPhoto(photo);
		getUiHandler().refreshPhotos(id, photo);
	}
	
	public void requestAlbumPhotos(final String id, final String albumId) {
		class AlbumPhotosCallback extends Callback<JavaScriptObject> {
			public void onSuccess(JavaScriptObject response) {
				if (Util.LOG) GWT.log("Received albums request response. Showing albums.");
				processAlbumPhotosRequest(id, albumId, response);
			}
		}
		getCommunicationHandler().sendAlbumPhotosRequest(albumId, new AlbumPhotosCallback());
	}
	
	public void processAlbumPhotosRequest(String id, String albumId, JavaScriptObject response) {
		JSOModel jso = response.cast();
		JsArray<JSOModel> albums = jso.getArray(Util.ARRAY_DATA);

		Photo photo;
		HashMap<String, String> properties;
		ArrayList<Photo> photos = new ArrayList<Photo>();
		for (int i=0; i<albums.length(); i++) {
			properties = new HashMap<String, String>();
			properties.put(Util.PHOTO_ID, albums.get(i).get(Util.PHOTO_ID));
			properties.put(Util.PHOTO_PICTURE, albums.get(i).get(Util.PHOTO_PICTURE));
			properties.put(Util.PHOTO_SOURCE, albums.get(i).get(Util.PHOTO_SOURCE));
			
			photo = new Photo(properties);
			getModelController().addPhoto(photo);
			photos.add(photo);
		}
		getUiHandler().addAlbumPhotos(id, albumId, photos);
	}

	public void requestNotes(final String userId) {
		if (Util.LOG) GWT.log("requestNotes called for "+getModelController().getNameFromId(userId)+" ("+userId+").");
		getNotesService().getNotes(userId, new AsyncCallback<String[]>() {
			public void onFailure(Throwable error) {
				if (Util.LOG) GWT.log("Received ERROR with requestNotes() for "+userId+".");
			}

			public void onSuccess(String[] notes) {
				String print = "";
				for (String note : notes) {
					print=print+note+", ";
				}
				if (Util.LOG) GWT.log("Received notes for "+userId+": "+print);
				getModelController().updateNotesInFriendsList(userId, notes);
				getUiHandler().refreshNoteSelection(userId, notes);
			}
		});
	}
	
	public void persistNotes(final String userId, String userName, String[] notes, String ownerId, final boolean showAlert) {
		if (Util.LOG) GWT.log("persistNotes called for "+getModelController().getNameFromId(userId)+" ("+userId+").");
		getNotesService().setNotes(userId, userName, ownerId, notes, new AsyncCallback<Void>() {
			  public void onFailure(Throwable error) {
				  if (Util.LOG) GWT.log("Received ERROR with persistNotes() for "+userId+".");
			  }
			  public void onSuccess(Void ignore) {
				  if (Util.LOG) GWT.log("Notes persisted successfully for "+userId+".");
				  if (showAlert) Window.alert("Notes saved for "+getModelController().getNameFromId(userId)+" ("+userId+")!");
			  }
		  });
	}
	
	public void persistNotes(
			final HashMap<String, String[]> userNotesMap, 
			HashMap<String, String> userNamesMap, 
			String ownerId) {
		persistSuccess.clear();
		for (final String userId : userNotesMap.keySet()) {
			getNotesService().setNotes(userId, userNamesMap.get(userId), ownerId, userNotesMap.get(userId), new AsyncCallback<Void>() {
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
	
	private void handleLoggedInUserInfoResponse(JavaScriptObject response) {
		JSOModel jso = response.cast();
		getModelController().setUser(createUser(jso));
		requestFriendsList();
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
	
	public IModelController getModelController() {
		return modelController;
	}
	
	private ICommunicationHandler getCommunicationHandler() {
		return commHandler;
	}
	
	private IUiHandler getUiHandler() {
		return uiHandler;
	}
	
    private NotesServiceAsync getNotesService() {
    	return notesService;
    }
	
	private String getPageToShow(String token) {
		token = token.replace("#", "");
		if (token == null || "".equals(token) || "#".equals(token)) {
			token = Util.PAGE_HOME;
		}
		return token;
	}
	
	private void requestPage(String page) {
		requestedPage = null;
		if (getCommunicationHandler().checkSession()) {
			if (getModelController().checkUserInfoExists()) {
				getUiHandler().showPage(page, false); // Show full data display
			} else { // Request the user data
				requestedPage = page;
				getUiHandler().showLoading(true);
				requestLoggedInUserInfo();
			}
		} else {
			getUiHandler().showPage(page, true); // Show default display
		}
	}
	
	public void onValueChange(ValueChangeEvent<String> event) {
		requestPage(getPageToShow(event.getValue()));
	}
	
	public User getUser() {
		return getModelController().getUser();
	}
	
	public ArrayList<String> getFriendNames() {
		return getModelController().getFriendNames();
	}
	
	public String getIdFromName(String name) {
		return getModelController().getIdFromName(name);
	}
	
	public User getFriendFromList(String userId) {
		return getModelController().getFriendFromList(userId);
	}
	
	public ArrayList<String> getFriendUserIds() {
		return getModelController().getFriendUserIds();
	}
}
