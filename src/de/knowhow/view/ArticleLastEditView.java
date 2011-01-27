package de.knowhow.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import javax.swing.JPanel;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.model.ArticleList;
import de.knowhow.model.gui.Label;

public class ArticleLastEditView extends ArticleView {

	private JPanel panel;
	private Label lastEdit;

	public ArticleLastEditView() {
		panel = new JPanel();
		window = panel;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 1;
		constraints.gridy = 2;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ArticleList) {
			this.lastEdit.setText(Constants
					.getText("articleLastEditView.lastEdit")
					+ " "
					+ ((ArticleList) o).getCurrArticle().getLastEdit());
		}
	}

	@Override
	protected void init() {
		panel.setLayout(new GridBagLayout());
		panel.setSize(ViewConstants.LASTEDIT_WIDTH,
				ViewConstants.LASTEDIT_HEIGTH);
		this.lastEdit = new Label(
				Constants.getText("articleLastEditView.lastEdit")
						+ " DD.MM.YYYY");
		this.lastEdit.setSize(panel.getWidth(), 25);
		this.lastEdit.setPreferredSize(lastEdit.getSize());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(lastEdit, c);
	}

	@Override
	public boolean isComponent() {
		return true;
	}
}
