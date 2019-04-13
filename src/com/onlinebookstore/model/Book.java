package com.onlinebookstore.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Book {
	private String genre, title, ISBN, publisher, image;
	private int yearPublished;
	private double price;
	private int quantity;
	private Author author;

	public Book(String genre, String title, Author name, String ISBN,
			String publisher, int yearPublished, double price, int quantity,
			String image) {
		this.genre = genre;
		this.title = title;
		this.author = name;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}

	public Author getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public String getTitle() {
		return title;
	}

	public String getISBN() {
		return ISBN;
	}

	public String getPublisher() {
		return publisher;
	}

	public int getYearPublished() {
		return yearPublished;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getImage() {
		return image;
	}

	public String toString() {
		return "Book[genre: " + genre + ", title: " + title + ", author: "
				+ author + "]";
	}

	public static Author[] getAuthors() {
		return new Author[] {};
	}

	public static boolean addBook(Book newBook) {
		Connection con = User.getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			String insertNewCustomer = "INSERT INTO books (title, author, isbn, publisher, year_published, price, quantity, type, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			try {
				pstmt = con.prepareStatement(insertNewCustomer);
				pstmt.setString(1, newBook.getTitle());
				pstmt.setString(2, newBook.getAuthor().getName());
				pstmt.setString(3, newBook.getISBN());
				pstmt.setString(4, newBook.getPublisher());
				pstmt.setInt(5, newBook.getYearPublished());
				pstmt.setDouble(6, newBook.getPrice());
				pstmt.setInt(7, newBook.getQuantity());
				pstmt.setString(8, newBook.getGenre());
				pstmt.setString(9, newBook.getImage());
				pstmt.executeUpdate();

				if (pstmt != null)
					pstmt.close();
				System.out.println("SUCCESS: Book Successfully Added!");
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR: book was not added!");
				return false;
			}
		}
		System.out.println("ERROR: book was not added!");
		return false;
	}
}
