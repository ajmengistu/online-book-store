package com.onlinebookstore.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;

public abstract class User {
	private String firstName, lastName, email, hashedPassword, salt;
	public static Connection con = null;

	public User() {
	}

	// Constructor to create a new person.
	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String firstName, String lastName, String email,
			String hashedPassword, String salt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.hashedPassword = hashedPassword;
		this.email = email;
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

	public static String[] verifyUserLoginCredentials(String loginEmail,
			String loginPassword) {
		String queryCustomersTable = "SELECT salt FROM customers WHERE email=?;";
		String queryAdministratorsTable = "SELECT salt FROM administrators WHERE email=?;";

		String salt = getUserSalt(queryCustomersTable, loginEmail);
		if (salt != null) {
			String hashedLoginPassword = getSecurePasswordSHA512(loginPassword,
					salt.getBytes());

			String getUserInfo = "SELECT first_name, last_name, email FROM customers WHERE email=? and hashed_password=?;";
			return verifyPassword(hashedLoginPassword, loginEmail, getUserInfo);
		} else {
			salt = getUserSalt(queryAdministratorsTable, loginEmail);
			String getUserInfo = "SELECT first_name, last_name, email FROM administrator WHERE email=? and hashed_password=?;";

			if (salt != null) {
				String hashedLoginPassword = getSecurePasswordSHA512(
						loginPassword, salt.getBytes());

				return verifyPassword(hashedLoginPassword, loginEmail,
						getUserInfo);
			}
		}

		return null;
	}

	public static String[] verifyPassword(String hashedLoginPassword,
			String loginEmail, String getUserInfo) {

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {

			try {
				pstmt = con.prepareStatement(getUserInfo);
				pstmt.setString(1, loginEmail);
				pstmt.setString(2, hashedLoginPassword);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // rs should return one row with user id
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String email = rs.getString("email");

					return new String[] { firstName, lastName, email };
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

		return null; // loginPassword invalid
	}

	public static String getUserSalt(String query, String email) {
		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // returns only one row
					return rs.getString("salt");
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
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
		String queryCustomersTable = "SELECT email FROM customers WHERE email=?;";
		String queryAdminTable = "SELECT email FROM administrators WHERE email=?;";

		if ((queryEmailExistance(email, queryCustomersTable))
				|| (queryEmailExistance(email, queryAdminTable))) {
			return true;
		} else {
			return false; // email does not exist in the database
		}
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

	// public static void main(String args[]) throws NoSuchAlgorithmException {
	// }
}