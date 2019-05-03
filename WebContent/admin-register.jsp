<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administrator Registration</title>

<link rel="stylesheet" type="text/css" href="css/register.css">
<tagfiles:bootstrapCSS />
</head>
<style>
body {
	background-image: url("./images/architecture-building.jpg");
	background-repeat: no-repeat;
}
</style>
<body>
	<div class="container" style="margin-top: 150px;">
		<div class="row">
			<div class="col-mid-10 offset=mid-1">
				<div class="row">
					<div class="col-mid-5 register-left" style="color: black;">
						<img src="images/black_arrow.png">
						<h3>Join Us</h3>
						<h6>Create an account &amp; browse unlimited number of books!</h6>
						<button type="button" class="btn btn-primary">About</button>
					</div>
					<div class="col-mid-7 register-right">
						<h2>Sign up</h2>
						<h4 align="center">Create an account</h4>
						<div class="register-form">

							<form class="form-horizontal" method="post" action=register.do>
								<div class="form-group">
									<input required autocomplete="off" placeholder="Employee ID"
										class="form-control" name="emp_id" />
								</div>
								<div class="form-group">
									<input required autocomplete="off" placeholder="First Name"
										class="form-control" name="first_name" />
								</div>
								<div class="form-group">
									<input required autocomplete="off" placeholder="Last Name"
										class="form-control" name="last_name" />
								</div>
								<div class="form-group">
									<input type="email" required autocomplete="off"
										placeholder="Email" class="form-control" name="email" />
								</div>
								<div class="form-group">
									<input type="password" required autocomplete="off"
										placeholder="Password" class="form-control" name="password" />
								</div>
								<div class="form-group">
									<input type="password" required autocomplete="off"
										placeholder="Confirm Password" class="form-control"
										name="confirm_password" />
								</div>

								<!-- Registration Confirmation Message-->
								<div>
									<%
										// Get the status of the user registration from the RegisterServlet.java class
										String status = (String) request.getAttribute("status");
										// When this page is first requested via ".../register", show an empty string status.
										if (status == null)
											status = "";
										if (session != null && session.getAttribute("firstName") != null)
											response.sendRedirect("admin_welcome");
									%>
									<p align=center style="color: red; font-weight: bold;"><%=status%></p>
								</div>

								<button type="submit" class="btn btn-primary"
									style="margin-bottom: 0px">Register</button>
							</form>

							<br>
						</div>
						<p style="font-weight: bold; color: blue">
							<a href="/online-book-store/login">Login</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- BootStrap Scripts & CarouselJavaScript -->
	<tagfiles:carouselJS />
	<tagfiles:bootstrapScripts />
</body>
</html>