import java.io.*;
import java.util.*;
import java.util.regex.*;

/* This is the class that handles the combined output
 * from the merging of the N-gram output and the
 * SenseRelate output files.
 */

public class NgramSenseRelateReader {
	private BufferedReader br;
	private ArrayList<String> words;
	private HashMap<String,ArrayList<String>> instances = new HashMap<String,ArrayList<String>>();
	
	public NgramSenseRelateReader(String file) {
		try {	
			br = new BufferedReader(new FileReader(file));
			String line = null;
			String id = null;
		
			while((line = br.readLine()) != null) {
	            if (line.matches("^[\\d]*:")) {
	                //System.out.println(line);
	                id = line.substring(0,line.length() - 1); //ommit the :
					words = new ArrayList<String>();
	            } else {
	                Scanner sc = new Scanner(line);
	                String trip = sc.findInLine(Pattern.compile("[a-zA-Z]+#[a-z]+[#]?[0-9]*"));
					sc.close();
	                if (trip != null) {
						// We need the whole line not just the triplet
	                    words.add(line.trim());
						instances.put(id, words);
	                }
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getPolarityFor(String id, String word) {
		ArrayList<String> wdList = instances.get(id);
		String result = null;
		for(String triplet : wdList) {
			if (triplet.contains(word)) {
				result = triplet.substring(triplet.length() - 3);
			}
		}
		
		return result;
	}
	
	public String getPosFor(String id, String word) {
		ArrayList<String> wdList = instances.get(id);
		String result = null;
		for(String triplet : wdList) {
			if (triplet.contains(word)) {
				result = triplet.split("#")[1];
			}
		}
		
		return result;
	}
	
	public boolean isValence(String id, String word) {
		ArrayList<String> wdList = instances.get(id);
		boolean result = false;
		for(String triplet : wdList) {
			if (triplet.contains(word)) {
				result = triplet.contains("valence");
			}
		}
		
		return result;
	}
	
	public HashMap<String,ArrayList<String>> getSentences() {
		return instances;
	}
	
}