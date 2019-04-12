package com.onlinebookstore.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private String firstName, lastName, email, userRole, userID, password;
	private static Connection con = null;

	public User() {
	}

	// Constructor to create a new person.
	public User(String firstName, String lastName, String email, String userRole) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = userRole;
		// this.userID = userID;
	}

	// Constructor to create a new person.
	public User(String firstName, String lastName, String email,
			String userRole, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRole = userRole;
		this.password = password;
	}
	public String getPassword(){
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getUserRole() {
		return userRole;
	}

	public String getUserID() {
		return userID;
	}

	public static boolean verifyEmployeeID(String employeeID, String firstName,
			String lastName) {
		boolean result = false;
		PreparedStatement pstmt = null;

		con = getConnection();

		if (con != null) {
			String query = "SELECT * FROM employees WHERE binary first_name=? and binary last_name=? and binary employee_id=?;";
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, firstName);
				pstmt.setString(2, lastName);
				pstmt.setString(3, employeeID);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					result = true;
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return result;
	}

	public static User verifyUserLoginCredentials(String loginEmail,
			String loginPassword) {
		String userSalt = getUserSalt(loginEmail);

		if (userSalt != null) {
			// If loginEmail exists in the users table
			String hashedLoginPassword = getSecurePasswordSHA512(loginPassword,
					userSalt.getBytes());

			return verifyPassword(loginEmail, hashedLoginPassword);
		}
		System.out.format("email: %s is invalid\n", loginEmail);
		return null;
	}

	public static User verifyPassword(String loginEmail,
			String hashedLoginPassword) {
		User userLogingIn = null;

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String getUser = "SELECT * FROM users WHERE binary email=? and binary hashed_password=?;";

			try {
				pstmt = con.prepareStatement(getUser);
				pstmt.setString(1, loginEmail);
				pstmt.setString(2, hashedLoginPassword);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // rs should return one row
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String email = rs.getString("email");
					String userRole = rs.getString("user_role");

					userLogingIn = new User(firstName, lastName, email,
							userRole);
					System.out.println("Returning an existing User");
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		return userLogingIn;
	}

	public static String getUserSalt(String email) {
		PreparedStatement pstmt = null;
		String userSalt = null;

		con = getConnection();

		if (con != null) {
			String getUserSalt = "SELECT salt FROM users WHERE binary email=?;";

			try {
				pstmt = con.prepareStatement(getUserSalt);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // returns only one row
					userSalt = rs.getString("salt");
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userSalt;
	}

	public static boolean queryEmailExistance(String email, String query) {
		PreparedStatement pstmt = null;
		boolean result = true;

		con = getConnection();

		if (con != null) {
			// String query = "SELECT email FROM customers WHERE email=?;";
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();

				if (!rs.next()) { // email does not already exist in the
					result = false; // database
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return result;
	}

	public static boolean emailAlreadyExists(String email) {
		PreparedStatement pstmt = null;
		boolean result = false;

		con = getConnection();

		if (con != null) {
			String query = "SELECT email FROM users WHERE binary email=?;";
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // email already exist in the database
					result = true;
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return result;
	}

	public static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	/***
	 * Return a new secure hashed password using SHA512 algorithm.
	 * 
	 * @param passwordToHash
	 * @param salt
	 **/
	public static String getSecurePasswordSHA512(String passwordToHash,
			byte[] salt) {
		String securePassword = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] newBytes = md.digest(passwordToHash.getBytes());
			securePassword = bytesToHex(newBytes);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return securePassword;
	}

	/***
	 * Returns an array of size 2 containing a string that is hashed and a
	 * string version of the salt used to create the hashed password for a
	 * specific user. Otherwise, return an array of size two containing an error
	 * message: ["Error", "Cannot get hashed password and salt"].
	 * 
	 * @param password
	 *            that customer entered.
	 **/
	public static String[] getSecurePasswordAndSalt(String passwordToHash) {
		String[] result = new String[2];

		try {
			byte[] salt = User.getSalt();
			String hashedPassword = User.getSecurePasswordSHA512(
					passwordToHash, salt);

			result[0] = hashedPassword;
			result[1] = new String(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new String[] { "Error",
					"Cannot get secure password and salt." };
		}

		return result;
	}

	/***
	 * Returns a new string version of the byte array. Converts a 16 bytes array
	 * into hexadecimal string of length 128.
	 * 
	 * @param bytes
	 *            a byte array of a hashed password.
	 **/
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/***
	 * Connect to the local database for development purposes. The database must
	 * be named "onlinebookstore". The username is "root" with the password
	 * "admin".
	 **/
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/onlinebookstore";
		String username = "root";
		String password = "admin";

		// Return existing connection after first call
		if (con != null) {
			return con;
		}

		System.out.println("Getting local connection...");
		try {
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("Getting database connection");

			con = DriverManager.getConnection(url, username, password);
			if (con != null)
				System.out.println("Connected");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	public static boolean closeConnection() {
		System.out.println("Closing connection...");

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		System.out.println("Closed");
		return true;
	}

	public static boolean addNewUser(User newUser) {
		// Get hashed password and salt as a string 2-tuple
		String[] hashedPasswordAndSalt = getSecurePasswordAndSalt(newUser.getPassword());

		if (hashedPasswordAndSalt[0].equals("Error")) {
			return false;
		}

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String insertNewCustomer = "INSERT INTO users (first_name, last_name, email, hashed_password, salt, user_role) VALUES (?, ?, ?, ?, ?, ?);";
			try {
				pstmt = con.prepareStatement(insertNewCustomer);
				pstmt.setString(1, newUser.getFirstName());
				pstmt.setString(2, newUser.getLastName());
				pstmt.setString(3, newUser.getEmail());
				pstmt.setString(4, hashedPasswordAndSalt[0]); // hashed password
				pstmt.setString(5, hashedPasswordAndSalt[1]); // salt
				pstmt.setString(6, newUser.getUserRole());
				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();

				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}
}