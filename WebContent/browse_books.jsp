<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Browse Books</title>

<tagfiles:bootstrapCSS />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:navbar_style />

<style>
.checked {
	color: orange;
}
</style>

</head>
<body>
	<br>
	<tagfiles:title_header />
	<tagfiles:searchbar />
	<br>

	<!-- Customer Navigation Bar -->
	<tagfiles:customer_navbar />
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
							<img src="${image}" alt="img"
								style="height: 210px; width: 120px; margin-left: 20px;">

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">${title}</h6>

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
							<img src="${image}" alt="img"
								style="height: 210px; width: 120px; margin-left: 20px;">

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">${title}</h6>

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
							<img src="${image}" alt="img"
								style="height: 210px; width: 120px; margin-left: 20px;">

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">${title}</h6>

								<!-- Author -->
								<p
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">${author}</p>

								<!-- Year Published -->
								<p>${yearPublished}</p>

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
							<img src="${image}" alt="img"
								style="height: 210px; width: 120px; margin-left: 20px;">

							<div class="card-body">
								<!-- Title -->
								<h6 class="card-text"
									style="overflow: hidden; border: none; text-overflow: ellipsis; display: -webkit-box; line-height: 30px; /* fallback */ max-height: 65px; /* fallback */ -webkit-line-clamp: 2; /* number of lines to
                                    show */ -webkit-box-orient: vertical;">${title}</h6>

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
				</match:ListAncientLiteratureBooks>
			</div>
		</div>
	</div>
	<br>
	<br>
	<!-- Footer -->
	<footer class="container-fluid text-center"
		style="background-color: grey;
      padding: 40px;">
	<p style="font-weight: bold;">OnlineBookStore Copyright (c) 2019</p>
	</footer>

	<!-- BootStrap Scripts & CarouselJavaScript -->
	<tagfiles:carouselJS />
	<tagfiles:bootstrapScripts />
</body>
</html>