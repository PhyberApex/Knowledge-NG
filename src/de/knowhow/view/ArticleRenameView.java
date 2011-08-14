package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.ArticleListController;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.ArticleList;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.Label;
import de.knowhow.model.gui.Textfield;

public class ArticleRenameView extends ArticleView {

	private ArticleListController acl;
	private Dialog dialog;
	private Label lbname;
	private Textfield tfname;
	private Button confirm;
	private Button cancel;

	public ArticleRenameView(ArticleListController acl) {
		dialog = new Dialog();
		window = dialog;
		this.acl = acl;
	}

	protected void init() {
		dialog.setModal(true);
		dialog.setSize(ViewConstants.RENAME_WIDTH, ViewConstants.RENAME_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((d.width - dialog.getSize().width) / 2,
				(d.height - dialog.getSize().height) / 2);
		dialog.initPane();
		this.lbname = new Label(Constants.getText("keyword.article"));
		this.lbname.setSize(50, 25);
		this.lbname.setLocation(10, ((dialog.getHeight() - 30) / 2)
				- (lbname.getHeight() / 2));
		this.tfname = new Textfield(Constants.getText("renameArticle.tfname"));
		this.tfname.setSize(ViewConstants.RENAME_WIDTH - lbname.getWidth()
				- lbname.getX() - 10, 25);
		this.tfname.setLocation(lbname.getX() + lbname.getWidth() + 5, ((dialog
				.getHeight() - 30) / 2)
				- (lbname.getHeight() / 2));
		this.confirm = new Button(Constants.getText("button.confirm"));
		this.confirm.setSize(ViewConstants.RENAME_WIDTH / 2 - 20, 20);
		this.confirm.setLocation(10, ViewConstants.RENAME_HEIGTH - 30);
		this.confirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					acl.confirmRename();
				} catch (DatabaseException ex) {
					acl.error(ex);
				}
				setVisible(false);
			}
		});
		this.cancel = new Button(Constants.getText("button.cancel"));
		this.cancel.setSize(ViewConstants.RENAME_WIDTH / 2 - 20, 20);
		this.cancel.setLocation(ViewConstants.RENAME_WIDTH - 10
				- this.cancel.getWidth(), ViewConstants.RENAME_HEIGTH - 30);
		this.cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		dialog.getPane().add(lbname);
		dialog.getPane().add(tfname);
		dialog.getPane().add(confirm);
		dialog.getPane().add(cancel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (((ArticleList) arg0).getCurrArticle() != null) {
			this.tfname
					.setText(((ArticleList) arg0).getCurrArticle().getName());
		}
	}

	public String getArtName() {
		return tfname.getText();
	}

	@Override
	public boolean isComponent() {
		return false;
	}
}
