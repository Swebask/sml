package sml;

import java.util.HashMap;

import sml.ds.ProductItem;

public class ItemSet {
	private HashMap<String, ProductItem> idToProductItemMap = new HashMap<String, ProductItem>();
	
	public ProductItem getLinkedItemProfile(String itemID) {
		return idToProductItemMap.get(itemID);
	}
	
	public void addProductItemToMap(String itemId, String title, double price) {
		idToProductItemMap.put(itemId, new ProductItem(itemId, title, price));
	}
}
