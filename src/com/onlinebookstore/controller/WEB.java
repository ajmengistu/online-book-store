package com.onlinebookstore.controller;

import java.math.BigDecimal;

public interface WEB {
	/* Payment Credentials */
	public String MERCHANT_ID = "64fmbfx4mt6pc69j";
	public String PUBLIC_KEY = "cnt5rnqt5zxcmcbf";
	public String PRIVATE_KEY = "e533d5e2074d2bdad3e78fb988e000f6";

	public String HOST = "http://localhost:8080/online-book-store/";

	public BigDecimal SHIPPING_COST = new BigDecimal("5.99");

	/* Customer Paths */
	public String SHOPPING_CART = "shopping_cart";
	public String HOME = "home";

	/* Administrator Paths */
	public String ADMIN_HOME = "admin-home";
	public String LOGIN = "login";
	public String REGISTER = "register";

	/* Customer Paths & Administrator Paths */
	public String ADMIN_REGISTER = "admin-register";
	public String ADD_BOOKS = "admin-add-books";

	/* ERROR Paths */
	public String ERROR_404 = "404.jsp";

	/* SUCCESS Paths */
	public String REGISTRATION_SUCCESSFUL = "registration_successful";
	public String LOGIN_SUCCESSFUL = "login_successful";

	/* Constants */
	public String CUSTOMER = "CUSTOMER";
	public String ADMINISTRATOR = "ADMINISTRATOR";
	public String USER = "user"; // current user (admin or customer)
	public String CHECKOUT = "checkout";
}
