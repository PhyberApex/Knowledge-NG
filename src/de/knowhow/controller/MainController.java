package de.knowhow.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import de.knowhow.base.Config;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Article;
import de.knowhow.model.Attachment;
import de.knowhow.model.Search;
import de.knowhow.model.Topic;
import de.knowhow.model.db.DAO;
import de.knowhow.model.db.DAO_MYSQL;
import de.knowhow.model.db.DAO_SQLite;
import de.knowhow.view.AboutView;
import de.knowhow.view.MainView;
import de.knowhow.view.MenuView;
import de.knowhow.view.SearchView;
import de.knowhow.view.SplashScreen;
import de.knowhow.view.SubtopicView;

public class MainController {

	private DAO db;
	private ArticleListController acl;
	private TopicListController tcl;
	private TreeController treeC;
	private AttachmentListController attL;
	private CSSController csc;
	private MainView mv;
	private MenuView menuV;
	private Config config;

	public MainController() {
		SplashScreen splash = new SplashScreen();
		splash.setVisible(true);
		config = new Config();
		Constants.setLanguage(new Locale(config.getProperty("lang")));
		ViewConstants.reload(config);
		Constants.setDBName(config.getProperty("defaultdb"));
		if (config.getProperty("databasetyp").equals("1")) {
			db = new DAO_SQLite();
		} else if (config.getProperty("databasetyp").equals("2")) {
			db = new DAO_MYSQL();
			Constants.setHost(config.getProperty("host"));
			Constants.setUser(config.getProperty("user"));
			Constants.setPassword(config.getProperty("pass"));
		}
		try {
			db.openDB();
		} catch (DatabaseException e1) {
			error(e1);
		}
		db.checkDB();
		mv = new MainView(this);
		menuV = new MenuView(this);
		mv.add(menuV);
		splash.next();
		this.csc = new CSSController(this.db, this);
		splash.next();
		this.attL = new AttachmentListController(this.db, this);
		splash.next();
		this.acl = new ArticleListController(this.db, this, attL, csc);
		splash.next();
		this.tcl = new TopicListController(this.db, this);
		this.treeC = new TreeController(acl, tcl);
		this.attL.init();
		splash.next();
		try {
			attL.cacheImages();
		} catch (DatabaseException e) {
			error(e);
		}
		splash.next();
		mv.add(acl.getPlainView());
		mv.add(acl.getRenderView());
		mv.add(tcl.getTopicChooseView());
		mv.add(treeC.getTreeView());
		splash.next();
		mv.setVisible(true);
		splash.dispose();
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainController mc = new MainController();
	}

	public void cancel() {
		acl.cancel();
	}

	public void confirm(String action) {
		try {
			if (action.contains("Article")) {
				acl.confirm(action);
			} else {
				tcl.confirm(action);
			}
		} catch (DatabaseException e) {
			mv.error(e);
		}
	}

	public void exit() {
		try {
			db.closeDB();
		} catch (DatabaseException e) {
		}
		mv.dispose();
		deleteDir(new File("tmp/"));
		System.exit(0);
	}

	public void setRenderVisible(boolean b) {
		acl.setRenderVisible(b);
	}

	public void setPlainVisible(boolean b) {
		acl.setPlainVisible(b);
		menuV.setEditable(b);
	}

	public void insertHTML(String tag) {
		acl.insertHTML(tag);
	}

	public void changeResolution(String res) {
		config.setProperty("resolution", res);
		prefChange();
	}

	private void prefChange() {
		JOptionPane.showMessageDialog(mv, Constants
				.getText("message.warning.restart"), "Information",
				JOptionPane.INFORMATION_MESSAGE);
		config.saveChanges();
	}

	public void changeLanguage(String string) {
		config.setProperty("lang", string);
		prefChange();
	}

	public void newArticle() {
		try {
			acl.newArticle();
		} catch (DatabaseException e) {
			error(e);
		}
	}

	public void newTopic() {
		try {
			tcl.newTopic();
		} catch (DatabaseException e) {
			error(e);
		}
	}

	public void newDatabase() {
		String name = JOptionPane.showInputDialog(Constants
				.getText("newDatabase")
				+ ":");
		if (name != null) {
			config.setProperty("defaultdb", name);
		}
		prefChange();
	}

