package view.buyer;

import java.util.ArrayList;

import client.Main;
import controller.ItemController;
import factories.GUIComponentFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import model.domain.Item;

public class BuyerHomePage {

	public static ArrayList<String> categoryList = new ArrayList<>();
	public static ArrayList<Item> itemList = new ArrayList<>();

	public static void loadPage(String itemName, String searchBarText) {
		// Root
		VBox screen = new VBox();

		// Welcome message setup
		HBox welcomeMessageContainer = new HBox();
		Label welcomeLabel = new Label("Welcome, ");
		Label customerNameLabel = new Label(Main.currentUser.getUsername());
		welcomeMessageContainer.getChildren().addAll(welcomeLabel, customerNameLabel);

		// Page structure setup
		HBox content = new HBox();
		VBox leftContent = new VBox();
		VBox rightContent = new VBox();
		content.getChildren().addAll(leftContent, rightContent);

		screen.getChildren().addAll(GUIComponentFactory.createNavbar(searchBarText), welcomeMessageContainer, content);

		// Category filter setup
		VBox filterBox = new VBox();
		Label filterLabel = new Label("Filter");
		Label filterByCategoryLabel = new Label("Category");
		leftContent.getChildren().addAll(filterLabel, filterBox);

		// Header message setup (such as "Showing 3 products")
		VBox itemContainer = new VBox();
		int minBoxHeight = 1500;
		HBox resultMsgContainer = new HBox();
		resultMsgContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Showing ", "#717489"),
				GUIComponentFactory.createSrchMsgLbl(Integer.toString(itemList.size()), Main.themeOrange),
				GUIComponentFactory.createSrchMsgLbl(" products", "#717489"));
		itemContainer.getChildren().add(resultMsgContainer);

		// create card for each item from db
		if (!itemList.isEmpty()) {
			for (Item item : itemList) {
				minBoxHeight += 200;
				itemContainer.getChildren().add(GUIComponentFactory.createHomePageItemCard(item));
			}
		}

		rightContent.getChildren().add(itemContainer);

		// drop down filter setup for each item category
		HBox filterContainer = new HBox();
		ComboBox<Label> categoryBox = new ComboBox<>();
		for (String category : categoryList) {
			Label categoryLabel = new Label(
					category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase());
			categoryLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
			categoryLabel.setTextFill(Color.BLACK);
			categoryLabel.setMinWidth(100);
			categoryLabel.setMinHeight(15);
			categoryBox.getItems().add(categoryLabel);
		}
		Label promptText = new Label("Select a category");
		categoryBox.setValue(promptText);

		Button applyFilterButton = GUIComponentFactory.createButton("Apply");
		applyFilterButton.setOnAction(e -> {
			ItemController.browseItem(categoryBox.getValue().getText(), Main.defaultPlaceholder);
		});

		filterContainer.getChildren().addAll(categoryBox, applyFilterButton);
		filterBox.getChildren().addAll(filterByCategoryLabel, filterContainer);

		// scroll pane for homepage
		ScrollPane root = new ScrollPane();
		root.setContent(screen);

		Main.switchRoot(root);

//		
//		
//		
//		
//		
//		
//		
//		
//------STYLING (Action and Logic doesn't exist here)--------------------------------------------------------------------------------------

		screen.setBackground(Main.defaultBg);
		screen.setSpacing(20);
		screen.setAlignment(Pos.TOP_CENTER);
		screen.setMinWidth(Main.viewPortWidth);
		screen.setMinHeight(10);

		welcomeMessageContainer.setTranslateY(97);
		welcomeMessageContainer.setMinWidth(Main.contentWidth);
		welcomeMessageContainer.setMaxWidth(Main.contentWidth);
		welcomeMessageContainer.setMinHeight(100);
		welcomeMessageContainer.setAlignment(Pos.CENTER_LEFT);

		welcomeLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		welcomeLabel.setTextFill(Color.WHITE);

		customerNameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		customerNameLabel.setTextFill(Color.web(Main.themeOrange));

		content.setMaxWidth(Main.contentWidth);
		content.setAlignment(Pos.TOP_CENTER);
		content.setMinWidth(Main.contentWidth);
		content.setTranslateX(80);
		content.setTranslateY(120);

		leftContent.setAlignment(Pos.TOP_LEFT);
		leftContent.setSpacing(20);
		leftContent.setMinWidth(Main.contentWidth * 0.2);

		filterBox.setSpacing(20);
		filterBox.setMinWidth(Main.contentWidth * 0.2);
		filterBox.setMaxWidth(Main.contentWidth * 0.2);
		filterBox.setMinHeight(130);
		filterBox.setStyle("-fx-background-radius : 10; -fx-background-color : " + Main.navbarGrey + ";");
		filterBox.setPadding(new Insets(20, 10, 10, 20));

		filterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		filterLabel.setTextFill(Color.web("#717489"));

		filterByCategoryLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		filterByCategoryLabel.setTextFill(Color.web("#717489"));

		rightContent.setAlignment(Pos.TOP_CENTER);
		rightContent.setMinWidth(Main.contentWidth * 0.8);
		rightContent.setSpacing(20);
		rightContent.setTranslateX(80);

		itemContainer.setSpacing(20);

		root.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
			double deltaY = event.getDeltaY() * 2;
			root.setVvalue(root.getVvalue() - deltaY / root.getContent().getBoundsInLocal().getHeight());
			event.consume();
		});

		filterContainer.setAlignment(Pos.CENTER_LEFT);
		filterContainer.setSpacing(10);

		categoryBox.setStyle("-fx-background-color : #545877");
		categoryBox.setMinHeight(35);
		categoryBox.setMinWidth(186);

		promptText.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		promptText.setTextFill(Color.web("#9da3cd"));

		screen.setMinHeight(Main.tempScreenMinHeight);
		root.setFitToWidth(true);

	}
}
