package controller;

import factories.GUIComponentFactory;
import javafx.scene.control.Alert;
import model.domain.Item;
import routes.Route;
import view.AdminHomePage;
import view.BuyerHomePage;
import view.ItemDetailPage;

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

}
