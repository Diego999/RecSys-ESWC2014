package ch.hearc.p3.recsys.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Tools
{
	public static boolean compare(double a, double b, double epsilon)
	{
		return Math.abs(a - b) < epsilon;
	}

	public static boolean compare(double a, double b)
	{
		return compare(a, b, 1e-6);
	}

	// Source https://stackoverflow.com/questions/2864840/treemap-sort-by-value
	public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map)
	{
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(new Comparator<Map.Entry<K, V>>()
		{
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2)
			{
				return -e1.getValue().compareTo(e2.getValue()); // Order desc
			}
		});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	// Source
	// https://stackoverflow.com/questions/13944353/how-to-sort-treemap-by-value-not-by-key
	public static Iterator<Entry<Integer, Double>> valueIterator(TreeMap<Integer, Double> map)
	{
		Set<Entry<Integer, Double>> set = new TreeSet<Entry<Integer, Double>>(new Comparator<Map.Entry<Integer, Double>>()
		{
			@Override
			public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2)
			{
				return o1.getValue().compareTo(o2.getValue()) < 0 ? 1 : -1;
			}
		});
		set.addAll(map.entrySet());
		return set.iterator();
	}
}
