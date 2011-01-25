package de.knowhow.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import de.knowhow.base.Config;
import de.knowhow.exception.DatabaseException;
import de.knowhow.extra.Splash;
import de.knowhow.extra.Upload;
import de.knowhow.model.Attachment;
import de.knowhow.model.AttachmentList;
import de.knowhow.model.db.DAO;
import de.knowhow.view.AttachmentForArticleView;

public class AttachmentListController extends Controller {

	private AttachmentList al;
	private DAO db;
	private ArticleListController acl;
	private int currID;
	private AttachmentForArticleView attachArtView;
	public static Splash splash;

	public AttachmentListController(ArticleListController acl) {
		this.db = Config.getInstance().getDBHandle();
		this.acl = acl;
		al = new AttachmentList(db);
	}

	public void loadData() {
		try {
			al.load();
			models.add(al);
		} catch (DatabaseException e) {
			acl.error(e);
		}
		
	}

	public void loadGUI() {
		attachArtView = new AttachmentForArticleView(this);
		SwingUtilities.invokeLater(attachArtView);
		views.add(attachArtView);
		acl.addObserver(this.attachArtView);
		addObservers();
	}

	public void writeAttToFS(int attID, String path) throws DatabaseException {
		Attachment attach = al.getAttachmentByAttachmentID(attID);
		attach.loadBin();
		File tmp = new File(path);
		try {
			FileOutputStream fos = new FileOutputStream(tmp);
			fos.write(attach.getBinary());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cacheImages() throws DatabaseException {
		ArrayList<Attachment> attachments = al.getAttachments();
		new File("tmp/").mkdir();
		for (int i = 0; i < attachments.size(); i++) {
			if (attachments.get(i).isImage()) {
				Attachment attach = attachments.get(i);
				attach.loadBin();
				File tmp = new File("tmp/" + attach.getAttachment_ID());
				try {
					FileOutputStream fos = new FileOutputStream(tmp);
					fos.write(attach.getBinary());
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void newAttachment(File file, boolean image)
			throws DatabaseException, IOException {
		Upload ul = new Upload(this.db, image, file, this.al, currID);
		Thread tr = new Thread(ul);
		tr.start();
	}

	public AttachmentForArticleView getAttachArtView() {
		return this.attachArtView;
	}

	public void insertImageLink(int iD) {
		acl.insertHTMLLink("IMAGE", iD);
	}

	public void insertFileLink(int iD) {
		acl.insertHTMLLink("FILE", iD);
	}

	public ArrayList<Attachment> getAttachments() {
		return this.al.getAttachments();
	}

	public void setCurrID(int iD) {
		this.currID = iD;
		al.setCurrAttachmentsByArticleID(iD);
	}
}