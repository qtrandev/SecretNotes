package com.secretnotes.fb.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;

public class ServerRequest {
	
	private NotesController secretNotes;
	private NotesServiceAsync notesService;
	
	private ServerRequest() {
		notesService = GWT.create(NotesService.class);
	}

    private static class ServerRequestHolder { 
            public static final ServerRequest instance = new ServerRequest();
    }

    public static ServerRequest getServer() {
            return ServerRequestHolder.instance;
    }
    
    private NotesServiceAsync getNotesService() {
    	return notesService;
    }

	public static void setSecretNotes(NotesController secretNotes) {
		getServer().secretNotes = secretNotes;
	}
	
	public void persistNotes(String userId, String userName, String[] notes, boolean showAlert) {
		secretNotes.persistNotes(userId, userName, notes, showAlert, getNotesService());
	}
	
	public void persistNotes(HashMap<String, String[]> userNotesMap, HashMap<String, String> userNamesMap) {
		secretNotes.persistNotes(userNotesMap, userNamesMap, getNotesService());
	}
	
	public void requestNotes(String userId) {
		secretNotes.requestNotes(userId, getNotesService());
	}
	
	public void requestPhotos(String userId) {
		secretNotes.requestPhotos(userId);
	}
}