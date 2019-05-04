<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="/online-book-store/c/home"
		style="color: aquamarine; padding: 10px;"><b>Online</b>BookStore</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNavDropdown">
		<ul class="navbar-nav w-100">
			<div class="form-group col-md-8 mx-auto">
				<input class="form-control autocomplete form-control-md"
					placeholder="Search books"
					style="border: 0px solid; border-radius: 20px;" />
			</div>

			<li class="nav-item dropdown ml-auto" style="margin-right: 15px;"><a
				class="nav-link dropdown-toggle" href="#"
				id="navbarDropdownMenuLink" data-toggle="dropdown"
				aria-haspopup="true" aria-expanded="false"> <span
					style="color: aquamarine;">Welcome, ${user.getLastName()}
						${user.getFirstName()}! </span>
			</a>
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="/online-book-store/c/account"><i
						class="fa
                                        fa-user"
						aria-hidden="true"></i>&nbsp&nbsp<span style="font-weight: bold;">Your
							Account</span> </a> <a class="dropdown-item" href="order-history"><i
						class="fa
                                        fa-list"
						aria-hidden="true"></i>&nbsp&nbsp<span>Your
							Orders</span></a><a class="dropdown-item" href="#"><i
						class="fa
                                        fa-book"
						aria-hidden="true"></i>&nbsp&nbsp<span>Your
							Recommendations</span></a> <br> <a class="dropdown-item" href="/online-book-store/c/logout.do">
						<button type="button"
							class="btn btn-warning
                                        btn-md">
							<span>Sign out</span>
						</button>
					</a>
				</div></li>

			<li class="nav-item active" style="margin-right: 20px;"><a
				class="nav-link" href="/online-book-store/c/cart.do"><b><i
						class="fa
                                    fa-shopping-cart fa-2x"></i>
						Cart (<span class="text-warning">${numOfItems}</span>)</b> <span
					class="sr-only">(current)</span></a></li>

		</ul>
	</div>
</nav>



