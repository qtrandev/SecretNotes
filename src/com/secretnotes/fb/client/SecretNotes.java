package com.secretnotes.fb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.secretnotes.fb.client.data.DataContainer;
import com.secretnotes.fb.client.data.IDataContainer;

public class SecretNotes implements EntryPoint {

	public void onModuleLoad() {
		IDataContainer dataContainer = new DataContainer();
		ICommunicationHandler communicationHandler = new CommunicationHandler();
		IUiHandler uiHandler = new UiHandler(dataContainer);
		IModelController modelController = new ModelController(dataContainer);
		NotesServiceAsync notesService = GWT.create(NotesService.class);
		
		NotesController notesController = new NotesController(modelController, communicationHandler, uiHandler, notesService);
		communicationHandler.setNotesController(notesController);
		uiHandler.setNotesController(notesController);
		notesController.loadModule();
	}
}
