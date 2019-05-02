<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: You Account</title>

<tagfiles:jQueryScripts />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:bootstrapCSS />
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		if (session.getAttribute("user") == null)
			response.sendRedirect(WEB.LOGIN);
	%>



	<!-- Navigation Bar -->
	<c:choose>
		<c:when test="${user != null}">
			<tagfiles:customer_navbar />
			<br />
		</c:when>
		<c:otherwise>
			<tagfiles:home_page_navbar />
			<br />
		</c:otherwise>
	</c:choose>


	<br>
	<div style="text-align: center">
		<h3>Login & Security</h3>
	</div>
	<br>
	<div class="row justify-content-center align-items-center">
		<div class="col-md-5">
			<div class="card card-body bg-white">
				<table class="table borderless">
					<tbody>
						<tr>
							<td style="font-weight: bold;">Name:</td>
							<td style="">${user.getFirstName()}&nbsp${user.getLastName()}</td>
							<td><button type="button" class="btn btn-secondary">Edit</button>
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold;">Email:</td>
							<td style="">${user.getEmail()}</td>
							<td><button type="button" class="btn btn-secondary">Edit</button>
							</td>

						</tr>
						<tr>
							<td style="font-weight: bold;">Password:</td>
							<td style="">**********</td>
							<td><button type="button" class="btn btn-secondary">Edit</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>


	<!-- Footer -->
	<tagfiles:footer />
	<!-- jQuery -->
	<tagfiles:jquery_search_query_database />
	<!-- BootStrap Scripts -->
	<tagfiles:bootstrapScripts />
	<!-- CarouselJS -->
	<tagfiles:carouselJS />
</body>
</html>

