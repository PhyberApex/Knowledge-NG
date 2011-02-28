package de.knowhow.view;

import java.awt.GridBagConstraints;
import java.util.Observable;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import org.apache.log4j.Logger;
import de.knowhow.base.Config;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;
import de.knowhow.extra.export.ExportFactory;
import de.knowhow.model.db.DAO;
import de.knowhow.model.db.DAO_MYSQL;
import de.knowhow.model.db.DAO_SQLite;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Menu;
import de.knowhow.model.gui.MenuItem;
import de.knowhow.model.gui.RadioButtonMenuItem;
import de.knowhow.model.gui.Textfield;

public class MenuView extends View {

	private static final long serialVersionUID = 1L;
	private JMenuBar menu;
	private JMenu file;
	private MenuItem newArticle;
	private MenuItem newTopic;
	private MenuItem print;
	private Menu upload;
	private MenuItem uploadImage;
	private MenuItem uploadFile;
	private MenuItem newDatabase;
	private MenuItem openDatabase;
	private Menu export;
	private MenuItem exportHTML;
	private MenuItem exportPDF;
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
	private MenuItem insertListElement;
	private Menu insertLink;
	private MenuItem insertLinkArticle;
	private MenuItem insertLinkImage;
	private MenuItem insertLinkFile;

	private Menu prefs;
	private Menu lang;
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
		this.menu = new JMenuBar();
		window = menu;
		this.mc = mc;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.weightx = 1;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 0;
	}

	public void init() {
		menu.setSize(ViewConstants.MENU_WIDTH, ViewConstants.MENU_HEIGTH);
		menu.setPreferredSize(menu.getSize());
		file = new Menu(Constants.getText("menu.file"));
		newArticle = new MenuItem(Constants.getText("menu.file.newArticle"));
		newArticle.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/add_file.png"));
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
				.setIcon(Constants
						.createImageIcon("/de/knowhow/resource/img/icon/delete_file.png"));
		deleteArticle.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("delete article clicked");
				mc.deleteArticle();
			}
		});
		file.add(deleteArticle);
		newTopic = new MenuItem(Constants.getText("menu.file.newTopic"));
		newTopic.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/add_folder.png"));
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
				.setIcon(Constants
						.createImageIcon("/de/knowhow/resource/img/icon/delete_folder.png"));
		deleteTopic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("delete topic clicked");
				mc.deleteTopic();
			}
		});
		file.add(deleteTopic);
		file.addSeparator();
		print = new MenuItem(Constants.getText("menu.file.print"));
		print.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/print.png"));
		print.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("print clicked");
				mc.print();
			}
		});
		file.add(print);
		upload = new Menu(Constants.getText("menu.file.upload"));
		upload.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/icon/upload.png")));
		uploadImage = new MenuItem(Constants.getText("menu.file.upload.image"));
		uploadImage
				.setIcon(Constants
						.createImageIcon("/de/knowhow/resource/img/icon/upload_image.png"));
		uploadImage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("upload image clicked");
				mc.upload(true);
			}
		});
		upload.add(uploadImage);
		uploadFile = new MenuItem(Constants.getText("menu.file.upload.file"));
		uploadFile
				.setIcon(Constants
						.createImageIcon("/de/knowhow/resource/img/icon/upload_file.png"));
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
		export.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/export.png"));
		exportHTML = new MenuItem(Constants.getText("menu.file.export.HTML"));
		exportHTML.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("export to HTML clicked");
				mc.export(ExportFactory.HTML);
			}
		});
		export.add(exportHTML);
		exportPDF = new MenuItem(Constants.getText("menu.file.export.PDF"));
		exportPDF.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("export to PDF clicked");
				mc.export(ExportFactory.PDF);
			}
		});
		// TODO DELETE following line for testing and as pdf export is working properly! 
		//exportPDF.setEnabled(false);
		export.add(exportPDF);
		file.add(export);
		file.addSeparator();
		newDatabase = new MenuItem(Constants.getText("menu.file.newDatabase"));
		newDatabase.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/new_db.png"));
		newDatabase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("new database clicked");
				mc.newDatabase();
			}
		});
		file.add(newDatabase);
		openDatabase = new MenuItem(Constants.getText("menu.file.openDatabase"));
		openDatabase.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/open_db.png"));
		openDatabase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("open database clicked");
				mc.newDatabase();
			}
		});
		file.add(openDatabase);
		file.addSeparator();
		close = new MenuItem(Constants.getText("menu.file.close"));
		close.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/close.png"));
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
		css.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/css.png"));
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
		insertListElement = new MenuItem(
				Constants.getText("menu.edit.insertListElement"));
		insertListElement
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						logger.debug("insert HTML tag \"<li>\" clicked");
						mc.insertHTML("LISTELEMENT");
					}
				});
		edit.add(insertListElement);
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
		lang.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/prefs_language.png"));
		ButtonGroup langGroup = new ButtonGroup();
		RadioButtonMenuItem langDE = new RadioButtonMenuItem("Deutsch");
		langDE.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/de_DE_Icon.png"));
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
		langEN.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/en_EN_Icon.png"));
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
		this.database = new Menu(Constants.getText("keyword.database"));
		database.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/prefs_database.png"));
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
		this.prefs.add(this.database);
		this.help = new Menu(Constants.getText("menu.help"));
		this.about = new MenuItem(Constants.getText("menu.help.about"));
		this.about.setIcon(Constants
				.createImageIcon("/de/knowhow/resource/img/icon/about.png"));
		this.about.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("about clicked");
				mc.about();
			}
		});
		this.help.add(about);
		this.bt_plain = new JButton(
				Constants
						.createImageIcon("/de/knowhow/resource/img/plainButton.png"));
		this.bt_plain.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("plain article view clicked");
				mc.changeArticleView(ArticleView.PLAINVIEW);
				bt_render.setEnabled(true);
				bt_plain.setEnabled(false);
			}
		});
		this.bt_render = new JButton(
				Constants
						.createImageIcon("/de/knowhow/resource/img/renderButton.png"));
		this.bt_render.setEnabled(false);
		this.bt_render.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.debug("rendered article view clicked");
				mc.changeArticleView(ArticleView.RENDEREDVIEW);
				bt_plain.setEnabled(true);
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
		// Enable or disable some features regarding databsetype
		Class<? extends DAO> dbClass = Config.getInstance().getDBHandle()
				.getClass();
		if (dbClass == DAO_MYSQL.class) {
			this.mysql.setSelected(true);
			this.mysql.setEnabled(false);
			this.mysql_change.setEnabled(true);
			newDatabase.setEnabled(false);
			openDatabase.setEnabled(false);
		} else if (dbClass == DAO_SQLite.class) {
			this.sqlite.setSelected(true);
			this.sqlite.setEnabled(false);
			this.mysql_change.setEnabled(false);
		}
		menu.add(file);
		menu.add(edit);
		menu.add(prefs);
		menu.add(help);
		menu.add(tfSearch);
		menu.add(btSearch);
		JLabel puffer = new JLabel("     ");
		menu.add(puffer);
		menu.add(bt_render);
		menu.add(bt_plain);
		menu.setSize(ViewConstants.MENU_WIDTH, ViewConstants.MENU_HEIGTH);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// Nothing to do here
	}

	@Override
	public boolean isComponent() {
		return true;
	}
}