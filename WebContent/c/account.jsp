<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ page import="com.onlinebookstore.controller.WEB, com.onlinebookstore.model.User"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.time.LocalDate, java.time.format.DateTimeFormatter;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Add meta tags for mobile and IE -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<title>OnlineBookStore: Update Account</title>

<tagfiles:bootstrapCSS />
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		User user = (User) session.getAttribute(WEB.USER);
		if (session.getAttribute(WEB.USER) == null)
			response.sendRedirect(WEB.LOGIN);
	%>



	<tagfiles:404-erorr-navbar />


	<br>
	<div style="text-align: center">
		<h3>Login &#38; Security</h3>
	</div>
	<br>
	<div class="row justify-content-center align-items-center">
		<div class="col-md-5">
			<div class="card card-body bg-white">
				<table class="table borderless">
					<tbody>
						<tr>
							<td style="font-weight: bold;">Name:</td>
							<td style="">${user.getFirstName()}&nbsp;
								${user.getLastName()}</td>
							<td></td>
						</tr>
						<tr>
							<td style="font-weight: bold;">Email:</td>
							<td style="">${user.getEmail()}</td>
							<td></td>

						</tr>
						<tr>
							<td style="font-weight: bold;">Password:</td>
							<td style="">*******************</td>
							<td><button type="button" class="btn btn-secondary"
									data-toggle="modal" data-target="#modalRegisterForm">Edit</button>
							</td>

						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<c:if test="${user.getUserRole() == 'CUSTOMER'}">
			<div class="container" style="margin-left: 14.3%; margin-top: 1%;">
				<a href=<%=WEB.HOME%> class="btn btn-warning"><i
					class="fa
                    fa-angle-left"></i> Continue
					Shopping</a>
			</div>
		</c:if>
	</div>



	<div class="modal fade" id="modalRegisterForm" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header text-center">
					<h4 class="modal-title w-100 font-weight-bold">Edit Password</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body mx-3">
					<div class="md-form mb-5">
						<form action=<%=WEB.CHANGE_PASSWORD_DO%> method="post">
							<label for="inputAddress"><strong></strong></label> <input
								required type="password" class="form-control"
								name="current-password" placeholder="Current password">
							<br> <label for=""><strong></strong></label> <input required
								type="password" class="form-control" name="new-password"
								placeholder="New password"> <br> <label
								for="inputCity"><strong></strong></label> <input required
								type="password" class="form-control" name="confirm-password"
								placeholder="Confirm password"> <br>

							<div class="modal-footer d-flex justify-content-center"
								style="margin-top: 50px;">
								<button class="btn btn-warning">Update password</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.js"></script>
	<script
		src="https://js.braintreegateway.com/js/braintree-2.32.1.min.js"></script>
	<tagfiles:footer />
	<tagfiles:bootstrapScripts />

</body>
</html>