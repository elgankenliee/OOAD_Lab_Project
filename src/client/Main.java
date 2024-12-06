package client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import model.domain.User;
import routes.Route;

public class Main extends Application {
	public static User currentUser = null;
	public static int viewPortHeight = 1000;
	public static int viewPortWidth = 1920;
	public static int contentWidth = viewPortWidth - 400;
	public static int tempScreenMinHeight = 1500;
	public static String defaultPlaceholder = "Search Items in CaLouselF Store";

	public static String themeOrange = "#FF9C24";
	public static String themeWhite = "#F3F3F3";
	public static String buttonGrey = "#646a9b";
	public static String navbarGrey = "#303243";

	public static String AESencryptionKey = "what if i just walk away bcs im a cool guy";

	public static LinearGradient bgGradient2 = new LinearGradient(0, 0, 1, 1, true,
			javafx.scene.paint.CycleMethod.REFLECT, new Stop(0, Color.web("#b968e8")),
			new Stop(1, Color.web("#1d214f")));
	public static LinearGradient bgGradient = new LinearGradient(0, 0, 1, 0, true,
			javafx.scene.paint.CycleMethod.REFLECT, new Stop(1, Color.web("#333548")),
			new Stop(0, Color.web("#19191D")));
	public static Background defaultBg = new Background(
			new BackgroundFill(bgGradient, CornerRadii.EMPTY, Insets.EMPTY));
	public static Background defaultBg2 = new Background(
			new BackgroundFill(bgGradient2, CornerRadii.EMPTY, Insets.EMPTY));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("CaLouselF");
		primaryStage.setScene(new Scene(new VBox(), viewPortWidth, viewPortHeight));
		Route.redirectLoginPage(primaryStage);
	}

}
