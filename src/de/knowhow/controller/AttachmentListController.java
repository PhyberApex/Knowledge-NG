package de.knowhow.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import de.knowhow.exception.DatabaseException;
import de.knowhow.extra.Upload;
import de.knowhow.model.ArticleList;
import de.knowhow.model.Attachment;
import de.knowhow.model.AttachmentList;
import de.knowhow.model.db.DAO;
import de.knowhow.view.AttachmentForArticleView;
import de.knowhow.view.Splash;

public class AttachmentListController implements Observer {

	private AttachmentList al;
	private DAO db;
	private MainController mc;
	private int currID;
	private AttachmentForArticleView attachArtView;
	public static Splash splash;

	public AttachmentListController(DAO db, MainController mc) {
		this.db = db;
		this.mc = mc;
		al = new AttachmentList(db);
	}

	public void loadData() {
		try {
			al.load();
		} catch (DatabaseException e) {
			mc.error(e);
		}
	}

	public void loadGUI() {
		attachArtView = new AttachmentForArticleView(this);
		attachArtView.setVisible(false);
		SwingUtilities.invokeLater(attachArtView);
		al.addObserver(attachArtView);
		mc.getAcl().addObserver(this);
	}

	public void reload(int iD) {
		this.currID = iD;
		this.al.setCurrAttachments(this.al.getAttachmentsByArticleID(iD));
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

	@Override
	public void update(Observable o, Object arg) {
		reload(((ArticleList) o).getCurrArticle().getArticle_ID());
	}

	public void insertImageLink(int iD) {
		mc.insertHTMLLink("IMAGE", iD);
	}

	public void insertFileLink(int iD) {
		mc.insertHTMLLink("FILE", iD);
	}

	public ArrayList<Attachment> getAttachments() {
		return this.al.getAttachments();
	}
}