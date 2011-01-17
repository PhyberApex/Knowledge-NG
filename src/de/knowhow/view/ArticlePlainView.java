package de.knowhow.view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import de.knowhow.base.ReleaseNote;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.ArticleListController;
import de.knowhow.model.ArticleList;

public class ArticlePlainView extends JPanel implements Observer, Runnable {

	private static final long serialVersionUID = 1L;
	private JScrollPane contentScrollPane;
	private JTextArea ta_content;
	@SuppressWarnings("unused")
	private ArticleListController acl;

	public ArticlePlainView(ArticleListController acl) {
		super();
		this.acl = acl;
	}

	private void init() {
		this.setLayout(null);
		this.contentScrollPane = new JScrollPane();
		this.ta_content = new JTextArea();
		this.ta_content.setLocation(0, 0);
		this.ta_content.setText(ReleaseNote.getReleaseNote());
		this.contentScrollPane.setLocation(5, 0);
		this.contentScrollPane.setSize(ViewConstants.ARTPLAIN_WIDTH - 10,
				ViewConstants.ARTPLAIN_HEIGTH);
		this.contentScrollPane.setViewportView(ta_content);

		this.setSize(ViewConstants.ARTPLAIN_WIDTH,
				ViewConstants.ARTPLAIN_HEIGTH);
		this.setLocation(ViewConstants.ARTPLAIN_POS_X,
				ViewConstants.ARTPLAIN_POS_Y);
		this.add(contentScrollPane);

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

	@Override
	public void run() {
		init();
	}
}