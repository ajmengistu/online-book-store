<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Users</title>

<!-- Bootstrap CSS -->
<tagfiles:bootstrapCSS />
<!-- Font-Awesome CSS -->
<tagfiles:awesomefonts />
<tagfiles:navbar_style />
</head>
<body>
	<tagfiles:admin_navbar />

	<div>
		<%-- 	<%
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
			} else if (userRole.equals(WEB.CUSTOMER)) {
				response.sendRedirect(WEB.WELCOME);
			}
		%>

		<p align="center" style="color: black; font-weight: bold;"><%="Hello, " + firstName + " " + lastName + ".\n"
					+ "Email: " + email%></p>
 --%>
	</div>



	<br>
	<br>
	<br>

	<div align="center">
		<form action="search-users" method="post">
			Search for Users: <input type="text" name="q" placeholder="Search" /> <input
				type="submit" value="Submit" />
		</form>

		<br>
		<h2 align="center">User List</h2>
		<table class="table" align="center"
			style="width: 50%; margin-left: auto; margin-right: auto;">
			<thead class="thead-dark">
				<tr style="text-align: center;">
					<th scope="col">First</th>
					<th scope="col">Last</th>
					<th scope="col">Email</th>
					<th>Role</th>
				</tr>
			</thead>

			<match:listusers query="${query}">
				<tr style="text-align: center;">
					<td>${first}</td>
					<td>${last}</td>
					<td>${email}</td>
					<td>${userRole}</td>
				</tr>
			</match:listusers>
		</table>
	</div>

	<!-- Footer -->
	<tagfiles:footer />
	<!-- BootStrap Scripts -->
	<tagfiles:bootstrapScripts />
</body>
</html>