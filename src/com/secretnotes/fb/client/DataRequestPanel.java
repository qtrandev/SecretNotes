package com.secretnotes.fb.client;

import com.google.gwt.user.client.ui.FlowPanel;

public abstract class DataRequestPanel extends FlowPanel implements
		DataRequester {
	
	private INotesController notesController;
	
	public DataRequestPanel(INotesController notesController) {
		super();
		this.notesController = notesController;
	}

	public INotesController getNotesController() {
		return notesController;
	}
}
