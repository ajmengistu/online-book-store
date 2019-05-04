<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.*, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: View Stock</title>

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
			request.getRequestDispatcher(WEB.ERROR_404).forward(
			request, response);
		}
		
		int q = -1;
		String query = request.getParameter("q");
		try{
			q = Integer.parseInt(query);
		}catch(NumberFormatException e){			
		}
		
		ArrayList<Book> books = Book.getBooks(q);
	%>



	<br>
	<br>
	<br>

	<div align="center">
		<form action="admin-stock" method="post">
			Search for Book: <input type="number" name="q" placeholder="Book Id" />
			<input type="submit" value="Submit" />
		</form>

		<br>
		<h2 align="center">Book List</h2>
		<table class="table" align="center"
			style="width: 50%; margin-left: auto; margin-right: auto;">
			<thead class="thead-dark">
				<tr style="text-align: center;">
					<th scope="col">Book Id</th>
					<th scope="col">Title</th>
					<th scope="col">Authors</th>
					<th scope="col">Publication Year</th>
					<th scope="col">Image</th>
					<th scope="col">Price</th>
					<th scope="col">Stock</th>
					<th scope="col">Format</th>
				</tr>
			</thead>

			<%
				for(Book book : books){
			%>
			<tr style="text-align: center;">
				<td><%=book.getBookId()%></td>
				<td><%=book.getTitle()%></td>
				<td><%=book.getAuthor().getName()%></td>
				<td><%=book.getYearPublished()%></td>
				<td><img src="<%=book.getImage()%>" alt="img"
					style="height: 150px; width: 90px; margin-left: 20px;"></td>
				<td>$<%=book.getPrice()%></td>
				<td><%=book.getQuantity()%></td>
				<td><%=book.getFormat()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>

	<!-- Footer -->
	<tagfiles:footer />
	<!-- BootStrap Scripts -->
	<tagfiles:bootstrapScripts />
</body>
</html>