package de.knowhow.view;

import java.awt.event.MouseEvent;
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

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private Label topic;
	private ComboBox topicBox;
	private TopicListController tcl;

	public TopicChooseView(TopicListController topicListController) {
		panel = new JPanel();
		this.tcl = topicListController;
		panel.setLayout(null);
		window = panel;
	}

	protected void init() {
		panel.setSize(ViewConstants.TOPICCHOOSE_WIDTH,
				ViewConstants.TOPICCHOOSE_HEIGTH);
		panel.setLocation(ViewConstants.TOPICCHOOSE_POS_X,
				ViewConstants.TOPICCHOOSE_POS_Y);
		this.topic = new Label(Constants.getText("keyword.topic") + ":");
		this.topic.setSize(this.topic.getPreferredSize());
		this.topic.setLocation(5, (panel.getHeight() / 2)
				- (topic.getHeight() / 2));
		this.topicBox = new ComboBox();
		this.topicBox.setSize(
				panel.getWidth() - topic.getX() - topic.getWidth() - 20, 25);
		this.topicBox.setLocation(topic.getX() + topic.getWidth() + 5,
				(panel.getHeight() / 2) - (topicBox.getHeight() / 2));
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for (int i = 0; i < tcl.getTopics().size(); i++) {
			model.addElement(tcl.getTopics().get(i));
		}
		this.topicBox.setModel(model);
		this.topicBox.addMouseListener(new java.awt.event.MouseListener() {
			public void mouseClicked(MouseEvent e) {
				tcl.changeTopicOfCurrArticle(((Topic) topicBox
						.getSelectedItem()).getTopic_ID());
			}

			@Override
			public void mouseEntered(MouseEvent e) {//Nothing to do here
			}

			@Override
			public void mouseExited(MouseEvent e) {//Nothing to do here
			}

			@Override
			public void mousePressed(MouseEvent e) {//Nothing to do here
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {//Nothing to do here
			}
		});
		panel.add(topic);
		panel.add(topicBox);
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

	@Override
	public boolean isComponent() {
		return true;
	}
}
