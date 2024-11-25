package model;

public class Wishlist {
	private int wishlistID;
	private int itemID;
	private int buyerID;

	public Wishlist(int wishlistID, int itemID, int buyerID) {
		super();
		this.wishlistID = wishlistID;
		this.itemID = itemID;
		this.buyerID = buyerID;
	}

	public int getWishlistID() {
		return wishlistID;
	}

	public void setWishlistID(int wishlistID) {
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
