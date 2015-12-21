function go() {
	if (document.getElementById("username").value == ""
		|| document.getElementById("password").value == "") {
		$('#modalMsg').text("用戶名和密碼不能為空");
		$('#modal-container-390574').modal('show');
		return;
	}
	var password = encryptedStr(document.getElementById("password").value, publickey);
	var json = new Object();
	json.style = 'manager';
	json.method = 'login';
	json.username = document.getElementById("username").value;
	json.password = password;
	myAjax(json, keepInfo);
}

//回调的保存数据用的函数
function keepInfo(result) {
	$.cookie('mid', result.mid);
	$.cookie('mname', result.mname);
	self.location = "index.html";
}
