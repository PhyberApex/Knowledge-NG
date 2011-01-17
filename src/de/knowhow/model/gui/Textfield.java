package de.knowhow.model.gui;

import java.awt.Font;
import javax.swing.JTextField;

public class Textfield extends JTextField {

	private static final long serialVersionUID = 1564L;

	public Textfield(String text) {
		super(text);
		this.setFont(new Font("Verdana", Font.PLAIN, 11));
	}
}
