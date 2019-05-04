package com.onlinebookstore.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Payment {
	private int paymentId, orderId;
	private String transactionId;
	private String dateCreated;

	public Payment(int paymentId, int orderId, String transId, String date) {
		this.paymentId = paymentId;
		this.orderId = orderId;
		this.transactionId = transId;
		this.dateCreated = date;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public int getOrderId() {
		return orderId;
	}

	public String getTransId() {
		return transactionId;
	}

	public String getDate() {
		return dateCreated;
	}

	public String toString() {
		return "paymentId: " + paymentId + " orderId: " + orderId
				+ " transactionId: " + transactionId + " dateCreated: "
				+ dateCreated + "\n";
	}

	
	
	public static ArrayList<Payment> getPayments(int orderId) {
		Connection con = User.getConnection();

		String query = null;
		ArrayList<Payment> payments = new ArrayList<Payment>();
		if (orderId == -1) {
			query = "SELECT * FROM payments ORDER BY date_created DESC LIMIT 10;";
		} else {
			query = "SELECT * FROM payments WHERE order_id = ? ORDER BY date_created DESC LIMIT 10;";
		}

		// Check if item already exist, if it does, get the quantity.
		PreparedStatement pstmt = null;
		if (con != null) {
			try {

				pstmt = con.prepareStatement(query);
				if (orderId != -1) {
					pstmt.setInt(1, orderId);
				}

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					Payment payment = new Payment(rs.getInt("payment_id"),
							rs.getInt("order_id"),
							rs.getString("transaction_id"),
							User.formatDate(rs.getString("date_created")));
					payments.add(payment);
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return payments;

	}

	public static void main(String[] args) {
		System.out.println(getPayments(20));
	}
}
