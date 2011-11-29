package com.secretnotes.fb.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NotesServiceAsync {

	public void getNotes(String userId, AsyncCallback<String[]> async);
	public void setNotes(String userId, String userName, String[] notes, AsyncCallback<Void> async);
}
