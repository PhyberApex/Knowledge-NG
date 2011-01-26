package de.knowhow.view;

import java.util.Observable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTML;
import javax.swing.text.html.StyleSheet;
import de.knowhow.base.ReleaseNote;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.ArticleListController;
import de.knowhow.controller.CSSController;
import de.knowhow.model.ArticleList;
import de.knowhow.model.gui.HTMLEditor;
import de.knowhow.model.gui.HTMLEditorKit;

public class ArticleRenderView extends ArticleView {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane contentScrollPane;
	private HTMLEditor htmlEdit_content;
	private ArticleListController acl;
	private CSSController csc;

	public ArticleRenderView(ArticleListController acl, CSSController csc) {
		this.panel = new JPanel();
		window = this.panel;
		this.acl = acl;
		this.csc = csc;
	}

	protected void init() {
		panel.setLayout(null);
		this.contentScrollPane = new JScrollPane();
		this.htmlEdit_content = new HTMLEditor(acl);
		this.htmlEdit_content.setLocation(0, 0);
		HTMLEditorKit kit = new HTMLEditorKit();
		this.htmlEdit_content.setEditorKit(kit);
		StyleSheet styleSheet = kit.getStyleSheet();
		for (int i = 0; i < csc.getStyleSheetInLines().size(); i++) {
			styleSheet.addRule(csc.getStyleSheetInLines().get(i));
		}
		kit.getActions();
		Document doc = kit.createDefaultDocument();
		this.htmlEdit_content.setDocument(doc);
		this.htmlEdit_content.setText(ReleaseNote.getReleaseNote());
		this.contentScrollPane.setLocation(5, 0);
		this.contentScrollPane.setSize(ViewConstants.ARTRENDER_WIDTH - 10,
				ViewConstants.ARTRENDER_HEIGTH - 40);
		this.contentScrollPane.setViewportView(htmlEdit_content);
		panel.setSize(ViewConstants.ARTRENDER_WIDTH,
				ViewConstants.ARTRENDER_HEIGTH);
		panel.setLocation(ViewConstants.ARTPLAIN_POS_X,
				ViewConstants.ARTRENDER_POS_Y);
		panel.add(contentScrollPane);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.htmlEdit_content.setText(((ArticleList) arg0).getCurrArticle()
				.getContent());
		this.htmlEdit_content.setCaretPosition(0);
	}

	public void cancel() {
		this.htmlEdit_content.setText(ReleaseNote.getReleaseNote());
	}

	public String getArticleContent() {
		return this.htmlEdit_content.getText();
	}

	@Override
	public void insertArticleLink(int iD) {
		new HTMLEditorKit.InsertHTMLTextAction(null, "\n<a href=\"article://"
				+ iD + "\">PLACE YOUR DESCRIPTIV TEXT HERE</a>", HTML.Tag.BODY,
				HTML.Tag.A).actionPerformed(null);
	}

	@Override
	public void insertFileLink(int iD) {
		new HTMLEditorKit.InsertHTMLTextAction(null,
				"\n<a href=\"attachment://" + iD
						+ "\">PLACE YOUR DESCRIPTIV TEXT HERE</a>",
				HTML.Tag.BODY, HTML.Tag.A).actionPerformed(null);
	}

	@Override
	public void insertHTML(String tag) {
		if (tag.equals("CODE")) {
			new HTMLEditorKit.InsertHTMLTextAction(null,
					"\n<code>PASTE YOUR CODE HERE</code>\n", HTML.Tag.BODY,
					HTML.Tag.CODE).actionPerformed(null);
		} else if (tag.equals("LIST")) {
			new HTMLEditorKit.InsertHTMLTextAction(null,
					"\n<ul>\n<li>Listelement</li>\n</ul>\n", HTML.Tag.BODY,
					HTML.Tag.UL).actionPerformed(null);
		} else if (tag.equals("LISTELEMENT")) {
			new HTMLEditorKit.InsertHTMLTextAction(null,
					"\n<li>Listelement</li>\n", HTML.Tag.UL, HTML.Tag.LI)
					.actionPerformed(null);
		}
	}

	@Override
	public void insertImageLink(int iD) {
		new HTMLEditorKit.InsertHTMLTextAction(null, "\n<img src=\"tmp/" + iD
				+ "\" />", HTML.Tag.BODY, HTML.Tag.IMG).actionPerformed(null);
	}

	@Override
	public boolean isComponent() {
		return true;
	}
}