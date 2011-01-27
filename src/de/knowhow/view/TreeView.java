package de.knowhow.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
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

public class TreeView extends View implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private Tree tree;
	private JScrollPane treeScrollPane;
	private TreeController treeC;

	public TreeView(TreeController treeC) {
		panel = new JPanel();
		this.treeC = treeC;
		window = panel;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.3;
		constraints.weighty = 0;
		constraints.gridheight = 3;
		constraints.gridx = 0;
		constraints.gridy = 1;
	}

	protected void init() {
		panel.setLayout(new GridBagLayout());
		panel.setSize(ViewConstants.TREE_WIDTH, ViewConstants.TREE_HEIGTH);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weighty = 1;
		c.weightx = 1;
		treeScrollPane = new JScrollPane();
		tree = new Tree(treeC.getRootNode());
		tree.addTreeSelectionListener(this);
		treeScrollPane.setSize(ViewConstants.TREE_WIDTH - 10,
				ViewConstants.TREE_HEIGTH - 40);
		treeScrollPane.setPreferredSize(treeScrollPane.getSize());
		treeScrollPane.setViewportView(tree);
		panel.add(treeScrollPane, c);
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
			if (arg1 != null
					&& (arg1.equals("new") || arg1.equals("delete") || arg1
							.equals("sub"))) {
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

	@Override
	public boolean isComponent() {
		return true;
	}
}
