package view.general;

import java.util.ArrayList;

import client.Main;
import controller.ItemController;
import controller.TransactionController;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.domain.Item;

public class ItemDetailPage {

	public static ArrayList<String> bidList = new ArrayList<>();

	public static void loadPage(Item item) {
		VBox root = new VBox();
		root.setBackground(Main.defaultBg);
		root.setAlignment(Pos.TOP_CENTER);
		root.setSpacing(80);

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
				GUIComponentFactory.createSrchMsgLbl(UserController.getUsername(item.getSellerID()), Main.themeOrange));
		itemAuthorContainer.setTranslateY(20);

		itemDetailContainer.getChildren().addAll(itemCategoryContainer, itemSizeContainer, itemAuthorContainer);

		namePriceContainer.getChildren().addAll(itemTitle, itemPriceLabel);
		topMiddleContent.getChildren().addAll(namePriceContainer, itemDetailContainer);

		VBox bottomMiddleContent = new VBox();
		bottomMiddleContent.setMinHeight(400);
		bottomMiddleContent.setSpacing(5);

		Label detailLabel = new Label("Bid log");
		detailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		detailLabel.setTextFill(Color.web(Main.themeOrange));

		Rectangle divider = new Rectangle(400, 1, Color.web(Main.themeOrange));

		VBox bidLogContainer = new VBox();

