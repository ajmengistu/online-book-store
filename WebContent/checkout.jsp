<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.time.LocalDate, java.time.format.DateTimeFormatter;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Add meta tags for mobile and IE -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<title>OnlineBookStore: Place Your Order</title>

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


	<div class="container">
		<nav class="navbar navbar-expand-lg navbar-light bg-dark"> <span
			class="navbar-brand" style="color: aquamarine;"><a
			href="home" style="color: inherit; text-decoration: none;"><b>Online</b>BookStore</a></span>
		</nav>
	</div>
	<br>
	<div class="container">
		<h3>Review your order</h3>
	</div>
	<br>
	<div class="modal fade" id="modalRegisterForm" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header text-center">
					<h4 class="modal-title w-100 font-weight-bold">Shipping
						address</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body mx-3">
					<div class="md-form mb-5">
						<form action="addAddress.do" method="post">
							<!-- <label for="inputEmail4"><strong>Full Name:</strong></label> <input
								type="text" class="form-control" id="inputName"
								placeholder="Name"> <br>  -->
							<label for="inputAddress"><strong>Address 1:</strong></label> <input
								required type="text" class="form-control" name="inputAddress"
								placeholder="1234 Main St"> <br> <label
								for="inputAddress2"><strong>Address 2:</strong></label> <input
								required type="text" class="form-control" name="inputAddress2"
								placeholder="Apartment, studio, or floor"> <br> <label
								for="inputCity"><strong>City:</strong></label> <input required
								type="text" class="form-control" name="inputCity"> <br>
							<label for="inputState"><strong>State:</strong></label> <input
								required type="text" class="form-control" name="inputState">
							<br> <label for="inputZip"><strong>ZIP:</strong></label> <input
								required type="number" class="form-control" name="inputZip">

							<div class="modal-footer d-flex justify-content-center"
								style="margin-top: 50px;">
								<button class="btn btn-warning">Add address</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<h6>Shipping Address</h6>
				<br> <span> <c:if test="${! empty userAddress}">
				${user.getFirstName()} ${user.getLastName()}
				</c:if>
				</span>
				<p>${userAddress}</p>
				<a href="" data-toggle="modal" data-target="#modalRegisterForm">${addressStatus}</a>
			</div>
			<div class="col-md-5">
				<h6>Payment Method</h6>
				<br>
				<!-- Set up a container element for the button -->
				<div class="container">
					<form id="placeOrder" method="post"
						action="/online-book-store/place-order">
						<div id="payment-form"></div>
					</form>
				</div>
				<br>

			</div>

			<div class="col-md-4">
				<div class="card card-body bg-light">
					<h6 style="font-weight: bold;">Order Summary</h6>
					<table class="table">
						<tbody>
							<tr>
								<td>Items (${numOfItems}):</td>
								<td style="">$${subTotal}</td>

							</tr>
							<tr>
								<td>Shipping & handling:</td>
								<td style="">$${shippingCost}</td>

							</tr>
							<tr style="background-color: #ccffb3; margin-top: 10%;">
								<td
									style="color: rgb(236, 74, 74); font-weight: bold; font-size: 20px;">Order
									Total</td>
								<td
									style="color: rgb(236, 74, 74); font-weight: bold; font-size: 20px;">$${totalCost}</td>
							</tr>
						</tbody>
					</table>
					<input class="btn btn-warning" type="submit" value="Place order"
						form="placeOrder"> <br>
				</div>
			</div>
		</div>

		<hr>
	</div>

	<div class="container">
		<h5 style="color: orange;">
			Estimated delivery:
			<%=LocalDate.now().plusDays(5)
					.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"))%></h5>
	</div>

	<br>
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
							<c:forEach items="${shoppingCart}" var="item">
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
								</tr>
								<c:set var="count" value="${count + 1}" scope="page" />
							</c:forEach>


						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.js"></script>
	<script
		src="https://js.braintreegateway.com/js/braintree-2.32.1.min.js"></script>
	<script>
		$.ajax({
			url : 'token',
			type : 'get',
			dataType : 'json'
		}).success(function(data) {
			braintree.setup(data, "dropin", {
				container : "payment-form"
			});
		});
	</script>

	<tagfiles:footer />
	<tagfiles:bootstrapScripts />

</body>
</html>