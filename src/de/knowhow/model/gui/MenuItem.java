package de.knowhow.model.gui;

import java.awt.Font;
import javax.swing.JMenuItem;

public class MenuItem extends JMenuItem {

	private static final long serialVersionUID = 1L;

	public MenuItem(String text) {
		super(text);
		this.setFont(new Font("Arial", Font.PLAIN, 12));
	}
}
