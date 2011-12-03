package com.secretnotes.fb.server;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.secretnotes.fb.client.NotesService;
import com.secretnotes.fb.shared.NotesData;

public class NotesServiceImpl extends RemoteServiceServlet implements NotesService {

	private static final PersistenceManagerFactory PMF = 
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public String[] getNotes(String userId){
		PersistenceManager pm = getPersistenceManager();
		String[] notes = new String[0];
		try {
			Query q = pm.newQuery(NotesData.class, "userId == id");
			q.declareParameters("String id");
			List<NotesData> users = (List<NotesData>) q.execute(userId);
			for (NotesData user : users) {
				notes = user.getNotes();
			}
		} finally {
			pm.close();
		}
		return notes;
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	public void setNotes(String userId, String userName, String ownerId, String[] notes) {
		PersistenceManager pm = getPersistenceManager();
		try {
			NotesData user = pm.getObjectById(NotesData.class, userId);
			user.setNotes(notes);
		} catch (Exception e) { // Create new object if not found
			pm.makePersistent(new NotesData(userId, notes, ownerId));
		} finally {
			pm.close();
		}
	}
}




