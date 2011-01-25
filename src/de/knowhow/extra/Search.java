package de.knowhow.extra;

import java.util.ArrayList;
import java.util.Iterator;

import de.knowhow.model.Article;

public class Search {

	public static Iterator<Article> getArticles(String searchKey,
			Iterator<Article> search) {
		ArrayList<Article> result = new ArrayList<Article>();
		while (search.hasNext()) {
			Article currArt = search.next();
			if (currArt.getContent().toLowerCase()
					.contains(searchKey.toLowerCase())) {
				result.add(currArt);
			} else if (currArt.getName().toLowerCase()
					.contains(searchKey.toLowerCase())) {
				result.add(currArt);
			}
		}
		return result.iterator();
	}
}