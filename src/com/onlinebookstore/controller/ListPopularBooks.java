package com.onlinebookstore.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.onlinebookstore.model.Book;

public class ListPopularBooks extends SimpleTagSupport{

	public void doTag() throws JspException, IOException {
		// Get the top 15 rated books
		ArrayList<Book> topRatedBooks = Book.getPopularBooks();

		for (Book book : topRatedBooks) {
			getJspContext().setAttribute("title", book.getTitle());
			getJspContext().setAttribute("author", book.getAuthor().getName());
			getJspContext().setAttribute("avgerageRating", book.getAverageRatings());
			getJspContext().setAttribute("image", book.getImage());
			getJspContext().setAttribute("price", book.getPrice());
			getJspBody().invoke(null);
		}
	}
}
