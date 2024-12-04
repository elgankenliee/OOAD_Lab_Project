package routes;

import controller.ItemController;
import javafx.stage.Stage;
import view.BuyerHomePage;
import view.LoginPage;
import view.RegisterPage;

public class Route {

	public static void redirectRegisterPage(Stage primaryStage) {
		RegisterPage.initRegisterPage(primaryStage);
	}

	public static void redirectLoginPage(Stage primaryStage) {
		LoginPage.initLoginPage(primaryStage);
	}

	public static void redirectBuyerHomePage(Stage primaryStage, String searchedItemName, String placeholder) {
		ItemController.browseItem(primaryStage, searchedItemName, placeholder);
		BuyerHomePage.initBuyerHomePage(primaryStage, searchedItemName, placeholder);
	}

}
