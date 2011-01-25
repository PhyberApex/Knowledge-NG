package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JScrollPane;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.ArticleListController;
import de.knowhow.model.Article;
import de.knowhow.model.ArticleList;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.Table;
import de.knowhow.model.gui.TableModel;

public class ArticleLinkView extends ArticleView {

	private static final long serialVersionUID = 1L;
	private Dialog dialog;
	private ArticleListController acl;
	private JScrollPane spArticles;
	private Table tbArticles;
	private Button btConfirm;
	private Button btCancel;

	public ArticleLinkView(ArticleListController acl) {
		this.dialog = new Dialog();
		window = dialog;
		this.acl = acl;
	}

	protected void init() {
		dialog.setSize(ViewConstants.ARTLINK_WIDTH,
				ViewConstants.ARTLINK_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((d.width - dialog.getSize().width) / 2,
				(d.height - dialog.getSize().height) / 2);
		dialog.initPane();
		this.spArticles = new JScrollPane();
		this.spArticles.setSize(ViewConstants.ARTLINK_WIDTH,
				ViewConstants.ARTLINK_HEIGTH - 40);
		this.spArticles.setLocation(0, 0);
		this.tbArticles = new Table();
		this.spArticles.setViewportView(tbArticles);
		this.btCancel = new Button(Constants.getText("button.cancel"));
		this.btCancel.setSize(130, 20);
		this.btCancel.setLocation(dialog.getWidth() - btCancel.getWidth() - 10,
				dialog.getHeight() - 35);
		this.btCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		this.btConfirm = new Button(Constants.getText("button.confirm"));
		this.btConfirm.setSize(130, 20);
		this.btConfirm.setLocation(10, dialog.getHeight() - 35);
		this.btConfirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int row;
				row = tbArticles.getSelectedRow();
				acl.insertArticleLink(Integer.parseInt(tbArticles.getModel()
						.getValueAt(row, 0).toString()));
				setVisible(false);
			}
		});
		dialog.getPane().add(spArticles);
		dialog.getPane().add(btConfirm);
		dialog.getPane().add(btCancel);
	}

	@Override
	public boolean isComponent() {
		return false;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ArticleList artL = (ArticleList) arg0;
		ArrayList<Article> arts = artL.getArticles();
		String[] names = { "ID", "Name", "LastEdit" };
		Object[][] rowData = new Object[arts.size()][3];
		for (int i = 0; i < arts.size(); i++) {
			rowData[i][0] = arts.get(i).getArticle_ID();
			rowData[i][1] = arts.get(i).getName();
			rowData[i][2] = arts.get(i).getLastEdit();
		}
		TableModel model = new TableModel(rowData, names);
		this.tbArticles.setModel(model);
	}
}