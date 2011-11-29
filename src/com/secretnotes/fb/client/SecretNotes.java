package com.secretnotes.fb.client;

import com.google.gwt.core.client.EntryPoint;
import com.secretnotes.fb.client.data.DataContainer;

public class SecretNotes implements EntryPoint {

	public void onModuleLoad() {
		DataContainer dataContainer = new DataContainer();
		NotesController notesController = new NotesController(dataContainer);
		notesController.loadModule();
	}
}
