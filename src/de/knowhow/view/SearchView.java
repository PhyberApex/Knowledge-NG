package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import javax.swing.JScrollPane;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;
import de.knowhow.extra.Search;
import de.knowhow.model.Article;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.Table;
import de.knowhow.model.gui.TableModel;

public class SearchView extends View {

	private static final long serialVersionUID = 1L;
	private Dialog dialog;
	private JScrollPane spSearch;
	private Table tbSearch;
	private Button btOpen;
	private Button btClose;
	private ArrayList<Article> result = new ArrayList<Article>();
	private MainController mc;

	public SearchView(MainController mc) {
		dialog = new Dialog();
		window = dialog;
		this.mc = mc;
		init();
	}

	protected void init() {
		dialog.setSize(ViewConstants.ARTPLAIN_WIDTH, ViewConstants.MAIN_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((d.width - dialog.getSize().width) / 2,
				(d.height - dialog.getSize().height) / 2);
		dialog.initPane();
		this.spSearch = new JScrollPane();
		this.spSearch.setSize(dialog.getWidth() - 20, dialog.getHeight() - 50);
		this.spSearch.setLocation(10, 10);
		this.tbSearch = new Table();
		String[] names = { "ID", "Name", "LastEdit" };
		Object[][] rowData = { { "No-ID", "No-Name", "No-Edit" } };
		TableModel model = new TableModel(rowData, names);
		this.tbSearch.setModel(model);
		this.spSearch.setViewportView(this.tbSearch);
		this.btOpen = new Button(Constants.getText("search.btOpen"));
		this.btOpen.setSize(dialog.getWidth() / 2 - 40, 20);
		this.btOpen.setLocation(20,
				this.spSearch.getY() + this.spSearch.getHeight() + 10);
		this.btOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.setCurrArtByID((Integer) (tbSearch.getValueAt(
						tbSearch.getSelectedRow(), 0)));
				dialog.setVisible(false);
			}
		});
		this.btClose = new Button(Constants.getText("search.btClose"));
		this.btClose.setSize(dialog.getWidth() / 2 - 20, 20);
		this.btClose.setLocation(dialog.getWidth() - this.btClose.getWidth()
				- 20, this.spSearch.getY() + this.spSearch.getHeight() + 10);
		this.btClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		dialog.getPane().add(spSearch);
		dialog.getPane().add(btOpen);
		dialog.getPane().add(btClose);
	}

	@Override
	public void update(Observable o, Object arg) {
		// nothing to do here
	}

	@Override
	public boolean isComponent() {
		return false;
	}

	public void searchFor(String text, Iterator<Article> iterator) {
		setVisible(true);
		Iterator<Article> resultIterator = Search.getArticles(text, iterator);
		result.clear();
		while (resultIterator.hasNext()) {
			Article currArt = resultIterator.next();
			result.add(currArt);
		}
		reload();
	}

	private void reload() {
		this.tbSearch = new Table();
		String[] names = { "ID", "Name", "LastEdit" };
		Object[][] rowData = new Object[result.size()][3];
		for (int i = 0; i < result.size(); i++) {
			rowData[i][0] = result.get(i).getArticle_ID();
			rowData[i][1] = result.get(i).getName();
			rowData[i][2] = result.get(i).getLastEdit();
		}
		TableModel model = new TableModel(rowData, names);
		this.tbSearch.setModel(model);
		this.spSearch.setViewportView(tbSearch);
	}
}