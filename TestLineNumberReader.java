import java.io.*;
import java.util.*;

public class TestLineNumberReader {
    public static void main(String[] args) {
        try {
        	LineNumberReader lnr = new LineNumberReader(new FileReader(args[0]));
        	String line = null;
        	while((line = lnr.readLine()) != null) {
        		int result = (lnr.getLineNumber() - 1) % 3;
        		if (result == 0) {
        			if (line.matches("-->[n|p][o|e][s|g]")) {
        				System.out.println("Line " + lnr.getLineNumber() + ": " + line.substring(3));
        			} else {
        				Scanner s = new Scanner(line);
        				s.useDelimiter("#");
        				String correct = null;
        				while(s.hasNext()) {
        					String s1 = s.next().trim();
        					String s2 = s.next().trim();
        					String s3 = s.next().trim();
        					correct = s1 + "#" + s2 + "#" + s3;
        				}
        				System.out.println("Line " + lnr.getLineNumber() + ": " + correct);
        			}
        		}
        	}
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
}
