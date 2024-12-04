package view;

import client.Main;
import controller.UserController;
import controller.WishlistController;
import factories.GUIComponentFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Item;

public class ItemDetailPage {

	public static void initCustomerItemDetailPage(Stage primaryStage, Item item) {
		VBox screen = new VBox();
		screen.setBackground(Main.defaultBg);
		screen.setAlignment(Pos.TOP_CENTER);
		screen.setSpacing(80);

		HBox content = new HBox();
		int contentSpacing = 0;
		content.setAlignment(Pos.TOP_CENTER);
		content.setSpacing(contentSpacing);
		content.setMaxWidth(Main.contentWidth);
		content.setMinHeight(Main.viewPortHeight - 300);

		VBox leftContent = new VBox();
		leftContent.setAlignment(Pos.TOP_CENTER);
		leftContent.setMinWidth((Main.contentWidth * 0.3));
		leftContent.setMaxWidth((Main.contentWidth * 0.3));

		Rectangle picture = new Rectangle(350, 350, Color.GRAY);
		picture.setArcHeight(30);
		picture.setArcWidth(30);

		VBox middleContent = new VBox();
		middleContent.setMinWidth((Main.contentWidth * 0.3));

		VBox topMiddleContent = new VBox();
		topMiddleContent.setMinHeight(230);
		topMiddleContent.setMaxHeight(230);

		VBox namePriceContainer = new VBox();

		Label itemTitle = new Label(item.getItemName());
		itemTitle.setWrapText(true);
		itemTitle.setPrefWidth(400);
		itemTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 28));
		itemTitle.setTextFill(Color.web(Main.themeWhite));

		Label itemPriceLabel = new Label("¥" + item.getItemPrice());
		itemPriceLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 45));
		itemPriceLabel.setTextFill(Color.web(Main.themeOrange));

		VBox itemDetailContainer = new VBox();

		HBox itemCategoryContainer = new HBox();

		itemCategoryContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Category : ", "#8a8da1"),
				GUIComponentFactory.createSrchMsgLbl(item.getItemCategory(), Main.themeOrange));
		HBox itemSizeContainer = new HBox();
		itemSizeContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Size : ", "#8a8da1"),
				GUIComponentFactory.createSrchMsgLbl(item.getItemSize(), Main.themeOrange));
		HBox itemAuthorContainer = new HBox();
		itemAuthorContainer.getChildren().addAll(GUIComponentFactory.createSrchMsgLbl("Uploaded by ", "#8a8da1"),
				GUIComponentFactory.createSrchMsgLbl(UserController.getSellerName(item.getSellerID()),
						Main.themeOrange));
		itemAuthorContainer.setTranslateY(20);

		itemDetailContainer.getChildren().addAll(itemCategoryContainer, itemSizeContainer, itemAuthorContainer);

		namePriceContainer.getChildren().addAll(itemTitle, itemPriceLabel);
		topMiddleContent.getChildren().addAll(namePriceContainer, itemDetailContainer);

		VBox bottomMiddleContent = new VBox();
		bottomMiddleContent.setMinHeight(400);
		bottomMiddleContent.setSpacing(5);

		Label detailLabel = new Label("Item Detail");
		detailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		detailLabel.setTextFill(Color.web(Main.themeOrange));

		Rectangle divider = new Rectangle(400, 1, Color.web(Main.themeOrange));

		Label itemDescLabel = new Label("nigga basket");
		itemDescLabel.setWrapText(true);
		itemDescLabel.setPrefWidth(400);

		itemDescLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, 17));
		itemDescLabel.setTextFill(Color.web(Main.themeWhite));

		bottomMiddleContent.getChildren().addAll(detailLabel, divider, itemDescLabel);

		VBox rightContent = new VBox();
		rightContent.setAlignment(Pos.TOP_LEFT);
		rightContent.setMinWidth((Main.contentWidth * 0.3));
		rightContent.setMaxWidth((Main.contentWidth * 0.3));
		rightContent.setSpacing(20);

		int qtySelectorWidth = 250;
		VBox saleContainer = new VBox();
		saleContainer.setAlignment(Pos.CENTER_LEFT);
		saleContainer.setPadding(new Insets(0, 0, 0, 15));
		saleContainer.setMaxWidth(qtySelectorWidth);
		saleContainer.setMinWidth(287);
		saleContainer.setMinHeight(50);
		saleContainer.setStyle(
				"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #b968e8, #2f3269); -fx-background-radius :10");

		Label saleLabel = new Label("入札する!!!");
		Font font = Font.loadFont(GUIComponentFactory.class.getResourceAsStream("/resources/fonts/MPLUS1Code-Bold.ttf"),
				20);

		saleLabel.setFont(font);
		saleLabel.setTextFill(Color.WHITE);

		saleContainer.getChildren().addAll(saleLabel);

		VBox qtySelector = new VBox();
		qtySelector.setSpacing(20);
		qtySelector.setPadding(new Insets(15, 15, 15, 15));
//		qtySelector.setStyle("-fx-background-radius: 10px; -fx-background-color : #4d4e61");
		qtySelector.setMinHeight(200);
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

		Label selectorLabel = new Label("Place bid OR Buy now!");
		selectorLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		selectorLabel.setTextFill(Color.web("#cecfde"));

		HBox buttonContainer = new HBox();
		buttonContainer.setSpacing(qtySelectorWidth * 0.1);
		Button buyNowButton = GUIComponentFactory.createButton("Buy Now");
		buyNowButton.setMinWidth(qtySelectorWidth * 0.45);
		buyNowButton.setTranslateY(23);
		buyNowButton.setOnAction(e -> {
			Alert confirmation = GUIComponentFactory.createConfirmation("Confirmation", "Buy this item now?",
					"*You will be charged 10 times the highest bid");
			confirmation.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					Alert notification = GUIComponentFactory.createNotification("Notification", "Transaction created!",
							"Please track your shipping progress regularly");
					notification.showAndWait();
				}
			});
		});

		Button bidButton = GUIComponentFactory.createButton("Place Bid");
		bidButton.setMinWidth(qtySelectorWidth * 0.45);
		bidButton.setTranslateY(23);
		bidButton.setOnAction(e -> {
//			CartHandler.addToCart(item, qtySpinner.getValue());
		});

		Button addToCartButton = GUIComponentFactory.createButton("Add to wishlist");
		addToCartButton.setMinWidth(qtySelectorWidth);
		addToCartButton.setTranslateY(23);
		addToCartButton.setOnAction(e -> {
			WishlistController.addWishlist(item.getitemID(), Main.currentUser.getUserID());
		});

		buttonContainer.getChildren().addAll(buyNowButton, bidButton);

		qtySelector.getChildren().addAll(selectorLabel, buttonContainer, addToCartButton);

		rightContent.getChildren().addAll(saleContainer, qtySelector);
		middleContent.getChildren().addAll(topMiddleContent, bottomMiddleContent);
		leftContent.getChildren().addAll(picture);
		content.getChildren().addAll(leftContent, middleContent, rightContent);
		screen.getChildren().addAll(GUIComponentFactory.createNavbar(primaryStage, "Search Items in CaLouselF Store"),
				content);

		Scene scene = new Scene(screen, Main.viewPortWidth, Main.viewPortHeight);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
