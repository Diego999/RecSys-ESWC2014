package ch.hearc.p3.recsys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Lemmatizer {
	
	//Tags can be found on page 317 http://acl.ldc.upenn.edu/J/J93/J93-2004.pdf
	private static final List<String> AUTHORIZED_TAGS = Arrays.asList("NN","NNS","NNP","NNPS");
	private static List<String> STOPWORDS = null;
	
	private static StanfordCoreNLP pipeline = null;
	
	public static void initialize()
	{
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		pipeline = new StanfordCoreNLP(props);
		
		//This try-catch sucks a bit. We keep it like this to simplify
		try
		{
			FileReader fr = new FileReader("res/englishST.txt");
			BufferedReader bf = new BufferedReader(fr);
				
			String line;
			STOPWORDS = new ArrayList<String>();
			while((line = bf.readLine()) != null)
				STOPWORDS.add(line);
			
			bf.close();
			fr.close();
		}
		catch(Exception e)
		{
			System.err.println("There won't be a stopword list");
		}
	}
	
	public static List<Triple<String, String, String>> lemmatize(String text)
	{
		if(pipeline == null)
			initialize();
		
        Annotation document = new Annotation(removeStopWords(text));
        pipeline.annotate(document);

        List<Triple<String, String, String>> lemmas = new LinkedList<Triple<String, String, String>>();
        //We iterate over all the sentences
        for(CoreMap sentence: document.get(SentencesAnnotation.class)) {
        	//We analyze the sentence, token - PartOfSpeech - Lemma
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
            	String pos = token.get(PartOfSpeechAnnotation.class);
            	if(AUTHORIZED_TAGS.contains(pos))
                	lemmas.add(new Triple<String, String, String>(token.toString(), pos, token.get(LemmaAnnotation.class)));
            }
        }
        return lemmas;
	}
	
	private static String removeStopWords(String text)
	{
		if(STOPWORDS == null)
			return text;
		
		String[] words = text.split(" ");
		StringBuilder out = new StringBuilder();
		String delimiter = " ";
		for(String word : words)
			if(!STOPWORDS.contains(word))
			{
				out.append(word);
				out.append(delimiter);
			}
		return out.toString();
	}
	
	private Lemmatizer(){}
}
