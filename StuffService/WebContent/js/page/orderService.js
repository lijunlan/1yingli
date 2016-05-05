//1:按类搜索。2:按名字，订单号搜索。
var fun = 1;
//页数
var page = 1;
var url = window.location.href;
var mid;
//表示订单的普通状态
var state = "0100";
//表示订单是否已经支付导师
var salaryState = 0;
//一页最多显示12个订单
var order = new Array(12);
checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');

//订单回退
var restart = function (oid) {
	//myJson的mid和style是不会变的，因此在StuffService-common.js中统一赋值
	myJson.method = "restartOrder";
	myJson.orderId = oid.toString();
	myAjax(myJson, get);
	Messenger().post("回退完成");
}

//支付导师
var payTeacher = function (oid) {
	myJson.method = "orderSalaryDone";
	myJson.orderId = oid.toString();
	myAjax(myJson, get);
	Messenger().post("支付完成");
}

//退款
var refund = function (oid) {
	myJson.method = "orderReturnDone";
	myJson.orderId = oid.toString();
	myAjax(myJson, get);
	Messenger().post("退款完成");
}

//客服处理订单
var process = function (oid, agree) {
	myJson.method = "dealOrder";
	myJson.orderId = oid.toString();
	myJson.deal = agree.toString();
	myAjax(myJson, get);
	Messenger().post("处理完成");
}

//显示详情
function showDetail(index, action) {
	//action=0,普通的显示
	//action=1,增加状态追踪
	//处理1970时间
	var ct = parseInt(order[index].createTime, 10);
	var pt = parseInt(order[index].payTime, 10);
	var createTime = new Date(ct);
	var payTime = new Date(pt);
	$("#modalDetail").text("");
	$("#modalDetail").append("<b>流水ID：</b>" + order[index].orderId + "<br>");
	$("#modalDetail").append("<b>订单ID：</b>" + order[index].orderListId + "<br>");
	$("#modalDetail").append("<b>课程名称：</b>" + order[index].title + "<br>");
	//当订单是搜索出来的，应显示订单的所有状态，此时action=
	if (action == 1) {
		var tmp2 = getStateName(order[index].state.split(",")[0]);
		$("#modalDetail").append("<b>订单状态：</b>" + tmp2 + "<br>");
	}
	if (action == 2 || action == 3) {
		tmp = order[index].state.split(",");
		var tmp2 = "";
		for (i = 0; i < tmp.length; i++) {
			tmp2 += getStateName(tmp[i]) + "<-";
		}
		$("#modalDetail").append("<b>订单状态：</b>" + tmp2 + "<br>");
	}
	$("#modalDetail").append("<b>课程时间安排：</b>" + order[index].selectTimes + "<br>");
	$("#modalDetail").append("<b>课程时长：</b>" + order[index].time + "<br>");
	$("#modalDetail").append("<b>交易时间：</b>" + createTime.toLocaleString() + "<br>");
	$("#modalDetail").append("<b>支付时间：</b>" + payTime.toLocaleString() + "<br>");
	$("#modalDetail").append("<b>支付方式：</b>" + order[index].payMethod + "<br>");
	$("#modalDetail").append("<b>导师确认时间：</b>" + order[index].okTime + "<br>");
	$("#modalDetail").append("<b>交易金额：</b>" + order[index].money + "<br>");
	$("#modalDetail").append("<b>原始价格：</b>" + order[index].originMoney + "<br>");
	$("#modalDetail").append("<b>分销人姓名：</b>" + order[index].distriName + "<br>");
	$("#modalDetail").append("<b>客户姓名：</b>" + order[index].customerName + "<br>");
	$("#modalDetail").append("<b>客户手机：</b>" + order[index].customerPhone + "<br>");
	$("#modalDetail").append("<b>客户微信：</b>" + order[index].weixin + "<br>");
	$("#modalDetail").append("<b>客户邮箱：</b>" + order[index].customerEmail + "<br>");
	$("#modalDetail").append("<b>客户自我介绍：</b>" + order[index].userIntroduce + "<br>");
	$("#modalDetail").append("<b>导师姓名：</b>" + order[index].teacherName + "<br>");
	$("#modalDetail").append("<b>导师支付宝：</b>" + order[index].teacherAlipayNo + "<br>");
	$("#modalDetail").append("<b>导师Paypal：</b>" + order[index].teacherPaypalNo + "<br>");
	$("#modalDetail").append("<b>客户问题：</b>" + order[index].question + "<br>");
	$("#modalDetail").append("<b>备注：</b>" + order[index].remark + "<br>");
	$("#modalDetail").append("<b>是否回访：</b>" + (order[index].returnVisit=="true"?"是":"否") + "<br>");
	if(order[index].returnVisit!="true"){
		$("#modalDetail").append("<b></b><button onclick=\"returnVisit("+order[index].orderId+")\">确认回访</button><br>");
	}
	$("#modalDetail").append("<b>提交备注：</b><textarea id=\"remarkTextArea\"/><br>");
	$("#modalDetail").append("<b></b><button onclick=\"remark("+order[index].orderId+")\">提交备注</button><br>");
	$("#modalOrderDetail").modal();
}

