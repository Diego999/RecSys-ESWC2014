package ch.hearc.p3.recsys.bookanalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import ch.hearc.p3.recsys.io.Reader;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Lemmatizer
{

	// Tags can be found on page 317 http://acl.ldc.upenn.edu/J/J93/J93-2004.pdf
	private static final List<String>	AUTHORIZED_TAGS		= Arrays.asList("NN", "NNS", "NNP", "NNPS");
	private static final List<String>	STOPWORDS;
	private static final String			FILEPATH_STOPWORD	= "res/englishST.txt";
	private static final String			SEPARATOR			= "\n";

	private static StanfordCoreNLP		pipeline;

	static
	{
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		pipeline = new StanfordCoreNLP(props);

		STOPWORDS = new ArrayList<String>();
		try
		{
			for (String[] strings : Reader.readTextFile(FILEPATH_STOPWORD, SEPARATOR))
				for (String string : strings)
					STOPWORDS.add(string);
		} catch (Exception e)
		{
			System.err.println("There won't be a stopword list");
		}
	}

	public static List<String> lemmatize(String text)
	{
		return lemmatize(text, false);
	}
	
	public static List<String> lemmatize(String text, boolean allTag)
	{
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		List<String> lemmas = new LinkedList<String>();
		// We iterate over all the sentences
		for (CoreMap sentence : document.get(SentencesAnnotation.class))
		{
			// We analyze the sentence, token - PartOfSpeech - Lemma
			for (CoreLabel token : sentence.get(TokensAnnotation.class))
			{
				String pos = token.get(PartOfSpeechAnnotation.class);
				if (allTag || AUTHORIZED_TAGS.contains(pos))
					lemmas.add(token.get(LemmaAnnotation.class));
			}
		}
		return lemmas;
	}

	public static void removeStopWords(List<String> texts)
	{
		if (STOPWORDS != null)
		{
			for(String stop : STOPWORDS)
				texts.remove(stop);
		}
	}

	private Lemmatizer()
	{
	}
}