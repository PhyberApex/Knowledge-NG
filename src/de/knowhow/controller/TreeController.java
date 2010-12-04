package de.knowhow.controller;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import de.knowhow.model.Article;
import de.knowhow.model.Topic;
import de.knowhow.model.gui.TreeNode;
import de.knowhow.view.TreeView;

public class TreeController {

	private ArticleListController acl;
	private TopicListController tcl;
	private ArrayList<Article> al;
	private ArrayList<Topic> tl;
	private TreeView treeV;

	public TreeController(ArticleListController acl, TopicListController tcl) {
		this.acl = acl;
		this.tcl = tcl;
		this.al = this.acl.getArticles();
		this.tl = this.tcl.getTopics();
		this.treeV = new TreeView(this);
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
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).getTopic_ID_FK() == 0) {
				addNode.add(new TreeNode(al.get(i)));
			}
		}
		if (!addNode.isLeaf()) {
			node.add(addNode);
		}
	}

	private void createNode(DefaultMutableTreeNode node, int ID) {
		for (int i = 0; i < tl.size(); i++) {
			TreeNode addNode = null;
			if (tl.get(i).getTopic_ID_FK() == ID) {
				addNode = new TreeNode(tl.get(i));
				createNode(addNode, tl.get(i).getTopic_ID());
				for (int j = 0; j < al.size(); j++) {
					if (al.get(j).getTopic_ID_FK() == tl.get(i).getTopic_ID()) {
						addNode.add(new TreeNode(al.get(j)));
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
}