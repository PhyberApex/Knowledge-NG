package de.knowhow.controller;

import java.awt.Component;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import de.knowhow.base.Config;
import de.knowhow.base.Constants;
import de.knowhow.exception.DatabaseException;
import de.knowhow.extra.Print;
import de.knowhow.model.Article;
import de.knowhow.model.ArticleList;
import de.knowhow.model.db.DAO;
import de.knowhow.model.gui.MenuItem;
import de.knowhow.view.ArticleLastEditView;
import de.knowhow.view.ArticleLinkView;
import de.knowhow.view.ArticlePlainView;
import de.knowhow.view.ArticleRenameView;
import de.knowhow.view.ArticleRenderView;
import de.knowhow.view.ArticleView;
import de.knowhow.view.View;

public class ArticleListController extends Controller implements Runnable {

	private ArticleList al;
	private DAO db;
	private MainController mc;
	private ArticleRenderView renderView;
	private ArticlePlainView plainView;
	private ArticleRenameView artRename;
	private ArticleLinkView artLink;
	private ArticleLastEditView artLast;
	private CSSController csc;
	private ArrayList<ArticleView> articleViews = new ArrayList<ArticleView>();
	private ArticleView currArticleView;
	private JPopupMenu popupMenu;
	private static Logger logger = Logger.getRootLogger();

	public ArticleListController(MainController mc, CSSController csc) {
		this.db = Config.getInstance().getDBHandle();
		this.mc = mc;
		this.csc = csc;
	}

	public void loadData() {
		al = new ArticleList(this.db);
		models.add(al);
		try {
			al.load();
		} catch (DatabaseException e) {
			mc.error(e);
		}
	}

	public void loadGUI() {
		SwingUtilities.invokeLater(this);
		renderView = new ArticleRenderView(this, this.csc);
		SwingUtilities.invokeLater(renderView);
		plainView = new ArticlePlainView(this);
		SwingUtilities.invokeLater(plainView);
		currArticleView = renderView;
		artRename = new ArticleRenameView(this);
		SwingUtilities.invokeLater(artRename);
		artLink = new ArticleLinkView(this);
		SwingUtilities.invokeLater(artLink);
		artLast = new ArticleLastEditView();
		SwingUtilities.invokeLater(artLast);
		articleViews.add(plainView);
		articleViews.add(renderView);
		articleViews.add(artRename);
		articleViews.add(artLink);
		articleViews.add(artLast);
		views = new ArrayList<View>(articleViews);
		addObservers();
	}

	public void setCurrArticleView(int view) {
		ArticleView artV = currArticleView;
		if (view == ArticleView.RENDEREDVIEW) {
			artV = renderView;
		} else if (view == ArticleView.PLAINVIEW) {
			artV = plainView;
		}
		currArticleView.setVisible(false);
		artV.setVisible(true);
		currArticleView = artV;

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

	public void confirmContent() throws DatabaseException {
		al.setCurrContent(currArticleView.getContent());
	}

	public void confirmRename() throws DatabaseException {
		al.getCurrArticle().setName(artRename.getArtName());
	}

	public void setCurrArticle(Article art) {
		al.setCurrArticle(art);
	}

	public void insertHTML(String tag) {
		getCurrView().insertHTML(tag);
	}

	public ArticleView getCurrView() {
		return this.currArticleView;
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
			currArticleView.insertImageLink(iD);
		} else if (string.equals("FILE")) {
			currArticleView.insertFileLink(iD);
		}
	}

	public void insertArticleLink(int iD) {
		getCurrView().insertArticleLink(iD);
	}

	public void writeAttToFS(int att_ID, String path) {
		mc.writeAttToFS(att_ID, path);
	}

	public void print() {
		PageFormat pageFormat = new PageFormat();
		Paper a4paper = new Paper();
		double paperWidth = 8.26;
		double paperHeight = 11.69;
		a4paper.setSize(paperWidth * 72.0, paperHeight * 72.0);

		/*
		 * set the margins respectively the imageable area
		 */
		double leftMargin = 0.78; /* should be about 2cm */
		double rightMargin = 0.78;
		double topMargin = 0.78;
		double bottomMargin = 0.78;

		a4paper.setImageableArea(leftMargin * 72.0, topMargin * 72.0,
				(paperWidth - leftMargin - rightMargin) * 72.0, (paperHeight
						- topMargin - bottomMargin) * 72.0);
		pageFormat.setPaper(a4paper);
		// pageFormat.setOrientation(PageFormat.LANDSCAPE);

		Print documentRenderer = new Print(pageFormat, "Report");
		documentRenderer.print(renderView.getEditorPane());
	}

	public void showPopupMenu(Component component, int x, int y) {
		popupMenu.show(component, x, y);
	}

	@Override
	public void run() {
		popupMenu = new JPopupMenu();
		MenuItem renameArticle = new MenuItem(
				Constants.getText("menu.edit.renameArticle"));
		renameArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("rename article clicked");
				mc.renameArticle();
			}
		});
		popupMenu.add(renameArticle);
		MenuItem print = new MenuItem(Constants.getText("menu.file.print"));
		print.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("print clicked");
				mc.print();
			}
		});
		popupMenu.add(print);
		MenuItem deleteArticle = new MenuItem(
				Constants.getText("menu.file.deleteArticle"));
		deleteArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("delete article clicked");
				mc.deleteArticle();
			}
		});
		popupMenu.add(deleteArticle);
	}
}