package ch.hearc.p3.recsys.bookanalysis.abstracts;

import java.util.ArrayList;
import java.util.List;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;

public class Corpus
{
	private List<Document> documents;
	
	public Corpus()
	{
		documents = new ArrayList<Document>();
	}
	
	public boolean contains(int id)
	{
		for(Document doc : documents)
			if(doc.equals(id))
				return true;
		return false;
	}
	
	public void addDocument(Document doc)
	{
		documents.add(doc);
	}
	
	public Document getDocument(int id) throws KeyNotFoundException
	{
		for(Document doc : documents)
			if(doc.equals(id))
				return doc;
		throw new KeyNotFoundException(id + " hasn't be found in Document ");
	}
	
	public double getIdf(String word)
	{
		double numberDocumentsWithTerm = 0;
		for(Document doc : documents)
			if(doc.getTf(word) != 0)
				++numberDocumentsWithTerm;
		return (numberDocumentsWithTerm != 0) ? Math.log10(documents.size())/numberDocumentsWithTerm : 1;
	}
}
