package de.knowhow.view;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;
import de.knowhow.model.gui.Menu;
import de.knowhow.model.gui.MenuItem;
import de.knowhow.model.gui.RadioButtonMenuItem;

public class MenuView extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu file;
	private MenuItem newArticle;
	private MenuItem newTopic;
	private Menu upload;
	private MenuItem uploadImage;
	private MenuItem uploadFile;
	private MenuItem newDatabase;
	private MenuItem openDatabase;
	private MenuItem close;

	private Menu edit;
	private MenuItem renameArticle;
	private MenuItem deleteArticle;
	private MenuItem subtopic;
	private Menu css;
	private MenuItem plainCSS;
	private MenuItem assistCSS;
	private MenuItem renameTopic;
	private MenuItem deleteTopic;
	private MenuItem insertCode;
	private MenuItem insertList;
	private Menu insertLink;
	private MenuItem insertLinkArticle;
	private MenuItem insertLinkImage;
	private MenuItem insertLinkFile;

	private Menu prefs;
	private Menu lang;
	private Menu res;
	private Menu database;
	private RadioButtonMenuItem sqlite;
	private RadioButtonMenuItem mysql;

	private Menu help;
	private MenuItem about;

	private MainController mc;

	public MenuView(MainController mc) {
		super();
		this.mc = mc;
		init();
	}

	public void init() {
		file = new Menu(Constants.getText("menu.file"));
		newArticle = new MenuItem(Constants.getText("menu.file.newArticle"));
		newArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.newArticle();
			}
		});
		file.add(newArticle);
		deleteArticle = new MenuItem(Constants
				.getText("menu.file.deleteArticle"));
		deleteArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.deleteArticle();
			}
		});
		file.add(deleteArticle);
		newTopic = new MenuItem(Constants.getText("menu.file.newTopic"));
		newTopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.newTopic();
			}
		});
		file.addSeparator();
		file.add(newTopic);
		deleteTopic = new MenuItem(Constants.getText("menu.file.deleteTopic"));
		deleteTopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.deleteTopic();
			}
		});
		file.add(deleteTopic);
		upload = new Menu(Constants.getText("menu.file.upload"));
		uploadImage = new MenuItem(Constants.getText("menu.file.upload.image"));
		uploadImage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.upload(true);
			}
		});
		upload.add(uploadImage);
		uploadFile = new MenuItem(Constants.getText("menu.file.upload.file"));
		uploadFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.upload(false);
			}
		});
		upload.add(uploadFile);
		file.addSeparator();
		file.add(upload);
		file.addSeparator();
		newDatabase = new MenuItem(Constants.getText("menu.file.newDatabase"));
		newDatabase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.newDatabase();
			}
		});
		if (Constants.getHost() != null) {
			newDatabase.setEnabled(false);
		}
		file.add(newDatabase);
		openDatabase = new MenuItem(Constants.getText("menu.file.openDatabase"));
		openDatabase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.newDatabase();
			}
		});
		if (Constants.getHost() != null) {
			openDatabase.setEnabled(false);
		}
		file.add(openDatabase);
		file.addSeparator();
		close = new MenuItem(Constants.getText("menu.file.close"));
		close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.exit();
			}
		});
		file.add(close);
		edit = new Menu(Constants.getText("menu.edit"));
		renameArticle = new MenuItem(Constants
				.getText("menu.edit.renameArticle"));
		renameArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.renameArticle();
			}
		});
		edit.add(renameArticle);
		renameTopic = new MenuItem(Constants.getText("menu.edit.renameTopic"));
		renameTopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.renameTopic();
			}
		});
		edit.add(renameTopic);
		this.subtopic = new MenuItem(Constants.getText("menu.edit.subtopic"));
		this.subtopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.subTopic();
			}
		});
		edit.add(subtopic);
		edit.addSeparator();
		css = new Menu(Constants.getText("menu.edit.css"));
		plainCSS = new MenuItem(Constants.getText("menu.edit.css.plain"));
		plainCSS.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.editCSS("plain");
			}
		});
		css.add(plainCSS);
		assistCSS = new MenuItem(Constants.getText("menu.edit.css.assist"));
		assistCSS.setEnabled(false);
		css.add(assistCSS);
		edit.add(css);
		edit.addSeparator();
		insertCode = new MenuItem(Constants.getText("menu.edit.insertCode"));
		insertCode.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.insertHTML("CODE");
			}
		});
		edit.add(insertCode);
		insertList = new MenuItem(Constants.getText("menu.edit.insertList"));
		insertList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.insertHTML("LIST");
			}
		});
		edit.add(insertList);
		insertLink = new Menu(Constants.getText("menu.edit.insertLink"));

		insertLinkArticle = new MenuItem(Constants
				.getText("menu.edit.insertLink.article"));
		insertLinkArticle
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						mc.insertLink("Article");
					}
				});
		insertLink.add(insertLinkArticle);
		insertLinkImage = new MenuItem(Constants
				.getText("menu.edit.insertLink.image"));
		insertLinkImage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.insertLink("Image");
			}
		});
		insertLink.add(insertLinkImage);
		insertLinkFile = new MenuItem(Constants
				.getText("menu.edit.insertLink.file"));
		insertLinkFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.insertLink("File");
			}
		});
		insertLink.add(insertLinkFile);
		edit.add(insertLink);
		prefs = new Menu(Constants.getText("menu.prefs"));
		lang = new Menu(Constants.getText("menu.prefs.lang"));
		ButtonGroup langGroup = new ButtonGroup();
		RadioButtonMenuItem langDE = new RadioButtonMenuItem("Deutsch");
		if (Constants.getBundle().getLocale().getLanguage().equals("de")) {
			langDE.setSelected(true);
		}
		langDE.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.changeLanguage("DE");
			}
		});
		langGroup.add(langDE);
		lang.add(langDE);
		RadioButtonMenuItem langEN = new RadioButtonMenuItem("English");
		if (Constants.getBundle().getLocale().getLanguage().equals("en")) {
			langEN.setSelected(true);
		}
		langEN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.changeLanguage("EN");
			}
		});
		langGroup.add(langEN);
		lang.add(langEN);
		prefs.add(lang);
		res = new Menu(Constants.getText("menu.prefs.res"));
		ButtonGroup resGroup = new ButtonGroup();
		RadioButtonMenuItem res600 = new RadioButtonMenuItem("600x480");
		resGroup.add(res600);
		res.add(res600);
		res600.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.changeResolution("1");
			}
		});
		res.add(res600);
		RadioButtonMenuItem res800 = new RadioButtonMenuItem("800x600");
		resGroup.add(res800);
		res.add(res800);
		res800.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.changeResolution("2");
			}
		});
		res.add(res800);
		RadioButtonMenuItem res1024 = new RadioButtonMenuItem("1024x768");
		resGroup.add(res1024);
		res1024.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.changeResolution("3");
			}
		});
		res.add(res1024);
		switch (ViewConstants.MAIN_WIDTH) {
		case 600:
			res600.setSelected(true);
			break;
		case 800:
			res800.setSelected(true);
			break;
		case 1024:
			res1024.setSelected(true);
			break;
		default:
			res600.setSelected(true);
		}
		prefs.add(res);
		this.database = new Menu(Constants.getText("keyword.database"));
		ButtonGroup databaseGroup = new ButtonGroup();
		this.sqlite = new RadioButtonMenuItem(Constants
				.getText("menu.prefs.database.sqlite"));
		this.sqlite.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.changeDatabase("1");
			}
		});
		databaseGroup.add(this.sqlite);
		this.database.add(this.sqlite);
		this.mysql = new RadioButtonMenuItem(Constants
				.getText("menu.prefs.database.mysql"));
		this.mysql.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.changeDatabase("2");
			}
		});
		databaseGroup.add(this.mysql);
		this.database.add(this.mysql);
		if (Constants.getHost() != null) {
			this.mysql.setSelected(true);
			this.mysql.setEnabled(false);
		} else {
			this.sqlite.setSelected(true);
			this.sqlite.setEnabled(false);
		}
		this.prefs.add(this.database);
		this.help = new Menu(Constants.getText("menu.help"));
		this.about = new MenuItem(Constants.getText("menu.help.about"));
		this.about.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.about();
				;
			}
		});
		this.help.add(about);
		this.add(file);
		this.add(edit);
		this.add(prefs);
		this.add(help);
		this.setEditable(false);
		this.setSize(ViewConstants.MENU_WIDTH, ViewConstants.MENU_HEIGTH);
		this.setLocation(ViewConstants.MENU_POS_X, ViewConstants.MENU_POS_Y);
	}

	public void setEditable(boolean b) {
		this.insertCode.setEnabled(b);
		this.insertList.setEnabled(b);
		this.insertLink.setEnabled(b);
	}
}