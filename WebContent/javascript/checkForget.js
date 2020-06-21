var flag = "false";

/**
 * 调用检查方法并返回结果
 * 
 * @returns
 */
function forget() {
	var email = document.getElementById("email").value;
	var idCard = document.getElementById("idCard").value;
	var password = document.getElementById("password").value;

	checkForget(email, idCard, password);
	if (checkIdCode() == true) {
		if (flag == "true") {
			alert("修改成功");
			window.location.href = "Login.html";
		} else {
			alert("修改失败");
		}
	} else {
		alert("验证码错误");
	}
}

/**
 * 将用户输入传到后台检查
 * 
 * @param email
 * @param password
 * @returns
 */
function checkForget(email, idCard, password) {
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
	xmlHttp.open("post", "forget", false);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.send("email=" + email + "&idCard=" + idCard + "&password="
			+ password);
}