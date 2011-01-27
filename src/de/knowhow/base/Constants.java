package de.knowhow.base;

/**
 * This class holds the basic information for this application
 */

import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

public class Constants {

	private static final String APP_NAME = "KnowledgeNG";
	private static final String APP_VERSION = "1.4";
	private static final String APP_AUTHOR = "Janis Walliser";
	private static String db_name;
	private static ResourceBundle lang = ResourceBundle
			.getBundle("de.knowhow.resource.knowledge");

	public static String getAppName() {
		return APP_NAME;
	}

	public static String getAppVersion() {
		return APP_VERSION;
	}

	public static String getAppAuthor() {
		return APP_AUTHOR;
	}

	public static String getDBName() {
		return db_name;
	}

	public static void setDBName(String newDB) {
		db_name = newDB;
	}

	public static String getText(String key) {
		return lang.getString(key);
	}

	public static void setLanguage(Locale locale) {
		lang = ResourceBundle
				.getBundle("de.knowhow.resource.knowledge", locale);
	}

	public static String getDate() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (month.length() < 2) {
			month = "0" + month;
		}
		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		if (day.length() < 2) {
			day = "0" + day;
		}
		return day + "." + month + "." + year;
	}

	public static ResourceBundle getBundle() {
		return lang;
	}

	public static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Constants.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
