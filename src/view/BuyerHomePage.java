package view;

import java.util.ArrayList;

import client.Main;
import factories.GUIComponentFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.domain.Item;
import routes.Route;

public class BuyerHomePage {

	public static ArrayList<String> categoryList = new ArrayList<>();
	public static ArrayList<Item> itemList = new ArrayList<>();

	public static void loadPage(Stage primaryStage, String itemName, String searchBarText) {

		VBox screen = new VBox();
		screen.setBackground(Main.defaultBg);
		screen.setSpacing(20);
		screen.setAlignment(Pos.TOP_CENTER);
		screen.setMinWidth(Main.viewPortWidth);
		screen.setMinHeight(10);

		HBox welcomeMessageContainer = new HBox();
		welcomeMessageContainer.setTranslateY(97);
		Label welcomeLabel = new Label("Welcome, ");

		Label customerNameLabel = new Label(Main.currentUser.getUsername());

		welcomeLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		welcomeLabel.setTextFill(Color.WHITE);

		customerNameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		customerNameLabel.setTextFill(Color.web(Main.themeOrange));

		welcomeMessageContainer.getChildren().addAll(welcomeLabel, customerNameLabel);
		welcomeMessageContainer.setMinWidth(Main.contentWidth);
		welcomeMessageContainer.setMaxWidth(Main.contentWidth);
		welcomeMessageContainer.setMinHeight(100);
		welcomeMessageContainer.setAlignment(Pos.CENTER_LEFT);

		HBox content = new HBox();
		content.setMaxWidth(Main.contentWidth);
		content.setAlignment(Pos.TOP_CENTER);
		content.setMinWidth(Main.contentWidth);
		content.setTranslateX(80);
		content.setTranslateY(120);

		VBox leftContent = new VBox();
		leftContent.setAlignment(Pos.TOP_LEFT);
		leftContent.setSpacing(20);
		leftContent.setMinWidth(Main.contentWidth * 0.2);

		VBox filterBox = new VBox();
		filterBox.setSpacing(20);
		filterBox.setMinWidth(Main.contentWidth * 0.2);
		filterBox.setMaxWidth(Main.contentWidth * 0.2);
		filterBox.setMinHeight(130);
		filterBox.setStyle("-fx-background-radius : 10; -fx-background-color : " + Main.navbarGrey + ";");
		filterBox.setPadding(new Insets(20, 10, 10, 20));

		Label filterLabel = new Label("Filter");
		filterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		filterLabel.setTextFill(Color.web("#717489"));

		Label filterByCategoryLabel = new Label("Category");
		filterByCategoryLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		filterByCategoryLabel.setTextFill(Color.web("#717489"));

		leftContent.getChildren().addAll(filterLabel, filterBox);

		VBox rightContent = new VBox();
		rightContent.setAlignment(Pos.TOP_CENTER);
		rightContent.setMinWidth(Main.contentWidth * 0.8);
		rightContent.setSpacing(20);
		rightContent.setTranslateX(80);

		content.getChildren().addAll(leftContent, rightContent);

		screen.getChildren().addAll(GUIComponentFactory.createNavbar(primaryStage, searchBarText),
				welcomeMessageContainer, content);

		VBox itemContainer = new VBox();
		itemContainer.setSpacing(20);
		int minBoxHeight = 1500;

		HBox resultMsgContainer = new HBox();

		resultMsgContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Showing ", "#717489"),
				GUIComponentFactory.createSrchMsgLbl(Integer.toString(itemList.size()), Main.themeOrange),
				GUIComponentFactory.createSrchMsgLbl(" products", "#717489"));
		itemContainer.getChildren().add(resultMsgContainer);

		if (!itemList.isEmpty()) {

			for (Item item : itemList) {
				minBoxHeight += 200;
				itemContainer.getChildren().add(GUIComponentFactory.createHomePageItemCard(primaryStage, item));
			}
		}

		rightContent.getChildren().add(itemContainer);

		ScrollPane scrollPane = new ScrollPane();

		scrollPane.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
			double deltaY = event.getDeltaY() * 2;
			scrollPane.setVvalue(
					scrollPane.getVvalue() - deltaY / scrollPane.getContent().getBoundsInLocal().getHeight());
			event.consume();
		});

		HBox filterContainer = new HBox();
		filterContainer.setAlignment(Pos.CENTER_LEFT);
		filterContainer.setSpacing(10);

		ComboBox<Label> categoryBox = new ComboBox<>();

		categoryBox.setStyle("-fx-background-color : #545877");
		categoryBox.setMinHeight(35);
		categoryBox.setMinWidth(186);

		Label promptText = new Label("Select a category");
		promptText.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		promptText.setTextFill(Color.web("#9da3cd"));

		categoryBox.setValue(promptText);

		for (String category : categoryList) {
			Label categoryLabel = new Label(
					category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase());
			categoryLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
			categoryLabel.setTextFill(Color.BLACK);
			categoryLabel.setMinWidth(100);
			categoryLabel.setMinHeight(15);
			categoryBox.getItems().add(categoryLabel);
		}

		Button applyFilterButton = GUIComponentFactory.createButton("Apply");
		applyFilterButton.setOnAction(e -> {
			Route.redirectBuyerHomePage(primaryStage, categoryBox.getValue().getText(), Main.defaultPlaceholder);
		});

		filterContainer.getChildren().addAll(categoryBox, applyFilterButton);
		filterBox.getChildren().addAll(filterByCategoryLabel, filterContainer);

		screen.setMinHeight(Main.tempScreenMinHeight);
		scrollPane.setContent(screen);
		scrollPane.setFitToWidth(true);

		Scene scene = new Scene(scrollPane, Main.viewPortWidth, Main.viewPortHeight);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
