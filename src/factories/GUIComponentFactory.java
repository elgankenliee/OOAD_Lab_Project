package factories;

import client.Main;
import controller.ItemController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
import javafx.stage.Stage;
import model.Item;
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

	public static VBox createRegisterForm(Stage primaryStage) {
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
		PasswordField confirmPassField = new PasswordField();
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
		registerLink.setOnAction(e -> Route.redirectLoginPage(primaryStage));
		linkContainer.getChildren().addAll(signInLabel, registerLink);
		linkContainer.setAlignment(Pos.CENTER);

		Button registerButton = createButton("Register");
		registerButton.setMaxWidth(formWidth);
		registerButton.setOnAction(e -> UserController.checkAccountValidation(primaryStage, nameField.getText(),
				passField.getText(), numField.getText(), addressField.getText(), roleToggleGroup.getSelectedToggle(),
				termsCheckbox.isSelected()));

		registerWindow.getChildren().addAll(titleLabel, divider, createLabel("Username", 14), nameField,
				createLabel("Password", 14), passField, createLabel("Phone Number (+62)", 14), numField,
				createLabel("Address", 14), addressField, createLabel("Role", 14), radioGroup, tncBox, registerButton,
				linkContainer);

		registerWindow.setTranslateY(-60);
		registerWindow.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				UserController.checkAccountValidation(primaryStage, nameField.getText(), passField.getText(),
						numField.getText(), addressField.getText(), roleToggleGroup.getSelectedToggle(),
						termsCheckbox.isSelected());
			}
		});

		return registerWindow;

	}

	public static VBox createLoginForm(Stage primaryStage) {

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
		registerLink.setOnAction(e -> Route.redirectRegisterPage(primaryStage));
		linkContainer.getChildren().addAll(registerLabel, registerLink);
		linkContainer.setAlignment(Pos.CENTER);

		Button loginButton = createButton("Login");
		loginButton.setMaxWidth(formWidth);

		loginButton.setOnAction(e -> {
			UserController.login(primaryStage, nameField.getText(), passField.getText());
		});

		loginWindow.getChildren().addAll(titleLabel, divider, createLabel("Username", 14), nameField,
				createLabel("Password", 14), passField, loginButton, linkContainer);

		loginWindow.setTranslateY(-60);

		loginWindow.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				UserController.login(primaryStage, nameField.getText(), passField.getText());
			}
		});

		nameField.setText("elgankenlie");

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

	public static VBox createNavbar(Stage primaryStage, String placeholder) {

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
		navbar.setMaxWidth(Main.viewPortWidth - 400);
		navbar.setMinHeight(navbarHeight);
		navbar.setStyle("-fx-background-radius : 60; -fx-background-color:" + Main.navbarGrey + "");

		TextField searchBar = new TextField(placeholder);
		double searchBarHeight = 40;
		searchBar.setMinWidth(Main.viewPortWidth / 2.3);
		searchBar.setMinHeight(searchBarHeight);
		searchBar.setStyle(
				"-fx-background-color : #545877; -fx-text-fill : #F3F3F3; -fx-font-weight : bold; -fx-font-size : 14px");

		Button searchButton = createNavbarButton("Search");
		searchButton.setOnAction(e -> {
			ItemController.browseItem(primaryStage, searchBar.getText(), searchBar.getText());
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

		navbarContainer.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				ItemController.browseItem(primaryStage, searchBar.getText(), searchBar.getText());
			}
		});

		VBox navbarLogo = createNavbarLogo();
		navbarLogo.setOnMouseClicked(e -> {
			ItemController.browseItem(primaryStage, "", Main.defaultPlaceholder);
		});

		HBox leftNavbarContents = new HBox();
		leftNavbarContents.setSpacing(navbarContentSpacing);
		leftNavbarContents.setAlignment(Pos.CENTER_LEFT);
		leftNavbarContents.getChildren().addAll(navbarLogo, searchBar, searchButton);
		leftNavbarContents.setTranslateX(50);

		HBox rightNavbarContents = new HBox();
		rightNavbarContents.setAlignment(Pos.CENTER_LEFT);
		rightNavbarContents.setTranslateX(-80);
		rightNavbarContents.setSpacing(navbarContentSpacing / 1.2);

		Rectangle divider = new Rectangle(2, 0.7 * navbarHeight);
		divider.setFill(Color.web("#545877"));

		Button cartButton = createNavbarButton("My Wishlist");
		cartButton.setMinHeight(searchBarHeight);
		cartButton.setOnAction(e -> {
//			CustomerCartPage.initCustomerCart(primaryStage, "", "Search Items in GoGoQuery Store");
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
			UserController.logout(primaryStage);
		});

		rightNavbarContents.getChildren().addAll(divider, cartButton, logoutButton);

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

	public static Label createSrchMsgLbl(String message, String color) {
		Label resultMsgLabel = new Label(message);

		resultMsgLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		resultMsgLabel.setTextFill(Color.web(color));
		return resultMsgLabel;
	}

	public static VBox createHomePageItemCard(Stage primaryStage, Item item) {
		VBox itemBox = new VBox();
		itemBox.setAlignment(Pos.CENTER);
		itemBox.setBackground(new Background(new BackgroundFill(Color.web(Main.navbarGrey), null, null)));
		itemBox.setMaxWidth(700);
		itemBox.setMinHeight(170);
		itemBox.setOnMouseClicked(e -> {
			ItemController.viewDetail(primaryStage, item);
		});

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
//		itemTitleLabel.setPrefWidth(450);
		itemTitleLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 23));
		itemTitleLabel.setTextFill(Color.web(Main.themeWhite));

		Label itemSizeLabel = new Label(" (" + item.getItemSize() + ")");
		itemSizeLabel.setWrapText(true);
//		itemSizeLabel.setPrefWidth(450);
		itemSizeLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 23));
		itemSizeLabel.setTextFill(Color.web(Main.themeOrange));

		titleContainer.getChildren().addAll(itemTitleLabel, itemSizeLabel);

		HBox priceCategoryContainer = new HBox();
		priceCategoryContainer.setAlignment(Pos.CENTER_LEFT);
		priceCategoryContainer.setSpacing(10);

		Label itemCategoryLabel = new Label("¥" + item.getItemPrice());
		itemCategoryLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		itemCategoryLabel.setTextFill(Color.web(Main.themeOrange));

		Button stockLeftLabel = new Button(item.getItemCategory());
		stockLeftLabel.setMinHeight(20);
		stockLeftLabel.setMinWidth(50);
		stockLeftLabel.setStyle(
				"-fx-background-color : #ff2121; -fx-font-family : Helvetica; -fx-font-weight : bold; -fx-font-size : 15px; -fx-text-fill : white");

		priceCategoryContainer.getChildren().addAll(itemCategoryLabel, stockLeftLabel);

		itemDetail.getChildren().addAll(titleContainer, priceCategoryContainer);

		content.getChildren().addAll(picture, itemDetail);
		itemBox.getChildren().addAll(content);
		return itemBox;
	}

	public static Spinner createSpinner(int minVal, int maxVal, int initVal) {
		Spinner<Integer> qtySpinner = new Spinner<>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minVal, maxVal,
				initVal);
		qtySpinner.setValueFactory(valueFactory);
		qtySpinner.setPrefWidth(100);
		qtySpinner.setPrefHeight(26);

		qtySpinner.setStyle(
				"-fx-background-color : #545877; -fx-text-fill : #F3F3F3; -fx-font-weight : bold; -fx-font-size : 14px");

		return qtySpinner;
	}

}
