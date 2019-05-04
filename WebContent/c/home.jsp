<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.*"%>
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
		User user = (User) session.getAttribute(WEB.USER);
		if (user != null && user.getUserRole().equals(WEB.ADMINISTRATOR)) {
			response.sendRedirect(WEB.ADMIN_SIGN_OUT);
		}
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
	<!-- Display Books -->
	<div class="container mt-3 primary">
		<h4>Most Popular Books</h4>
		<br>
	</div>

	<div class="container mt-3">
		<div class="row">
			<div class="owl-carousel owl-theme">
				<match:ListPopularBooks>
					<div class="item">
						<div class="card" style="border: none;">

							<!-- Book Image -->
							<a href="book?id=${bookId}&title=${title}"><img
								src="${image}" alt="img" title="${title}"
								style="height: 210px; width: 120px; margin-left: 20px;"></a>

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">
									<a href="book?id=${bookId}&title=${title}">${title}</a>
								</h6>

								<!-- Author -->
								<p
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">by
									${author}</p>

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

	<br>

	<div class="container mt-3 primary">
		<h4>Top Rated Books</h4>
		<br>
	</div>

	<div class="container mt-3">
		<div class="row">
			<div class="owl-carousel owl-theme">
				<match:ListTopRatedBooks>
					<div class="item">
						<div class="card" style="border: none;">

							<!-- Book Image -->
							<a href="book?id=${bookId}&title=${title}"><img
								src="${image}" alt="img" title="${title}"
								style="height: 210px; width: 120px; margin-left: 20px;"></a>

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">
									<a href="book?id=${bookId}&title=${title}">${title}</a>
								</h6>

								<!-- Author -->
								<p
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">by
									${author}</p>

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
				</match:ListTopRatedBooks>
			</div>
		</div>
	</div>
	<br>

	<div class="container mt-3 primary">
		<h4>Top Rated Books By Year</h4>
		<br>
	</div>
	<br>

	<div class="container mt-3">
		<div class="row">
			<div class="owl-carousel owl-theme">
				<match:ListTopRatedBooksByYear>
					<div class="item">
						<div class="card" style="border: none;">

							<!-- Book Image -->
							<a href="book?id=${bookId}&title=${title}"><img
								src="${image}" alt="img" title="${title}"
								style="height: 210px; width: 120px; margin-left: 20px;"></a>

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">
									<a href="book?id=${bookId}&title=${title}">${title}</a>
								</h6>

								<!-- Author -->
								<p
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">by
									${author}</p>

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
				</match:ListTopRatedBooksByYear>
			</div>
		</div>
	</div>
	<br>

	<div class="container mt-3 primary">
		<h4>Ancient Literature</h4>
		<br>
	</div>
	<br>

	<div class="container mt-3">
		<div class="row">
			<div class="owl-carousel owl-theme">
				<match:ListAncientLiteratureBooks>
					<div class="item">
						<div class="card" style="border: none;">

							<!-- Book Image -->
							<a href="book?id=${bookId}&title=${title}"><img
								src="${image}" alt="img" title="${title}"
								style="height: 210px; width: 120px; margin-left: 20px;"></a>

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">
									<a href="book?id=${bookId}&title=${title}">${title}</a>
								</h6>

								<!-- Author -->
								<p
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">by
									${author}</p>

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
				</match:ListAncientLiteratureBooks>
			</div>
		</div>
	</div>
	<br>
	<br>


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