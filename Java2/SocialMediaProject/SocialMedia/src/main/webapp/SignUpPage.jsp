
<html lang="en">

<head>
	<title>Sign up page</title>
	<link rel="stylesheet" href="loginStyle.css">
</head>

<body class="body">
	<div class="container">

		<form class="form" action="AccountControllerSRV" method="get">

			<h2>Sign up</h2>
			<div class="form-control">
				<label for="FirstName">First Name</label> 
				<input type="text" id="firstName" placeholder="Enter first name" name="firstName" required> 
				
				<label for="LastName">Last Name</label> 
				<input type="text" id="lastName" placeholder="Enter last name" name="lastName" required> 
				
				<label for="email">Email</label> 
				<input type="email" id="email" placeholder="Enter email" name="email" required> 
				
				<label for="password">Password</label> 
				<input type="password" id="password" placeholder="Enter password" name="password" required>
				
				<input type="hidden" name="command" value="SignUp"/>
			</div>
			<div class="form-control">
				<button id="button" type="submit">Sign Up</button>
			</div>

		</form>
		<a href="loginPage.jsp">Login</a>
	</div>

	
	
</body>


</html>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              