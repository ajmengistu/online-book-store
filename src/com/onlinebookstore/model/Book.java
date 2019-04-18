package com.onlinebookstore.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Book {
	private String genre, title, ISBN, publisher, image;
	private int yearPublished, stock, numberOfRatings, bookId;
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

	public Book(Integer bookId, String title, Author name,
			Double averageRatings, Integer numOfRatings, String imageUrl,
			Double price, Integer stock, Integer yearPublished) {
		this.title = title;
		this.author = name;
		this.averageRatings = averageRatings;
		this.numberOfRatings = numOfRatings;
		this.image = imageUrl;
		this.price = price;
		this.stock = stock;
		this.yearPublished = yearPublished;
		this.bookId = bookId;
	}

	public int getBookId() {
		return bookId;
	}

	public Author getAuthor() {
		return author;
	}

	public Double getAverageRatings() {
		return averageRatings;
	}

	public int getNumberOfRatings() {
		return numberOfRatings;
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
					Integer bookId = rs.getInt("book_id");
					String title = rs.getString("title");
					String authors = rs.getString("authors");
					Double averageRatings = rs.getDouble("average_ratings");
					Integer ratings = rs.getInt("ratings");
					String imageUrl = rs.getString("image");
					Double price = rs.getDouble("price");
					Integer stock = rs.getInt("stock");
					Integer yearPublished = rs
							.getInt("original_publication_year");

					topRatedBooks.add(new Book(bookId, title, new Author(
							authors), averageRatings, ratings, imageUrl, price,
							stock, yearPublished));
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR: Cannot Retrieve Books");
				return new ArrayList<Book>();
			}
		}
		System.out.println("No Connection...");
		return topRatedBooks;

	}

	public static ArrayList<Book> getTopRatedBooks() {
		String getBooks = "SELECT * FROM books "
				+ "ORDER BY average_ratings DESC LIMIT 25;";
		return getBooksToDisplay(getBooks);
	}

	public static ArrayList<Book> getPopularBooks() {
		String getPopularBooks = "SELECT * FROM books ORDER BY ratings DESC LIMIT 25;";
		return getBooksToDisplay(getPopularBooks);
	}

	public static ArrayList<Book> getTopRatedBooksByYear() {
		String getTopRatedBooksByYear = "SELECT * FROM books "
				+ "GROUP BY original_publication_year "
				+ "HAVING MAX(average_ratings) "
				+ "ORDER BY original_publication_year DESC LIMIT 25;";
		return getBooksToDisplay(getTopRatedBooksByYear);
	}

	public static ArrayList<Book> getAncientLiteratureBooks() {
		String getAncientBooks = "SELECT * FROM books ORDER BY original_publication_year LIMIT 25;";
		return getBooksToDisplay(getAncientBooks);
	}

	public static Book getBookById(int bookID) {
		Connection con = User.getConnection();

		Book book = null;
		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String getBook = "SELECT * FROM books WHERE book_id=?";
				pstmt = con.prepareStatement(getBook);
				pstmt.setInt(1, bookID);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					Integer bookId = rs.getInt("book_id");
					String title = rs.getString("title");
					String authors = rs.getString("authors");
					Double averageRatings = rs.getDouble("average_ratings");
					Integer ratings = rs.getInt("ratings");
					String imageUrl = rs.getString("image");
					Double price = rs.getDouble("price");
					Integer stock = rs.getInt("stock");
					Integer yearPublished = rs
							.getInt("original_publication_year");

					book = new Book(bookId, title, new Author(authors),
							averageRatings, ratings, imageUrl, price, stock,
							yearPublished);
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR: Cannot Retrieve Book");
				return null;
			}
		}
		System.out.println("No Connection...");
		return book;
	}

//	public static void main(String args[]) {
//		Book b = getBookById(2);
//		System.out.println(b.getTitle() + " " + b.getBookId());
//	}
}
