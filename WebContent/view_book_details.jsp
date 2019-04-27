<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: ${title}</title>

<tagfiles:bootstrapCSS />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:navbar_style />
</head>
<body>
	<br>

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

	<div class="container" style="margin-top: 10px;">
		<div class="card" style="border: none;">

			<div class="row no-gutters">
				<div class="col-auto">
					<img src="${image}" title="${title}" class="img-fluid" alt=""
						style="height: 210px; width: 120px; margin-left: 20px;">
				</div>
				<div class="col" style="margin-left: 50px;">
					<div class="card-block px-2">
						<!-- <span class="badge badge-danger" style="margin-bottom: 15px;">Out
							of Stock</span> <span class="badge badge-warning"
							style="margin-bottom: 15px;">Low in Stock</span> -->
						<span class="badge badge-success" style="margin-bottom: 15px;">In
							Stock</span>
						<h4 class="card-title" style="font-size: 30px;">${title}</h4>
						<p class="card-text" style="font-size: 20px;">${author}</p>
						<p class="card-text" style="font-size: 20px;">${yearPublished}</p>
						<!-- Price -->
						<h5 style="color: red;">
							$<span class="text-center">${price}</span>
						</h5>
						<p class="card-text" title="${averageRatings}">
							<match:ListStarRatings rating="${averageRatings}"></match:ListStarRatings>
							<span>${numOfRatings}</span>
						</p>
						<form action="cart.do?id=${bookId}" method="post">
							<button class="btn btn-primary" style="">Add to Cart</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container mt-3 primary">
		<h4 style="margin-top: 200px;">Customers also shopped for</h4>
		<br>
	</div>

	<!-- Send A query via a query tag to get items that have similar authors & popular by that year -->
	<!--  -->

	<div class="container mt-3">
		<div class="row">
			<div class="owl-carousel owl-theme">
				<match:ListPopularBooks>
					<div class="item">
						<div class="card" style="border: none;">

							<!-- Book Image -->
							<img src="${image}" alt="img"
								style="height: 210px; width: 120px; margin-left: 20px;">

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">
									<a href="book?id=${bookId}">${title}</a>
								</h6>

								<!-- Author -->
								<p
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">${author}</p>

								<!-- Price -->
								<h5 style="color: red;">
									$<span class="text-center">${price}</span>
								</h5>

								<!-- Ratings -->
								<match:ListStarRatings rating="${averageRating}">
								</match:ListStarRatings>
								<span>${numOfRatings}</span>

							</div>
						</div>
					</div>
				</match:ListPopularBooks>

			</div>
		</div>
	</div>

	<tagfiles:footer />

	<!-- BootStrap Scripts & CarouselJavaScript -->
	<tagfiles:carouselJS />
	<tagfiles:bootstrapScripts />
</body>
</html>

