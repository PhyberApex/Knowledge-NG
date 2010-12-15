package de.knowhow.model.gui;

import java.awt.Font;
import javax.swing.JComboBox;

public class ComboBox extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ComboBox() {
		super();
		this.setFont(new Font("Verdana", Font.PLAIN, 12));
	}
}
