package com.onlinebookstore.model;

public class Item {
	private Book book;
	private int quantity;

	public Item() {
	}

	public Item(Book book, int quantity) {
		this.book = book;
		this.quantity = quantity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int subTotal() {
		return 0;
	}

	public String toString() {
		return book.toString() + " [Quantity: " + quantity + "]";
	}
}