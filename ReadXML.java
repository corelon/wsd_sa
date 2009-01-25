package gr.demokritos.iit.polarity;
//  Created by Nikos Tzanos on 12/1/08.
//  Copyright 2008 IIT Demokritos. All rights reserved.
//
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.*;

public class ReadXML {

    private File fnSentence;
    private File fnValence;
    
    private Hashtable<String,String> valenceTable = new Hashtable<String,String>();
	private Hashtable<String,Vector<String>> instanceTable = new Hashtable<String,Vector<String>>();


    public ReadXML(File fns, File fnv) {
        fnSentence = fns;
        fnValence = fnv;
        generateTables();
    }

    public ReadXML(String fns, String fnv) {
        fnSentence = new File(fns);
        fnValence = new File(fnv);
        generateTables();

    }
    
    private void generateTables() {
        try {
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document docSentence = db.parse(fnSentence);
            Document docValence = db.parse(fnValence);
            
            NodeList nls = docSentence.getElementsByTagName("instance");
            NodeList nlv = docValence.getElementsByTagName("valence");
            
			// Handle the INSTANCES xml file.

            for (int s = 0; s < nls.getLength(); s++) {

                Node tNode = nls.item(s);

                if (tNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element tElmnt = (Element) tNode;
                    String idSentence = tElmnt.getAttribute("id");
					Vector<String> valenceVector = new Vector<String>();
					if (tElmnt.hasAttribute("valence")) {
						String valence = tElmnt.getAttribute("valence");
						valenceVector.add(valence);
					}
					if (tElmnt.hasAttribute("valence1")) {
						String valence1 = tElmnt.getAttribute("valence1");
						valenceVector.add(valence1);
					}
					if (tElmnt.hasAttribute("valence2")) {
						String valence2 = tElmnt.getAttribute("valence2");
						valenceVector.add(valence2);
					}
					if (tElmnt.hasAttribute("valence3")) {
						String valence3 = tElmnt.getAttribute("valence3");
						valenceVector.add(valence3);
					}
					if (tElmnt.hasAttribute("valence_all")) {
						String valence_all = tElmnt.getAttribute("valence_all");
						valenceVector.add(valence_all);
					}
					
					if (!valenceVector.isEmpty()) {
						instanceTable.put(idSentence,valenceVector);
					}
                    
				}
			}
			
			// Handle the VALENCES xml file.
                    
			for (int x = 0; x < nlv.getLength(); x++) {
				Node utNode = nlv.item(x);
				
				if (utNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element utElmnt = (Element) utNode;
					
					String idValence = utElmnt.getTextContent();
					String valueValence = utElmnt.getAttribute("value");
					// Make the mapping with the annotation that is used by Vassiliki for reversers, intensifiers and diminishers.
					if (valueValence.equals("1")) {
						valueValence = "6";
					} else if (valueValence.equals("2")) {
						valueValence = "5";
					} else {
						valueValence = "4";
					}
					valenceTable.put(idValence, valueValence);
				}
			}
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Hashtable<String,String> getValences() {
        return valenceTable;
    }
	
	public Hashtable<String,Vector<String>> getInstances() {
		return instanceTable;
	}

}
