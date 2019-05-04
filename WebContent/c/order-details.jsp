<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page
	import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.*, java.math.BigDecimal"%>

<%@ taglib prefix="match" uri="match-functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Online Shopping for Books</title>

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
		Order order = (Order) session.getAttribute("order");
		BigDecimal total = WEB.SHIPPING_COST.add(order.getTotal());
	%>










	<!-- Navigation Bar -->
	<c:choose>
		<c:when test="${user != null}">
			<tagfiles:customer_navbar />
		</c:when>
		<c:otherwise>
			<tagfiles:home_page_navbar />
		</c:otherwise>
	</c:choose>

	<br>

	<div class="container">
		<h3>Order Details</h3>
		<br>
		<p>Ordered on ${order.getDateOrdered()} &nbsp |&nbsp Order#
			${order.getOrderNum()}</p>
		<hr>
	</div>
	<br>
	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<h6>Shipping Address</h6>
				<br> ${user.getFirstName()} ${user.getLastName()}
				<p>${order.getShippingAddress()}</p>
			</div>
			<div class="col-md-4">
				<div class="card card-body bg-white">
					<h6 style="font-weight: bold;">Order Summary</h6>
					<table class="table borderless">
						<tbody>
							<tr>
								<td>Items (${order.getOrderList().size()}):</td>
								<td style="">$${order.getTotal()}</td>


							</tr>
							<tr>
								<td><span>Shipping &#38; handling:</span></td>
								<td style="">$<%=WEB.SHIPPING_COST%></td>

							</tr>
							<tr style="background-color: #ccffb3; margin-top: 10%;">
								<td style="font-weight: bold; font-size: 15px;">Order Total</td>
								<td style="font-weight: bold; font-size: 15px;">$<%=total%></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<hr>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-8">
				<div class="card card-body bg-light">
					<table class="table">
						<thead>
							<tr style="text-align: center;">
								<th style="width: 10%">Product</th>
								<!-- <th style="width: 35%"></th>
								<th style="width: 8%">Price</th>
								<th style="width: 50%">Quantity</th> -->
							</tr>
						</thead>
						<tbody>
							<c:set var="count" value="0" scope="page" />
							<c:forEach items="${order.getOrderList()}" var="item">
								<tr>
									<td><img src="${item.getBook().getImage()}" alt="img"
										style="height: 150px; width: 90px; margin-left: 20px;"></td>
									<td><a style="font-size: 15px; font-weight: bold">${item.getBook().getTitle()}</a>
										<p>by ${item.getBook().getAuthor().getName()}</p>
										<p style="color: rgb(236, 74, 74); font-weight: bold;">$${item.getBook().getPrice()}</p>
										<p>
											<span style="font-weight: bold;">Quantity: </span>${item.getQuantity()}</p></td>
								</tr>
								<c:set var="count" value="${count + 1}" scope="page" />
							</c:forEach>


						</tbody>
					</table>
				</div>
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