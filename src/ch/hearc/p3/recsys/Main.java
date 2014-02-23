package ch.hearc.p3.recsys;

import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.io.books.ExportBooksFromDBpedia;
import ch.hearc.p3.recsys.io.xml.ImportBooksXML;
import ch.hearc.p3.recsys.utils.Pair;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		List<Pair<Integer, Map<TypeData, List<String>>>> books = ImportBooksXML.importBooksXML(ExportBooksFromDBpedia.BOOKS_FILE);
	}
}
