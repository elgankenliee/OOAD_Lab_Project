package controller;

import java.sql.SQLException;

import javafx.stage.Stage;
import model.Item;
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

				int dbItemID = db.rs.getInt("ItemID");
				int dbSellerID = db.rs.getInt("SellerID");
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
		BuyerHomePage.initBuyerHomePage(primaryStage, searchedItemName, placeholder);
	}

	public static void viewDetail(Stage primaryStage, Item item) {

		ItemDetailPage.initCustomerItemDetailPage(primaryStage, item);

	}

}
