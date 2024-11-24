package view;

import client.Main;
import factories.GUIComponentFactory;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage {

	public static void initLoginPage(Stage primaryStage) {

		VBox titleContainer = GUIComponentFactory.createLogo();

		VBox parentContainer = new VBox();
		parentContainer.setAlignment(Pos.CENTER);
		parentContainer.setSpacing(10);

		parentContainer.setBackground(Main.defaultBg);

		parentContainer.getChildren().addAll(titleContainer, GUIComponentFactory.createLoginForm(primaryStage));

//		Scene scene = new Scene(parentContainer, viewPortWidth, viewPortHeight);

		primaryStage.getScene().setRoot(parentContainer);
		primaryStage.show();

	}

}
