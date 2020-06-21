var idCode;

/**
 * 点击图片切换验证码
 * 
 * @param img
 * @returns
 */
function changeImg(img) {
	img.src = img.src + "?time=" + new Date().getTime();
}

/**
 * 检查验证码
 * 
 * @returns
 */
function checkIdCode() {
	// 获取session中的验证码
	getIdCode();
	// 获得用户输入的验证码
	var input = document.getElementById("idCode").value;
	if (input == idCode) {
		document.getElementById("idCodeTip").style.color = "green";
		document.getElementById("idCodeTip").innerHTML = "√";
		return true;
	} else {
		document.getElementById("idCodeTip").style.color = "red";
		document.getElementById("idCodeTip").innerHTML = "*验证码错误";
		return false;
	}
}

/**
 * 获取session中的验证码
 * 
 * @returns
 */
function getIdCode() {
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			idCode = xmlHttp.responseText;
		}
	}
	xmlHttp.open("get", "getSession?action=getIdCode", true);
	xmlHttp.send();
}