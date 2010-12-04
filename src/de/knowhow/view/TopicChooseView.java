package de.knowhow.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.TopicListController;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Article;
import de.knowhow.model.ArticleList;
import de.knowhow.model.Topic;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.ComboBox;

public class TopicChooseView extends JPanel implements Observer {

	private Button confirmButton;
	private ComboBox topicBox;
	private Button cancelButton;
	private TopicListController tcl;

	public TopicChooseView(TopicListController topicListController) {
		super();
		this.tcl = topicListController;
		this.setLayout(null);
		init();
	}

	private void init() {
		this.confirmButton = new Button(Constants.getText("button.confirm"));
		this.confirmButton.setSize(130, 20);
		this.confirmButton.setLocation(5, 0);
		this.confirmButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						try {
							tcl.confirm("ArticleContent");
						} catch (DatabaseException e1) {
							tcl.error(e1);
						}
					}
				});
		this.topicBox = new ComboBox();
		this.topicBox.setSize(100, 20);
		this.topicBox.setLocation(ViewConstants.ARTPLAIN_WIDTH / 2
				- this.topicBox.getWidth() / 2, 0);
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for (int i = 0; i < tcl.getTopics().size(); i++) {
			model.addElement(tcl.getTopics().get(i));
		}
		this.topicBox.setModel(model);
		this.topicBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				tcl.changeTopicOfCurrArticle(((Topic) topicBox
						.getSelectedItem()).getTopic_ID());
			}
		});
		this.cancelButton = new Button(Constants.getText("button.cancel"));
		this.cancelButton.setSize(130, 20);
		this.cancelButton.setLocation(ViewConstants.ARTPLAIN_WIDTH
				- this.cancelButton.getWidth() - 5, 0);
		this.cancelButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						tcl.cancel();
					}
				});
		this.add(confirmButton);
		this.add(topicBox);
		this.add(cancelButton);
		this.setSize(ViewConstants.TOPICCHOOSE_WIDTH,
				ViewConstants.TOPICCHOOSE_HEIGTH);
		this.setLocation(ViewConstants.TOPICCHOOSE_POS_X,
				ViewConstants.TOPICCHOOSE_POS_Y);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
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
}
