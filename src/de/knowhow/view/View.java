package de.knowhow.view;

import java.awt.Component;
import java.util.Observer;

public abstract class View implements Runnable, Observer {

	protected Component window;

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
}
