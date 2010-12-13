package de.knowhow.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.db.DAO;

public class CSS {

	private int css_ID;
	private String tag;
	private String rule;
	private DAO db;

	public CSS(DAO db, int css_ID, String tag, String rule, boolean insert)
			throws DatabaseException {
		this.db = db;
		this.css_ID = css_ID;
		this.tag = tag;
		this.rule = rule;
		if (insert) {
			try {
				this.db
						.prepareStatement("INSERT INTO css (tag, rule,) values(?, ?);");
				this.db.getStatement().setString(1, tag);
				this.db.getStatement().setString(2, rule);
				this.db.executeBatch();
			} catch (DatabaseException e) {
				throw e;
			} catch (SQLException e) {
			}
			this.css_ID = getNextID();
		}
	}

	public int getCss_ID() {
		return css_ID;
	}

	public void setCss_ID(int cssID) {
		css_ID = cssID;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	private int getNextID() throws DatabaseException {
		int nextID = 0;
		try {
			this.db
					.prepareStatement("SELECT css_ID AS next FROM css ORDER BY css_ID DESC LIMIT 1");
			ResultSet rs = this.db.execute();
			while (rs.next()) {
				nextID = Integer.parseInt(rs.getString("next"));
			}
			rs.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
		}
		return nextID;
	}

	public void delete() throws DatabaseException {
		try {
			this.db.prepareStatement("DELETE FROM css WHERE css_ID = ?");
			this.db.getStatement().setInt(1, this.getCss_ID());
			this.db.executeBatch();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
		}
	}
}