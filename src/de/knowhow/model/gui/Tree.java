package de.knowhow.model.gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Tree extends JTree{

	public Tree(DefaultMutableTreeNode rootNode){
		super(rootNode);
		this.setRootVisible(false);
	}
}
