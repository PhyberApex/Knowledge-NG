package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Label;

public class AboutView extends JFrame {

	private Label lbAbout;
	private Button btClose;

	public AboutView() {
		super();
		this.setUndecorated(true);
		this.setLayout(null);
		init();
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}

	private void init() {
		this.setSize(ViewConstants.MAIN_WIDTH, ViewConstants.MAIN_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this
				.getSize().height) / 2);
		this.lbAbout = new Label(Constants.getText("about"));
		this.lbAbout.setSize(ViewConstants.MAIN_WIDTH - 50,
				ViewConstants.MAIN_HEIGTH - 40);
		this.lbAbout.setLocation(25, 10);
		this.lbAbout.setVerticalAlignment(JButton.TOP);
		this.btClose = new Button(Constants.getText("about.btClose"));
		this.btClose.setSize(150, 20);
		this.btClose.setLocation((this.getWidth() / 2)
				- (this.btClose.getWidth() / 2), this.lbAbout.getY()
				+ this.lbAbout.getHeight());
		this.btClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		this.add(lbAbout);
		this.add(btClose);
	}
}
