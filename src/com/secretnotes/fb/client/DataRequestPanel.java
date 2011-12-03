package com.secretnotes.fb.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.secretnotes.fb.client.data.IDataContainer;

public abstract class DataRequestPanel extends FlowPanel implements
		DataRequester {
	
	private IDataContainer dataContainer;
	
	public DataRequestPanel(IDataContainer dataContainer) {
		super();
		this.dataContainer = dataContainer;
	}

	public IDataContainer getDataContainer() {
		return dataContainer;
	}

	protected ServerRequest getServer() {
		return ServerRequest.getServer();
	}
}
