package de.knowhow.view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.StyleSheet;
import de.knowhow.base.Constants;
import de.knowhow.base.ReleaseNote;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.ArticleListController;
import de.knowhow.controller.AttachmentListController;
import de.knowhow.controller.CSSController;
import de.knowhow.model.ArticleList;
import de.knowhow.model.gui.HTMLEditor;
import de.knowhow.model.gui.HTMLEditorKit;
import de.knowhow.model.gui.Label;

public class ArticleRenderView extends JPanel implements Observer, Runnable {

	private static final long serialVersionUID = 1L;
	private JScrollPane contentScrollPane;
	private Label lastEdit;
	private HTMLEditor htmlEdit_content;
	private ArticleListController acl;
	private AttachmentListController attachcl;
	private CSSController csc;

	public ArticleRenderView(ArticleListController acl,
			AttachmentListController attachcl, CSSController csc) {
		super();
		this.attachcl = attachcl;
		this.acl = acl;
		this.csc = csc;
	}

	private void init() {
		this.setLayout(null);
		this.contentScrollPane = new JScrollPane();
		this.htmlEdit_content = new HTMLEditor(attachcl, acl);
		this.htmlEdit_content.setLocation(0, 0);
		this.htmlEdit_content.setEditable(false);
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
		this.lastEdit = new Label(Constants.getText("renderView.lastEdit")
				+ " -");
		this.lastEdit.setSize(ViewConstants.ARTRENDER_WIDTH - 20, 20);
		this.lastEdit.setLocation(10, ViewConstants.ARTRENDER_HEIGTH - 30);
		this.lastEdit.setHorizontalTextPosition(Label.CENTER);
		this.setSize(ViewConstants.ARTRENDER_WIDTH,
				ViewConstants.ARTRENDER_HEIGTH);
		this.setLocation(ViewConstants.ARTPLAIN_POS_X,
				ViewConstants.ARTRENDER_POS_Y);
		this.add(lastEdit);
		this.add(contentScrollPane);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.htmlEdit_content.setText(((ArticleList) arg0).getCurrArticle()
				.getContent());
		this.lastEdit.setText(Constants.getText("renderView.lastEdit") + " "
				+ ((ArticleList) arg0).getCurrArticle().getLastEdit());
		this.htmlEdit_content.setCaretPosition(0);
	}

	public void cancel() {
		this.htmlEdit_content.setText(ReleaseNote.getReleaseNote());
		this.lastEdit.setText(Constants.getText("renderView.lastEdit") + " -");
	}

	public String getArticleContent() {
		return this.htmlEdit_content.getText();
	}

	@Override
	public void run() {
		init();
	}
}