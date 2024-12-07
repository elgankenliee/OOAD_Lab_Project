package model.domain;

import java.sql.SQLException;

import client.Main;
import util.Connect;
import view.BuyerWishlistPage;

public class Wishlist {

	private static Connect db = Connect.getInstance();

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

	public static boolean uniqueWishlist(String itemID, String userID) {
		String query = "SELECT * FROM Wishlist WHERE itemID = " + itemID + " AND BuyerID =" + userID + ";";
		db.rs = db.execQuery(query);
		try {
			if (db.rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void addWishlist(String itemID, String userID) {
		String query = "INSERT INTO Wishlist (BuyerID, ItemID) VALUES (" + userID + ", " + itemID + ")";
		db.execUpdate(query);
	}

	public static void removeWishlist(String itemID, String userID) {
		String query = "DELETE FROM wishlist WHERE buyerID = " + userID + " AND itemID = " + itemID;
		db.execUpdate(query);
	}

	public static void initWishlist() {
		String query = "SELECT * " + "FROM wishlist w JOIN items i ON w.ItemID = i.ItemID " + "WHERE BuyerID = "
				+ Main.currentUser.getUserID() + " ORDER BY WishlistID DESC;";

		db.rs = db.execQuery(query);
		try {
			while (db.rs.next()) {

				String dbItemID = db.rs.getString("ItemID");
				String dbSellerID = db.rs.getString("SellerID");
				String dbItemName = db.rs.getString("ItemName");
				String dbItemSize = db.rs.getString("ItemSize");
				double dbItemPrice = db.rs.getDouble("ItemPrice");
				String dbItemCategory = db.rs.getString("ItemCategory");
				String dbItemStatus = db.rs.getString("ItemStatus");
				String dbItemWishList = db.rs.getString("ItemWishlist");
				String dbItemOfferStatus = db.rs.getString("ItemOfferStatus");
				BuyerWishlistPage.itemList.add(new Item(dbItemID, dbSellerID, dbItemName, dbItemSize, dbItemPrice,
						dbItemCategory, dbItemStatus, dbItemWishList, dbItemOfferStatus));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
