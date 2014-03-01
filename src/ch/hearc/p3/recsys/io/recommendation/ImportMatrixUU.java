package ch.hearc.p3.recsys.io.recommendation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.io.Reader;
import ch.hearc.p3.recsys.recommendation.Matrix2D;
import ch.hearc.p3.recsys.settings.SettingsFilePaths;

public class ImportMatrixUU
{
	public static void importMatrixUU(Matrix2D<Integer, Integer, Double> matrix) throws FileNotFoundException, IOException, NumberFormatException, KeyNotFoundException
	{
		List<String[]> values = Reader.readTextFile(SettingsFilePaths.FILEPATH_UU, ExportMatrixUU.SEPARATOR);
		Iterator<String[]> it = values.iterator();

		List<Integer> users = new ArrayList<Integer>();
		for (String s : it.next())
			users.add(Integer.valueOf(s));

		for (Integer u1 : users)
		{
			ListIterator<Integer> itU2 = users.listIterator();
			for (String val : it.next())
				matrix.setItem(u1, itU2.next(), Double.valueOf(val));
		}
	}
}
