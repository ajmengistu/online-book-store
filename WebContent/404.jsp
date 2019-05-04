<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Page Not Found</title>


<tagfiles:jQueryScripts />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:bootstrapCSS />
</head>
<body>
	<tagfiles:customer_navbar />
	<br>
	<div class="container">
		<h3 style="text-align: center">SORRY, we couldn't find that page</h3>
		<h5 style="text-align: center">
			Try searching or go to <a href="home">OnlineBookStore's home page</a>
		</h5>
		<br> <br> <img alt=""
			src="http://localhost:8080/online-book-store/images/brown-dog.jpg"
			style="height: 780px; width: 500px;">
	</div>
	<!-- jQuery -->
	<tagfiles:jquery_search_query_database />
	<!-- BootStrap Scripts -->
	<tagfiles:bootstrapScripts />
	<!-- CarouselJS -->
	<tagfiles:carouselJS />
</body>
</html>