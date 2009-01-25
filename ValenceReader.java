import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.*;

/* This is the class that reads the file containing
 * the valence shifter dictionary.
 */

public class ValenceReader {
	private File valence;
	
	private HashMap<String,String> valenceMap = new HashMap<String,String>();
	
	public ValenceReader(String file) {
		valence = new File(file);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document docValence = db.parse(valence);
            
            NodeList nlv = docValence.getElementsByTagName("valence");

			for (int x = 0; x < nlv.getLength(); x++) {
				Node utNode = nlv.item(x);
				
				if (utNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element utElmnt = (Element) utNode;
					
					String idValence = utElmnt.getTextContent();
					String valueValence = utElmnt.getAttribute("value");
					
					//System.out.println(idValence + ":" + valueValence);
										
					valenceMap.put(idValence, valueValence);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getHmmValueFor(String valence) {
		return valenceMap.get(valence);
	}
	
	public static void main (String [] args) {
		ValenceReader vr = new ValenceReader(args[0]);
	}
}