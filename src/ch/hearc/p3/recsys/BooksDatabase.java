package ch.hearc.p3.recsys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class BooksDatabase {
	
	private static final String SEPARATOR = "\t";
	private static final String FILEPATH_DATABASE = "res/dbpedia_mapping.tsv";
	
	//DBbook_ItemID \t name \t DBpedia_uri
	private static Map<String, Pair<String, String>> BOOK_TABLE = null;
	
	static
	{
		//This try-catch sucks a bit. We keep it like this to simplify
		try
		{
			FileReader fr = new FileReader(FILEPATH_DATABASE);
			BufferedReader bf = new BufferedReader(fr);
				
			String line;
			BOOK_TABLE = new HashMap<String, Pair<String, String>>();
			while((line = bf.readLine()) != null)
				{
				String[] data = line.split(SEPARATOR);
				BOOK_TABLE.put(data[0], new Pair<String, String>(data[1], data[2]));
				}
			
			bf.close();
			fr.close();
		}
		catch(Exception e)
		{
			System.err.println("Database cannot be read !");
			System.exit(-1);
		}
	}
	
	public static Pair<String, String> getBook(int id) throws KeyNotFoundException
	{
		return getBook(String.valueOf(id));
	}
	
	public static Pair<String, String> getBook(String id) throws KeyNotFoundException
	{
		if(!BOOK_TABLE.containsKey(id))
			throw new KeyNotFoundException("Object not found !");
		return BOOK_TABLE.get(id);
	}
}
