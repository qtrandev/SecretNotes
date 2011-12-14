package com.secretnotes.fb.client.data;


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
