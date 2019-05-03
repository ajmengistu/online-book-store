package com.onlinebookstore.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Book {
	private String format, title, ISBN, publisher, image;
	private int yearPublished, stock, numberOfRatings, bookId;
	private BigDecimal price;
	private double averageRatings;
	private Author author;

	public Book(String format, String title, Author name, String ISBN,
			String publisher, int yearPublished, BigDecimal price, int stock,
			String image) {
		this.format = format;
		this.title = title;
		this.author = name;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.price = price;
		this.stock = stock;
		this.image = image;
	}

	public Book(String format, Integer bookId, String title, Author name,
			Double averageRatings, int numOfRatings, String imageUrl,
			BigDecimal price, int stock, int yearPublished) {
		this.format = format;
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
	public Book(Integer bookId, String title, Author name,
			Double averageRatings, int numOfRatings, String imageUrl,
			BigDecimal price, int stock, int yearPublished) {
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

	public Book(String title, Author author, String image, BigDecimal price,
			int bookId) {
		this.title = title;
		this.author = author;
		this.image = image;
		this.price = price;
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

	public String getFormat() {
		return format;
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

	public BigDecimal getPrice() {
		return price;
	}

	public int getQuantity() {
		return stock;
	}

	public String getImage() {
		return image;
	}

	public String toString() {
		return "Book[format: " + format + ", title: " + title + ", author: "
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
				pstmt.setDouble(6,
						Double.parseDouble(newBook.getPrice().toString()));
				pstmt.setInt(7, newBook.getQuantity());
				pstmt.setString(8, newBook.getFormat());
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
					String price = rs.getString("price");
					Integer stock = rs.getInt("stock");
					Integer yearPublished = rs
							.getInt("original_publication_year");

					topRatedBooks.add(new Book(bookId, title, new Author(
							authors), averageRatings, ratings, imageUrl,
							new BigDecimal(price), stock, yearPublished));
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
					String price = rs.getString("price");
					Integer stock = rs.getInt("stock");
					Integer yearPublished = rs
							.getInt("original_publication_year");

					book = new Book(bookId, title, new Author(authors),
							averageRatings, ratings, imageUrl, new BigDecimal(
									price), stock, yearPublished);
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

	public static ArrayList<Book> getBooks(int bookId) {
		Connection con = User.getConnection();

		String query = null;
		ArrayList<Book> books = new ArrayList<Book>();
		if (bookId == -1) {
			query = "SELECT * FROM books ORDER BY stock ASC LIMIT 10;";
		} else {
			query = "SELECT * FROM books WHERE book_id = ?;";
		}

		// Check if item already exist, if it does, get the quantity.
		PreparedStatement pstmt = null;
		if (con != null) {
			try {

				pstmt = con.prepareStatement(query);
				if (bookId != -1) {
					pstmt.setInt(1, bookId);
				}

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					int bkId = rs.getInt("book_id");
					String format = rs.getString("book_format");					
					String title = rs.getString("title");
					String authors = rs.getString("authors");
					Double averageRatings = rs.getDouble("average_ratings");
					Integer ratings = rs.getInt("ratings");
					String image = rs.getString("image");
					String price = rs.getString("price");
					Integer stock = rs.getInt("stock");
					Integer yearPublished = rs
							.getInt("original_publication_year");

					Book book = new Book(format, bkId, title, new Author(authors),
							averageRatings, ratings, image, new BigDecimal(
									price), stock, yearPublished);
					books.add(book);
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return books;
	}

	public static ArrayList<Book> getBookBySearch(String query) {
		ArrayList<Book> matchedBooks = new ArrayList<>();
		Connection con = User.getConnection();

		PreparedStatement pstmt = null;
		if (con != null) {
			try {
				String getBook = "SELECT * FROM books WHERE (title LIKE ?) OR (authors LIKE ?) ORDER BY ratings DESC LIMIT 4;";
				pstmt = con.prepareStatement(getBook);
				pstmt.setString(1, "%" + query + "%");
				pstmt.setString(2, "%" + query + "%");

				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {
					int bookId = rs.getInt("book_id");
					String title = rs.getString("title");
					String authors = rs.getString("authors");
					Double averageRatings = rs.getDouble("average_ratings");
					Integer ratings = rs.getInt("ratings");
					String image = rs.getString("image");
					String price = rs.getString("price");
					Integer stock = rs.getInt("stock");
					Integer yearPublished = rs
							.getInt("original_publication_year");

					Book book = new Book(bookId, title, new Author(authors),
							averageRatings, ratings, image, new BigDecimal(
									price), stock, yearPublished);
					matchedBooks.add(book);
				}

				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERROR: Cannot Retrieve Book");
				return new ArrayList<Book>();
			}
		}
		System.out.println("No Connection...");
		return matchedBooks;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;

		Book book = (Book) o;

		return book.getBookId() == this.bookId;
	}

	public static void main(String args[]) {
		// Book b = getBookById(2);
		// Book c = getBookById(3);
		// BigDecimal bc = new BigDecimal(1.02 + "");
		// BigDecimal cc = new BigDecimal(1.02 + "");
		// System.out.println(bc.toString());
		// System.out.println(cc.toString());
		// System.out.println("tostring" + cc);
		// BigDecimal r = bc.subtract(cc);
		// System.out.println(r);
		// System.out.println(r.equals(new BigDecimal("0.00")));
		// System.out.println(bc.multiply(new BigDecimal("18")));
		//
		// System.out.println(b.getPrice());
		// System.out.println(c.getPrice());
		// System.out.println(b.getPrice() - c.getPrice());
		// System.out.println(b.getPrice() * 77);
		// System.out.println(getBookById(8).getPrice());

		for (Book book : getBooks(1))
			System.out.println(book.toString());

	}
}
