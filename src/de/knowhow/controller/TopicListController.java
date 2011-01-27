package de.knowhow.controller;

import java.util.ArrayList;
import java.util.Observer;

import javax.swing.SwingUtilities;

import de.knowhow.base.Config;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Topic;
import de.knowhow.model.TopicList;
import de.knowhow.model.db.DAO;
import de.knowhow.view.SubtopicView;
import de.knowhow.view.TopicChooseView;
import de.knowhow.view.TopicRenameView;

public class TopicListController extends Controller {

	private TopicList tl;
	private DAO db;
	private MainController mc;
	private TopicChooseView tcv;
	private TopicRenameView topicRename;
	private SubtopicView subView;

	public TopicListController(MainController mc) {
		this.db = Config.getInstance().getDBHandle();
		this.mc = mc;
		tl = new TopicList(this.db);
	}

	public void loadData() {
		try {
			tl.load();
			models.add(tl);
		} catch (DatabaseException e) {
			mc.error(e);
		}
	}

	public void loadGUI() {
		this.tcv = new TopicChooseView(this);
		this.tcv.setVisible(false);
		SwingUtilities.invokeLater(tcv);
		views.add(tcv);
		this.topicRename = new TopicRenameView(this);
		SwingUtilities.invokeLater(topicRename);
		views.add(topicRename);
		this.subView = new SubtopicView(this);
		SwingUtilities.invokeLater(subView);
		views.add(subView);
		addObservers();
		mc.getAcl().addObserver(tcv);
	}

	public TopicChooseView getTopicChooseView() {
		return this.tcv;
	}

	public TopicRenameView getTopicRenameView() {
		return this.topicRename;
	}

	public ArrayList<Topic> getTopics() {
		return this.tl.getTopics();
	}

	public Topic getTopicByID(int ID) {
		return this.tl.getTopicByID(ID);
	}

	public void cancel() {
		mc.cancel();
	}

	public void confirm(String action) throws DatabaseException {
		if (action.equals("rename")) {
			tl.setCurrName(topicRename.getTopicName());

		}
	}

	public void setCurrTopic(Topic newTop) {
		tl.setCurrTopic(newTop);
	}

	public void error(DatabaseException ex) {
		mc.error(ex);
	}

	public void addObserver(Observer obs) {
		this.tl.addObserver(obs);
	}

	public void newTopic() throws DatabaseException {
		tl.newTopic();
	}

	public void delete() throws DatabaseException {
		this.tl.deleteCurrTopic();
		mc.deleteArticleByTopic(tl.getCurrTopic().getTopic_ID());
	}

	public void changeTopicOfCurrArticle(int i) {
		mc.changeTopicOfCurrArticle(i);
	}

	public void setCurrTopic_ID_FK(int topicID) throws DatabaseException {
		tl.setCurrTopic_ID_FK(topicID);
	}

	public void subTopic() {
		subView.setVisible(true);
	}
}