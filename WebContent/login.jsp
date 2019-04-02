<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <!-- Bootstrap CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
      <title>LOGIN</title>
      <style type="text/css">
         body {
         background-image: url("images/books.jpg");
         background-attachment: fixed;
         }
      </style>
   </head>
   <body>
      <div class="row">
         <div class="container">
            <div class="form" class="col-sm-10" style="width: 600px; margin-left: 250px; margin-top: 50px;">
               <div class="jumbotron">
                  <div class="form-group" style="text-align: center;">
                     <h1>ACCOUNT LOGIN</h1>
                  </div>
                  <form class="form-horizontal" style="margin-left: 50px;" method="post" action="login.do">
                     <div class="form-group input-group">
                        <input type="email" required autocomplete="on" placeholder="Email" class="form-control" name="email"/>
                     </div>
                     <div class="form-group input-group">
                        <input type="password"required autocomplete="off" placeholder="Password" class="form-control" name="password"/>
                     </div>
                     <!-- Validate User Account -->
                     <div>
                        <!-- <p align=center style="color: red; font-weight: bold;">There was a Problem. Please Try Again</p> -->
                        <%                 
                           String invalid = (String) request.getAttribute("invalid");
                           if (invalid == null) invalid = "";
                                               
                           %>
                        <p align=center style="color: red; font-weight: bold;"><%= invalid %></p>
                     </div>
                     <div class="form-group">
                        <button class="btn btn-primary">SIGN IN</button>
                     </div>
                     <p><a href="/practice-web-app/register">New user</a></p>
                  </form>
               </div>
            </div>
         </div>
      </div>
      <!-- Optional JavaScript -->
      <!-- jQuery first, then Popper.js, then Bootstrap JS -->
      <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
   </body>
</html>