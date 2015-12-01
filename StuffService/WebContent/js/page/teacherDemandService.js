//初始化
var mid;
checkLogin();
registNotify();
page = 1;
$(document).ready(function () {
	document.getElementById("lastPage").disabled = true;
	if (page == 1)
		document.getElementById("lastPage").disabled = true;
	document.getElementById("pageInput").value = page;
	get();
})

//根据ajax返回的数据，显示页面
var changeTable = function (result) {
	$("#infoTable").empty();
	$("#infoTable").append(
		"<tr><th>ID</th><th>邮箱</th><th>建议</th><th>创建时间</th><th>IP</th></tr>");
	var list = result.data;
	$.each(list, function (index, data) {
		var createTime = parseInt(data.createTime, 10);
		createTime = new Date(createTime);
		var row = "<tr><td>";
		row += data.id + "</td><td>";
		row += data.email + "</td><td>";
		row += data.demand + "</td><td>";
		row += createTime.toLocaleString() + "</td><td>";
		row += data.ip + "</td></tr>";
		$("#infoTable").append(row);
	})
};

//点击获取触发的函数
function get() {
	myJson.method = "getTeacherDemand";
	myJson.page = document.getElementById("pageInput").value;
	myAjax(myJson, changeTable);
	page = document.getElementById("pageInput").value;
	if (page <= 1) {
		document.getElementById("lastPage").disabled = true;
	} else {
		document.getElementById("lastPage").disabled = false;
	}
}

function changePage(action) {
	if (action == "last") {
		page--;
	} else if (action == "next") {
		page++;
	}
	document.getElementById("pageInput").value = page;
	get();
}