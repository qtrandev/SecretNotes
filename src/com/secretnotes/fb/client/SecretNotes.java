package com.secretnotes.fb.client;

import com.google.gwt.core.client.EntryPoint;
import com.secretnotes.fb.client.data.DataContainer;

public class SecretNotes implements EntryPoint {

	public void onModuleLoad() {
		DataContainer dataContainer = new DataContainer();
		CommunicationHandler communicationHandler = new CommunicationHandler();
		NotesController notesController = new NotesController(dataContainer, communicationHandler);
		notesController.loadModule();
	}
}
