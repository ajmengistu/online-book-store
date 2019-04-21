<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Shopping Cart</title>

<tagfiles:bootstrapCSS />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:navbar_style />
<style>
body, html {
	height: 100%;
}
</style>
</head>
<body>

	<br>
	<tagfiles:title_header />
	<tagfiles:searchbar />
	<br>

	<!-- Navigation Bar -->
	<%
		if (session == null || session.getAttribute("firstName") == null) {
	%>
	<tagfiles:home_page_navbar />
	<%
		} else {
	%>
	<tagfiles:customer_navbar />
	<%
		}
	%>
	<br>
	<br>

	<c:if test="${! empty shoppingCart}">
		<div class="container" style="margin-left: 25%;">
			<div class="row" style="margin-left: -20%;">
				<div class="col-md-8" style="margin-left: 0%;">
					<div class="card card-body bg-light">
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
										<td><a href="book?id=${item.getBook().getBookId()}"
											style="font-weight: bold; font-size: 20px;">${item.getBook().getTitle()}</a>
											<p>by ${item.getBook().getAuthor().getName()}</p></td>
										<td style="color: rgb(236, 74, 74); font-weight: bold;">$${item.getBook().getPrice()}</td>
										<td>
											<form action="cart.do?id=${item.getBook().getBookId()}"
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
													<%-- </select> <span id="${item.getBook().getBookId()}"></span> --%>
												</select> <span id="${count}"></span>
												<%-- <button id="${item.getBook().getBookId()}" class="btn btn-warning" style="margin-left: 10%; display: none;">Update</button> --%>
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
						<h4>Cart Summary</h4>
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
						<br> <a href="checkout.do" class="btn btn-primary">Checkout</a>
					</div>
				</div>
			</div>
		</div>
		<div class="container" style="margin-left: 14.3%; margin-top: 1%;">
			<a href="home" class="btn btn-warning"><i
				class="fa
                    fa-angle-left"></i> Continue Shopping</a>
		</div>
	</c:if>



	<c:if test="${empty shoppingCart}">
		<div class="container">
			<h4>Your Shopping Cart is empty.</h4>
			<p>
				If you already have an account, <a href="login">Sign In</a> to see
				your Cart. Continue shopping on <a href="home">OnlineBookStore
					homepage!</a>
			</p>
		</div>
	</c:if>







	<!-- Footer -->
	<footer class="page-footer font-small blue-grey lighten-5"
		style="margin-top: 37%; font-size: 13.5px;">

	<div style="background-color: #21d192;">
		<div class="container">

			<!-- Grid row-->
			<div class="row py-4 d-flex align-items-center">
				<!-- Grid column -->


				<!-- Grid column -->

			</div>
			<!-- Grid row-->

		</div>
	</div>

	<!-- Footer Links -->
	<div class="container text-center text-md-left mt-5">

		<!-- Grid row -->
		<div class="row mt-3 dark-grey-text">

			<!-- Grid column -->
			<div class="col-md-3 col-lg-4 col-xl-3 mb-4">

				<!-- Content -->
				<h6 class="text-uppercase font-weight-bold">Online Book Store</h6>
				<hr class="teal accent-3 mb-4 mt-0 d-inline-block mx-auto"
					style="width: 60px;">
				<p>Here you can use rows and columns here to organize your
					footer content. Lorem ipsum dolor sit amet, consectetur adipisicing
					elit.</p>

			</div>
			<!-- Grid column -->

			<!-- Grid column -->
			<div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">

				<!-- Links -->
				<h6 class="text-uppercase font-weight-bold">Products</h6>
				<hr class="teal accent-3 mb-4 mt-0 d-inline-block mx-auto"
					style="width: 60px;">
				<p>
					<a class="dark-grey-text" href="#!">MDBootstrap</a>
				</p>
				<p>
					<a class="dark-grey-text" href="#!">MDWordPress</a>
				</p>
				<p>
					<a class="dark-grey-text" href="#!">BrandFlow</a>
				</p>
				<p>
					<a class="dark-grey-text" href="#!">Bootstrap Angular</a>
				</p>

			</div>
			<!-- Grid column -->

			<!-- Grid column -->
			<div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">

				<!-- Links -->
				<h6 class="text-uppercase font-weight-bold">Useful links</h6>
				<hr class="teal accent-3 mb-4 mt-0 d-inline-block mx-auto"
					style="width: 60px;">
				<p>
					<a class="dark-grey-text" href="#!">Your Account</a>
				</p>
				<p>
					<a class="dark-grey-text" href="#!">Purchase History</a>
				</p>
				<p>
					<a class="dark-grey-text" href="#!">Shipping Rates</a>
				</p>
				<p>
					<a class="dark-grey-text" href="#!">Help</a>
				</p>

			</div>
			<!-- Grid column -->

			<!-- Grid column -->
			<div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">

				<!-- Links -->
				<h6 class="text-uppercase font-weight-bold">Contact</h6>
				<hr class="teal accent-3 mb-4 mt-0 d-inline-block mx-auto"
					style="width: 60px;">
				<p>
					<i class="fa fa-home"></i> New York, NY 10012, US
				</p>
				<p>
					<i class="fa fa-envelope"></i> info@onlinebookstore.com
				</p>
				<p>
					<i class="fa fa-phone"></i> + 01 234 567 88
				</p>
				<p>
					<i class="fa fa-print"></i> + 01 234 567 89
				</p>

			</div>
			<!-- Grid column -->

		</div>
		<!-- Grid row -->

	</div>
	<!-- Footer Links --> <!-- Copyright -->
	<div class="footer-copyright text-center text-black-50 py-3">
		© 2019 Copyright: <a class="dark-grey-text" href="home">OnlineBookStore</a>
	</div>
	<!-- Copyright --> </footer>
	<!-- Footer -->
	<script>
		function change(id) {			
		  document.getElementById(id).innerHTML = '<button class=\"btn btn-warning\" style=\"margin-left: 10%;\">Update</button>';
		}
	</script>





	<!-- BootStrap Scripts & CarouselJavaScript -->
	<tagfiles:carouselJS />
	<tagfiles:bootstrapScripts />
</body>
</html>