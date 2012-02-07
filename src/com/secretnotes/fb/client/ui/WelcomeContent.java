package com.secretnotes.fb.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WelcomeContent extends Composite {
	
	interface MyStyle extends CssResource {
		String enabled();
		String disabled();
	}
	interface MyUiBinder extends UiBinder<Widget, WelcomeContent> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
//	@UiField SpanElement nameSpan;
	@UiField MyStyle newr;
	
	void setEnabled(boolean enabled) {
		getElement().addClassName(enabled ? newr.enabled() : newr.disabled());
		getElement().removeClassName(enabled ? newr.disabled() : newr.enabled());
	}
	
	public WelcomeContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}



