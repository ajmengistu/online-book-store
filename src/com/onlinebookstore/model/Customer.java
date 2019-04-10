package com.onlinebookstore.model;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Customer extends User {

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
	public static boolean addNewCustomer(String first, String last,
			String email, String password) {
		// Get hashed password and salt as a string 2-tuple
		String[] hashedPasswordAndSalt = getSecurePasswordAndSalt(password);

		if (hashedPasswordAndSalt[0].equals("Error")) {
			return false;
		}

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
	 * Returns a string of the salt value of the user corresponding to the
	 * loginEmail.
	 * 
	 * @param loginEmail
	 *            login email of the user attempting to login to their account.
	 * */
	public static String getUserSalt(String loginEmail) {
		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String getSalt = "SELECT salt FROM customers WHERE email=?;";
			try {
				pstmt = con.prepareStatement(getSalt);
				pstmt.setString(1, loginEmail);

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) { // returns only one row
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

		return "";
	}

	/**
	 * Return true, if user email and password exists in the database.
	 * Otherwise, return false.
	 * 
	 * @param hashedLoginPassword
	 *            login email of the user attempting to login
	 * @param loginEmail
	 *            login password of the user attempting to login
	 */
	public static boolean verifyPassword(String hashedLoginPassword,
			String loginEmail) {
		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {

			String getEmailAndPassword = "SELECT id FROM customers WHERE email=? and hashed_password=?;";
			try {
				pstmt = con.prepareStatement(getEmailAndPassword);
				pstmt.setString(1, loginEmail);
				pstmt.setString(2, hashedLoginPassword);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) { // rs should return one row with user id
					return true;
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false; // loginPassword invalid
	}

	// public static void main(String args[]) throws NoSuchAlgorithmException {
	// }
}
