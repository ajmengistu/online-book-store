<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Books</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<!-- Font-Awesome CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Administrator Navigation Bar CSS -->
<tagfiles:navbar_style />
</head>
<body>
	<!-- Administrator User Navigation Bar -->
	<tagfiles:admin_navbar />
	<div>
		<%-- <%
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
				/* If a Customer attempts to access this Administrator webpage, send them to their welcome page. */
				response.sendRedirect(WEB.WELCOME);
			}
		%>
		<p align="center" style="color: black; font-weight: bold;"><%="Hello, " + firstName + " " + lastName + ".\n"
					+ "Email: " + email%></p>
 --%>
	</div>

	<!-- Form to Add Books to the Database -->
	<br>
	<div class="row">
		<div class="container" style="margin-top: 10px;">
			<div class="form" class="col-sm-10">
				<div class="jumbotron">
					<div class="form-group" style="text-align: center;">
						<h1>Add a New Book</h1>
					</div>

					<form class="form-horizontal" style="margin-left: 50px;"
						method="post" action="add_books.do">
						<div class="form-group input-group">
							<input required placeholder="Title" class="form-control"
								name="title" />
						</div>
						<div class="form-group input-group">
							<input required placeholder="Author" class="form-control"
								name="author" />
						</div>
						<div class="form-group input-group">
							<input required placeholder="ISBN" class="form-control"
								name="isbn" />
						</div>
						<div class="form-group input-group">
							<input required placeholder="Publisher" class="form-control"
								name="publisher" />
						</div>
						<div class="form-group input-group">
							<input required placeholder="Year Published" class="form-control"
								type="number" name="yearPublished" />
						</div>
						<div class="form-group input-group">
							<input required placeholder="Price" class="form-control"
								type="decimal" name="price" />
						</div>
						<div class="form-group input-group">
							<input required placeholder="Quantity" class="form-control"
								type="number" name="quantity" />
						</div>
						<div class="form-group">
							Type: <select name="type">
								<option value="fiction" required>Fiction</option>
								<option value="nonfiction" required>Non-fiction</option>
							</select>
						</div>
						<div class="form-group">
							<label for="file-upload">Select Image: </label> <input
								type="file" name="image">
						</div>

						<div class="form-group">
							<button class="btn btn-primary">Submit</button>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>
</body>
</html>