package de.knowhow.model.gui;

import java.awt.Font;

import javax.swing.JMenu;

public class Menu extends JMenu {

	public Menu(String text) {
		super(text);
		this.setFont(new Font("Arial", Font.PLAIN, 12));
	}
}
