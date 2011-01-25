package de.knowhow.view;

import java.util.Observable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import de.knowhow.base.ReleaseNote;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.ArticleListController;
import de.knowhow.model.ArticleList;

public class ArticlePlainView extends ArticleView {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane contentScrollPane;
	private JTextArea ta_content;
	@SuppressWarnings("unused")
	private ArticleListController acl;

	public ArticlePlainView(ArticleListController acl) {
		this.panel = new JPanel();
		window = this.panel;
		this.acl = acl;
	}

	protected void init() {
		panel.setLayout(null);
		this.contentScrollPane = new JScrollPane();
		this.ta_content = new JTextArea();
		this.ta_content.setLocation(0, 0);
		this.ta_content.setText(ReleaseNote.getReleaseNote());
		this.contentScrollPane.setLocation(5, 0);
		this.contentScrollPane.setSize(ViewConstants.ARTPLAIN_WIDTH - 10,
				ViewConstants.ARTPLAIN_HEIGTH);
		this.contentScrollPane.setViewportView(ta_content);

		panel.setSize(ViewConstants.ARTPLAIN_WIDTH,
				ViewConstants.ARTPLAIN_HEIGTH);
		panel.setLocation(ViewConstants.ARTPLAIN_POS_X,
				ViewConstants.ARTPLAIN_POS_Y);
		panel.add(contentScrollPane);

	}

	public String getArticleContent() {
		return this.ta_content.getText();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.ta_content.setText(((ArticleList) arg0).getCurrArticle()
				.getContent());
		this.ta_content.setCaretPosition(0);
	}

	public void cancel() {
		this.ta_content.setText(ReleaseNote.getReleaseNote());
	}

	public void insertHTML(String tag) {
		int pos = ta_content.getCaretPosition();
		String newContent = ta_content.getText().substring(0, pos);
		if (tag.equals("CODE")) {
			newContent += "\n<code>PASTE YOUR CODE HERE</code>\n";
		} else if (tag.equals("LIST")) {
			newContent += "\n<ul>\n<li>Listelement</li>\n</ul>\n";
		} else if (tag.equals("LISTELEMENT")) {
			newContent += "\n<li>Listelement</li>\n";
		}
		newContent += ta_content.getText().substring(pos,
				ta_content.getText().length());
		ta_content.setText(newContent);
		ta_content.setCaretPosition(0);
	}

	public void insertFileLink(int iD) {
		int pos = ta_content.getCaretPosition();
		String newContent = ta_content.getText().substring(0, pos);
		newContent += "\n<a href=\"attachment://" + iD
				+ "\">PLACE YOUR DESCRIPTIV TEXT HERE</a>";
		newContent += ta_content.getText().substring(pos,
				ta_content.getText().length());
		ta_content.setText(newContent);
		ta_content.setCaretPosition(0);
	}

	public void insertImageLink(int iD) {
		int pos = ta_content.getCaretPosition();
		String newContent = ta_content.getText().substring(0, pos);
		newContent += "\n<img src=\"tmp/" + iD + "\" />";
		newContent += ta_content.getText().substring(pos,
				ta_content.getText().length());
		ta_content.setText(newContent);
		ta_content.setCaretPosition(0);
	}

	public void insertArticleLink(int iD) {
		int pos = ta_content.getCaretPosition();
		String newContent = ta_content.getText().substring(0, pos);
		newContent += "\n<a href=\"article://" + iD
				+ "\">PLACE YOUR DESCRIPTIV TEXT HERE</a>";
		newContent += ta_content.getText().substring(pos,
				ta_content.getText().length());
		ta_content.setText(newContent);
		ta_content.setCaretPosition(0);
	}
}