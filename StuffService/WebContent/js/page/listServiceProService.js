var page = 1;
var url = window.location.href;
var mid;

var fun = 1;

checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');

//显示细节
var detail = function (serviceProId) {
	window.open(
		"serviceProDetail.html?serviceProId=" + serviceProId, "newWindow",
		"height=500,width=400,top=100,left=100,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=yes, status=yes");
}

var changeTable = function (result) {
	$("#infoTable").empty();
	$("#infoTable").append(
		"<tr><th>ID</th><th>申请人</th><th>状态</th><th>上架</th>"
		+ "<th>服务标题</th><th>服务简介</th><th>价格</th><th>操作</th></tr>");
	var list = result.data;
	$.each(list, function (index, data) {
		var tmp = parseInt(data.createTime, 10);
		var d = new Date(tmp);
		var row = "<tr><td>";
		row += data.serviceProId ;
		row += "<button onclick='removeServicePro("
			+ data.serviceProId + ")'>删除</button></td><td>";
		row += "<a href=\"http://120.26.83.33/StuffServicet/teacherDetail.html?tid="+data.teacherId+"\" target=\"_blank\">"+data.teacherName + "</a></td><td>";
		row += state2zh(data.state) + "</td><td>";
		row += data.onshow + "</td><td>";
		row += data.title + "</td><td>";
		row += data.summary + "</td><td>";
		row += data.price+"/"+data.numeral+data.quantifier+ "</td><td>";
	 	row += "<button onclick='detail("
			+ data.serviceProId + ")'>详情</button>";
		if(data.state==0){
			row += "<button onclick=\"accept("+data.serviceProId+")\">通过</button><button onclick=\"refuse("+data.serviceProId+")\">拒绝</button></td>";
		}else{
			row += "</td>";
		}
		$("#infoTable").append(row);
	});
};

function removeServicePro(serviceProId){
	myJson.method = "removeServicePro";
	myJson.serviceProId = serviceProId.toString();
	myAjax(myJson,getAll);
}

function accept(serviceProId){
	myJson.method = "validateServicePro";
	myJson.serviceProId = serviceProId.toString();
	myJson.deal = true;
	myAjax(myJson,getAll);
}

function refuse(serviceProId){
	myJson.method = "validateServicePro";
	myJson.serviceProId = serviceProId.toString();
	myJson.deal = false;
	myAjax(myJson,getAll);
}

$(function(){
	var url = window.location.href;
	var attri = url.split("?")[1];
	if(attri!=null){
		var key = attri.split("=")[0];
		var value = attri.split("=")[1];
		if(key=="state"&&value=="0"){
			myJson.state=0;
			fun=3;
		}
	}
	myJson.method = "getServiceProList";
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
	myJson.method = "getServiceProList";
	if(fun==2){
		myJson.username=$("#inputSearchUsernameID").val();
		delete myJson.state;
	}else if (fun==3){
		myJson.state = 0;
		delete myJson.username;
	}else{
		delete myJson.username;
		delete myJson.state;
	}
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
}

function get(){
	fun = 2;
	page = document.getElementById("pageInput").value;
	delete myJson.state;
	myJson.username=$("#inputSearchUsernameID").val();
	myJson.method = "getServiceProList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
	if (document.getElementById("pageInput").value > 1)
		document.getElementById("lastPage").disabled = false;
}

function getAll() {
	fun = 1;
	page = document.getElementById("pageInput").value;
	delete myJson.username;
	delete myJson.state;
	myJson.method = "getServiceProList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
	if (document.getElementById("pageInput").value > 1)
		document.getElementById("lastPage").disabled = false;
}

function getByState() {
	fun = 3;
	page = document.getElementById("pageInput").value;
	delete myJson.username;
	myJson.state = 0;
	myJson.method = "getServiceProList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
	if (document.getElementById("pageInput").value > 1)
		document.getElementById("lastPage").disabled = false;
}