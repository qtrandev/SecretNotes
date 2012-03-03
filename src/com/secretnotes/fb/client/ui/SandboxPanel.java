package com.secretnotes.fb.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class SandboxPanel extends FlowPanel {
	
	private TabLayoutPanel sandboxContainerPanel;
	private FlowPanel sandboxPanel;
	private FlowPanel queryPanel;
	private FlowPanel randomProfilePanel;
	private FlowPanel friendOfFriendPanel;
	private FlowPanel hotOrNotPanel;
	
	public SandboxPanel() {
		super();
		initAboutPanel();
	}
	
	private void initAboutPanel() {
		add(getAboutContainerPanel());
		getAboutContainerPanel().add(getSandboxPanel(), "Sandbox");
		getAboutContainerPanel().add(getQueryPanel(), "Query");
		getAboutContainerPanel().add(getRandomProfilePanel(), "Random Profile");
		getAboutContainerPanel().add(getFriendOfFriendPanel(), "Friend of Friend");
		getAboutContainerPanel().add(getHotOrNotPanel(), "Hot or Not");
	}
	
	private TabLayoutPanel getAboutContainerPanel() {
		if (sandboxContainerPanel == null) {
			sandboxContainerPanel = new TabLayoutPanel(30.0, Unit.PX);
			sandboxContainerPanel.setHeight("600px");
		}
		return sandboxContainerPanel;
	}
	
	private FlowPanel getSandboxPanel() {
		if (sandboxPanel == null) {
			sandboxPanel = new FlowPanel();
			sandboxPanel.add(new QueryWelcomeContent());
		}
		return sandboxPanel;
	}
	
	private FlowPanel getQueryPanel() {
		if (queryPanel == null) {
			queryPanel = new FlowPanel();
			queryPanel.add(new QueryWelcomeContent());
		}
		return queryPanel;
	}
	
	private FlowPanel getRandomProfilePanel() {
		if (randomProfilePanel == null) {
			randomProfilePanel = new FlowPanel();
			randomProfilePanel.add(new QueryWelcomeContent());
		}
		return randomProfilePanel;
	}
	
	private FlowPanel getFriendOfFriendPanel() {
		if (friendOfFriendPanel == null) {
			friendOfFriendPanel = new FlowPanel();
			friendOfFriendPanel.add(new QueryWelcomeContent());
		}
		return friendOfFriendPanel;
	}
	
	private FlowPanel getHotOrNotPanel() {
		if (hotOrNotPanel == null) {
			hotOrNotPanel = new FlowPanel();
			hotOrNotPanel.add(new QueryWelcomeContent());
		}
		return hotOrNotPanel;
	}
}
