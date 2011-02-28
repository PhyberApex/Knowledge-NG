package de.knowhow.extra.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.knowhow.base.Constants;
import de.knowhow.controller.MainController;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Article;
import de.knowhow.model.Attachment;
import de.knowhow.model.Topic;

public class HTMLExport extends ExportType {

	public HTMLExport(MainController mc) {
		super(mc);
	}

	@Override
	protected void innerExport(String path) throws IOException {
		String absolutePath = path + "/" + Constants.getDBName();
		new File(absolutePath).mkdir();
		new File(absolutePath + "/articles").mkdir();
		new File(absolutePath + "/attachments").mkdir();
		splash.showStatus(Constants.getText("export.css"), 101);
		String stylesheet = mc.getCsc().getStyleSheet();
		FileOutputStream stylesheetOut = new FileOutputStream(absolutePath
				+ "/style.css");
		for (int i = 0; i < stylesheet.length(); i++) {
			stylesheetOut.write((byte) stylesheet.charAt(i));
		}
		stylesheetOut.close();
		splash.showStatus(Constants.getText("export.index"), 101);
		String html = "<html>\n<head>\n"
				+ "<link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\" />\n"
				+ "<title>" + Constants.getDBName()
				+ "</title>\n</head>\n<body>\n<h1>" + Constants.getDBName()
				+ "</h1>\n<ul>";
		html = appendIndexBody(html, 0);
		html += "\n</ul></body>\n</html>";
		File index = new File(absolutePath + "/index.html");
		index.createNewFile();
		FileOutputStream fileOut = new FileOutputStream(index);
		for (int i = 0; i < html.length(); i++) {
			splash.showStatus(Constants.getText("export.index"),
					(html.length() / (i + 1)));
			fileOut.write((byte) html.charAt(i));
		}
		fileOut.close();
		ArrayList<Article> al = mc.getAcl().getArticles();
		for (int i = 0; i < al.size(); i++) {
			splash.showStatus(Constants.getText("export.article") + " "
					+ +(i + 1) + "/" + al.size() + "...", 0);
			String content = al.get(i).getContent();
			// make head information CSS and title for article
			content = content
					.replaceAll(
							"<head>[^³]*</head>",
							"<head>\n\t<link rel=\"stylesheet\" href=\"../style.css\" type=\"text/css\" />\n\t<title>"
									+ al.get(i).getName() + "</title>\n</head>");
			// make image links
			content = content.replaceAll("<img src=\"tmp/",
					"<img src=\"../attachments/");
			// make file link
			content = content.replaceAll("<a href=\"attachment://",
					"<a href=\"../attachments/");
			// make article link
			content = content.replaceAll("<a href=\"article://",
					"<a href=\"../articles/");
			// add back link at the end of article file
			content = content.replaceAll("</body>",
					"<p><a id =\"backLink\" href=\"../index.html \">"
							+ Constants.getText("export.backToIndex")
							+ "</p>\n</body>");
			FileOutputStream writeStream = new FileOutputStream(absolutePath
					+ "/articles/" + al.get(i).getArticle_ID() + "");
			for (int j = 0; j < content.length(); j++) {
				splash.showStatus(Constants.getText("export.article") + " "
						+ +(i + 1) + "/" + al.size() + "...",
						(content.length() / (j + 1)));
				writeStream.write((byte) content.charAt(j));
			}
			writeStream.close();
		}
		ArrayList<Attachment> attl = mc.getAttL().getAttachments();
		for (int i = 0; i < attl.size(); i++) {
			splash.showStatus(Constants.getText("export.attachment") + " "
					+ (i + 1) + "/" + attl.size() + "...", 101);
			try {
				attl.get(i).loadBin();
			} catch (DatabaseException e) {
				mc.error(e);
			}
			FileOutputStream writeStream = new FileOutputStream(absolutePath
					+ "/attachments/"
					+ attl.get(i).getAttachment_ID()
					+ attl.get(i).getName()
							.substring(attl.get(i).getName().length()));
			writeStream.write(attl.get(i).getBinary());
			writeStream.close();
			attl.get(i).clearBinary();
		}
	}

	private String appendIndexBody(String html, int iD) {
		ArrayList<Topic> tl = mc.getTcl().getTopics();
		ArrayList<Article> al = mc.getAcl().getArticles();
		for (int i = 0; i < tl.size(); i++) {
			if (tl.get(i).getTopic_ID_FK() == iD) {
				html += "\n<li><ul>" + tl.get(i).getName();
				html = appendIndexBody(html, tl.get(i).getTopic_ID());
				for (int j = 0; j < al.size(); j++) {
					if (al.get(j).getTopic_ID_FK() == tl.get(i).getTopic_ID()) {
						html += "\n<li><a href=\"articles/"
								+ al.get(j).getArticle_ID() + "\">"
								+ al.get(j).getName() + "</a></li>";
					}
				}
				html += "\n</ul></li>";
			} else {
				continue;
			}
		}
		return html;
	}

	@Override
	public String getInfotext() {
		return Constants.getText("export.HTML");
	}

}
