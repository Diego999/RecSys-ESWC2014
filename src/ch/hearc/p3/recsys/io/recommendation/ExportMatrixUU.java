package ch.hearc.p3.recsys.io.recommendation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.io.Writer;
import ch.hearc.p3.recsys.io.databases.RatingsDatabase;
import ch.hearc.p3.recsys.recommendation.Matrix2D;
import ch.hearc.p3.recsys.settings.SettingsFilePaths;

public class ExportMatrixUU
{
	public static final String	SEPARATOR	= ";";

	public static void exportMatrixUU(Matrix2D<Integer, Integer, Double> matrix) throws KeyNotFoundException, FileNotFoundException, IOException
	{
		Set<Integer> users = matrix.getCols();
		List<List<String>> data = new ArrayList<List<String>>();
		
		List<String> usersKey = new ArrayList<String>();
		for(Integer user : RatingsDatabase.getAllUsers())
			usersKey.add(String.valueOf(user));
		data.add(usersKey);
		
		for (Integer u1 : users)
		{
			List<String> temp = new ArrayList<String>();
			for (Integer u2 : users)
				temp.add(String.valueOf(matrix.getItem(u1, u2)));
			data.add(temp);
		}
		Writer.write(data, SEPARATOR, SettingsFilePaths.FILEPATH_UU);
	}
}
