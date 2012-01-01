package com.secretnotes.fb.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.data.User;

public class NotesPanel extends Composite implements DataRequester {
	
	private INotesController notesController;
	private FlowPanel stackPanel;
	private Label profileHeading;
	private Label notesHeading;
	private Label friendsHeading;
	
	private FlowPanel profilePanel;
	private Image profilePic;
	private Label nameLabel;
	private Label infoLabel;
	
	private FlowPanel notesPanel;
	private ArrayList<CheckBox> cb_notes;
	private HashMap<String,FriendPanel> friendPanelsMap = new HashMap<String,FriendPanel>();
	
	private FlowPanel friendsPanel;

	public NotesPanel(INotesController notesController) {
		this.notesController = notesController;
		initPanels();
		initWidget(getStackPanel());
	}
	
	public void initPanels() {
		initStackPanel();
		initProfilePanel();
		initNotesPanel();
		initFriendsPanel();
		friendPanelsMap.clear();
	}
	
	private void initStackPanel() {
		getStackPanel().clear();
		profileHeading = new Label("Profile");
		notesHeading = new Label("Notes");
		friendsHeading = new Label("Friends");
		getStackPanel().add(profileHeading);
		getStackPanel().add(getProfilePanel());
		getStackPanel().add(notesHeading);
		getStackPanel().add(getNotesPanel());
		getStackPanel().add(friendsHeading);
		Button saveAllButton = new Button("Save All Notes");
		saveAllButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				persistAllNotes();
			}
		});
		getStackPanel().add(saveAllButton);
		getStackPanel().add(getFriendsPanel());
		
		profileHeading.setStyleName("notes_heading");
		notesHeading.setStyleName("notes_heading");
		friendsHeading.setStyleName("notes_heading");
		getProfilePanel().setStyleName("notes_panel");
		getNotesPanel().setStyleName("notes_panel");
		getFriendsPanel().setStyleName("notes_panel");
	}
	
	private void initProfilePanel() {
		getProfilePanel().clear();
		profilePic = new Image("logo.png");
		nameLabel = new Label("User Name");
		infoLabel = new Label("User Info");
		getProfilePanel().add(profilePic);
		getProfilePanel().add(nameLabel);
		getProfilePanel().add(infoLabel);
	}
	
	private void initNotesPanel() {
		getNotesPanel().clear();
		cb_notes = new ArrayList<CheckBox>();
		CheckBox cb;
		for (String note_cat : Util.NOTES_CATEGORY) {
			cb = new CheckBox(Util.CONSTANTS.getString(note_cat));
			cb.setName(note_cat);
			cb_notes.add(cb);
		}
		for (CheckBox cb_note : cb_notes) {
			getNotesPanel().add(cb_note);
		}
	}
	
	private void initFriendsPanel() {
		getFriendsPanel().clear();
	}
	
	public void refreshPanel() {		
		FriendPanel fp;
		int stop=0;
		friendPanelsMap.clear();
		User friend;
		for (String friendId : getNotesController().getFriendUserIds()) {
			friend = getNotesController().getFriendFromList(friendId);
			fp = new FriendPanel(getNotesController(), friendId, friend.getProfilePic(),
					friend.getName(), Util.NOTES_CATEGORY, 
					getNotesController().getUser().getUserId());
			fp.setStyleName("friend_panel");
			getFriendsPanel().add(fp);
			friendPanelsMap.put(friendId, fp);
			if (stop++>20) break; // Limit to reduce requests - TEMP
		}
		
		getProfilePanel().clear();
		User user = getNotesController().getUser();
		profilePic = new Image(user.getProfilePic());
		nameLabel = new Label(user.getName());
		infoLabel = new Label(user.getLocale());
		getProfilePanel().add(profilePic);
		getProfilePanel().add(nameLabel);
		getProfilePanel().add(infoLabel);
	}
	
	public void refreshNoteSelection(String userId, String[] notes) {
		if (userId.equals(getNotesController().getUser().getUserId())) { // Handle main user
			for (String note : notes) {
				for (CheckBox cb : cb_notes) {
					if (note.equals(cb.getName())) {
						cb.setValue(true);
						break;
					}
				}
			}
			return;
		}
		FriendPanel fp = friendPanelsMap.get(userId);
		if (fp != null) {
			fp.setNotesSelection(notes);
		}
	}
	
	public void persistAllNotes() {
		HashMap<String, String[]> userNotesMap = new HashMap<String, String[]>();
		HashMap<String, String> userNamesMap = new HashMap<String, String>();
		for (FriendPanel fp : friendPanelsMap.values()) {
			userNotesMap.put(fp.getUserId(), fp.getSelectedNotes());
			userNamesMap.put(fp.getUserId(), fp.getUserName());
		}
		getNotesController().persistNotes(userNotesMap, userNamesMap, getNotesController().getUser().getUserId());
	}
	
	private FlowPanel getStackPanel() {
		if (stackPanel == null) {
			stackPanel = new FlowPanel();
		}
		return stackPanel;
	}
	
	private FlowPanel getProfilePanel() {
		if (profilePanel == null) {
			profilePanel = new FlowPanel();
		}
		return profilePanel;
	}
	
	private FlowPanel getNotesPanel() {
		if (notesPanel == null) {
			notesPanel = new FlowPanel();
		}
		return notesPanel;
	}
	
	private FlowPanel getFriendsPanel() {
		if (friendsPanel == null) {
			friendsPanel = new FlowPanel();
		}
		return friendsPanel;
	}

	public INotesController getNotesController() {
		return notesController;
	}
}
