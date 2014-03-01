package ch.hearc.p3.recsys.io.databases;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.io.Reader;
import ch.hearc.p3.recsys.settings.SettingsFilePaths;

public class RatingsDatabase
{
	/*
	 * Each line in the file is composed by: userID \t itemID \t rating. The
	 * ratings are in binary scale. 1 means that the item is relevant for the
	 * user, 0 means irrelevant. The training set contains 72371 ratings. There
	 * are 6181 users and 6733 items which have been rated by at least one user.
	 * Each user has between 5 and 25 ratings. The itemID are the same of the
	 * ones in the Mapping file.
	 */
	private static final String									SEPARATOR	= "\t";

	// userID \t itemID \t rating
	private static final Map<Integer, Map<Integer, Integer>>	RATING_TABLE;

	static
	{
		RATING_TABLE = new HashMap<Integer, Map<Integer, Integer>>();
		try
		{
			for (String[] strings : Reader.readTextFile(SettingsFilePaths.FILEPATH_RATINGS, SEPARATOR))
			{
				int user = Integer.valueOf(strings[0]);
				if (!RATING_TABLE.containsKey(user))
					RATING_TABLE.put(user, new HashMap<Integer, Integer>());

				RATING_TABLE.get(user).put(Integer.valueOf(strings[1]), Integer.valueOf(strings[2]));
			}
		} catch (FileNotFoundException e)
		{
			System.err.println("Ratings database cannot be read !");
			System.exit(-1);
		} catch (Exception e)
		{
			System.err.println("Fatal error with the ratings database. Message : " + e.getMessage());
			System.exit(-1);
		}
	}

	public static Set<Integer> getAllUsers()
	{
		return RATING_TABLE.keySet();
	}

	public static boolean hasRated(int user, int book)
	{
		return RATING_TABLE.containsKey(user) && RATING_TABLE.get(user).containsKey(book);
	}

	public static Set<Integer> getRatedBooks(int user) throws KeyNotFoundException
	{
		if (!RATING_TABLE.containsKey(user))
			throw new KeyNotFoundException("User (" + user + ") not found !");

		return RATING_TABLE.get(user).keySet();
	}

	public static Integer getRating(int user, int book) throws KeyNotFoundException
	{
		if (!RATING_TABLE.containsKey(user))
			throw new KeyNotFoundException("User (" + user + ") not found !");
		if (!RATING_TABLE.get(user).containsKey(book))
			throw new KeyNotFoundException("Book (" + book + ") for User (" + user + ") not found !");

		return RATING_TABLE.get(user).get(book);
	}

	public static Set<Entry<Integer, Integer>> getUsersRatings(int user) throws KeyNotFoundException
	{
		if (!RATING_TABLE.containsKey(user))
			throw new KeyNotFoundException("User (" + user + ") not found !");

		return RATING_TABLE.get(user).entrySet();
	}
}
