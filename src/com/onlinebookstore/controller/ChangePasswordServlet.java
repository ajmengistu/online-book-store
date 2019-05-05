package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlinebookstore.model.User;

@WebServlet("/password/update.do")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(WEB.ERROR_404);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String currentPass = request.getParameter("current-password");
		String newPass = request.getParameter("new-password");
		String confirmPass = request.getParameter("confirm-password");
		System.out.println(currentPass + " " + confirmPass);

		User user = (User) request.getSession().getAttribute(WEB.USER);

		if (confirmPass.equals(newPass)
				&& User.verifyUserLoginCredentials(user.getEmail(), currentPass) != null) {
			User.changePassword(newPass, user.getUserId());
			response.sendRedirect(WEB.CHANGE_PASSWORD_SUCCESSFUL);
		} else {
			response.sendRedirect(WEB.CHANGE_PASSWORD_FAILED);
		}
	}

}
