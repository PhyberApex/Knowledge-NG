package de.knowhow.base;

import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class Constants {

	private static final String APP_NAME = "Knowledge-NG";
	private static final String APP_VERSION = "1.2";
	private static String db_name;
	private static String host;
	private static String user;
	private static String password;
	private static ResourceBundle lang = ResourceBundle
			.getBundle("de.knowhow.resource.knowledge");

	public static String getAppName() {
		return APP_NAME;
	}

	public static String getAppVersion() {
		return APP_VERSION;
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

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		Constants.host = host;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		Constants.user = user;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Constants.password = password;
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
}
