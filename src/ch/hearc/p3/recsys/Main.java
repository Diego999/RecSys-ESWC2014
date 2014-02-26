package ch.hearc.p3.recsys;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ch.hearc.p3.recsys.bookanalysis.Statistics;
import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.bookanalysis.analysis.Abstract;
import ch.hearc.p3.recsys.bookanalysis.analysis.Analyzer;
import ch.hearc.p3.recsys.bookanalysis.analysis.Author;
import ch.hearc.p3.recsys.bookanalysis.analysis.Genre;
import ch.hearc.p3.recsys.bookanalysis.analysis.Subject;
import ch.hearc.p3.recsys.bookanalysis.analysis.Title;
import ch.hearc.p3.recsys.io.Writer;
import ch.hearc.p3.recsys.settings.SettingsBookAnalysis;
import ch.hearc.p3.recsys.settings.SettingsFilePaths;
import ch.hearc.p3.recsys.utils.Pair;

public class Main
{

	public static void main(String[] args) throws Exception
	{

	}

	private static void exportFeatures(List<List<Pair<Integer, List<Pair<String, Double>>>>> books) throws FileNotFoundException, IOException
	{
		List<List<String>> data = new ArrayList<List<String>>();
		for (List<Pair<Integer, List<Pair<String, Double>>>> list : books)
			for (Pair<Integer, List<Pair<String, Double>>> pair : list)
				for (Pair<String, Double> feature : pair.getValue())
				{
					List<String> features = new ArrayList<String>();
					features.add(String.valueOf(pair.getKey()));
					features.add(feature.getKey());
					features.add(String.valueOf(feature.getValue()));
					data.add(features);
				}
		Writer.write(data, SettingsFilePaths.SEPARATOR_FEATURES, SettingsFilePaths.FILEPATH_FEATURES);
	}

	private static void plainOutput(List<List<Pair<Integer, List<Pair<String, Double>>>>> books) throws IOException, ParserConfigurationException, SAXException
	{
		long start = System.currentTimeMillis();
		display(books);
		System.out.println((new Statistics(books)).toString());
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
