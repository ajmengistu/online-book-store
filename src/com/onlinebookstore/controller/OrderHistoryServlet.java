package com.onlinebookstore.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.Order;
import com.onlinebookstore.model.User;

/**
 * Servlet implementation class OrderHistoryServlet
 */
@WebServlet("/c/order-history")
public class OrderHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("------------Getting Order History-----------------------");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(WEB.USER);
		if (user != null) {
			ArrayList<Order> orderList = User.getOrderHistory(user.getUserId());
			session.setAttribute("orderList", orderList);
			request.getRequestDispatcher("order-history.jsp").forward(request,
					response);
		} else {
			response.sendRedirect(WEB.LOGIN);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
