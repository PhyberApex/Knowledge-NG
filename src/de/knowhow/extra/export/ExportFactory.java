package de.knowhow.extra.export;

import de.knowhow.controller.MainController;

public class ExportFactory {

	public static final int HTML = 1;
	public static final int PDF = 2;

	public static ExportType createExport(int format, MainController mc) {
		ExportType export;
		switch (format) {
		case HTML:
			export = new HTMLExport(mc);
			break;
		case PDF:
			export = new PDFExport(mc);
			break;
		default:
			export = new HTMLExport(mc);
		}
		return export;
	}
}