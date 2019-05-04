package com.onlinebookstore.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebookstore.model.Book;

/**
 * Servlet implementation class SearchBooks
 */
@WebServlet("/c/searchbooks")
public class SearchBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("q");

		System.out.println("Query: " + query);

		ArrayList<Book> books = Book.getBookBySearch(query);
		for(Book b :books)
			System.out.println(b);

		ObjectMapper mapper = new ObjectMapper();
		String map = mapper.writeValueAsString(books);

		response.setContentType("application/json");
		response.getWriter().println(map);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
