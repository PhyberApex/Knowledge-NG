package de.knowhow.extra.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import de.knowhow.base.Constants;
import de.knowhow.controller.MainController;
import de.knowhow.model.Article;
import de.knowhow.model.Topic;

public class PDFExport extends ExportType {

	private static Font FONTH1 = new Font(Font.FontFamily.TIMES_ROMAN, 24,
			Font.BOLD);
	private static Font FONTH2 = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font FONTHEADPATH = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.NORMAL);
	private static Font article = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);

	private Document document;
	private String headline = "";

	public PDFExport(MainController mc) {
		super(mc);
	}

	@Override
	protected void innerExport(String path) throws IOException {
		try {
			// new File(path + Constants.getDBName()).mkdir();
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

	private Element getPDFBody() {
		Paragraph body = new Paragraph();
		appendBody(body, 0);
		return body;
	}

	private void appendBody(Paragraph body, int iD) {
		ArrayList<Topic> tl = mc.getTcl().getTopics();
		ArrayList<Article> al = mc.getAcl().getArticles();
		for (int i = 0; i < tl.size(); i++) {
			if (tl.get(i).getTopic_ID_FK() == iD) {
				headline += tl.get(i).getName() + " => ";
				appendBody(body, tl.get(i).getTopic_ID());
				for (int j = 0; j < al.size(); j++) {
					if (al.get(j).getTopic_ID_FK() == tl.get(i).getTopic_ID()) {
						headline += al.get(j).getName();
						body.add(new Paragraph(headline, FONTHEADPATH));
						parseHtmlToPdf(al.get(j).getContent(), body);
						addEmptyLine(body, 1);
						headline = headline.substring(0, headline.length()
								- al.get(j).getName().length());
					}
				}
				headline = headline.substring(0, headline.length()
						- (tl.get(i).getName() + " => ").length());
			} else {
				continue;
			}
		}
	}

	private void parseHtmlToPdf(String content, Paragraph body) {
		body.add(content);
		addEmptyLine(body, 2);
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
}
