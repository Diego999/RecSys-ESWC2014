//This file is from https://github.com/vostuni/SparqlClient for the challenge

package ch.hearc.p3.recsys;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class QueryExecutor
{

	private String	resource;
	String			property;
	private String	endpoint	= "http://dbpedia.org/sparql";
	private String	graphURI	= "http://dbpedia.org";

	public void exec(String resource)
	{
		this.resource = resource;
		Query query;
		String q;

		String resourceQuery = "<" + resource + ">";
		// creation of a sparql query for getting all the resources connected to
		// resource
		// the FILTER isIRI is used to get only resources, so this query
		// descards any literal or data-type

		q = "PREFIX dbpprop: <http://dbpedia.org/property/> select ?name " + "where { " + "<http://dbpedia.org/resource/The_Silver_Kiss> dbpprop:name ?name.FILTER (LANG(?name) = \"en\")" + "}";
		try
		{
			query = QueryFactory.create(q);

			execQuery(query);
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	private void execQuery(Query query)
	{

		System.out.println("executing query  : " + query.toString());

		QueryExecution qexec = null;
		try
		{
			if (graphURI == null)
				qexec = QueryExecutionFactory.sparqlService(endpoint, query);
			else
				qexec = QueryExecutionFactory.sparqlService(endpoint, query, graphURI);

			ResultSet results = qexec.execSelect();
			// ResultSetFormatter.out(System.out, results, query) ;
			QuerySolution qs;

			RDFNode node, prop;

			String n = "", p = this.property;

			System.out.println("Results:");
			// iteration over the resultset
			while (results.hasNext())
			{

				qs = results.next();

				if (qs.contains("name"))
				{
					prop = qs.get("name");
					p = prop.toString();

					p = p.substring(0, p.indexOf("@"));
					System.out.println(p);
				}
			}

		} finally
		{
			qexec.close();
		}

	}

}
