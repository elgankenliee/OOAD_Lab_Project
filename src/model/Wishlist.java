package model;

public class Wishlist {
	private String wishlistID;
	private int itemID;
	private int buyerID;

	public Wishlist(String wishlistID, int itemID, int buyerID) {
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

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(int buyerID) {
		this.buyerID = buyerID;
	}
}
