package de.knowhow.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.knowhow.base.Constants;
import de.knowhow.base.ReleaseNote;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.ArticleListController;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.ArticleList;
import de.knowhow.model.gui.Button;

public class ArticlePlainView extends ArticleView {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private Button confirmButton;
	private Button cancelButton;
	private JScrollPane contentScrollPane;
	private JTextArea ta_content;
	private ArticleListController acl;

	public ArticlePlainView(ArticleListController acl) {
		this.panel = new JPanel();
		this.acl = acl;
		window = this.panel;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridx = 1;
		constraints.gridy = 3;
	}

	protected void init() {
		panel.setLayout(new GridBagLayout());
		panel.setSize(ViewConstants.ARTPLAIN_WIDTH,
				ViewConstants.ARTPLAIN_HEIGTH);
		this.contentScrollPane = new JScrollPane();
		this.ta_content = new JTextArea();
		this.ta_content.setLocation(0, 0);
		this.ta_content.setText(ReleaseNote.getReleaseNote());
		this.contentScrollPane.setSize(ViewConstants.ARTPLAIN_WIDTH - 10,
				ViewConstants.ARTPLAIN_HEIGTH - 40);
		this.contentScrollPane.setPreferredSize(contentScrollPane.getSize());
		this.contentScrollPane.setViewportView(ta_content);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(contentScrollPane, c);
		this.confirmButton = new Button(Constants.getText("button.confirm"));
		this.confirmButton.setSize(confirmButton.getPreferredSize());
		this.confirmButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try {
							acl.confirmContent();
						} catch (DatabaseException e1) {
							acl.error(e1);
						}
					}
				});
		c.fill = GridBagConstraints.NONE;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(confirmButton, c);
		this.cancelButton = new Button(Constants.getText("button.cancel"));
		this.cancelButton.setSize(cancelButton.getPreferredSize());
		this.cancelButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						acl.cancel();
					}
				});
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(cancelButton, c);
		setVisible(false);
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

	@Override
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

	@Override
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

	@Override
	public void insertImageLink(int iD) {
		int pos = ta_content.getCaretPosition();
		String newContent = ta_content.getText().substring(0, pos);
		newContent += "\n<img src=\"tmp/" + iD + "\" />";
		newContent += ta_content.getText().substring(pos,
				ta_content.getText().length());
		ta_content.setText(newContent);
		ta_content.setCaretPosition(0);
	}

	@Override
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
	public boolean isComponent() {
		return true;
	}

	@Override
	public String getContent() {
		return ta_content.getText();
	}
}