<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
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
			String firstName = "";

			session = request.getSession(false);
			firstName = (String) session.getAttribute("firstName");

			if (session == null || firstName == null)
				response.sendRedirect("login");
		%>
		<p align="center" style="color: rgb(35, 216, 35);"><%="Hello, " + firstName%></p>
	</div>

	<tagfiles:customer_navbar />

	<p align=center
		style="color: black; font-size: 32px; font-weight: bold; margin-top: 200px;"><%="Welcome back, " + firstName + "!"%></p>



</body>
</html>