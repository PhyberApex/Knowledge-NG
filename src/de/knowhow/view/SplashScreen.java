package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;

public class SplashScreen extends JFrame {

	private JProgressBar bar;
	private JLabel label;
	private ArrayList<String> states;
	private int currState = 0;

	public SplashScreen() {
		super();
		this.setLayout(null);
		init();
	}

	private void init() {
		this.setVisible(false);
		this.setSize(ViewConstants.SPLASH_WIDTH, ViewConstants.SPLASH_HEIGTH);
		JLabel splash = new JLabel(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/splash.png")));
		splash.setLocation(0, 0);
		splash.setSize(ViewConstants.SPLASH_WIDTH, ViewConstants.SPLASH_HEIGTH);
		this.add(splash);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this
				.getSize().height) / 2);
		this.setUndecorated(true);
		this.bar = new JProgressBar();
		this.bar.setSize(250, 25);
		this.bar.setLocation((ViewConstants.SPLASH_WIDTH / 2)
				- (this.bar.getWidth() / 2)+1, 60);
		this.bar.setValue(0);
		this.label = new JLabel(Constants.getText("splash.init"));
		this.label.setSize(250, 25);
		this.label.setLocation(10, 0);
		this.label.setHorizontalAlignment(JLabel.CENTER);
		this.add(this.bar);
		this.bar.add(this.label);
		this.states = new ArrayList<String>();
		this.states.add("splash.loadAttachment");
		this.states.add("splash.loadArt");
		this.states.add("splash.loadTopic");
		this.states.add("splash.caching");
		this.states.add("splash.paint");
		this.setVisible(true);
	}

	public void next() {
		currState++;
		this.bar.setValue(100 / this.states.size() * currState);
		if (currState > this.states.size()) {
			label.setText(Constants.getText("splash.finish"));
		} else {
			label.setText(Constants.getText(this.states.get(currState - 1)));
		}
	}
}