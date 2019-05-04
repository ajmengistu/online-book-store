<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<tagfiles:bootstrapCSS />
<title>OnlineBookStore: Login</title>
<style type="text/css">
body {
	background-image: url("../images/books.jpg");
	background-repeat: no-repeat;
}
</style>
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
		if (status == null) // Client requested login page for the first time.
			status = "";
		// User attempted to login 
		if (session.getAttribute(WEB.USER) != null)
			response.sendRedirect(WEB.HOME);
	%>

	<div class="row">
		<div class="container" style="margin-top: 150px; margin-left: 500px">
			<div class="form" class="col-sm-10"
				style="width: 600px; margin-left: 250px; margin-top: 50px;">
				<div class="jumbotron">
					<div class="form-group" style="text-align: center;">
						<h1>ACCOUNT LOGIN</h1>
					</div>

					<form class="form-horizontal" style="margin-left: 50px;"
						method="post" action=<%=WEB.LOGIN_DO%>>
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
							<p align=center style="color: red; font-weight: bold;"><%=status%></p>
						</div>
						<div class="form-group">
							<button class="btn btn-primary">SIGN IN</button>
						</div>

						<p>
							<a href=<%=WEB.REGISTER%>>New User</a>
						</p>
						<p>
							<a href=<%=WEB.HOME%>> Maybe Later</a>
						</p>
					</form>

				</div>
			</div>
		</div>
	</div>
	
	<tagfiles:bootstrapScripts />
</body>
</html>