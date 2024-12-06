package routes;

import controller.ItemController;
import javafx.stage.Stage;
import view.BuyerHistoryPage;
import view.BuyerHomePage;
import view.LoginPage;
import view.RegisterPage;

public class Route {

	public static void redirectRegisterPage(Stage primaryStage) {
		RegisterPage.loadPage(primaryStage);
	}

	public static void redirectLoginPage(Stage primaryStage) {
		LoginPage.loadPage(primaryStage);
	}

	public static void redirectBuyerHomePage(Stage primaryStage, String searchedItemName, String placeholder) {
		ItemController.browseItem(primaryStage, searchedItemName, placeholder);
		BuyerHomePage.loadPage(primaryStage, searchedItemName, placeholder);
	}

	public static void redirectBuyerHistoryPage(Stage primaryStage) {
		BuyerHistoryPage.loadPage(primaryStage);

	}

}