	public void renameArticle() {
		acl.getRenameView().setVisible(true);
	}

	public void renameTopic() {
		tcl.getTopicRenameView().setVisible(true);
	}

	public void error(DatabaseException e) {
		mv.error(e);
	}

	public void currArtChanged(int ID) {
		attL.reload(ID);
	}

	public void deleteArticle() {
		int ret = JOptionPane.showConfirmDialog(mv, Constants
				.getText("message.warning.deleteArticle"), "Warning",
				JOptionPane.OK_CANCEL_OPTION);
		if (ret == JOptionPane.OK_OPTION) {
			try {
				acl.delete();
			} catch (DatabaseException e) {
				error(e);
			}
		}
	}

	public void deleteTopic() {
		int ret = JOptionPane.showConfirmDialog(mv, Constants
				.getText("message.warning.deleteTopic"), "Warning",
				JOptionPane.OK_CANCEL_OPTION);
		if (ret == JOptionPane.OK_OPTION) {
			try {
				tcl.delete();
			} catch (DatabaseException e) {
				error(e);
			}
		}
	}

	public void deleteArticleByTopic(int topicID) throws DatabaseException {
		acl.deleteByTopic(topicID);
	}

	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));
			}
		}
		dir.delete();
	}

	public void setTopicChooserVisible(boolean b) {
		tcl.getTopicChooseView().setVisible(b);
	}

	public void changeTopicOfCurrArticle(int i) {
		try {
			acl.changeTopicOfCurrArticle(i);
		} catch (DatabaseException e) {
			error(e);
		}
	}

	public void addArticleListObserver(Observer obs) {
		acl.addObserver(obs);
	}

	public void upload(boolean image) {
		JFileChooser fcUpload = new JFileChooser();
		fcUpload.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fcUpload.showSaveDialog(mv);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fcUpload.getSelectedFile();
			try {
				attL.newAttachment(file, image);
			} catch (DatabaseException e1) {
				error(e1);
			} catch (IOException e) {
			}
		}
	}

	public void insertLink(String string) {
		if (string.equals("Image") || string.equals("File")) {
			attL.getAttachArtView().setVisible(true);
			attL.getAttachArtView().setImage(false);
			if (string.equals("Image")) {
				attL.getAttachArtView().setImage(true);
			}
		} else if (string.equals("Article")) {
			acl.getArtLink().setVisible(true);
		}
	}

	public void insertHTMLLink(String string, int iD) {
		acl.insertHTMLLink(string, iD);
	}

	public void changeDatabase(String string) {
		if (string.equals("1")) {
			String database = JOptionPane.showInputDialog(Constants
					.getText("keyword.database")
					+ ":");
			if (database != null) {
				config.setProperty("databasetyp", string);
				config.setProperty("defaultdb", database);
				config.setProperty("host", "");
				config.setProperty("user", "");
				config.setProperty("pass", "");
				prefChange();
			}
		} else if (string.equals("2")) {
			String host = JOptionPane.showInputDialog(Constants
					.getText("database.host")
					+ ":");
			String database = JOptionPane.showInputDialog(Constants
					.getText("keyword.database")
					+ ":");
			String user = JOptionPane.showInputDialog(Constants
					.getText("database.user")
					+ ":");
			String pass = JOptionPane.showInputDialog(Constants
					.getText("database.password")
					+ ":");
			if (host != null && database != null && user != null
					&& pass != null) {
				config.setProperty("databasetyp", string);
				config.setProperty("host", host);
				config.setProperty("defaultdb", database);
				config.setProperty("user", user);
				config.setProperty("pass", pass);
				prefChange();
			}
		}
	}

	public void search(String text) {
		SearchView sV = new SearchView(Search.getArticles(text, acl
				.getArticles()), this);
		sV.setVisible(true);
	}

	public void about() {
		@SuppressWarnings("unused")
		AboutView about = new AboutView();
	}

	public void setCurrArtByID(int iD) {
		acl.setCurrArticle(acl.getArticleByID(iD));
	}

	public void subTopic() {
		SubtopicView sub = new SubtopicView(this, this.tcl);
		sub.setVisible(true);
	}

	public void setSubtopicByID(int topicID) {
		try {
			tcl.setCurrTopic_ID_FK(topicID);
		} catch (DatabaseException e) {
			error(e);
		}
	}

	public void editCSS(String string) {
		if (string.equals("plain")) {
			csc.getPlainView().setVisible(true);
		} else if (string.equals("assist")) {
			// TODO assisted CSSEditor
		}
	}

	public void export(String action) {
		if (action.equals("HTML")) {
			JFileChooser fcExport = new JFileChooser();
			fcExport.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fcExport.showSaveDialog(mv);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fcExport.getSelectedFile();
				try {
					exportHTML(file.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// TODO other export types
	}

	private void exportHTML(String absolutePath) throws IOException {
		absolutePath = absolutePath + "/" + Constants.getDBName();
		new File(absolutePath).mkdir();
		new File(absolutePath + "/articles").mkdir();
		new File(absolutePath + "/attachments").mkdir();
		String stylesheet = csc.getStyleSheet();
		FileOutputStream stylesheetOut = new FileOutputStream(absolutePath
				+ "/style.css");
		for (int i = 0; i < stylesheet.length(); i++) {
			stylesheetOut.write((byte) stylesheet.charAt(i));
		}
		stylesheetOut.close();
		String html = "<html>\n<head>\n"
				+ "<link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\" />\n"
				+ "<title>" + Constants.getDBName()
				+ "</title>\n</head>\n<body>\n<h1>" + Constants.getDBName()
				+ "</h1>\n<ul>";
		html = appendHTMLBody(html, 0);
		html += "\n</ul></body>\n</html>";
		File index = new File(absolutePath + "/index.html");
		index.createNewFile();
		FileOutputStream fileOut = new FileOutputStream(index);
		for (int i = 0; i < html.length(); i++) {
			fileOut.write((byte) html.charAt(i));
		}
		fileOut.close();
		ArrayList<Article> al = acl.getArticles();
		for (int i = 0; i < al.size(); i++) {
			String content = al.get(i).getContent();
			content = content.replaceAll("<img src=\"tmp/",
					"<img src=\"../attachments/");
			content = content.replaceAll("<a href=\"attachment://",
					"<a href=\"../attachments/");
			content = content.replaceAll("<a href=\"article://",
					"<a href=\"../articles/");
			FileOutputStream writeStream = new FileOutputStream(absolutePath
					+ "/articles/" + al.get(i).getArticle_ID() + "");
			for (int j = 0; j < content.length(); j++) {
				writeStream.write((byte) content.charAt(j));
			}
			writeStream.close();
		}
		ArrayList<Attachment> attl = attL.getAttachments();
		for (int i = 0; i < attl.size(); i++) {
			try {
				attl.get(i).loadBin();
			} catch (DatabaseException e) {
				error(e);
			}
			FileOutputStream writeStream = new FileOutputStream(absolutePath
					+ "/attachments/"
					+ attl.get(i).getAttachment_ID()
					+ attl.get(i).getName().substring(
							attl.get(i).getName().length()));
			for (int j = 0; j < attl.get(i).getBinary().length; j++) {
				writeStream.write(attl.get(i).getBinary()[j]);
			}
			writeStream.close();
		}
	}

	private String appendHTMLBody(String html, int iD) {
		ArrayList<Topic> tl = tcl.getTopics();
		ArrayList<Article> al = acl.getArticles();
		for (int i = 0; i < tl.size(); i++) {
			if (tl.get(i).getTopic_ID_FK() == iD) {
				html += "\n<li><ul>" + tl.get(i).getName();
				html = appendHTMLBody(html, tl.get(i).getTopic_ID());
				for (int j = 0; j < al.size(); j++) {
					if (al.get(j).getTopic_ID_FK() == tl.get(i).getTopic_ID()) {
						html += "\n<li><a href=\"articles/"
								+ al.get(j).getArticle_ID() + "\">"
								+ al.get(j).getName() + "</a></li>";
					}
				}
				html += "\n</ul></li>";
			} else {
				continue;
			}
		}
		return html;
	}
}