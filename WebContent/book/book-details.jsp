<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.Book, java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: ${title}</title>

<tagfiles:jQueryScripts />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:bootstrapCSS />
</head>
<body>


	<%
		ArrayList<Book> randBooks = Book.getRandomBooks();
			session.setAttribute("randBooks", randBooks);
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

	<div class="container" style="margin-top: 10px;">
		<div class="card" style="border: none;">

			<div class="row no-gutters">
				<div class="col-auto">
					<img src="${image}" title="${title}" class="img-fluid" alt=""
						style="height: 210px; width: 120px; margin-left: 20px;">
				</div>
				<div class="col" style="margin-left: 50px;">
					<div class="card-block px-2">
						<span class="badge badge-success" style="margin-bottom: 15px;">In
							Stock</span>
						<h4 class="card-title" style="font-size: 30px;">${title}</h4>
						<p class="card-text" style="font-size: 20px;">by ${author}</p>
						<p class="card-text" style="font-size: 20px;">${yearPublished}</p>
						<!-- Price -->
						<h5 style="color: red;">
							$<span class="text-center">${price}</span>
						</h5>
						<p class="card-text" title="${averageRatings}">
							<match:ListStarRatings rating="${averageRatings}"></match:ListStarRatings>
							<span>${numOfRatings}</span>
						</p>
						<form action='<%=WEB.CART_DO%>?id=${bookId}' method="post">
							<button class="btn btn-primary" style="">Add to Cart</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container mt-3 primary">
		<h4 style="margin-top: 200px;">Customers also shopped for</h4>
		<hr>
		<br>
	</div>


	<div class="container mt-3">	
		<div class="row">
			<div class="owl-carousel owl-theme">
				<c:forEach items="${randBooks}" var="book">
					<div class="item">
						<div class="card" style="border: none;">

							<!-- Book Image -->
							<a
								href="/online-book-store/book/bk?id=${book.getBookId()}&title=${book.getTitle()}"><img
								src="${book.getImage()}" alt="img" title="${book.getTitle()}"
								style="height: 210px; width: 120px; margin-left: 20px;"></a>

							<!-- Book Title -->
							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">
									<a
										href="/online-book-store/book/bk?id=${book.getBookId()}&title=${book.getTitle()}">${book.getTitle()}</a>
								</h6>

								<!-- Author -->
								<p
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">by
									${book.getAuthor().getName()}</p>

								<!-- Price -->
								<h5 style="color: red;">
									$<span class="text-center">${book.getPrice()}</span>
								</h5>

								<!-- Ratings -->
								<!-- Ratings -->
								<match:ListStarRatings rating="${book.getAverageRatings()}">
								</match:ListStarRatings>
								<span>${book.getNumberOfRatings()}</span>

							</div>
						</div>
					</div>
				</c:forEach>

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

