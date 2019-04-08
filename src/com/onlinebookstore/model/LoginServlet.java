package com.onlinebookstore.model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * If ".../login.do" url is requested, redirect the client back to the login
	 * page.
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect("login");
	}

	/**
	 * Verify user login credentials.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get user login credentials: email and password
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Verify if login email already exists. If so, get the salt
		// of the corresponding user. Note: each account is associated with a
		// unique email.
		String salt = Customer.getUserSalt(email);
		if (salt.length() != 0) {
			String hashedLoginPassword = Customer.getSecurePasswordSHA512(
					password, salt.getBytes());

			if (Customer.verifyPassword(hashedLoginPassword, email)) {
				// direct user to welcome page
				response.sendRedirect("register");
			} else {
				// email exists, but password is incorrect.
				// However, send a generic message to person attempting to
				// login
				System.out.println("invalid password");
				sendErrorMessage(
						"You have entered an invalid email or password",
						request, response);
			}
		} else {
			// loginEmail does not exist in the database. Send a
			// generic message.
			System.out.println("invalid email");
			sendErrorMessage("You have entered an invalid email or password",
					request, response);

		}

	}

	/**
	 * Provide a generic message for security; to not give potential hackers
	 * extra information. Send message:
	 * "You have entered an invalid email or password"
	 */
	private void sendErrorMessage(String message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("status", message);
		System.out.println(message);
		RequestDispatcher rd = request.getRequestDispatcher("login");
		rd.forward(request, response);
	}
}
