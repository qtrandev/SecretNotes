package com.secretnotes.fb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CommunicationHandler implements ICommunicationHandler {
	
	private INotesController notesController;
	
	private FBCore fbCore;
	private FBEvent fbEvent;
	private String APP_ID;
	private boolean status;
	private boolean xfbml;
	private boolean cookie;
	
	private static int PHOTO_UPLOAD_LIMIT = 25;
	private static int ALBUM_LIMIT = 25;
	private static int PHOTO_ALBUM_LIMIT = 100;
	
	public CommunicationHandler() {
		fbCore = GWT.create(FBCore.class);
		fbEvent = GWT.create(FBEvent.class);
		APP_ID = "259802324063697"; // GOOGLE APP ENGINE TESTING - CHANGE 2 USES OF THIS FOR YOUR APP!
		status = true;
		xfbml = true;
		cookie = true;
	}
	
	public void setNotesController(INotesController notesController) {
		this.notesController = notesController;
	}
	
	private INotesController getNotesController() {
		return notesController;
	}
	
	private FBCore getFbCore() {
		return fbCore;
	}
	
	private FBEvent getFbEvent() {
		return fbEvent;
	}
	
	public void initCommunication() {
		getFbCore().init(APP_ID, status, cookie, xfbml);
	}
	
	public void setCommunicationId(String id) {
		APP_ID = id;
	}
	
	public void subscribeSessionChange(AsyncCallback<JavaScriptObject> sessionChangeCallback) {
		getFbEvent().subscribe("auth.authResponseChange", sessionChangeCallback);
	}
	
	public void registerLoginCallback(AsyncCallback<JavaScriptObject> callback) {
		getFbCore().getLoginStatus(callback);
	}

	/** 
	 * Check for a Facebook session.
	 * @return true if the session is valid.
	 */
	public boolean checkSession() {
		return getFbCore().getSession() != null;
	}
	
	public void sendRequest(String request, AsyncCallback<JavaScriptObject> callback) {
		getFbCore().api(request, callback);
	}
	
	public void sendCurrentUserRequest(AsyncCallback<JavaScriptObject> callback) {
		sendRequest("/me", callback);
	}
	
	public void sendCurrentUserFriendsRequest(AsyncCallback<JavaScriptObject> callback) {
		sendRequest("/me/friends", callback);
	}
	
	public void sendUploadedPhotosRequest(String userId, AsyncCallback<JavaScriptObject> callback) {
		sendRequest("/" +userId+ "/photos?limit="+PHOTO_UPLOAD_LIMIT, callback);
	}
	
	public void sendAlbumsRequest(String userId, AsyncCallback<JavaScriptObject> callback) {
		sendRequest("/" +userId+ "/albums?limit="+ALBUM_LIMIT, callback);
	}
	
	public void sendPhotoRequest(String photoId, AsyncCallback<JavaScriptObject> callback) {
		sendRequest("/"+photoId, callback);
	}
	
	public void sendAlbumPhotosRequest(String albumId, AsyncCallback<JavaScriptObject> callback) {
		sendRequest("/" +albumId+ "/photos?limit="+PHOTO_ALBUM_LIMIT, callback);
	}
}
