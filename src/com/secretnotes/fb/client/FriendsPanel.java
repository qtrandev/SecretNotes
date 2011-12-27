package com.secretnotes.fb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.secretnotes.fb.client.data.IDataContainer;

public class FriendsPanel extends DataRequestPanel implements DataRequester{
	
	private FlowPanel friendLinksPanel;
	
	public FriendsPanel(IDataContainer dataContainer) {
		super(dataContainer);
		initFriendsPanel();
	}
	
	public void initFriendsPanel() {
		clear();
		add(new HTML(Util.MESSAGES.page_welcome("Friends")));
		add(new HTML("Nothing here yet"));
		add(new HTML("<hr/>More to come"));
		
		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		oracle.add("Test User");
		oracle.add("Adam Apple");
		oracle.add("Adam Bonnie");
		oracle.add("Bonnie Clyde");
		oracle.add("Barry Clyde");
		oracle.add("Bo Clyde");
		SuggestBox box = new SuggestBox(oracle);
		add(box);
		box.setFocus(true);
	}
	
	public void showInProgress() {
		clear();
		add(new HTML("Making call for the list of friends..."));
	}

	
	public void displayLoggedInFriendsPanel() {
		clear();
		add(new HTML("Hi "+getDataContainer().getUser().getFirstName()+"! Type a friend's name in the box."));
		add(new Image(getDataContainer().getUser().getProfilePic()));
		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle ();
		SuggestBox box = new SuggestBox(oracle);
		box.addSelectionHandler(new SelectionHandler<Suggestion>() {
			public void onSelection(SelectionEvent<Suggestion> event) {
				showPhotos(event.getSelectedItem().getReplacementString());
			}
		});
		add(box);

		oracle.add(getDataContainer().getUser().getName());
		for (String friendName : getDataContainer().getFriendNames()) {
			oracle.add(friendName);
		}
		
		Label friendLabel;
		for (String friendName : getDataContainer().getFriendNames()) {
			friendLabel = new Label(friendName);
			friendLabel.setStyleName("friendLinkLabel");
			friendLabel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					showPhotos(((Label)event.getSource()).getText());
				}
			});
			getFriendLinksPanel().add(friendLabel);
		}

		add(getFriendLinksPanel());
		box.setFocus(true);
		if (Util.LOG) GWT.log("Added everything needed to friends page.");
	}
	
	private void showPhotos(String name) {
		String id = lookUpId(name);
		if (Util.LOG) GWT.log("Sending request to get photos of "+name+" ("+id+").");
		getServer().requestPhotos(id);
	}

	private String lookUpId(String name) {
		String id = getDataContainer().getIdFromName(name);
		return id;
	}
	
	private FlowPanel getFriendLinksPanel() {
		if (friendLinksPanel == null) {
			friendLinksPanel = new FlowPanel();
			friendLinksPanel.getElement().setId("friendLinksPanel");
		}
		return friendLinksPanel;
	}
}
