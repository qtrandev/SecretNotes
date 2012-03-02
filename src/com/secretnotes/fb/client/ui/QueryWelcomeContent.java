package com.secretnotes.fb.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class QueryWelcomeContent extends Composite {
	
	interface MyUiBinder extends UiBinder<Widget, QueryWelcomeContent> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	public QueryWelcomeContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}



