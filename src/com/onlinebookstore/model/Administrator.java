package com.onlinebookstore.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrator extends User {
	String employeeID;

	public static boolean addNewAdministrator(String employee_id, String first,
			String last, String email, String password) {
		// Get hashed password and salt as a string 2-tuple
		String[] hashedPasswordAndSalt = getSecurePasswordAndSalt(password);

		if (hashedPasswordAndSalt[0].equals("Error")) {
			return false;
		}

		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String insertNewAdministrator = "INSERT INTO administrators (first_name, last_name, email, hashed_password, employee_id, salt) VALUES (?, ?, ?, ?, ?, ?);";
			try {
				pstmt = con.prepareStatement(insertNewAdministrator);
				pstmt.setString(1, first);
				pstmt.setString(2, last);
				pstmt.setString(3, email);
				pstmt.setString(4, hashedPasswordAndSalt[0]); // hashed password
				pstmt.setString(5, employee_id);
				pstmt.setString(6, hashedPasswordAndSalt[1]); // salt
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
	 * Returns true, if employee_id exists in the employee table. Otherwise,
	 * false.
	 */
	public static boolean verifyEmployeeID(String employee_id, String firstName, String lastName) {
		boolean result = false;
		con = getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String queryEmployeeID = "SELECT * FROM employees WHERE employee_id=? and first_name=? and last_name=?;";
			try {
				pstmt = con.prepareStatement(queryEmployeeID);
				pstmt.setString(1, employee_id);
				pstmt.setString(2, firstName);
				pstmt.setString(3,  lastName);
				
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					result = true;
					System.out.println("Valid admins....");
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
}