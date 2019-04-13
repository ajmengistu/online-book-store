package com.onlinebookstore.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.onlinebookstore.model.User;

public class ListUsers extends SimpleTagSupport {
	ArrayList<User> user = null;
	String query = "";

	public void setQuery(String query) {
		this.query = query;
	}

	public void doTag() throws JspException, IOException {
		// if the query is changed, search based on the query
		if (!query.equals(""))
			user = User.getUserSearch(query);
		else
			user = User.getUsers();

		// We will only display first 10 for webpage
		int length = user.size();
		if (length > 10) {
			length = 10;
		}

		for (int i = 0; i < length; i++) {
			getJspContext().setAttribute("first", user.get(i).getFirstName());
			getJspContext().setAttribute("last", user.get(i).getLastName());
			getJspContext().setAttribute("email", user.get(i).getEmail());
			getJspContext().setAttribute("userRole", user.get(i).getUserRole());
			getJspBody().invoke(null);
		}
	}
}