function remark(orderId){
	myJson.method = "remarkOrder";
	myJson.orderId = orderId.toString();
	myJson.remark = $("#remarkTextArea").val();
	myAjax(myJson, null);
	Messenger().post("提交备注完成");
}

function returnVisit(orderId){
	myJson.method = "returnVisitOrder";
	myJson.orderId = orderId.toString();
	myAjax(myJson, null);
	Messenger().post("回访完成");
}

//根据ajax返回的数据显示页面
var changeTable = function (result) {
	$("#infoTable").empty();
	//当搜索的时候
	if (fun == 2) {
		$("#infoTable").append(
			"<tr><th>流水号</th><th>订单号</th> <th>下单或支付时间</th> <th>交易金额</th> <th>原始价格</th>" +
			"<th>客户姓名</th> <th>电话</th> <th>微信</th> <th>邮箱</th> <th>导师</th><th>是否评价</th>" +
			"<th>操作</th> <th>点击显示详情</th></tr>");
	}
	//非搜索 
	else {
		//当订单状态为尚未支付的时候，显示下单时间
		if ((state == "0100" || state == "0200") && salaryState == 0) {
			$("#infoTable").append(
				"<tr><th>流水号</th><th>订单号</th> <th>下单时间</th> <th>交易金额</th> <th>原始价格</th>" +
				"<th>客户姓名</th> <th>电话</th> <th>微信</th> <th>邮箱</th> <th>导师</th>" +
				"<th>操作</th> <th>点击显示详情</th></tr>");
		}
		//当导师和学员已经完成交流，根据salaryState筛选，根据状态显示学员是否评价导师
		//此时学员已经支付，因此显示支付时间 
		else if (salaryState == 1 || salaryState == 2) {
			$("#infoTable").append(
				"<tr><th>流水号</th><th>订单号</th> <th>支付时间</th> <th>交易金额</th> <th>原始价格</th>" +
				"<th>客户姓名</th> <th>电话</th> <th>微信</th> <th>邮箱</th> <th>导师</th>" +
				"<th>是否评价</th><th>操作</th> <th>点击显示详情</th></tr>");
		} 
		//订单已经支付，但是还没有到应该支付给导师那一步，变化仅仅是显示支付时间而不是下单时间
		else {
			$("#infoTable").append(
				"<tr><th>流水号</th><th>订单号</th> <th>支付时间</th> <th>交易金额</th> <th>原始价格</th>" +
				"<th>客户姓名</th> <th>电话</th> <th>微信</th> <th>邮箱</th> <th>导师</th>" +
				"<th>操作</th> <th>点击显示详情</th></tr>");
		}
	}
	var list = result.data;
	$.each(list, function (index, data) {
		order[index] = data;
		//处理1970时间
		var ct = parseInt(data.createTime, 10);
		var pt = parseInt(data.payTime, 10);
		var paytime = new Date(pt);
		var createtime = new Date(ct);
		var row = "<tr><td>";
		row += data.orderListId+"<br>流水需支付："+data.orderListPayMoney + "</td><td>";
		row += data.orderId + "</td><td>";
		//按照学员是否支付来决定要显示的事“支付时间”或“下单时间”
		if (state == "0100" || state == "0200") {
			row += createtime.toLocaleString() + "</td><td>";
		} else {
			row += paytime.toLocaleString() + "</td><td>";
		}
		row += data.money + "</td><td>";
		row += data.originMoney + "</td><td>";
		row += data.customerName + "</td><td>";
		row += data.customerPhone + "</td><td>";
		row += data.weixin + "</td><td style='word-break:break-all'>";
		row += data.customerEmail + "</td><td>";
		row += data.teacherName + "</td><td>";
		//根据订单是否要支付导师或已经支付导师，显示学员是否已经评价导师
		if (salaryState == 1 || salaryState == 2 || fun == 2) {
			if (data.state.split(',')[0] == '1000' ) {
				row += "NO</td><td>";
			} else if (data.state.split(',')[0] == '1010' || data.state.split(',')[0] == '1100') {
				row += "YES</td><td>";
			} else {
				row += "</td><td>";
			}
		}
		//订单不同的状态要进行不同的操作
		if (data.state.split(',')[0] == "0700") {
			row += "<button class='am-btn am-btn-danger' onclick=refund('" + data.orderId
			+ "')>确认退款</button><button  class='am-btn am-btn-danger' onclick=restart('" + data.orderId + "')>状态回退</button></td><td>";
		} else if (data.salaryState == "1") {
			row += "<button class='am-btn am-btn-danger' onclick=payTeacher('" + data.orderId
			+ "')>已完成支付</button><button class='am-btn am-btn-danger' onclick='goToPay("+data.orderId+")'>去支付</button></td><td>";
		} else if (data.state.split(',')[0] == "1300") {
			row += "<button class='am-btn am-btn-danger' onclick=process('" + data.orderId + "',"
			+ false + ")>同意退款</button><button class='am-btn am-btn-danger' onclick=process('"
			+ data.orderId + "'," + true + ")>拒绝退款</button></td><td>";
		} else {
			row += "</td><td>";
		}
		//显示订单详情

		if(data.returnVisit=="true")
			row += "<button class='am-btn am-btn-primary' onclick='showDetail(" + index + "," + fun + ")'>详情</button></td></tr>";
		else
			row += "<button class='am-btn am-btn-primary' style='background-color: #d9534f;border-color:#d9534f' onclick='showDetail(" + index + "," + fun + ")'>详情</button></td></tr>";
		$("#infoTable").append(row);
	})
}

