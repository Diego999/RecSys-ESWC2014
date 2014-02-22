package ch.hearc.p3.recsys.io.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ch.hearc.p3.recsys.bookanalysis.SPARQLExecutor;
import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.exception.AttributeFormIncorrectException;
import ch.hearc.p3.recsys.exception.PrefixUnknownException;
import ch.hearc.p3.recsys.io.databases.BooksDatabase;
import ch.hearc.p3.recsys.io.xml.ExportBooksXML;
import ch.hearc.p3.recsys.settings.SettingsSPARQL;
import ch.hearc.p3.recsys.utils.Pair;

public class ExportBooksFromDBpedia
{
	public static void export() throws AttributeFormIncorrectException, PrefixUnknownException, ParserConfigurationException, TransformerException
	{
		long start = System.currentTimeMillis();
		int i = 0;
		double size = BooksDatabase.getAllBooks().size();
		List<Map<TypeData, List<String>>> allBooks = new ArrayList<Map<TypeData, List<String>>>();

		for (Pair<String, String> pair : BooksDatabase.getAllBooks())
		{
			System.out.println(String.format("%.2f", i / size) + "% " + pair.getKey());
			Map<TypeData, List<String>> data = new HashMap<TypeData, List<String>>();
			for (Entry<TypeData, Pair<String[], Map<String, List<String>>>> entry : SettingsSPARQL.ALL_DATA_TO_EXTRACT.entrySet())
			{
				List<String> f = new ArrayList<String>();
				for (String at : entry.getValue().getKey())
				{
					List<String> ret = SPARQLExecutor.exec(pair.getValue(), at, entry.getValue().getValue());
					if (ret != null)
					{
						f.addAll(ret);
					}
				}
				data.put(entry.getKey(), f);
			}
			allBooks.add(data);
			i += 1;
		}
		ExportBooksXML.exportBooksXML("test.xml", allBooks);
		System.out.println("Time : " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");
	}
}
