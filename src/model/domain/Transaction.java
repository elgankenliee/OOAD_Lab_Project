package model.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.presentation.TransactionView;
import util.Connect;
import view.buyer.BuyerHistoryPage;

public class Transaction {
	private static Connect db = Connect.getInstance();
	private String transactionID;
	private String userID;
	private String itemID;

	public Transaction(String transactionID, String userID, String itemID) {
		super();
		this.transactionID = transactionID;
		this.userID = userID;
		this.itemID = itemID;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public static void purchaseItem(String userID, String itemID) {
		String query = null;
		try {
			query = "INSERT INTO transactions (BuyerID, ItemID) VALUES (?, ?)";
			PreparedStatement ps1 = db.addQuery(query);
			ps1.setString(1, userID);
			ps1.setString(2, itemID);
			ps1.executeUpdate();

			query = "UPDATE items SET ItemStatus = 'Sold' WHERE ItemID = ?";
			PreparedStatement ps2 = db.addQuery(query);
			ps2.setString(1, itemID);
			ps2.executeUpdate();

			query = "DELETE FROM Wishlist WHERE ItemID = ?";
			PreparedStatement ps3 = db.addQuery(query);
			ps3.setString(1, itemID);
			ps3.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewHistory(String userID) {

		BuyerHistoryPage.historyList.clear();
		String query = "SELECT transactionID, itemName, itemCategory, itemSize, itemPrice, TransactionDate "
				+ "FROM users u JOIN transactions t ON u.UserID = t.BuyerID JOIN items i ON i.ItemID = t.ItemID "
				+ "WHERE userID = ? ORDER BY TransactionID DESC;";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, userID);
			db.rs = ps.executeQuery();

			while (db.rs.next()) {
				String dbTransactionID = db.rs.getString("transactionID");
				String dbItemName = db.rs.getString("itemName");
				String dbItemCategory = db.rs.getString("itemCategory");
				String dbItemSize = db.rs.getString("itemSize");
				double dbItemPrice = db.rs.getDouble("itemPrice");
				String dbTransactionDate = db.rs.getString("TransactionDate");

				BuyerHistoryPage.historyList.add(new TransactionView(dbTransactionID, dbItemName, dbItemCategory,
						dbItemSize, dbItemPrice, dbTransactionDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
