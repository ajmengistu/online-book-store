<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Change Password</title>
</head>

<tagfiles:jQueryScripts />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:bootstrapCSS />
<body>
	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		User user = (User) session.getAttribute(WEB.USER);
		String navbar = null;
		if (session.getAttribute(WEB.USER) == null)
			response.sendRedirect(WEB.LOGIN);
		else if (user.getUserRole().equals(WEB.ADMINISTRATOR)) {
			navbar = "<tagfiles:customer_navbar />";
		} else {
			navbar = "<tagfiles:admin_navbar />";
		}
	%>
	<div><%=navbar%></div>

	<br>
	<h3 style="text-align: center;">Change Password</h3>
	<br>
	<div class="row justify-content-center align-items-center">
		<div class="col-md-3">
			<div class="card card-body bg-white">
				<form class="form-horizontal" method="post"
					action=/password/update.do>
					<div class="form-group">
						<input type="password" required autocomplete="off"
							placeholder="Current password" class="form-control"
							name="current-password" />
					</div>

					<div class="form-group">
						<input type="password" required autocomplete="off"
							placeholder="New password" class="form-control"
							name="new-password" />
					</div>
					<div class="form-group">
						<input type="password" required autocomplete="off"
							placeholder="Confirm Password" class="form-control"
							name="confirm-password" />
					</div>

					<!-- Registration Confirmation Message-->
					<div>
						<p align=center style="color: red; font-weight: bold;"></p>
					</div>

					<button type="submit" class="btn btn-primary"
						style="margin-bottom: 0px">Update Password</button>
				</form>

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