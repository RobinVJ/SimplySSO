var executeLogout = function(appLogoutUrl) {
  executeAjaxGet("http://localhost:8080/AuthPlatform/logout.do", function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			// call completed successfully
			//no action
			window.location = appLogoutUrl;
		} 
	});
  
  return true;
};

