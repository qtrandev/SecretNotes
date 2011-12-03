package com.secretnotes.fb.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class FriendsContainerPanel extends TabLayoutPanel {

	public FriendsContainerPanel() {
		this(30.0, Unit.PX);
	}
	public FriendsContainerPanel(double barHeight, Unit barUnit) {
		super(barHeight, barUnit);
	}

}
