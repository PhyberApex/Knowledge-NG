package de.knowhow.extra.security;

import java.util.ArrayList;

public class SwitchedHexEncoder extends Encoder {

	public String encode(String pSecret) {
		String revealed = "";
		boolean err = false;
		if (pSecret.length() % 2 != 0) {
			err = true;
		} else {
			String two = "";
			while (pSecret.length() > 0) {
				err = (!checkForHex(pSecret.substring(0, 1)));
				if (err) {
					break;
				}
				two = getFirstTwo(pSecret);
				two = String.valueOf((char) Integer.parseInt(two, 16));
				revealed += two;
				pSecret = pSecret.substring(2);
			}
		}
		if (err) {
			revealed = "Ungültiger Wert.";
		}
		return revealed;
	}

	private String getFirstTwo(String pSecret) {
		String first = "";
		String second = "";
		first = pSecret.substring(1, 2);
		second = pSecret.substring(0, 1);
		return first.concat(second);
	}

	private boolean checkForHex(String pStr) {
		String[] strHex = new String[] { "0", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "A", "B", "C", "D", "E", "F" };
		ArrayList<String> hex = new ArrayList<String>();
		for (int i = 0; i < strHex.length; i++) {
			hex.add(strHex[i]);
		}
		for (int i = 0; i < pStr.length(); i++) {
			if (!hex.contains(pStr.substring(i, i + 1))) {
				return false;
			}
		}
		return true;
	}
}