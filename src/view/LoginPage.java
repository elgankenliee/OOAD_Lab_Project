package view;

import client.Main;
import factories.GUIComponentFactory;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage {

	public static void loadPage(Stage primaryStage) {

		VBox titleContainer = GUIComponentFactory.createLogo();

		VBox parentContainer = new VBox();
		parentContainer.setAlignment(Pos.CENTER);
		parentContainer.setSpacing(10);

		parentContainer.setBackground(Main.defaultBg);

		parentContainer.getChildren().addAll(titleContainer, GUIComponentFactory.createLoginForm(primaryStage));

		primaryStage.getScene().setRoot(parentContainer);
		primaryStage.show();

	}

}
