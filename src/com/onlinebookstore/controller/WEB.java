package com.onlinebookstore.controller;

import java.math.BigDecimal;

public interface WEB {
	/* Payment Credentials */
	public String MERCHANT_ID = "64fmbfx4mt6pc69j";
	public String PUBLIC_KEY = "cnt5rnqt5zxcmcbf";
	public String PRIVATE_KEY = "e533d5e2074d2bdad3e78fb988e000f6";

	public String HOST = "/online-book-store";

	public BigDecimal SHIPPING_COST = new BigDecimal("5.99");

	/* Customer Paths */
	public String CUSTOMER = "CUSTOMER";
	public String C = "/c";
	public String SHOPPING_CART = HOST + C + "/shopping-cart";
	public String HOME = HOST + C + "/home";
	public String REGISTER = HOST + C + "/register";
	public String CHECKOUT = HOST + C + "/checkout";
	

	/* Servlets */
	public String CART_DO = HOST + C + "/cart.do";
	public String LOGIN_DO = HOST + C + "/login.do";
	public String REGISTER_DO = HOST + C + "/register.do";

	/* Administrator Paths */
	public String ADMINISTRATOR = "ADMINISTRATOR";
	public String ADMIN = "/admin";
	public String ADMIN_HOME = HOST + ADMIN + "/admin-home";
	public String ADMIN_REGISTER = HOST + ADMIN + "/admin-register";
	public String ADD_BOOKS = HOST + ADMIN + "/admin-add-books";

	/* Customer Paths & Administrator Paths */
	public String USER = "user"; // current user (admin or customer)
	public String LOGIN = HOST + C + "/login";

	/* ERROR Paths */
	public String ERRORS = "errors";
	public String ERROR_404 = HOST + ERRORS + "/404.jsp";
	public String ADMIN_SIGN_OUT = HOST + ERRORS + "/admin-sign-out";

	/* SUCCESS Paths */
	public String SUCCESS = "/success";
	public String REGISTRATION_SUCCESSFUL = HOST + SUCCESS
			+ "/registration-successful.jsp";
	public String LOGIN_SUCCESSFUL = HOST + SUCCESS + "/login-successful.jsp";
}
