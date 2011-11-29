package com.secretnotes.fb.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class FriendPanel extends Composite {
	private FlowPanel friendStack;
	private Image userImage;
	private Label nameLabel;
	private Button setNoteButton;
	private FlowPanel notesPanel;
	private ArrayList<CheckBox> notesList;
	private String userId;
	private String userName;
	
	public FriendPanel(final String userId, final String userImg, String name, String[] notesBoxes) {
		this.userId = userId;
		this.userName = name;
		friendStack = new FlowPanel();
		userImage = new Image(userImg);
		nameLabel = new Label(name);
		notesList = new ArrayList<CheckBox>();
		notesPanel = new FlowPanel();
		
		friendStack.add(userImage);
		friendStack.add(nameLabel);
		friendStack.add(notesPanel);
		setNoteButton = new Button(Util.CONSTANTS.set_note());
		friendStack.add(setNoteButton);
		initNotesPanel(notesBoxes);
		initWidget(friendStack);
		setNoteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ServerRequest.getServer().persistNotes(userId, userName, getSelectedNotes(), true);
			}
		});
	}
	
	private void initNotesPanel(String[] notes) {
		notesPanel.clear();
		CheckBox checkBox;
		for (String note : notes) {
			checkBox = new CheckBox(Util.CONSTANTS.getString(note));
			checkBox.setName(note);
			notesList.add(checkBox);
		}
		for (CheckBox note : notesList) {
			notesPanel.add(note);
		}
	}
	
	public String[] getSelectedNotes() {
		ArrayList<String> result = new ArrayList<String>();
		for (CheckBox checkBox : notesList) {
			if (checkBox.getValue()) {
				result.add(checkBox.getName());
			}
		}
		return result.toArray(new String[0]);
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setNotesSelection(String[] notes) {
		for (String note : notes) {
			for (CheckBox cb : notesList) {
				if (note.equals(cb.getName())) {
					cb.setValue(true);
					break;
				}
			}
		}
	}
}
