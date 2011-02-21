package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.base.license.GPL2DE;
import de.knowhow.base.license.GPL2EN;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;

public class GPLView extends View {

	private static final long serialVersionUID = 1L;
	private Dialog dialog;
	private final JTabbedPane tabPane = new JTabbedPane();
	private Button btClose;

	public GPLView() {
		dialog = new Dialog();
		window = dialog;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// Nothing to do here
	}

	@Override
	protected void init() {
		dialog.setModal(true);
		dialog.setSize(ViewConstants.MAIN_WIDTH, ViewConstants.MAIN_WIDTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((d.width - dialog.getSize().width) / 2,
				(d.height - dialog.getSize().height) / 2);
		dialog.initPane();
		tabPane.setSize(dialog.getWidth() - 10, dialog.getHeight() - 50);
		tabPane.setLocation(5, 5);
		tabPane.setBorder(BorderFactory
				.createTitledBorder("GPL v2 Information"));
		JScrollPane spENGPL = new JScrollPane();
		spENGPL.setLocation(0, 0);
		spENGPL.setSize(tabPane.getX() - 5, tabPane.getY() - 5);
		StyleContext.NamedStyle centerStyle = StyleContext
				.getDefaultStyleContext().new NamedStyle();
		StyleConstants.setAlignment(centerStyle, StyleConstants.ALIGN_CENTER);
		JTextPane tpENGPL = new JTextPane();
		tpENGPL.setLogicalStyle(centerStyle);
		tpENGPL.setText(getEN());
		tpENGPL.setSize(tpENGPL.getPreferredSize());
		spENGPL.setViewportView(tpENGPL);
		tpENGPL.setCaretPosition(0);
		tabPane.addTab("English", spENGPL);
		JScrollPane spDEGPL = new JScrollPane();
		spDEGPL.setLocation(0, 0);
		spDEGPL.setSize(tabPane.getX() - 5, tabPane.getY() - 5);
		JTextPane tpDEGPL = new JTextPane();
		tpDEGPL.setLogicalStyle(centerStyle);
		tpDEGPL.setText(getDE());
		tpDEGPL.setSize(tpDEGPL.getPreferredSize());
		spDEGPL.setViewportView(tpDEGPL);
		tpDEGPL.setCaretPosition(0);
		tabPane.addTab("German", spDEGPL);
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
		dialog.getPane().add(tabPane);
		dialog.getPane().add(btClose);
	}

	private String getEN() {
		return new GPL2EN().getLicenseText();
	}

	private String getDE() {
		return new GPL2DE().getLicenseText();
	}

	@Override
	public boolean isComponent() {
		return false;
	}

}
