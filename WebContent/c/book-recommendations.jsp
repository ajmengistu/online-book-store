<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.*, java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Book Recommendations</title>

<tagfiles:jQueryScripts />
<tagfiles:awesomefonts />
<tagfiles:carouselCSS />
<tagfiles:bootstrapCSS />
</head>
<body>


	<%
		User user = (User) session.getAttribute(WEB.USER);
			if(user == null){
		response.sendRedirect(WEB.LOGIN);
			}else{
		ArrayList<Book> bookList = User.getBookRecommendations(user.getUserId());
		session.setAttribute("bookList", bookList);
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
	<br>
	<div class="container">
		<h3>Your Book Recommendations</h3>
	</div>
	<br>


	<c:if test="${empty bookList}">
		<div class="container">
			<h5>You do not have any book recommendations right now. Continue shopping at <a href=<%=WEB.HOME%> >Online<b>BookStore</b></a> home page!</h5>
		</div>
	</c:if>


	<c:set var="count" value="0" scope="page" />
	<div class="container">
		<c:forEach items="${bookList}" var="book">
			<c:if test="${count%3==0}">
				<div class="row">
			</c:if>


			<div class="item">
				<div class="card" style="width: 18rem; border: none;">
					<!-- Book Image -->
					<a
						href="<%=WEB.HOST%>/book/bk?id=${book.getBookId()}&title=${book.getTitle()}"><img
						src="${book.getImage()}" alt="img" title="${book.getTitle()}"
						style="height: 210px; width: 120px; margin-left: 20px;"></a>
					<div class="card-body">
						<!-- Title -->
						<h6>
							<a
								href="<%=WEB.HOST%>/book/bk?id=${book.getBookId()}&title=${book.getTitle()}">${book.getTitle()}</a>
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


			<c:if test="${(count+1)%3==0}">
	</div>
	<br>
	</c:if>
	<c:set var="count" value="${count + 1}" scope="page" />
	</c:forEach>
	<c:if test="${count+1%3!=0}">
	</div>
	</c:if>
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

