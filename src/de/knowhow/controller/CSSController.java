package de.knowhow.controller;

import java.util.ArrayList;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.CSSList;
import de.knowhow.model.db.DAO;
import de.knowhow.view.CSSPlainEditView;

public class CSSController {

	private MainController mc;
	private CSSList cssl;
	private CSSPlainEditView plainView;
	private DAO db;

	public CSSController(DAO db, MainController mainController) {
		this.mc = mainController;
		this.db = db;
		this.cssl = new CSSList(this.db);
		try {
			cssl.load();
		} catch (DatabaseException e) {
			mc.error(e);
		}
		this.plainView = new CSSPlainEditView(this);
		this.plainView.setVisible(false);
		this.cssl.addObserver(plainView);
	}

	public ArrayList<String> getStyleSheetInLines() {
		return null;
	}

	public CSSPlainEditView getPlainView() {
		return plainView;
	}

	public String getStyleSheet() {
		String output = "";
		for (int i = 0; i < cssl.getRules().size(); i++) {
			output += cssl.getRules().get(i).getTag();
			output += " {\n";
			String[] rules = cssl.getRules().get(i).getRule().split(";");
			for (int j = 0; j < rules.length; j++) {
				output += " \t";
				output += rules[j];
				output += ";\n";
			}
			output += "}\n\n";
		}
		return output;
	}
}