package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinebookstore.model.Book;

/**
 * Servlet implementation class GetBookDetailsServlet
 */
@WebServlet("/book")
public class GetBookDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get book information of the requested book by book_id
		String bookId = request.getParameter("id");
		Book book = Book.getBookById(Integer.parseInt(bookId));

		RequestDispatcher rd = request
				.getRequestDispatcher("/view_book_details.jsp");

		request.setAttribute("title", book.getTitle());
		request.setAttribute("author", book.getAuthor().getName());
		request.setAttribute("averageRatings", book.getAverageRatings());
		request.setAttribute("numOfRatings", String.format("%,d",book.getNumberOfRatings()));
		request.setAttribute("yearPublished", book.getYearPublished());
		request.setAttribute("image", book.getImage());
		request.setAttribute("price", book.getPrice());
		request.setAttribute("bookId", book.getBookId());
		// getstock
		// get isbn13,isbn
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
