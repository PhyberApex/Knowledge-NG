package de.knowhow.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.knowhow.base.Config;
import de.knowhow.base.Constants;
import de.knowhow.base.Export;
import de.knowhow.base.ViewConstants;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Search;
import de.knowhow.model.db.DAO;
import de.knowhow.model.db.DAO_MYSQL;
import de.knowhow.model.db.DAO_SQLite;
import de.knowhow.view.AboutView;
import de.knowhow.view.MainView;
import de.knowhow.view.MenuView;
import de.knowhow.view.SearchView;
import de.knowhow.view.Splash;
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
	public static Splash splash;

	public MainController() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}

		config = new Config();
		Constants.setLanguage(new Locale(config.getProperty("lang")));
		ViewConstants.reload(config);
		URL url = ClassLoader.getSystemResource("de/knowhow/resource/img/splash.png");
        Image image = null;
        if (url != null)
            try {image = ImageIO.read(url);} catch (IOException ex) {}
        splash = new Splash( image, Constants.getText("splash.init"));
		splash.setVisible(true);
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
		this.csc = new CSSController(this.db, this);
		this.attL = new AttachmentListController(this.db, this);
		this.acl = new ArticleListController(this.db, this, attL, csc);
		this.tcl = new TopicListController(this.db, this);
		this.treeC = new TreeController(acl, tcl);
		splash.showStatus(Constants.getText("splash.loadCSS"),15);
		this.csc.loadData();
		splash.showStatus(Constants.getText("splash.loadAttachment"),28);
		this.attL.loadData();
		splash.showStatus(Constants.getText("splash.loadArt"),42);
		this.acl.loadData();
		splash.showStatus(Constants.getText("splash.loadTopic"),57);
		this.tcl.loadData();
		this.treeC.loadData();
		splash.showStatus(Constants.getText("splash.caching"),71);
		try {
			attL.cacheImages();
		} catch (DatabaseException e) {
			error(e);
		}
		splash.showStatus(Constants.getText("splash.paint"),85);
		this.csc.loadGUI();
		this.attL.loadGUI();
		this.acl.loadGUI();
		this.tcl.loadGUI();
		this.treeC.loadGUI();
		mv = new MainView(this);
		menuV = new MenuView(this);
		mv.add(menuV);
		mv.add(acl.getPlainView());
		mv.add(acl.getRenderView());
		mv.add(tcl.getTopicChooseView());
		mv.add(treeC.getTreeView());
		mv.setVisible(true);
		splash.close();
	}

	public static void main(String[] args) {
		new MainController();
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
		new AboutView();
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
		Export ex = new Export(action, this);	
		Thread tr = new Thread(ex);
		tr.start();
	}

	public CSSController getCsc() {
		return this.csc;
	}

	public ArticleListController getAcl() {
		return this.acl;
	}

	public AttachmentListController getAttL() {
		return this.attL;
	}

	public TopicListController getTcl() {
		return this.tcl;
	}

}