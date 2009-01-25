import java.io.*;
import java.util.*;

public class NgramParser {
    
    private String file;
    
    private ArrayList<String> triplets = new ArrayList<String>();
    private ArrayList<String> polarities = new ArrayList<String>();
    
    public NgramParser(String f) {
        file = f;
        createNgramPolarityList();
    }
    
    public ArrayList<String> getTriplet() {
        return triplets;
    }
    
    public ArrayList<String> getPolarity() {
        return polarities;
    }
    
    private void createNgramPolarityList() {
        
        try {
        	LineNumberReader lnr = new LineNumberReader(new FileReader(file));      	
        	
        	String line = null;
        	while((line = lnr.readLine()) != null) {
        		int result = (lnr.getLineNumber() - 1) % 3;
        		if (result == 0) {
        			if (!(line.matches("-->[n|p][o|e][s|g]"))) {
                        Scanner s = new Scanner(line);
        				s.useDelimiter("#");        				
        				while(s.hasNext()) {
        					String s1 = s.next().trim();
        					String s2 = s.next().trim();
        					String s3 = s.next().trim();
        					String correctTriplet = s1 + "#" + s2 + "#" + s3;
        					triplets.add(correctTriplet);        					
        				}
        			} else {
                        polarities.add(line.substring(3));
        			}
        			        			        			
        		}
        	}
                    	
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
    }
}
