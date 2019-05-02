<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="home"
		style="color: aquamarine; padding: 10px;"><b>Online</b>BookStore</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNavDropdown">

		<ul class="navbar-nav w-100">
			<!-- <li class="nav-item active"><a class="nav-link" href="#">
					Today's Deals <span class="sr-only">(current)</span>
			</a></li> -->
			<div class="form-group col-md-8 mx-auto">
				<input class="form-control autocomplete form-control-md"
					placeholder="Search books"
					style="border: 0px solid; border-radius: 20px;" />
			</div>
			<li class="nav-item"><a class="nav-link" href="#"></a></li>
			<li class="nav-item"><a class="nav-link" href="#"></a></li>
			<li class="nav-item dropdown ml-auto"><a
				class="nav-link dropdown-toggle" href="#"
				id="navbarDropdownMenuLink" data-toggle="dropdown"
				aria-haspopup="true" aria-expanded="false"> <span
					style="color: aquamarine;"> Sign in <i class="fa fa-sign-in"
						aria-hidden="true"></i>
				</span>
			</a>
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="login"> <span
						class="text-warning"></span>
						<button type="button"
							class="btn btn-warning
                                    btn-md"
							style="margin-bottom: 20px;">Sign in</button>
					</a> <a class="dropdown-item" href="register">New customer? <span
						style="text-decoration: underline;">Register here</span>
					</a>

				</div></li>

			<li class="nav-item active" style="margin-right: 20px;"><a
				class="nav-link" href="shopping_cart"><b><i
						class="fa
                                    fa-shopping-cart fa-2x"></i>
						Cart (<span class="text-warning"><c:choose>

								<c:when
									test="${shoppingCart == null ||
								shoppingCart.size() == 0}">
								0
								</c:when>
								<c:otherwise>${numOfItems}
								</c:otherwise>
							</c:choose></span>)</b> <span class="sr-only">(current)</span></a></li>

		</ul>
	</div>
</nav>


