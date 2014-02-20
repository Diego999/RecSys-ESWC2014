package ch.hearc.p3.recsys.io.databases;

import java.util.HashSet;
import java.util.Set;

public class FeaturesDatabase
{
	private static Set<String>	FEATURE_TABLE;

	static
	{
		FEATURE_TABLE = new HashSet<String>();
	}

	public static boolean contains(String feature)
	{
		return FEATURE_TABLE.contains(feature);
	}
	
	public static void addFeatures(String feature)
	{
		FEATURE_TABLE.add(feature);
	}

	public static Set<String> getAllFeatures()
	{
		return FEATURE_TABLE;
	}
}
