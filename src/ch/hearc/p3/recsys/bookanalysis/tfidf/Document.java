package ch.hearc.p3.recsys.bookanalysis.tfidf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.Lemmatizer;

public class Document
{
	private int						id;
	private Map<String, Integer>	statistics;
	private double					allOccurances;

	public Document(int id, String text)
	{
		this.id = id;
		statistics = new HashMap<String, Integer>();
		allOccurances = 0;
		addText(text);
	}

	public void addText(String text)
	{
		List<String> lemmas = Lemmatizer.lemmatize(text, true);
		Lemmatizer.removeStopWords(lemmas);

		for (String lemma : lemmas)
		{
			if (statistics.containsKey(lemma))
				statistics.put(lemma, statistics.get(lemma) + 1);
			else
				statistics.put(lemma, 1);
			++allOccurances;
		}
	}

	public double getTf(String word)
	{
		if (!statistics.containsKey(word))
			return 0.0;
		else
		{
			return statistics.get(word) / allOccurances;
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Integer)
			return ((int) obj) == id;
		return false;
	}

	@Override
	public int hashCode()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return String.valueOf(id);
	}

	public Map<String, Integer> getStatistics()
	{
		return statistics;
	}

	public int getId()
	{
		return id;
	}
}
