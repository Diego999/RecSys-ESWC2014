package ch.hearc.p3.recsys.bookanalysis.tfidf;

import java.util.HashMap;
import java.util.Map;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;

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
}
