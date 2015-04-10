import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleOpinionScorer {

	private Map<String, Double> dictionaryOfScores;

	public SimpleOpinionScorer(String pathToSentimentDictionary) throws IOException {
		// This is our main dictionary representation
		dictionaryOfScores = new HashMap<String, Double>();

		// From String to list of doubles.
		HashMap<String, HashMap<Integer, Double>> tempDictionary = new HashMap<String, HashMap<Integer, Double>>();

		BufferedReader sentimentWordNetFile = null;
		try {
			sentimentWordNetFile = new BufferedReader(new FileReader(pathToSentimentDictionary));
			int lineNumber = 0;

			String line;
			while ((line = sentimentWordNetFile.readLine()) != null) {
				lineNumber++;

				// If it's a comment, skip this line.
				if (!line.trim().startsWith("#")) {
					// We use tab separation
					String[] data = line.split("\t");
					String wordTypeMarker = data[0];

					// Example line:
					// POS ID PosS NegS SynsetTerm#sensenumber Desc
					// a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
					// ascetic#2 practicing great self-denial;...etc

					// Is it a valid line? Otherwise, through exception.
					if (data.length != 6) {
						throw new IllegalArgumentException(
								"Incorrect tabulation format in file, line: "
										+ lineNumber);
					}

					// Calculate synset score as score = PosS - NegS
					Double synsetScore = Double.parseDouble(data[2])
							- Double.parseDouble(data[3]);

					// Get all Synset terms
					String[] synTermsSplit = data[4].split(" ");

					// Go through all terms of current synset.
					for (String synTermSplit : synTermsSplit) {
						// Get synterm and synterm rank
						String[] synTermAndRank = synTermSplit.split("#");
						String synTerm = synTermAndRank[0] + "#"
								+ wordTypeMarker;

						int synTermRank = Integer.parseInt(synTermAndRank[1]);
						// What we get here is a map of the type:
						// term -> {score of synset#1, score of synset#2...}

						// Add map to term if it doesn't have one
						if (!tempDictionary.containsKey(synTerm)) {
							tempDictionary.put(synTerm,
									new HashMap<Integer, Double>());
						}

						// Add synset link to synterm
						tempDictionary.get(synTerm).put(synTermRank, synsetScore);
					}
				}
			}

			// Go through all the terms.
			for (Map.Entry<String, HashMap<Integer, Double>> entry : tempDictionary.entrySet()) {
				String word = entry.getKey();
				Map<Integer, Double> synSetScoreMap = entry.getValue();

				// Calculate weighted average. Weigh the synsets according to
				// their rank.
				// Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
				// Sum = 1/1 + 1/2 + 1/3 ...
				double score = 0.0;
				double sum = 0.0;
				for (Map.Entry<Integer, Double> setScore : synSetScoreMap
						.entrySet()) {
					score += setScore.getValue() / (double) setScore.getKey();
					sum += 1.0 / (double) setScore.getKey();
				}
				score /= sum;

				dictionaryOfScores.put(word, score);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sentimentWordNetFile != null) {
				sentimentWordNetFile.close();
			}
		}
	}

	public double extract(String word, String pos) {
		return dictionaryOfScores.get(word + "#" + pos);
	}
	
	// public static void main(String [] args) throws IOException {
	// 	if(args.length<1) {
	// 		System.err.println("Usage: java SimpleOpinionScorer <pathToSentiWordNetFile>");
	// 		return;
	// 	}
		
	// 	String pathToSentimentDictionary = args[0];
	// 	SimpleOpinionScorer sentiwordnet = new SimpleOpinionScorer(pathToSentimentDictionary);
		
	// 	System.out.println("mind-blowing#a "+sentiwordnet.extract("mind-blowing", "a"));
	// 	System.out.println("super#a "+sentiwordnet.extract("super", "a"));
	// 	System.out.println("awesome#a "+sentiwordnet.extract("awesome", "a"));
	// 	System.out.println("blue#n "+sentiwordnet.extract("blue", "n"));
	// }
}