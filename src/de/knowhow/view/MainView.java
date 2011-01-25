package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;

public class MainView extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private MainController mc;

	public MainView(MainController mc) {
		super(Constants.getAppName() + " v." + Constants.getAppVersion());
		this.setIconImage(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/logo.png"))
				.getImage());
		this.mc = mc;
	}

	private void init() {
		this.setSize(ViewConstants.MAIN_WIDTH, ViewConstants.MAIN_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
	}

	public void exit() {
		mc.exit();
	}

	public void error(Exception e) {
		JOptionPane.showMessageDialog(this,
				Constants.getText("message.error." + e.getMessage()), "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void run() {
		this.setLayout(null);
		this.setVisible(false);
		this.setResizable(false);
		init();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		this.setVisible(true);
	}
}
