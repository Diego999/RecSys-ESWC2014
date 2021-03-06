package ch.hearc.p3.recsys.io.databases;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.io.Reader;
import ch.hearc.p3.recsys.settings.SettingsFilePaths;
import ch.hearc.p3.recsys.utils.Pair;

public class BooksDatabase
{
	private static final String								SEPARATOR	= "\t";

	// DBbook_ItemID \t name \t DBpedia_uri
	private static final Map<Integer, Pair<String, String>>	BOOK_TABLE;

	static
	{
		BOOK_TABLE = new HashMap<Integer, Pair<String, String>>();
		try
		{
			for (String[] strings : Reader.readTextFile(SettingsFilePaths.FILEPATH_BOOK_MAPPING, SEPARATOR))
				BOOK_TABLE.put(Integer.valueOf(strings[0]), new Pair<String, String>(strings[1], strings[2]));
		} catch (FileNotFoundException e)
		{
			System.err.println("Books Database cannot be read !");
			System.exit(-1);
		} catch (Exception e)
		{
			System.err.println("Fatal error with the books database. Message : " + e.getMessage());
			System.exit(-1);
		}
	}

	public static boolean contains(int book)
	{
		return BOOK_TABLE.containsKey(book);
	}

	public static Set<Integer> getAllBooks()
	{
		return BOOK_TABLE.keySet();
	}
	
	public static Map<Integer, Pair<String, String>> getAllDataBooks()
	{
		return BOOK_TABLE;
	}

	public static Pair<String, String> getBook(int id) throws KeyNotFoundException
	{
		if (!BOOK_TABLE.containsKey(id))
			throw new KeyNotFoundException("Object not found !");
		return BOOK_TABLE.get(id);
	}
}
