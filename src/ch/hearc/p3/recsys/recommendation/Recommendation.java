package ch.hearc.p3.recsys.recommendation;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.io.databases.BooksDatabase;
import ch.hearc.p3.recsys.io.databases.BooksFeaturesDatabase;
import ch.hearc.p3.recsys.io.databases.FeaturesDatabase;
import ch.hearc.p3.recsys.io.databases.RatingsDatabase;
import ch.hearc.p3.recsys.settings.SettingsRecommendation;
import ch.hearc.p3.recsys.utils.Pair;

public class Recommendation
{
	private Matrix2D<Integer, Integer, Integer>	r;
	private Matrix2D<Integer, String, Double>	f;
	private Matrix2D<Integer, String, Double>	p;
	private Matrix2D<Integer, String, Double>	ff;
	private Matrix1D<String, Integer>			uf;
	private Matrix1D<String, Double>			iuf;
	private Matrix2D<Integer, String, Double>	w;
	private Matrix2D<Integer, Integer, Double>	uu;

	private Set<String>							features;
	private Set<Integer>						users;
	private Set<Integer>						books;

	private static final double					EMPTY_CASE	= -1.0;

	public Recommendation()
	{
		features = new HashSet<String>(FeaturesDatabase.getAllFeatures());
		users = new HashSet<Integer>(RatingsDatabase.getAllUsers());
		books = new HashSet<Integer>(BooksDatabase.getAllBooks());

		r = new Matrix2D<Integer, Integer, Integer>(users, books, (int) (EMPTY_CASE));
		f = new Matrix2D<Integer, String, Double>(books, features, EMPTY_CASE);
		p = new Matrix2D<Integer, String, Double>(users, features, 0.0);
		ff = new Matrix2D<Integer, String, Double>(users, features, 0.0);
		uf = new Matrix1D<String, Integer>(features, 0);
		iuf = new Matrix1D<String, Double>(features, 0.0);
		w = new Matrix2D<Integer, String, Double>(users, features, EMPTY_CASE);
		uu = new Matrix2D<Integer, Integer, Double>(users, users, EMPTY_CASE);

		try
		{

			initializeBasicMatrices();
			initializeFrequencyMatrices();
			computeMatrices();
		} catch (Exception e)
		{
			System.err.println("Couldn't initialize the matrices !");
			System.exit(-1);
		}
	}

	private void initializeBasicMatrices() throws KeyNotFoundException
	{
		fillMatrixR();
		fillMatrixF();
		fillMatrixP();
	}

	private void initializeFrequencyMatrices()
	{

	}

	private void computeMatrices()
	{

	}

	private void fillMatrixR() throws KeyNotFoundException
	{
		for (Integer user : users)
			for (Entry<Integer, Integer> entry : RatingsDatabase.getUsersRatings(user))
				r.setItem(user, entry.getKey(), entry.getValue());
	}

	private void fillMatrixF() throws KeyNotFoundException
	{
		for (Integer book : books)
			for (Pair<String, Double> pair : BooksFeaturesDatabase.getFeaturesBook(book))
				f.setItem(book, pair.getKey(), pair.getValue());
	}

	private void fillMatrixP() throws KeyNotFoundException
	{
		for (Integer user : users)
			for (Integer book : books)
				if (r.getItem(user, book) != (int) EMPTY_CASE && r.getItem(user, book) > SettingsRecommendation.Pt)
					for (Pair<String, Double> pair : BooksFeaturesDatabase.getFeaturesBook(book))
						p.setItem(user, pair.getKey(), p.getItem(user, pair.getKey()) + f.getItem(book, pair.getKey()));
	}
}
