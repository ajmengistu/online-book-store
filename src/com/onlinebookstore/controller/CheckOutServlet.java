package com.onlinebookstore.controller;

import java.io.IOException;

import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.User;

/**
 * Servlet implementation class CheckOutServlet
 */
@WebServlet("/c/checkout.do")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (!isUserSignedIn(session)) {
			System.out.println("Sign-in Before Checking Out");
			response.sendRedirect(WEB.LOGIN);
		} else {
			User user = (User) session.getAttribute(WEB.USER);

			getUserAddress(user.getUserId(), session);

			// get payment method

			// To make sure the User goes through the shopping_cart page to
			// access the checkout page.
			session.setAttribute(WEB.CHECKOUT, "success");
			response.sendRedirect(WEB.CHECKOUT);
		}
	}

	private void getUserAddress(int userId, HttpSession session) {
		Pair<Integer, String> userAddress = User.getAddress(userId);
		System.out.println("in CheckoutServlet");
		System.out.println(userAddress);
		if (userAddress != null) {
			session.setAttribute("addressId", userAddress.getKey());
			session.setAttribute("userAddress", userAddress.getValue());
			session.setAttribute("addressStatus", "Change address");
			System.out.println("user address-----------");
		} else {
			session.setAttribute("addressStatus", "Add a shipping address");
			session.setAttribute("userAddress", "");
			System.out.println("user address-");
		}
	}

	private boolean isUserSignedIn(HttpSession session) {
		boolean status = false;
		if (session != null && session.getAttribute(WEB.USER) != null) {
			status = true;
		}
		return status;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

//	public static void main(String[] args) {
//		Pair<Integer, String> userAddress = User.getAddress(113);
//		System.out.println(userAddress.toString());
//	}
}
