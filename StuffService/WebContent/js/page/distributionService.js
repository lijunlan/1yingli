//初始化
var mid;
var page = 1;
checkLogin();
registNotify();

//清空填写的信息
var clean = function () {
	document.getElementById("username").value = "";
	document.getElementById("name").value = "";
	document.getElementById("pwd").value = "";
	document.getElementById("pwd2").value = "";
	document.getElementById("voucherMoney").value = "";
	document.getElementById("voucherCount").value = "";
}

//检查信息是否填写完全
var complete = function () {
	if (document.getElementById("username").value == ""
		|| document.getElementById("name").value == ""
		|| document.getElementById("pwd").value == ""
		|| document.getElementById("pwd2").value == ""
		|| document.getElementById("voucherMoney").value == ""
		|| document.getElementById("voucherCount").value == "") {
		return false;
	}
	else
		return true;
}

//根据ajax返回的信息填充画面
var changeTable = function (result) {
	$("#distributionTable").empty();
	$("#distributionTable").append(
		"<thead><tr><th>二维码</th><th>ID</th><th>用户名</th><th>姓名</th><th>注册时间</th>" +
		"<th>最后登录时间</th><th>是否发优惠券</th><th>优惠券面额</th>" +
		"<th>优惠券数量</th><th>成交数</th><th>订单数</th><th>注册数</th></tr></thead><tbody>");
	list = result.data;
	$.each(list, function (index, data) {
		var createTime = parseInt(data.createTime, 10);
		var lastLoginTime = parseInt(data.lastLoginTime, 10);
		var createTime = new Date(createTime);
		var lastLoginTime = new Date(lastLoginTime);
		var row = "<tr><td>";
		row += "<div id='" + data.id + "'/></td><td>";
		row += data.id + "</td><td>";
		row += data.username + "</td><td>";
		row += data.name + "</td><td>";
		row += createTime.toLocaleString() + "</td><td>";
		row += lastLoginTime.toLocaleString() + "</td><td>";
		row += data.sendVoucher + "</td><td>";
		row += data.voucherMoney + "</td><td>";
		row += data.voucherCount + "</td><td>";
		row += data.dealNumber + "</td><td>";
		row += data.orderNumber + "</td><td>";
		row += data.registerNumber + "</td></tr>";
		$("#distributionTable").append(row);

		var qrcode = new QRCode(document.getElementById(data.id), {
			width: 150,//设置宽高
			height: 150
		});
		qrcode.makeCode("http://www.1yingli.cn/#!/register?distributorNO=" + data.number);

	})
	$("#distributionTable").append("</tbody>");
};

var getList = function () {
	myJson.method = "getDistributorList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
}

$(function () {
	document.getElementById("lastPage").disabled = true;
	getList();
	if (page == 1)
		document.getElementById("lastPage").disabled = true;
	document.getElementById("pageInput").value = page;
});

function changePage(action) {
	if (action == "last") {
		page--;
	} else if (action == "next") {
		page++;
	}
	document.getElementById("pageInput").value = page;
	if (page <= 1) {
		document.getElementById("lastPage").disabled = true;
	} else {
		document.getElementById("lastPage").disabled = false;
	}
	getList();
}

function get() {
	page = document.getElementById("pageInput").value;
	if (page > 1) {
		document.getElementById("lastPage").disabled = false;
	} else {
		document.getElementById("lastPage").disabled = true;
	}
	getList();
}

$(document).ready(function () {
	$("#submit").click(function () {
		if (complete() == false) {
			alert("信息不完整");
			return;
		}
		if (document.getElementById("pwd").value != document.getElementById("pwd2").value) {
			alert("两次输入密码不一致");
			return;
		}
		username = document.getElementById("username").value;
		name = document.getElementById("name").value;
		password = document.getElementById("pwd").value;
		if (password.length > 20 || password.length < 6) {
			alert("密码长度要求6-20位，非纯数字");
			return;
		}
		var patrn = /^[0-9]{6,20}$/;
		if (patrn.exec(password)) {
			alert("密码长度要求6-20位，非纯数字");
			return;
		}
		voucherMoney = document.getElementById("voucherMoney").value;
		voucherCount = document.getElementById("voucherCount").value;
		var checkbox = document.getElementById('sendVoucher');
		var sendVoucher = checkbox.checked;
		password = encryptedStr(password, publickey);

		myJson.method = 'createDistributor';
		myJson.username = username;
		myJson.name = name;
		myJson.password = password;
		myJson.voucherMoney = voucherMoney.toString();
		myJson.voucherCount = voucherCount.toString();
		myJson.sendVoucher = sendVoucher.toString();
		myAjax(myJson, get);
		clean();
	})
})