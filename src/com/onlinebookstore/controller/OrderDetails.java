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
@WebServlet("/order-details")
public class OrderDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("o"));
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user != null) {
			String hash = request.getParameter("o");
			Order order = (Order) User.getOrderDetails(hash);
			System.out.println("----------------------------------------------------------");
			System.out.println(order);
			System.out.println("----------------------------------------------------------");
			session.setAttribute("order", order);
			request.getRequestDispatcher("/order-details.jsp")
					.forward(request, response);
//			response.sendRedirect("/online-book-store/order-details.jsp");
		} else {
			// 404 or send them to login page
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// error 404
	}

}
