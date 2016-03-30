var page = 1;
var url = window.location.href;
var mid;

checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');

//显示细节
//TODO
var detail = function (pagesId) {
	window.open(
		"activityService.html?pagesId=" + pagesId, "newWindow",
		"height=500,width=400,top=100,left=100,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=yes, status=yes");
}

var createActivity = function (){
	var des = $("#inputDes").val();
	var key = $("#inputKey").val();
	myJson.method = "createActivity";
	myJson.description = des;
	myJson.key = key;
	myAjax(myJson, getAll);
}

var changeTable = function (result) {
	$("#activityList").empty();
	$("#activityList").append(
		"<tr><th>ID</th><th>活动标识</th><th>描述</th><th>创建时间</th>"
		+ "<th>包含导师数量</th><th>包含文章数量</th><th>包含服务数量</th><th>操作</th></tr>");
	var list = result.data;
	$.each(list, function (index, data) {
		var tmp = parseInt(data.createTime, 10);
		var d = new Date(tmp);
		var row = "<tr><td>";
		row += data.pagesId ;
		row += "<button onclick='removeActivity("
			+ data.pagesId + ")'>删除</button></td><td>";
		row += data.key + "</td><td>";
		row += data.description + "</td><td>";
		row += d.toLocaleString() + "</td><td>";
		row += data.teacherCount + "</td><td>";
		row += data.passageCount + "</td><td>";
		row += data.serviceProCount + "</td><td>";
	 	row += "<button onclick='detail("
			+ data.pagesId + ")'>编辑</button></td>";
		$("#activityList").append(row);
	});
};

function removeActivity(pagesId){
	myJson.method = "removeActivity";
	myJson.pagesId = pagesId.toString();
	myAjax(myJson,getAll);
}

$(function(){
	myJson.method = "getActivityList";
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
	myJson.method = "getActivityList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
}


function getAll() {
	page = document.getElementById("pageInput").value;
	myJson.method = "getActivityList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
	if (document.getElementById("pageInput").value > 1)
		document.getElementById("lastPage").disabled = false;
}
