package com.secretnotes.fb.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.secretnotes.fb.client.Util;
import com.secretnotes.fb.client.data.User;

public class HomePanel extends FlowPanel {
	
	private TabLayoutPanel homeContainerPanel;
	private WelcomePanel welcomePanel;
	
	public HomePanel() {
		super();
		initHomePanel();
	}
	
	private void initHomePanel() {
		add(getHomeContainerPanel());
	}
	
	public void showDefaultPanel() {
		getHomeContainerPanel().clear();
		getHomeContainerPanel().add(getWelcomePanel(), "Welcome");
	}
	
	public void showUserWelcomePanel(User user) {
		getHomeContainerPanel().clear();
		getHomeContainerPanel().add(createUserWelcomePanel(user), "Welcome");
	}
	
	private TabLayoutPanel getHomeContainerPanel() {
		if (homeContainerPanel == null) {
			homeContainerPanel = new TabLayoutPanel(30.0, Unit.PX);
			homeContainerPanel.setHeight("600px");
		}
		return homeContainerPanel;
	}
	
	private WelcomePanel getWelcomePanel() {
		if (welcomePanel == null) {
			welcomePanel = new WelcomePanel();
		}
		return welcomePanel;
	}
	
	private FlowPanel createUserWelcomePanel(User user) {
		UserWelcomePanel userWelcomePanel  = new UserWelcomePanel(user);
		userWelcomePanel.getElement().setId("userWelcomePanel");
		return userWelcomePanel;
	}
}
