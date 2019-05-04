<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Shopping Cart</title>

<tagfiles:jQueryScripts />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:bootstrapCSS />
<style>
</style>
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		Integer size = (Integer) session.getAttribute("numOfItems");
		if (size != null && size == 0) {
			response.sendRedirect(WEB.HOME);
		}
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

	<c:if test="${! empty shoppingCart}">
		<div class="container" style="margin-left: 25%;">
			<div class="row" style="margin-left: -20%;">
				<div class="col-md-8" style="margin-left: 0%;">

					<div class="card card-body bg-light">
						<div style="margin-bottom: 1%;">
							<h4 style="font-weight: bold;">Shopping Cart</h4>
						</div>
						<table class="table">
							<thead>
								<tr style="text-align: center;">
									<th style="width: 10%">Product</th>
									<th style="width: 35%"></th>
									<th style="width: 8%">Price</th>
									<th style="width: 50%">Quantity</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="count" value="0" scope="page" />
								<c:forEach items="${shoppingCart}" var="item">
									<tr>
										<td><img src="${item.getBook().getImage()}" alt="img"
											style="height: 150px; width: 90px; margin-left: 20px;"></td>
										<td><a
											href="book?id=${item.getBook().getBookId()}&title=${item.getBook().getTitle()}"
											style="font-weight: bold; font-size: 20px;">${item.getBook().getTitle()}</a>
											<p>by ${item.getBook().getAuthor().getName()}</p></td>
										<td style="color: rgb(236, 74, 74); font-weight: bold;">$${item.getBook().getPrice()}</td>
										<td>
											<form
												action='<%=WEB.CART_DO%>?id=${item.getBook().getBookId()}'
												class="form-inline" method="post">
												<select onchange="change(${count})" name="quantity"
													class="form-control" style="margin-left: 42%;">
													<option value="0">0</option>
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
													<option selected="selected">${item.getQuantity()}</option>
												</select> <span id="${count}"></span>
											</form>
										</td>
									</tr>
									<c:set var="count" value="${count + 1}" scope="page" />
								</c:forEach>


							</tbody>
						</table>
					</div>
				</div>
				<div class="col-md-4">
					<div class="card card-body bg-light">
						<h4 style="font-weight: bold;">Cart Summary</h4>
						<br>
						<table class="table">
							<tbody>
								<tr>
									<td>Subtotal</td>
									<td style="color: rgb(236, 74, 74); font-weight: bold;">$${subTotal}</td>
								</tr>
								<tr>
									<td>Shipping</td>
									<td style="color: rgb(236, 74, 74); font-weight: bold;">$${shippingCost}</td>
								</tr>
								<tr style="background-color: #ccffb3; margin-top: 10%;">
									<td>Total</td>
									<td style="color: rgb(236, 74, 74); font-weight: bold;">$${totalCost}</td>
								</tr>
							</tbody>
						</table>
						<br> <a href=<%=WEB.CHECKOUT_DO%> class="btn btn-primary">Checkout</a>
					</div>
				</div>
			</div>
		</div>
		<div class="container" style="margin-left: 14.3%; margin-top: 1%;">
			<a href=<%=WEB.HOME%> class="btn btn-warning"><i
				class="fa
                    fa-angle-left"></i> Continue Shopping</a>
		</div>
	</c:if>

	<c:choose>
		<c:when test="${user != null}">
			<c:if test="${empty shoppingCart}">
				<div class="container">
					<h4>Your Shopping Cart is empty.</h4>
					<p>
						Continue shopping on <a href=<%=WEB.HOME%>>OnlineBookStore
							homepage!</a>
					</p>
				</div>
			</c:if>

		</c:when>
		<c:otherwise>
			<c:if test="${empty shoppingCart}">
				<div class="container">
					<h4>Your Shopping Cart is empty.</h4>
					<p>
						If you already have an account, <a href=<%=WEB.LOGIN%>>Sign In</a>
						to see your Cart. Continue shopping on <a href=<%=WEB.HOME%>>OnlineBookStore
							homepage!</a>
					</p>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>



	<script>
		function change(id) {			
		  document.getElementById(id).innerHTML = '<button class=\"btn btn-warning\" style=\"margin-left: 10%;\">Update</button>';
		}
	</script>

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