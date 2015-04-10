package sml;

import java.util.HashMap;

import sml.ds.User;

public class UserSet {

	private HashMap<String, User> idToUserMap = new HashMap<String, User>();
	
	public User getLinkedUserProfile(String userID) {
		if(!idToUserMap.containsKey(userID)) 
			idToUserMap.put(userID, new User(userID));
		return idToUserMap.get(userID);
	}
}
