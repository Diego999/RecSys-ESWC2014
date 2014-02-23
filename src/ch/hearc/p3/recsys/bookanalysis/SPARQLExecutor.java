package ch.hearc.p3.recsys.bookanalysis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.exception.AttributeFormIncorrectException;
import ch.hearc.p3.recsys.exception.PrefixUnknownException;
import ch.hearc.p3.recsys.settings.SettingsSPARQL;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class SPARQLExecutor
{
	private static final String	END_POINT						= "http://dbpedia.org/sparql";
	private static final String	GRAPH_URI						= "http://dbpedia.org";
	private static final String	DEFAULT_ATTRIBUTE_NAME			= "attribute";
	private static final String	DEFAULT_ATTRIBUTE_NAME_SPARQL	= "?" + DEFAULT_ATTRIBUTE_NAME;
	private static final String	START_URI						= "<";
	private static final String	END_URI							= ">";
	private static final String	SEPARATOR_LINE					= "\n";
	private static final String	SEPARATOR_TERM					= " ";
	private static final String	SELECT							= "SELECT ";
	private static final String	WHERE							= " where { " + SEPARATOR_LINE;
	private static final String	END_BLOC						= "}";
	private static final String	END_OF_LINE						= "." + SEPARATOR_LINE;
	private static final String	LANGUAGE_TAG_SYMBOLE			= "@";

	public static List<String> exec(String resource, String attribute, Map<String, List<String>> attributesForUrl) throws AttributeFormIncorrectException, PrefixUnknownException
	{

		StringBuilder sb = new StringBuilder();
		sb.append(loadPrefixes(attribute));
		sb.append(SEPARATOR_LINE);
		sb.append(SELECT);
		sb.append(DEFAULT_ATTRIBUTE_NAME_SPARQL);
		sb.append(WHERE);
		sb.append(START_URI);
		sb.append(resource);
		sb.append(END_URI);
		sb.append(SEPARATOR_TERM);
		sb.append(attribute);
		sb.append(SEPARATOR_TERM);
		sb.append(DEFAULT_ATTRIBUTE_NAME_SPARQL);
		sb.append(END_OF_LINE);
		sb.append(END_BLOC);

		Query query = QueryFactory.create(sb.toString());

		// System.out.println("\n" + query.toString() + "\n");

		return searchContents(attribute, attributesForUrl, searchEntries(query));
	}

	private static List<String> searchContents(String attribute, Map<String, List<String>> attributesForUrl, List<String> entries) throws AttributeFormIncorrectException, PrefixUnknownException
	{
		List<String> output = new ArrayList<String>();
		for (String entry : entries)
		{
			// The Silver Kiss@en
			if (entry.contains(LANGUAGE_TAG_SYMBOLE))
			{
				String[] params = entry.split(LANGUAGE_TAG_SYMBOLE);
				if (params[1].equals(SettingsSPARQL.TAG_LANG))
					output.add(params[0]);
			} else
			{
				// If a new available resource
				try
				{
					new URL(entry);
					for (String at : attributesForUrl.get(attribute))
					{
						List<String> out = exec(entry, at, null); // There is
																	// only 1
																	// depth max

						if (out.size() > 0)
						{
							output.addAll(out);
							break;
						}
					}
				} catch (MalformedURLException e)
				{
					// Plain-text
					output.add(entry);
				} catch (Exception e)
				{
					// Nothing to do, you are higher than 1 depth
				}
			}
		}
		return output;
	}

	private static List<String> searchEntries(Query query)
	{
		QueryExecution qexec = null;
		List<String> output = new ArrayList<String>();
		try
		{
			qexec = QueryExecutionFactory.sparqlService(END_POINT, query, GRAPH_URI);
			ResultSet results = qexec.execSelect();

			while (results.hasNext())
			{
				QuerySolution qs = results.next();

				if (qs.contains(DEFAULT_ATTRIBUTE_NAME))
				{
					RDFNode prop = qs.get(DEFAULT_ATTRIBUTE_NAME);
					output.add(prop.toString());
				}
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		} finally
		{
			qexec.close();
		}
		return output;
	}

	private static String loadPrefixes(String attribute) throws AttributeFormIncorrectException, PrefixUnknownException
	{
		if (!attribute.contains(":"))
			throw new AttributeFormIncorrectException();

		String prefixName = attribute.split(":")[0];

		if (!SettingsSPARQL.PREFIX_SPARQL.containsKey(prefixName))
			throw new PrefixUnknownException();

		return SettingsSPARQL.PREFIX_SPARQL.get(prefixName);
	}
}
