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
	public String BOOK = "/book";
	public String BOOK_SEARCH = HOST + BOOK + "/bk";
	public String SHOPPING_CART = HOST + C + "/shopping-cart";
	public String HOME = HOST + C + "/home";
	public String REGISTER = HOST + C + "/register";
	public String CHECKOUT = HOST + C + "/checkout";
	public String ACCOUNT = HOST + C + "/account";
	public String BOOK_DETAILS = HOST + BOOK + "/book-details";
	public String ORDER_DETAILS = HOST + C + "/order-details";

	/* Servlets */
	public String CART_DO = HOST + C + "/cart.do";
	public String LOGIN_DO = HOST + C + "/login.do";
	public String LOGOUT_DO = HOST + C + "/logout.do";
	public String REGISTER_DO = HOST + C + "/register.do";
	public String CHECKOUT_DO = HOST + C + "/checkout.do";
	public String PLACE_ORDER_DO = HOST + C + "/place-order.do";

	/* Administrator Paths */
	public String ADMINISTRATOR = "ADMINISTRATOR";
	public String ADMIN = "/admin";
	public String ADMIN_HOME = HOST + ADMIN + "/admin-home";
	public String ADMIN_REGISTER = HOST + ADMIN + "/admin-register";
	public String ADD_BOOKS = HOST + ADMIN + "/admin-add-books";
	public String ADMIN_VIEW_USERS = HOST + ADMIN + "/admin-view-users";

	/* Customer Paths & Administrator Paths */
	public String USER = "user"; // current user (admin or customer)
	public String LOGIN = HOST + C + "/login";

	/* ERROR Paths */
	public String ERRORS = "errors";
	public String ERROR_404 = HOST + ERRORS + "/404.jsp";
	public String ADMIN_SIGN_OUT = HOST + ERRORS + "/admin-sign-out";
	public String PAYMENT_FAILED = HOST + ERRORS + "/payment-failed.jsp";

	/* SUCCESS Paths */
	public String SUCCESSFUL = "successful";
	public String SUCCESS = "/success";
	public String REGISTRATION_SUCCESSFUL = HOST + SUCCESS
			+ "/registration-successful.jsp";
	public String LOGIN_SUCCESSFUL = HOST + SUCCESS + "/login-successful.jsp";
	public String PAYMENT_SUCCESSFUL = HOST + SUCCESS
			+ "/payment-successful.jsp";

}
