package sml.ds;

import java.util.HashMap;

/**
 * Stores individual feature names and their corresponding indices. If each individual feature
 * need more attributes, then we need to add another class called Feature and define a HashMap
 * from String to Feature.
 * @author somak
 *
 */
public class FeatureNameTable {

	private static HashMap<String, Integer> stringFeaturesToIndexMap = new HashMap<String, Integer>();

	public void populateFeatureNames() {
		//TODO iterate over feature names and populate hashmap.
	}
	
	public static int lookUp(String featureName) {
		return stringFeaturesToIndexMap.get(featureName);
	}
	
}
