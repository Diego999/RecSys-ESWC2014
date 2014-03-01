package ch.hearc.p3.recsys.io.databases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.io.Reader;
import ch.hearc.p3.recsys.settings.SettingsFilePaths;
import ch.hearc.p3.recsys.utils.Pair;

public class BooksFeaturesDatabase
{
	private static Map<Integer, List<Pair<String, Double>>>	BOOKS_FEATURES_TABLE;

	static
	{
		BOOKS_FEATURES_TABLE = new HashMap<Integer, List<Pair<String, Double>>>();
		try
		{
			for (String[] strings : Reader.readTextFile(SettingsFilePaths.FILEPATH_FEATURES, SettingsFilePaths.SEPARATOR_FEATURES))
				try
				{
					int id = Integer.valueOf(strings[0]);
					String feature = strings[1];
					double weight = Double.valueOf(strings[2]);
					FeaturesDatabase.addFeatures(feature);

					if (!BooksDatabase.contains(id))
						throw new KeyNotFoundException("Book " + id + " hasn't be found !");
					if (!BOOKS_FEATURES_TABLE.containsKey(id))
						BOOKS_FEATURES_TABLE.put(id, new ArrayList<Pair<String, Double>>());
					BOOKS_FEATURES_TABLE.get(id).add(new Pair<String, Double>(feature, weight));
				} catch (Exception e)
				{
					// Nothing, we don't add the feature if there is a problem.
				}
		} catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("Features file cannot be loaded !");
			System.exit(-1);
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}

	public static void initialize()
	{

	}

	public static List<Pair<String, Double>> getFeaturesBook(int book) throws KeyNotFoundException
	{
		if (!BOOKS_FEATURES_TABLE.containsKey(book))
			throw new KeyNotFoundException("Object not found !");
		return BOOKS_FEATURES_TABLE.get(book);
	}

	public static Set<String> getFeaturesBookTextOnly(int book) throws KeyNotFoundException
	{
		if (!BOOKS_FEATURES_TABLE.containsKey(book))
			throw new KeyNotFoundException("Object not found !");
		Set<String> features = new HashSet<String>();
		for (Pair<String, Double> entry : BOOKS_FEATURES_TABLE.get(book))
			features.add(entry.getKey());
		return features;
	}

	public static double getWeight(int book, String feature) throws KeyNotFoundException
	{
		if (!BOOKS_FEATURES_TABLE.containsKey(book))
			throw new KeyNotFoundException("Object not found !");

		for (Pair<String, Double> pair : BOOKS_FEATURES_TABLE.get(book))
			if (pair.getKey().equals(feature))
				return pair.getValue();

		throw new KeyNotFoundException("The feature " + feature + " is not associated with the book " + book);
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
