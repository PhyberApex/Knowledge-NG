package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.MainController;

public class MainView extends JFrame {

	private MainController mc;
	private JButton bt_render;
	private JButton bt_plain;

	public MainView(MainController mc) {
		super(Constants.getAppName() + " v." + Constants.getAppVersion());
		this.mc = mc;
		this.setLayout(null);
		this.setVisible(false);
		init();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}

	private void init() {
		this.setSize(ViewConstants.MAIN_WIDTH, ViewConstants.MAIN_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2, (d.height - this
				.getSize().height) / 2);
		this.bt_plain = new JButton(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/plain.PNG")));
		this.bt_plain.setLocation(540, 0);
		this.bt_plain.setSize(ViewConstants.PLAIN_WIDTH,
				ViewConstants.PLAIN_HEIGTH);
		this.bt_plain.setLocation(ViewConstants.PLAIN_POS_X,
				ViewConstants.PLAIN_POS_Y);
		this.bt_plain.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.setPlainVisible(true);
				bt_render.setEnabled(true);
				mc.setTopicChooserVisible(true);
				mc.setRenderVisible(false);
				bt_plain.setEnabled(false);
			}
		});
		this.bt_render = new JButton(new ImageIcon(ClassLoader
				.getSystemResource("de/knowhow/resource/img/render.PNG")));
		this.bt_render.setLocation(565, 0);
		this.bt_render.setSize(ViewConstants.RENDER_WIDTH,
				ViewConstants.RENDER_HEIGTH);
		this.bt_render.setLocation(ViewConstants.RENDER_POS_X,
				ViewConstants.RENDER_POS_Y);
		this.bt_render.setEnabled(false);
		this.bt_render.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mc.setRenderVisible(true);
				bt_plain.setEnabled(true);
				mc.setTopicChooserVisible(false);
				mc.setPlainVisible(false);
				bt_render.setEnabled(false);
			}
		});
		this.add(bt_plain);
		this.add(bt_render);
	}

	public void exit() {
		mc.exit();
	}

	public void error(Exception e) {
		JOptionPane.showMessageDialog(this, Constants.getText("message.error."
				+ e.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
	}
}
