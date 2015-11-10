<%@page import="javax.activation.DataHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="cn.yiyingli.Util.Json"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单管理</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/ajax-pushlet-client.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.cookie.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/swfobject.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/messenger.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/StuffService-common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap-responsive.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/messenger.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/messenger-theme-future.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/buttons.css">
<style>
table {
	table-layout: fixed;
	word-break: break-all;
	word-wrap: break-word;
}
</style>
<script type="text/javascript">
	//1:按类搜索。2:按名字，订单号搜索
	var fun=1;
	var page = 1;
	var url = window.location.href
	var mid;
	var state;
	var salaryState=0;
	checkLogin()
	registNotify()
	var payTeacher = function(oid) {
		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style': 'manager','method':'orderSalaryDone','mid':'"
					+ mid + "','orderId':'" + oid + "'}",
			success : function(data, status) {
				$("#get").trigger('click');
				Messenger().post("支付完成")
				//alert("Data: " + data + "\nStatus: " + status);

			}
		});
	}
	var refund = function(oid) {
		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style': 'manager','method':'orderReturnDone','mid':'"
					+ mid + "','orderId':'" + oid + "'}",
			success : function(data, status) {
				$("#get").trigger('click');
				Messenger().post("退款完成")
				//alert("Data: " + data + "\nStatus: " + status);
			}
		});
	}
	var process = function(oid, agree) {

		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style': 'manager','method':'dealOrder','mid':'" + mid
					+ "','orderId':'" + oid + "','deal':'" + agree + "'}",
			success : function(data, status) {
				$("#get").trigger('click');
				Messenger().post("处理完成")
				//alert("Data: " + data + "\nStatus: " + status);
			}
		});
	}
	var changeTable = function(info) {
		$("#infoTable").empty();
		if(state=="0500"){
			$("#infoTable")
			.append(
					"<tr><th width=\"7%\">订单号</th><th width=\"7%\">课程名称</th><th width=\"7%\">课程时间安排</th>"
							+ "<th width=\"1%\">课程时长</th><th width=\"3%\">交易时间</th><th width=\"3%\">导师确认时间</th><th width=\"3%\">交易金额</th><th width=\"2%\">客户姓名</th>"
							+ "<th width=\"4%\">客户手机</th><th width=\"4%\">客户微信</th><th width=\"4%\">客户邮箱</th><th width=\"21%\">客户简介</th><th width=\"3%\">导师</th>"
							+ "<th width=\"7%\">导师支付宝账号</th><th width=\"21%\">问题</th><th width=\"3%\">操作</th></tr>");
		}else{
			$("#infoTable")
			.append(
					"<tr><th width=\"7%\">订单号</th><th width=\"7%\">课程名称</th><th width=\"7%\">课程时间安排</th>"
							+ "<th width=\"1%\">课程时长</th><th width=\"3%\">交易时间</th><th width=\"3%\">交易金额</th><th width=\"3%\">客户姓名</th>"
							+ "<th width=\"4%\">客户手机</th><th width=\"5%\">客户微信</th><th width=\"5%\">客户邮箱</th><th width=\"21%\">客户简介</th><th width=\"3%\">导师</th>"
							+ "<th width=\"7%\">导师支付宝账号</th><th width=\"21%\">问题</th><th width=\"3%\">操作</th></tr>");
		}
		
		var list = eval("(" + info + ")");
		$.each(list, function(index, data) {
			var ct = parseInt(data.createTime, 10)
			var createtime = new Date(ct)
			var row = "<tr><td>"
			row += data.orderId + "</td><td>";
			row += data.title + "</td><td>";
			row += data.selectTimes + "</td><td>";
			row += data.time + "</td><td>";
			row += createtime.toLocaleString() + "</td><td>";
			if(state=="0500"){
				row += data.okTime + "</td><td>";
			}
			row += data.price + "</td><td>";
			row += data.customerName + "</td><td>"
			row += data.customerPhone + "</td><td>";
			row += data.weixin + "</td><td>";
			row += data.customerEmail + "</td><td>";
			row += data.userIntroduce + "</td><td>";
			row += data.teacherName + "</td><td>";
			row += data.teacherAlipayNo + "</td><td>";
			row += data.question + "</td>";
			if (data.state.split(',')[0] == "0700") {
				row += "<td><button onclick=refund('" + data.orderId
						+ "')>确认退款</button></td></tr>"
			} else if (data.salaryState=="1") {
				row += "<td><button onclick=payTeacher('" + data.orderId
						+ "')>确认支付导师</button></td></tr>"
			} else if (data.state.split(',')[0] == "1300") {
				row += "<td><button onclick=process('" + data.orderId + "',"
						+ false + ")>同意退款</button><button onclick=process('"
						+ data.orderId + "'," + true
						+ ")>拒绝退款</button></td></tr>"
			}else{
				row+="<td></td></tr>";
			}
			$("#infoTable").append(row);
		})
	}
	
	var getStateName=function(s){
		switch(s){
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
			stateName = "导师已经确认，等待确认时间";
			break;
		case "0500":
			stateName = "已经约定好时间，等待服务";
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
		return stateName
	}
	var changeTableBySearch = function(info) {
		$("#infoTable").empty();
		$("#infoTable")
				.append(
						"<tr><th width=\"7%\">订单号</th><th width=\"10%\">订单状态</th><th width=\"6%\">课程名称</th><th width=\"6%\">课程时间安排</th>"
								+ "<th width=\"1%\">课程时长</th><th width=\"3%\">交易时间</th><th width=\"3%\">交易金额</th><th width=\"3%\">客户姓名</th>"
								+ "<th width=\"6%\">客户手机</th><th width=\"6%\">客户邮箱</th><th width=\"18%\">客户简介</th><th width=\"3%\">导师</th>"
								+ "<th width=\"6%\">导师支付宝账号</th><th width=\"19%\">问题</th><th width=\"3%\">操作</th></tr>");
		var list = eval("(" + info + ")");
		$.each(list, function(index, data) {
			var tmp = parseInt(data.createTime, 10)
			var d = new Date(tmp)
			var row = "<tr><td>"
			row += data.orderId + "</td><td>";
			//添加订单状态
			tmp=data.state.split(",")
			for(i=0;i<tmp.length;i++){
				row += getStateName(tmp[i])+"<-"
			}
			row += "</td><td>";
			
			row += data.title + "</td><td>";
			row += data.selectTimes + "</td><td>";
			row += data.time + "</td><td>";
			row += d.toLocaleString() + "</td><td>";
			row += data.price + "</td><td>";
			row += data.customerName + "</td><td>"
			row += data.customerPhone + "</td><td>";
			row += data.customerEmail + "</td><td>";
			row += data.userIntroduce + "</td><td>";
			row += data.teacherName + "</td><td>";
			row += data.teacherAlipayNo + "</td><td>";
			row += data.question + "</td>";
			if (data.state.split(',')[0] == "0700") {
				row += "<td><button onclick=refund('" + data.orderId
						+ "')>确认退款</button></td></tr>"
			} else if (data.salaryState == "1") {
				row += "<td><button onclick=payTeacher('" + data.orderId
						+ "')>确认支付导师</button></td></tr>"
			} else if (data.state.split(',')[0] == "1300") {
				row += "<td><button onclick=process('" + data.orderId + "',"
						+ false + ")>同意退款</button><button onclick=process('"
						+ data.orderId + "'," + true
						+ ")>拒绝退款</button></td></tr>"
			}else{
				row+="<td></td></tr>";
			}
			$("#infoTable").append(row);
		})
	}
	//init
	$(function() {
		if (url.indexOf("?") != -1) {
			str = url.split("?")
			tmp = str[1].split("=")
			var tmpState = tmp[1]
		}
		if (tmpState != null) {
			state = tmpState
			if(state == "0900"){
				salaryState = 1;
				var json= "{'mid': '" + mid + "','page':'" + page + "','salaryState':'" + salaryState +"'}"
			}else{
				var json= "{'mid': '" + mid + "','page':'" + page + "','state':'" + state + "'}"
			}
			$.ajax({
				type : "POST",
				url : "/StuffService/mGetOrder",
				data :json,
				success : function(data, status) {
					var result = eval("(" + data + ")");
					if (result.data == "manager is not existed") {
						$('#modal-container-390574').modal('show')
						return
					}
					changeTable(result.data);
					if (state == "0700") {
						$("#orderState").val(7)
					} else if (state == "1300") {
						$("#orderState").val(14)
					} else if (state == "0900") {
						//暂时沿用之前的标识
						$("#orderState").val(17)
					}
				}
			});
		} else {
			$.ajax({
				type : "POST",
				url : "/StuffService/mGetOrder",
				data : "{'mid': '" + mid + "','page':'" + page + "','state':'"
						+ "0100" + "'}",
				success : function(data, status) {
					var result = eval("(" + data + ")");
					if (result.data == "manager is not existed") {
						$('#modal-container-390574').modal('show')
						return
					}
					changeTable(result.data);
				}
			});
		}
		if (page == 1)
			document.getElementById("lastPage").disabled = true;
		document.getElementById("pageInput").value = page;
	})
	$(document)
			.ready(
					function() {
						$("button#get")
								.click(
										function() {
											fun=1;
											salaryState = 0;
											switch (document
													.getElementById("orderState").value) {
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
											default:
												state = "0100";
											}
											page=document
											.getElementById("pageInput").value
											if(salaryState!=0){
												var json="{'mid': '"
													+ mid
													+ "','page':'"
													+ page
													+ "','salaryState':'" + salaryState +"'}"
											}else{
												var json="{'mid': '"
													+ mid
													+ "','page':'"
													+ page
													+ "','state':'"
													+ state + "'}"
											}
											$.ajax({
														type : "POST",
														url : "/StuffService/mGetOrder",
														data : json,
														success : function(
																data, status) {
															var result = eval("("
																	+ data
																	+ ")");
															changeTable(result.data);
															
															document.getElementById("nextPage").disabled = false;
															if (document
																	.getElementById("pageInput").value > 1)
																document
																		.getElementById("lastPage").disabled = false;
															page = document
																	.getElementById("pageInput").value
														}
													});
										});
						$("button#lastPage")
								.click(
										function() {
											page--;
											document
											.getElementById("pageInput").value = page;
											if (page <= 1)
												document.getElementById("lastPage").disabled = true;
											if(fun==1){
												$('button#get').trigger("click")
											}else if(fun==2){
												$('button#searchButton').trigger("click")
											}
											
										});
						$("button#nextPage")
								.click(
										function() {
											page++;
											document
											.getElementById("pageInput").value = page;
											if(page>1){
												document.getElementById("lastPage").disabled = false;
											}
											if(fun==1){
												$('button#get').trigger("click")
											}else if(fun==2){
												$('button#searchButton').trigger("click")
											}
											
										});
						$("#return").click(
								function() {
									self.location = serverUrl
											+ "StuffService/index.jsp";
								})
								
						$("#searchButton").click(function(){
							fun=2;
							salaryState=0;
							if($("#search").val()==""){
								return
							}
							//判断输入内容
							reg=/^[0-9]+$/; 
							if(reg.test($("#search").val())){
								//订单no
								$.ajax({
														type : "POST",
														url : "http://service.1yingli.cn/yiyingliService/manage",
														data : "{'style':'manager','method':'getOrderById','mid': '"
																+ mid
																+ "','id':'"
																+ $("#search").val()
																+ "'}",
														success : function(
																data, status) {
															var result = eval("("
																	+ data
																	+ ")");
															changeTableBySearch(result.data);
															document.getElementById("lastPage").disabled = true;
															document.getElementById("nextPage").disabled = true;
														}
													})
							}else{
								//人名
								$
													.ajax({
														type : "POST",
														url : "http://service.1yingli.cn/yiyingliService/manage",
														data : "{'style':'manager','method':'getOrderListByName','mid': '"
																+ mid
																+ "','name':'"
																+ $("#search").val()
																+ "','page':'"+document
																.getElementById("pageInput").value+"'}",
														success : function(
																data, status) {
															var result = eval("("
																	+ data
																	+ ")");
															changeTableBySearch(result.data);
															page=document.getElementById("pageInput").value
															document.getElementById("lastPage").disabled = false;
															if (page <= 1)
																document.getElementById("lastPage").disabled = true;
															document.getElementById("nextPage").disabled = false;
														}
													})
							}
						})

					})
	
</script>
</head>
<body>
	<button id="return">返回功能选择</button>
	<br>
	<select name="orderState" id="orderState">
		
		<option value="1">客户下单，尚未支付</option>
		<option value="2">客户放弃支付，包括：客户取消订单，客户超时未支付</option>
		<option value="3">客户完成支付，等待导师确认</option>
		<option value="4">导师已经确认，等待确认时间</option>
		<option value="5">已经约定好时间，等待服务</option>
		<option value="6">服务完毕，用户不满</option>
		<option value="7">等待退款</option>
		<option value="8">退款成功</option>
		<option value="10">等待双方评价</option>
		<option value="11">等待导师评价</option>
		<option value="12">订单成功完成</option>
		<option value="13">订单失败关闭</option>
		<option value="14">客服介入中</option>
		<option value="15">尚未服务，用户请求退款</option>
		<option value="16">订单异常</option>
		<option value="17">尚未支付给导师</option>
		<option value="18">已支付给导师</option>
	</select>
	<button id="get">获取订单</button>
	<br>
	<input placeholder="搜索：例如订单id，姓名" id="search" type="text">
	<button id="searchButton">搜索</button>
	<br>
	<button id="lastPage">上一页</button>
	<input id="pageInput"></input>
	<button id="nextPage">下一页</button>

	<table border="1" id="infoTable" width="100%">
		<tr>
			<th>订单号</th>
			<th>课程名称</th>
			<th>课程时间安排</th>
			<th>课程时长</th>
			<th>交易时间</th>
			<th>交易金额</th>
			<th>客户姓名</th>
			<th>客户手机</th>
			<th>客户微信</th>
			<th>客户邮箱</th>
			<th>客户简介</th>
			<th>导师</th>
			<th>导师支付宝账号</th>
			<th>问题</th>
			<th>操作</th>
		</tr>
	</table>
	<div id="sound"></div>
	<div id="modal-container-390574" class="modal hide fade" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">出错</h3>
		</div>
		<div class="modal-body">
			<p id="modalMsg">登录过期，请重新登录</p>
		</div>
		<div class="modal-footer">
			<a class="button button-glow button-rounded button-caution"
				data-dismiss="modal" aria-hidden="true">关闭</a> <a
				class="button button-glow button-rounded button-raised button-primary"
				href="login.jsp">重新登录</a>
		</div>
	</div>
</body>
</html>