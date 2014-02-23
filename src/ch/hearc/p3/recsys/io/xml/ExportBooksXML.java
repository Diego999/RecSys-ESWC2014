package ch.hearc.p3.recsys.io.xml;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ch.hearc.p3.recsys.bookanalysis.TypeData;
import ch.hearc.p3.recsys.utils.Pair;

public class ExportBooksXML
{
	public static final String		ROOT_NAME		= "books";
	public static final String		BOOK_NAME		= "book";
	public static final String BOOK_ID = "id";
	public static final TypeData[]	BOOK_ATTRIBUTES	= { TypeData.Abstract, TypeData.Author, TypeData.Genre, TypeData.Subject, TypeData.Title };

	// Original code :
	// http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
	public static void exportBooksXML(String filepath, List<Pair<Integer, Map<TypeData, List<String>>>> books) throws ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(ROOT_NAME);
		doc.appendChild(rootElement);

		for (Pair<Integer, Map<TypeData, List<String>>> book : books)
		{
			Element bookElem = doc.createElement(BOOK_NAME);
			rootElement.appendChild(bookElem);

			Attr attr = doc.createAttribute(BOOK_ID);
			attr.setValue(book.getKey().toString());
			bookElem.setAttributeNode(attr);
			
			for (Entry<TypeData, List<String>> data : book.getValue().entrySet())
			{
				for (String val : data.getValue())
				{
					Element element = doc.createElement(data.getKey().toString().toLowerCase());
					element.appendChild(doc.createTextNode(val));
					bookElem.appendChild(element);
				}
			}
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filepath));

		transformer.transform(source, result);
	}
}
