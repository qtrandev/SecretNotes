package com.secretnotes.fb.client.data;

import com.secretnotes.fb.client.PhotoManager;
import com.secretnotes.fb.client.User;

public class PhotoOwnership {
	
	private User user;
	private PhotoManager photoManager;
	
	public PhotoOwnership(User user) {
		super();
		this.user = user;
		this.photoManager = new PhotoManager();
	}

	public User getUser() {
		return user;
	}

	public PhotoManager getPhotoManager() {
		return photoManager;
	}
	
}
