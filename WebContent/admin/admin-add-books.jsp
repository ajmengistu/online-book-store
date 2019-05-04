<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page
	import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Admin Add Books</title>

<tagfiles:bootstrapCSS />
</head>
<body>
	<!-- Administrator User Navigation Bar -->
	<tagfiles:admin_navbar />
	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("http://localhost:8080/online-book-store/login");
			// If a Customer requested this page 404 or redirect them to their home 
		} else if (user.getUserRole().equals(WEB.CUSTOMER)) {
			request.getRequestDispatcher(WEB.ERROR_404).forward(request,
					response);
		}
	%>


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
	<!-- Footer -->
	<tagfiles:footer />
	<!-- BootStrap Scripts -->
	<tagfiles:bootstrapScripts />
</body>
</html>