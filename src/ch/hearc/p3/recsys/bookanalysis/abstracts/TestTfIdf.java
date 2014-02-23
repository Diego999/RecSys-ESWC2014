package ch.hearc.p3.recsys.bookanalysis.abstracts;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.hearc.p3.recsys.bookanalysis.abstracts.Document;
import ch.hearc.p3.recsys.bookanalysis.abstracts.Corpus;
import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.utils.Tools;

public class TestTfIdf
{

	@Test
	public void withStopWords()
	{
		Corpus corpus = new Corpus();
		corpus.addDocument(new Document(1, "because before test"));
		corpus.addDocument(new Document(2, "because before test"));
		corpus.addDocument(new Document(3, "because"));
		corpus.addDocument(new Document(4, "before test"));
		corpus.addDocument(new Document(5, "eat pear"));
		TfIdf tfIdf = new TfIdf(corpus);

		try
		{
			assertTrue(Tools.compare(tfIdf.getTfIdf("test", 1), 0.232990001));
			assertTrue(Tools.compare(tfIdf.getTfIdf("pear", 5), 0.349485002));
		} catch (KeyNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void withoutStopWords()
	{
		Corpus corpus = new Corpus();
		corpus.addDocument(new Document(1, "eaten pomme tomorrow"));
		corpus.addDocument(new Document(2, "drink water tomorrow"));
		corpus.addDocument(new Document(3, "yesterday buy bee"));
		corpus.addDocument(new Document(4, "absolutely bee"));
		corpus.addDocument(new Document(5, "eaten pear"));
		TfIdf tfIdf = new TfIdf(corpus);

		try
		{
			assertTrue(Tools.compare(tfIdf.getTfIdf("bee", 3), 0.116495001));
			assertTrue(Tools.compare(tfIdf.getTfIdf("pear", 5), 0.349485002));
		} catch (KeyNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
