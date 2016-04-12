//var page = 1;
var url = window.location.href;
var mid;

checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');

var tmp = url.split("?");
var pagesId;
if(tmp.length>1){
	var t2 = tmp[1].split("=");
	if(t2.length>1){
		if(t2[0]=="pagesId"){
			pagesId=t2[1];
		}else{
			self.location.href="index.html";
		}
	}else{
		self.location.href="index.html";
	}
}else{
	self.location.href="index.html";
}

//显示细节
//TODO

function activityStyle2String(style){
	if(style==0){
		return "文章";
	}else if(style==1){
		return "导师";
	}else if(style==2){
		return "服务";
	}else{
		return "未知";
	}
}

function getId(style,data){
	if(style==0){
		return data.passageId;
	}else if(style==1){
		return data.teacherId;
	}else if(style==2){
		return data.serviceProId;
	}else{
		return "未知";
	}
}

function getName(style,data){
	if(style==0){
		return data.passageTitle;
	}else if(style==1){
		return data.teacherName;
	}else if(style==2){
		return data.serviceProTitle;
	}else{
		return "未知";
	}
}

var createContent = function(){
	myJson.method="createActivityContent";
	myJson.weight=$("#inputWeight").val();
	myJson.contentStyle=$("#inputStyle").val();
	myJson.activityDes = $("#inputDes").val();
	myJson.pagesId = pagesId;
	if(myJson.contentStyle==0){
		myJson.passageId = $("#inputID").val();
	}else if(myJson.contentStyle==1){
		myJson.teacherId = $("#inputID").val();
	}else if(myJson.contentStyle==2){
 		myJson.serviceProId = $("#inputID").val();
	}else{
		return;
	}
	myAjax(myJson, getAll);
}


var changeTable = function (result) {
	$("#activity").empty();
	$("#activity").append(
		"<tr><th>ID</th><th>活动标题</th><th>权重</th><th>类型</th>"
		+ "<th>ID</th><th>名字</th><th>操作</th></tr>");
	var list = result.data;
	$.each(list, function (index, data) {
		var tmp = parseInt(data.createTime, 10);
		var d = new Date(tmp);
		var row = "<tr><td>";
		row += data.contentId +"</td><td>";
		row += data.activityDes + "</td><td>";
		row += data.weight + "</td><td>";
		row += activityStyle2String(data.contentStyle) + "</td><td>";
		var showId = getId(data.contentStyle,data);
		var showName = getName(data.contentStyle,data);
		row += showId + "</td><td>";
		row += showName + "</td><td>";
	 	row += "<a href=\"activityContent.html?data="
			+ encodeURI($.toJSON(data)) + "\" target=\"_blank\">查看信息</a>" +
			"<button onclick='removeContent("
			+ data.contentId + ")'>删除</button></td>";
		$("#activity").append(row);
	});
};

function removeContent(contentId){
	myJson.method = "removeActivityContent";
	myJson.contentId = contentId.toString();
	myAjax(myJson,getAll);
}

$(function(){
	myJson.method = "getActivity";
	myJson.pagesId = pagesId.toString();
	myAjax(myJson, changeTable);
	// if (page == 1)
	// 	document.getElementById("lastPage").disabled = true;
	// document.getElementById("pageInput").value = page;
});

// function changePage(action) {
// 	if (action == "last") {
// 		page--;
// 	} else if (action == "next") {
// 		page++;
// 	}
// 	document.getElementById("pageInput").value = page;
// 	if (page <= 1)
// 		document.getElementById("lastPage").disabled = true;
// 	else
// 		document.getElementById("lastPage").disabled = false;
// 	myJson.method = "getActivity";
// 	myJson.page = page.toString();
// 	myAjax(myJson, changeTable);
// }


function getAll() {
	//page = document.getElementById("pageInput").value;
	myJson.method = "getActivity";
	myJson.pagesId = pagesId.toString();
	myAjax(myJson, changeTable);
	// if (document.getElementById("pageInput").value > 1)
	// 	document.getElementById("lastPage").disabled = false;
}
