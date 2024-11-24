package routes;

import javafx.stage.Stage;
import view.LoginPage;
import view.RegisterPage;

public class Route {

	public static void redirectRegisterPage(Stage primaryStage) {
		RegisterPage.initRegisterPage(primaryStage);
	}

	public static void redirectLoginPage(Stage primaryStage) {
		LoginPage.initLoginPage(primaryStage);
	}

	public static void redirectBuyerHomePage(Stage primaryStage) {

	}

}
