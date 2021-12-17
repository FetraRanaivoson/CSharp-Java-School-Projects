<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">

<head>
	<title>Login page</title>
	<link rel="stylesheet" href="loginStyle.css">
</head>

<body class="body">
	<div class="container">
		<form action="AccountControllerSRV" id="form" class="form"
			method="get">

			<h2>Login page</h2>
			<div class="form-control">
				<label for="email">Email</label> <input type="email" id="email"
					placeholder="Enter email" name="email" required> <label
					for="password">Password</label> <input type="password"
					id="password" placeholder="Enter password" name="password" required>
			</div>
			<div class="form-control">
				<button id="button" type="submit">Login</button>
			</div>
		</form>
	<a href="SignUpPage.jsp">Sign up</a>
	</div>

	

</body>


</html>