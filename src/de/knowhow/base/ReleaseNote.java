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
			+ "<li>If you have not used this programm before a default database will be created named as \"knowledge.db\"</li>\n"
			+ "<li>Presentation of the articles is made via CSS, which is editable under \"Edit\"</li>\n"
			+ "<li>Saving is made in a sqlite database or in a MYSQL-database</li>\n"
			+ "<li>You can attach binary files to your articles, and images wich will be shown in the HTML-View</li>\n"
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
			+ "</ul>\n";

	public static final String getReleaseNote() {
		return note;
	}
}