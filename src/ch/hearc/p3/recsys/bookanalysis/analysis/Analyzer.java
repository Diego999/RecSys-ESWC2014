package ch.hearc.p3.recsys.bookanalysis.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.utils.Pair;

public abstract class Analyzer
{
	protected TypeData							typeData;
	protected List<Pair<Integer, List<String>>>	data;

	public Analyzer(List<Pair<Integer, Map<TypeData, List<String>>>> books, TypeData typeData)
	{
		this.typeData = typeData;
		data = extractData(books, typeData);
	}

	public abstract List<Pair<Integer, List<Pair<String, Double>>>> computeFeatures();

	private static List<Pair<Integer, List<String>>> extractData(List<Pair<Integer, Map<TypeData, List<String>>>> books, TypeData typeData)
	{
		List<Pair<Integer, List<String>>> out = new ArrayList<Pair<Integer, List<String>>>();

		for (Pair<Integer, Map<TypeData, List<String>>> pair : books)
		{
			List<String> data = new ArrayList<String>();
			for (String string : pair.getValue().get(typeData))
			{
				data.add(string.trim());
			}
			out.add(new Pair<Integer, List<String>>(pair.getKey(), data));
		}

		return out;
	}
}
