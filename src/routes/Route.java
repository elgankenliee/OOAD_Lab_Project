package routes;

import model.domain.Item;
import view.BuyerHistoryPage;
import view.BuyerHomePage;
import view.BuyerWishlistPage;
import view.ItemDetailPage;
import view.LoginPage;
import view.RegisterPage;

public class Route {

	public static void redirectRegisterPage() {
		RegisterPage.loadPage();
	}

	public static void redirectLoginPage() {
		LoginPage.loadPage();
	}

	public static void redirectBuyerHomePage(String searchedItemName, String placeholder) {
		BuyerHomePage.loadPage(searchedItemName, placeholder);
	}

	public static void redirectBuyerHistoryPage() {
		BuyerHistoryPage.loadPage();

	}

	public static void redirectItemDetailPage(Item item) {
		ItemDetailPage.loadPage(item);
	}

	public static void redirectBuyerWishlistPage(String searchedItem, String placeHolder) {
		BuyerWishlistPage.loadPage(searchedItem, placeHolder);

	}

}
