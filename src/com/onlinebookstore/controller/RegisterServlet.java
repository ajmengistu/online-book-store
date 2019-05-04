package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.User;

/**
 * Verify customer registration information.
 */
@WebServlet("/c/register.do")
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
		response.sendRedirect(WEB.REGISTER);
	}

	/**
	 * Verify customer registration information.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-cache");
		// Directs caches not to store the page under any circumstance
		response.setHeader("Cache-Control", "no-store");
		// Causes the proxy cache to see the page as "stale"
		response.setDateHeader("Expire", 0);
		// HTTP 1.0 backward compatibility
		response.setHeader("Pragma", "no-cache");

		// Get all the String data from the fields of the Customer or Admin
		// registration form for validation. If validation is successful, add
		// the Customer or Admin into the database.
		String employee_id = request.getParameter("emp_id");
		// employee_id will be "null", if a Customer attempted to register.
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirm_password = request.getParameter("confirm_password");

		System.out.println(employee_id == null ? "Customer registering..."
				: "Administrator registering...");
		// Verify password confirmation
		if (!password.equals(confirm_password)) {
			sendErrorMessage(employee_id, "password does not match", request,
					response);
		} else {
			// Verify that the email does not already exist in the users table.
			// Note: one unique email per User.
			boolean emailAlreadyExists = User.emailAlreadyExists(email);

			if (emailAlreadyExists) {
				sendErrorMessage(employee_id, "email already exists", request,
						response);
			} else { // Otherwise, add the new Customer or Administrator
				boolean newUserAdded = false;

				if ((employee_id != null)
						&& (!User.verifyEmployeeID(employee_id, first_name,
								last_name))) {
					sendErrorMessage(employee_id, "employee ID is invalid",
							request, response);

				} else if (employee_id == null) {
					// Add a new Customer
					User newUser = new User(first_name, last_name, email,
							WEB.CUSTOMER, password);
					// newUserAdded = User.addNewUser(first_name, last_name,
					// email, password, WEB.CUSTOMER);
					newUserAdded = User.addNewUser(newUser);

					verifyNewUserIsAdded(request, response, newUserAdded, null);

					System.out.println("Registering Customer: " + first_name);
				} else {
					// Add an Administrator
					User newUser = new User(first_name, last_name, email,
							WEB.ADMINISTRATOR, password);
					newUserAdded = User.addNewUser(newUser);
					// newUserAdded = User.addNewUser(first_name, last_name,
					// email, password, WEB.ADMINISTRATOR);

					verifyNewUserIsAdded(request, response, newUserAdded,
							employee_id);

					System.out.println("Registering Admin: " + first_name);
				}
			}
		}
	}

	/**
	 * Verify if a new User (Customer or Administrator) was added to the
	 * database successfully.
	 */
	private void verifyNewUserIsAdded(HttpServletRequest request,
			HttpServletResponse response, boolean newUserAdded,
			String employee_id) throws IOException, ServletException {
		if (newUserAdded) {
			System.out.println("New User added");
			// Show message: 'Registration Successful'
			// Redirect customer to login page to confirm email and
			// auto-fill email & password while their password is
			// fresh on their mind.
			HttpSession session = request.getSession();
			session.setAttribute("registration_status", "success");
			response.sendRedirect(WEB.REGISTRATION_SUCCESSFUL);

		} else { // New User was not successfully added
			sendErrorMessage(employee_id, "please try again", request, response);
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
	private void sendErrorMessage(String emp_id, String message,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = null;

		if (emp_id == null) { // Customer registering
			rd = request.getRequestDispatcher(WEB.REGISTER);
			request.setAttribute("status", message);
		} else { // Administrator registering
			rd = request.getRequestDispatcher(WEB.ADMIN_REGISTER);
			request.setAttribute("status", message);
		}

		System.out.println(message);

		rd.forward(request, response);
	}

}