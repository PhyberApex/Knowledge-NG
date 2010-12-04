package de.knowhow.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import org.apache.log4j.Logger;

import de.knowhow.base.Constants;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.db.DAO;

public class TopicList extends Observable {

	private ArrayList<Topic> topics;
	private Topic currTopic;
	private DAO db;
	private static Logger logger = Logger.getRootLogger();

	public TopicList(DAO db) {
		this.db = db;
		topics = new ArrayList<Topic>();
	}

	public void load() throws DatabaseException {
		ResultSet rs;
		try {
			this.db.prepareStatement("SELECT * FROM topic;");
			rs = this.db.execute();
			while (rs.next()) {
				int topic_ID = Integer.parseInt(rs.getString("topic_ID"));
				String name = rs.getString("name");
				int topic_ID_FK = Integer.parseInt(rs.getString("topic_ID_FK"));
				Topic currTopic = new Topic(this.db, topic_ID, name,
						topic_ID_FK);
				this.topics.add(currTopic);
			}
			rs.close();
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
	}

	public void reload() throws DatabaseException {
		topics.clear();
		this.load();
	}

	public Topic getCurrTopic() {
		return this.currTopic;
	}

	public void setCurrTopic(Topic newTop) {
		this.currTopic = newTop;
		notifyObservers();
		setChanged();
	}

	public void setCurrName(String pName) throws DatabaseException {
		currTopic.setName(pName);
		notifyObservers();
		setChanged();
	}

	public void setCurrTopic_ID_FK(int pID) throws DatabaseException {
		currTopic.setTopic_ID_FK(pID);
		notifyObservers();
		setChanged();
	}

	public ArrayList<Topic> getTopics() {
		return this.topics;
	}

	public Topic getTopicByID(int ID) {
		for (int i = 0; i < this.topics.size(); i++) {
			if (topics.get(i).getTopic_ID() == ID) {
				return topics.get(i);
			}
		}
		return null;
	}

	public void newArticle() throws DatabaseException {
		Topic topic = new Topic(db);
		this.topics.add(topic);
		setCurrTopic(topic);
		this.notifyObservers();
		this.setChanged();
	}

	public void deleteCurrTopic() throws DatabaseException {
		currTopic.delete();
		topics.remove(currTopic);
	}
}