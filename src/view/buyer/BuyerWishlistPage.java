package view.buyer;

import java.util.ArrayList;
import java.util.Optional;

import client.Main;
import controller.ItemController;
import controller.TransactionController;
import controller.WishlistController;
import factories.GUIComponentFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import model.domain.Item;

public class BuyerWishlistPage {

	public static Boolean cartIsEmpty = false;
	public static Label cartTotalPriceLabel = new Label("");
	public static ArrayList<Item> itemList = new ArrayList<>();

	public static void loadPage(String searchedString, String searchBarText) {

		double cartSubtotal = 0.0;

		VBox screen = new VBox();
		ScrollPane root = new ScrollPane();

		HBox headerContainer = new HBox();
		Label customerNameLabel = new Label(Main.currentUser.getUsername());
		Label wishlistLabel = new Label("'s Wishlist");

		HBox content = new HBox();
		VBox rightContent = new VBox();
		VBox leftContent = new VBox();
		content.getChildren().addAll(leftContent, rightContent);

		screen.getChildren().addAll(GUIComponentFactory.createNavbar(searchBarText), headerContainer, content);

		HBox resultMsgContainer = new HBox();

		VBox itemContainer = new VBox();

		// Checks if the wishlist is empty or not. If empty, it displays a message
		// saying "Your wishlist is empty!".
		// If there are items in the wishlist, it displays the total number of items,
		// calculates the subtotal based on the highest bid of each item,
		// and then displays the items in the cart with a "buy now" price that is 10
		// times the highest bid for each item.
		if (itemList.size() < 1) {
			cartIsEmpty = true;
			Label emptywishlistLabel = new Label("Your wishlist is empty!");
			emptywishlistLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 50));
			emptywishlistLabel.setTextFill(Color.web("717489"));
			itemContainer.getChildren().add(emptywishlistLabel);
		} else {

			cartIsEmpty = false;
			resultMsgContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Showing ", "#717489"),
					GUIComponentFactory.createSrchMsgLbl(Integer.toString(itemList.size()), Main.themeOrange),
					GUIComponentFactory.createSrchMsgLbl(" products in cart", "#717489"));
			itemContainer.getChildren().add(resultMsgContainer);

			// Iterates through each item in the itemList, calculates the subtotal by adding
			// the highest bid of each item,
			// and adds a visual representation of each item to the itemContainer (using a
			// method to create the UI for each cart item).
			for (Item i : itemList) {
				cartSubtotal += ItemController.getHighestBid(i);
				itemContainer.getChildren().add(GUIComponentFactory.createCartItemBox(i, root));
			}
			cartSubtotal *= 10; // buy now price = 10x highest bid for each item
		}

		leftContent.getChildren().add(itemContainer);

		int checkOutBoxWidth = 250;
		VBox checkOutBox = new VBox();

		Label summaryLabel = new Label("Billing summary");
		HBox totalPriceContainer = new HBox();
		Label totalLabel = new Label("Total : ");

		Button checkOutButton = GUIComponentFactory.createButton("Checkout Items");
		// Triggers a confirmation alert, if buyer responds with OK, a new transaction
		// is created for each items in their wishlist
		checkOutButton.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Checkout Confirmation");
			alert.setHeaderText("Are you sure you want to checkout your cart?");
			alert.setContentText("Please confirm your choice.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				// iterate through each item in current user's wishlist and create a transaction
				// for each item
				for (Item item : itemList) {
					TransactionController.purchaseItem(Main.currentUser.getUserID(), item.getitemID());
				}

				Alert registerAlert = new Alert(AlertType.INFORMATION);
				registerAlert.setTitle("Transaction Information");
				registerAlert.setHeaderText("Transaction success!");
				registerAlert.setContentText("Your order is now in queue.");
				registerAlert.showAndWait();
				WishlistController.initWishlist("", "Search Items in CaLouselF Store");
			}
		});

		totalPriceContainer.getChildren().addAll(totalLabel, cartTotalPriceLabel);

		Rectangle divider = new Rectangle(checkOutBoxWidth, 2, Color.web("cecfde"));

		Label checkoutMsgLabel = new Label("*Buy now price = 10x highest bid");

		if (!cartIsEmpty) {
			rightContent.getChildren().addAll(checkOutBox);
		}

		checkOutBox.getChildren().addAll(summaryLabel, totalPriceContainer, checkoutMsgLabel, divider, checkOutButton);

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

		root.setContent(screen);
		root.setFitToWidth(true);

		headerContainer.setTranslateY(97);

		wishlistLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		wishlistLabel.setTextFill(Color.WHITE);

		customerNameLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, FontPosture.ITALIC, 90));
		customerNameLabel.setTextFill(Color.web(Main.themeOrange));

		headerContainer.getChildren().addAll(customerNameLabel, wishlistLabel);
		headerContainer.setMinWidth(Main.contentWidth);
		headerContainer.setMaxWidth(Main.contentWidth);
		headerContainer.setMinHeight(100);
		headerContainer.setAlignment(Pos.CENTER_LEFT);

		content.setMaxWidth(Main.contentWidth);
		content.setAlignment(Pos.TOP_CENTER);
		content.setMinWidth(Main.contentWidth);
		content.setTranslateX(80);
		content.setTranslateY(120);

		rightContent.setAlignment(Pos.TOP_LEFT);
		rightContent.setSpacing(20);
		rightContent.setMinWidth(Main.contentWidth * 0.4);

		leftContent.setAlignment(Pos.TOP_CENTER);
		leftContent.setMinWidth(Main.contentWidth * 0.6);
		leftContent.setSpacing(20);
		leftContent.setTranslateX(80);

		itemContainer.setSpacing(20);

		root.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
			double deltaY = event.getDeltaY() * 2;
			root.setVvalue(root.getVvalue() - deltaY / root.getContent().getBoundsInLocal().getHeight());
			event.consume();
		});

		checkOutBox.setSpacing(20);
		checkOutBox.setPadding(new Insets(15, 15, 8, 15));
		checkOutBox.setMinHeight(226);
		checkOutBox.setMaxWidth(checkOutBoxWidth);

		checkOutBox.setStyle("-fx-border-color: #71717c; " + "-fx-border-width: 3px; " + "-fx-border-radius: 10px; "
				+ "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");

		checkOutBox.setOnMouseEntered(e -> {
			checkOutBox.setStyle("-fx-border-color: " + Main.themeOrange + "; " + "-fx-border-width: 3px; "
					+ "-fx-border-radius: 10px; " + "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");
		});

		checkOutBox.setOnMouseExited(e -> {
			checkOutBox.setStyle("-fx-border-color: #71717c; " + "-fx-border-width: 3px; " + "-fx-border-radius: 10px; "
					+ "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");
		});

		summaryLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		summaryLabel.setTextFill(Color.web("#cecfde"));

		totalPriceContainer.setAlignment(Pos.CENTER_LEFT);

		totalLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		totalLabel.setTextFill(Color.web(Main.themeWhite));

		cartTotalPriceLabel.setText("¥" + ItemController.formatCurrency(cartSubtotal));
		cartTotalPriceLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		cartTotalPriceLabel.setTextFill(Color.web(Main.themeOrange));

		divider.setTranslateY(-20);

		checkOutButton.setMinWidth(checkOutBoxWidth);
		checkOutButton.setTranslateY(-13);

		checkoutMsgLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
		checkoutMsgLabel.setTextFill(Color.web("#7a7a83"));
		checkoutMsgLabel.setTranslateY(-20);

		screen.setMinHeight(Main.tempScreenMinHeight);
	}

}
