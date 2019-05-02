package com.onlinebookstore.controller;

import java.math.BigDecimal;

public interface WEB {
	/* Payment Credentials */
	
	public String MERCHANT_ID = "64fmbfx4mt6pc69j";
	public String PUBLIC_KEY = "cnt5rnqt5zxcmcbf";
	public String PRIVATE_KEY = "e533d5e2074d2bdad3e78fb988e000f6";

	public BigDecimal SHIPPING_COST = new BigDecimal("5.99");

	public String WELCOME = "welcome"; // welcome.jsp
	public String ADMIN_WELCOME = "admin_welcome"; // admin_welcome.jsp
	public String LOGIN = "login"; // login.jsp
	public String REGISTER = "register";// register.jsp
	public String ADMIN_REGISTER = "admin_register";
	public String REGISTRATION_SUCCESSFUL = "registration_successful"; // registration_successful.jsp
	public String LOGIN_SUCCESSFUL = "login_successful"; // login_successful.jsp
	public String CUSTOMER = "CUSTOMER";
	public String ADMINISTRATOR = "ADMINISTRATOR";
	public String ADD_BOOKS = "add_books"; // add_books.jsp
	public String HOME = "home"; // home.jsp
	public String CHECKOUT = "checkout"; // checkout.jsp
	public String User = "user"; // current user
	public String SHOPPING_CART = "shopping_cart"; // shopping_cart.jsp
}
