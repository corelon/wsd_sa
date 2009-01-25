package gr.demokritos.iit.sentiment;

import java.io.*;

public class NgramGraphOutputExtractor {
    public static void main(String[] args) {
        try {
            // Read the file which contains the output of the n-gram graphs
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
