package model.domain;

public class Wishlist {
	private String wishlistID;
	private String itemID;
	private String buyerID;

	public Wishlist(String wishlistID, String itemID, String buyerID) {
		super();
		this.wishlistID = wishlistID;
		this.itemID = itemID;
		this.buyerID = buyerID;
	}

	public String getWishlistID() {
		return wishlistID;
	}

	void setWishlistID(String wishlistID) {
		this.wishlistID = wishlistID;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(String buyerID) {
		this.buyerID = buyerID;
	}
}
