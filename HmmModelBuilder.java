import java.io.*;
import java.util.*;
import java.util.regex.*;

public class HmmModelBuilder {
	public static void main(String[] args) {
		// We need to read the following files:
		// The combined file from SenseRelate results and N-grams
		// The file containing the valence dictionary
		// The file containing the Gold Standard
		
		// We also need to write to two files:
		// One which will contain the positive sentences
		// One which will contain the negative sentences
		
		// The output files will need to be in the following form:
		// a; b; c; d
		// Each letter denotes the combined POS/polarity of each word 
		// in each sentence. The words are ordered according to their position in the respective sentence.
		NgramSenseRelateReader nsrr = new NgramSenseRelateReader(args[0]);
		GoldStandardReader gsr = new GoldStandardReader(args[1]);
		ValenceReader vr = new ValenceReader(args[2]);
		PrintWriter positive = null;
		PrintWriter negative = null;
		try {
			
		
			positive = new PrintWriter(args[3]);
			negative = new PrintWriter(args[4]);
		
			HashMap<String,ArrayList<String>> sentences = nsrr.getSentences();
			Set<String> ids = sentences.keySet();
		
		
			for (String id : ids) {
				ArrayList<String> triplets = sentences.get(id);
				String mappedSentence = "";
				for (String triplet : triplets) {
					Scanner sc = new Scanner(triplet);
					String word = sc.findInLine(Pattern.compile(":[\\s]*[a-z]+[\\-a-z]*[_a-z]*#"));
					sc.close();
					System.out.println("ID is: " + id);
					System.out.println("Word is: " + word);
					if (word.contains(":") || word.contains("#")) {
						if (word.contains(":") && word.contains("#")) {
							// Start from the second character and end at the one before last thus excluding : and #.
							word = word.substring(1,word.length() - 1).trim(); 
						} else if (word.contains(":")) {
							word = word.substring(1,word.length()).trim();
						} else {
							word = word.substring(0,word.length() - 1).trim();
						}
					} else {
						word = word.trim();
					}
					System.out.print(id + ":" + word);
					
					if (nsrr.isValence(id,word)) {
						String mapping = vr.getHmmValueFor(word);
						System.out.println(":" + mapping);
						mappedSentence = mappedSentence.concat(mapping + "; ");
					} else {
						String pos = nsrr.getPosFor(id,word);
						String polarity = nsrr.getPolarityFor(id,word);
						String mapping = "";
					
						if (pos.equals("n")) {
							if (polarity.equals("pos")) {
								mapping = "0";
								mappedSentence = mappedSentence.concat(mapping + "; ");
							} else {
								mapping = "1";
								mappedSentence = mappedSentence.concat(mapping + "; ");
							}
						} else if (pos.equals("v")) {
							if (polarity.equals("pos")) {
								mapping = "2";
								mappedSentence = mappedSentence.concat(mapping + "; ");
							} else {
								mapping = "3";
								mappedSentence = mappedSentence.concat(mapping + "; ");
							}
						} else if (pos.equals("a")) {
							if (polarity.equals("neg")) {
								mapping = "7";
								mappedSentence = mappedSentence.concat(mapping + "; ");
							} else {
								mapping = "8";
								mappedSentence = mappedSentence.concat(mapping + "; ");
							}
						} else { // adverb ("r")
							if (polarity.equals("pos")) {
								mapping = "9";
								mappedSentence = mappedSentence.concat(mapping + "; ");
							} else {
								mapping = "10";
								mappedSentence = mappedSentence.concat(mapping + "; ");
							}
						}
						
						System.out.println(":" + mapping);
					}
				}
				String sentencePolarity = gsr.returnValueFor(id);
				System.out.println(sentencePolarity);
			
				if (sentencePolarity.equals("pos")) {
					positive.println(mappedSentence.trim());
				} else {
					negative.println(mappedSentence.trim());
				}
				//System.out.println(id + " : " + mappedSentence.trim() + " : " + sentencePolarity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			positive.close();
			negative.close();
		}
	}
}
