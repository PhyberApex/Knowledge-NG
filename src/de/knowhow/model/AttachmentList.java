package de.knowhow.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.db.DAO;

public class AttachmentList extends Model {

	private ArrayList<Attachment> attachments;
	private ArrayList<Attachment> currAttachments;
	private int currArticleID;
	private DAO db;

	public AttachmentList(DAO db) {
		this.db = db;
		attachments = new ArrayList<Attachment>();
	}

	public void load() throws DatabaseException {
		ResultSet rs;
		try {
			this.db.prepareStatement("SELECT * FROM attachment;");
			rs = this.db.execute();
			while (rs.next()) {
				String name = rs.getString("name");
				int attachment_ID = Integer.parseInt(rs
						.getString("attachment_ID"));
				int article_ID_FK = Integer.parseInt(rs
						.getString("article_ID_FK"));
				int intImage = Integer.parseInt(rs.getString("image"));
				boolean image;
				if (intImage == 0) {
					image = false;
				} else {
					image = true;
				}
				Attachment currAttachment = new Attachment(this.db,
						attachment_ID, name, article_ID_FK, image);
				this.attachments.add(currAttachment);
			}
			rs.close();
		} catch (SQLException e) {
		}
	}

	public void reload() throws DatabaseException {
		attachments.clear();
		this.load();
		setCurrAttachments(getAttachmentsByArticleID(currArticleID));
	}

	public ArrayList<Attachment> getAttachmentsByArticleID(int ID) {
		ArrayList<Attachment> found = new ArrayList<Attachment>();
		for (int i = 0; i < this.attachments.size(); i++) {
			if (attachments.get(i).getArticle_ID_FK() == ID) {
				found.add(attachments.get(i));
			}
		}
		return found;
	}

	public ArrayList<Attachment> getAttachments() {
		return this.attachments;
	}

	public ArrayList<Attachment> getCurrAttachments() {
		return this.currAttachments;
	}

	public Attachment getAttachmentByAttachmentID(int ID) {
		for (int i = 0; i < this.attachments.size(); i++) {
			if (attachments.get(i).getAttachment_ID() == ID) {
				return attachments.get(i);
			}
		}
		return null;
	}

	public void setCurrAttachments(ArrayList<Attachment> attachs) {
		this.currAttachments = attachs;
		setChanged();
		notifyObservers();
	}

	public void addAttachment(Attachment attach) {
		attachments.add(attach);
		setChanged();
		notifyObservers();
	}

	public void setCurrArticleID(int currArticleID) {
		this.currArticleID = currArticleID;
		setChanged();
		notifyObservers();
	}

	public int getCurrArticleID() {
		return currArticleID;
	}

	public void setCurrAttachmentsByArticleID(int iD) {
		setCurrAttachments(getAttachmentsByArticleID(iD));
	}
}
