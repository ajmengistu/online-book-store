package com.onlinebookstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.onlinebookstore.model.Item;
import com.onlinebookstore.model.User;

@WebServlet("/c/place-order.do")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 404.jsp ERROR
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String paymentMethodNonce = request
				.getParameter("payment_method_nonce");

		BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
				WEB.MERCHANT_ID, WEB.PUBLIC_KEY, WEB.PRIVATE_KEY);

		if (paymentMethodNonce == null) {
			// Show an error message and redirect user to checkout page
		}

		HttpSession session = request.getSession(false);
		BigDecimal subTotal = (BigDecimal) session.getAttribute("subTotal");
		BigDecimal shippingCost = (BigDecimal) session
				.getAttribute("shippingCost");
		int addressId = (int) session.getAttribute("addressId");
		User user = (User) session.getAttribute("user");
		@SuppressWarnings("unchecked")
		ArrayList<Item> shoppingCart = (ArrayList<Item>) session
				.getAttribute("shoppingCart");

		System.out.println("Total cost: "
				+ request.getSession().getAttribute("totalCost"));
		System.out.println("subTotal cost: "
				+ request.getSession().getAttribute("subTotal"));
		System.out.println("shippingCost cost: "
				+ request.getSession().getAttribute("shippingCost"));
		System.out.println("Addressid: " + addressId);
		System.out.println("Payment method: " + paymentMethodNonce);
		TransactionRequest req = new TransactionRequest()
				.amount(new BigDecimal(subTotal.toString()))
				// .amount(new BigDecimal("4001.99 "))
				.shippingAmount(new BigDecimal(shippingCost.toString()))
				.paymentMethodNonce(paymentMethodNonce).options()
				.submitForSettlement(true).done();

		Result<Transaction> result = gateway.transaction().sale(req);
		System.out.println(result.getMessage());
		if (result.isSuccess()) {
			Transaction transaction = result.getTarget();
			System.out.println("Success!: " + transaction.getId());
			String transactionId = transaction.getId();

			// ---- Update the database accordingly ----
			// pair return (orderId, hash)
			Pair<Integer, String> pair = User // Record order
					.placeOrder(user.getUserId(), addressId, subTotal);
			User.placeBookOrders(pair.getKey(), shoppingCart); // Place book
																// Orders
			User.placePayment(pair.getKey(), transactionId);// Record
															// payments
			User.emptyCart(user.getUserId(), shoppingCart); // Empty user
			// Cart
			// from database
			User.updateStock(shoppingCart); // Update Book stock from
			// database
			session.setAttribute("shoppingCart", null);// Empty current
			session.setAttribute("numOfItems", 0);
			// shoppingCart session
			// Show paymentSuccessful
			session.setAttribute("hash", pair.getValue());
			System.out.println("--before success");
			// request.getRequestDispatcher("online-book-store/success/payment-successful.jsp").forward(
			// request, response);
			session.setAttribute("payment-status", WEB.SUCCESSFUL);
			response.sendRedirect(WEB.PAYMENT_SUCCESSFUL);
			System.out.println("--after success");
		} else if (result.getTransaction() != null) {
			Transaction transaction = result.getTransaction();
			System.out.println("Failed!: " + transaction.getId());
			System.out.println("Error processing transaction:");
			System.out.println("  Status: " + transaction.getStatus());
			System.out.println("  Code: "
					+ transaction.getProcessorResponseCode());
			System.out.println("  Text: "
					+ transaction.getProcessorResponseText());

			// show paymentFailed message
			// redirect back to checkout page
			request.getRequestDispatcher(WEB.PAYMENT_FAILED).forward(request,
					response);
		} else {
			for (ValidationError error : result.getErrors()
					.getAllDeepValidationErrors()) {
				System.out.println("Attribute: " + error.getAttribute());
				System.out.println("  Code: " + error.getCode());
				System.out.println("  Message: " + error.getMessage());
			}
			// show paymentFailed message
			// redirect back to checkout page
			request.getRequestDispatcher(WEB.PAYMENT_FAILED).forward(request,
					response);
		}

	}
}