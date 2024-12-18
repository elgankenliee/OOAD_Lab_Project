package factories;

import client.Main;
import controller.ItemController;
import controller.TransactionController;
import controller.UserController;
import controller.WishlistController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.domain.Item;
import routes.Route;

public class GUIComponentFactory {

	private static int formWidth = 500;

	public static Label createLabel(String text, int size) {
		Label newLabel = new Label(text);
		newLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, size));
		newLabel.setTextFill(Color.GREY);
		newLabel.setTranslateY(6);
		return newLabel;
	}

	public static VBox createRegisterForm() {
		VBox registerWindow = new VBox();
		registerWindow.setSpacing(10);
		registerWindow.setAlignment(Pos.CENTER_LEFT);
		registerWindow
				.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(-20))));
		registerWindow.setMaxWidth(350);
		registerWindow.setMinHeight(200);

		Label titleLabel = new Label("Register");
		titleLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));

		Rectangle divider = new Rectangle(formWidth / 2, 2);

		TextField nameField = new TextField();
		PasswordField passField = new PasswordField();
		TextField numField = new TextField();
		TextField addressField = new TextField();

		RadioButton customerRadioButton = new RadioButton("Customer");
		RadioButton sellerRadioButton = new RadioButton("Seller");
		RadioButton otherRadioButton = new RadioButton("Toyota Agya");

		ToggleGroup roleToggleGroup = new ToggleGroup();
		HBox radioGroup = new HBox(10);
		radioGroup.getChildren().addAll(customerRadioButton, sellerRadioButton, otherRadioButton);
		customerRadioButton.setToggleGroup(roleToggleGroup);
		sellerRadioButton.setToggleGroup(roleToggleGroup);
		otherRadioButton.setToggleGroup(roleToggleGroup);

		CheckBox termsCheckbox = new CheckBox("I accept the");
		Hyperlink tncLink = new Hyperlink("Terms and Conditions");
