package model.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import client.Main;
import util.Connect;
import view.admin.AdminHomePage;
import view.buyer.BuyerHomePage;
import view.general.ItemDetailPage;
import view.seller.SellerHomePage;

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

	// Handles item browsing by either displaying all approved items or
	// filtering by item name/category based on the search input.
	public static void browseItem(boolean isEmptySearch, String searchedItemName) {
		String query = null;
		try {
			if (isEmptySearch) {
				BuyerHomePage.categoryList.clear();
				query = "SELECT * FROM Items WHERE ItemStatus LIKE 'approved' ORDER BY ItemID DESC";
			} else {
				query = "SELECT * FROM Items WHERE ItemStatus LIKE 'approved' AND (ItemName LIKE ? OR ItemCategory LIKE ?) ORDER BY ItemID DESC";
			}

			try (PreparedStatement ps = db.addQuery(query)) {
				if (!isEmptySearch) {
					ps.setString(1, "%" + searchedItemName + "%");
					ps.setString(2, "%" + searchedItemName + "%");
				}

				db.rs = ps.executeQuery();
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

					if (searchedItemName.isEmpty()) {
						if (!BuyerHomePage.categoryList.contains(dbItemCategory.toLowerCase())) {
							BuyerHomePage.categoryList.add(dbItemCategory.toLowerCase());
						}
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// This method retrieves and displays the list of pending bids for a given item,
	// showing the bidder's username, offer price, and bid date.
	public static void viewDetail(Item item) {

		String itemID = item.getitemID();

		String query = "SELECT CONCAT(username, ' placed a ¥', offerprice, ' bid at ', offerdate) AS BidSentence "
				+ "FROM users u JOIN offers o ON u.UserID = o.BuyerID WHERE itemID = ? AND OfferStatus LIKE 'Pending' ORDER BY offerdate DESC";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, itemID);
			db.rs = ps.executeQuery();
			while (db.rs.next()) {
				String dbBidSentence = db.rs.getString("BidSentence");
				ItemDetailPage.bidList.add(dbBidSentence);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// This method retrieves the highest pending bid for a given item, or returns
	// the item's current price if no bid exists.
	public static int getHighestBid(Item item) {
		if (bidExists(item.getitemID())) {

			String query = "SELECT MAX(offerprice) AS HighestBid FROM offers WHERE OfferStatus LIKE 'Pending' AND itemID = ?";

			try (PreparedStatement ps = db.addQuery(query)) {
				ps.setString(1, item.getitemID());
				db.rs = ps.executeQuery();
				if (db.rs.next()) {
					return db.rs.getInt("HighestBid");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (int) Math.round(item.getItemPrice());
	}

	// This method checks if there is a pending bid for the given item by querying
	// the offers table.
	public static boolean bidExists(String itemID) {
		String query = "SELECT * FROM offers WHERE OfferStatus LIKE 'Pending' AND itemID = ?";
		PreparedStatement ps = db.addQuery(query);
		try {
			ps.setString(1, itemID);
			db.rs = ps.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (db.rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// Inserts a new pending bid for the specified item and updates the item status
	// to 'Waiting for highest bidder'
	public static void offerPrice(String itemID, int bidPrice) {
		String query = "INSERT INTO offers (buyerid, itemid, offerprice, offerstatus) VALUES (?, ?, ?, 'Pending')";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, Main.currentUser.getUserID());
			ps.setString(2, itemID);
			ps.setInt(3, bidPrice);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "UPDATE Items SET ItemOfferStatus = 'Waiting for highest bidder' WHERE ItemID = ?";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, itemID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Fetches and displays all items with 'Waiting for approval' status to be
	// reviewed by the admin
	public static void viewRequestedItem() {
		String query = "SELECT * FROM items WHERE itemStatus LIKE 'Waiting for approval' ORDER BY ItemID DESC";
		db.rs = db.execQuery(query);
		try {
			while (db.rs.next()) {
				String dbItemID = db.rs.getString("ItemID");
				String dbSellerID = db.rs.getString("SellerID");
				String dbItemName = db.rs.getString("ItemName");
				String dbItemSize = db.rs.getString("ItemSize");
				Double dbItemPrice = db.rs.getDouble("ItemPrice");
				String dbItemCategory = db.rs.getString("ItemCategory");
				String dbItemStatus = db.rs.getString("ItemStatus");
				String dbItemWishlist = db.rs.getString("ItemWishlist");
				String dbItemOfferStatus = db.rs.getString("ItemOfferStatus");
				AdminHomePage.itemList.add(new Item(dbItemID, dbSellerID, dbItemName, dbItemSize, dbItemPrice,
						dbItemCategory, dbItemStatus, dbItemWishlist, dbItemOfferStatus));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Approves the item and sets its offer status to 'Pending', indicating that
	// there's no active bid yet in this item
	public static void approveItem(String itemID) {
		String query = "UPDATE items SET ItemStatus = 'Approved', ItemOfferStatus = 'Pending' WHERE ItemID = ?";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, itemID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Declines the item, records the reason, and deletes it from the database
	public static void declineItem(String itemID, String reason) {
		String query = "UPDATE items SET ItemStatus = 'Declined', ItemOfferStatus = 'Declined', DeclineReason = ? WHERE ItemID = ?";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, reason);
			ps.setString(2, itemID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "DELETE FROM items WHERE ItemID = ?";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, itemID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Fetches and displays all items listed by the current seller from the database
	public static void viewItem() {
		String query = "SELECT * FROM items WHERE SellerID = ? ORDER BY ItemID DESC";

		try (PreparedStatement ps = db.addQuery(query)) {
			// Set the SellerID parameter
			ps.setString(1, Main.currentUser.getUserID());

			// Execute the query
			db.rs = ps.executeQuery();
			while (db.rs.next()) {
				String dbItemID = db.rs.getString("ItemID");
				String dbSellerID = db.rs.getString("SellerID");
				String dbItemName = db.rs.getString("ItemName");
				String dbItemSize = db.rs.getString("ItemSize");
				Double dbItemPrice = db.rs.getDouble("ItemPrice");
				String dbItemCategory = db.rs.getString("ItemCategory");
				String dbItemStatus = db.rs.getString("ItemStatus");
				String dbItemWishlist = db.rs.getString("ItemWishlist");
				String dbItemOfferStatus = db.rs.getString("ItemOfferStatus");

				// Add item to the list
				SellerHomePage.itemList.add(new Item(dbItemID, dbSellerID, dbItemName, dbItemSize, dbItemPrice,
						dbItemCategory, dbItemStatus, dbItemWishlist, dbItemOfferStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Fetches and displays all approved items listed by the current seller from the
	// database
	public static void viewAcceptedItem() {
		String query = "SELECT * FROM items WHERE SellerID = ? AND ItemStatus LIKE ? ORDER BY ItemID DESC";

		try (PreparedStatement ps = db.addQuery(query)) {
			// Set the parameters for the query
			ps.setString(1, Main.currentUser.getUserID()); // SellerID
			ps.setString(2, "Approved"); // ItemStatus

			// Execute the query and process results
			db.rs = ps.executeQuery();
			while (db.rs.next()) {
				String dbItemID = db.rs.getString("ItemID");
				String dbSellerID = db.rs.getString("SellerID");
				String dbItemName = db.rs.getString("ItemName");
				String dbItemSize = db.rs.getString("ItemSize");
				Double dbItemPrice = db.rs.getDouble("ItemPrice");
				String dbItemCategory = db.rs.getString("ItemCategory");
				String dbItemStatus = db.rs.getString("ItemStatus");
				String dbItemWishlist = db.rs.getString("ItemWishlist");
				String dbItemOfferStatus = db.rs.getString("ItemOfferStatus");

				// Add the item to the SellerHomePage list
				SellerHomePage.itemList.add(new Item(dbItemID, dbSellerID, dbItemName, dbItemSize, dbItemPrice,
						dbItemCategory, dbItemStatus, dbItemWishlist, dbItemOfferStatus));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Retrieves and displays items that are approved and waiting for the highest
	// bidder, with at least one offer made
	public static void viewOfferItem() {
		String query = "SELECT * FROM items WHERE SellerID = ? AND ItemStatus LIKE 'Approved' AND ItemOfferStatus LIKE 'Waiting for highest bidder' AND ItemID IN (SELECT ItemID FROM offers) ORDER BY ItemID DESC";
		PreparedStatement ps = db.addQuery(query);
		try {
			ps.setString(1, Main.currentUser.getUserID());
			db.rs = ps.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (db.rs.next()) {
				String dbItemID = db.rs.getString("ItemID");
				String dbSellerID = db.rs.getString("SellerID");
				String dbItemName = db.rs.getString("ItemName");
				String dbItemSize = db.rs.getString("ItemSize");
				Double dbItemPrice = db.rs.getDouble("ItemPrice");
				String dbItemCategory = db.rs.getString("ItemCategory");
				String dbItemStatus = db.rs.getString("ItemStatus");
				String dbItemWishlist = db.rs.getString("ItemWishlist");
				String dbItemOfferStatus = db.rs.getString("ItemOfferStatus");
				SellerHomePage.itemList.add(new Item(dbItemID, dbSellerID, dbItemName, dbItemSize, dbItemPrice,
						dbItemCategory, dbItemStatus, dbItemWishlist, dbItemOfferStatus));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Updates the details of an item (name, category, size, and price) based on the
	// provided itemID
	public static boolean editItem(String itemID, String itemName, String itemCategory, String itemSize,
			String itemPrice) {
		String query = "UPDATE items " + "SET itemName = ?, itemCategory = ?, itemSize = ?, itemPrice = ? "
				+ "WHERE itemID = ?";

		try (PreparedStatement ps = db.addQuery(query)) {
			// Set the parameters for the query
			ps.setString(1, itemName); // itemName
			ps.setString(2, itemCategory); // itemCategory
			ps.setString(3, itemSize); // itemSize
			ps.setDouble(4, Double.parseDouble(itemPrice)); // itemPrice
			ps.setString(5, itemID); // itemID

			// Execute the update
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Return false in case of an error
		}
	}

	// Inserts a new item into the database with the provided details and initial
	// status values
	public static boolean uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice) {
		String query = "INSERT INTO items (SellerID, ItemName, ItemSize, ItemPrice, ItemCategory, ItemStatus, ItemOfferStatus) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = db.addQuery(query)) {
			// Set the parameters for the query
			ps.setString(1, Main.currentUser.getUserID()); // SellerID
			ps.setString(2, itemName); // ItemName
			ps.setString(3, itemSize); // ItemSize
			ps.setDouble(4, Double.parseDouble(itemPrice)); // ItemPrice
			ps.setString(5, itemCategory); // ItemCategory
			ps.setString(6, "Waiting for approval"); // ItemStatus
			ps.setString(7, "Pending"); // ItemOfferStatus

			// Execute the insert
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Return false in case of an error
		}
	}

	// Retrieves the BuyerID of the highest bidder for a given item, if a bid
	// exists.
	public static String getHighestBidder(String itemID) {
		if (bidExists(itemID)) {
			String query = "SELECT BuyerID FROM Offers WHERE OfferPrice = (SELECT MAX(OfferPrice) FROM Offers WHERE OfferStatus LIKE 'Pending')";
			db.rs = db.execQuery(query);
			try {
				if (db.rs.next()) {
					return db.rs.getString("BuyerID");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	// Accepts the highest bid offer for a given item if a bid exists.
	public static boolean acceptOffer(String itemID) {
		if (bidExists(itemID)) {
			String query = "UPDATE Offers SET OfferStatus = 'Accepted' " + "WHERE OfferPrice = ("
					+ "SELECT MAX(OfferPrice) FROM Offers WHERE ItemID = ?" + ") AND ItemID = ?";
			try (PreparedStatement ps = db.addQuery(query)) {
				// Set parameters for the query
				ps.setString(1, itemID); // Placeholder for the subquery
				ps.setString(2, itemID); // Placeholder for the main query

				// Execute the update
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// Declines the highest pending bid for an item and updates the item offer
	// status to 'Pending' if no bids remain.
	public static boolean declineOffer(String itemID, String reason) {
		String query = null;
		if (bidExists(itemID)) {
			query = "UPDATE Offers "
					+ "SET OfferStatus = 'Declined', DeclineReason = ? WHERE OfferPrice = (SELECT MAX(OfferPrice) FROM Offers WHERE ItemID = ? AND OfferStatus LIKE 'Pending') AND ItemID = ?";

			PreparedStatement ps = db.addQuery(query);

			try {
				// Set the parameters in the query
				ps.setString(1, reason); // First placeholder for DeclineReason
				ps.setString(2, itemID); // Second placeholder for ItemID in subquery
				ps.setString(3, itemID); // Third placeholder for ItemID in main query

				// Execute the update
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (!bidExists(itemID)) {
				query = "UPDATE items SET ItemOfferStatus = 'Pending'";
				db.execUpdate(query);
			}
			return true;
		}
		return false;

	}

	// Deletes an item from the database based on the given item ID.
	public static boolean deleteItem(String itemID) {
		String query = "DELETE FROM items WHERE ItemID = ?";
		PreparedStatement ps = db.addQuery(query);
		try {
			ps.setString(1, itemID);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
