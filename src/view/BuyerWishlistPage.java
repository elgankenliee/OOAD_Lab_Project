package view;

import java.util.ArrayList;
import java.util.Optional;

import client.Main;
import factories.GUIComponentFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Item;

public class BuyerWishlistPage {

	public static Boolean cartIsEmpty = false;
	public static Double cartSubtotal = 0.0;
	public static Label cartTotalPriceLabel = new Label("");
	public static ArrayList<Item> itemList = new ArrayList<>();

	public BuyerWishlistPage() {
		// TODO Auto-generated constructor stub
	}

	public static void initBuyerWishlist(Stage primaryStage, String searchedString, String searchBarText) {
		cartSubtotal = 0.0;
		VBox screen = new VBox();
		screen.setBackground(Main.defaultBg);
		screen.setSpacing(20);
		screen.setAlignment(Pos.TOP_CENTER);
		screen.setMinWidth(Main.viewPortWidth);
		screen.setMinHeight(10);
//		screen.setTranslateY(200);

		ScrollPane cartPageScrollPane = new ScrollPane();
		cartPageScrollPane.setContent(screen);
		cartPageScrollPane.setFitToWidth(true);

		HBox headerContainer = new HBox();
		headerContainer.setTranslateY(97);

		Label customerNameLabel = new Label(Main.currentUser.getUsername());

		Label cartLabel = new Label("'s Wishlist");
		cartLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		cartLabel.setTextFill(Color.WHITE);

		customerNameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		customerNameLabel.setTextFill(Color.web(Main.themeOrange));

		headerContainer.getChildren().addAll(customerNameLabel, cartLabel);
		headerContainer.setMinWidth(Main.contentWidth);
		headerContainer.setMaxWidth(Main.contentWidth);
		headerContainer.setMinHeight(100);
		headerContainer.setAlignment(Pos.CENTER_LEFT);

		HBox content = new HBox();
		content.setMaxWidth(Main.contentWidth);
		content.setAlignment(Pos.TOP_CENTER);
		content.setMinWidth(Main.contentWidth);
//		content.setSpacing(20);
		content.setTranslateX(80);
		content.setTranslateY(120);

		VBox rightContent = new VBox();
		rightContent.setAlignment(Pos.TOP_LEFT);
		rightContent.setSpacing(20);
		rightContent.setMinWidth(Main.contentWidth * 0.4);

		VBox leftContent = new VBox();
		leftContent.setAlignment(Pos.TOP_CENTER);
		leftContent.setMinWidth(Main.contentWidth * 0.6);
		leftContent.setSpacing(20);
		leftContent.setTranslateX(80);

		content.getChildren().addAll(leftContent, rightContent);

		screen.getChildren().addAll(GUIComponentFactory.createNavbar(primaryStage, searchBarText), headerContainer,
				content);

//		leftContent.getChildren().add(CartHandler.loadCart(primaryStage, cartPageScrollPane));
		VBox itemContainer = new VBox();
		itemContainer.setSpacing(20);

		HBox resultMsgContainer = new HBox();

		if (itemList.size() < 1) {
			cartIsEmpty = true;
			Label emptyCartLabel = new Label("Your wishlist is empty!");
			emptyCartLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 50));
			emptyCartLabel.setTextFill(Color.web("717489"));
			itemContainer.getChildren().add(emptyCartLabel);
		} else {

			cartIsEmpty = false;
			resultMsgContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Showing ", "#717489"),
					GUIComponentFactory.createSrchMsgLbl(Integer.toString(itemList.size()), Main.themeOrange),
					GUIComponentFactory.createSrchMsgLbl(" products in cart", "#717489"));
			itemContainer.getChildren().add(resultMsgContainer);

			for (Item i : itemList) {
				cartSubtotal += i.getItemPrice() * 1;
				itemContainer.getChildren()
						.add(GUIComponentFactory.createCartItemBox(primaryStage, i, cartPageScrollPane));
			}
			cartSubtotal *= 10;
		}

		leftContent.getChildren().add(itemContainer);

		cartPageScrollPane.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
			double deltaY = event.getDeltaY() * 2;
			cartPageScrollPane.setVvalue(cartPageScrollPane.getVvalue()
					- deltaY / cartPageScrollPane.getContent().getBoundsInLocal().getHeight());
			event.consume();
		});

		int qtySelectorWidth = 250;
		VBox qtySelector = new VBox();
		qtySelector.setSpacing(20);
		qtySelector.setPadding(new Insets(15, 15, 8, 15));
		qtySelector.setMinHeight(226);
		qtySelector.setMaxWidth(qtySelectorWidth);

		qtySelector.setStyle("-fx-border-color: #71717c; " + "-fx-border-width: 3px; " + "-fx-border-radius: 10px; "
				+ "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");

		qtySelector.setOnMouseEntered(e -> {
			qtySelector.setStyle("-fx-border-color: " + Main.themeOrange + "; " + "-fx-border-width: 3px; "
					+ "-fx-border-radius: 10px; " + "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");
		});

		qtySelector.setOnMouseExited(e -> {
			qtySelector.setStyle("-fx-border-color: #71717c; " + "-fx-border-width: 3px; " + "-fx-border-radius: 10px; "
					+ "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");
		});

		Label selectorLabel = new Label("Billing summary");
		selectorLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		selectorLabel.setTextFill(Color.web("#cecfde"));

		HBox totalContainer = new HBox();
		totalContainer.setAlignment(Pos.CENTER_LEFT);

		Label totalLabel = new Label("Total : ");
		totalLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		totalLabel.setTextFill(Color.web(Main.themeWhite));

		cartTotalPriceLabel.setText(String.format("$%.2f", cartSubtotal));
		cartTotalPriceLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		cartTotalPriceLabel.setTextFill(Color.web(Main.themeOrange));

		totalContainer.getChildren().addAll(totalLabel, cartTotalPriceLabel);

		Rectangle divider = new Rectangle(qtySelectorWidth, 2, Color.web("cecfde"));
		divider.setTranslateY(-20);

		Button checkOutButton = GUIComponentFactory.createButton("Checkout Items");
		checkOutButton.setMinWidth(qtySelectorWidth);
		checkOutButton.setTranslateY(-13);

		VBox radioContainer = new VBox();
		radioContainer.setSpacing(10);

		Label courierLabel = new Label("*Buy now price = 10x highest bid");
		courierLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		courierLabel.setTextFill(Color.web("#7a7a83"));
		courierLabel.setTranslateY(-20);

		RadioButton dhlRadio = new RadioButton("Logistics Cargo Airline Service");
		RadioButton fedExRadio = new RadioButton("Sunib Express");
		RadioButton uspsRadio = new RadioButton("DuckCing Parcel");
		RadioButton upsRadio = new RadioButton("GNBox Cargo");

		dhlRadio.setStyle(
				"-fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");
		fedExRadio.setStyle(
				"-fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");
		upsRadio.setStyle(
				"-fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");
		uspsRadio.setStyle(
				"-fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");

		radioContainer.getChildren().addAll(courierLabel, dhlRadio, fedExRadio, upsRadio, uspsRadio);

		ToggleGroup courierGroup = new ToggleGroup();
		dhlRadio.setToggleGroup(courierGroup);
		fedExRadio.setToggleGroup(courierGroup);
		upsRadio.setToggleGroup(courierGroup);
		uspsRadio.setToggleGroup(courierGroup);

		checkOutButton.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Checkout Confirmation");
			alert.setHeaderText("Are you sure you want to checkout your cart?");
			alert.setContentText("Please confirm your choice.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
//				CartHandler.createTransaction();
				Alert registerAlert = new Alert(AlertType.INFORMATION);
				registerAlert.setTitle("Transaction Information");
				registerAlert.setHeaderText("Transaction success!");
				registerAlert.setContentText("Your order is now in queue.");
				registerAlert.showAndWait();
				BuyerWishlistPage.initBuyerWishlist(primaryStage, "", "Search Items in GoGoQuery Store");
			}
		});

		if (!cartIsEmpty) {
			rightContent.getChildren().addAll(qtySelector);
		}

		qtySelector.getChildren().addAll(selectorLabel, totalContainer, courierLabel, divider, checkOutButton);

		screen.setMinHeight(Main.tempScreenMinHeight);

		Scene scene = new Scene(cartPageScrollPane, Main.viewPortWidth, Main.viewPortHeight);

		primaryStage.setScene(scene);

		primaryStage.show();
	}

}
