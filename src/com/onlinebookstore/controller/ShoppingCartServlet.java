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
import com.onlinebookstore.model.User;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart.do")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BigDecimal SHIPPING_COST = new BigDecimal("5.99");
	public List<Item> shoppingCart = new ArrayList<Item>();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// response.sendRedirect("shopping_cart");
		// doPost(request, response);
		System.out.println("Inside");
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			System.out.println("FIRSTTIME------------");

			if (session.getAttribute("shoppingCart") == null) {
				User user = (User) session.getAttribute("user");
				shoppingCart = User.getCart(user.getUserId());
				System.out.println("hello");
			}

		}

		if (session != null) {
			System.out.println("Calculating User shopping cart costs...");
			getCost(session);
			getTotalNumOfItems(session);
			session.setAttribute("shoppingCart", shoppingCart);
			response.sendRedirect("shopping_cart");
		}
	}

	private void getTotalNumOfItems(HttpSession session) {
		int numOfItems = 0;

		for (Item item : shoppingCart) {
			numOfItems += item.getQuantity();
		}

		session.setAttribute("numOfItems", numOfItems);

	}

	/**
	 * A post request is made when a Customer/User adds an item to their cart by
	 * clicking on 'Add to Cart' button in the 'view_book_details.jsp' page or
	 * when a Customer/User updates their item count in the 'shopping_cart.jsp'
	 * page by clicking on 'Update' button.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get parameters bookId and quantity.
		int bookId = Integer.parseInt(request.getParameter("id"));
		// If quantity is null, user is clicking on the 'Add to Cart' button.
		// Else, user is clicking on the 'Update' button in shopping Cart page.
		String quantity = request.getParameter("quantity");
		// Check if a shoppingCart has not be created already for this session.
		HttpSession session = request.getSession(false);
		session.setAttribute("numOfItems", shoppingCart.size());

		if (session.getAttribute("shoppingCart") == null) {
			// Only done once per session.
			shoppingCart = new ArrayList<>();
		}

		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			// User is logged-in (has an account)
			// Remove all the items already in the shoppingCart
			if (session.getAttribute("shoppingCart") != null) {
				shoppingCart = new ArrayList<>();
			}

			// User clicked on Add to Cart
			if (quantity == null) {
				User.addBookToCart(user.getUserId(), bookId);
				shoppingCart = User.getCart(user.getUserId());
			} else { // User wants to update Cart
				User.updateCart(user.getUserId(), bookId,
						Integer.parseInt(quantity));
				shoppingCart = User.getCart(user.getUserId());
			}
		} else { // Anonymous User (has no account)
			// Check if the Book with bookId is already in the shoppingCart
			int index = getIndexOfBook(bookId);
			// Update Shopping Cart
			updateShoppingCart(index, quantity, bookId);
		}

		getCost(session);
		getTotalNumOfItems(session);
		session.setAttribute("shoppingCart", shoppingCart);
		response.sendRedirect("shopping_cart");

	}

	private void getCost(HttpSession session) {
		BigDecimal totalCost = new BigDecimal("0.00");
		BigDecimal subTotal = new BigDecimal("0.00");
		BigDecimal shippingCost = new BigDecimal("0.00");

		if (session != null && shoppingCart != null) {
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
		}

		session.setAttribute("totalCost", totalCost);
		session.setAttribute("subTotal", subTotal);
		session.setAttribute("shippingCost", shippingCost);
	}

	private void updateShoppingCart(int index, String quantity, int bookId) {
		if (index != -1) {
			// If item already exists
			// Update Shopping Cart
			if (quantity == null) {
				// Adding the same item to cart
				System.out.println("adding item to cart");
				shoppingCart.get(index).setQuantity(
						shoppingCart.get(index).getQuantity() + 1);
			} else {
				// Updating the quantity
				int qty = Integer.parseInt(quantity);
				if (qty == 0) {
					System.out.println("removing item from cart!");
					shoppingCart.remove(index);
				} else {
					System.out.println("Updating cart quantity");
					// set the new quantity
					shoppingCart.get(index).setQuantity(qty);
				}
			}
		} else { // Otherwise, add new item to cart

			Book book = Book.getBookById(bookId);
			Item item = new Item(book, 1);
			shoppingCart.add(item);
		}

	}

	private int getIndexOfBook(int bookId) {
		int x = -1;
		for (int i = 0; i < shoppingCart.size(); i++) {
			if (shoppingCart.get(i).getBook().getBookId() == bookId) {
				System.out.println("Found it");
				x = i;
			}
		}
		return x;
	}
}
