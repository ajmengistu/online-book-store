<%@ taglib prefix="tagfiles" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="match" uri="match-functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.onlinebookstore.controller.WEB"%>
<%@ page import="com.onlinebookstore.model.User"%>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OnlineBookStore: Your Admin Account</title>

<tagfiles:bootstrapCSS />
</head>
<body>


	<%
		response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expire", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect(WEB.LOGIN);
			// If a Customer requested this page 404 or redirect them to their home 
		} else if (user.getUserRole().equals(WEB.CUSTOMER)) {
			request.getRequestDispatcher(WEB.ERROR_404).forward(request,
					response);
		}
	%>
	<tagfiles:admin_navbar />

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


	<!-- Footer -->
	<tagfiles:footer />
	<!-- BootStrap Scripts -->
	<tagfiles:bootstrapScripts />
</body>
</html>

