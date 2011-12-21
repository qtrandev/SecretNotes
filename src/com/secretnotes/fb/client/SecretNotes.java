package com.secretnotes.fb.client;

import com.google.gwt.core.client.EntryPoint;
import com.secretnotes.fb.client.data.DataContainer;

public class SecretNotes implements EntryPoint {

	public void onModuleLoad() {
		DataContainer dataContainer = new DataContainer();
		CommunicationHandler communicationHandler = new CommunicationHandler();
		UiHandler uiHandler = new UiHandler(dataContainer);
		NotesController notesController = new NotesController(dataContainer, communicationHandler, uiHandler);
		notesController.loadModule();
	}
}
