package de.knowhow.controller;

import java.util.ArrayList;
import java.util.Observer;
import javax.swing.SwingUtilities;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Article;
import de.knowhow.model.ArticleList;
import de.knowhow.model.db.DAO;
import de.knowhow.view.ArticleLinkView;
import de.knowhow.view.ArticlePlainView;
import de.knowhow.view.ArticleRenameView;
import de.knowhow.view.ArticleRenderView;

public class ArticleListController {

	private ArticleList al;
	private DAO db;
	private MainController mc;
	private ArticleRenderView renderView;
	private ArticlePlainView plainView;
	private ArticleRenameView artRename;
	private AttachmentListController attachcl;
	private ArticleLinkView artLink;
	private CSSController csc;

	public ArticleListController(DAO db, MainController mc,
			AttachmentListController attachcl, CSSController csc) {
		this.db = db;
		this.mc = mc;
		this.attachcl = attachcl;
		this.csc = csc;
	}

	public void loadData() {
		al = new ArticleList(this.db);
		try {
			al.load();
		} catch (DatabaseException e) {
			mc.error(e);
		}
	}

	public void loadGUI() {
		renderView = new ArticleRenderView(this, this.attachcl, this.csc);
		renderView.setVisible(true);
		SwingUtilities.invokeLater(renderView);
		plainView = new ArticlePlainView(this);
		plainView.setVisible(false);
		SwingUtilities.invokeLater(plainView);
		artRename = new ArticleRenameView(this);
		SwingUtilities.invokeLater(artRename);
		artLink = new ArticleLinkView(this);
		SwingUtilities.invokeLater(artLink);
		al.addObserver(plainView);
		al.addObserver(renderView);
		al.addObserver(artRename);
		al.addObserver(artLink);
	}

	public ArticlePlainView getPlainView() {
		return this.plainView;
	}

	public ArticleRenderView getRenderView() {
		return this.renderView;
	}

	public ArticleLinkView getArtLink() {
		return artLink;
	}

	public ArticleRenameView getRenameView() {
		return this.artRename;
	}

	public ArrayList<Article> getArticles() {
		return this.al.getArticles();
	}

	public Article getArticleByID(int ID) {
		return this.al.getArticleByID(ID);
	}

	public void cancel() {
		plainView.cancel();
		renderView.cancel();
	}

	public void confirm(String action) throws DatabaseException {
		if (action.equals("renameArticle")) {
			al.setCurrName(artRename.getArtName());
		} else if (action.equals("ArticleContent")) {
			al.setCurrContent(plainView.getArticleContent());
		} else if (action.equals("newArticle")) {
			al.newArticle();
		}
	}

	public void setCurrArticle(Article art) {
		al.setCurrArticle(art);
		mc.currArtChanged(art.getArticle_ID());
	}

	public void setRenderVisible(boolean b) {
		this.renderView.setVisible(b);
	}

	public void setPlainVisible(boolean b) {
		this.plainView.setVisible(b);
	}

	public void insertHTML(String tag) {
		if (plainView.isEnabled()) {
			plainView.insertHTML(tag);
		}
	}

	public void error(DatabaseException e) {
		mc.error(e);
	}

	public void newArticle() throws DatabaseException {
		al.newArticle();
	}

	public void addObserver(Observer obs) {
		this.al.addObserver(obs);
	}

	public void delete() throws DatabaseException {
		this.al.deleteCurrArt();
	}

	public void deleteByTopic(int topicID) throws DatabaseException {
		this.al.deleteArtByTopic(topicID);
	}

	public void changeTopicOfCurrArticle(int i) throws DatabaseException {
		al.setCurrTopic_ID_FK(i);
	}

	public void insertHTMLLink(String string, int iD) {
		if (string.equals("IMAGE")) {
			plainView.insertImageLink(iD);
		} else if (string.equals("FILE")) {
			plainView.insertFileLink(iD);
		}
	}

	public void insertArticleLink(int iD) {
		plainView.insertArticleLink(iD);
	}
}