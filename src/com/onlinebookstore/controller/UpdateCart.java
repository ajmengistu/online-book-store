package com.onlinebookstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.Book;
import com.onlinebookstore.model.Item;

/**
 * Servlet implementation class UpdateCart
 */
@WebServlet("/updatecart.do")
public class UpdateCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("id"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		System.out.println("bookid: " + bookId);

		HttpSession session = request.getSession();

		@SuppressWarnings("unchecked")
		ArrayList<Item> shoppingCart = (ArrayList<Item>) session
				.getAttribute("shoppingCart");

		System.out.println("UpdateCart------------------");
		System.out.println(shoppingCart.toString());
		Book book = Book.getBookById(bookId);

		int x = -1;
		for (int i = 0; i < shoppingCart.size(); i++) {
			if (shoppingCart.get(i).getBook().getBookId() == bookId) {
				System.out.println("Found it");
				x = i;
			}
		}

		if (quantity != 0) {
			shoppingCart.get(x).setQuantity(quantity);

		} else {
			shoppingCart.remove(x);
		}

		System.out.println(shoppingCart.toString());
		System.out.println("UpdateCart------------------");
		
		
		
		response.sendRedirect("shopping_cart");
	}

}
