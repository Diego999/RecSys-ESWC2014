package ch.hearc.p3.recsys.settings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.TypeData;

public class SettingsBookAnalysis
{
	public static final Map<TypeData, Double>	ATTRIBUTE_WEIGHT_FACTOR;

	// Tags can be found on page 317 http://acl.ldc.upenn.edu/J/J93/J93-2004.pdf
	public static final List<String>			AUTHORIZED_TAGS				= Arrays.asList("NN", "NNS", "NNP", "NNPS");

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

	private SettingsBookAnalysis()
	{
	}
}
