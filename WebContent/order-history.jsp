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
<title>OnlineBookStore: You Orders</title>

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
	<div class="container">
		<h3>Your Order History</h3>
		<hr>
	</div>


	<c:forEach items="${orderList}" var="order">
		<div class="container">
			<h5>Ordered ${order.getDateOrdered()}</h5>
			<div class="row">
				<div class="col-md-8">
					<div class="card card-body bg-light">
						<table class="table">
							<thead>
								<tr style="text-align: center;">
									<th style="width: 10%"></th>
									<th style="width: 35%"></th>
									<th style="width: 20%"></th>
									<!--<th style="width: 8%">Price</th>
								<th style="width: 50%">Quantity</th> -->
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${order.getOrderList()}" var="item">
									<tr>
										<td><img src="${item.getBook().getImage()}" alt="img"
											style="height: 150px; width: 90px; margin-left: 20px;"></td>
										<td><span class="badge badge-success"
											style="margin-bottom: 15px;">In Stock</span><br> <a
											style="font-size: 15px; font-weight: bold">${item.getBook().getTitle()}</a>
											<p>by ${item.getBook().getAuthor().getName()}</p>
											<p style="color: rgb(236, 74, 74); font-weight: bold;">$${item.getBook().getPrice()}</p>
											<p>
												<span style="font-weight: bold;">Quantity: </span>${item.getQuantity()}</p></td>
										<td></td>
										<td><a href="order-details?o=${order.getOrderNum()}"><button
													type="button" class="btn btn-secondary">Order
													details</button></a>
									</tr>

								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<hr>
			<br>
		</div>

	</c:forEach>

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

