package ch.hearc.p3.recsys;

import ch.hearc.p3.recsys.bookanalysis.Lemmatizer;
import ch.hearc.p3.recsys.exception.KeyNotFoundException;

public class Main
{

	public static void main(String[] args) throws KeyNotFoundException
	{
		String text = "Stanford University is located in California. It is a great university.";
 		for (String t : Lemmatizer.lemmatize(text))
 			System.out.println(t);
	}
}
