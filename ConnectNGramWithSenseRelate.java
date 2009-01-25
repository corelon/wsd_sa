import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class ConnectNGramWithSenseRelate {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            int tIndex = 0;
            int pIndex = 0;
            
            // This will probably change soon (name and implementation)
            NgramParser tnp = new NgramParser(args[1]);
            
            ArrayList<String> triplet = tnp.getTriplet();
            ArrayList<String> polarity = tnp.getPolarity();
            
            FileWriter sw = new FileWriter(args[2]);
            String line = null;
            
            while(((line = br.readLine()) != null) && (tIndex < triplet.size()) && (pIndex < polarity.size())) {
                if (line.matches("^[\\d]*:")) {
                    //System.out.println(line);
                    sw.write(line + "\n");
                } else {
                    Scanner sc = new Scanner(line);
                    String trip = sc.findInLine(Pattern.compile("[a-zA-Z]+#[a-z]+[#]?[0-9]*"));
                    //if (trip != null && trip.equals(triplet.get(tIndex))) {
                    System.out.println(trip);
                    if (trip != null && !trip.toLowerCase().contains("no#cl")) {
                        //System.out.println(triplet.get(tIndex) + ":" +polarity.get(pIndex));
                        String pol = polarity.get(pIndex);
                        sw.write(line + " " + pol + "\n");
                        sc.close();
						tIndex++;
		                pIndex++;
                    } else {
						String pol = "neg";
						sw.write("no#a#1" + " " + pol + "\n");
						sc.close();
					}
                }
                
                //sw.flush();
            }
            
            
            sw.close();
                    
                    
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}