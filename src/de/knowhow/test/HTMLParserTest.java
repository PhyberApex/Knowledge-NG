package de.knowhow.test;

import de.knowhow.extra.parser.HTMLParser;

public class HTMLParserTest {

	public static void main(String[] args){
		String value = "<p>TEST<b>BLABLA<a>TESTTEST</a>BLUBB</b>TEST2</p>";
		System.out.println(HTMLParser.parseToPDF(value));
	}
}
