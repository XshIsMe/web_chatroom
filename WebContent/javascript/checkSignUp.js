/**
 * 检查邮箱格式
 * 
 * @returns
 */
function checkEmail() {
	var email = document.getElementById("email").value;
	var str = /^[a-zA-Z0-9_-]{1,18}@[a-zA-Z0-9_-]{1,10}.com$/;
	if (str.test(email)) {
		document.getElementById("emailTip").style.color = "green";
		document.getElementById("emailTip").innerHTML = "√";
		return true;
	} else {
		document.getElementById("emailTip").style.color = "red";
		document.getElementById("emailTip").innerHTML = "*邮箱格式错误";
		return false;
	}
}

/**
 * 检查用户名格式
 * 
 * @returns
 */
function checkUserName() {
	var userName = document.getElementById("userName").value;
	var str = /^[A-Za-z0-9\u4e00-\u9fa5]{1,20}$/;
	if (str.test(userName)) {
		document.getElementById("userNameTip").style.color = "green";
		document.getElementById("userNameTip").innerHTML = "√";
		return true;
	} else {
		document.getElementById("userNameTip").style.color = "red";
		document.getElementById("userNameTip").innerHTML = "*用户名不能包含符号,并且长度不能超过20";
		return false;
	}
}

/**
 * 检查密码格式
 * 
 * @returns
 */
function checkPassword() {
	var password = document.getElementById("password").value;
	var str = /^[A-Za-z0-9]{1,20}$/;
	if (str.test(password)) {
		document.getElementById("passwordTip").style.color = "green";
		document.getElementById("passwordTip").innerHTML = "√";
		return true;
	} else {
		document.getElementById("passwordTip").style.color = "red";
		document.getElementById("passwordTip").innerHTML = "*密码不能包含符号,并且长度不能超过20";
		return false;
	}
}

/**
 * 检查两次输入密码是否相同
 * 
 * @returns
 */
function checkPassword2() {
	var password = document.getElementById("password").value;
	var password2 = document.getElementById("password2").value;
	if (password == password2) {
		document.getElementById("password2Tip").style.color = "green";
		document.getElementById("password2Tip").innerHTML = "√";
		return true;
	} else {
		document.getElementById("password2Tip").style.color = "red";
		document.getElementById("password2Tip").innerHTML = "*两次输入的密码不一致";
		return false;
	}
}

/**
 * 检查身份证格式
 * 
 * @returns
 */
function checkIdCard() {
	var idCard = document.getElementById("idCard").value;
	var str = /^[0-9]{17}[0-9xX]{1}$/;
	if (str.test(idCard)) {
		document.getElementById("idCardTip").style.color = "green";
		document.getElementById("idCardTip").innerHTML = "√";
		return true;
	} else {
		document.getElementById("idCardTip").style.color = "red";
		document.getElementById("idCardTip").innerHTML = "*请输入正确的身份证号码";
		return false;
	}
}

/**
 * 检查姓名格式
 * 
 * @returns
 */
function checkName() {
	var name = document.getElementById("name").value;
	var str = /^(([\u4e00-\u9fa5]{2,5})|([A-Za-z]{1,20}))$/;
	if (str.test(name)) {
		document.getElementById("nameTip").style.color = "green";
		document.getElementById("nameTip").innerHTML = "√";
		return true;
	} else {
		document.getElementById("nameTip").style.color = "red";
		document.getElementById("nameTip").innerHTML = "*请输入正确的姓名";
		return false;
	}
}

var flag = "false";

/**
 * 调用检查方法并返回结果
 * 
 * @returns
 */
function signUp() {
	var email = document.getElementById("email").value;
	var userName = document.getElementById("userName").value;
	var password = document.getElementById("password").value;
	var idCard = document.getElementById("idCard").value;
	var name = document.getElementById("name").value;

	checkSignUp(email, userName, password, idCard, name);
	if (checkIdCode() == true) {
		if (checkEmail() == true) {
			if (checkUserName() == true) {
				if (checkPassword() == true) {
					if (checkPassword2() == true) {
						if (checkIdCard() == true) {
							if (checkName() == true) {
								if (flag == "true") {
									alert("注册成功，请到邮箱激活账号");
									window.location.href = "Login.html";
								} else {
									alert("邮箱或用户名已被注册");
								}
							} else {
								alert("请输入正确的姓名");
							}
						} else {
							alert("请输入正确的身份证号码");
						}
					} else {
						alert("两次输入的密码不一致");
					}
				} else {
					alert("密码不能包含符号,并且长度不能超过20");
				}
			} else {
				alert("用户名不能包含符号,并且长度不能超过20");
			}
		} else {
			alert("邮箱格式错误");
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
function checkSignUp(email, userName, password, idCard, name) {
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
	xmlHttp.open("post", "signUp", false);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.send("email=" + email + "&userName=" + userName + "&password="
			+ password + "&idCard=" + idCard + "&name=" + name);
}