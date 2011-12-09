package com.secretnotes.fb.client;

import java.util.ArrayList;

public class PhotoManager {
	
	private ArrayList<Album> albumList = new ArrayList<Album>();
	
	public PhotoManager() {
		super();
	}

	public ArrayList<Album> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(ArrayList<Album> albumList) {
		this.albumList = albumList;
	}
	
	public void addAlbum(Album album) {
		getAlbumList().add(album);
	}
	
	public Album getAlbum(String albumId) {
		Album result = null;
		for (Album album : getAlbumList()) {
			if (album.getId().equals(albumId)) {
				result = album;
				break;
			}
		}
		return result;
	}
}
