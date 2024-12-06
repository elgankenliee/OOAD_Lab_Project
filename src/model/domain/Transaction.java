package model.domain;

import java.sql.SQLException;

import model.presentation.TransactionView;
import util.Connect;
import view.BuyerHistoryPage;

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
		String query = String.format("INSERT INTO transactions (BuyerID, ItemID) VALUES('%s', '%s')", userID, itemID);
		db.execUpdate(query);
	}

	public static void viewHistory(String userID) {

		BuyerHistoryPage.historyList.clear();
		String query = "SELECT transactionID, itemName, itemCategory, itemSize, itemPrice, TransactionDate\r\n"
				+ "FROM users u JOIN transactions t ON u.UserID = t.BuyerID JOIN items i ON i.ItemID = t.ItemID\r\n"
				+ "WHERE userID = " + userID + " ORDER BY TransactionID ;";
		db.rs = db.execQuery(query);
		try {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
