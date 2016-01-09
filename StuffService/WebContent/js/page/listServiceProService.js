var page = 1;
var url = window.location.href;
var mid;

checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');


var changeTable = function (result) {
	$("#infoTable").empty();
	$("#infoTable").append(
		"<tr><th>ID</th><th>申请人</th><th>状态</th><th>上架</th>"
		+ "<th>服务标题</th><th>服务简介</th><th>服务内容</th><th>价格</th><th>操作</th></tr>");
	var list = result.data;
	$.each(list, function (index, data) {
		var tmp = parseInt(data.createTime, 10);
		var d = new Date(tmp);
		var row = "<tr><td>";
		row += data.tid + "</td><td>";
		row += data.name + "</td><td>";
		row += data.onService + "</td><td>";
		row += data.address + "</td><td>";
		row += data.email + "</td><td>";
		row += data.level + "</td><td>";
		row += "<div><input id='input" + data.tid + "' type='text' value='" + data.mile + "'><button  type='button' onclick='editMile("
		+ data.tid + ")'>修改</button></div></td><td>";
		if (data.onService == 'true') {
			row += "<button onclick='detail("
			+ data.tid + ")'>详情</button></td>";
		} else {
			row += "并没有</td>";
		}
		$("#infoTable").append(row);
	})
};

function editMile(tid) {
	myJson.method = 'editTeacherMile';
	myJson.teacherId = tid.toString();
	myJson.mile = $('#input' + tid).val().toString();
	if(fun == 1){
		myAjax(myJson, get);
	}else if(fun == 2){
		myAjax(myJson, search);
	}
	
}

$(function () {
	myJson.method = "getTeacherList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
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
	if (page <= 1)
		document.getElementById("lastPage").disabled = true;
	else
		document.getElementById("lastPage").disabled = false;
	myJson.method = "getTeacherList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
}

function get() {
	fun = 1;
	page = document.getElementById("pageInput").value;
	myJson.method = "getTeacherList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
	if (document.getElementById("pageInput").value > 1)
		document.getElementById("lastPage").disabled = false;
}