		for (String bidMessage : bidList) {
			Label bidMessageLabel = new Label(bidMessage);
			bidMessageLabel.setWrapText(true);
			bidMessageLabel.setPrefWidth(500);

			bidMessageLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, 17));
			bidMessageLabel.setTextFill(Color.web(Main.themeWhite));
			bidLogContainer.getChildren().addAll(bidMessageLabel);
		}

		bottomMiddleContent.getChildren().addAll(detailLabel, divider, bidLogContainer);

		VBox rightContent = new VBox();
		rightContent.setAlignment(Pos.TOP_LEFT);
		rightContent.setMinWidth((Main.contentWidth * 0.3));
		rightContent.setMaxWidth((Main.contentWidth * 0.3));
		rightContent.setSpacing(20);

		int optionBoxWidth = 250;
		VBox decorContainer = new VBox();
		decorContainer.setAlignment(Pos.CENTER_LEFT);
		decorContainer.setPadding(new Insets(0, 0, 0, 15));
		decorContainer.setMaxWidth(optionBoxWidth);
		decorContainer.setMinWidth(287);
		decorContainer.setMinHeight(50);
		decorContainer.setStyle(
				"-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #b968e8, #2f3269); -fx-background-radius :10");

		Label saleLabel = new Label("入札する!!!");
		Font font = Font.loadFont(GUIComponentFactory.class.getResourceAsStream("/resources/fonts/MPLUS1Code-Bold.ttf"),
				20);

		saleLabel.setFont(font);
		saleLabel.setTextFill(Color.WHITE);

		decorContainer.getChildren().addAll(saleLabel);

		VBox optionBox = new VBox();
		optionBox.setSpacing(20);
		optionBox.setPadding(new Insets(15, 15, 15, 15));
		optionBox.setMinHeight(200);
		optionBox.setMaxWidth(optionBoxWidth);

		optionBox.setStyle("-fx-border-color: #71717c; " + "-fx-border-width: 3px; " + "-fx-border-radius: 10px; "
				+ "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");

		optionBox.setOnMouseEntered(e -> {
			optionBox.setStyle("-fx-border-color: " + Main.themeOrange + "; " + "-fx-border-width: 3px; "
					+ "-fx-border-radius: 10px; " + "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");
		});

		optionBox.setOnMouseExited(e -> {
			optionBox.setStyle("-fx-border-color: #71717c; " + "-fx-border-width: 3px; " + "-fx-border-radius: 10px; "
					+ "-fx-background-radius: 10px; " + "-fx-background-color: #373745;");
		});

		Label selectorLabel = new Label("Place bid OR Buy now!");
		selectorLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		selectorLabel.setTextFill(Color.web("#cecfde"));

		HBox buttonContainer = new HBox();
		buttonContainer.setSpacing(optionBoxWidth * 0.1);
		Button buyNowButton = GUIComponentFactory.createButton("Buy Now");
		buyNowButton.setMinWidth(optionBoxWidth * 0.45);
		buyNowButton.setTranslateY(23);
		buyNowButton.setOnAction(e -> {
			Alert confirmation = GUIComponentFactory.createConfirmation("Confirmation", "Buy this item now?",
					"*You will be charged 10 times the highest bid");
			confirmation.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					TransactionController.purchaseItem(Main.currentUser.getUserID(), item.getitemID());
					Alert notification = GUIComponentFactory.createNotification("Notification", "Transaction created!",
							"Please track your shipping progress regularly");
					notification.showAndWait();
					ItemController.browseItem("", Main.defaultPlaceholder);
				}
			});
		});

		Button bidButton = GUIComponentFactory.createButton("Place Bid");
		bidButton.setMinWidth(optionBoxWidth * 0.45);
		bidButton.setTranslateY(23);
		bidButton.setOnAction(e -> {

			Stage bidWindow = new Stage();
			bidWindow.setTitle("Place Your Bid");

			// Create the layout
			VBox layout = new VBox(15);
			layout.setAlignment(Pos.CENTER);
			layout.setPadding(new Insets(20));
			layout.setStyle(
					"-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");

			// Label for instructions
			Label instructionLabel = new Label("Enter your bid amount:");
			instructionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			instructionLabel.setTextFill(Color.web("#333333"));

			// Integer field for bid amount
			TextField bidAmountField = new TextField();
			bidAmountField.setPromptText("Enter your bid");
			bidAmountField.setStyle(
					"-fx-padding: 8; -fx-border-color: #aaaaaa; -fx-border-radius: 5; -fx-background-radius: 5;");

			// Submit button
			Button submitBidButton = GUIComponentFactory.createButton("Submit Bid");

			submitBidButton.setOnAction(event -> {

				String bidAmount = bidAmountField.getText();
				try {
					int bid = Integer.parseInt(bidAmount);
					if (ItemController.validBidAmount(item, bid)) {
						Alert alert = GUIComponentFactory.createConfirmation("Confirmation",
								"Are you sure you want to place a bid of ¥" + bid + "?", "");
						alert.showAndWait().ifPresent(response -> {
							if (response == ButtonType.OK) {
								ItemController.offerPrice(item.getitemID(), bid);
								Alert notification = GUIComponentFactory.createNotification("Notification",
										"Your bid has been placed", "Watch out for another bidder!");
								notification.showAndWait();
								bidWindow.close();
								ItemController.viewDetail(item);
							}
						});

					} else {
						Alert alert = GUIComponentFactory.createError("Error", "Invalid amount!",
								"amount must be higher than the initial item price and the last bid");
						alert.showAndWait();
					}
					// Close the window after submission
				} catch (NumberFormatException ex) {
					Alert alert = GUIComponentFactory.createError("Error", "Enter a valid amount!", "");
					alert.showAndWait();
				}
			});

			layout.getChildren().addAll(instructionLabel, bidAmountField, submitBidButton);

			Scene scene = new Scene(layout, 350, 250);
			bidWindow.setScene(scene);
			bidWindow.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
			bidWindow.show();

		});

		Button addToWishlistButton = GUIComponentFactory.createButton("Add to wishlist");
		addToWishlistButton.setMinWidth(optionBoxWidth);
		addToWishlistButton.setTranslateY(23);
		addToWishlistButton.setOnAction(e -> {
			WishlistController.addWishlist(item.getitemID(), Main.currentUser.getUserID());
		});

		Button acceptOfferButton = GUIComponentFactory.createButton("Accept Bid");
		acceptOfferButton.setMinWidth(optionBoxWidth * 0.45);
		acceptOfferButton.setTranslateY(23);
		acceptOfferButton.setOnAction(e -> {
			ItemController.acceptOffer(item.getitemID());
		});

		Button declineOfferButton = GUIComponentFactory.createButton("Decline Bid");
		declineOfferButton.setMinWidth(optionBoxWidth * 0.45);
		declineOfferButton.setTranslateY(23);
		declineOfferButton.setOnAction(e -> {

			Stage declineWindow = new Stage();
			declineWindow.setTitle("Bid Decline Form");

			// Create the layout
			VBox layout = new VBox(15);
			layout.setAlignment(Pos.CENTER);
			layout.setPadding(new Insets(20));
			layout.setStyle(
					"-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");

			// Label for instructions
			Label instructionLabel = new Label("Enter decline reason :");
			instructionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			instructionLabel.setTextFill(Color.web("#333333"));

			TextField reasonField = new TextField();
			reasonField.setPromptText("Enter decline reason");
			reasonField.setStyle(
					"-fx-padding: 8; -fx-border-color: #aaaaaa; -fx-border-radius: 5; -fx-background-radius: 5;");

			// Submit button
			Button submitReasonButton = GUIComponentFactory.createButton("Submit Reason");
			submitReasonButton.setOnAction(event -> {
				boolean isDeclined = ItemController.declineOffer(item, item.getitemID(), reasonField.getText());
				if (isDeclined) {
					declineWindow.close();
				}
			});

			layout.getChildren().addAll(instructionLabel, reasonField, submitReasonButton);

			Scene scene = new Scene(layout, 350, 250);
			declineWindow.setScene(scene);
			declineWindow.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
			declineWindow.show();

		});

		if (Main.currentUser.getUserRole().equalsIgnoreCase("Buyer")) {
			buttonContainer.getChildren().addAll(buyNowButton, bidButton);
			optionBox.getChildren().addAll(selectorLabel, buttonContainer, addToWishlistButton);
		} else {

			if (!ItemController.bidExists(item.getitemID()) || !item.getItemStatus().equalsIgnoreCase("Approved")) {
				styleInactiveBtn(declineOfferButton);
				styleInactiveBtn(acceptOfferButton);
			}
			selectorLabel.setText("Accept or Decline Bid");
			buttonContainer.getChildren().addAll(acceptOfferButton, declineOfferButton);
			optionBox.getChildren().addAll(selectorLabel, buttonContainer);
		}

		rightContent.getChildren().addAll(decorContainer, optionBox);
		middleContent.getChildren().addAll(topMiddleContent, bottomMiddleContent);
		leftContent.getChildren().addAll(picture);
		content.getChildren().addAll(leftContent, middleContent, rightContent);
		root.getChildren().addAll(GUIComponentFactory.createNavbar("Search Items in CaLouselF Store"), content);

		Main.switchRoot(root);
	}

	public static void styleActiveBtn(Button btn) {
		btn.setStyle("-fx-background-color: " + Main.themeOrange
				+ "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

		btn.setOnMouseEntered(e -> {
			btn.setStyle(
					"-fx-background-color: #C67025; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		});
		btn.setOnMouseExited(e -> {
			btn.setStyle("-fx-background-color: " + Main.themeOrange
					+ "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		});
	}

	public static void styleInactiveBtn(Button btn) {
		btn.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

	}

}
