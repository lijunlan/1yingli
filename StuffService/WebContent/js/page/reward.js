//页数
var page = 1;
var url = window.location.href;
var mid;

checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');

function deal (rewardId) {
	myJson.method = "finishPayReward";
	myJson.rewardId = rewardId;
	myAjax(myJson,null);
	Messenger().post("成功完成打款");
}

var changeTable = function (result) {
	$("#infoTable").empty();
	$("#infoTable").append(
		"<tr><th>编号</th><th>导师名字</th><th>打赏人</th>"
		+ "<th>文章ID</th><th>时间</th><th>金额</th><th>操作</th></tr>");
	var list = result.data;
	$.each(list, function (index, data) {
		var tmp = parseInt(data.time, 10);
		var d = new Date(tmp);
		var row = "<tr><td>";
		row += data.rewardId + "</td><td>";
		row += "<a href=\"http://120.26.83.33/StuffServicet/teacherDetail.html?tid="+data.teacherId+"\" target=\"_blank\">"+data.teacherName + "</a></td><td>";
		row += data.userName + "</td><td>";
		row += data.passageId + "</td><td>";
		row += d.toLocaleString() + "</td><td>";
		row += data.money + "</td><td>";
		if(data.finishSalary!="true"){
			row += "<button onclick=\"deal("+data.rewardId+")\">完成打款</button></td>";
		}else{
			row += "</td>";
		}
		$("#infoTable").append(row);
	});
};


function get(){
	var teacherId = $("#inputSearchTeacherId").val();
	if(teacherId!=null&&teacherId!=""){
		myJson.method = "getRewardByTeacher";	
		myJson.teacherId = teacherId
	}else{
		myJson.method = "getRewardList";
	}
		myJson.page = page.toString();
		myAjax(myJson, changeTable);
}


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
	get();
}


$(function () {
	if (page == 1){
		document.getElementById("lastPage").disabled = true;
	}
	document.getElementById("pageInput").value = page;
	get();
});

