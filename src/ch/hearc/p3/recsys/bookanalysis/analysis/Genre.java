package ch.hearc.p3.recsys.bookanalysis.analysis;

import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.utils.Pair;

public class Genre extends AnalyzerSimple
{
	private static final TypeData	TYPE		= TypeData.Genre;
	
	public Genre(List<Pair<Integer, Map<TypeData, List<String>>>> books)
	{
		super(books, TYPE);
	}
}
