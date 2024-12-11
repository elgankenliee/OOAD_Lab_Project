package controller;

import factories.GUIComponentFactory;
import javafx.scene.control.Alert;
import model.domain.Wishlist;
import routes.Route;
import view.buyer.BuyerWishlistPage;

public class WishlistController {

	// Checks if the item is already in the user's wishlist.
	public static boolean uniqueWishlist(String itemID, String userID) {
		return Wishlist.uniqueWishlist(itemID, userID);
	}

	// Adds an item to the user's wishlist if it's not already added.
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

	// Clears the item list, initializes the wishlist, and redirects to the buyer
	// wishlist page with the given search parameters -> for navbar purposes.
	public static void initWishlist(String searchedItem, String placeHolder) {
		BuyerWishlistPage.itemList.clear();
		Wishlist.initWishlist();
		Route.redirectBuyerWishlistPage(searchedItem, placeHolder);
	}

	// This method is included based on the class diagram, but the parameters do not
	// align with its intended functionality, making it currently unusable.
	public static void viewWishlist(String wishlistID, String userID) {
	}
}
