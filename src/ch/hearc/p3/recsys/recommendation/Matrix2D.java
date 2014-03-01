package ch.hearc.p3.recsys.recommendation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;

public class Matrix2D<K1, K2, V>
{
	private Map<K1, Map<K2, V>>	matrix;
	private Set<K1>				keys1;
	private Set<K2>				keys2;

	public Matrix2D(Set<K1> keys1, Set<K2> keys2, V defaultValue)
	{
		this.keys1 = keys1;
		this.keys2 = keys2;

		matrix = new HashMap<K1, Map<K2, V>>();
		for (K1 k1 : keys1)
		{
			matrix.put(k1, new HashMap<K2, V>());
			for (K2 k2 : keys2)
				matrix.get(k1).put(k2, defaultValue);
		}
	}

	public V getItem(K1 k1, K2 k2) throws KeyNotFoundException
	{
		if (!matrix.containsKey(k1))
			throw new KeyNotFoundException(k1 + " hasn't be found !");
		if (!matrix.get(k1).containsKey(k2))
			throw new KeyNotFoundException("(" + k1 + ", " + k2 + ") hasn't be found !");
		return matrix.get(k1).get(k2);
	}

	public void setItem(K1 k1, K2 k2, V v) throws KeyNotFoundException
	{
		if (!matrix.containsKey(k1))
			throw new KeyNotFoundException(k1 + " hasn't be found !");
		if (!matrix.get(k1).containsKey(k2))
			throw new KeyNotFoundException("(" + k1 + ", " + k2 + ") hasn't be found !");
		matrix.get(k1).put(k2, v);
	}

	public Set<K1> getCols()
	{
		return keys1;
	}

	public Set<K2> getRows()
	{
		return keys2;
	}
}
