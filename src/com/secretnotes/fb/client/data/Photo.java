package com.secretnotes.fb.client.data;

import java.util.HashMap;

import com.secretnotes.fb.client.Util;

public class Photo extends DataHolder {

	public Photo(HashMap<String, String> properties) {
		super(properties);
	}
	
	public String getPicture() {
		return getData(Util.PHOTO_PICTURE);
	}
	
	public String getPhotoId() {
		return getData(Util.PHOTO_ID);
	}
	
	public String getSource() {
		return getData(Util.PHOTO_SOURCE);
	}
}
