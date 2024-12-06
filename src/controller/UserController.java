package controller;

import java.sql.SQLException;

import client.Main;
import factories.GUIComponentFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import model.domain.User;
import routes.Route;
import util.AESHelper;
import util.Connect;

public class UserController {

	private static Connect db = Connect.getInstance();
	private static Boolean loggedInAsBuyer = false;
	private static Boolean loggedInAsSeller = false;

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	public static String getSellerName(String sellerID) {
		String query = "SELECT Username FROM Users WHERE UserID = " + sellerID + ";";
		db.rs = db.execQuery(query);
		try {
			if (db.rs.next()) {
				return db.rs.getString("Username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "anonymous";
	}

	public static boolean checkCredentials(String username, String password) {
		String query = "SELECT * FROM Users WHERE username = '" + username + "'";
		db.rs = db.execQuery(query);

		try {
			if (db.rs.next()) {

				int userID = db.rs.getInt("UserID");
				String dbUsername = db.rs.getString("Username");
				String dbPassword = db.rs.getString("Password");
				String userPhone = db.rs.getString("PhoneNumber");
				String userAddress = db.rs.getString("Address");
				String userRole = db.rs.getString("Role");

				if (dbPassword.equals(AESHelper.encrypt(password, Main.AESencryptionKey))) {
					User user = new User(String.valueOf(userID), dbUsername, dbPassword, userPhone, userAddress,
							userRole);

					if (user.getUserRole().equalsIgnoreCase("seller")) {
						loggedInAsSeller = true;
					} else {
						loggedInAsBuyer = true;
					}

					Main.currentUser = user;
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean uniqueUser(String username) {

		String query = "SELECT username FROM Users WHERE username = '" + username + "'";
		db.rs = db.execQuery(query);

		try {
			if (db.rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static void logout(Stage primaryStage) {
		Main.currentUser = null;
		loggedInAsBuyer = loggedInAsSeller = false;
		Route.redirectLoginPage(primaryStage);
	}

	public static void login(Stage primaryStage, String username, String password) {

		if (username.equals("") || password.equals("")) {

			Alert alert = GUIComponentFactory.createError("Invalid Login", "Log in failed",
					"Please fill out all fields.");
			alert.showAndWait();

			return;
		}

		loggedInAsBuyer = false;
		loggedInAsSeller = false;

		checkCredentials(username, password);

		if (!loggedInAsBuyer && !loggedInAsSeller) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Login");
			alert.setHeaderText("Wrong Credentials!");
			alert.setContentText("You entered a wrong username or password.");
			alert.showAndWait();
		}

		if (loggedInAsBuyer) {
			Route.redirectBuyerHomePage(primaryStage, "", Main.defaultPlaceholder);
		} else if (loggedInAsSeller) {
//			SellerHomePage.initSellerHome();
		}

	}

	private static boolean isSpecial(String str) {

		return str.contains("!") || str.contains("@") || str.contains("#") || str.contains("$") || str.contains("%")
				|| str.contains("^") || str.contains("&") || str.contains("*");
	}

	private static boolean isNumeric(String phoneNum) {
		if (phoneNum == null || phoneNum.isEmpty()) {
			return false;
		}
		phoneNum = phoneNum.substring(1);
		for (char c : phoneNum.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	public static void checkAccountValidation(Stage primaryStage, String username, String password, String phoneNum,
			String address, Toggle role, boolean isAgree) {

		System.out.println(role);
		if (username.isEmpty()) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"username must be filled");
			alert.showAndWait();
		} else if (!uniqueUser(username)) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"username is already taken. Please use other address.");
			alert.showAndWait();
		} else if (username.length() < 3) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"username must be atleast 3 characters long");
			alert.showAndWait();
		} else if (password.isEmpty()) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"Password must be filled");
			alert.showAndWait();
		} else if (password.length() < 8) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"Password must contains at least 8 characters");
			alert.showAndWait();
		} else if (!isSpecial(password)) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"Password must contains special character");
			alert.showAndWait();
		} else if (phoneNum == null) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"Please type your phone number");
			alert.showAndWait();
		} else if (!phoneNum.startsWith("+62")) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"Phone number must starts with +62");
			alert.showAndWait();
		} else if (!isNumeric(phoneNum)) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"Phone number must be numeric");
			alert.showAndWait();
		} else if (phoneNum.length() != 12) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"Phone number must be 9 numbers long (exclude +62)");
			alert.showAndWait();
		} else if (address.isEmpty()) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"Address must be filled");
			alert.showAndWait();
		} else if (role == null) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error", "Role must be selected");
			alert.showAndWait();
		} else if (!isAgree) {
			Alert alert = GUIComponentFactory.createError("Register Failed", "Register Error",
					"You must agree to the terms and conditions");
			alert.showAndWait();
		} else {
			String encryptedPassword = AESHelper.encrypt(password, Main.AESencryptionKey);
			String userRole = role.toString().contains("'Seller'") ? "Seller" : "Buyer";
			register(username, encryptedPassword, phoneNum, address, userRole);
			Route.redirectLoginPage(primaryStage);
		}

	}

	public static void register(String username, String password, String phoneNumber, String address, String role) {

		String query = String.format(
				"INSERT INTO Users (Username, Password, PhoneNumber, Address, Role) VALUES ('%s', '%s', '%s', '%s', '%s');",
				username, password, phoneNumber, address, role);
		db.execUpdate(query);

		Alert notification = GUIComponentFactory.createNotification("Notification", "Your account has been created",
				"Please login with your credentials");
		notification.showAndWait();
	}
}
