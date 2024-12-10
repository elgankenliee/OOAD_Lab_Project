package controller;

import factories.GUIComponentFactory;
import javafx.scene.control.Alert;
import model.domain.Wishlist;
import routes.Route;
import view.buyer.BuyerWishlistPage;

public class WishlistController {

	public static boolean uniqueWishlist(String itemID, String userID) {
		return Wishlist.uniqueWishlist(itemID, userID);
	}

	public static void addWishlist(String itemID, String userID) {
		if (uniqueWishlist(itemID, userID)) {
			Wishlist.addWishlist(itemID, userID);
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
		Wishlist.removeWishlist(itemID, userID);
	}

	public static void initWishlist(String searchedItem, String placeHolder) {
		BuyerWishlistPage.itemList.clear();
		Wishlist.initWishlist();
		Route.redirectBuyerWishlistPage(searchedItem, placeHolder);
	}

	public static void viewWishlist(String wishlistID, String userID) {
	}
}
