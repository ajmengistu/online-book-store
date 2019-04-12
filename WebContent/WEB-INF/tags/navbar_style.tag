<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<style>
* {
	box-sizing: border-box
}

body {
	font-family: Arial, Helvetica, sans-serif;
}

.navigbar {
	width: 100%;
	background-color: #555;
	overflow: auto;
}

.navigbar a {
	float: left;
	padding: 12px;
	color: white;
	text-decoration: none;
	font-size: 17px;
	width: 20%; /* Four links of equal widths */
	text-align: center;
}

.navigbar a:hover {
	background-color: #000;
}

.navigbar a.active {
	background-color: #4CAF50;
}

@media screen and (max-width: 500px) {
	.navigbar a {
		float: none;
		display: block;
		width: 100%;
		text-align: left;
	}
}
</style>