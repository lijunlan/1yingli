//初始化
var page = 1;
var url = window.location.href;
var mid;
checkLogin();
registNotify();

$(function () {
	myJson.method = "getUserList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
	if (page == 1)
		document.getElementById("lastPage").disabled = true;
	document.getElementById("pageInput").value = page;
})

//根据ajax返回的数据
var changeTable = function (result) {
	$("#infoTable").empty();
	//因为数据量不一样，自动分配位置表格会很难看，因此手动分配，这个可以再优化一下
	$("#infoTable").append(
		"<tr><th width=\"3%\">用户名</th><th width=\"3%\">真实姓名</th><th width=\"3%\">昵称</th>"
		+ "<th width=\"1%\">是否是老师</th><th width=\"4%\">电话</th><th width=\"4%\">地址</th>"
		+ "<th width=\"24%\">头像地址</th><th width=\"4%\">创建时间</th><th width=\"4%\">邮箱</th><th width=\"50%\">简历</th></tr>");
	list = result.data;
	$.each(list, function (index, data) {
		var row = "<tr><td>";
		row += data.username + "</td><td>";
		row += data.name + "</td><td>";
		row += data.nickName + "</td><td>";
		row += data.isTeacher + "</td><td>";
		row += data.phone + "</td><td>";
		row += data.address + "</td><td>";
		row += "<img src='" + data.iconUrl + "' onload='if (this.width>200 || this.height>200) if (this.width/this.height>1) this.width=200; else this.height=200;'/></td><td>";
		row += data.creatTime + "</td><td>";
		row += data.email + "</td><td>";
		row += data.resume + "</td>";
		$("#infoTable").append(row);
	})
};

//变更页面
function changePage(action) {
	if (action == "last") {
		page--;
		if (page <= 1)
			document.getElementById("lastPage").disabled = true;
	} else if (action == "next") {
		page++;
		if (page >= 1)
			document.getElementById("lastPage").disabled = false;
	}
	//myJson中的mid和style不变，因此在StuffService-common.js中统一赋值
	myJson.method = "getUserList";
	myJson.page = page.toString();
	//封装的ajax方法
	myAjax(myJson, changeTable);
	document.getElementById("pageInput").value = page;
}

//获取页面内容
function get() {
	myJson.method = "getUserList";
	myJson.page = document.getElementById("pageInput").value;
	myAjax(myJson, changeTable);
	if (document.getElementById("pageInput").value > 1)
		document.getElementById("lastPage").disabled = false;
	page = document.getElementById("pageInput").value;
}