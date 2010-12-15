package de.knowhow.model.gui;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	public TableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
