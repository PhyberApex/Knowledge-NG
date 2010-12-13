package de.knowhow.model.gui;

import java.awt.Font;

import javax.swing.JTextArea;

public class TextArea extends JTextArea {

	public TextArea(String text) {
		super(text);
		setTabSize(2);
		this.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
}
