<!DOCTYPE html>
<html>
	<head>
		<title>Login to Auth Platform</title>
	</head>
	<body>
		<h1>Please Login to Proceed !</h1>
		<br/><br/>
		<form method="post" action="/AuthPlatform/login.do">
			<fieldset>
				<br/>
				<label for="User"> Enter Username: </label>
				<input type="text" name="username" id="User"/>
				<br/>
				<label for="login-password">
					Enter Password:
				</label>
				<input type="password" name="password" id="login-password" />
				<br/>
				<input type="hidden" name="REQUESTED-URL" value='${requestScope["REQUESTED_URL"]}'/>
				<input type="hidden" name="LOGIN-URL" value='${requestScope["LOGIN_URL"]}' />				
				<input type="submit" value="login" />
			</fieldset>
		</form>
	</body>
</html>