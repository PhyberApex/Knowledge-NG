package de.knowhow.model.gui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class Dialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel content;

	public Dialog() {
		super();
		this.setUndecorated(true);
		this.content = new JPanel();
		this.content.setLayout(null);
		this.content.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.WHITE));
		this.getContentPane().add(this.content);
	}

	public JPanel getPane() {
		return this.content;
	}

	public void initPane() {
		this.content.setSize(this.getSize());
	}
}
