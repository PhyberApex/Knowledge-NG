package de.knowhow.model.gui;

import java.awt.Font;

import javax.swing.JButton;

public class Button extends JButton{

	public Button(String content) {
		super(content);
		this.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
}
