var xmlhttp;
var executeAjaxGet = function(url, cfunc) {
//	  alert('I am executeAjaxGet!');
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = cfunc;
	xmlhttp.open("GET", url, true);
	xmlhttp.send();
};

var keepAlive = function() {
	executeAjaxGet(globals.AUTH_REFRESH_URL, function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			// call completed successfully
			//no action
		} else if (xmlhttp.readyState == 4 && xmlhttp.status == 401) {
			window.location.reload();// the user is not authorized to be
										// here- restart the login process
		}
	});
	executeAjaxGet(globals.APP_REFRESH_URL, function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			// call completed successfully
			//no action
		} 
	});
};

setInterval(function() {
	keepAlive();
}, 1000 * 60 * 1); // every 2min
