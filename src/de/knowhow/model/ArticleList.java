package de.knowhow.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import org.apache.log4j.Logger;

import de.knowhow.base.Constants;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.db.DAO;

public class ArticleList extends Observable {

	private ArrayList<Article> articles;
	private Article currArticle;
	private DAO db;
	private static Logger logger = Logger.getRootLogger();

	public ArticleList(DAO db) {
		this.db = db;
		articles = new ArrayList<Article>();
	}

	public void load() throws DatabaseException {
		ResultSet rs;
		try {
			this.db.prepareStatement("SELECT * FROM article;");
			rs = this.db.execute();
			while (rs.next()) {
				String lastEdit = rs.getString("lastEdit");
				int article_ID = Integer.parseInt(rs.getString("article_ID"));
				String content = rs.getString("content");
				String name = rs.getString("name");
				int topic_ID_FK = Integer.parseInt(rs.getString("topic_ID_FK"));
				Article currArticle = new Article(this.db, article_ID, name,
						lastEdit, content, topic_ID_FK);
				this.articles.add(currArticle);
			}
			rs.close();
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
	}

	public void reload() throws DatabaseException {
		articles.clear();
		this.load();
	}

	public Article getCurrArticle() {
		return this.currArticle;
	}

	public void setCurrName(String pName) throws DatabaseException {
		currArticle.setName(pName);
		notifyObservers();
		setChanged();
	}

	public void setCurrContent(String pContent) throws DatabaseException {
		currArticle.setContent(pContent);
		notifyObservers();
		setChanged();
	}

	public void setCurrTopic_ID_FK(int pID) throws DatabaseException {
		currArticle.setTopic_ID_FK(pID);
		notifyObservers("update");//dirty hack
		setChanged();
	}

	public void setCurrArticle(Article art) {
		this.currArticle = art;
		notifyObservers();
		setChanged();
	}
	public ArrayList<Article> getArticles(){
		return this.articles;
	}
	public Article getArticleByID(int ID){
		for(int i = 0; i < this.articles.size(); i++){
			if(articles.get(i).getArticle_ID() == ID){
				return articles.get(i);
			}
		}
		return null;
	}

	public void newArticle() throws DatabaseException {
		Article art = new Article(db);
		this.articles.add(art);
		setCurrArticle(art);
		notifyObservers("new");
		setChanged();
	}

	public void deleteCurrArt() throws DatabaseException {
		currArticle.delete();
		articles.remove(currArticle);
		notifyObservers("delete");
		setChanged();
	}

	public void deleteArtByTopic(int topicID) throws DatabaseException {
		ArrayList<Article> articles = getArticles();
		Article art;
		for (int i = 0 ; i <= articles.size(); i++){
			art = articles.get(i);
			if (art.getTopic_ID_FK() == topicID){
				art.delete();
				this.articles.remove(art);
			}
		}
		notifyObservers("delete");
		setChanged();
	}
}