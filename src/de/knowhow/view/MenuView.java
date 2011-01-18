package de.knowhow.view;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.log4j.Logger;

import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Menu;
import de.knowhow.model.gui.MenuItem;
import de.knowhow.model.gui.RadioButtonMenuItem;
import de.knowhow.model.gui.Textfield;

public class MenuView extends JMenuBar implements Runnable {

	private static final long serialVersionUID = 1L;
	private JMenu file;
	private MenuItem newArticle;
	private MenuItem newTopic;
	private Menu upload;
	private MenuItem uploadImage;
	private MenuItem uploadFile;
	private MenuItem newDatabase;
	private MenuItem openDatabase;
	private Menu export;
	private MenuItem exportHTML;
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
	private MenuItem mysql_change;

	private Menu help;
	private MenuItem about;

	private Textfield tfSearch;
	private Button btSearch;
	private JButton bt_plain;
	private JButton bt_render;

	private MainController mc;

	private static Logger logger = Logger.getLogger(MenuView.class.getName());

	public MenuView(MainController mc) {
		super();
		this.mc = mc;
	}

	public void init() {
		file = new Menu(Constants.getText("menu.file"));
		newArticle = new MenuItem(Constants.getText("menu.file.newArticle"));
		newArticle
				.setIcon(new ImageIcon(
						ClassLoader
								.getSystemResource("de/knowhow/resource/img/icon/add_file.png")));
		newArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("new article clicked");
				mc.newArticle();
			}
		});
		file.add(newArticle);
		deleteArticle = new MenuItem(
				Constants.getText("menu.file.deleteArticle"));
		deleteArticle
				.setIcon(new ImageIcon(
						ClassLoader
								.getSystemResource("de/knowhow/resource/img/icon/delete_file.png")));
		deleteArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("delete article clicked");
				mc.deleteArticle();
			}
		});
		file.add(deleteArticle);
		newTopic = new MenuItem(Constants.getText("menu.file.newTopic"));
		newTopic.setIcon(new ImageIcon(
				ClassLoader
						.getSystemResource("de/knowhow/resource/img/icon/add_folder.png")));
		newTopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("new topic clicked");
				mc.newTopic();
			}
		});
		file.addSeparator();
		file.add(newTopic);
		deleteTopic = new MenuItem(Constants.getText("menu.file.deleteTopic"));
		deleteTopic
				.setIcon(new ImageIcon(
						ClassLoader
								.getSystemResource("de/knowhow/resource/img/icon/delete_folder.png")));
		deleteTopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("delete topic clicked");
				mc.deleteTopic();
			}
		});
		file.add(deleteTopic);
		upload = new Menu(Constants.getText("menu.file.upload"));
		upload.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/icon/upload.png")));
		uploadImage = new MenuItem(Constants.getText("menu.file.upload.image"));
		uploadImage
				.setIcon(new ImageIcon(
						ClassLoader
								.getSystemResource("de/knowhow/resource/img/icon/upload_image.png")));
		uploadImage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("upload image clicked");
				mc.upload(true);
			}
		});
		upload.add(uploadImage);
		uploadFile = new MenuItem(Constants.getText("menu.file.upload.file"));
		uploadFile
				.setIcon(new ImageIcon(
						ClassLoader
								.getSystemResource("de/knowhow/resource/img/icon/upload_file.png")));
		uploadFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("upload file clicked");
				mc.upload(false);
			}
		});
		upload.add(uploadFile);
		file.addSeparator();
		file.add(upload);
		file.addSeparator();
		export = new Menu(Constants.getText("menu.file.export"));
		export.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/icon/export.png")));
		exportHTML = new MenuItem(Constants.getText("menu.file.export.HTML"));
		exportHTML.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("export to HTML clicked");
				mc.export("HTML");
			}
		});
		export.add(exportHTML);
		file.add(export);
		file.addSeparator();
		newDatabase = new MenuItem(Constants.getText("menu.file.newDatabase"));
		newDatabase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("new database clicked");
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
				logger.debug("open database clicked");
				mc.newDatabase();
			}
		});
		if (Constants.getHost() != null) {
			openDatabase.setEnabled(false);
		}
		file.add(openDatabase);
		file.addSeparator();
		close = new MenuItem(Constants.getText("menu.file.close"));
		close.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/icon/close.png")));
		close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("exit clicked");
				mc.exit();
			}
		});
		file.add(close);
		edit = new Menu(Constants.getText("menu.edit"));
		renameArticle = new MenuItem(
				Constants.getText("menu.edit.renameArticle"));
		renameArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("rename article clicked");
				mc.renameArticle();
			}
		});
		edit.add(renameArticle);
		renameTopic = new MenuItem(Constants.getText("menu.edit.renameTopic"));
		renameTopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("rename topic clicked");
				mc.renameTopic();
			}
		});
		edit.add(renameTopic);
		this.subtopic = new MenuItem(Constants.getText("menu.edit.subtopic"));
		this.subtopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("make subtopic clicked");
				mc.subTopic();
			}
		});
		edit.add(subtopic);
		edit.addSeparator();
		css = new Menu(Constants.getText("menu.edit.css"));
		css.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/icon/css.png")));
		plainCSS = new MenuItem(Constants.getText("menu.edit.css.plain"));
		plainCSS.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("css plain editor clicked");
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
				logger.debug("insert HTML tag \"<code>\" clicked");
				mc.insertHTML("CODE");
			}
		});
		edit.add(insertCode);
		insertList = new MenuItem(Constants.getText("menu.edit.insertList"));
		insertList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("insert HTML tag \"<ul>\" clicked");
				mc.insertHTML("LIST");
			}
		});
		edit.add(insertList);
		insertLink = new Menu(Constants.getText("menu.edit.insertLink"));
		insertLinkArticle = new MenuItem(
				Constants.getText("menu.edit.insertLink.article"));
		insertLinkArticle
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						logger.debug("insert HTML tag \"<a>\" with article clicked");
						mc.insertLink("Article");
					}
				});
		insertLink.add(insertLinkArticle);
		insertLinkImage = new MenuItem(
				Constants.getText("menu.edit.insertLink.image"));
		insertLinkImage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("insert HTML tag \"<a>\" with image clicked");
				mc.insertLink("Image");
			}
		});
		insertLink.add(insertLinkImage);
		insertLinkFile = new MenuItem(
				Constants.getText("menu.edit.insertLink.file"));
		insertLinkFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("insert HTML tag \"<a>\" with file clicked");
				mc.insertLink("File");
			}
		});
		insertLink.add(insertLinkFile);
		edit.add(insertLink);
		prefs = new Menu(Constants.getText("menu.prefs"));
		lang = new Menu(Constants.getText("menu.prefs.lang"));
		lang.setIcon(new ImageIcon(
				ClassLoader
						.getSystemResource("de/knowhow/resource/img/icon/prefs_language.png")));
		ButtonGroup langGroup = new ButtonGroup();
		RadioButtonMenuItem langDE = new RadioButtonMenuItem("Deutsch");
		langDE.setIcon(new ImageIcon(
				ClassLoader
						.getSystemResource("de/knowhow/resource/img/icon/de_DE_Icon.png")));
		if (Constants.getBundle().getLocale().getLanguage().equals("de")) {
			langDE.setSelected(true);
		}
		langDE.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("change language to DE clicked");
				mc.changeLanguage("DE");
			}
		});
		langGroup.add(langDE);
		lang.add(langDE);
		RadioButtonMenuItem langEN = new RadioButtonMenuItem("English");
		langEN.setIcon(new ImageIcon(
				ClassLoader
						.getSystemResource("de/knowhow/resource/img/icon/en_EN_Icon.png")));
		if (Constants.getBundle().getLocale().getLanguage().equals("en")) {
			langEN.setSelected(true);
		}
		langEN.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("change language to EN clicked");
				mc.changeLanguage("EN");
			}
		});
		langGroup.add(langEN);
		lang.add(langEN);
		prefs.add(lang);
		res = new Menu(Constants.getText("menu.prefs.res"));
		res.setIcon(new ImageIcon(
				ClassLoader
						.getSystemResource("de/knowhow/resource/img/icon/prefs_resolution.png")));
		ButtonGroup resGroup = new ButtonGroup();
		RadioButtonMenuItem res600 = new RadioButtonMenuItem("600x480");
		resGroup.add(res600);
		res.add(res600);
		res600.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("change resolution to 1 clicked");
				mc.changeResolution("1");
			}
		});
		res.add(res600);
		RadioButtonMenuItem res800 = new RadioButtonMenuItem("800x600");
		resGroup.add(res800);
		res.add(res800);
		res800.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("change resolution to 2 clicked");
				mc.changeResolution("2");
			}
		});
		res.add(res800);
		RadioButtonMenuItem res1024 = new RadioButtonMenuItem("1024x768");
		resGroup.add(res1024);
		res1024.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("change resolution to 3 clicked");
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
		database.setIcon(new ImageIcon(
				ClassLoader
						.getSystemResource("de/knowhow/resource/img/icon/prefs_database.png")));
		ButtonGroup databaseGroup = new ButtonGroup();
		this.sqlite = new RadioButtonMenuItem(
				Constants.getText("menu.prefs.database.sqlite"));
		this.sqlite.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("change database to sqlite clicked");
				sqlite.setEnabled(false);
				mysql.setEnabled(true);
				mc.changeDatabase("1");
			}
		});
		databaseGroup.add(this.sqlite);
		this.database.add(this.sqlite);
		this.mysql = new RadioButtonMenuItem(
				Constants.getText("menu.prefs.database.mysql"));
		this.mysql.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("change database to mysql clicked");
				mysql.setEnabled(false);
				sqlite.setEnabled(true);
				mc.changeDatabase("2");
			}
		});
		databaseGroup.add(this.mysql);
		this.database.add(this.mysql);
		this.mysql_change = new MenuItem(
				Constants.getText("menu.prefs.database.mysql_change"));
		this.mysql_change
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						logger.debug("change mysql details clicked");
						mc.changeDatabase("2");
					}
				});
		databaseGroup.add(this.mysql_change);
		this.database.add(this.mysql_change);
		if (Constants.getHost() != null) {
			this.mysql.setSelected(true);
			this.mysql.setEnabled(false);
			this.mysql_change.setEnabled(true);
		} else {
			this.sqlite.setSelected(true);
			this.sqlite.setEnabled(false);
			this.mysql_change.setEnabled(false);
		}
		this.prefs.add(this.database);
		this.help = new Menu(Constants.getText("menu.help"));
		this.about = new MenuItem(Constants.getText("menu.help.about"));
		this.about.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("about clicked");
				mc.about();
			}
		});
		this.help.add(about);
		this.bt_plain = new JButton(
				new ImageIcon(ClassLoader
						.getSystemResource("de/knowhow/resource/img/plain.PNG")));
		this.bt_plain.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("plain article view clicked");
				mc.setPlainVisible(true);
				bt_render.setEnabled(true);
				mc.setTopicChooserVisible(true);
				mc.setRenderVisible(false);
				bt_plain.setEnabled(false);
			}
		});
		this.bt_render = new JButton(
				new ImageIcon(
						ClassLoader
								.getSystemResource("de/knowhow/resource/img/render.PNG")));
		this.bt_render.setEnabled(false);
		this.bt_render.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("rendered article view clicked");
				mc.setRenderVisible(true);
				bt_plain.setEnabled(true);
				mc.setTopicChooserVisible(false);
				mc.setPlainVisible(false);
				bt_render.setEnabled(false);
			}
		});
		this.tfSearch = new Textfield("");
		this.btSearch = new Button(Constants.getText("mainView.btSearch"));
		this.btSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.search(tfSearch.getText());
			}
		});
		this.add(file);
		this.add(edit);
		this.add(prefs);
		this.add(help);
		this.add(tfSearch);
		this.add(btSearch);
		this.add(bt_plain);
		this.add(bt_render);
		this.setEditable(false);
		this.setSize(ViewConstants.MENU_WIDTH, ViewConstants.MENU_HEIGTH);
		this.setLocation(ViewConstants.MENU_POS_X, ViewConstants.MENU_POS_Y);
	}

	public void setEditable(boolean b) {
		this.insertCode.setEnabled(b);
		this.insertList.setEnabled(b);
		this.insertLink.setEnabled(b);
	}

	@Override
	public void run() {
		init();
	}
}