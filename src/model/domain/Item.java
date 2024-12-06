package model.domain;

import java.sql.SQLException;

import client.Main;
import util.Connect;
import view.BuyerHomePage;
import view.ItemDetailPage;

public class Item {

	private static Connect db = Connect.getInstance();

	private String itemID;
	private String sellerID;

	private String itemName;
	private String itemSize;
	private double itemPrice;
	private String itemCategory;
	private String itemStatus;
	private String itemWishlist;
	private String itemOfferStatus;

	public Item(String itemID, String sellerID, String itemName, String itemSize, double itemPrice, String itemCategory,
			String itemStatus, String itemWishlist, String itemOfferStatus) {
		this.itemID = itemID;
		this.sellerID = sellerID;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.itemPrice = itemPrice;
		this.itemCategory = itemCategory;
		this.itemStatus = itemStatus;
		this.itemWishlist = itemWishlist;
		this.itemOfferStatus = itemOfferStatus;
	}

	public String getSellerID() {
		return sellerID;
	}

	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}

	public String getitemID() {
		return itemID;
	}

	public void setitemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

	public double getItemPrice() {
		return (int) Math.round(itemPrice);
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getItemWishlist() {
		return itemWishlist;
	}

	public void setItemWishlist(String itemWishlist) {
		this.itemWishlist = itemWishlist;
	}

	public String getItemOfferStatus() {
		return itemOfferStatus;
	}

	public void setItemOfferStatus(String itemOfferStatus) {
		this.itemOfferStatus = itemOfferStatus;
	}

	public static void browseItem(boolean isEmptySearch, String searchedItemName) {
		String query = null;
		if (isEmptySearch) {
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

	public static void viewDetail(Item item) {

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

	public static void offerPrice(String itemID, int bidPrice) {
		// TODO Auto-generated method stub
		String query = String.format(
				"insert into offers (buyerid, itemid, offerprice, offerstatus) values (%s, %s, %d, 'Pending');",
				Main.currentUser.getUserID(), itemID, bidPrice);
		db.execUpdate(query);
	}
}
