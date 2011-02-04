package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import javax.swing.DefaultComboBoxModel;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.TopicListController;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Topic;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.ComboBox;
import de.knowhow.model.gui.Dialog;

public class SubtopicView extends View {

	private static final long serialVersionUID = 1L;
	private Dialog dialog;
	private ComboBox cbTopic;
	private Button btConfirm;
	private Button btCancel;
	private TopicListController tcl;

	public SubtopicView(TopicListController tcl) {
		dialog = new Dialog();
		window = dialog;
		this.tcl = tcl;
	}

	protected void init() {
		dialog.setModal(true);
		dialog.setSize(ViewConstants.SUBTOPIC_WIDTH,
				ViewConstants.SUBTOPIC_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((d.width - dialog.getSize().width) / 2,
				(d.height - dialog.getSize().height) / 2);
		dialog.initPane();
		this.cbTopic = new ComboBox();
		this.cbTopic.setSize(dialog.getWidth() - 40, 25);
		this.cbTopic.setLocation(20, 10);
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for (int i = 0; i < tcl.getTopics().size(); i++) {
			model.addElement(tcl.getTopics().get(i));
		}
		this.cbTopic.setModel(model);
		this.btConfirm = new Button(Constants.getText("button.confirm"));
		this.btConfirm.setSize(dialog.getWidth() / 2 - 40, 20);
		this.btConfirm.setLocation(20,
				this.cbTopic.getY() + this.cbTopic.getHeight() + 10);
		this.btConfirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					tcl.setCurrTopic_ID_FK(((Topic) cbTopic.getSelectedItem())
							.getTopic_ID());
				} catch (DatabaseException e1) {
					tcl.error(e1);
				}
				dialog.setVisible(false);
			}
		});
		this.btCancel = new Button(Constants.getText("button.cancel"));
		this.btCancel.setSize(dialog.getWidth() / 2 - 20, 20);
		this.btCancel.setLocation(dialog.getWidth() - this.btCancel.getWidth()
				- 20, this.cbTopic.getY() + this.cbTopic.getHeight() + 10);
		this.btCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		dialog.getPane().add(this.cbTopic);
		dialog.getPane().add(this.btConfirm);
		dialog.getPane().add(this.btCancel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for (int i = 0; i < tcl.getTopics().size(); i++) {
			model.addElement(tcl.getTopics().get(i));
		}
		this.cbTopic.setModel(model);
	}

	@Override
	public boolean isComponent() {
		return false;
	}
}
