package de.knowhow.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
	}

	public void loadData() {
		try {
			cssl.load();
		} catch (DatabaseException e) {
			mc.error(e);
		}
	}

	public void loadGUI() {
		this.plainView = new CSSPlainEditView(this);
		this.plainView.setVisible(false);
		this.cssl.addObserver(plainView);
	}

	public ArrayList<String> getStyleSheetInLines() {
		ArrayList<String> output = new ArrayList<String>();
		for (int i = 0; i < cssl.getRules().size(); i++) {
			String line = cssl.getRules().get(i).getTag();
			line += " {";
			String[] rules = cssl.getRules().get(i).getRule().split(";");
			for (int j = 0; j < rules.length; j++) {
				line += " " + rules[j];
				line += ";";
			}
			line += " }";
			output.add(line);
		}
		return output;
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

	public void confirm(String text) {
		saveRules(convertFromPlain(text));
	}

	private void saveRules(HashMap<String, String> rules) {
		if (rules == null) {
			return;
		}
		try {
			cssl.setAll(rules);
		} catch (DatabaseException e) {
			mc.error(e);
		}
	}

	private HashMap<String, String> convertFromPlain(String text) {
		HashMap<String, String> output = new HashMap<String, String>();
		boolean eof = false;
		text = text.replaceAll("\n", "");
		text = text.replaceAll("\t", "");
		while (!eof) {
			text = text.trim();
			if (text.equals("")) {
				eof = true;
			} else {
				try {
					String tag = text.substring(0, text.indexOf("{")).trim();
					text = text.substring(text.indexOf("{") + 1);
					String rules = text.substring(0, text.indexOf("}") - 1)
							.trim();
					text = text.substring(text.indexOf("}") + 1);
					output.put(tag, rules);
				} catch (Exception e) {
					mc.error(new DatabaseException("css"));

					eof = true;
					return null;
				}
			}
		}
		return output;
	}
}