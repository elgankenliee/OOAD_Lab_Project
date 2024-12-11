package model.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import client.Main;
import util.AESHelper;
import util.Connect;

public class User {

	private static Connect db = Connect.getInstance();

	private String userID;
	private String username;
	private String userPassword;
	private String userPhone;
	private String userAddress;
	private String userRole;

	public User(String userID, String username, String userPassword, String userPhone, String userAddress,
			String userRole) {
		super();
		this.userID = userID;
		this.username = username;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.userAddress = userAddress;
		this.userRole = userRole;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	// Retrieves the username of a seller based on the seller's UserID. If no
	// username is found, returns "anonymous".
	public static String getUsername(String sellerID) {
		String query = "SELECT Username FROM Users WHERE UserID = ?;";
		PreparedStatement ps = db.addQuery(query);

		try {
			ps.setString(1, sellerID);
			db.rs = ps.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (db.rs.next()) {
				return db.rs.getString("Username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "anonymous";
	}

	// Validates user login by checking if the entered username and encrypted
	// password match the stored values in the database.
	// If successful, creates a User object for the current session and returns the
	// user's role.
	// If the login fails, returns "invalid".
	public static String login(String username, String password) {
		String query = "SELECT * FROM Users WHERE username = ?";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, username);
			db.rs = ps.executeQuery();

			if (db.rs.next()) {

				int userID = db.rs.getInt("UserID");
				String dbUsername = db.rs.getString("Username");
				String dbPassword = db.rs.getString("Password");
				String userPhone = db.rs.getString("PhoneNumber");
				String userAddress = db.rs.getString("Address");
				String userRole = db.rs.getString("Role");

				if (dbPassword.equals(AESHelper.encrypt(password, Main.AESencryptionKey))) {
					Main.currentUser = new User(String.valueOf(userID), dbUsername, dbPassword, userPhone, userAddress,
							userRole);
					return Main.currentUser.getUserRole();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "invalid";
	}

	// Registers a new user by inserting their details (username, password, phone
	// number, address, and role) into the database.
	public static void register(String username, String password, String phoneNumber, String address, String role) {
		String query = "INSERT INTO Users (Username, Password, PhoneNumber, Address, Role) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, phoneNumber);
			ps.setString(4, address);
			ps.setString(5, role);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Checks if the given username already exists in the database. Returns `false`
	// if the username is found, indicating it is not unique. Otherwise, returns
	// `true` if the username is unique.
	public static boolean uniqueUser(String username) {
		String query = "SELECT username FROM Users WHERE username = ?";
		PreparedStatement ps = db.addQuery(query);
		try {
			ps.setString(1, username);
			db.rs = ps.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (db.rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

}
