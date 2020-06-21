var websocket = null;
var userName = null;

/**
 * 获取用户名
 * 
 * @returns
 */
function getUserName() {
	var xmlHttp;

	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			userName = xmlHttp.responseText;
		}
	}

	xmlHttp.open("get", "getSession?action=getUserName", false);
	xmlHttp.send();
}

/**
 * 判断当前浏览器是否支持WebSocket
 */
if ("WebSocket" in window) {
	websocket = new WebSocket("ws://localhost:8888");
} else {
	alert("当前浏览器不支持WebSocket");
}

/**
 * 连接发生错误的回调方法
 */
websocket.onerror = function() {
	setMessageInnerHTML("WebSocket连接发生错误", "message");
}

/**
 * 连接成功建立的回调方法
 */
websocket.onopen = function() {
	setMessage("[系统]网络一线牵，珍惜这段缘~");
	getUserName();
	websocket.send("online&" + userName);
}

/**
 * 接收到消息的回调方法
 */
websocket.onmessage = function(event) {
	setMessage(event.data);
}

/**
 * 连接关闭的回调方法
 */
websocket.onclose = function() {
	setMessage("WebSocket连接关闭");
	websocket.send("offline&" + userName);
}

/**
 * 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
 */
window.onbeforeunload = function() {
	websocket.close();
}

/**
 * 将消息显示在网页上
 * 
 * @param message
 * @returns
 */
function setMessage(message) {
	// 获取消息框id
	var messageBox = document.getElementById("messageBox");
	var time = new Date().toLocaleString();
	// 向消息框添加信息
	messageBox.value += time + "\n" + message + "\n\n";
	// 将滚动条移动到最底部
	messageBox.scrollTop = messageBox.scrollHeight;
}

/**
 * 发送消息
 * 
 * @returns
 */
function send() {
	var message = document.getElementById("text").value;
	var chatObject = document.getElementById("friendList").value
	var action = null;
	// 获取单选按钮内容
	var radio = document.getElementsByName("chatObject");
	for (i = 0; i < radio.length; i++) {
		if (radio[i].checked) {
			action = radio[i].value;
		}
	}
	if ("" != message) {
		websocket.send(action + "&" + message + "@" + chatObject);
		setMessage(message);
	} else {
		alert("消息不能为空");
		return;
	}
	document.getElementById("text").value = "";
}