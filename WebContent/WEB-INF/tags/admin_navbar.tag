<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<div>	
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <button class="navbar-toggler" type="button" data-toggle="collapse"
        data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03"
        aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <a class="navbar-brand" href="admin-home" style="color:aquamarine;">Online<b>BookStore</b></a>
      <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
          <li class="nav-item active">
            <a class="nav-link" href="admin-home">Home <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item active">
            <a class="nav-link" href="admin-sales">Sales/Orders <span class="sr-only">(current)</span></a>
          </li> <li class="nav-item active">
            <a class="nav-link" href="admin-payments">Payments <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item active">
            <a class="nav-link" href="view-users">Customers/Users <span class="sr-only">(current)</span></a>
          </li> <li class="nav-item active">
            <a class="nav-link" href="admin-stock">Stock <span class="sr-only">(current)</span></a>
          </li>
          </li> <li class="nav-item active">
            <a class="nav-link" href="add-books">Add Books <span class="sr-only">(current)</span></a>
          </li>
        </ul>
        <!-- <form class="form-inline my-2 my-lg-0">
          <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form> -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
            role="button" data-toggle="dropdown" aria-haspopup="true"
            aria-expanded="false">
            <span style="color:aquamarine;">Welcome, ${user.getFirstName()} ${user.getLastName()}!</span>
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdown">
            <a class="dropdown-item" href="admin-account">Your Account</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="logout.do"><button class="btn btn-warning">Sign
                out</button></a>
          </div>
        </li>
        <div class=""><span class="text-right" style="color: rgb(70, 241, 70)">Administrator Account</span></div>
      </div>
    </nav>
</div>

