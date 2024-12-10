package view.general;

import client.Main;
import factories.GUIComponentFactory;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class LoginPage {

	public static void loadPage() {

		VBox titleContainer = GUIComponentFactory.createLogo();

		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10);

		root.setBackground(Main.defaultBg);

		root.getChildren().addAll(titleContainer, GUIComponentFactory.createLoginForm());

		Main.switchRoot(root);

	}

}
