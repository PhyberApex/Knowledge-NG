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
import de.knowhow.controller.AttachmentListController;
import de.knowhow.model.Attachment;
import de.knowhow.model.AttachmentList;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Table;
import de.knowhow.model.gui.TableModel;

public class AttachmentForArticleView extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private AttachmentListController attL;
	private Table tbAttachmentsFile;
	private Table tbAttachmentsImage;
	private JScrollPane spAttachments;
	private Button btConfirm;
	private Button btCancel;
	private boolean image;

	public AttachmentForArticleView(AttachmentListController attL) {
		super();
		this.attL = attL;
		this.setLayout(null);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		init();
	}

	private void init() {
		this.setSize(ViewConstants.ATTACH_WIDTH, ViewConstants.ATTACH_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this
				.getSize().height) / 2);
		this.spAttachments = new JScrollPane();
		this.spAttachments.setSize(ViewConstants.ATTACH_WIDTH,
				ViewConstants.ATTACH_HEIGTH - 40);
		this.spAttachments.setLocation(0, 0);
		this.tbAttachmentsFile = new Table();
		this.tbAttachmentsImage = new Table();
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
		this.add(spAttachments);
		this.add(btConfirm);
		this.add(btCancel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
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
}