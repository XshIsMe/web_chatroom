var flag = "false";

/**
 * 调用检查方法并返回结果
 * 
 * @returns
 */
function login() {
	var email = document.getElementById("email").value;
	var password = document.getElementById("password").value;

	checkLogin(email, password);
	if (checkIdCode() == true) {
		if (flag == "true") {
			window.location.href = "WebChat.html";
		} else {
			alert("账号或密码错误");
		}
	} else {
		alert("验证码错误");
	}
}

/**
 * 将用户输入的账号密码传到后台检查
 * 
 * @param email
 * @param password
 * @returns
 */
function checkLogin(email, password) {
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			flag = xmlHttp.responseText;
		}
	}
	xmlHttp.open("post", "login", false);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.send("email=" + email + "&password=" + password);
}