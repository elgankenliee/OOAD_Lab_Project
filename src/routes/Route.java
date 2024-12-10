package routes;

import model.domain.Item;
import view.admin.AdminHomePage;
import view.buyer.BuyerHistoryPage;
import view.buyer.BuyerHomePage;
import view.buyer.BuyerWishlistPage;
import view.general.ItemDetailPage;
import view.general.LoginPage;
import view.general.RegisterPage;
import view.seller.SellerHomePage;

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

	public static void redirectAdminHomePage() {
		AdminHomePage.loadPage();
	}

	public static void redirectSellerHomePage() {
		SellerHomePage.loadPage();

	}

}
