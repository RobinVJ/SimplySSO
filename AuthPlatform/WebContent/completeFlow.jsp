<%@page import="com.robin.sso.api.support.Constants"%>
<!DOCTYPE html>
<html>
	<head>
		<meta HTTP-EQUIV="REFRESH"
			content='3; url=${requestScope["appStartupURL"]}' />
	</head>

	<body>
		<div id="pleasewait" style="display: block; text-align: center">
			<table id="mytable">
				<tr>
					<td>
						<h2>Loading, Please wait..</h2>
					</td>
				</tr>
			</table>
		</div>
	</body>


</html>