//获取订单状态码对应的中文
var getStateName = function (s) {
	switch (s) {
		case "0100":
			stateName = "客户下单，尚未支付";
			break;
		case "0200":
			stateName = "客户放弃支付，包括：客户取消订单，客户超时未支付";
			break;
		case "0300":
			stateName = "客户完成支付，等待导师确认";
			break;
		case "0400":
			stateName = "导师已经确认";
			break;
		case "0500":
			stateName = "等待服务";
			break;
		case "0620":
			stateName = "服务完毕，用户不满";
			break;
		case "0700":
			stateName = "等待退款";
			break;
		case "0800":
			stateName = "退款成功";
			break;
		case "0900":
			stateName = "服务完毕,等待用户确认";
			break;
		case "1000":
			stateName = "等待双方评价";
			break;
		case "1010":
			stateName = "等待导师评价";
			break;
		case "1100":
			stateName = "订单成功完成";
			break;
		case "1200":
			stateName = "订单失败关闭";
			break;
		case "1300":
			stateName = "客服介入中";
			break;
		case "1500":
			stateName = "尚未服务，用户请求退款";
			break;
		case "1400":
			stateName = "订单异常";
			break;
		default:
			stateName = "";
	}
	return stateName;
}

// init
$(function () {
	//可能网页是通过浏览器提示框打开的，因此检查参数
	if (url.indexOf("?") != -1) {
		str = url.split("?");
		tmp = str[1].split("=");
		var tmpState = tmp[1];
	}
	//页面是通过提示框打开的时候
	//tmpState为参数中的状态
	if (tmpState != null) {
		state = tmpState;
		myJson.method = "getOrderList";
		myJson.page = page.toString();
		//状态码0900现在并不存在
		//状态码改过，之前0900是要支付导师，后来被单独了出来，因为对这个网页而言用起来方便，因此没有更改
		if (state == "2000") {
			salaryState = 1;
			myJson.salaryState = salaryState.toString();
			delete myJson.state;
		} else {
			//不是特殊的状态码，因此直接处理
			myJson.state = state.toString();
			delete myJson.salaryState;
		}
		myAjax(myJson, changeTable);
		if (state == "0700") {
			$('#orderState').find('option').eq(6).attr('selected', true);
		} else if (state == "1300") {
			$('#orderState').find('option').eq(12).attr('selected', true);
		} else if (state == "0900") {
			// 暂时沿用之前的标识
			$('#orderState').find('option').eq(17).attr('selected', true);
		} else if (state == "0300") {
			$('#orderState').find('option').eq(2).attr('selected', true);
		}else if (state == "2000") {
			$('#orderState').find('option').eq(15).attr('selected', true);
		}
	} else {
		//页面是管理员自己打开的，而不是点击提示框进来的
		myJson.method = "getOrderList";
		myJson.page = page.toString();
		myJson.state = "0100";
		delete myJson.salaryState;
		myAjax(myJson, changeTable);
	}
	if (page == 1)
		document.getElementById("lastPage").disabled = true;
	document.getElementById("pageInput").value = page;
});

