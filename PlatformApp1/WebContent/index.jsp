<%@page import="com.robin.app.util.NetUtil"%>
<html>
	<head>
		<title> Home Page</title>
		<script type="text/javascript">
			var globals = {};
			globals.AUTH_REFRESH_URL= '<%=NetUtil.getAppUrl(request)%>' + '/refresh.do';
			globals.APP_REFRESH_URL = 'http://localhost:8080/AuthPlatform/refresh.do';
		</script>
	</head>
	<body>
		<br /><h3> Welcome ${sessionScope["username"]},</h3> 
		<br/>This is the Home Page for ${pageContext.request.contextPath}
		<br />
		<a href="samplePage.jsp"> Profile Page</a> 
		<br />
		<br />
		<br />
		<script type="text/javascript" src="keepAlive.js"></script>
		<%@include file="logoutFragment.jspf"%>		
	</body>
</html>