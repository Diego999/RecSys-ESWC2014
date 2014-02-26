package ch.hearc.p3.recsys.bookanalysis.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.settings.SettingsBookAnalysis;
import ch.hearc.p3.recsys.utils.Pair;

public abstract class AnalyzerSimple extends Analyzer
{
	private static final String REMOVE_COMA = ",";
	
	public AnalyzerSimple(List<Pair<Integer, Map<TypeData, List<String>>>> books, TypeData typeData)
	{
		super(books, typeData);
	}

	public List<Pair<Integer, List<Pair<String, Double>>>> computeFeatures()
	{
		List<Pair<Integer, List<Pair<String, Double>>>> out = new ArrayList<Pair<Integer, List<Pair<String, Double>>>>();
		for (Pair<Integer, List<String>> pair : data)
		{
			List<Pair<String, Double>> list = new ArrayList<Pair<String, Double>>();

			List<String> feature = new ArrayList<String>();
			for (String s : pair.getValue())
				feature.add(s);

			for (String s : feature)
			{
				list.add(new Pair<String, Double>(s.toLowerCase().trim().replace(REMOVE_COMA, ""), SettingsBookAnalysis.ATTRIBUTE_WEIGHT_FACTOR.get(typeData)));
			}
			out.add(new Pair<Integer, List<Pair<String, Double>>>(pair.getKey(), list));
		}
		return out;
	}
}