//		tncLink.setOnAction(e -> TermsAndConditionPage.showTermsConditions());

		HBox tncBox = new HBox(5);
		tncBox.setAlignment(Pos.BASELINE_LEFT);
		tncBox.getChildren().addAll(termsCheckbox, tncLink);

		HBox linkContainer = new HBox(3);
		Label signInLabel = new Label("Already have an account? Sign in ");
		signInLabel.setTextFill(Color.GRAY);
		Hyperlink registerLink = new Hyperlink("Here!");
		registerLink.setOnAction(e -> Route.redirectLoginPage());
		linkContainer.getChildren().addAll(signInLabel, registerLink);
		linkContainer.setAlignment(Pos.CENTER);

		Button registerButton = createButton("Register");
		registerButton.setMaxWidth(formWidth);

		// triggers the checkAccountValidation method from the UserController class
		registerButton.setOnAction(
				e -> UserController.checkAccountValidation(nameField.getText(), passField.getText(), numField.getText(),
						addressField.getText(), roleToggleGroup.getSelectedToggle(), termsCheckbox.isSelected()));

		registerWindow.getChildren().addAll(titleLabel, divider, createLabel("Username", 14), nameField,
				createLabel("Password", 14), passField, createLabel("Phone Number (+62)", 14), numField,
				createLabel("Address", 14), addressField, createLabel("Role", 14), radioGroup, tncBox, registerButton,
				linkContainer);

		registerWindow.setTranslateY(-60);
		// triggers the checkAccountValidation method from the UserController class
		registerWindow.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				UserController.checkAccountValidation(nameField.getText(), passField.getText(), numField.getText(),
						addressField.getText(), roleToggleGroup.getSelectedToggle(), termsCheckbox.isSelected());
			}
		});

		return registerWindow;

	}

	public static VBox createLoginForm() {

		VBox loginWindow = new VBox();
		loginWindow.setSpacing(10);
		loginWindow.setAlignment(Pos.CENTER_LEFT);
		loginWindow
				.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(-20))));
		loginWindow.setMaxWidth(350);
		loginWindow.setMinHeight(200);

		Label titleLabel = new Label("Login");
		titleLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));

		Rectangle divider = new Rectangle(formWidth / 2, 2);

		TextField nameField = new TextField();
		nameField.setMinHeight(30);
		PasswordField passField = new PasswordField();
		passField.setMinHeight(30);

		HBox linkContainer = new HBox();
		Label registerLabel = new Label("Are you new? Register ");
		registerLabel.setTextFill(Color.GRAY);

		Hyperlink registerLink = new Hyperlink("Here!");
		registerLink.setTranslateX(-3);

		registerLink.setOnAction(e -> {
			Route.redirectRegisterPage();
		});
		linkContainer.getChildren().addAll(registerLabel, registerLink);
		linkContainer.setAlignment(Pos.CENTER);

		Button loginButton = createButton("Login");
		loginButton.setMaxWidth(formWidth);

		// Triggers the login logic from UserController using the input from nameField
		// and passField
		loginButton.setOnAction(e -> {
			UserController.login(nameField.getText(), passField.getText());
		});

		loginWindow.getChildren().addAll(titleLabel, divider, createLabel("Username", 14), nameField,
				createLabel("Password", 14), passField, loginButton, linkContainer);

		loginWindow.setTranslateY(-60);

		// Triggers the login logic from UserController when the ENTER key is pressed
		loginWindow.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				UserController.login(nameField.getText(), passField.getText());
			}
		});

		return loginWindow;
	}

	public static VBox createLogo() {
		Label title = new Label("CaLouselF");
		Label titleAccent = new Label("ナイトマーケット");
		title.setStyle("-fx-text-fill: white;-fx-font-size:120px; -fx-font-weight:bold; -fx-font-style: italic;");

		Font font = Font.loadFont(GUIComponentFactory.class.getResourceAsStream("/resources/fonts/MPLUS1Code-Bold.ttf"),
				55);

		font = Font.font(font.getFamily(), FontWeight.EXTRA_BOLD, font.getSize());
		titleAccent.setFont(font);
		titleAccent.setStyle("-fx-text-fill: " + Main.themeOrange + ";");

		VBox titleContainer = new VBox();

		titleContainer.setAlignment(Pos.CENTER);
		titleContainer.getChildren().addAll(title, titleAccent);

		title.setTranslateY(45);
		titleAccent.setTranslateX(110);

		titleContainer.setTranslateY(-90);
		return titleContainer;
	}

	public static VBox createNavbar(String placeholder) {

		VBox navbarContainer = new VBox();
		navbarContainer.setAlignment(Pos.BOTTOM_CENTER);
		navbarContainer.setBackground(Main.defaultBg);
		navbarContainer.setMinHeight(180);
		navbarContainer.setMinWidth(Main.viewPortWidth);

		int navbarContentSpacing = 30;
		int navbarHeight = 110;
		HBox navbar = new HBox();
		navbar.setAlignment(Pos.CENTER_LEFT);
		navbar.setSpacing(80);
		navbar.setMaxWidth(Main.viewPortWidth - 350);
		navbar.setMinHeight(navbarHeight);
		navbar.setStyle("-fx-background-radius : 60; -fx-background-color:" + Main.navbarGrey + "");

		TextField searchBar = new TextField(placeholder);
		double searchBarHeight = 40;
		searchBar.setMinWidth(Main.viewPortWidth / 2.3);
		searchBar.setMinHeight(searchBarHeight);
		searchBar.setStyle(
				"-fx-background-color : #545877; -fx-text-fill : #F3F3F3; -fx-font-weight : bold; -fx-font-size : 14px");

		Button searchButton = createNavbarButton("Search");

		// Triggers the browseItem method from ItemController when the search button is
		// clicked
		searchButton.setOnAction(e -> {
			ItemController.browseItem(searchBar.getText(), searchBar.getText());
		});
		searchButton.setStyle(
				"-fx-background-color: #7278B2; -fx-text-fill: #F3F3F3; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family : helvetica");

		searchButton.setOnMouseEntered(e -> {
			searchButton.setStyle(
					"-fx-background-color: #646A9B; -fx-text-fill: #F3F3F3; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family : helvetica");
		});
		searchButton.setOnMouseExited(e -> {
			searchButton.setStyle(
					"-fx-background-color: #7278B2; -fx-text-fill: #F3F3F3; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family : helvetica");
		});

		searchButton.setMinHeight(searchBarHeight - 2);
		searchButton.setMinWidth(100);
		searchButton.setTranslateX(-1 * navbarContentSpacing - 101);
		searchButton.setTranslateY(-1);

		// Triggers the browseItem method from ItemController when the Enter key is
		// pressed while the navbarContainer is focused
		navbarContainer.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				ItemController.browseItem(searchBar.getText(), searchBar.getText());
			}
		});

		VBox navbarLogo = createNavbarLogo();

		HBox leftNavbarContents = new HBox();
		leftNavbarContents.setSpacing(navbarContentSpacing);
		leftNavbarContents.setAlignment(Pos.CENTER_LEFT);
		leftNavbarContents.setTranslateX(50);

		HBox rightNavbarContents = new HBox();
		rightNavbarContents.setAlignment(Pos.CENTER_LEFT);
		rightNavbarContents.setTranslateX(-80);
		rightNavbarContents.setSpacing(navbarContentSpacing / 1.2);

		Rectangle divider = new Rectangle(2, 0.7 * navbarHeight);
		divider.setFill(Color.web("#545877"));

		Button wishlistButton = createNavbarButton("My Wishlist");
		wishlistButton.setMinHeight(searchBarHeight);

		// Calls the initWishlist method from WishlistController, passing empty string
		// and a search prompt as parameters when wishlistButton is clicked
		wishlistButton.setOnAction(e -> {
			WishlistController.initWishlist("", "Search Items in CaLouselF Store");
		});

		Button historyButton = createNavbarButton("History");
		historyButton.setMinHeight(searchBarHeight);

		// Triggers the viewHistory method from TransactionController to display the
		// transaction history of the current user when historyButton is clicked
		historyButton.setOnAction(e -> {
			TransactionController.viewHistory(Main.currentUser.getUserID());
		});

		Button allItemsButton = createNavbarButton("My Uploads");
		allItemsButton.setMinHeight(searchBarHeight);

		// [FOR SELLER ONLY]Triggers the viewItem method from ItemController to display
		// all items when
		// allItemsButton is clicked
		allItemsButton.setOnAction(e -> {
			ItemController.viewItem();
		});

		Button approvedItemsButton = createNavbarButton("My Approved Items");
		approvedItemsButton.setMinHeight(searchBarHeight);

		// [FOR SELLER ONLY] Triggers the viewAcceptedItem method from ItemController to
		// display approved items for the seller when approvedItemsButton is clicked
		approvedItemsButton.setOnAction(e -> {
			ItemController.viewAcceptedItem();
		});

		Button offeredItemsButton = createNavbarButton("Active Bids");
		offeredItemsButton.setMinHeight(searchBarHeight);

		// [FOR SELLER ONLY] Triggers the viewOfferItem method from ItemController to
		// display items that have been offered for sale when offeredItemsButton is
		// clicked
		offeredItemsButton.setOnAction(e -> {
			ItemController.viewOfferItem();
		});

		Button logoutButton = createButton("Log Out");
		logoutButton.setMinHeight(searchBarHeight);
		logoutButton.setStyle(
				"-fx-background-color: #ff2121; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

		logoutButton.setOnMouseEntered(e -> {
			logoutButton.setStyle(
					"-fx-background-color: #c91a1a; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		});
		logoutButton.setOnMouseExited(e -> {
			logoutButton.setStyle(
					"-fx-background-color: #ff2121; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		});

		logoutButton.setOnAction(e -> {
			UserController.logout();
		});

		if (Main.currentUser.getUserRole().equalsIgnoreCase("buyer")) {
			leftNavbarContents.getChildren().addAll(navbarLogo, searchBar, searchButton);
			rightNavbarContents.getChildren().addAll(divider, wishlistButton, historyButton, logoutButton);
			navbarLogo.setOnMouseClicked(e -> {
				ItemController.browseItem("", Main.defaultPlaceholder);
			});
		} else {
			leftNavbarContents.getChildren().addAll(navbarLogo);
			rightNavbarContents.getChildren().addAll(divider, allItemsButton, approvedItemsButton, offeredItemsButton,
					logoutButton);
			rightNavbarContents.setTranslateX(20);
			navbarLogo.setOnMouseClicked(e -> {
				ItemController.viewItem();
			});
		}

		navbar.getChildren().addAll(leftNavbarContents, rightNavbarContents);
		navbarContainer.getChildren().addAll(navbar);

		return navbarContainer;
	}

	private static Button createNavbarButton(String buttonText) {
		Button newButton = new Button(buttonText);
		newButton.setStyle(
				"-fx-background-color: transparent; -fx-text-fill: #F3F3F3; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family : helvetica");

		newButton.setOnMouseEntered(e -> {
			newButton.setStyle(
					"-fx-background-color: transparent; -fx-text-fill: #d6d6d6; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family : helvetica");
		});
		newButton.setOnMouseExited(e -> {
			newButton.setStyle(
					"-fx-background-color: transparent; -fx-text-fill: #F3F3F3; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family : helvetica");
		});
		return newButton;
	}

	public static Button createButton(String buttonText) {
		Button newButton = new Button(buttonText);

		newButton.setStyle("-fx-background-color: " + Main.themeOrange
				+ "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

		newButton.setOnMouseEntered(e -> {
			newButton.setStyle(
					"-fx-background-color: #C67025; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		});
		newButton.setOnMouseExited(e -> {
			newButton.setStyle("-fx-background-color: " + Main.themeOrange
					+ "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		});
		return newButton;
	}

	public static VBox createNavbarLogo() {
		Label title = new Label("CaLouselF");
		Label titleAccent = new Label("Store");
		if (Main.currentUser.getUserRole().equalsIgnoreCase("seller")) {
			titleAccent.setText("Seller");
		}
		title.setStyle("-fx-text-fill: white	;-fx-font-size:30px; -fx-font-weight:bold; -fx-font-style: italic;");
		titleAccent.setStyle("-fx-text-fill: " + Main.themeOrange
				+ ";-fx-font-size:45px; -fx-font-weight:bold; -fx-font-style: italic;");

		VBox titleContainer = new VBox();
		titleContainer.setAlignment(Pos.CENTER);
		titleContainer.getChildren().addAll(title, titleAccent);
		titleContainer.setSpacing(-35);

		title.setTranslateX(-25);
		return titleContainer;
	}

	public static Alert createError(String title, String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	public static Alert createNotification(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	public static Alert createConfirmation(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	public static Label createSrchMsgLbl(String message, String color) {
		Label resultMsgLabel = new Label(message);

		resultMsgLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		resultMsgLabel.setTextFill(Color.web(color));
		return resultMsgLabel;
	}

	public static VBox createHomePageItemCard(Item item) {
		VBox itemBox = new VBox();
		itemBox.setAlignment(Pos.CENTER);
		itemBox.setBackground(new Background(new BackgroundFill(Color.web(Main.navbarGrey), null, null)));
		itemBox.setMaxWidth(700);
		itemBox.setMinHeight(170);

		// Triggers the viewDetail method from ItemController to display detailed
		// information of the clicked item in itemBox
		itemBox.setOnMouseClicked(e -> ItemController.viewDetail(item));

		itemBox.setOnMouseEntered(e -> {
			itemBox.setBackground(new Background(new BackgroundFill(Color.web("#252633"), null, null)));
		});
		itemBox.setOnMouseExited(e -> {
			itemBox.setBackground(new Background(new BackgroundFill(Color.web(Main.navbarGrey), null, null)));
		});

		HBox content = new HBox();
		content.setSpacing(20);
		content.setAlignment(Pos.CENTER);

		Rectangle picture = new Rectangle(130, 130, Color.GRAY);
		picture.setArcHeight(10);
		picture.setArcWidth(10);

		VBox itemDetail = new VBox();
		int itemDetailHeight = 120;
		itemDetail.setMaxHeight(itemDetailHeight);
		itemDetail.setMinWidth(500);
		itemDetail.setMaxWidth(500);

		HBox titleContainer = new HBox();
		titleContainer.setMinHeight(itemDetailHeight * 0.5);
		titleContainer.setAlignment(Pos.CENTER_LEFT);

		Label itemTitleLabel = new Label(item.getItemName());
		itemTitleLabel.setWrapText(true);
		itemTitleLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 23));
		itemTitleLabel.setTextFill(Color.web(Main.themeWhite));

		Label itemSizeLabel = new Label(" (" + item.getItemSize() + ")");
		itemSizeLabel.setWrapText(true);
		itemSizeLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 23));
		itemSizeLabel.setTextFill(Color.web(Main.themeOrange));

		titleContainer.getChildren().addAll(itemTitleLabel, itemSizeLabel);

		HBox priceCategoryContainer = new HBox();
		priceCategoryContainer.setAlignment(Pos.CENTER_LEFT);
		priceCategoryContainer.setSpacing(10);

		Label itemCategoryLabel = new Label("¥" + item.getItemPrice());
		itemCategoryLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		itemCategoryLabel.setTextFill(Color.web(Main.themeOrange));

		Button categoryLabel = new Button(item.getItemCategory());
		categoryLabel.setMinHeight(20);
		categoryLabel.setMinWidth(50);
		categoryLabel.setStyle(
				"-fx-background-color : #ff2121; -fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");

		priceCategoryContainer.getChildren().addAll(itemCategoryLabel, categoryLabel);

		itemDetail.getChildren().addAll(titleContainer, priceCategoryContainer);

		content.getChildren().addAll(picture, itemDetail);
		itemBox.getChildren().addAll(content);
		return itemBox;
	}

	public static HBox createCartItemBox(Item item, ScrollPane wishlistPageScrollPane) {
		HBox itemBox = new HBox();
		itemBox.setAlignment(Pos.CENTER_LEFT);
		itemBox.setBackground(new Background(new BackgroundFill(Color.web(Main.navbarGrey), null, null)));
		itemBox.setMaxWidth(710);
		itemBox.setMinHeight(170);

		// Triggers the viewDetail method from ItemController to display detailed
		// information of the clicked item in itemBox
		itemBox.setOnMouseClicked(e -> ItemController.viewDetail(item));

		itemBox.setOnMouseEntered(e -> {
			itemBox.setBackground(new Background(new BackgroundFill(Color.web("#252633"), null, null)));
		});
		itemBox.setOnMouseExited(e -> {
			itemBox.setBackground(new Background(new BackgroundFill(Color.web(Main.navbarGrey), null, null)));
		});

		HBox content = new HBox();
		content.setSpacing(20);
		content.setAlignment(Pos.CENTER_LEFT);
		content.setTranslateX(20);

		Rectangle picture = new Rectangle(130, 130, Color.GRAY);
		picture.setArcHeight(10);
		picture.setArcWidth(10);

		VBox itemDetail = new VBox();
		int itemDetailHeight = 120;
		itemDetail.setMaxHeight(itemDetailHeight);
		itemDetail.setMinWidth(520);
		itemDetail.setMaxWidth(520);

		VBox titleContainer = new VBox();
		titleContainer.setMinHeight(itemDetailHeight * 0.5);
		titleContainer.setAlignment(Pos.CENTER_LEFT);

		Label itemTitle = new Label(item.getItemName());
		itemTitle.setWrapText(true);
		itemTitle.setPrefWidth(450);
		itemTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 23));
		itemTitle.setTextFill(Color.web(Main.themeWhite));

		titleContainer.getChildren().addAll(itemTitle);

		HBox priceStockContainer = new HBox();
		priceStockContainer.setAlignment(Pos.CENTER_LEFT);
		priceStockContainer.setSpacing(10);

		Button categoryLabel = new Button(item.getItemCategory());
		categoryLabel.setMinHeight(20);
		categoryLabel.setStyle(
				"-fx-background-color : #ff2121; -fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");

		Label itemPriceLabel = new Label("¥" + item.getItemPrice());
		itemPriceLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		itemPriceLabel.setTextFill(Color.web(Main.themeOrange));

		Button removeButton = new Button(" x ");
		removeButton.setMinHeight(20);
		removeButton.setMinWidth(50);
		removeButton.setTranslateX(-20);
		removeButton.setTranslateY(20);

		// Displays a confirmation dialog before removing the item from the wishlist. If
		// confirmed, this triggers removeWishlist from WishlistController, then a
		// notification is shown.
		// If canceled, the action is aborted and a message is logged.
		removeButton.setOnAction(e -> {
			Alert confirmation = createConfirmation("Item Removal Confirmation",
					"Do you want to remove this item from your wishlist?", "Please confirm your choice.");
			confirmation.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					WishlistController.removeWishlist(item.getitemID(), Main.currentUser.getUserID());
					Alert notification = GUIComponentFactory.createNotification("Notification",
							"Item has been removed from your wishlist", "");
					notification.show();
					WishlistController.initWishlist("", Main.defaultPlaceholder);
				} else {
					// User canceled the action
					System.out.println("Bid canceled by user.");
				}
			});
		});
		removeButton.setStyle(
				"-fx-background-color : #ff2121; -fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");
		removeButton.setOnMouseEntered(e -> {
			removeButton.setStyle(
					"-fx-background-color : #c91a1a; -fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");
		});

		removeButton.setOnMouseExited(e -> {
			removeButton.setStyle(
					"-fx-background-color : #ff2121; -fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");
		});

		HBox stockLabelContainer = new HBox();
		stockLabelContainer.setAlignment(Pos.CENTER);

		Label stockLabel = new Label("Quantity ");
		stockLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 19));
		stockLabel.setTextFill(Color.web("#717489"));

		Label stockQtyLabel = new Label(Integer.toString(123));
		stockQtyLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 19));
		stockQtyLabel.setTextFill(Color.web(Main.themeOrange));

		stockLabelContainer.getChildren().addAll(stockLabel, stockQtyLabel);

		VBox rightContent = new VBox();
		rightContent.setAlignment(Pos.TOP_RIGHT);
		rightContent.setMinHeight(170);
		rightContent.setMinWidth(80);
		rightContent.getChildren().addAll(removeButton);

		priceStockContainer.getChildren().addAll(itemPriceLabel, categoryLabel);

		itemDetail.getChildren().addAll(titleContainer, priceStockContainer);

		content.getChildren().addAll(picture, itemDetail);
		itemBox.getChildren().addAll(content, rightContent);

		return itemBox;
	}

}
