package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.ArticleListController;
import de.knowhow.model.Article;
import de.knowhow.model.ArticleList;
import de.knowhow.model.Attachment;
import de.knowhow.model.AttachmentList;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Table;
import de.knowhow.model.gui.TableModel;

public class ArticleLinkView extends JFrame implements Observer {

	private ArticleListController acl;
	private JScrollPane spArticles;
	private Table tbArticles;
	private Button btConfirm;
	private Button btCancel;

	public ArticleLinkView(ArticleListController acl) {
		super();
		this.acl = acl;
		this.setLayout(null);
		this.setUndecorated(true);
		init();
	}

	private void init() {
		this.setSize(ViewConstants.ARTLINK_WIDTH, ViewConstants.ARTLINK_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this
				.getSize().height) / 2);
		this.spArticles = new JScrollPane();
		this.spArticles.setSize(ViewConstants.ARTLINK_WIDTH,
				ViewConstants.ARTLINK_HEIGTH - 40);
		this.spArticles.setLocation(0, 0);
		this.tbArticles = new Table();
		this.spArticles.setViewportView(tbArticles);
		this.btCancel = new Button(Constants.getText("button.cancel"));
		this.btCancel.setSize(130, 20);
		this.btCancel.setLocation(this.getWidth() - btCancel.getWidth() - 10,
				this.getHeight() - 35);
		this.btCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		this.btConfirm = new Button(Constants.getText("button.confirm"));
		this.btConfirm.setSize(130, 20);
		this.btConfirm.setLocation(10, this.getHeight() - 35);
		this.btConfirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int row;
				row = tbArticles.getSelectedRow();
				acl.insertArticleLink(Integer.parseInt(tbArticles.getModel()
						.getValueAt(row, 0).toString()));
				setVisible(false);
			}
		});
		this.add(spArticles);
		this.add(btConfirm);
		this.add(btCancel);
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