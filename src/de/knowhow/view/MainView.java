package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import de.knowhow.base.Constants;
import de.knowhow.controller.MainController;

public class MainView extends View {

	private JFrame frame;
	private MainController mc;

	public MainView(MainController mc) {
		frame = new JFrame(Constants.getAppName() + " v."
				+ Constants.getAppVersion());
		frame.setIconImage(Constants.createImageIcon(
				"/de/knowhow/resource/img/logo.png").getImage());
		window = frame;
		frame.setResizable(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		this.mc = mc;
	}

	protected void init() {
		frame.setMinimumSize(new Dimension(600, 480));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - frame.getSize().width) / 2,
				(d.height - frame.getSize().height) / 2);
	}

	public void exit() {
		mc.exit();
	}

	public void error(Exception e) {
		JOptionPane.showMessageDialog(frame,
				Constants.getText("message.error." + e.getMessage()), "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// Nothing to do here
	}

	@Override
	public boolean isComponent() {
		return false;
	}

	@Override
	public JFrame getComponent() {
		return this.frame;
	}
}
