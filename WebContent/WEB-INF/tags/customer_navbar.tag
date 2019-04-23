<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<div class="navigbar">
		<a class="active" href="#"><i class="fa fa-fw fa-home"></i>
			Account <c:if test="${user != null}">
				[ Hello, ${user.getFirstName()} ${user.getLastName()} ]
			</c:if> </a> <a href="#"><i class="fa fa-fw fa-book"></i> Recommendations</a> <a
			href="#"><i class="fa fa-fw fa-list"></i> Purchase History</a> <a
			href="cart.do"><i class="fa fa-fw fa-shopping-cart"></i>
			Cart</a> <a href="logout.do"><i class="fa fa-fw fa-user"></i> Logout
		</a>
	</div>
</div>