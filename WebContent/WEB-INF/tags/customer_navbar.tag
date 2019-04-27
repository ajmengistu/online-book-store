<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="home"
		style="color: aquamarine; padding: 10px;"><b>Online</b>BookStore</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNavDropdown">
		<ul class="navbar-nav w-100">
			<li class="nav-item active"><a class="nav-link" href="#">Popular
					Books <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link" href="#">
					Top Rated Books <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link" href="#">
					New Books <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link" href="#">
					Today's Deals <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="#"></a></li>
			<li class="nav-item dropdown ml-auto"><a
				class="nav-link dropdown-toggle" href="#"
				id="navbarDropdownMenuLink" data-toggle="dropdown"
				aria-haspopup="true" aria-expanded="false"> <span
					style="color: white;">Welcome, ${user.getLastName()}
						${user.getFirstName()}! </span>
			</a>
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="navbarDropdownMenuLink">
					<a class="dropdown-item" href="#"><i
						class="fa
                                        fa-user"
						aria-hidden="true"></i> <span class="text-success">Your
							Account</span> </a> <a class="dropdown-item" href="#"><i
						class="fa
                                        fa-envelope"
						aria-hidden="true"></i> <span class="text-success">${user.getEmail()}</span></a>
					<a class="dropdown-item" href="#"><i
						class="fa
                                        fa-list"
						aria-hidden="true"></i> Your Orders</a> <a class="dropdown-item"
						href="#"><i
						class="fa
                                        fa-book"
						aria-hidden="true"></i> Your Recommendations</a> <br> <a
						class="dropdown-item" href="logout.do">
						<button type="button"
							class="btn btn-warning
                                        btn-md">
							<span>Sign out</span>
						</button>
					</a>
				</div></li>
			<li class="nav-item active" style="margin-right: 20px;"><a
				class="nav-link" href="cart.do"><b><i
						class="fa
                                    fa-shopping-cart fa-2x"></i>
						Cart (<span class="text-warning">${numOfItems}</span>)</b> <span class="sr-only">(current)</span></a></li>

		</ul>
	</div>
</nav>



