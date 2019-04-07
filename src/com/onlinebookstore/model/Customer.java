package com.onlinebookstore.model;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Customer extends Person {
	private static Connection con = null;

	public Customer() {
	}

	/**
	 * Constructor to send a servlet an existing Customer in the customers
	 * table.
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public Customer(String firstName, String lastName) {
	}

	/**
	 * Constructor to create a new Customer to add to the customers table in the
	 * database.
	 * 
	 * @param firstName
	 *            , lastName, email, hashedPassword, salt
	 */
	public Customer(String firstName, String lastName, String email,
			String hashedPassword, String salt) {
		super(firstName, lastName, email, hashedPassword, salt);
	}

	public String getCustomerID() {
		return "";
	}

	/**
	 * Add a Customer to the database with all of its fields specified. Return
	 * false, if connection fails or new Customer was not inserted. Otherwise,
	 * return true.
	 * 
	 * @param first
	 *            new Customer first name
	 * @param last
	 *            new Customer last name
	 * @param email
	 *            new Customer email
	 * @param password
	 *            new Customer password
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean addCustomer(String first, String last, String email,
			String password) throws NoSuchAlgorithmException {

		// Get hashed password and salt as a string 2-tuple
		String[] hashedPasswordAndSalt = getSecurePasswordAndSalt(password);

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String insertNewCustomer = "INSERT INTO customers (first_name, last_name, email, hashed_password, salt) VALUES (?, ?, ?, ?, ?);";
			try {
				pstmt = con.prepareStatement(insertNewCustomer);
				pstmt.setString(1, first);
				pstmt.setString(2, last);
				pstmt.setString(3, email);
				pstmt.setString(4, hashedPasswordAndSalt[0]); // hashed password
				pstmt.setString(5, hashedPasswordAndSalt[1]); // salt
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

	/**
	 * Return a Customer object with all of its field instantiated for the
	 * Customer with the given email in the customers table.
	 * 
	 * Return a Customer with the first name "No" and last name "Matches", if
	 * the Customer with the email does not exit.
	 */
	/*
	 * public static Customer getCustomer(String email) { return new
	 * Customer("NO", "Matches"); }
	 */

	/**
	 * Return true, if the email of a new user already exists in the database or
	 * if for some reason connection to the database fails. Otherwise, return
	 * false.
	 * 
	 * @param email
	 *            email of a new customer registering for an account
	 * 
	 */
	public static boolean verifyEmail(String email) {
		PreparedStatement pstmt = null;
		boolean result = true;

		con = getConnection();

		if (con != null) {
			String query = "SELECT email FROM customers WHERE email=?;";
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // email already exists in the database
					result = true;
				} else {
					result = false;
				}

				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * Connect to the local database for development purposes. The database must
	 * be named "onlinebookstore". The username is "root" with the password
	 * "admin".
	 */
	private static Connection getConnection() {
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
