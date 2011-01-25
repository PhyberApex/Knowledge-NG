package de.knowhow.view;

public abstract class ArticleView extends View {

	public abstract void insertHTML(String tag);

	public abstract void insertFileLink(int iD);

	public abstract void insertImageLink(int iD);

	public abstract void insertArticleLink(int iD);
}
