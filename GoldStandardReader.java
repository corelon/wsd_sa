import java.io.*;
import java.util.*;
/*
 * This is the class that reads the polarity values
 * from the Gold Standard.
 */

public class GoldStandardReader {
	private BufferedReader br;
	private HashMap<String,String> gs = new HashMap<String,String>();
	
	public GoldStandardReader(String file) {
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				Scanner sc = new Scanner(line);
				sc.useDelimiter("\\s");
				String id = sc.next().trim();
				String value = sc.next().trim();
				//System.out.println(id + ":" + value);
				
				// We need to pay extra attention as
				// this case only treats the minus in from of the value
				// Thus a sentence that is 0 will return POS!!!!
				if (value.startsWith("-")) {
					value = "neg";
				} else {
					value = "pos";
				}
				//System.out.println(id + ":" + value);
				gs.put(id,value);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String returnValueFor(String id) {
		return gs.get(id);
	} 
	
	private Set<String> getMappings() {
		return gs.keySet();
	}
	
	
	public static void main(String[] args) {
		GoldStandardReader gsr = new GoldStandardReader(args[0]);
		Set<String> keys = gsr.getMappings();
		for (String key : keys) {
			System.out.println(key + ":" + gsr.returnValueFor(key));
		}
	}
}