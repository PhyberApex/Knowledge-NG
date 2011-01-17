package de.knowhow.model.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.EtchedBorder;

public class Dialog extends JWindow {

	private static final long serialVersionUID = 1L;
	private JPanel content;

	public Dialog() {
		super();
		this.content = new JPanel();
		this.content.setLayout(null);
		this.content.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.blue, Color.black));
		this.getContentPane().add(this.content);
	}

	public JPanel getPane() {
		return this.content;
	}

	public void initPane() {
		this.content.setSize(this.getSize());
	}
}
