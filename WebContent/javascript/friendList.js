var userName = null;

window.onload = function() {
	getUserName();
	document.getElementById("userName").innerHTML = userName;
	getFriendList(userName);
	getGroupList(userName);
}

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
				addList("friendList", friendName);
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
				addList("groupList", groupName);
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
function addList(messagebox, value) {
	var box = document.getElementById(messagebox);
	var list = document.createElement("div");
	list.className = "center";
	var button = document.createElement("input");
	button.type = "button";
	button.className = "name";
	button.value = value;
	list.appendChild(button);
	box.appendChild(list);
}

/**
 * 添加好友
 * 
 * @returns
 */
function addFriend() {
	var friendName = document.getElementById("friendName").value;
	if (friendName == "") {
		alert("好友名不能为空");
		return;
	}
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			alert(xmlHttp.responseText);
			location.reload();
		}
	}
	xmlHttp.open("get", "addOrDelete?action=addFriend" + "&userName="
			+ userName + "&friendName=" + friendName, true);
	xmlHttp.send();
}

/**
 * 删除好友
 * 
 * @returns
 */
function deleteFriend() {
	var friendName = document.getElementById("friendName").value;
	if (friendName == "") {
		alert("好友名不能为空");
		return;
	}
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			alert(xmlHttp.responseText);
			location.reload();
		}
	}
	xmlHttp.open("get", "addOrDelete?action=deleteFriend" + "&userName="
			+ userName + "&friendName=" + friendName, true);
	xmlHttp.send();
}

/**
 * 添加群
 * 
 * @returns
 */
function addGroup() {
	var groupName = document.getElementById("groupName").value;
	if (groupName == "") {
		alert("群名不能为空");
		return;
	}
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			alert(xmlHttp.responseText);
			location.reload();
		}
	}
	xmlHttp.open("get", "addOrDelete?action=addGroup" + "&userName=" + userName
			+ "&groupName=" + groupName, true);
	xmlHttp.send();
}

/**
 * 删除群
 * 
 * @returns
 */
function deleteGroup() {
	var groupName = document.getElementById("groupName").value;
	if (groupName == "") {
		alert("群名不能为空");
		return;
	}
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			alert(xmlHttp.responseText);
			location.reload();
		}
	}
	xmlHttp.open("get", "addOrDelete?action=deleteGroup" + "&userName="
			+ userName + "&groupName=" + groupName, true);
	xmlHttp.send();
}

/**
 * 修改用户名
 * 
 * @returns
 */
function changeUserName() {
	var newUserName = document.getElementById("newUserName").value;
	if (newUserName == "") {
		alert("用户名不能为空");
		return;
	}
	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			alert(xmlHttp.responseText);
			location.reload();
		}
	}
	xmlHttp.open("get", "changeUserName?oldUserName=" + userName
			+ "&newUserName=" + newUserName, true);
	xmlHttp.send();
}

/**
 * 邀请好友进群
 * 
 * @returns
 */
function invite() {
	var groupName = document.getElementById("inviteGroupName").value;
	var friendName = document.getElementById("inviteFriendName").value;

	if (groupName == "") {
		alert("群名不能为空");
		return;
	}
	if (friendName == "") {
		alert("好友名不能为空");
		return;
	}

	var xmlHttp;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest;
	} else {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			alert(xmlHttp.responseText);
			location.reload();
		}
	}
	xmlHttp.open("get", "addOrDelete?action=inviteFriend" + "&userName="
			+ userName + "&friendName=" + friendName + "&groupName="
			+ groupName, true);
	xmlHttp.send();
}