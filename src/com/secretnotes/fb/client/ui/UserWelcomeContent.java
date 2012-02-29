package com.secretnotes.fb.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.secretnotes.fb.client.data.User;

public class UserWelcomeContent extends Composite {
	
	interface MyUiBinder extends UiBinder<Widget, UserWelcomeContent> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField SpanElement idSpan;
	@UiField SpanElement firstNameSpan;
	@UiField SpanElement lastNameSpan;
	@UiField SpanElement linkSpan;
	@UiField SpanElement genderSpan;
	@UiField SpanElement timezoneSpan;
	@UiField SpanElement localeSpan;
	@UiField SpanElement verifiedSpan;
	@UiField SpanElement updatedTimeSpan;
	@UiField SpanElement pictureSpan;
	
	public UserWelcomeContent(User user) {
		initWidget(uiBinder.createAndBindUi(this));
		idSpan.setInnerText(user.getUserId());
		firstNameSpan.setInnerText(user.getFirstName());
		lastNameSpan.setInnerText(user.getLastName());
		linkSpan.setInnerText(user.getLink());
		genderSpan.setInnerText(user.getGender());
		timezoneSpan.setInnerText(user.getTimezone());
		localeSpan.setInnerText(user.getLocale());
		verifiedSpan.setInnerText(user.getVerified());
		updatedTimeSpan.setInnerText(user.getUpdatedTime());
		pictureSpan.setInnerHTML("<img src='"+user.getProfilePic()+"'/>");
	}
}



