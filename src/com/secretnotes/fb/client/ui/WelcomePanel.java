package com.secretnotes.fb.client.ui;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.secretnotes.fb.client.Util;

public class WelcomePanel extends FlowPanel {

	public WelcomePanel() {
		super();
		initPanel();
	}
	
	private void initPanel() {
		HTML welcomeHtml = new HTML(Util.MESSAGES.hello_basic());
        Anchor sourceLink = new Anchor("Facebook Link");
        sourceLink.addStyleName("sourceLink");
        sourceLink.setTarget("blank");
        sourceLink.setHref("http://www.facebook.com");
        add(welcomeHtml);
        add(sourceLink);
	}
}
