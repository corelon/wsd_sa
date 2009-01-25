import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class TestStringContains {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            int tIndex = 0;
            int pIndex = 0;
            
            // This will probably change soon (name and implementation)
            TestNgramParser tnp = new TestNgramParser(args[1]);
            
            ArrayList<String> triplet = tnp.getTriplet();
            ArrayList<String> polarity = tnp.getPolarity();
            
            FileWriter sw = new FileWriter("test_result.txt");
            String line = null;
            
            while((line = br.readLine()) != null && tIndex < triplet.size() && pIndex < polarity.size()) {
                if (line.matches("^[\\d]*:")) {
                    //System.out.println(line);
                    sw.write(line + "\n");
                } else {
                    Scanner sc = new Scanner(line);
                    String trip = sc.findInLine(Pattern.compile("[a-zA-Z]+#[a-z]+#[0-9]*"));
                    //if (trip != null && trip.equals(triplet.get(tIndex))) {
                    if (trip != null) {
                        System.out.println(triplet.get(tIndex));
                        String pol = polarity.get(pIndex);
                        sw.write(line + " " + pol + "\n");
                        sc.close();
                    }
                }
                tIndex++;
                pIndex++;
            }
            
            sw.flush();
            sw.close();
                    
                    
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}