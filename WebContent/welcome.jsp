<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<tagfiles:navbar_style />
</head>
<body>
	<tagfiles:title_header />

	<!-- Greet User Account Holder -->
	<div>
		<%
			response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
			response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
			response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
			response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

			String firstName = (String) session.getAttribute("firstName");
			String lastName = (String) session.getAttribute("lastName");
			String email = (String) session.getAttribute("email");
			String userRole = (String) session.getAttribute("userRole");

			if (firstName == null || lastName == null || email == null
					|| userRole == null) {
				response.sendRedirect(WEB.LOGIN);
			} else if (userRole.equals(WEB.ADMINISTRATOR)) {
				response.sendRedirect(WEB.ADMIN_WELCOME);
			}
		%>
		<p align="center" style="color: black; font-weight: bold;"><%="Hello, " + firstName + " " + lastName + ".\n"
					+ "Email: " + email%></p>
	</div>

	<tagfiles:customer_navbar />

	<p align=center
		style="color: black; font-size: 32px; font-weight: bold; margin-top: 200px;"><%="Welcome back " + firstName + " " + lastName + "!"%></p>
</body>
</html>