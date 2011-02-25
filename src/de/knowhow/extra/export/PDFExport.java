package de.knowhow.extra.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import de.knowhow.base.Constants;
import de.knowhow.controller.MainController;
import de.knowhow.model.Article;
import de.knowhow.model.Topic;

public class PDFExport extends ExportType {

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);

	private Document document;

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
			// create index page(s)
			Paragraph preface = new Paragraph();
			addEmptyLine(preface, 1);
			// Header
			preface.add(new Paragraph(Constants.getDBName(), catFont));
			addEmptyLine(preface, 1);
			List list = new List(true, false, 10);
			appendIndex(list, 0);
			document.add(list);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO I DONT THE FUCK KNOW HOW TO DO THAT SRSLY!!!
	
	private void appendIndex(List list, int iD) {
		ArrayList<Topic> tl = mc.getTcl().getTopics();
		ArrayList<Article> al = mc.getAcl().getArticles();
		for (int i = 0; i < tl.size(); i++) {
			if (tl.get(i).getTopic_ID_FK() == iD) {
				list.add(tl.get(i).getName());
				List newList = new List();
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
