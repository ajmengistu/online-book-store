package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.Order;
import com.onlinebookstore.model.User;

/**
 * Servlet implementation class OrderSummary
 */
@WebServlet("/c/order-details")
public class OrderDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("here--");
		System.out.println(request.getParameter("o"));
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute(WEB.USER);

		if (user != null) {
			String hash = request.getParameter("o");
			Order order = (Order) User.getOrderDetails(hash);
			System.out
					.println("----------------------------------------------------------");
			System.out.println(order);
			System.out
					.println("----------------------------------------------------------");
			if (order != null) {
				session.setAttribute("order", order);
				request.getRequestDispatcher("/c/order-details.jsp").forward(
						request, response);
			} else {
				request.getRequestDispatcher(WEB.ERROR_404).forward(
						request, response);
			}
		} else {
			// 404 or send them to login page
			response.sendRedirect(WEB.LOGIN);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// error 404
	}

}
