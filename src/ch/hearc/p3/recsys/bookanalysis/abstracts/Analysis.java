package ch.hearc.p3.recsys.bookanalysis.abstracts;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import ch.hearc.p3.recsys.exception.CorpusNotCompleteException;
import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.utils.Pair;

public class Analysis
{
	private Corpus corpus;
	private boolean isCorpusComplete;
	private TfIdf tfIdf;
	
	public Analysis()
	{
		corpus = new Corpus();
		isCorpusComplete = false;
		tfIdf = null;
	}
	
	public void addDocumentInCorpus(String text, int id)
	{
		corpus.addDocument(new Document(id, text));
	}
	
	public void setCorpusComplete()
	{
		isCorpusComplete = true;
		tfIdf = new TfIdf(corpus);
	}

    public double computeTfIdf(String term, int id) throws KeyNotFoundException
    {
        return tfIdf.getTfIdf(term, id);
    }
    
    // Actually, K = all
    public Pair<Map<Integer, Map<String, Double>>, Map<Integer, SortedSet<Entry<String, Double>>>> getTfIdfTheKMostImportant(int id) throws CorpusNotCompleteException
    {
        if(!isCorpusComplete)
            throw new CorpusNotCompleteException("The corpus is not complete ! Please call set_corpus_complete when you've filled it.");

        return tfIdf.getAllTfIdfSorted(id);
    }
}
