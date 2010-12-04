package de.knowhow.model.gui;

import java.awt.Font;

import javax.swing.JTextField;

public class Textfield extends JTextField {

	public Textfield(String text) {
		super(text);
		this.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
}
