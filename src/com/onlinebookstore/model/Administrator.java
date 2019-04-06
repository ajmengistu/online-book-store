package com.onlinebookstore.model;

public class Administrator extends Person {
	String employeeID;

	public Administrator(String eid, String firstName, String lastName,
			String email, String password) {
		super(firstName, lastName, email, password);
	}
}
