package ch.hearc.p3.recsys.bookanalysis.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.hearc.p3.recsys.bookanalysis.Lemmatizer;
import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.bookanalysis.tfidf.Corpus;
import ch.hearc.p3.recsys.bookanalysis.tfidf.Document;
import ch.hearc.p3.recsys.bookanalysis.tfidf.TfIdf;
import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.settings.SettingsBookAnalysis;
import ch.hearc.p3.recsys.utils.Pair;

public class Abstract extends Analyzer
{
	private Corpus					corpus;
	private TfIdf					tfIdf;

	private static final TypeData	TYPE		= TypeData.Abstract;
	private static final String		SEPARATOR	= " ";

	public Abstract(List<Pair<Integer, Map<TypeData, List<String>>>> books)
	{
		super(books, TYPE);
		corpus = new Corpus();
		tfIdf = null;
	}

	@Override
	public List<Pair<Integer, List<Pair<String, Double>>>> computeFeatures()
	{
		Map<Integer, List<String>> lemmas = new HashMap<Integer, List<String>>();
		for (Pair<Integer, List<String>> attributes : data)
		{
			StringBuilder sb = new StringBuilder();
			for (String value : attributes.getValue())
			{
				sb.append(value);
				sb.append(SEPARATOR);
			}

			String text = sb.toString();
			corpus.addDocument(new Document(attributes.getKey(), text));
			List<String> lems = Lemmatizer.lemmatize(text);
			Lemmatizer.removeStopWords(lems);
			lemmas.put(attributes.getKey(), lems);
		}

		tfIdf = new TfIdf(corpus);

		List<Pair<Integer, List<Pair<String, Double>>>> out = new ArrayList<Pair<Integer, List<Pair<String, Double>>>>();
		for (Entry<Integer, List<String>> entry : lemmas.entrySet())
		{
			int id = entry.getKey();
			List<Pair<String, Double>> listTfIdf = new ArrayList<Pair<String, Double>>();
			for (String lemma : entry.getValue())
				try
				{
					listTfIdf.add(new Pair<String, Double>(lemma.toLowerCase().trim(), tfIdf.getTfIdf(lemma, id) * SettingsBookAnalysis.ATTRIBUTE_WEIGHT_FACTOR.get(typeData)));
				} catch (KeyNotFoundException e)
				{
					System.err.println("Error with book " + id + " and lemma " + lemma);
				}
			out.add(new Pair<Integer, List<Pair<String, Double>>>(id, listTfIdf));
		}

		return out;
	}
}
