package ch.hearc.p3.recsys.recommendation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;
import ch.hearc.p3.recsys.io.databases.BooksFeaturesDatabase;
import ch.hearc.p3.recsys.io.databases.FeaturesDatabase;
import ch.hearc.p3.recsys.io.databases.RatingsDatabase;
import ch.hearc.p3.recsys.settings.SettingsRecommendation;
import ch.hearc.p3.recsys.utils.Pair;
import ch.hearc.p3.recsys.utils.Tools;

public class Recommendation
{
	private Matrix2D<Integer, String, Double>	p;
	private Matrix2D<Integer, Integer, Double>	uu;

	private Set<String>							features;
	private Set<Integer>						users;

	private static final double					EMPTY_CASE	= -1.0;

	public Recommendation()
	{
		users = RatingsDatabase.getAllUsers();
		BooksFeaturesDatabase.initialize();
		features = FeaturesDatabase.getAllFeatures();

		p = new Matrix2D<Integer, String, Double>(users, features, EMPTY_CASE);

		try
		{
			fillMatrixP();
			computeW();
			computeUU();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.err.println("Couldn't initialize the matrices !");
			System.exit(-1);
		}
	}

	public Set<Integer> computeRecommendedBooks(int u1) throws KeyNotFoundException
	{
		// Compute neighbors
		TreeMap<Integer, Double> neighboors = new TreeMap<Integer, Double>();
		for (Integer u2 : users)
			if (uu.getItem(u1, u2) > EMPTY_CASE && !Tools.compare(uu.getItem(u1, u2), EMPTY_CASE))
				neighboors.put(u2, uu.getItem(u1, u2));

		Iterator<Entry<Integer, Double>> sortedNeighboors = Tools.valueIterator(neighboors);

		// Keep the K most similar neighbors
		int i = 0;
		Set<Integer> finalNeighboors = new HashSet<Integer>();
		while (sortedNeighboors.hasNext() && i < SettingsRecommendation.K_NEIGHBOOR)
			finalNeighboors.add(sortedNeighboors.next().getKey());

		// Search unrated books among the neighbors
		Set<Integer> unratedBooks = new HashSet<Integer>();
		Set<Integer> ratedBooks = RatingsDatabase.getRatedBooks(u1);

		for (Integer neighboor : finalNeighboors)
			for (Integer book : RatingsDatabase.getRatedBooks(neighboor))
				if (!ratedBooks.contains(book))
					unratedBooks.add(book);

		// Extract features from each book
		Map<Integer, Set<String>> featuresBooks = new HashMap<Integer, Set<String>>();
		for (Integer book : unratedBooks)
			featuresBooks.put(book, BooksFeaturesDatabase.getFeaturesBookTextOnly(book));

		// Compute features frequency
		Map<String, Integer> featuresFrequency = new HashMap<String, Integer>();
		for (Entry<Integer, Set<String>> entry : featuresBooks.entrySet())
			for (String f : entry.getValue())
				if (!featuresFrequency.containsKey(f))
					featuresFrequency.put(f, 1);
				else
					featuresFrequency.put(f, featuresFrequency.get(f) + 1);

		// Compute cumulative features frequency by books
		TreeMap<Integer, Double> cumulativeFeaturesFrequencyBook = new TreeMap<Integer, Double>();
		for (Integer book : unratedBooks)
		{
			cumulativeFeaturesFrequencyBook.put(book, 0.0);
			for (String f : featuresBooks.get(book))
				cumulativeFeaturesFrequencyBook.put(book, cumulativeFeaturesFrequencyBook.get(book) + featuresFrequency.get(f));
		}

		// We keep the K books with the highest cumulative features frequency
		Iterator<Entry<Integer, Double>> allRecommendedBooks = Tools.valueIterator(cumulativeFeaturesFrequencyBook);
		i = 0;
		Set<Integer> recommendedBooks = new HashSet<Integer>();
		while (allRecommendedBooks.hasNext() && i < SettingsRecommendation.K_RECOMMENDED)
			recommendedBooks.add(allRecommendedBooks.next().getKey());

		return recommendedBooks;
	}

	private void computeW() throws KeyNotFoundException
	{
		Matrix1D<String, Integer> uf = new Matrix1D<String, Integer>(features, 0);

		// There would be a better way, for example realize a double indexation
		// for the matrix but we don't have time and lot of memory
		for (Integer user : users)
			for (String feature : features)
				if (p.getItem(user, feature) > EMPTY_CASE && !Tools.compare(p.getItem(user, feature), EMPTY_CASE))
					uf.setItem(feature, uf.getItem(feature) + 1);

		Matrix1D<String, Double> iuf = new Matrix1D<String, Double>(features, 0.0);
		for (String feature : features)
			iuf.setItem(feature, Math.log10((double) (users.size()) / (double) (uf.getItem(feature))));

		for (Integer user : users)
			for (String feature : features)
				if (p.getItem(user, feature) > EMPTY_CASE && !Tools.compare(p.getItem(user, feature), EMPTY_CASE))
					p.setItem(user, feature, p.getItem(user, feature) * iuf.getItem(feature));
	}

	private void computeUU() throws KeyNotFoundException
	{
		uu = new Matrix2D<Integer, Integer, Double>(users, users, EMPTY_CASE);
		// This is a symmetric matrix so we do not need to iterate over all the
		// combination. We use start and start2 to divide by 2 the time.
		int start = 0;
		for (Integer u1 : users)
		{
			System.out.println(start);
			int start2 = 0;
			for (Integer u2 : users)
			{
				if (start2 < start)
				{
					++start2;
					continue;
				}
				if (u1.equals(u2))
					continue;
				Set<String> featuresIntersect = new HashSet<String>();

				for (String f : features)
					if (p.getItem(u1, f) > EMPTY_CASE && !Tools.compare(p.getItem(u1, f), EMPTY_CASE) && p.getItem(u2, f) > EMPTY_CASE && !Tools.compare(p.getItem(u2, f), EMPTY_CASE))
						featuresIntersect.add(f);

				if (featuresIntersect.size() > 0)
				{
					double value = 0.0, divisorLeft = 0.0, divisorRight = 0.0;
					;
					for (String f : featuresIntersect)
					{
						value += p.getItem(u1, f) * p.getItem(u2, f);
						divisorLeft += Math.pow(p.getItem(u1, f), 2);
						divisorRight += Math.pow(p.getItem(u2, f), 2);
					}

					value /= (Math.sqrt(divisorLeft) * Math.sqrt(divisorRight));
					uu.setItem(u1, u2, value);
					uu.setItem(u2, u1, value);
				}
			}
			++start;
		}
		p = null; // Useless when we have U
	}

	private void fillMatrixP() throws KeyNotFoundException
	{
		for (Integer user : RatingsDatabase.getAllUsers())
			for (Entry<Integer, Integer> entry : RatingsDatabase.getUsersRatings(user))
			{
				int book = entry.getKey();
				if (RatingsDatabase.getRating(user, book) != (int) EMPTY_CASE && RatingsDatabase.getRating(user, book) > SettingsRecommendation.Pt)
					for (Pair<String, Double> pair : BooksFeaturesDatabase.getFeaturesBook(book))
						p.setItem(user, pair.getKey(), p.getItem(user, pair.getKey()) + BooksFeaturesDatabase.getWeight(book, pair.getKey()));
			}
	}
}
