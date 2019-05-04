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

@WebServlet("/c/place-order")
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
			request.getRequestDispatcher("payment-successful.jsp").forward(
					request, response);
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
			request.getRequestDispatcher("paymentFailed.jsp").forward(request,
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
			request.getRequestDispatcher("paymentFailed.jsp").forward(request,
					response);
		}

	}

	// public static void main(String[] args) {
	// // String uniqueID = UUID.randomUUID().toString();
	// // System.out.println(uniqueID);
	// BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
	// WEB.MERCHANT_ID, WEB.PUBLIC_KEY, WEB.PRIVATE_KEY);
	// PaymentMethod paymentMethod = gateway.paymentMethod().find(""
	// +
	// "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJkYmEzOGFmZGU1OGRjYzAwNGY1YTgzOTY2ODhlMjJlM2YxNDFjODU3MzY1ZjUyNGFmNjVhZmIwMzRkNDVjNWMzfGNyZWF0ZWRfYXQ9MjAxOS0wNS0wMlQxNzowMjo0OC42OTQ0NDkyMjYrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTY0Zm1iZng0bXQ2cGM2OWpcdTAwMjZwdWJsaWNfa2V5PWNudDVybnF0NXp4Y21jYmYiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvNjRmbWJmeDRtdDZwYzY5ai9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJncmFwaFFMIjp7InVybCI6Imh0dHBzOi8vcGF5bWVudHMuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9ncmFwaHFsIiwiZGF0ZSI6IjIwMTgtMDUtMDgifSwiY2hhbGxlbmdlcyI6W10sImVudmlyb25tZW50Ijoic2FuZGJveCIsImNsaWVudEFwaVVybCI6Imh0dHBzOi8vYXBpLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy82NGZtYmZ4NG10NnBjNjlqL2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFuYWx5dGljcyI6eyJ1cmwiOiJodHRwczovL29yaWdpbi1hbmFseXRpY3Mtc2FuZC5zYW5kYm94LmJyYWludHJlZS1hcGkuY29tLzY0Zm1iZng0bXQ2cGM2OWoifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoib25saW5lYm9va3N0b3JlIiwiY2xpZW50SWQiOm51bGwsInByaXZhY3lVcmwiOiJodHRwOi8vZXhhbXBsZS5jb20vcHAiLCJ1c2VyQWdyZWVtZW50VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3RvcyIsImJhc2VVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFzc2V0c1VybCI6Imh0dHBzOi8vY2hlY2tvdXQucGF5cGFsLmNvbSIsImRpcmVjdEJhc2VVcmwiOm51bGwsImFsbG93SHR0cCI6dHJ1ZSwiZW52aXJvbm1lbnROb05ldHdvcmsiOnRydWUsImVudmlyb25tZW50Ijoib2ZmbGluZSIsInVudmV0dGVkTWVyY2hhbnQiOmZhbHNlLCJicmFpbnRyZWVDbGllbnRJZCI6Im1hc3RlcmNsaWVudDMiLCJiaWxsaW5nQWdyZWVtZW50c0VuYWJsZWQiOnRydWUsIm1lcmNoYW50QWNjb3VudElkIjoib25saW5lYm9va3N0b3JlIiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn0sIm1lcmNoYW50SWQiOiI2NGZtYmZ4NG10NnBjNjlqIiwidmVubW8iOiJvZmYifQ==");
	// System.out.println(paymentMethod.getClass().getName());
	// System.out.println(paymentMethod.getClass());
	// }
}