package controller;

import java.sql.SQLException;

import client.Main;
import factories.GUIComponentFactory;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.domain.Item;
import util.Connect;
import view.BuyerWishlistPage;

public class WishlistController {

	private static Connect db = Connect.getInstance();

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
		if (uniqueWishlist(itemID, userID)) {
			String query = "INSERT INTO Wishlist (BuyerID, ItemID) VALUES (" + userID + ", " + itemID + ")";
			db.execUpdate(query);
			Alert alert = GUIComponentFactory.createNotification("Wishlist Notification", "Wishlist Created!",
					"This item has been added to your wishlist.");
			alert.showAndWait();
		} else {
			Alert alert = GUIComponentFactory.createError("Wishlist Error", "Operation failed!",
					"This item is already in your wishlist.");
			alert.showAndWait();
		}
	}

	public static void removeWishlist(String itemID, String userID) {
		String query = "DELETE FROM wishlist WHERE buyerID = " + userID + " AND itemID = " + itemID;
		db.execUpdate(query);

	}

	public static void initWishlist(Stage primaryStage, String searchedItem, String searchbarText) {
		BuyerWishlistPage.itemList.clear();
		String query = "SELECT * " + "FROM wishlist w JOIN items i ON w.ItemID = i.ItemID " + "WHERE BuyerID = "
				+ Main.currentUser.getUserID() + ";";

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

		BuyerWishlistPage.initBuyerWishlist(primaryStage, searchedItem, searchbarText);
	}

	public static void viewWishlist(String wishlistID, String userID) {
	}
}
