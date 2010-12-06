package de.knowhow.model;

import java.util.ArrayList;

public class Search {
	

	public static ArrayList<Article> getArticles(String searchKey, ArrayList<Article> search){
		ArrayList<Article> result = new ArrayList<Article>();
		for (int i = 0; i< search.size();i++){
			if(search.get(i).getContent().toLowerCase().contains(searchKey.toLowerCase())){
				result.add(search.get(i));
			}
			else if (search.get(i).getName().toLowerCase().contains(searchKey.toLowerCase())){
				result.add(search.get(i));
			}
		}
		return result;
	}
}