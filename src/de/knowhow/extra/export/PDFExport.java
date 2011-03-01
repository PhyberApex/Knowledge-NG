package de.knowhow.extra.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import de.knowhow.base.Constants;
import de.knowhow.controller.MainController;
import de.knowhow.model.Article;
import de.knowhow.model.Topic;

public class PDFExport extends ExportType {

	private static Font FONTH1 = new Font(Font.FontFamily.TIMES_ROMAN, 32,
			Font.BOLD);
	private static Font FONTH2 = new Font(Font.FontFamily.TIMES_ROMAN, 24,
			Font.BOLD);
	private static Font FONTHEADPATH = new Font(Font.FontFamily.TIMES_ROMAN,
			12, Font.NORMAL);
	private static Font FONTH3 = new Font(Font.FontFamily.TIMES_ROMAN, 20,
			Font.NORMAL);
	private static Font FONTARTICLE = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.NORMAL);
	private static Font TAGFONTH1 = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.NORMAL);

	private Document document;
	private String headline = "";

	public PDFExport(MainController mc) {
		super(mc);
	}

	@Override
	protected void innerExport(String path) throws IOException {
		try {
			document = new Document();
			path = path + "/" + Constants.getDBName() + ".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			document.addTitle(Constants.getDBName());
			document.addAuthor(System.getProperty("user.name"));
			document.addCreator(Constants.getAppName() + "version "
					+ Constants.getAppVersion());
			document.add(getIndexPages());
			document.newPage();
			document.add(getPDFBody());
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Element getPDFBody() throws DocumentException {
		Paragraph body = new Paragraph();
		appendBody(body, 0);
		return body;
	}

	private void appendBody(Paragraph body, int iD) throws DocumentException {
		ArrayList<Topic> tl = mc.getTcl().getTopics();
		ArrayList<Article> al = mc.getAcl().getArticles();
		for (int i = 0; i < tl.size(); i++) {
			if (tl.get(i).getTopic_ID_FK() == iD) {
				headline += tl.get(i).getName() + " => ";
				appendBody(body, tl.get(i).getTopic_ID());
				boolean header = false;
				for (int j = 0; j < al.size(); j++) {
					if (al.get(j).getTopic_ID_FK() == tl.get(i).getTopic_ID()) {
						if (!header) {
							body.add(new Paragraph(tl.get(i).getName(), FONTH2));
							header = true;
						}
						headline += al.get(j).getName();
						addEmptyLine(body, 1);
						body.add(new Paragraph(al.get(j).getName(), FONTH3));
						body.add(new Paragraph(headline, FONTHEADPATH));
						LineSeparator sep = new LineSeparator();
						sep.setPercentage(75);
						body.add(sep);
						addEmptyLine(body, 1);
						String content = al.get(j).getContent();
						body.add(parseHtmlToPdf(content));
						body.add(sep);
						addEmptyLine(body, 2);
						headline = headline.substring(0, headline.length()
								- al.get(j).getName().length());
					}
				}
				headline = headline.substring(0, headline.length()
						- (tl.get(i).getName() + " => ").length());
				if (!header) {
					LineSeparator sep = new LineSeparator();
					sep.setPercentage(100);
					body.add(sep);
					addEmptyLine(body, 1);
				}
			} else {
				continue;
			}
			addEmptyLine(body, 1);
		}
	}

	private Element parseHtmlToPdf(String content) {
		Paragraph body = new Paragraph("", FONTARTICLE);
		String search = "<body>";
		content = content.substring(content.indexOf(search) + search.length());
		content = content.substring(0, content.indexOf("</body>"));
		int i = 0;
		boolean end = false;
		while (!end) {
			content = content.trim();
			if (content.startsWith("<p>")) {
				int indexOfNextTag = content.indexOf("<");
				if (indexOfNextTag != -1) {
					body.add(content.substring(
							content.indexOf("<p>") + "<p>".length(),
							indexOfNextTag));
					int indexOfNextCloseTag = content.indexOf("</"
							+ content.charAt(indexOfNextTag + 1));
					content = content.substring(indexOfNextTag,
							indexOfNextCloseTag);
					body.add(parseHtmlToPdf(content));
					int indexOfClosingTag = content.lastIndexOf("</p>");
					body.add(content.substring("<p>".length(),
							indexOfClosingTag));
				} else {
					body.add(content.substring(
							content.indexOf("<p>") + "<p>".length(),
							content.indexOf("</p>")));
					content = content.substring(content.indexOf("</p>")
							+ "</p>".length());
				}

			} else if (content.startsWith("<a")) {

			}
			if (content.indexOf("</") == -1) {
				end = true;
			}
			i++;
			if (i > 100) {
				break;
			}
		}
		return body;
	}

	private Element getIndexPages() {
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		preface.add(new Paragraph(Constants.getDBName(), FONTH1));
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Content", FONTH2));
		addEmptyLine(preface, 1);
		List list = new List(true, false, 15);
		appendIndex(list, 0);
		preface.add(list);
		return preface;
	}

	private void appendIndex(List list, int iD) {
		ArrayList<Topic> tl = mc.getTcl().getTopics();
		ArrayList<Article> al = mc.getAcl().getArticles();
		for (int i = 0; i < tl.size(); i++) {
			if (tl.get(i).getTopic_ID_FK() == iD) {
				list.add(tl.get(i).getName());
				List newList = new List(false, false, 15);
				appendIndex(newList, tl.get(i).getTopic_ID());
				for (int j = 0; j < al.size(); j++) {
					if (al.get(j).getTopic_ID_FK() == tl.get(i).getTopic_ID()) {
						newList.add(al.get(j).getName());
					}
				}
				list.add(newList);
			} else {
				continue;
			}
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	@Override
	public String getInfotext() {
		return Constants.getText("export.PDF");
	}
}
