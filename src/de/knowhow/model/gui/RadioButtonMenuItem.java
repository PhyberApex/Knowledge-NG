package de.knowhow.model.gui;

import java.awt.Font;

import javax.swing.JRadioButtonMenuItem;

public class RadioButtonMenuItem extends JRadioButtonMenuItem {

	public RadioButtonMenuItem(String text) {
		super(text);
		this.setFont(new Font("Arial", Font.PLAIN, 12));
	}
}
