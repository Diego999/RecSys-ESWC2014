package ch.hearc.p3.recsys.bookanalysis.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.Lemmatizer;
import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.settings.SettingsBookAnalysis;
import ch.hearc.p3.recsys.utils.Pair;

public class Subject extends Analyzer
{
	private static final String		SEPARATOR_LEVEL_1	= ",";
	private static final String		SEPARATOR_LEVEL_2	= ";";
	private static final String		SEPARATOR_LEVEL_3	= ":";

	private static final TypeData	TYPE				= TypeData.Subject;

	public Subject(List<Pair<Integer, Map<TypeData, List<String>>>> books)
	{
		super(books, TYPE);
	}

	@Override
	public List<Pair<Integer, List<Pair<String, Double>>>> computeFeatures()
	{
		List<Pair<Integer, List<Pair<String, Double>>>> out = new ArrayList<Pair<Integer, List<Pair<String, Double>>>>();
		for (Pair<Integer, List<String>> pair : data)
		{
			List<Pair<String, Double>> list = new ArrayList<Pair<String, Double>>();

			List<String> feature = new ArrayList<String>();
			for (String s : pair.getValue())
				for (String ss : s.split(SEPARATOR_LEVEL_1))
				{
					extract(feature, ss);
				}
			for (String s : feature)
			{
				List<String> lemmas = Lemmatizer.lemmatize(s.toLowerCase().trim());
				Lemmatizer.removeStopWords(lemmas);
				for(String lemma : lemmas)
					list.add(new Pair<String, Double>(lemma, SettingsBookAnalysis.ATTRIBUTE_WEIGHT_FACTOR.get(typeData)));
			}
			out.add(new Pair<Integer, List<Pair<String, Double>>>(pair.getKey(), list));
		}
		return out;
	}

	private static void extract(List<String> list, String string)
	{
		if (!string.contains(SEPARATOR_LEVEL_2) && !string.contains(SEPARATOR_LEVEL_3))
			list.add(string.toLowerCase().trim());
		else
			if (string.contains(SEPARATOR_LEVEL_2))
				for (String s : string.split(SEPARATOR_LEVEL_2))
				{
					extract(list, s);
				}
			else
			{
				int i = 1;
				for (String s : string.split(SEPARATOR_LEVEL_3))
					if (i++ % 2 == 0)
						list.add(s.toLowerCase().trim());
			}
	}
}
