package com.secretnotes.fb.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.secretnotes.fb.client.Util;
import com.secretnotes.fb.client.data.User;

public class HomePanel extends FlowPanel {
	
	private TabLayoutPanel homeContainerPanel;
	private FlowPanel welcomePanel;
	
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
	
	private FlowPanel getWelcomePanel() {
		if (welcomePanel == null) {
			welcomePanel = new FlowPanel();
	        HTML welcomeHtml = new HTML(Util.MESSAGES.hello_basic());
	        Anchor sourceLink = new Anchor("Facebook Link");
	        sourceLink.addStyleName("sourceLink");
	        sourceLink.setTarget("blank");
	        sourceLink.setHref("http://www.facebook.com");
	        welcomePanel.add(welcomeHtml);
	        welcomePanel.add(sourceLink);
		}
		return welcomePanel;
	}
	
	private FlowPanel createUserWelcomePanel(User user) {
		FlowPanel userWelcomePanel = new FlowPanel();
		HTML welcomeHTML = new HTML(Util.MESSAGES.hello(user.getName()) + 
				"ID: " + user.getUserId() + "<br>" +
				"FIRST NAME: " + user.getFirstName() + "<br>" +
				"LAST NAME: " + user.getLastName() + "<br>" +
				"LINK: " + user.getLink() + "<br>" +
				"GENDER: " + user.getGender() + "<br>" +
				"TIMEZONE: " + user.getTimezone() + "<br>" +
				"LOCALE: " + user.getLocale() + "<br>" +
				"VERIFIED: " + user.getVerified() + "<br>" +
				"UPDATED TIME: " + user.getUpdatedTime() + "<br>" +
				"TYPE: " + user.getType()
			);
		userWelcomePanel.add(welcomeHTML);
		return userWelcomePanel;
	}
}
