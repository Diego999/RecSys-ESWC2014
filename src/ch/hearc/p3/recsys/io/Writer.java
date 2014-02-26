package ch.hearc.p3.recsys.io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer
{
	public static void write(List<List<String>> lists, String separator, String filename) throws FileNotFoundException, IOException
	{
		FileWriter fw = null;
		BufferedWriter bw = null;

		try
		{
			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);

			for (List<String> list : lists)
			{
				StringBuilder sb = new StringBuilder();
				for (String string : list)
				{
					sb.append(string);
					sb.append(separator);
				}
				sb.append("\n");
				bw.write(sb.toString());
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
}
