package de.knowhow.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JScrollPane;
import de.knowhow.base.Constants;
import de.knowhow.base.ViewConstants;
import de.knowhow.controller.CSSController;
import de.knowhow.model.gui.Button;
import de.knowhow.model.gui.Dialog;
import de.knowhow.model.gui.TextArea;

public class CSSPlainEditView extends Dialog implements Observer, Runnable {

	private static final long serialVersionUID = 1L;
	private CSSController csc;
	private Button btCancel;
	private Button btConfirm;
	private JScrollPane spCSS;
	private TextArea taCSS;

	public CSSPlainEditView(CSSController csc) {
		super();
		this.csc = csc;
	}

	public void init() {
		this.setSize(ViewConstants.CSSPLAIN_WIDTH,
				ViewConstants.CSSPLAIN_HEIGTH);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
		this.spCSS = new JScrollPane();
		this.spCSS.setSize(this.getWidth() - 20, this.getHeight() - 50);
		this.spCSS.setLocation(10, 10);
		this.taCSS = new TextArea(csc.getStyleSheet());
		this.taCSS.setSize(this.getWidth(), this.getHeight());
		this.taCSS.setLocation(0, 0);
		this.spCSS.setViewportView(this.taCSS);
		this.btConfirm = new Button(Constants.getText("button.confirm"));
		this.btConfirm.setSize(this.getWidth() / 2 - 40, 20);
		this.btConfirm.setLocation(20,
				this.spCSS.getY() + this.spCSS.getHeight() + 10);
		this.btConfirm.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				csc.confirm(taCSS.getText());
				setVisible(false);
			}
		});
		this.btCancel = new Button(Constants.getText("button.cancel"));
		this.btCancel.setSize(this.getWidth() / 2 - 20, 20);
		this.btCancel.setLocation(this.getWidth() - this.btCancel.getWidth()
				- 20, this.spCSS.getY() + this.spCSS.getHeight() + 10);
		this.btCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		this.getPane().add(spCSS);
		this.getPane().add(this.btConfirm);
		this.getPane().add(this.btCancel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		taCSS.setText(csc.getStyleSheet());
	}

	@Override
	public void run() {
		init();
	}
}