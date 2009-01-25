import java.io.*;
import java.util.*;

public class CountNgrams {
    
    private String file;
    
    private ArrayList<String> triplets = new ArrayList<String>();
    
    public CountNgrams(String f) {
        file = f;
        createNgramPolarityList();
    }
    
    public ArrayList<String> getTriplet() {
        return triplets;
    }
    
    private void createNgramPolarityList() {
        
        try {
        	LineNumberReader lnr = new LineNumberReader(new FileReader(file));      	
        	
        	String line = null;
        	while((line = lnr.readLine()) != null) {
        		int result = (lnr.getLineNumber() - 1) % 6;
        		if (result == 0) {
                    Scanner s = new Scanner(line);
       				s.useDelimiter("#");
       				//System.out.println(line);        				
       				if(s.hasNext()) {
       					triplets.add(s.next().trim());       					
       				}
        			s.close();
        		}
        	}
                    	
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
    }

	public static void main(String[] args) {
		CountNgrams cn = new CountNgrams(args[0]);
		ArrayList<String> trip = cn.getTriplet();
		for (String t : trip) {
			System.out.println(t);
		}
	}
}
