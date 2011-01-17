package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;
import de.knowhow.controller.TopicListController;
import de.knowhow.model.Topic;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.ComboBox;
import de.knowhow.model.gui.Dialog;

public class SubtopicView extends Dialog implements Runnable {

	private static final long serialVersionUID = 1L;
	private ComboBox cbTopic;
	private Button btConfirm;
	private Button btCancel;
	private MainController mc;
	private TopicListController tcl;

	public SubtopicView(MainController mc, TopicListController tcl) {
		super();
		this.mc = mc;
		this.tcl = tcl;
	}

	private void init() {
		this.setSize(ViewConstants.SUBTOPIC_WIDTH,
				ViewConstants.SUBTOPIC_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this
				.getSize().height) / 2);
		this.initPane();
		this.cbTopic = new ComboBox();
		this.cbTopic.setSize(this.getWidth() - 40, 25);
		this.cbTopic.setLocation(20, 10);
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for (int i = 0; i < tcl.getTopics().size(); i++) {
			model.addElement(tcl.getTopics().get(i));
		}
		this.cbTopic.setModel(model);
		this.btConfirm = new Button(Constants.getText("button.confirm"));
		this.btConfirm.setSize(this.getWidth() / 2 - 40, 20);
		this.btConfirm.setLocation(20, this.cbTopic.getY()
				+ this.cbTopic.getHeight() + 10);
		this.btConfirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.setSubtopicByID(((Topic) cbTopic.getSelectedItem())
						.getTopic_ID());
				dispose();
			}
		});
		this.btCancel = new Button(Constants.getText("button.cancel"));
		this.btCancel.setSize(this.getWidth() / 2 - 20, 20);
		this.btCancel.setLocation(this.getWidth() - this.btCancel.getWidth()
				- 20, this.cbTopic.getY() + this.cbTopic.getHeight() + 10);
		this.btCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		this.getPane().add(this.cbTopic);
		this.getPane().add(this.btConfirm);
		this.getPane().add(this.btCancel);
	}

	@Override
	public void run() {
		init();
	}
}
