package controller;

import java.sql.SQLException;

import factories.GUIComponentFactory;
import javafx.scene.control.Alert;
import util.Connect;

public class WishlistController {

	private static Connect db = Connect.getInstance();

	public static boolean uniqueWishlist(int itemID, int userID) {
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

	public static void addWishlist(int itemID, int userID) {
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
}
