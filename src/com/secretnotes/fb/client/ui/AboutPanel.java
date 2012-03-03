package com.secretnotes.fb.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class AboutPanel extends FlowPanel {
	
	private TabLayoutPanel aboutContainerPanel;
	private FlowPanel aboutPanel;
	
	public AboutPanel() {
		super();
		initHomePanel();
	}
	
	private void initHomePanel() {
		add(getAboutContainerPanel());
		showDefaultPanel();
	}
	
	public void showDefaultPanel() {
		getAboutContainerPanel().clear();
		getAboutContainerPanel().add(getAboutPanel(), "About");
	}
	
	private TabLayoutPanel getAboutContainerPanel() {
		if (aboutContainerPanel == null) {
			aboutContainerPanel = new TabLayoutPanel(30.0, Unit.PX);
			aboutContainerPanel.setHeight("600px");
		}
		return aboutContainerPanel;
	}
	
	private FlowPanel getAboutPanel() {
		if (aboutPanel == null) {
			aboutPanel = new FlowPanel();
			aboutPanel.add(new QueryWelcomeContent());
		}
		return aboutPanel;
	}
}
