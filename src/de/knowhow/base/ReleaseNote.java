package de.knowhow.base;

public class ReleaseNote {

	private static final String note = "<html><body>\n" + "<h1>"
			+ Constants.getAppName()
			+ "</h1>\n"
			+ "<h2>Version "
			+ Constants.getAppVersion()
			+ "</h2>\n"
			+ "<h3>Release Notes:</h3>"
			+ "<ul>"
			+ "<li>Organize all your knowhow in topics and articles</li>\n"
			+ "<li>The articles are saved in simple HTML</li>\n"
			+ "<li>If you have not used this programm before a default databse will be created named as \"knowledge.db\"</li>\n"
			+ "<li>Look of the article will can be defined via the style.css css-file</li>\n"
			+ "<li>Saving is made in a sqlite database</li>\n"
			+ "<li>You can create new databases</li>\n"
			+ "<li>Following data can be saved:<ul>\n"
			+ "<li>Topics:<ul>\n"
			+ "<li>Topicname</li>\n"
			+ "<li>Topic of the topic</li>\n"
			+ "</ul></li>\n"
			+ "<li>Article<ul>\n"
			+ "<li>Articlename</li>\n"
			+ "<li>Content of the article(HTML)</li>\n"
			+ "<li>Date of last change</li>\n"
			+ "<li>Topic of the article</li>\n"
			+ "</ul></li>\n"
			+ "</ul></li>\n"
			+ "</ul>\n"
			+ "<h3>Changelog:</h3>\n"
			+ "<ul>\n"
			+ "<li>1.0<ul>\n"
			+ "<li>RELEASE</li>\n"
			+ "</ul></li>\n"
			+ "</ul>\n"
			+ "<h3>Known Bugs:</h3>\n"
			+ "<ul>\n"
			+ "<li>None up till now</li>\n"
			+ "</ul>\n"
			+ "<h3>ToDos:</h3>\n"
			+ "<ul>\n"
			+ "<li>Exportfunktion die Wissensdatenbank in HTML exportieren.</li>\n"
			+ "<li><b>CSS Editor</b></li>\n" + "</ul>" + "</body>\n\n";

	public static final String getReleaseNote() {
		return note;
	}
}
