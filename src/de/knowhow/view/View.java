package de.knowhow.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.Observer;

public abstract class View implements Runnable, Observer {

	protected Component window;
	protected GridBagConstraints constraints = new GridBagConstraints();

	protected abstract void init();

	public View setVisible(boolean visible) {
		window.setVisible(visible);
		return this;
	}

	@Override
	public void run() {
		init();
	}

	public Component getComponent() {
		return window;
	}

	public abstract boolean isComponent();

	public GridBagConstraints getConstraints() {
		return constraints;
	}
}
