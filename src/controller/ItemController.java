package controller;

import factories.GUIComponentFactory;
import javafx.scene.control.Alert;
import model.domain.Item;
import routes.Route;
import view.admin.AdminHomePage;
import view.buyer.BuyerHomePage;
import view.general.ItemDetailPage;
import view.seller.SellerHomePage;

public class ItemController {

	public static void browseItem(String searchedItemName, String placeholder) {
		BuyerHomePage.itemList.clear();
		Item.browseItem(searchedItemName == "", searchedItemName);
		Route.redirectBuyerHomePage(searchedItemName, placeholder);
	}

	public static void viewDetail(Item item) {
		ItemDetailPage.bidList.clear();
		Item.viewDetail(item);
		Route.redirectItemDetailPage(item);
	}

	public static int getHighestBid(Item item) {
		return Item.getHighestBid(item);
	}

	public static void offerPrice(String itemID, int bidPrice) {
		Item.offerPrice(itemID, bidPrice);
	}

	public static boolean validBidAmount(Item item, int bid) {
		return bid > Item.getHighestBid(item) && bid > item.getItemPrice();
	}

	public static String formatCurrency(double amount) {
		if (amount >= 1_000_000) {
			return String.format("%.2fm", amount / 1_000_000);
		} else if (amount >= 1_000) {
			return String.format("%.2fk", amount / 1_000);
		} else {
			return String.format("%.2f", amount);
		}
	}

	public static void viewRequestedItem() {
		AdminHomePage.itemList.clear();
		Item.viewRequestedItem();
		Route.redirectAdminHomePage();
	}

	public static void approveItem(String itemID) {
		Item.approveItem(itemID);
		ItemController.viewRequestedItem();

	}

	public static boolean declineItem(String itemID, String declineReason) {
		if (declineReason.isEmpty()) {
			Alert error = GUIComponentFactory.createError("Error", "Decline reason can't be empty",
					"Please input disapproval reason");
			error.showAndWait();
			return false;
		}
		Item.declineItem(itemID, declineReason);
		Alert notification = GUIComponentFactory.createNotification("Notification", "Item has been declined",
				"press OK to proceed");
		notification.showAndWait();
		ItemController.viewRequestedItem();
		return true;

	}

	public static void viewItem() {
		SellerHomePage.itemList.clear();
		Item.viewItem();
		Route.redirectSellerHomePage();
	}

	public static void viewAcceptedItem() {
		SellerHomePage.itemList.clear();
		Item.viewAcceptedItem();
		Route.redirectSellerHomePage();
	}

	public static void viewOfferItem() {
		SellerHomePage.itemList.clear();
		Item.viewOfferItem();
		Route.redirectSellerHomePage();

	}

	public static boolean checkItemValidation(String itemName, String itemCategory, String itemSize, String itemPrice) {
		itemPrice = itemPrice.trim();
		double price = -1;
		if (itemName.isEmpty() || itemCategory.isEmpty() || itemSize.isEmpty() || itemPrice.isEmpty()) {
			Alert error = GUIComponentFactory.createError("Error", "All fields must be filled!",
					"Please fill all fields");
			error.showAndWait();
			return false;
		} else if (itemName.length() < 3 || itemCategory.length() < 3) {
			Alert error = GUIComponentFactory.createError("Error", "Input is too short!",
					"Item name and category require at least 3 characters input");
			error.showAndWait();
			return false;
		}

		try {
			price = Double.parseDouble(itemPrice);

		} catch (NumberFormatException e) {
			Alert error = GUIComponentFactory.createError("Formatting error", "Invalid price input!",
					"Please input a valid value");
			error.showAndWait();
			return false;
		}

		if (price <= 0) {
			Alert error = GUIComponentFactory.createError("Error", "Price must be greater than 0!",
					"Please raise the price");
			error.showAndWait();
			return false;
		}

		return itemName.length() >= 3 && itemCategory.length() >= 3 && !itemSize.isEmpty() && !itemPrice.isEmpty()
				&& price > 0;
	}

	public static boolean editItem(String itemID, String itemName, String itemCategory, String itemSize,
			String itemPrice) {
		if (checkItemValidation(itemName, itemCategory, itemSize, itemPrice)) {
			return Item.editItem(itemID, itemName, itemCategory, itemSize, itemPrice);
		}
		return false;
	}

	public static boolean uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice) {
		if (checkItemValidation(itemName, itemCategory, itemSize, itemPrice)) {
			return Item.uploadItem(itemName, itemCategory, itemSize, itemPrice);
		}
		return false;
	}

	public static boolean acceptOffer(String itemID) {
		if (bidExists(itemID)) {
			String highestBidderID = Item.getHighestBidder(itemID);
			Item.acceptOffer(itemID);
			TransactionController.purchaseItem(highestBidderID, itemID);
			Alert notification = GUIComponentFactory.createNotification("Notification", "Transaction created!",
					"Item sold to " + UserController.getUsername(highestBidderID));
			notification.showAndWait();
			return true;
		}
		Alert error = GUIComponentFactory.createError("Error", "Unable to create transaction!",
				"There's no active bid for this item");
		error.showAndWait();
		return false;
	}

	public static boolean declineOffer(Item item, String itemID, String reason) {

		if (bidExists(itemID)) {
			Item.declineOffer(itemID, reason);
			Alert notification = GUIComponentFactory.createNotification("Notification", "Top bid declined!",
					"Press OK to proceed");
			notification.showAndWait();
			ItemController.viewDetail(item);
			return true;
		}
		Alert error = GUIComponentFactory.createError("Error", "Unable to decline bid!",
				"There's no active bid for this item");
		error.showAndWait();
		return false;
	}

	public static boolean bidExists(String itemID) {
		return Item.bidExists(itemID);
	}

	public static void deleteItem(String itemID) {
		// TODO Auto-generated method stub

	}

}
