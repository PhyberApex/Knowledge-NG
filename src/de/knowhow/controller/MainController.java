package de.knowhow.controller;

/*
 * Main class everything starts here
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import de.knowhow.base.Config;
import de.knowhow.base.Constants;
import de.knowhow.base.Initializer;
import de.knowhow.exception.DatabaseException;
import de.knowhow.extra.Export;
import de.knowhow.extra.Splash;
import de.knowhow.model.db.DAO;
import de.knowhow.view.AboutView;
import de.knowhow.view.MainView;
import de.knowhow.view.MenuView;
import de.knowhow.view.SearchView;
import de.knowhow.view.View;

public class MainController extends Controller {

	private ArrayList<Controller> controller = new ArrayList<Controller>();
	private ArticleListController acl;
	private TopicListController tcl;
	private TreeController treeC;
	private AttachmentListController attL;
	private CSSController csc;
	private MainView mv;
	private MenuView menuV;
	private AboutView aboutView;
	private SearchView searchView;
	private Config config;
	private DAO db;
	public static Splash splash;
	private static Logger logger = Logger.getLogger(MainController.class
			.getName());

	public MainController() {
		if (!Initializer.getInstance().initializeApp()) {
			JOptionPane.showMessageDialog(null, "This should never happen!",
					"Fatal Error", JOptionPane.ERROR_MESSAGE, null);
			logger.fatal("error in intialize process");
			System.exit(0);
		}
		splash = new Splash(Constants.createImageIcon(
				"/de/knowhow/resource/img/splash.png").getImage(),
				Constants.getText("splash.init"));
		splash.setVisible(true);
		config = Config.getInstance();
		Constants.setDBName(config.getProperty("defaultdb"));
		db = config.getDBHandle();
		try {
			db.openDB();
		} catch (DatabaseException e) {
			error(e);
			logger.error("Database Exception " + e.getMessage());
		}
		db.checkDB();
		init();
		loadData();
		splash.showStatus(Constants.getText("splash.paint"), 85);
		loadGUI();
		SwingUtilities.invokeLater(mv);
		SwingUtilities.invokeLater(menuV);
		SwingUtilities.invokeLater(aboutView);
		SwingUtilities.invokeLater(searchView);
		splash.close();
		mv.setVisible(true);
	}

	private void init() {
		this.csc = new CSSController(this);
		controller.add(csc);
		this.acl = new ArticleListController(this, csc);
		controller.add(acl);
		this.attL = new AttachmentListController(acl);
		controller.add(attL);
		this.tcl = new TopicListController(this);
		controller.add(tcl);
		this.treeC = new TreeController(acl, tcl);
		controller.add(treeC);
	}

	public void addViews(Controller controller) {
		Iterator<View> iterator = controller.getViewIterator();
		while (iterator.hasNext()) {
			View aktView = iterator.next();
			if (aktView.isComponent()) {
				getMainComponent().add(aktView.getComponent(),
						aktView.getConstraints());
				aktView.setVisible(true);
			}
		}
	}

	public static void main(String[] args) {
		new MainController();

	}

	public void cancel() {
		acl.cancel();
	}

	public void confirm(String action) {
		try {
			tcl.confirm(action);
		} catch (DatabaseException e) {
			error(e);
			logger.error("Database Exception " + e.getMessage());
		}
	}

	public void exit() {
		try {
			db.closeDB();
		} catch (DatabaseException e) {
		}
		mv.getComponent().dispose();
		deleteDir(new File("tmp/"));
		System.exit(0);
	}

	public void insertHTML(String tag) {
		acl.insertHTML(tag);
	}

	public void changeResolution(String res) {
		config.setProperty("resolution", res);
		prefChange();
	}

	private void prefChange() {
		JOptionPane.showMessageDialog(mv.getComponent(),
				Constants.getText("message.warning.restart"), "Information",
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
				.getText("newDatabase") + ":");
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

	public void error(Exception e) {
		mv.error(e);
	}

	public void deleteArticle() {
		int ret = JOptionPane.showConfirmDialog(mv.getComponent(),
				Constants.getText("message.warning.deleteArticle"), "Warning",
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
		int ret = JOptionPane.showConfirmDialog(mv.getComponent(),
				Constants.getText("message.warning.deleteTopic"), "Warning",
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
		int returnVal = fcUpload.showSaveDialog(mv.getComponent());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fcUpload.getSelectedFile();
			try {
				attL.newAttachment(file, image);
			} catch (DatabaseException e1) {
				error(e1);
				logger.error(e1.getMessage());
			} catch (IOException e) {
			}
		}
	}

	public void insertLink(String string) {
		if (string.equals("Image") || string.equals("File")) {
			attL.getAttachArtView().setImage(false);
			attL.getAttachArtView().setVisible(true);
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
					.getText("keyword.database") + ":");
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
					.getText("database.host") + ":");
			String database = JOptionPane.showInputDialog(Constants
					.getText("keyword.database") + ":");
			String user = JOptionPane.showInputDialog(Constants
					.getText("database.user") + ":");
			String pass = JOptionPane.showInputDialog(Constants
					.getText("database.password") + ":");
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
		searchView.searchFor(text, acl.getArticles().iterator());
	}

	public void about() {
		aboutView.setVisible(true);
	}

	public void setCurrArtByID(int iD) {
		acl.setCurrArticle(acl.getArticleByID(iD));
	}

	public void subTopic() {
		tcl.subTopic();
	}

	public void editCSS(String string) {
		if (string.equals("plain")) {
			csc.getPlainView().setVisible(true);
			logger.debug("Starting CSS Editor");
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

	public void writeAttToFS(int att_ID, String path) {
		try {
			attL.writeAttToFS(att_ID, path);
		} catch (DatabaseException e) {
			error(e);
			logger.error(e.getMessage());
		}
	}

	@Override
	public void loadGUI() {
		this.csc.loadGUI();
		this.attL.loadGUI();
		this.acl.loadGUI();
		this.tcl.loadGUI();
		this.treeC.loadGUI();
		mv = new MainView(this);
		menuV = new MenuView(this);
		views.add(menuV);
		aboutView = new AboutView();
		views.add(aboutView);
		searchView = new SearchView(this);
		views.add(searchView);
		doLayout();
		addViews(this);
		addViews(acl);
//		addViews(attL);
		addViews(tcl);
		addViews(treeC);
	}

	private void doLayout() {
		getMainComponent().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
	}

	private JFrame getMainComponent() {
		return mv.getComponent();
	}

	@Override
	public void loadData() {
		splash.showStatus(Constants.getText("splash.loadCSS"), 15);
		this.csc.loadData();
		splash.showStatus(Constants.getText("splash.loadAttachment"), 28);
		this.attL.loadData();
		splash.showStatus(Constants.getText("splash.loadArt"), 42);
		this.acl.loadData();
		splash.showStatus(Constants.getText("splash.loadTopic"), 57);
		this.tcl.loadData();
		this.treeC.loadData();
		splash.showStatus(Constants.getText("splash.caching"), 71);
		try {
			attL.cacheImages();
		} catch (DatabaseException e) {
			error(e);
			logger.error("Database Exception " + e.getMessage());
		}
	}

	public void changeArticleView(int view) {
		acl.setCurrArticleView(view);
	}

	public void print() {
		acl.print();
	}
}