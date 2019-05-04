<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.onlinebookstore.controller.*"%>
<%@ page import="com.onlinebookstore.model.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Payment Successful</title>
</head>
<body>

	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		if (session.getAttribute(WEB.USER) == null)
			response.sendRedirect(WEB.LOGIN);
		if (session.getAttribute("payment-status") == null) {
			response.sendRedirect(WEB.HOME);
		}else{
			session.setAttribute("payment-status", null);
		}
	%>


	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js'></script>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.33.1/sweetalert2.all.js'></script>
	<script>
		swal({
			title : 'Payment Successful!',
			text : '',
			type : 'success'
		}).then(function() {
			window.location = '<%=WEB.ORDER_DETAILS%>?o=${hash}';
		});
	</script>
</body>
</html>