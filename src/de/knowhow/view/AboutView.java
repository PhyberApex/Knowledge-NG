package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.Label;

public class AboutView extends View {

	private Dialog dialog;
	private JLabel lbLogo;
	private MainController mc;
	private Label lbAppName;
	private Label lbAppVersion;
	private Label lbAppAuthor;
	private Label lbInfo;
	private Label lbLicense;
	private ArrayList<Label> lbCredits = new ArrayList<Label>();
	private Button btClose;

	public AboutView(MainController mc) {
		this.dialog = new Dialog();
		window = dialog;
		this.mc = mc;
	}

	protected void init() {
		dialog.setModal(true);
		dialog.setSize(ViewConstants.ABOUT_WIDTH, ViewConstants.ABOUT_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((d.width - dialog.getSize().width) / 2,
				(d.height - dialog.getSize().height) / 2);
		dialog.initPane();
		this.lbLogo = new JLabel(new ImageIcon(
				ClassLoader
						.getSystemResource("de/knowhow/resource/img/logo.png")));
		this.lbLogo.setSize(this.lbLogo.getPreferredSize());
		this.lbLogo.setLocation(
				dialog.getWidth() - this.lbLogo.getWidth() - 10, 20);
		dialog.getPane().add(lbLogo);
		this.lbAppName = new Label("Appliaction Name: "
				+ Constants.getAppName());
		this.lbAppName.setSize(this.lbAppName.getPreferredSize());
		this.lbAppName.setLocation(10, 20);
		dialog.getPane().add(lbAppName);
		this.lbAppVersion = new Label("Version: " + Constants.getAppVersion());
		this.lbAppVersion.setSize(this.lbAppVersion.getPreferredSize());
		this.lbAppVersion.setLocation(10,
				lbAppName.getY() + lbAppName.getHeight() + 15);
		dialog.getPane().add(lbAppVersion);
		this.lbAppAuthor = new Label("Author: " + Constants.getAppAuthor());
		this.lbAppAuthor.setSize(this.lbAppAuthor.getPreferredSize());
		this.lbAppAuthor.setLocation(10,
				lbAppVersion.getY() + lbAppName.getHeight() + 15);
		dialog.getPane().add(lbAppAuthor);
		this.lbInfo = new Label(
				"Further information: Project home(click to view)");
		this.lbInfo.setSize(this.lbInfo.getPreferredSize());
		this.lbInfo.setLocation(10,
				lbAppAuthor.getY() + lbAppAuthor.getHeight() + 15);
		this.lbInfo.addMouseListener(new java.awt.event.MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(
							new URI("http://code.google.com/p/knowledgeng/"));
					dialog.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		dialog.getPane().add(lbInfo);
		this.lbLicense = new Label("License: GNU GPL v2 (click to view)");
		this.lbLicense.setSize(this.lbInfo.getPreferredSize());
		this.lbLicense.setLocation(10, lbInfo.getY() + lbAppAuthor.getHeight()
				+ 15);
		this.lbLicense.addMouseListener(new java.awt.event.MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mc.showGPL();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		dialog.getPane().add(lbLicense);
		this.lbCredits.add(new Label("Special thanks to:"));
		this.lbCredits.add(new Label("Theresa Carolus(splashscreen and logo)"));
		this.lbCredits.add(new Label(
				"Andre Uhres @ JavaForum(splashscreen code)"));
		this.lbCredits.add(new Label(
				"Rob Kenworthy @ Javaworld (relativ paths for images)"));
		for (int i = 0; i < lbCredits.size(); i++) {
			lbCredits.get(i).setSize(lbCredits.get(i).getPreferredSize());
			if (i == 0) {
				lbCredits.get(i).setLocation(10,
						lbLicense.getY() + lbLicense.getHeight() + 15);
			} else {
				lbCredits.get(i).setLocation(
						10,
						lbCredits.get(i - 1).getHeight()
								+ lbCredits.get(i - 1).getY());
			}
			dialog.getPane().add(lbCredits.get(i));
		}
		this.btClose = new Button(Constants.getText("about.btClose"));
		this.btClose.setSize(this.btClose.getPreferredSize());
		this.btClose.setLocation(
				(dialog.getWidth() / 2) - (this.btClose.getWidth() / 2),
				dialog.getHeight() - this.btClose.getHeight() - 10);
		this.btClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		dialog.getPane().add(btClose);
	}

	@Override
	public boolean isComponent() {
		return false;
	}

	@Override
	public void update(Observable o, Object arg) {
		// Nothing to do here
	}
}
