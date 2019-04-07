package com.onlinebookstore.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Verify customer registration information.
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * If the client at any given time requests "/register.do", they will be
	 * redirected to the customer registration page. We want the client to use a
	 * post method on the form using the "/register.do" action, where invoked by
	 * filling out the form with valid information and clicking on the
	 * registration button.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect("register");
	}

	/**
	 * Verify customer registration information.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Get all the string data from the fields of the registration form
		// for validation. If validation is successful, add the customer
		// into the database.
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirm_password = request.getParameter("confirm_password");

		// Verify password confirmation
		if (!password.equals(confirm_password)) {
			sendErrorMessage("invalid password", request, response);
		} else {
			// Verify that the email does not already exist in the customers
			// table.
			boolean emailAlreadyExists = Customer.verifyEmail(email);
			if (emailAlreadyExists) {
				sendErrorMessage("Email already exists", request, response);
			} else {
				// Otherwise, add the new customer into the database
				boolean newCustomerAdded = false;
				try {
					newCustomerAdded = Customer.addCustomer(first_name,
							last_name, email, password);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}

				if (newCustomerAdded) {
					// Show message: 'Registration Successful'
					// Redirect customer to login page to confirm email and
					// auto-fill email & password while their password is fresh
					// on their mind.
					System.out.println("New Customer added: " + first_name
							+ " " + last_name);
					sendRegistrationSuccessfulMessage(response.getWriter());
					RequestDispatcher rd = request
							.getRequestDispatcher("login");
					rd.include(request, response);
				} else { // Connection to the database failed
					sendErrorMessage("Please try again", request, response);
				}
			}
		}
	}

	/**
	 * Send customer an error message for invalid password, invalid email, or
	 * failed connection.
	 * 
	 * @param message
	 *            a customized message to show customer attempting to register
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	private void sendErrorMessage(String message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("status", message);
		System.out.println(message);
		RequestDispatcher rd = request.getRequestDispatcher("register");
		rd.forward(request, response);
	}

	/**
	 * Send a message 'registration successful' to the new customer that just
	 * registered.
	 * 
	 * @param out
	 *            PrintWriter object
	 * */
	private void sendRegistrationSuccessfulMessage(PrintWriter out) {
		out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js'></script>");
		out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.all.js'></script>");
		out.println("<script>");
		out.println("$(document).ready(function(){swal ( 'Great' ,  'Registration Successful!' ,  'success' );});");
		out.println("</script>");
	}
}
