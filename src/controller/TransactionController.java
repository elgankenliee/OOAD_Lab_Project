package controller;

import model.domain.Transaction;
import routes.Route;

public class TransactionController {
	public static void purchaseItem(String userID, String itemID) {
		Transaction.purchaseItem(userID, itemID);
	}

	public static void viewHistory(String userID) {
		Transaction.viewHistory(userID);
		Route.redirectBuyerHistoryPage();
	}
}
