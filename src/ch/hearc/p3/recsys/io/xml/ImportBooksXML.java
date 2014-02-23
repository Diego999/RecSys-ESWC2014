package ch.hearc.p3.recsys.io.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.utils.Pair;

public class ImportBooksXML
{
	// Original code :
	// http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
	public static List<Pair<Integer, Map<TypeData, List<String>>>> importBooksXML(String filepath) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile = new File(filepath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName(ExportBooksXML.BOOK_NAME);
		List<Pair<Integer, Map<TypeData, List<String>>>> data = new ArrayList<Pair<Integer, Map<TypeData, List<String>>>>();

		for (int i = 0; i < nList.getLength(); ++i)
		{
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Map<TypeData, List<String>> attributes = new HashMap<TypeData, List<String>>();
				for (TypeData td : ExportBooksXML.BOOK_ATTRIBUTES)
					attributes.put(td, new ArrayList<String>());

				Node child = nNode.getFirstChild();

				while (child != null)
				{
					attributes.get(TypeData.valueOf(inverseFunctionNameTag(child.getNodeName()))).add(child.getTextContent());
					child = child.getNextSibling();
				}
				data.add(new Pair<Integer, Map<TypeData, List<String>>>(Integer.valueOf(((Element) nNode).getAttribute(ExportBooksXML.BOOK_ID)), attributes));
			}
		}
		return data;
	}

	private static String inverseFunctionNameTag(String tag)
	{
		return Character.toUpperCase(tag.charAt(0)) + tag.substring(1);
	}
}