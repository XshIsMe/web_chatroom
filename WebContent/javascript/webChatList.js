var userName = null;

window.onload = function() {
	getUserName();
}

/**
 * 保存文件
 * 
 * @returns
 */
function save() {
	var message = document.getElementById("messageBox").value;
	message = message.replace(/\n|\r\n/g, "<br/>");
	// 将聊天记录写到文件
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {

		}
	}
	xmlHttp.open("post", "download", true);
	xmlHttp.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xmlHttp.send("action=write&message=" + message);
	// 下载文件
	window.open("download");
}

/**
 * 获取好友列表
 * 
 * @param userName
 * @returns
 */
function getFriendList(userName) {
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var dataObj = eval("(" + xmlHttp.responseText + ")");
			for (var i = 0; i < dataObj.length; i++) {
				var friendName = dataObj[i].friendName;
				addList(friendName, friendName);
			}
		}
	}
	xmlHttp.open("get", "getList?action=getFriendList&username=" + userName,
			true);
	xmlHttp.send();
}

/**
 * 获取群列表
 * 
 * @param userName
 * @returns
 */
function getGroupList(userName) {
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var dataObj = eval("(" + xmlHttp.responseText + ")");
			for (var i = 0; i < dataObj.length; i++) {
				var groupName = dataObj[i].groupName;
				addList(groupName, groupName);
			}
		}
	}
	xmlHttp.open("get", "getList?action=getGroupList&username=" + userName,
			true);
	xmlHttp.send();
}

/**
 * 输出列表
 * 
 * @param messagebox
 * @param value
 * @returns
 */
function addList(value, id) {
	var friendList = document.getElementById("friendList");
	friendList.options.add(new Option(value, id));
}

/**
 * 选择聊天对象
 * 
 * @param action
 * @returns
 */
function changeList(action) {
	var friendList = document.getElementById("friendList");
	if (action == "公共聊天室") {
		friendList.options.length = 0;
	} else if (action == "私聊") {
		friendList.options.length = 0;
		getFriendList(userName);
	} else if (action == "群聊") {
		friendList.options.length = 0;
		getGroupList(userName);
	}
}