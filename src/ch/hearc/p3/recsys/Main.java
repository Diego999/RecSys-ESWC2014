package ch.hearc.p3.recsys;

import ch.hearc.p3.recsys.Triple;

public class Main {

	public static void main(String[] args) {
		String text = "Stanford University is located in California. It is a great university.";
        for(Triple<String, String, String> t : Lemmatizer.lemmatize(text))
        	System.out.println(t);
	}
}
