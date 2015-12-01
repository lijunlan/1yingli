var mid;
var page = 1
checkLogin()
registNotify()
document.getElementById("admin_name").innerText = $.cookie('mname')


var changeTable = function (result) {
	$("#voucherTable").empty();
	$("#voucherTable").append(
		"<thead><tr><th>ID</th><th>创建时间</th><th>优惠金额</th><th>优惠券验证码</th>" +
		"<th>开始时间</th><th>结束时间</th><th>创建人</th><th>订单ID</th>" +
		"<th>是否使用</th></tr></thead><tbody>");
	var list = result.data;
	$.each(list, function (index, data) {
		var createTime = parseInt(data.createTime, 10);
		var startTime = parseInt(data.startTime, 10);
		var endTime = parseInt(data.endTime, 10);
		var createTime = new Date(createTime);
		var startTime = new Date(startTime);
		var endTime = new Date(endTime);
		var row = "<tr><td>";
		row += data.id + "</td><td>";
		row += createTime.toLocaleString() + "</td><td>";
		row += data.money + "</td><td>";
		row += data.number + "</td><td>"
		row += startTime.toLocaleString() + "</td><td>";
		row += endTime.toLocaleString() + "</td><td>";
		row += data.origin + "</td><td>";
		row += data.orderId + "</td><td>";
		row += data.used + "</td>";
		$("#voucherTable").append(row);
	})
	$("#voucherTable").append("</tbody>");
}

var getList = function () {
	myJson.method = "getVoucherLsit";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
}
$(function () {
	$('#chooseBeginTime').datetimepicker({
		format: "Y,m,d,H,i", //格式化日期
		todayButton: true, //关闭选择今天按钮
		step: 1
	});
	$('#chooseEndTime').datetimepicker({
		format: "Y,m,d,H,i", //格式化日期
		todayButton: true, //关闭选择今天按钮
		step: 1
	});
	document.getElementById("lastPage").disabled = true;
	getList()
	if (page == 1)
		document.getElementById("lastPage").disabled = true;
	document.getElementById("pageInput").value = page;
});

function changePage(action) {
	if (action == "last") {
		page--
	} else if (action == "next") {
		page++
	}
	document.getElementById("pageInput").value = page
	$('button#get').trigger("click")
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
		var beginDate = $('#chooseBeginTime').val();
		var endDate = $('#chooseEndTime').val();
		process1 = beginDate.split(',');
		process2 = endDate.split(',');
		var date1 = new Date(process1[0], Number(process1[1]) - 1, process1[2], process1[3], process1[4], 0);
		var date2 = new Date(process1[0], Number(process2[1]) - 1, process2[2], process2[3], process2[4], 0);
		var time1 = date1.getTime();
		var time2 = date2.getTime();
		var money = $('#inputMoney').val();
		var count = $('#inputCount').val();
		if (isNaN(money) || money <= 0) {
			alert("所填写金额不正确");
			return;
		}
		if (isNaN(count) || count <= 0) {
			alert("所填写数量不正确");
			return;
		}
		if (count.toString().indexOf('.') != -1) {
			alert("所填写数量不正确");
			return;
		}
		if (time1 >= time2 || beginDate == "" || endDate == "") {
			alert("所填写时间不正确");
			return;
		}
		myJson.method = "createVoucher";
		myJson.money = money.toString();
		myJson.count = count.toString();
		myJson.endTime = time2.toString();
		myJson.startTime = time1.toString();
		myAjax(myJson, get);
	})
});