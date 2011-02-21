package de.knowhow.model.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class Tree extends JTree {

	private static final long serialVersionUID = 1L;

	public Tree(DefaultMutableTreeNode rootNode, TreePath path) {
		super(rootNode);
		this.setRootVisible(false);
		this.setSelectionPath(path);
	}

	public Tree(DefaultMutableTreeNode rootNode) {
		super(rootNode);
		this.setRootVisible(false);
	}
}
