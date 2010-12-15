package de.knowhow.model.gui;

import java.awt.Font;
import javax.swing.JLabel;

public class Label extends JLabel {
	private static final long serialVersionUID = 1L;

	public Label(String content) {
		super(content);
		this.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
}
