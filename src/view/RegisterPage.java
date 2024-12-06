package view;

import client.Main;
import factories.GUIComponentFactory;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class RegisterPage {

	public static void loadPage() {
		VBox titleContainer = GUIComponentFactory.createLogo();

		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10);

		root.setBackground(Main.defaultBg);

		root.getChildren().addAll(titleContainer, GUIComponentFactory.createRegisterForm());

		Main.switchRoot(root);
	}

}
