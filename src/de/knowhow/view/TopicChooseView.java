package de.knowhow.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Observable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.TopicListController;
import de.knowhow.model.Article;
import de.knowhow.model.ArticleList;
import de.knowhow.model.Topic;
import de.knowhow.model.gui.ComboBox;
import de.knowhow.model.gui.Label;

public class TopicChooseView extends View {

	private JPanel panel;
	private Label topic;
	private ComboBox topicBox;
	private boolean hack = true;
	private TopicListController tcl;

	public TopicChooseView(TopicListController topicListController) {
		panel = new JPanel();
		this.tcl = topicListController;
		window = panel;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 1;
		constraints.gridy = 1;
	}

	protected void init() {
		panel.setLayout(new GridBagLayout());
		panel.setSize(ViewConstants.TOPICCHOOSE_WIDTH,
				ViewConstants.TOPICCHOOSE_HEIGTH);
		this.topic = new Label(Constants.getText("keyword.topic") + ":");
		this.topic.setSize(this.topic.getPreferredSize());
		this.topicBox = new ComboBox();
		this.topicBox.setSize(
				panel.getWidth() - topic.getX() - topic.getWidth() - 20, 25);
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for (int i = 0; i < tcl.getTopics().size(); i++) {
			model.addElement(tcl.getTopics().get(i));
		}
		this.topicBox.setModel(model);
		this.topicBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hack) {
					hack = false;
				} else {
					tcl.changeTopicOfCurrArticle(((Topic) topicBox
							.getSelectedItem()).getTopic_ID());
					hack = true;
				}
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(topic, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(topicBox, c);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		hack = true;
		if (arg0.getClass() == ArticleList.class) {
			Article art = ((ArticleList) arg0).getCurrArticle();
			for (int i = 0; i < this.topicBox.getItemCount(); i++) {
				Topic top;
				top = (Topic) this.topicBox.getItemAt(i);
				if (art.getTopic_ID_FK() == top.getTopic_ID()) {
					this.topicBox.setSelectedIndex(i);
					return;
				}
			}
		} else {
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			for (int i = 0; i < tcl.getTopics().size(); i++) {
				model.addElement(tcl.getTopics().get(i));
			}
			this.topicBox.setModel(model);
		}
	}

	@Override
	public boolean isComponent() {
		return true;
	}
}
