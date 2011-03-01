package de.knowhow.extra.parser;

public class HTMLParser {

	public static final int TAG_P = 1;
	public static final int TAG_A = 2;
	public static final int TAG_IMG = 3;
	public static final int TAG_B = 4;
	public static final int TAG_I = 5;

	public static String parseToPDF(String content) {
		String value = "";
		while (content.contains("</")) {
			content = content.trim();
			content = content.substring(content.indexOf("<"));
			int tag = 0;
			String close = "";
			if (content.startsWith("<p")) {
				value += "\nP-TAG\n";
				close = "</p>";
			} else if (content.startsWith("<a")) {
				value += "\nA-TAG\n";
				close = "</a>";
			} else if (content.startsWith("<b")) {
				value += "\nB-TAG\n";
				close = "</b>";
			}
			content = content.substring(content.indexOf(">") + 1);
			if (content.indexOf("<") != -1) {
				value += content.substring(0, content.indexOf("<"));
			} else {
				value += content.substring(0, content.indexOf("<"));
			}
			String tmp = content.substring(content.indexOf("<")).trim();
			content = tmp;
			if (tmp.startsWith("<p")) {
				tag = TAG_P;
			} else if (tmp.startsWith("<a")) {
				tag = TAG_A;
			} else if (tmp.startsWith("<b")) {
				tag = TAG_B;
			} else {
				value += "\nTAG ENDE\n";
				return value;
			}
			int position = getPositionOfClosingTag(tmp, tag);
			tmp = tmp.substring(0, position);
			value += parseToPDF(tmp);
			value += content.substring(position,
					content.indexOf(close, position));
			content = content.substring(position,
					content.indexOf(close, position) + close.length());
			value += "\nTAG ENDE\n";

		}
		return value;
	}

	private static int getPositionOfClosingTag(String content, int tag) {
		String open, close;
		switch (tag) {
		case TAG_P:
			open = "<p";
			close = "</p>";
			break;
		case TAG_A:
			open = "<a";
			close = "</a>";
			break;
		case TAG_IMG:
			open = "<img";
			close = "</p>";
			break;
		case TAG_B:
			open = "<b";
			close = "</b";
			break;
		case TAG_I:
			open = "<i";
			close = "</i>";
			break;
		default:
			open = "";
			close = "";
		}
		String tmp = content;
		int count = 0;
		while (tmp.contains(open)) {
			tmp = tmp.substring(tmp.indexOf(open) + 1);
			count++;
		}
		int position = 0;
		for (int i = 0; i < count; i++) {
			position += content.substring(0,
					content.indexOf(close) + close.length()).length();
			content = content
					.substring(content.indexOf(close) + close.length());
		}
		return position;
	}
}