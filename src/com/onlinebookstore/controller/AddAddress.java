package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.User;

/**
 * Servlet implementation class AddAddress
 */
@WebServlet("/addAddress.do")
public class AddAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(WEB.SHOPPING_CART);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			// Replace this with a 404 page
			response.sendRedirect(WEB.HOME);
		} else {
			String address = request.getParameter("inputAddress");
			String address2 = request.getParameter("inputAddress2");
			String city = request.getParameter("inputCity");
			String state = request.getParameter("inputState");
			int zip = Integer.parseInt(request.getParameter("inputZip"));
			System.out.println(address);
			System.out.println(address2);
			System.out.println(zip);

			// Add this new address to the database
			User.addNewAddress(address, address2, city, state, zip);
			
			// Update userAddressID
//			session.setAttribute("userAddressId", id);

			response.sendRedirect("checkout.do");
		}
	}

}
