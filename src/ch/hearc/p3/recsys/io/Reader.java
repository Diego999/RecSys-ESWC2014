package ch.hearc.p3.recsys.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader
{
	public static List<String[]> readTextFile(String filePath, String separator) throws FileNotFoundException, IOException
	{
		FileReader fr = new FileReader(filePath);
		BufferedReader bf = new BufferedReader(fr);

		String line;
		List<String[]> output = new ArrayList<String[]>();
		while ((line = bf.readLine()) != null)
			output.add(line.split(separator));

		bf.close();
		fr.close();

		return output;
	}
}
