package controller;

import model.domain.Item;
import routes.Route;
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

}
