package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import client.Main;
import factories.GUIComponentFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Item;
import util.Connect;

public class ItemController {
	private static Connect db = Connect.getInstance();

	public static ArrayList<Item> fetchItem() {

		ArrayList<Item> itemList = new ArrayList<>();
		String query = "SELECT * FROM Items WHERE Status LIKE 'approved' ORDER BY ItemID DESC";
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
				itemList.add(new Item(dbItemID, dbSellerID, dbItemName, dbItemSize, dbItemPrice, dbItemCategory,
						dbItemStatus, dbItemWishList, dbItemOfferStatus));

//				itemList.add(new Item(itemID, itemName, itemPrice, itemDesc, itemStock));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		for (String str : CustomerDashboardPage.categoryList) {
//			System.out.println(str);
//		}

		return itemList;
	}

	public static VBox browseItem(Stage primaryStage, String searchedString) {
		ArrayList<Item> itemList = fetchItem();
		VBox itemContainer = new VBox();
		itemContainer.setSpacing(20);
		int minBoxHeight = 1500;

		HBox resultMsgContainer = new HBox();

		if (searchedString.isEmpty()) {

			resultMsgContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Showing ", "#717489"),
					GUIComponentFactory.createSrchMsgLbl(Integer.toString(itemList.size()), Main.themeOrange),
					GUIComponentFactory.createSrchMsgLbl(" products", "#717489"));
			itemContainer.getChildren().add(resultMsgContainer);

			for (Item item : itemList) {
				minBoxHeight += 200;
				itemContainer.getChildren().add(GUIComponentFactory.createDashboardItemBox(primaryStage, item));
			}
		} else {
			ArrayList<Item> suggestedItems = new ArrayList<>();

			searchedString = searchedString.toLowerCase();
			for (Item item : itemList) {
				if (item.getItemName().toLowerCase().contains(searchedString)
						|| item.getItemCategory().toLowerCase().contains(searchedString)
						|| searchedString.contains(item.getItemName().toLowerCase())
						|| searchedString.contains(item.getItemCategory().toLowerCase())) {
					suggestedItems.add(item);
				}
			}

			resultMsgContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Showing ", "#717489"),
					GUIComponentFactory.createSrchMsgLbl(Integer.toString(suggestedItems.size()), Main.themeOrange),
					GUIComponentFactory.createSrchMsgLbl(" products for ", "#717489"),
					GUIComponentFactory.createSrchMsgLbl("'" + searchedString + "'", Main.themeOrange));
			itemContainer.getChildren().add(resultMsgContainer);

			for (Item item : suggestedItems) {
				itemContainer.getChildren().add(GUIComponentFactory.createDashboardItemBox(primaryStage, item));
			}
			if (suggestedItems.size() > 5) {
				minBoxHeight += (suggestedItems.size() - 5) * 170;
			}

		}
		Main.tempScreenMinHeight = minBoxHeight;
		return itemContainer;
	}

}
