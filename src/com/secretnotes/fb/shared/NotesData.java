package com.secretnotes.fb.shared;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class NotesData {

	@PrimaryKey
	private String userId;
	@Persistent
	private String[] notes = new String[0];

	public NotesData(String userId, String[] notes) {
		this.userId = userId;
		this.notes = notes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getNotes() {
		return notes;
	}

	public void setNotes(String[] notes) {
		this.notes = notes;
	}

}
