package com.onlinebookstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlinebookstore.model.Book;
import com.onlinebookstore.model.Item;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart.do")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BigDecimal SHIPPING_COST = new BigDecimal("5.99");
	public List<Item> shoppingCart = new ArrayList<Item>();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("shopping_cart");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int bookId = Integer.parseInt(request.getParameter("id"));
		String quantity = request.getParameter("quantity");

		System.out.println("item #:" + bookId);

		HttpSession session = request.getSession(false);
		if (session.getAttribute("shoppingCart") == null) {
			shoppingCart = new ArrayList<>();
		}

		System.out.println("Shopping Cart servlet--------------");

		System.out.println(shoppingCart.toString());

		for (int i = 0; i < shoppingCart.size(); i++) {
			System.out.println(i + ": " + shoppingCart.get(i));
		}

		int x = -1;
		for (int i = 0; i < shoppingCart.size(); i++) {
			if (shoppingCart.get(i).getBook().getBookId() == bookId) {
				System.out.println("Found it");
				x = i;
			}
		}

		// If item already exists
		if (x != -1) {
			// Update Shopping Cart
			if (quantity == null) {
				// Adding the same item to cart
				System.out.println("adding item to cart");
				shoppingCart.get(x).setQuantity(
						shoppingCart.get(x).getQuantity() + 1);
			} else {
				// Updating the quantity
				int qty = Integer.parseInt(quantity);
				if (qty == 0) {
					System.out.println("removing item from cart!");
					shoppingCart.remove(x);
				} else {
					System.out.println("Updating cart quantity");
					// set the new quantity
					shoppingCart.get(x).setQuantity(qty);
				}
			}
		} else { // Otherwise, add new item to cart

			Book book = Book.getBookById(bookId);
			Item item = new Item(book, 1);
			shoppingCart.add(item);
		}

		System.out.println("Shopping Cart servlet--------------");

		BigDecimal totalCost = new BigDecimal("0.00");
		BigDecimal subTotal = new BigDecimal("0.00");
		BigDecimal shippingCost = new BigDecimal("0.00");
		for (int i = 0; i < shoppingCart.size(); i++) {
			BigDecimal temp = new BigDecimal(shoppingCart.get(i).getBook()
					.getPrice()
					+ "").multiply(new BigDecimal(shoppingCart.get(i)
					.getQuantity() + ""));
			subTotal = subTotal.add(temp);
		}
		if (!subTotal.equals(new BigDecimal("0.00"))) {
			shippingCost = this.SHIPPING_COST;
			totalCost = shippingCost.add(subTotal);
		}
		System.out.println();
		session.setAttribute("totalCost", totalCost);
		session.setAttribute("subTotal", subTotal);
		session.setAttribute("shippingCost", shippingCost);

		session.setAttribute("shoppingCart", shoppingCart);

		response.sendRedirect("shopping_cart");
	}
}
