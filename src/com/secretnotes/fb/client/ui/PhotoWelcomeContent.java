package com.secretnotes.fb.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PhotoWelcomeContent extends Composite {
	
	interface MyUiBinder extends UiBinder<Widget, PhotoWelcomeContent> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	public PhotoWelcomeContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}



