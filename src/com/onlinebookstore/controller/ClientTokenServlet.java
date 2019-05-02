package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/token")
public class ClientTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX,
				WEB.MERCHANT_ID, WEB.PUBLIC_KEY, WEB.PRIVATE_KEY);

		ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
		String clientToken = gateway.clientToken().generate(clientTokenRequest);

		// Send a client token as JSON to '/checkout' page to render
		// braintree.set() payment method

		ObjectMapper mapper = new ObjectMapper();
		String map = mapper.writeValueAsString(clientToken);

		response.setContentType("application/json");
		System.out.println(map);
		response.getWriter().println(map);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 404 ERROR
	}

}
