package com.secretnotes.fb.client;

import java.util.HashMap;

public class DataHolder {

	private HashMap<String,String> properties;
	
	public DataHolder(HashMap<String,String> properties) {
		this.properties = properties;
	}
	
	public String getData(String property) {
		return getProperties().get(property);
	}
	
	private HashMap<String,String> getProperties() {
		return properties;
	}
}
