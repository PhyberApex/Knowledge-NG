package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;
import de.knowhow.model.Article;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.Table;
import de.knowhow.model.gui.TableModel;

public class SearchView extends Dialog implements Runnable {

	private static final long serialVersionUID = 1L;
	private JScrollPane spSearch;
	private Table tbSearch;
	private Button btOpen;
	private Button btClose;
	private ArrayList<Article> result;
	private MainController mc;

	public SearchView(ArrayList<Article> result, MainController mc) {
		super();
		this.result = result;
		this.mc = mc;
		init();
	}

	private void init() {
		this.setSize(ViewConstants.ARTPLAIN_WIDTH, ViewConstants.MAIN_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this
				.getSize().height) / 2);
		this.initPane();
		this.spSearch = new JScrollPane();
		this.spSearch.setSize(this.getWidth() - 20, this.getHeight() - 50);
		this.spSearch.setLocation(10, 10);
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
		this.spSearch.setViewportView(this.tbSearch);
		this.btOpen = new Button(Constants.getText("search.btOpen"));
		this.btOpen.setSize(this.getWidth() / 2 - 40, 20);
		this.btOpen.setLocation(20, this.spSearch.getY()
				+ this.spSearch.getHeight() + 10);
		this.btOpen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.setCurrArtByID((Integer) (tbSearch.getValueAt(tbSearch
						.getSelectedRow(), 0)));
				dispose();
			}
		});
		this.btClose = new Button(Constants.getText("search.btClose"));
		this.btClose.setSize(this.getWidth() / 2 - 20, 20);
		this.btClose.setLocation(
				this.getWidth() - this.btClose.getWidth() - 20, this.spSearch
						.getY()
						+ this.spSearch.getHeight() + 10);
		this.btClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		this.getPane().add(spSearch);
		this.getPane().add(btOpen);
		this.getPane().add(btClose);
	}

	@Override
	public void run() {
		init();
	}
}