<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <%@ page import="com.onlinebookstore.controller.WEB"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<title>Login</title>
<style type="text/css">
body {
	background-image: url("./images/books.jpg");
	background-repeat: no-repeat;
}
</style>
</head>
<body>
	<div class="row">
		<div class="container" style="margin-top: 150px; margin-left: 500px">
			<div class="form" class="col-sm-10"
				style="width: 600px; margin-left: 250px; margin-top: 50px;">
				<div class="jumbotron">
					<div class="form-group" style="text-align: center;">
						<h1>ACCOUNT LOGIN</h1>
					</div>

					<form class="form-horizontal" style="margin-left: 50px;"
						method="post" action="login.do">
						<div class="form-group input-group">
							<input type="email" required autocomplete="on"
								placeholder="Email" class="form-control" name="email" />
						</div>
						<div class="form-group input-group">
							<input type="password" required autocomplete="off"
								placeholder="Password" class="form-control" name="password" />
						</div>

						<!-- If user provides invalid login credentials. 
							 Show user a message of why they cannot login -->
						<div>
							<%
								String status = (String) request.getAttribute("status");
								if (status == null) // Client requested login page for the first time.
									status = "";
								// User attempted to login 
								if (session != null && session.getAttribute("firstName") != null)
									response.sendRedirect("welcome");
							%>
							<p align=center style="color: red; font-weight: bold;"><%=status%></p>
						</div>
						<div class="form-group">
							<button class="btn btn-primary">SIGN IN</button>
						</div>

						<p>
							<a href="/online-book-store/register">New User</a>
						</p>
						<p>
							<a href="/online-book-store/home">Maybe Later</a>
						</p>
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