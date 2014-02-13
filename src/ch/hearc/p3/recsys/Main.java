package ch.hearc.p3.recsys;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;
import ch.hearc.p3.recsys.Triple;

public class Main {

	public static void main(String[] args) throws IOException {
		String text = "Stanford University is located in California. It is a great university.";
        for(Triple<String, String, String> t : Lemmatizer.lemmatize(text))
        	System.out.println(t);
	}
}
