package com.secretnotes.fb.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.secretnotes.fb.client.INotesController;
import com.secretnotes.fb.client.NotesPanel;

public class NotesContainerPanel extends FlowPanel {
	
	private INotesController notesController;
	private TabLayoutPanel sandboxContainerPanel;
	private NotesPanel allNotesPanel;
	private FlowPanel simpleNotesPanel;
	private FlowPanel randomNotesPanel;
	
	public NotesContainerPanel(INotesController notesController) {
		super();
		this.notesController = notesController;
		initNotesPanel();
	}
	
	public void refreshNoteSelection(String userId, String[] notes) {
		getAllNotesPanel().refreshNoteSelection(userId, notes);
	}
	
	public void showDefaultPanel() {
		getAllNotesPanel().showNotesWelcomePanel();
	}
	
	public void refreshPanel() {
		getAllNotesPanel().initPanels();
		getAllNotesPanel().refreshPanel();
	}
	
	private void initNotesPanel() {
		add(getAboutContainerPanel());
		getAboutContainerPanel().add(getAllNotesPanel(), "All");
		getAboutContainerPanel().add(getSimpleNotesPanel(), "Simple");
		getAboutContainerPanel().add(getRandomNotesPanel(), "Random");
	}
	
	private TabLayoutPanel getAboutContainerPanel() {
		if (sandboxContainerPanel == null) {
			sandboxContainerPanel = new TabLayoutPanel(30.0, Unit.PX);
			sandboxContainerPanel.setHeight("2000px");
		}
		return sandboxContainerPanel;
	}
	
	private NotesPanel getAllNotesPanel() {
		if (allNotesPanel == null) {
			allNotesPanel = new NotesPanel(notesController);
		}
		return allNotesPanel;
	}
	
	private FlowPanel getSimpleNotesPanel() {
		if (simpleNotesPanel == null) {
			simpleNotesPanel = new FlowPanel();
			simpleNotesPanel.add(new QueryWelcomeContent());
		}
		return simpleNotesPanel;
	}
	
	private FlowPanel getRandomNotesPanel() {
		if (randomNotesPanel == null) {
			randomNotesPanel = new FlowPanel();
			randomNotesPanel.add(new QueryWelcomeContent());
		}
		return randomNotesPanel;
	}
	
	public void showMobileView(int addedHeight) {
		int currentHeight = getAboutContainerPanel().getOffsetHeight();
		getAboutContainerPanel().setHeight(((currentHeight<addedHeight)?addedHeight:(currentHeight*2))+"px");
	}
}
