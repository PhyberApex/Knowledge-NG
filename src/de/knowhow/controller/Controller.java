package de.knowhow.controller;

import java.util.ArrayList;

import de.knowhow.model.Model;
import de.knowhow.view.View;

public abstract class Controller {

	protected ArrayList<Model> models = new ArrayList<Model>();
	protected ArrayList<View> views = new ArrayList<View>();
	protected View currView;

	// In this method you should load your frame/dialog
	public abstract void loadGUI();

	// In this method your model shoul be loaded
	public abstract void loadData();

	public ArrayList<View> getViews() {
		return views;
	}

	public View getCurrView() {
		return currView;
	}

	// Add every view as an observer to every model
	public void addObservers() {
		for (int i = 0; i < views.size(); i++) {
			for (int j = 0; j < models.size(); j++) {
				models.get(j).addObserver(views.get(i));
			}
		}
	}
}