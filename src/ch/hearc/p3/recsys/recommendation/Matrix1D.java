package ch.hearc.p3.recsys.recommendation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ch.hearc.p3.recsys.exception.KeyNotFoundException;

public class Matrix1D<K, V>
{
	private Map<K, V>	matrix;
	private Set<K>		keys;

	public Matrix1D(Set<K> keys, V defaultVal)
	{
		this.keys = keys;
		matrix = new HashMap<K, V>();
		for (K key : keys)
			matrix.put(key, defaultVal);
	}

	public V getItem(K key) throws KeyNotFoundException
	{
		if (!matrix.containsKey(key))
			throw new KeyNotFoundException(key + " hasn't be found !");
		return matrix.get(key);
	}

	public void setItem(K key, V value) throws KeyNotFoundException
	{
		if (!matrix.containsKey(key))
			throw new KeyNotFoundException(key + " hasn't be found !");
		matrix.put(key, value);
	}

	public Set<K> getCols()
	{
		return keys;
	}
}
