package com.secretnotes.fb.client.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.secretnotes.fb.client.Util;

public class Album extends DataHolder {
	
	private ArrayList<Photo> photoList;
	
	public Album(HashMap<String,String> properties) {
		super(properties);
		photoList = new ArrayList<Photo>();
	}

	public ArrayList<Photo> getPhotoList() {
		return photoList;
	}

	public void addPhoto(Photo photo) {
		getPhotoList().add(photo);
	}
	
	public int getPhotoCount() {
		return Integer.parseInt(getData(Util.ALBUM_COUNT));
	}
	
	public String getId() {
		return getData(Util.ALBUM_ID);
	}

	public String getName() {
		return getData(Util.ALBUM_NAME);
	}

	public String getCoverPhotoId() {
		return getData(Util.ALBUM_COVER_PHOTO_ID);
	}
	
	public Photo getCoverPhoto() {
		Photo result = null;
		String coverPhotoId = getCoverPhotoId();
		for (Photo photo : getPhotoList()) {
			if (photo.getPhotoId().equals(coverPhotoId)) {
				result = photo;
				break;
			}
		}
		return result;
	}
}
