package com.secretnotes.fb.client.ui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.secretnotes.fb.client.data.User;

public class UserWelcomePanel extends FlowPanel {
	
	public UserWelcomePanel(User user) {
		super();
		initPanel(user);
	}
	
	private void initPanel(User user) {
        add(new UserWelcomeContent(user));
	}
}
