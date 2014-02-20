package ch.hearc.p3.recsys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.hearc.p3.recsys.bookanalysis.SPARQLExecutor;
import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.io.databases.BooksDatabase;
import ch.hearc.p3.recsys.utils.Pair;

public class Main
{
	
	public static void main(String[] args) throws Exception
	{
		int i = 1;
		for (Pair<String, String> pair : BooksDatabase.getAllBooks())
		{
			System.out.println(i + " " + pair.getKey());
			for (Entry<TypeData, Pair<String[], Map<String, List<String>>>> entry : Settings.ALL_DATA_TO_EXTRACT.entrySet())
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
				System.out.println(entry.getKey().toString());
				System.out.println(f);
			}
			System.out.println();
			i+=1;
		}
	}
}
