package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import de.knowhow.base.Constants;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.Label;

public class AboutView extends Dialog implements Runnable {

	private static final long serialVersionUID = 1L;
	private JLabel lbLogo;
	private Label lbAppName;
	private Label lbAppVersion;
	private Label lbAppAuthor;
	private Label lbInfo;
	private Label lbLicense;
	private Button btClose;

	public AboutView() {
		super();
	}

	private void init() {
		this.setSize(400, 350);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
		this.initPane();
		this.lbLogo = new JLabel(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/logo.png")));
		this.lbLogo.setSize(this.lbLogo.getPreferredSize());
		this.lbLogo.setLocation(this.getWidth() - this.lbLogo.getWidth() - 10,
				20);
		this.getPane().add(lbLogo);
		this.lbAppName = new Label("Appliaction Name: "
				+ Constants.getAppName());
		this.lbAppName.setSize(this.lbAppName.getPreferredSize());
		this.lbAppName.setLocation(10, 20);
		this.getPane().add(lbAppName);
		this.lbAppVersion = new Label("Version: " + Constants.getAppVersion());
		this.lbAppVersion.setSize(this.lbAppVersion.getPreferredSize());
		this.lbAppVersion.setLocation(10,
				lbAppName.getY() + lbAppName.getHeight() + 15);
		this.getPane().add(lbAppVersion);
		this.lbAppAuthor = new Label("Author: " + Constants.getAppAuthor());
		this.lbAppAuthor.setSize(this.lbAppAuthor.getPreferredSize());
		this.lbAppAuthor.setLocation(10,
				lbAppVersion.getY() + lbAppName.getHeight() + 15);
		this.getPane().add(lbAppAuthor);
		this.lbInfo = new Label(
				"<html>Further information: <a href=\"ignore\">Project home</a></html>");
		this.lbInfo.setSize(this.lbInfo.getPreferredSize());
		this.lbInfo.setLocation(10,
				lbAppAuthor.getY() + lbAppAuthor.getHeight() + 15);
		this.lbInfo.addMouseListener(new java.awt.event.MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(
							new URI("http://code.google.com/p/knowledgeng/"));
					AboutView.this.dispose();
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
		this.getPane().add(lbInfo);
		this.lbLicense = new Label(
				"<html>License: <a href=\"ignore\">GNU GPL v2</a></html>");
		this.lbLicense.setSize(this.lbInfo.getPreferredSize());
		this.lbLicense.setLocation(10, lbInfo.getY() + lbAppAuthor.getHeight()
				+ 15);
		this.lbLicense.addMouseListener(new java.awt.event.MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					java.awt.Desktop
							.getDesktop()
							.browse(new URI(
									"http://www.gnu.org/licenses/old-licenses/gpl-2.0.html"));
					AboutView.this.dispose();
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
		this.getPane().add(lbLicense);
		this.btClose = new Button(Constants.getText("about.btClose"));
		this.btClose.setSize(this.btClose.getPreferredSize());
		this.btClose.setLocation(
				(this.getWidth() / 2) - (this.btClose.getWidth() / 2),
				this.getHeight() - this.btClose.getHeight() - 10);
		this.btClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		this.getPane().add(btClose);
	}

	@Override
	public void run() {
		init();
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
}
