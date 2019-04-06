package com.onlinebookstore.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;

public class Customer extends Person {
	private static Connection con = null;

	public Customer() {
	}

	//
	public Customer(String firstName, String lastName) {

	}

	// Constructor to create a new Customer to add to the database.
	public Customer(String firstName, String lastName, String email,
			String password) {
		super(firstName, lastName, email, password);
	}

	public String getCustomerID() {
		return "";
	}

	/**
	 * Add a Customer to the database with all of its fields specified. Return
	 * false, if connection fails or Customer was not inserted. Otherwise,
	 * return true.
	 * @param first new user first name
	 * @param last new user last name
	 * @param email new user last name
	 * @param password new user password
	 */
	public static boolean addCustomer(String first, String last, String email,
			String password) {
		// Connect to the database
//		Connection con = getConnection();
//		// First: make sure that there is no user with an identical email
//					// already in the database.
//					if (doesEmailAlreadyExist(email, con)) {
//						request.setAttribute("status", "Email already exists");
//						RequestDispatcher rd = request
//								.getRequestDispatcher("register.jsp");
//						rd.forward(request, response);
//					} else {	
//						// Second: if the new user does not already exist in the
//						// database.
//						// Add the user to the database.
//						if (con == null) {
//							System.out.println("Connection failed!");
//							request.setAttribute("status", "Please try again later");
//							RequestDispatcher rd = request
//									.getRequestDispatcher("register.jsp");
//							rd.forward(request, response);
//						} else {
//							// addNewCustomer()
//						}
		return false;
	}

	/**
	 * Return a Customer object with all of its field instantiated for the
	 * Customer with the given email in the customers table.
	 * 
	 * Return a Customer with the first name "No" and last name "Matches", if
	 * the Customer with the email does not exit.
	 */
	public Customer getCustomer(String email) {
		return new Customer("NO", "Matches");
	}

	/**
	 * Return true, if the email of a new user already exists in the database.
	 * Otherwise, return false.
	 * @param email
	 * 
	 */
	public static boolean verifyEmail(String email) {
		PreparedStatement pstmt = null;
		boolean result = true;
		if (con != null) {
			String query = "SELECT email FROM customers WHERE email=?;";
			try {
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, email);
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					// email already exists in the database
					result = true;
				} else {
					result = false;
				}
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * Connect to the local database for development purposes. The database must
	 * be named onlinebookstore; the username is "root" with the password
	 * "admin".
	 */
	private Connection getConnection() {
		// Return existing connection after first call
		if (con != null) {
			return con;
		}
		System.out.println("Getting local connection...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Getting database connection");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/onlinebookstore", "root",
					"admin");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
