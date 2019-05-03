<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>
<%@ page import="com.onlinebookstore.model.User, java.math.BigDecimal"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>

<tagfiles:awesomefonts />
<tagfiles:bootstrapCSS />
</head>
<body>
	<tagfiles:admin_navbar />

	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect(WEB.LOGIN);
			// If a Customer requested this page 404 or redirect them to their home 
		} else if (user.getUserRole().equals(WEB.CUSTOMER)) {
			response.sendRedirect(WEB.HOME);
		}
		BigDecimal netSales = User.getOrdersTotal();
	%>
	<br>
	<div style="text-align: center">
		<h3>Net Sales</h3>
	</div>
	<br>
	<div class="row justify-content-center align-items-center">
		<div class="col-md-5">
			<div class="card card-body bg-white">
				<table class="table borderless">
					<tbody>
						<tr>
							<td style="font-weight: bold; font-size: 25px;">Net Sales:</td>
							<td style="font-size: 25px; color: green;">$<%=netSales%></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>


	<tagfiles:bootstrapScripts />
</body>
</html>