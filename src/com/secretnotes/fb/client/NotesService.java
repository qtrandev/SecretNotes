package com.secretnotes.fb.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("notes")
public interface NotesService extends RemoteService {

	public String[] getNotes(String userId);
	public void setNotes(String userId, String userName, String ownerId, String[] notes);
}
