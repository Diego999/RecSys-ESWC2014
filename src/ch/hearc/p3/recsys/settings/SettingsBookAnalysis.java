package ch.hearc.p3.recsys.settings;

import java.util.HashMap;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.TypeData;

public class SettingsBookAnalysis
{
	public static final Map<TypeData, Double>	ATTRIBUTE_WEIGHT_FACTOR;

	private static final double					ATTRIBUTE_WEIGHT_TITLE		= 1.0;
	private static final double					ATTRIBUTE_WEIGHT_AUTHOR		= 1.0;
	private static final double					ATTRIBUTE_WEIGHT_SUBJECT	= 1.0;
	private static final double					ATTRIBUTE_WEIGHT_GENRE		= 1.0;
	private static final double					ATTRIBUTE_WEIGHT_ABSTRACT	= 1.0;

	static
	{
		ATTRIBUTE_WEIGHT_FACTOR = new HashMap<TypeData, Double>();
		ATTRIBUTE_WEIGHT_FACTOR.put(TypeData.Abstract, ATTRIBUTE_WEIGHT_ABSTRACT);
		ATTRIBUTE_WEIGHT_FACTOR.put(TypeData.Author, ATTRIBUTE_WEIGHT_AUTHOR);
		ATTRIBUTE_WEIGHT_FACTOR.put(TypeData.Genre, ATTRIBUTE_WEIGHT_GENRE);
		ATTRIBUTE_WEIGHT_FACTOR.put(TypeData.Subject, ATTRIBUTE_WEIGHT_SUBJECT);
		ATTRIBUTE_WEIGHT_FACTOR.put(TypeData.Title, ATTRIBUTE_WEIGHT_TITLE);
	}
}
