import java.io.*;
import java.text.*;
import java.util.regex.*;

public class TestSplit {
    public static void main(String[] args) {
        String test = "4640:";
        Pattern id = Pattern.compile("^[\\d]*");
        Matcher match = id.matcher(test);
        String result = match.toString();
    }
}