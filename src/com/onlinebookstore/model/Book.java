package com.onlinebookstore.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Book {
	private String genre, title, ISBN, publisher, image;
	private int yearPublished, stock, ratings;
	private double price, averageRatings;
	private Author author;

	public Book(String genre, String title, Author name, String ISBN,
			String publisher, int yearPublished, double price, int stock,
			String image) {
		this.genre = genre;
		this.title = title;
		this.author = name;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.price = price;
		this.stock = stock;
		this.image = image;
	}

	public Book(String title, Author name, Double averageRatings,
			Integer ratings, String imageUrl, Double price, Integer stock) {
		this.title = title;
		this.author = name;
		this.averageRatings = averageRatings;
		this.ratings = ratings;
		this.image = imageUrl;
		this.price = price;
		this.stock = stock;
	}

	public Author getAuthor() {
		return author;
	}

	public Double getAverageRatings() {
		return averageRatings;
	}

	public int getRatings(){
		return ratings;
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
		return stock;
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
			String insertNewCustomer = "INSERT INTO books "
					+ "(title, author, isbn, publisher, year_published, price, quantity, type, image) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
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

	public static ArrayList<Book> getBooksToDisplay(String query) {
		Connection con = User.getConnection();

		PreparedStatement pstmt = null;
		ArrayList<Book> topRatedBooks = new ArrayList<>();
		if (con != null) {

			try {
				pstmt = con.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					String title = rs.getString("title");
					String authors = rs.getString("authors");
					Double averageRatings = rs.getDouble("average_ratings");
					Integer ratings = rs.getInt("ratings");
					String imageUrl = rs.getString("image");
					Double price = rs.getDouble("price");
					Integer stock = rs.getInt("stock");

					topRatedBooks.add(new Book(title, new Author(authors),
							averageRatings, ratings, imageUrl, price, stock));
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR: Cannot Retrieve Books");
				new ArrayList<Book>();
			}
		}
		System.out.println("No Connection...");
		return topRatedBooks;

	}

	public static ArrayList<Book> getTopRatedBooks() {
		String getBooks = "SELECT title, authors, average_ratings, ratings, image, price, stock "
				+ "FROM books ORDER BY average_ratings DESC LIMIT 15;";

		return getBooksToDisplay(getBooks);
	}

	public static ArrayList<Book> getPopularBooks() {
		String getPopularBooks = "SELECT title, authors, average_ratings, ratings, image, price, stock "
				+ "FROM books ORDER BY ratings DESC LIMIT 15;";
		return getBooksToDisplay(getPopularBooks);
	}

	public static void main(String args[]) {
		for (Book b : getPopularBooks())
			System.out.println(b.getTitle());

	}

}
