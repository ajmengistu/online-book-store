package com.onlinebookstore.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.Book;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ArrayList<Book> shoppingCart = new ArrayList<Book>();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String bookId = request.getParameter("id");

		System.out.println("item #:" + bookId);
		HttpSession session = request.getSession(false);
		if(session.getAttribute("shoppingCart") == null){
			shoppingCart = new ArrayList<Book>();
		}
		Book book = Book.getBookById(Integer.parseInt(bookId));
		shoppingCart.add(book);

		for (int i = 0; i < shoppingCart.size(); i++) {
			System.out.println(i + ": " + shoppingCart.get(i));
		}

		
		session.setAttribute("shoppingCart", shoppingCart);

		RequestDispatcher rd = request
				.getRequestDispatcher("shopping_cart.jsp");
		request.setAttribute("shoppingCart", shoppingCart);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