function getAllOrders(){
	fun = 3;
	myJson.method="getAllOrderList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
}

//换页
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
	//fun==1说明当前是按类别浏览
	//fun==2说明当前正在搜索
	//fun==3说明在查看所有订单状态
	if (fun == 1) {
		$('button#get').trigger("click");
	} else if (fun == 2) {
		$('button#searchButton').trigger("click");
	} else if (fun == 3) {
		$('button#getAllOrderButton').trigger("click");
	}
}
//搜索
function search() {
	fun = 2;
	salaryState = 0;
	if ($("#search").val() == "") {
		return
	}
	// 判断输入内容
	reg = /^[0-9]+$/;
	if (reg.test($("#search").val())) {
		// 订单no
		myJson.method = "getOrderById"
		myJson.id = $("#search").val()
		myAjax(myJson, changeTable)
		document.getElementById("lastPage").disabled = true;
		document.getElementById("nextPage").disabled = true;
	} else {
		// 人名
		myJson.method = "getOrderListByName"
		myJson.name = $("#search").val()
		myJson.page = document.getElementById("pageInput").value
		myAjax(myJson, changeTable)
		page = document.getElementById("pageInput").value
		document.getElementById("lastPage").disabled = false;
		if (page <= 1)
			document.getElementById("lastPage").disabled = true;
		document.getElementById("nextPage").disabled = false;
	}
}

//点击获取触发的函数
function get() {
	fun = 1;
	salaryState = 0;
	var json;
	switch (document.getElementById("orderState").value) {
		case "1":
			state = "0100";
			break;
		case "2":
			state = "0200";
			break;
		case "3":
			state = "0300";
			break;
		case "4":
			state = "0400";
			break;
		case "5":
			state = "0500";
			break;
		case "6":
			state = "0620";
			break;
		case "7":
			state = "0700";
			break;
		case "8":
			state = "0800";
			break;
		case "10":
			state = "1000";
			break;
		case "11":
			state = "1010";
			break;
		case "12":
			state = "1100";
			break;
		case "13":
			state = "1200";
			break;
		case "14":
			state = "1300";
			break;
		case "15":
			state = "1500";
			break;
		case "16":
			state = "1400";
			break;
		case "17":
			salaryState = 1;
			break;
		case "18":
			salaryState = 2;
			break;
		case "19":
			state = "0900";
			break;
		default:
			state = "0100";
	}
	page = document.getElementById("pageInput").value;
	myJson.method = "getOrderList";
	myJson.page = page.toString();
	//因为状态码有两个，所以分别处理
	if (salaryState != 0) {
		myJson.salaryState = salaryState.toString();
		delete myJson.state;
	} else {
		myJson.state = state.toString();
		delete myJson.salaryState;
	}
	//按照分销人相关来获取订单
	var distri = document.getElementById("disList").distribution.value;
	if (distri != "NO") {
		myJson.rank = distri.toString();
	} else {
		delete myJson.rank;
	}
	myAjax(myJson, changeTable);
	document.getElementById("nextPage").disabled = false;
	if (document.getElementById("pageInput").value > 1)
		document.getElementById("lastPage").disabled = false;
	page = document.getElementById("pageInput").value;
}

var goToPay = function(oid){
	post(SERVICE_URL + 'AlipayTrans',{mid:mid,oid:oid});
}

function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];      
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}