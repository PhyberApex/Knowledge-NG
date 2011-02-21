package de.knowhow.controller;

import java.util.Iterator;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import de.knowhow.model.Article;
import de.knowhow.model.Model;
import de.knowhow.model.Topic;
import de.knowhow.model.gui.TreeNode;
import de.knowhow.view.TreeView;

public class TreeController extends Controller {

	private ArticleListController acl;
	private TopicListController tcl;
	private TreeView treeV;
	private int currTopicID = -1;
	private int currArticleID = -1;

	public TreeController(ArticleListController acl, TopicListController tcl) {
		this.acl = acl;
		this.tcl = tcl;
	}

	public void loadData() {
		Iterator<Model> iterator = acl.getModels();
		while (iterator.hasNext()) {
			models.add(iterator.next());
		}
		iterator = tcl.getModels();
		while (iterator.hasNext()) {
			models.add(iterator.next());
		}
	}

	public void loadGUI() {
		this.treeV = new TreeView(this);
		SwingUtilities.invokeLater(treeV);
		views.add(treeV);
		this.tcl.addObserver(this.treeV);
		this.acl.addObserver(this.treeV);
	}

	public TreeView getTreeView() {
		return this.treeV;
	}

	public DefaultMutableTreeNode getRootNode() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode();
		createNode(node, 0);
		addUnsortedArticles(node);
		return node;
	}

	private void addUnsortedArticles(DefaultMutableTreeNode node) {
		TreeNode addNode = new TreeNode("unsorted");
		Iterator<Article> iterator = acl.getArticles().iterator();
		while (iterator.hasNext()) {
			Article currArticle = iterator.next();
			if (currArticle.getTopic_ID_FK() == 0) {
				addNode.add(new TreeNode(currArticle));
			}
		}
		if (!addNode.isLeaf()) {
			node.add(addNode);
		}
	}

	private void createNode(DefaultMutableTreeNode node, int ID) {
		Iterator<Topic> topicIterator = tcl.getTopics().iterator();
		while (topicIterator.hasNext()) {
			Topic currTopic = topicIterator.next();
			TreeNode addNode = null;
			if (currTopic.getTopic_ID_FK() == ID
					&& currTopic.getTopic_ID_FK() != currTopic.getTopic_ID()) {
				addNode = new TreeNode(currTopic);
				createNode(addNode, currTopic.getTopic_ID());
				Iterator<Article> articleIterator = acl.getArticles()
						.iterator();
				while (articleIterator.hasNext()) {
					Article currArticle = articleIterator.next();
					if (currArticle.getTopic_ID_FK() == currTopic.getTopic_ID()) {
						addNode.add(new TreeNode(currArticle));
					}
				}
			} else {
				continue;
			}
			if (!addNode.isLeaf()) {
				node.add(addNode);
			}
		}
	}

	public void setCurrArticle(Article newArt) {
		acl.setCurrArticle(newArt);
	}

	public void setCurrTopic(Topic newTop) {
		tcl.setCurrTopic(newTop);
	}

	public int getCurrTopicID() {
		return currTopicID;
	}

	public void setCurrTopicID(int currTopicID) {
		this.currTopicID = currTopicID;
	}

	public int getCurrArticleID() {
		return currArticleID;
	}

	public void setCurrArticleID(int currArticleID) {
		this.currArticleID = currArticleID;
	}

	public void showTopicPopupMenu(int x, int y) {
		tcl.showPopupMenu(treeV.getComponent(), x, y);
	}

	public void showArticlePopupMenu(int x, int y) {
		acl.showPopupMenu(treeV.getComponent(), x, y);
	}
}