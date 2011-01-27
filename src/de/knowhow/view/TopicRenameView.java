package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.TopicListController;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.TopicList;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.Label;
import de.knowhow.model.gui.Textfield;

public class TopicRenameView extends View {

	private static final long serialVersionUID = 1L;
	private Dialog dialog;
	private TopicListController tcl;
	private Label lbname;
	private Textfield tfname;
	private Button confirm;
	private Button cancel;

	public TopicRenameView(TopicListController tcl) {
		dialog = new Dialog();
		this.tcl = tcl;
		window = dialog;
	}

	protected void init() {
		dialog.setLayout(null);
		dialog.setSize(ViewConstants.RENAME_WIDTH, ViewConstants.RENAME_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((d.width - dialog.getSize().width) / 2,
				(d.height - dialog.getSize().height) / 2);
		dialog.initPane();
		this.lbname = new Label(Constants.getText("keyword.topic"));
		this.lbname.setSize(50, 25);
		this.lbname.setLocation(10,
				((dialog.getHeight() - 30) / 2) - (lbname.getHeight() / 2));
		this.tfname = new Textfield(Constants.getText("renameTopic.tfname"));
		this.tfname.setSize(ViewConstants.RENAME_WIDTH - lbname.getWidth()
				- lbname.getX() - 10, 25);
		this.tfname.setLocation(lbname.getX() + lbname.getWidth() + 5,
				((dialog.getHeight() - 30) / 2) - (lbname.getHeight() / 2));
		this.confirm = new Button(Constants.getText("button.confirm"));
		this.confirm.setSize(ViewConstants.RENAME_WIDTH / 2 - 20, 20);
		this.confirm.setLocation(10, ViewConstants.RENAME_HEIGTH - 30);
		this.confirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					tcl.confirm("rename");
				} catch (DatabaseException ex) {
					tcl.error(ex);
				}
				setVisible(false);
			}
		});
		this.cancel = new Button(Constants.getText("button.cancel"));
		this.cancel.setSize(ViewConstants.RENAME_WIDTH / 2 - 20, 20);
		this.cancel.setLocation(
				ViewConstants.RENAME_WIDTH - 10 - this.cancel.getWidth(),
				ViewConstants.RENAME_HEIGTH - 30);
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
		this.tfname.setText(((TopicList) arg0).getCurrTopic().getName());
	}

	public String getTopicName() {
		return tfname.getText();
	}

	@Override
	public boolean isComponent() {
		return false;
	}
}
