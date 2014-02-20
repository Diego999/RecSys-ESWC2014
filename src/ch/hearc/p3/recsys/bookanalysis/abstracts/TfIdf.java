package ch.hearc.p3.recsys.bookanalysis.abstracts;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.Map.Entry;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.utils.Pair;
import ch.hearc.p3.recsys.utils.Tools;

public class TfIdf
{
	private Corpus								corpus;
	private Map<Integer, Map<String, Double>>	wordTf;
	private Map<Integer, Map<String, Double>>	wordTfIdf;

	public TfIdf(Corpus corpus)
	{
		this.corpus = corpus;
		wordTf = new HashMap<Integer, Map<String, Double>>();
		wordTfIdf = new HashMap<Integer, Map<String, Double>>();
	}

	public double getTfIdf(String word, int id) throws KeyNotFoundException
	{
		if (!corpus.contains(id))
			throw new KeyNotFoundException(id + " hasn't be found in the corpus !");

		Document doc = corpus.getDocument(id);

		double tf = doc.getTf(word);
		double idf = corpus.getIdf(word);
		double out = tf * idf;

		if (!wordTf.containsKey(id))
		{
			wordTf.put(id, new HashMap<String, Double>());
			wordTfIdf.put(id, new HashMap<String, Double>());
		}
		wordTf.get(id).put(word, tf);
		wordTfIdf.get(id).put(word, out);

		return out;
	}

	public Pair<Map<Integer, Map<String, Double>>, Map<Integer, SortedSet<Map.Entry<String, Double>>>> getAllTfIdfSorted()
	{
		return getAllTfIdfSorted(0);
	}

	public Pair<Map<Integer, Map<String, Double>>, Map<Integer, SortedSet<Map.Entry<String, Double>>>> getAllTfIdfSorted(int id)
	{
		Map<Integer, Map<String, Double>> tfs = new HashMap<Integer, Map<String, Double>>();
		Map<Integer, SortedSet<Map.Entry<String, Double>>> finalIdfs = new HashMap<Integer, SortedSet<Map.Entry<String, Double>>>();

		if (id == 0)
		{
			for (int idd : wordTf.keySet())
			{
				putTfIdf(tfs, finalIdfs, idd);
			}
		} else
			if (tfs.containsKey(id))
			{
				putTfIdf(tfs, finalIdfs, id);
			} else
				return null;
		
		return new Pair<Map<Integer, Map<String, Double>>, Map<Integer, SortedSet<Map.Entry<String, Double>>>>(tfs, finalIdfs);
	}

	private void putTfIdf(Map<Integer, Map<String, Double>> tfs, Map<Integer, SortedSet<Map.Entry<String, Double>>> finalIdfs, int id)
	{
		tfs.put(id, new HashMap<String, Double>());
		Map<String, Double> idf = new HashMap<String, Double>();

		for (Entry<String, Double> entry : wordTf.get(id).entrySet())
		{
			tfs.get(id).put(entry.getKey(), entry.getValue());
			idf.put(entry.getKey(), wordTfIdf.get(id).get(entry.getKey()));
		}
		finalIdfs.put(id, Tools.entriesSortedByValues(idf));
	}
	
}
