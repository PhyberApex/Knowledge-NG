package de.knowhow.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Splash extends JWindow {

	private static final long serialVersionUID = 1L;
	private JProgressBar progress;
	private Image imageSplash, imageBackground;

	private class UpdateStatus implements Runnable {
		public UpdateStatus(String status, int pc) {
			message = status;
			value = pc;
		}

		public void run() {
			progress.setValue(value);
			progress.setString(message);
			if (value > 100) {
				progress.setIndeterminate(true);
			} else {
				progress.setIndeterminate(false);
			}
		}

		private String message;
		private int value;
	}

	private class CloseSplashScreen implements Runnable {
		public void run() {
			setVisible(false);
			dispose();
		}
	}

	/**
	 * SplashScreen erzeugen
	 */
	public Splash(Image coolPicture, String initialMessage) {
		if (coolPicture != null) {
			setSize(coolPicture.getWidth(this),
					coolPicture.getHeight(null) + 25);
		} else {
			setSize(200, 200);
		}
		setLocationRelativeTo(null);
		MyPanel myPanel = new MyPanel(getSize());

		Color fgX = new Color(85, 43, 0);
		progress = new JProgressBar(0, 100);
		progress.setForeground(fgX);
		Dimension preferredSize = progress.getPreferredSize();
		double height = preferredSize.getHeight();
		double width = preferredSize.getWidth();
		height = height +5;/// 2.0;
		preferredSize.setSize(width, height);
		progress.setString(initialMessage);
		myPanel.add(progress, BorderLayout.SOUTH);
		progress.setStringPainted(true); // progressbar mit Beschriftung
		progress.setPreferredSize(preferredSize); //schmalere progressbar
		progress.setBorderPainted(false) ; //progressbar ohne Rand
		myPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		// myPanel.setBorder(new LineBorder(fgX, 2));
		progress.setBackground(new Color(254, 243, 224));
		getContentPane().add(myPanel);
		imageSplash = coolPicture;
		try {
			imageBackground = new Robot().createScreenCapture(getBounds());
		} catch (AWTException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Den splash screen schliessen und alle resources freigeben die damit
	 * zusammenh�ngen. Diese Methode ist thread safe und kann von jedem Thread
	 * aufgerufen werden.
	 */
	public void close() {
		if (isVisible()) {
			SwingUtilities.invokeLater(new CloseSplashScreen());
		}
	}

	/**
	 * Den splash screen sichtbar/unsichtbar machen.
	 */
	public void setVisible(boolean show) {
		if (show) {
			pack();
			setLocationRelativeTo(null);
		}
		super.setVisible(show);
	}

	/**
	 * Status der progressbar �ndern. Diese Methode ist thread safe und kann von
	 * jedem Thread aufgerufen werden.
	 */
	public void showStatus(String message, int percent) {
		if (isVisible()) {
			SwingUtilities.invokeLater(new UpdateStatus(message, percent));
		}
	}

	class MyPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public MyPanel(Dimension size) {
			setLayout(new BorderLayout());
			setPreferredSize(size);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (imageBackground != null) {
				g.drawImage(imageBackground, 0, 0, this);
			}

			g.drawImage(imageSplash, 0, 0, this);
		}
	}
}