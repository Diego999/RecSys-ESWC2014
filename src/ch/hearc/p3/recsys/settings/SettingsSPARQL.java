package ch.hearc.p3.recsys.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.utils.Pair;

public class SettingsSPARQL
{
	public static final Map<String, String>											PREFIX_SPARQL;

	public static final String[]													TITLE_ORDER_PREFERENCE_SPARQL		= new String[] { "rdfs:label", "dbpprop:name", "foaf:name" };
	public static final Map<String, List<String>>									TITLE_ORDER_PREFERENCE_SPARQL_LINK;

	public static final String[]													SUBJECT_ORDER_PREFERENCE_SPARQL		= new String[] { "dcterms:subject", "dbpprop:subject" };
	public static final Map<String, List<String>>									SUBJECT_ORDER_PREFERENCE_SPARQL_LINK;

	public static final String[]													GENRE_ORDER_PREFERENCE_SPARQL		= new String[] { "dbpprop:genre" };
	public static final Map<String, List<String>>									GENRE_ORDER_PREFERENCE_SPARQL_LINK;

	public static final String[]													ABSTRACT_ORDER_PREFERENCE_SPARQL	= new String[] { "dbpedia-owl:abstract" };
	public static final Map<String, List<String>>									ABSTRACT_ORDER_PREFERENCE_SPARQL_LINK;

	public static final String[]													AUTHOR_ORDER_PREFERENCE_SPARQL		= new String[] { "dbpedia-owl:author", "dbpprop:author", "dbpedia-owl:birthName", "dbpprop:birthName" };
	public static final Map<String, List<String>>									AUTHOR_ORDER_PREFERENCE_SPARQL_LINK;

	public static final String														TAG_LANG							= "en";

	public static final Map<TypeData, Pair<String[], Map<String, List<String>>>>	ALL_DATA_TO_EXTRACT;

	static
	{
		// Nothing to add for them
		TITLE_ORDER_PREFERENCE_SPARQL_LINK = new HashMap<String, List<String>>();
		ABSTRACT_ORDER_PREFERENCE_SPARQL_LINK = new HashMap<String, List<String>>();

		PREFIX_SPARQL = new HashMap<String, String>();
		PREFIX_SPARQL.put("rdfs", "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");
		PREFIX_SPARQL.put("dbpprop", "PREFIX dbpprop: <http://dbpedia.org/property/>");
		PREFIX_SPARQL.put("foaf", "PREFIX foaf:    <http://xmlns.com/foaf/0.1/>");
		PREFIX_SPARQL.put("dcterms", "PREFIX dcterms: <http://purl.org/dc/terms/>");
		PREFIX_SPARQL.put("dbpedia-owl", "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>");
		PREFIX_SPARQL.put("skos", "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>");

		SUBJECT_ORDER_PREFERENCE_SPARQL_LINK = new HashMap<String, List<String>>();
		SUBJECT_ORDER_PREFERENCE_SPARQL_LINK.put(SUBJECT_ORDER_PREFERENCE_SPARQL[0], new ArrayList<String>());
		SUBJECT_ORDER_PREFERENCE_SPARQL_LINK.get(SUBJECT_ORDER_PREFERENCE_SPARQL[0]).add("skos:prefLabel");
		SUBJECT_ORDER_PREFERENCE_SPARQL_LINK.put(SUBJECT_ORDER_PREFERENCE_SPARQL[1], new ArrayList<String>());
		SUBJECT_ORDER_PREFERENCE_SPARQL_LINK.get(SUBJECT_ORDER_PREFERENCE_SPARQL[1]).add("rdfs:label");

		GENRE_ORDER_PREFERENCE_SPARQL_LINK = new HashMap<String, List<String>>();
		GENRE_ORDER_PREFERENCE_SPARQL_LINK.put(GENRE_ORDER_PREFERENCE_SPARQL[0], new ArrayList<String>());
		GENRE_ORDER_PREFERENCE_SPARQL_LINK.get(GENRE_ORDER_PREFERENCE_SPARQL[0]).add("dbpprop:name");
		GENRE_ORDER_PREFERENCE_SPARQL_LINK.get(GENRE_ORDER_PREFERENCE_SPARQL[0]).add("rdfs:label");

		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK = new HashMap<String, List<String>>();
		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK.put(AUTHOR_ORDER_PREFERENCE_SPARQL[0], new ArrayList<String>());
		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK.get(AUTHOR_ORDER_PREFERENCE_SPARQL[0]).add("dbpprop:name");
		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK.get(AUTHOR_ORDER_PREFERENCE_SPARQL[0]).add("rdfs:label");
		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK.get(AUTHOR_ORDER_PREFERENCE_SPARQL[0]).add("foaf:name");

		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK.put(AUTHOR_ORDER_PREFERENCE_SPARQL[1], new ArrayList<String>());
		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK.get(AUTHOR_ORDER_PREFERENCE_SPARQL[1]).add("dbpprop:name");
		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK.get(AUTHOR_ORDER_PREFERENCE_SPARQL[1]).add("rdfs:label");
		AUTHOR_ORDER_PREFERENCE_SPARQL_LINK.get(AUTHOR_ORDER_PREFERENCE_SPARQL[1]).add("foaf:name");

		ALL_DATA_TO_EXTRACT = new HashMap<TypeData, Pair<String[], Map<String, List<String>>>>();
		ALL_DATA_TO_EXTRACT.put(TypeData.Title, new Pair<String[], Map<String, List<String>>>(TITLE_ORDER_PREFERENCE_SPARQL, TITLE_ORDER_PREFERENCE_SPARQL_LINK));
		ALL_DATA_TO_EXTRACT.put(TypeData.Author, new Pair<String[], Map<String, List<String>>>(AUTHOR_ORDER_PREFERENCE_SPARQL, AUTHOR_ORDER_PREFERENCE_SPARQL_LINK));
		ALL_DATA_TO_EXTRACT.put(TypeData.Genre, new Pair<String[], Map<String, List<String>>>(GENRE_ORDER_PREFERENCE_SPARQL, GENRE_ORDER_PREFERENCE_SPARQL_LINK));
		ALL_DATA_TO_EXTRACT.put(TypeData.Subject, new Pair<String[], Map<String, List<String>>>(SUBJECT_ORDER_PREFERENCE_SPARQL, SUBJECT_ORDER_PREFERENCE_SPARQL_LINK));
		ALL_DATA_TO_EXTRACT.put(TypeData.Abstract, new Pair<String[], Map<String, List<String>>>(ABSTRACT_ORDER_PREFERENCE_SPARQL, ABSTRACT_ORDER_PREFERENCE_SPARQL_LINK));
	}
}
