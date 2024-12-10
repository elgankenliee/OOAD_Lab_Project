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

	public static String login(String username, String password) {
		String query = "SELECT * FROM Users WHERE username = ?";

		try (PreparedStatement ps = db.addQuery(query)) {
			ps.setString(1, username);
			db.rs = ps.executeQuery();

			if (db.rs.next()) {

				String role = null;

				int userID = db.rs.getInt("UserID");
				String dbUsername = db.rs.getString("Username");
				String dbPassword = db.rs.getString("Password");
				String userPhone = db.rs.getString("PhoneNumber");
				String userAddress = db.rs.getString("Address");
				String userRole = db.rs.getString("Role");

				if (dbPassword.equals(AESHelper.encrypt(password, Main.AESencryptionKey))) {
					Main.currentUser = new User(String.valueOf(userID), dbUsername, dbPassword, userPhone, userAddress,
							userRole);

					if (userRole.equalsIgnoreCase("seller")) {
						role = "Seller";
					} else {
						role = "Customer";
					}

					return role;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "invalid";
	}

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

}
