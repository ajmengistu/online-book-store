<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.onlinebookstore.controller.*"%>
<%@ page import="com.onlinebookstore.model.*"%>
<%@ page import="java.util.ArrayList"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Login Successful</title>
</head>
<body>

	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		String url = null;

		if ((String) session.getAttribute("login_status") == null) {
			response.sendRedirect(WEB.LOGIN);
		} else {
			User user = (User) session.getAttribute("user");
			
			if (user.getUserRole().equals(WEB.ADMINISTRATOR)) {
				url = "/online-book-store/admin_welcome";
			} else {
				if (session.getAttribute("cart") != null) {
					url = "/online-book-store/cart.do";
				} else {
					// Send User to their home page. Note: User must have signed-in with an empty shoppingCart. 
					url = "/online-book-store/home";
				}
			}
			session.setAttribute("login_status", null);
		}
	%>

	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js'></script>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.all.js'></script>
	<script>
		swal({
			title : 'Welcome, ${user.getFirstName()} ${user.getLastName()}!',
			text : 'Login Successful!',
			type : 'success'
		}).then(function() {
			window.location = '<%=url%>';
		});
	</script>
</body>
</html>