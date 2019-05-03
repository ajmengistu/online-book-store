<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: View Payments</title>

<!-- Bootstrap CSS -->
<tagfiles:bootstrapCSS />
<!-- Font-Awesome CSS -->
<tagfiles:awesomefonts />
<tagfiles:navbar_style />
</head>
<body>
	<tagfiles:admin_navbar />

	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect(WEB.LOGIN);
			// If a Customer requested this page 404 or redirect them to their home 
		} else if (user.getUserRole().equals(WEB.CUSTOMER)) {
			response.sendRedirect(WEB.HOME);
		}
		
	%>
	



	<br>
	<br>
	<br>

	<div align="center">
		<form action="search-payments" method="post">
			Search for Payment: <input type="text" name="q" placeholder="User Id" />
			<input type="submit" value="Submit" />
		</form>

		<br>
		<h2 align="center">Payment List</h2>
		<table class="table" align="center"
			style="width: 50%; margin-left: auto; margin-right: auto;">
			<thead class="thead-dark">
				<tr style="text-align: center;">
					<th scope="col">Order Id</th>
					<th scope="col">Order#</th>
					<th scope="col">Total</th>
					<th scope="col">Date Ordered</th>
					<th scope="col">Total</th>
					<th scope="col">Address Id</th>
					<th scope="col">User Id</th>
				</tr>
			</thead>

			<match:listusers query="${query}">
				<tr style="text-align: center;">
					<td>${orderId}</td>
					<td>${orderNum}</td>
					<td>${total}</td>
					<td>${dateOrdered}</td>
					<td>${total}</td>
					<td>${addressId}</td>
					<td>${userId}</td>
				</tr>
			</match:listusers>
		</table>
	</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<tagfiles:bootstrapScripts />
</body>
</html>