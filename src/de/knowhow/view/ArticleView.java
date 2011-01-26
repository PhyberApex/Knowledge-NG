package de.knowhow.view;

public abstract class ArticleView extends View {

	public static int RENDEREDVIEW = 1;
	public static int PLAINVIEW = 2;

	public void insertHTML(String tag) {
		throw new UnsupportedOperationException();
	}

	public void insertFileLink(int iD) {
		throw new UnsupportedOperationException();
	}

	public void insertImageLink(int iD) {
		throw new UnsupportedOperationException();
	}

	public void insertArticleLink(int iD) {
		throw new UnsupportedOperationException();
	}
}
