package com.onlinebookstore.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.Book;

/**
 * Servlet implementation class LogoutServlet
 */
// @WebServlet(name = "LogOutServlet", urlPatterns = { "/LogOutServlet" })
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		session.setAttribute("firstName", null);
		session.setAttribute("email", null);
		session.setAttribute("shoppingCart", new ArrayList<Book>());
		session.invalidate();
		response.sendRedirect(WEB.HOME);
	}
}