package ch.hearc.p3.recsys.io.recommendation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.io.Writer;
import ch.hearc.p3.recsys.io.databases.RatingsDatabase;
import ch.hearc.p3.recsys.recommendation.Recommendation;
import ch.hearc.p3.recsys.settings.SettingsFilePaths;
import ch.hearc.p3.recsys.settings.SettingsRecommendation;

public class ExportResults
{
	public static final String	SEPARATOR	= "\t";

	public static void exportResults(Recommendation rec) throws KeyNotFoundException, FileNotFoundException, IOException
	{
		List<List<String>> data = new ArrayList<List<String>>();

		for (Integer user : RatingsDatabase.getAllUsers())
		{
			int i = 1;
			List<Integer> books = rec.computeRecommendedBooks(user);
			if (books.size() < SettingsRecommendation.K_RECOMMENDED)
			{
				System.err.println("There isn't enough books !");
				System.exit(-1);
			}
			for (Integer book : books)
			{
				List<String> line = new ArrayList<String>();
				line.add(String.valueOf(user));
				line.add(String.valueOf(book));
				line.add(String.valueOf(i++));
				data.add(line);
			}
		}

		Writer.write(data, SEPARATOR, SettingsFilePaths.FILEPATH_RESULT);
	}
}
