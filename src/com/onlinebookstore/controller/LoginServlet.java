package com.onlinebookstore.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.Item;
import com.onlinebookstore.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/c/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * If ".../login.do" url is requested, redirect the client back to the login
	 * page.
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(WEB.LOGIN);
	}

	/**
	 * Verify user login credentials.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get user login credentials: email and password
		String loginEmail = request.getParameter("email");
		String loginPassword = request.getParameter("password");

		// Verify if login email already exists. Note: each User account is
		// associated with a unique email.
		User user = User.verifyUserLoginCredentials(loginEmail, loginPassword);

		if (user != null) {
			// Bind valid user information to a session.
			HttpSession session = request.getSession();
			session.setAttribute(WEB.USER, user);
			session.setAttribute("login_status", "SUCCESSFUL");

			System.out.println("Login successful");

			@SuppressWarnings("unchecked")
			ArrayList<Item> shoppingCart = (ArrayList<Item>) session
					.getAttribute("shoppingCart");

			// If a User signed-in to their account with a non-empty
			// shoppingCart.
			if (session.getAttribute("shoppingCart") != null) {
				// Add those items to their shoppingCart in the database.
				User.addItems(shoppingCart, user.getUserId());
				// Start a new/fresh shoppingCart session
				session.setAttribute("shoppingCart", null);
				// Send them to their shopping_cart.jsp page
				session.setAttribute("cart", WEB.CART_DO);
			}

			session.setAttribute("numOfItems",
					getNumItems(User.getCart(user.getUserId())));

			// Direct User to their Admin or Customer welcome page
			response.sendRedirect(WEB.LOGIN_SUCCESSFUL);

		} else {
			// loginEmail or password is invalid. Send a generic message.
			sendErrorMessage("invalid email or password", request, response);
		}

	}

	private int getNumItems(ArrayList<Item> cart) {
		// Compute number of items in the current Users shopping cart
		int numOfItems = 0;

		for (Item item : cart) {
			numOfItems += item.getQuantity();
		}
		return numOfItems;
	}

	/**
	 * Provide a generic message for security i.e., to not give potential
	 * hackers extra information. Send message:
	 * "You have entered an invalid email or password"
	 */
	private void sendErrorMessage(String message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println(message);

		request.setAttribute("status", message);
		RequestDispatcher rd = request.getRequestDispatcher(WEB.LOGIN);
		rd.forward(request, response);
	}
}
