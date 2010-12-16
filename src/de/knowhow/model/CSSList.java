package de.knowhow.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.db.DAO;

public class CSSList extends Observable {
	private ArrayList<CSS> rules;
	private CSS currRules;
	private DAO db;

	public CSSList(DAO db) {
		this.db = db;
		rules = new ArrayList<CSS>();
	}

	public void load() throws DatabaseException {
		ResultSet rs;
		try {
			this.db.prepareStatement("SELECT * FROM css;");
			rs = this.db.execute();
			while (rs.next()) {
				String tag = rs.getString("tag");
				int css_ID = Integer.parseInt(rs.getString("css_ID"));
				String rule = rs.getString("rule");
				CSS currRule = new CSS(this.db, css_ID, tag, rule, false);
				this.rules.add(currRule);
			}
			rs.close();
		} catch (SQLException e) {
		}
	}

	public void reload() throws DatabaseException {
		rules.clear();
		this.load();
	}

	public CSS getCurrCss() {
		return this.currRules;
	}

	public void setCurrTag(String tag) throws DatabaseException {
		if (currRules != null) {
			currRules.setTag(tag);
			notifyObservers();
			setChanged();
		}
	}

	public void setCurrContent(String rule) throws DatabaseException {
		if (currRules != null) {
			currRules.setRule(rule);
			notifyObservers();
			setChanged();
		}
	}

	public void setCurrCSS(CSS newOne) {
		this.currRules = newOne;
	}

	public ArrayList<CSS> getRules() {
		return this.rules;
	}

	public CSS getCSSByID(int ID) {
		for (int i = 0; i < this.rules.size(); i++) {
			if (rules.get(i).getCss_ID() == ID) {
				return rules.get(i);
			}
		}
		return null;
	}

	public void newCSS(String tag, String rule) throws DatabaseException {
		CSS ru = new CSS(db, 0, tag, rule, false);
		this.rules.add(ru);
		setCurrCSS(ru);
		notifyObservers("new");
		setChanged();
	}

	public void deleteCurrCSS() throws DatabaseException {
		if (currRules != null) {
			currRules.delete();
			rules.remove(currRules);
			notifyObservers("delete");
			setChanged();
		}
	}

	public void setAll(HashMap<String, String> rules2) throws DatabaseException {
		for (int i = 0; i < this.rules.size(); i++) {
			this.rules.get(i).delete();
		}
		this.rules.clear();
		for (Map.Entry<String, String> e : rules2.entrySet()) {
			this.rules.add(new CSS(this.db, 0, e.getKey(), e.getValue(), true));
		}
		notifyObservers();
		setChanged();
	}
}