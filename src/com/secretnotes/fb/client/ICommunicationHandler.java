package com.secretnotes.fb.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ICommunicationHandler {
	public void initCommunication();
	public void setCommunicationId(String id);
	public void subscribeSessionChange(AsyncCallback<JavaScriptObject> sessionChangeCallback);
	public boolean checkSession();
	public void sendRequest(String request, AsyncCallback<JavaScriptObject> callback);
	public void sendCurrentUserRequest(AsyncCallback<JavaScriptObject> callback);
	public void sendCurrentUserFriendsRequest(AsyncCallback<JavaScriptObject> callback);
	public void sendUploadedPhotosRequest(String userId, AsyncCallback<JavaScriptObject> callback);
	public void sendAlbumsRequest(String userId, AsyncCallback<JavaScriptObject> callback);
	public void sendPhotoRequest(String photoId, AsyncCallback<JavaScriptObject> callback);
	public void sendAlbumPhotosRequest(String albumId, AsyncCallback<JavaScriptObject> callback);
	public void sendPhotoTagsRequest(String userId, AsyncCallback<JavaScriptObject> callback);
	public void registerLoginCallback(AsyncCallback<JavaScriptObject> callback);
	public void setNotesController(INotesController notesController);
}
