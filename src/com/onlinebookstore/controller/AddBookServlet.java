package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.*;

/**
 * Servlet implementation class AddBookServlet
 */
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(WEB.LOGIN);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get the information of the new book to be added
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String publisher = request.getParameter("publisher");
		String yearPublished = request.getParameter("yearPublished");
		String price = request.getParameter("price");
		String quantity = request.getParameter("quantity");
		String type = request.getParameter("type");
		String image = request.getParameter("image");

		HttpSession session = request.getSession();
		System.out.println(title + " " + author + " " + price + " " + type
				+ " " + image);

		if (Double.valueOf(price) <= 0 || Integer.valueOf(quantity) < 0
				|| Integer.valueOf(yearPublished) < 0) {

			session.setAttribute("add_books_status", "ERROR");
			response.sendRedirect("add_books_error");
		} else {
			Book newBook = new Book(type, title, new Author(author), isbn,
					publisher, Integer.valueOf(yearPublished),
					Double.valueOf(price), Integer.valueOf(quantity), image);

			if (Book.addBook(newBook)) {
				System.out.println("Book Added");
				session.setAttribute("add_books_status", "SUCCESS");
				response.sendRedirect("add_books_success");
			} else {
				System.out.println("ERROR: book was not added!");
				session.setAttribute("add_books_status", "ERROR");
				response.sendRedirect("add_books_error");
			}
		}
	}

}
