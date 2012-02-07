package com.secretnotes.fb.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ExternalContent extends Composite {
	
	interface MyUiBinder extends UiBinder<Widget, ExternalContent> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField SpanElement nameSpan;
	final WelcomeResource resource;
	
	public ExternalContent(WelcomeResource resource) {
		this.resource = resource;
		initWidget(uiBinder.createAndBindUi(this));
		nameSpan.setInnerText("Quyen");
	}
	
	@UiFactory /* this method could be static if you like */
	  public WelcomeResource getResource() {
	    return resource;
	  }

}



