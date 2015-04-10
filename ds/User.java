package sml.ds;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * This class defines an User. This representation of user will be obtained after
 * we have parsed all the comments, extracted features and set feature-values to
 * represent an user. At this level, we do not need comment-level attributes or
 * product-level attributes
 * @author somak
 *
 */
public class User {

	@Getter (AccessLevel.PUBLIC) private String userID;
	@Getter (AccessLevel.PUBLIC) private FeatureSet setOfFeatures;
	
	public double getFeature(int index) {
		return setOfFeatures.getFeatureValue(index);
	}
	
	public double getFeature(String featureName) {
		return setOfFeatures.getFeatureValue(featureName);
	}
	
	public void setFeature(String featureName, double value) {
		setOfFeatures.setFeature(featureName, value);
	}

	public User(String userID) {
		super();
		this.userID = userID;
		this.setOfFeatures = new FeatureSet();
	}

	/**
	 * Extract feature values from text and other fields and update {@link #setOfFeatures}.
	 * @param productID
	 * @param title
	 * @param price
	 * @param userID2
	 * @param profileName
	 * @param helpfulNess
	 * @param score
	 * @param time
	 * @param summary
	 * @param text
	 */
	public void addFeatureValuesToProfile(ProductItem productItem,
			String userID2, String profileName, String helpfulNess,
			double score, long time, String summary, String text) {

        
	}
}
