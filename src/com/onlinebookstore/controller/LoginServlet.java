package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.Customer;

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

		// Verify if login email already exists. If so, get the salt
		// of the corresponding user. Note: each account is associated with a
		// unique email.
		String salt = Customer.getUserSalt(loginEmail);

		if (salt.length() != 0) {
			String hashedLoginPassword = Customer.getSecurePasswordSHA512(
					loginPassword, salt.getBytes());

			if (Customer.verifyPassword(hashedLoginPassword, loginEmail)) {
				// Get Customer object with their information.

				// Bind valid user information: email & password to a session.
				HttpSession session = request.getSession();
				session.setAttribute("email", loginEmail);
				session.setAttribute("firstName", loginEmail);

				// Direct user to welcome page
				System.out.println("Login successful");
				// sendLoginSuccessfulMessage(response.getWriter());
				response.sendRedirect(WEB.WELCOME);
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
		RequestDispatcher rd = request.getRequestDispatcher(WEB.LOGIN);
		rd.forward(request, response);
	}
}
