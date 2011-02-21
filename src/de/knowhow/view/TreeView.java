package de.knowhow.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.TreeController;
import de.knowhow.model.Article;
import de.knowhow.model.ArticleList;
import de.knowhow.model.Topic;
import de.knowhow.model.TopicList;
import de.knowhow.model.gui.Tree;

public class TreeView extends View implements TreeSelectionListener,
		MouseListener {

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
		tree.addMouseListener(this);
		treeScrollPane.setSize(ViewConstants.TREE_WIDTH - 10,
				ViewConstants.TREE_HEIGTH - 40);
		treeScrollPane.setPreferredSize(treeScrollPane.getSize());
		treeScrollPane.setViewportView(tree);
		panel.add(treeScrollPane, c);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0.getClass() == ArticleList.class) {
			treeC.setCurrArticleID(((ArticleList) arg0).getCurrArticle()
					.getArticle_ID());
		} else if (arg0.getClass() == TopicList.class) {
			treeC.setCurrTopicID(((TopicList) arg0).getCurrTopic()
					.getTopic_ID());
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3
				&& tree.getRowForLocation(e.getX(), e.getY()) != -1) {
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			tree.setSelectionPath(selPath);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath
					.getLastPathComponent();
			if (node.isLeaf()) {
				treeC.setCurrArticle((Article) node.getUserObject());
				treeC.showArticlePopupMenu(e.getX(), e.getY());
			} else {
				treeC.setCurrTopic((Topic) node.getUserObject());
				treeC.showTopicPopupMenu(e.getX(), e.getY());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Nothing to do here
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Nothing to do here
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Nothing to do here
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Nothing to do here
	}

	public void reload() {
		this.tree.removeTreeSelectionListener(this);
		this.tree.removeMouseListener(this);
		this.tree = new Tree(treeC.getRootNode());
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);
		treeScrollPane.setViewportView(tree);
	}
}
