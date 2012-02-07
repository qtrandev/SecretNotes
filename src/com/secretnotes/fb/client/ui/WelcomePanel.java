package com.secretnotes.fb.client.ui;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.secretnotes.fb.client.Util;

public class WelcomePanel extends FlowPanel {
	
	private Label titleLabel;
	private Label titleDesc;
	private Label featuresLabel;
	private Label photoViewerHeading;
	private Label secretNotesHeading;
	private Label privacyLabel;
	private Label privacyDesc;
	private Image photoViewerScreenshot;
	private Image secretNotesScreenshot;

	public WelcomePanel() {
		super();
		initPanel();
	}
	
	private void initPanel() {
//		HTML welcomeHtml = new HTML(Util.MESSAGES.hello_basic());
//        Anchor sourceLink = new Anchor("Facebook Link");
//        sourceLink.addStyleName("sourceLink");
//        sourceLink.setTarget("blank");
//        sourceLink.setHref("http://www.facebook.com");
//        add(welcomeHtml);
//        add(sourceLink);
        add(new WelcomeContent());
        //add(new Hello((Resource)GWT.create(Resource.class)));
	}
}
