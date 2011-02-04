package de.knowhow.extra.security;

public class SwitchedHexDecoder extends Decoder {

	public String decode(String pClear) {
		String clear = "";
		String help = pClear;
		for (int i = 0; i < help.length(); i++) {
			char c = help.charAt(i);
			String hexstr = Integer.toHexString(c);
			hexstr = switchFirstTwo(hexstr);
			clear += hexstr;
		}
		return clear.toUpperCase();
	}

	private String switchFirstTwo(String pClear) {
		String switched = "";
		switched += pClear.substring(1, 2);
		switched += pClear.substring(0, 1);
		return switched;
	}
}
