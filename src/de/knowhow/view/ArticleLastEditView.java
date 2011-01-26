package de.knowhow.view;

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
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ArticleList) {
			this.lastEdit.setText("<html>"
					+ Constants.getText("articleLastEditView.lastEdit")
					+ "<br />"
					+ ((ArticleList) o).getCurrArticle().getLastEdit());
		}
	}

	@Override
	protected void init() {
		panel.setLayout(null);
		panel.setSize(ViewConstants.LASTEDIT_WIDTH,
				ViewConstants.LASTEDIT_HEIGTH);
		panel.setLocation(ViewConstants.LASTEDIT_POS_X,
				ViewConstants.LASTEDIT_POS_Y);
		this.lastEdit = new Label("<html>"
				+ Constants.getText("articleLastEditView.lastEdit") + "<br />");
		this.lastEdit.setSize(panel.getWidth(), 35);
		this.lastEdit.setLocation(5, 5);
		panel.add(lastEdit);
	}

	@Override
	public boolean isComponent() {
		return true;
	}
}
