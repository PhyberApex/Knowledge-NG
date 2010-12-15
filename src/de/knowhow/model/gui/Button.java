package de.knowhow.model.gui;

import java.awt.Font;
import javax.swing.JButton;

public class Button extends JButton {

	private static final long serialVersionUID = 1L;

	public Button(String content) {
		super(content);
		this.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
}
