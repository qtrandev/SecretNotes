package com.secretnotes.fb.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class AboutPanel extends FlowPanel {
	
	private TabLayoutPanel aboutContainerPanel;
	private FlowPanel projectPanel;
	private FlowPanel developerPanel;
	private FlowPanel resourcePanel;
	
	public AboutPanel() {
		super();
		initAboutPanel();
	}
	
	private void initAboutPanel() {
		add(getAboutContainerPanel());
		getAboutContainerPanel().add(getProjectPanel(), "Project");
		getAboutContainerPanel().add(getDeveloperPanel(), "Developer");
		getAboutContainerPanel().add(getResourcePanel(), "Resources");
	}
	
	private TabLayoutPanel getAboutContainerPanel() {
		if (aboutContainerPanel == null) {
			aboutContainerPanel = new TabLayoutPanel(30.0, Unit.PX);
			aboutContainerPanel.setHeight("600px");
		}
		return aboutContainerPanel;
	}
	
	private FlowPanel getProjectPanel() {
		if (projectPanel == null) {
			projectPanel = new FlowPanel();
			projectPanel.add(new QueryWelcomeContent());
		}
		return projectPanel;
	}
	
	private FlowPanel getDeveloperPanel() {
		if (developerPanel == null) {
			developerPanel = new FlowPanel();
			developerPanel.add(new QueryWelcomeContent());
		}
		return developerPanel;
	}
	
	private FlowPanel getResourcePanel() {
		if (resourcePanel == null) {
			resourcePanel = new FlowPanel();
			resourcePanel.add(new QueryWelcomeContent());
		}
		return resourcePanel;
	}
}
