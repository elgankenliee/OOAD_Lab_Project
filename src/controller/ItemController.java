package controller;

import java.sql.SQLException;

import client.Main;
import javafx.stage.Stage;
import model.domain.Item;
import util.Connect;
import view.BuyerHomePage;
import view.ItemDetailPage;

public class ItemController {
	private static Connect db = Connect.getInstance();

	public static void browseItem(Stage primaryStage, String searchedItemName, String placeholder) {
		BuyerHomePage.itemList.clear();
		String query = null;
		if (searchedItemName == "") {
			BuyerHomePage.categoryList.clear();
			query = "SELECT * FROM Items WHERE ItemStatus LIKE 'approved' ORDER BY ItemID DESC";
		} else {
			query = "SELECT * FROM Items WHERE ItemStatus LIKE 'approved' AND ItemName LIKE '%" + searchedItemName
					+ "%' OR ItemCategory LIKE '%" + searchedItemName + "%' ORDER BY ItemID DESC";
		}
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
				BuyerHomePage.itemList.add(new Item(dbItemID, dbSellerID, dbItemName, dbItemSize, dbItemPrice,
						dbItemCategory, dbItemStatus, dbItemWishList, dbItemOfferStatus));
				if (searchedItemName == "") {
					if (!BuyerHomePage.categoryList.contains(dbItemCategory.toLowerCase())) {
						BuyerHomePage.categoryList.add(dbItemCategory.toLowerCase());
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void viewDetail(Stage primaryStage, Item item) {
		getItemBid(item);
		ItemDetailPage.initCustomerItemDetailPage(primaryStage, item);

	}

	public static void getItemBid(Item item) {

		ItemDetailPage.bidList.clear();
		String itemID = item.getitemID();

		String query = "SELECT CONCAT(username, ' placed a ¥', offerprice,' bid at ', offerdate) AS BidSentence "
				+ "FROM users u JOIN offers o ON u.UserID = o.BuyerID " + "WHERE itemID = " + itemID
				+ " ORDER BY offerdate DESC";
		db.rs = db.execQuery(query);
		try {
			while (db.rs.next()) {
				String dbBidSentence = db.rs.getString("BidSentence");
				ItemDetailPage.bidList.add(dbBidSentence);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int getHighestBid(Item item) {

		String query = "SELECT MAX(offerprice) AS HighestBid FROM offers WHERE itemID = " + item.getitemID();
		db.rs = db.execQuery(query);
		try {
			if (db.rs.next()) {
				return db.rs.getInt("HighestBid");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int) Math.round(item.getItemPrice());
	}

	public static void offerPrice(Item item, int bidPrice) {
		String itemID = item.getitemID();
		String query = String.format(
				"insert into offers (buyerid, itemid, offerprice, offerstatus) values (%s, %s, %d, 'Pending');",
				Integer.parseInt(Main.currentUser.getUserID()), itemID, bidPrice);
		db.execUpdate(query);
	}

	public static boolean validBidAmount(Item item, int bid) {
		String itemID = item.getitemID();
		int highestBid = 0;
		String query = "SELECT MAX(offerprice) AS HighestBid FROM offers WHERE itemID = " + itemID;
		db.rs = db.execQuery(query);
		try {
			if (db.rs.next()) {
				highestBid = db.rs.getInt("HighestBid");
			} else
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bid > highestBid && bid > item.getItemPrice();
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
