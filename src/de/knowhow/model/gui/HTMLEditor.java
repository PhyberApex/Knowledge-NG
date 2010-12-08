package de.knowhow.model.gui;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.html.HTML;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.HTMLEditorKit.HTMLFactory;

import de.knowhow.base.Constants;
import de.knowhow.base.ReleaseNote;
import de.knowhow.controller.ArticleListController;
import de.knowhow.controller.AttachmentListController;
import de.knowhow.exception.DatabaseException;

public class HTMLEditor extends JEditorPane {
	private JFileChooser fc_save;
	private AttachmentListController attachcl;
	private ArticleListController acl;

	public HTMLEditor(AttachmentListController attachcl,
			ArticleListController acl) {
		super();
		this.attachcl = attachcl;
		this.acl = acl;
		init();
	}

	private void init() {
		HTMLEditorKit kit = new HTMLEditorKit();
		this.setEditorKit(kit);
		StyleSheet styleSheet = kit.getStyleSheet();
		makeStylsheet(styleSheet, "style.css");
		String htmlString = ReleaseNote.getReleaseNote();
		kit.getActions();
		Document doc = kit.createDefaultDocument();
		this.setDocument(doc);
		this.setText(htmlString);
		this.setLayout(null);
		this.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					int help = e.getDescription().indexOf("://");
					String protocol = e.getDescription().substring(0, help);
					if (protocol.equals("article")) {
						acl.setCurrArticle(acl.getArticleByID(Integer
								.parseInt(e.getDescription()
										.substring(help + 3))));
					} else if (protocol.equals("attachment")) {
						int att_ID = Integer.parseInt(e.getDescription()
								.substring(help + 3));
						fc_save = new JFileChooser();
						fc_save.setFileSelectionMode(JFileChooser.FILES_ONLY);
						int returnVal = fc_save.showSaveDialog(HTMLEditor.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc_save.getSelectedFile();
							String path = file.getAbsolutePath();
							try {
								attachcl.writeAttToFS(att_ID, path);
							} catch (DatabaseException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
	}

	public void setFc_save(JFileChooser fc_save) {
		this.fc_save = fc_save;
	}

	private void makeStylsheet(StyleSheet style, String path) {
		try {
			File input = new File(path);
			FileReader in = new FileReader(input);
			BufferedReader br = new BufferedReader(in);
			String line = br.readLine();
			while (line != null) {
				style.addRule(line);
				line = br.readLine();
			}
			br.close();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	public void setText(String text) {
		super.setText(text);
	}
}