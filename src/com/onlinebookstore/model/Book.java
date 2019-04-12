package com.onlinebookstore.model;

public class Book {
	private String genre, title, author, ISBN, publisher, yearPublished, image;
	private double price;
	private int quantity;

	public Book(String genre, String title, String author, String ISBN,
			String publisher, String yearPublished, double price, int quantity,
			String image) {
		this.genre = genre;
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}

	public String getAuthor() {
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

	public String getYearPublished() {
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

	public static boolean addBooks(Book newBook) {

		return false;
	}
}
