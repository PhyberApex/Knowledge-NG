package de.knowhow.view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.TreeController;
import de.knowhow.model.Article;
import de.knowhow.model.ArticleList;
import de.knowhow.model.Topic;
import de.knowhow.model.TopicList;
import de.knowhow.model.gui.Tree;

public class TreeView extends JPanel implements Observer, TreeSelectionListener {

	private Tree tree;
	private JScrollPane treeScrollPane;
	private TreeController treeC;

	public TreeView(TreeController treeC) {
		super();
		this.treeC = treeC;
		this.setLayout(null);
		init();
	}

	private void init() {
		treeScrollPane = new JScrollPane();
		tree = new Tree(treeC.getRootNode());
		tree.setLocation(0, 0);
		tree.addTreeSelectionListener(this);
		treeScrollPane.setSize(ViewConstants.TREE_WIDTH - 10,
				ViewConstants.TREE_HEIGTH - 40);
		treeScrollPane.setLocation(5, 0);
		treeScrollPane.setViewportView(tree);
		this.add(treeScrollPane);
		this.setSize(ViewConstants.TREE_WIDTH, ViewConstants.TREE_HEIGTH);
		this.setLocation(ViewConstants.TREE_POS_X, ViewConstants.TREE_POS_Y);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0.getClass() == ArticleList.class) {
			if (arg1 != null
					&& (arg1.equals("new") || arg1.equals("delete") || arg1
							.equals("update"))) {
				this.tree.removeTreeSelectionListener(this);
				this.tree = new Tree(treeC.getRootNode());
				tree.addTreeSelectionListener(this);
				treeScrollPane.setViewportView(tree);
			}
		} else if (arg0.getClass() == TopicList.class) {
			if (arg1 != null && (arg1.equals("new") || arg1.equals("delete"))) {
				this.tree.removeTreeSelectionListener(this);
				this.tree = new Tree(treeC.getRootNode());
				tree.addTreeSelectionListener(this);
				treeScrollPane.setViewportView(tree);
			}
		}
	}

	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		if (node == null)
			return;
		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			Article newArt = (Article) nodeInfo;
			treeC.setCurrArticle(newArt);
		} else if (!nodeInfo.equals("unsorted")) {
			Topic newTop = (Topic) nodeInfo;
			treeC.setCurrTopic(newTop);
		}
	}
}
