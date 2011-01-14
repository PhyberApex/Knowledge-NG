package de.knowhow.model.gui;

import java.io.File;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import de.knowhow.base.ReleaseNote;
import de.knowhow.controller.ArticleListController;
import de.knowhow.controller.AttachmentListController;
import de.knowhow.exception.DatabaseException;

public class HTMLEditor extends JEditorPane {

	private static final long serialVersionUID = 1L;
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
		String htmlString = ReleaseNote.getReleaseNote();
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
					else if (protocol.equals("http")) {
						//TODO browser öffnen mit dem link
					}
				}
			}
		});
	}

	public void setFc_save(JFileChooser fc_save) {
		this.fc_save = fc_save;
	}

	public void setText(String text) {
		super.setText(text);
	}
}