package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JScrollPane;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.AttachmentListController;
import de.knowhow.model.ArticleList;
import de.knowhow.model.Attachment;
import de.knowhow.model.AttachmentList;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.Table;
import de.knowhow.model.gui.TableModel;

public class AttachmentForArticleView extends View {

	private Dialog dialog;
	private AttachmentListController attL;
	private Table tbAttachmentsFile;
	private Table tbAttachmentsImage;
	private JScrollPane spAttachments;
	private Button btConfirm;
	private Button btCancel;
	private boolean image;

	public AttachmentForArticleView(AttachmentListController attL) {
		dialog = new Dialog();
		window = dialog;
		this.attL = attL;
	}

	protected void init() {
		dialog.setModal(true);
		dialog.setSize(ViewConstants.ATTACH_WIDTH, ViewConstants.ATTACH_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((d.width - dialog.getSize().width) / 2,
				(d.height - dialog.getSize().height) / 2);
		dialog.initPane();
		this.spAttachments = new JScrollPane();
		this.spAttachments.setSize(ViewConstants.ATTACH_WIDTH,
				ViewConstants.ATTACH_HEIGTH - 40);
		this.spAttachments.setLocation(0, 0);
		this.tbAttachmentsFile = new Table();
		this.tbAttachmentsImage = new Table();
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
				Table currTable;
				int row;
				if (isImage()) {
					currTable = tbAttachmentsImage;
					row = currTable.getSelectedRow();
					attL.insertImageLink(Integer.parseInt(currTable.getModel()
							.getValueAt(row, 0).toString()));
				} else {
					currTable = tbAttachmentsFile;
					row = currTable.getSelectedRow();
					attL.insertFileLink(Integer.parseInt(currTable.getModel()
							.getValueAt(row, 0).toString()));
				}
				setVisible(false);
			}
		});
		dialog.getPane().add(spAttachments);
		dialog.getPane().add(btConfirm);
		dialog.getPane().add(btCancel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof ArticleList) {
			attL.setCurrID(((ArticleList)arg0).getCurrArticle().getArticle_ID());
		} else {
			AttachmentList attL = (AttachmentList) arg0;
			ArrayList<Attachment> atts = attL.getCurrAttachments();
			ArrayList<Attachment> attsImage = new ArrayList<Attachment>();
			ArrayList<Attachment> attsFile = new ArrayList<Attachment>();
			for (int i = 0; i < atts.size(); i++) {
				if (atts.get(i).isImage()) {
					attsImage.add(atts.get(i));
				} else {
					attsFile.add(atts.get(i));
				}
			}
			String[] names = { "ID", "Name" };
			Object[][] rowData = new Object[attsFile.size()][2];
			for (int i = 0; i < attsFile.size(); i++) {
				rowData[i][0] = attsFile.get(i).getAttachment_ID();
				rowData[i][1] = attsFile.get(i).getName();
			}
			TableModel model = new TableModel(rowData, names);
			this.tbAttachmentsFile.setModel(model);
			rowData = new Object[attsImage.size()][2];
			for (int i = 0; i < attsImage.size(); i++) {
				rowData[i][0] = attsImage.get(i).getAttachment_ID();
				rowData[i][1] = attsImage.get(i).getName();
			}
			model = new TableModel(rowData, names);
			this.tbAttachmentsImage.setModel(model);
		}
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
		if (image) {
			this.spAttachments.setViewportView(tbAttachmentsImage);
		} else {
			this.spAttachments.setViewportView(tbAttachmentsFile);
		}
	}

	@Override
	public boolean isComponent() {
		return false;
	}
}