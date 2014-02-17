package ch.hearc.p3.recsys;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class BooksDatabase {

	private static final String SEPARATOR = "\t";
	private static final String FILEPATH_DATABASE = "res/dbpedia_mapping.tsv";

	// DBbook_ItemID \t name \t DBpedia_uri
	private static final Map<String, Pair<String, String>> BOOK_TABLE;

	static {
		BOOK_TABLE = new HashMap<String, Pair<String, String>>();
		try {
			for (String[] strings : Reader.readTextFile(FILEPATH_DATABASE,
					SEPARATOR))
				BOOK_TABLE.put(strings[0], new Pair<String, String>(strings[1],
						strings[2]));
		} catch (FileNotFoundException e) {
			System.err.println("Database cannot be read !");
			System.exit(-1);
		} catch (Exception e) {
			System.err.println("Fatal error with the database. Message : "
					+ e.getMessage());
			System.exit(-1);
		}
	}

	public static Pair<String, String> getBook(int id)
			throws KeyNotFoundException {
		return getBook(String.valueOf(id));
	}

	public static Pair<String, String> getBook(String id)
			throws KeyNotFoundException {
		if (!BOOK_TABLE.containsKey(id))
			throw new KeyNotFoundException("Object not found !");
		return BOOK_TABLE.get(id);
	}
}
