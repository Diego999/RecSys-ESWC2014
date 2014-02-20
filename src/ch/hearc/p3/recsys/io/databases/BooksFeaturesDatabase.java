package ch.hearc.p3.recsys.io.databases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.utils.Pair;

public class BooksFeaturesDatabase
{
	private static Map<Integer, List<Pair<String, Double>>>	BOOKS_FEATURES_TABLE;

	static
	{
		BOOKS_FEATURES_TABLE = new HashMap<Integer, List<Pair<String, Double>>>();
	}

	public static List<Pair<String, Double>> getFeaturesBook(int book) throws KeyNotFoundException
	{
		if (!BOOKS_FEATURES_TABLE.containsKey(book))
			throw new KeyNotFoundException("Object not found !");
		return BOOKS_FEATURES_TABLE.get(book);
	}

	public static void addBookFeature(int book, String feature, double weight) throws KeyNotFoundException
	{
		if (!BOOKS_FEATURES_TABLE.containsKey(book))
		{
			if (!BooksDatabase.contains(book))
				throw new KeyNotFoundException("The book " + book + " doesn't exist !");
			BOOKS_FEATURES_TABLE.put(book, new ArrayList<Pair<String, Double>>());
		}

		if (!FeaturesDatabase.contains(feature))
			throw new KeyNotFoundException("The feature " + feature + " doesn't exist !");

		BOOKS_FEATURES_TABLE.get(book).add(new Pair<String, Double>(feature, weight));
	}
}
