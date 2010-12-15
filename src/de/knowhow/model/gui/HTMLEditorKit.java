package de.knowhow.model.gui;

import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;

public class HTMLEditorKit extends javax.swing.text.html.HTMLEditorKit {

	private static final long serialVersionUID = 1L;

	public ViewFactory getViewFactory() {
		return new HTMLFactoryX();
	}

	public static class HTMLFactoryX extends HTMLFactory implements ViewFactory {

		public View create(Element elem) {
			Object o = elem.getAttributes().getAttribute(
					StyleConstants.NameAttribute);
			if (o instanceof HTML.Tag) {
				HTML.Tag kind = (HTML.Tag) o;
				if (kind == HTML.Tag.IMG)
					return new ImageView(elem);
			}
			return super.create(elem);
		}
	}
}
