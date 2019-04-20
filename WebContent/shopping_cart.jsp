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
		<div class="container">
			<div class="row">
				<div class="col-md-8">
					<div class="card card-body bg-light">
						<table class="table" cellspacing="10">
							<thead>
								<tr>
									<th></th>
									<th>Product</th>
									<th class="">Price</th>
									<th>Quantity</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${shoppingCart}" var="book">
									<tr>
										<td><img src="${book.getImage()}" alt="img"
											style="height: 150px; width: 90px; margin-left: 20px;"></td>
										<td><a href="book?id=${book.getBookId()}"
											style="font-weight: bold;">${book.getTitle()}</a><span>
												by ${book.getAuthor().getName()}</span></td>
										<td style="color: green;">${book.getPrice()}</td>
										<!-- <td>-</td> -->
										<td>
											<form action="" class="form-inline" method="">
												<select name="" class="form-control" type="" id="quantity">
													<option value="">1</option>
													<option value="">0</option>
													<option value="">2</option>
													<option value="">3</option>
													<option value="">4</option>
													<option value="">5</option>
													<option value="">6</option>
													<option value="">7</option>
													<option value="">8</option>
													<option value="">9</option>
													<!-- <option value="">10+</option> -->
												</select>

												<button class="btn ml-3"
													style="background-color: rgb(255, 255, 255)"
													onclick="fun()">Update</button>
											</form>
										</td>
									</tr>

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
									<td>$20.50</td>
								</tr>
								<tr>
									<td>Shipping</td>
									<td>$32.20</td>
								</tr>
								<tr style="background-color: #ccffb3">
									<td>Total</td>
									<td>$52.70</td>
								</tr>
							</tbody>
						</table>
						<br> <a href="checkout.do" class="btn btn-primary">Checkout</a>
					</div>
				</div>
			</div>
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




	<script>
		function fun() {
			alert('selectedValue');
		}
	</script>

	<!-- Footer -->
	<footer class="container-fluid text-center"
		style="background-color: grey;
      padding: 40px; position: relative;
  margin-top: 453px;">
	<p style="font-weight: bold;">OnlineBookStore Copyright (c) 2019</p>
	</footer>


	<!-- BootStrap Scripts & CarouselJavaScript -->
	<tagfiles:carouselJS />
	<tagfiles:bootstrapScripts />
</body>
</html>