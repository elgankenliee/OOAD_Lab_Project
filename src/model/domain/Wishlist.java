package model.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import client.Main;
import util.Connect;
import view.buyer.BuyerWishlistPage;

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

	// Checks if the specified item is already in the user's wishlist. Returns
	// `false` if the item is already in the wishlist, indicating it is not unique.
	// Otherwise, returns `true` if the item is not in the wishlist.
	public static boolean uniqueWishlist(String itemID, String userID) {
		String query = "SELECT * FROM Wishlist WHERE itemID = ? AND BuyerID = ?";

		try (PreparedStatement stmt = db.addQuery(query)) {
			stmt.setString(1, itemID);
			stmt.setString(2, userID);

			db.rs = stmt.executeQuery();
			if (db.rs.next()) {
				return false; // Item already exists in the wishlist
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true; // Item doesn't exist in the wishlist
	}

	// Adds the specified item to the user's wishlist by inserting the itemID and
	// userID into the Wishlist table.
	public static void addWishlist(String itemID, String userID) {
		String query = "INSERT INTO Wishlist (BuyerID, ItemID) VALUES (?, ?)";

		try (PreparedStatement stmt = db.addQuery(query)) {
			stmt.setString(1, userID);
			stmt.setString(2, itemID);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Removes the specified item from the user's wishlist by deleting the
	// corresponding entry from the Wishlist table.
	public static void removeWishlist(String itemID, String userID) {
		String query = "DELETE FROM wishlist WHERE buyerID = ? AND itemID = ?";

		try (PreparedStatement stmt = db.addQuery(query)) {
			stmt.setString(1, userID);
			stmt.setString(2, itemID);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Initializes the wishlist for the current user by querying the `wishlist`
	// table and joining it with the `items` table to fetch item details where the
	// item is marked as 'Approved'. The retrieved items are then added to the
	// `BuyerWishlistPage.itemList`.
	public static void initWishlist() {
		String query = "SELECT * FROM wishlist w JOIN items i ON w.ItemID = i.ItemID "
				+ "WHERE BuyerID = ? AND ItemStatus LIKE 'Approved' ORDER BY WishlistID DESC;";

		try (PreparedStatement stmt = db.addQuery(query)) {
			stmt.setString(1, Main.currentUser.getUserID());
			db.rs = stmt.executeQuery();

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
			e.printStackTrace();
		}
	}

}
