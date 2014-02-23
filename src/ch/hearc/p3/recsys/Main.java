package ch.hearc.p3.recsys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jdk.internal.jfr.events.FileWriteEvent;
import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.bookanalysis.analysis.Abstract;
import ch.hearc.p3.recsys.bookanalysis.analysis.Analyzer;
import ch.hearc.p3.recsys.bookanalysis.analysis.Author;
import ch.hearc.p3.recsys.bookanalysis.analysis.Genre;
import ch.hearc.p3.recsys.bookanalysis.analysis.Subject;
import ch.hearc.p3.recsys.bookanalysis.analysis.Title;
import ch.hearc.p3.recsys.io.xml.ImportBooksXML;
import ch.hearc.p3.recsys.settings.SettingsBookAnalysis;
import ch.hearc.p3.recsys.settings.SettingsFilePaths;
import ch.hearc.p3.recsys.utils.Pair;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();
		display(executeAnalyzer(createAnalyzer(ImportBooksXML.importBooksXML(SettingsFilePaths.BOOKS_FILE))));
		System.out.println("Total time : " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");
	}

	private static void display(List<List<Pair<Integer, List<Pair<String, Double>>>>> features) throws IOException
	{
		FileWriter fw = null;
		BufferedWriter bw = null;

		try
		{
			fw = new FileWriter("output.txt");
			bw = new BufferedWriter(fw);

			for (List<Pair<Integer, List<Pair<String, Double>>>> list : features)
			{
				bw.write("One of the category Subject, Author, Genre, Title or Abstract\n");
				for (Pair<Integer, List<Pair<String, Double>>> pair : list)
				{
					bw.write("Book : " + pair.getKey() + "\n");
					for (Pair<String, Double> feature : pair.getValue())
						bw.write(feature + "\n");
					bw.write("\n");
				}
				bw.write("\n");
			}
		} catch (Exception e)
		{
			System.err.println("Error during the writing process");
		} finally
		{
			bw.close();
			fw.close();
		}
	}

	private static Analyzer[] createAnalyzer(List<Pair<Integer, Map<TypeData, List<String>>>> books)
	{
		Analyzer[] analyzer = new Analyzer[SettingsBookAnalysis.ATTRIBUTE_WEIGHT_FACTOR.size()];
		analyzer[0] = new Abstract(books);
		analyzer[1] = new Author(books);
		analyzer[2] = new Genre(books);
		analyzer[3] = new Title(books);
		analyzer[4] = new Subject(books);
		return analyzer;
	}

	private static List<List<Pair<Integer, List<Pair<String, Double>>>>> executeAnalyzer(Analyzer[] analyzer)
	{
		List<List<Pair<Integer, List<Pair<String, Double>>>>> features = new ArrayList<List<Pair<Integer, List<Pair<String, Double>>>>>();
		for (Analyzer an : analyzer)
			features.add(an.computeFeatures());
		return features;
	}
}
