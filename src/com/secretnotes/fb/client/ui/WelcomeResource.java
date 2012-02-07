package com.secretnotes.fb.client.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Resources used by the entire application.
 */
public interface WelcomeResource extends ClientBundle {
	@Source("Style.css")
	Style style();
	
//	@Source("logo.png")
//	ImageResource logo();
	
	public interface Style extends CssResource {
		String mainBlock();
		String nameSpan();
	}
}
