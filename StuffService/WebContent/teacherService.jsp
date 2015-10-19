<%@page import="javax.activation.DataHandler"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
<script type="text/javascript">
	var page = 1;
	var url = window.location.href
	var mid;
	checkLogin()
	registNotify()
	var detail = function(tid) {
		window
				.open(
						"teacherDetail.jsp?tid=" + tid,
						"newWindow",
						"height=500,width=400,top=100,left=100,toolbar=yes,menubar=yes,scrollbars=yes, resizable=yes,location=yes, status=yes");
	}
	var changeTable = function(info) {
		$("#infoTable").empty();
		$("#infoTable")
				.append(
						"<tr><th>ID</th><th>姓名</th><th>是否可以提供服务</th><th>电话</th><th>地址</th>"
								+ "<th>学位认证</th><th>邮箱认证</th><th>身份认证</th>"
								+ "<th>手机认证</th><th>工作认证</th><th>创建时间</th>"
								+ "<th>邮箱</th><th>简介</th><th>级别</th><th>详细信息</th></tr>");
		var list = eval("(" + info + ")");
		$.each(list, function(index, data) {
			var tmp = parseInt(data.createTime, 10)
			var d = new Date(tmp)
			var row = "<tr><td>"
			row += data.tid + "</td><td>";
			row += data.name + "</td><td>";
			row += data.onService + "</td><td>"
			row += data.phone + "</td><td>";
			row += data.address + "</td><td>";
			row += data.checkDegree + "</td><td>";
			row += data.checkEmail + "</td><td>";
			row += data.checkIDCard + "</td><td>";
			row += data.checkPhone + "</td><td>"
			row += data.checkWork + "</td><td>";
			row += data.createTime + "</td><td>";
			row += data.email + "</td><td>";
			row += data.introduce + "</td><td>";
			row += data.level + "</td><td>";
			row += "<button onclick='detail(" + data.tid
					+ ")'>详情</button></td>";
			//d.toLocaleString()
			//时间
			$("#infoTable").append(row);
		})
	};

	$(function() {
		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style':'manager','method':'getTeacherList','mid': '"
					+ mid + "','page':'" + page + "'}",
			success : function(data, status) {
				var result = eval("(" + data + ")");
				if (result.msg == "manager is not existed") {
					$('#modal-container-390573').modal('show')
					return
				}
				changeTable(result.data)
			}
		});
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
											$
													.ajax({
														type : "POST",
														url : "http://service.1yingli.cn/yiyingliService/manage",
														data : "{'style':'manager','method':'getTeacherList','mid': '"
																+ mid
																+ "','page':'"
																+ document
																		.getElementById("pageInput").value
																+ "'}",
														success : function(
																data, status) {
															var result = eval("("
																	+ data
																	+ ")");
															changeTable(result.data);
															if (document
																	.getElementById("pageInput").value > 1)
																document
																		.getElementById("lastPage").disabled = false;
															page = document
																	.getElementById("pageInput").value
														}
													})
										})
						$("button#lastPage")
								.click(
										function() {
											page--;
											if (page <= 1)
												document
														.getElementById("lastPage").disabled = true;

											$
													.ajax({
														type : "POST",
														url : "http://service.1yingli.cn/yiyingliService/manage",
														data : "{'style':'manager','method':'getTeacherList','mid': '"
																+ mid
																+ "','page':'"
																+ page + "'}",
														success : function(
																data, status) {
															var result = eval("("
																	+ data
																	+ ")");
															changeTable(result.data);
															document
																	.getElementById("pageInput").value = page;
														}
													});
										});
						$("button#nextPage")
								.click(
										function() {
											page++;
											document.getElementById("lastPage").disabled = false;

											$
													.ajax({
														type : "POST",
														url : "http://service.1yingli.cn/yiyingliService/manage",
														data : "{'style':'manager','method':'getTeacherList','mid': '"
																+ mid
																+ "','page':'"
																+ page + "'}",
														success : function(
																data, status) {

															var result = eval("("
																	+ data
																	+ ")");
															changeTable(result.data);
															document
																	.getElementById("pageInput").value = page;
														}
													});
										});
						$("#return").click(
								function() {
									self.location = serverUrl
											+ "StuffService/index.jsp";
								})

					})
</script>
<title>导师管理</title>
</head>
<body>
	<button id="return">返回功能选择</button>
	<button id="get">获取导师</button>
	<br>
	<button id="lastPage">上一页</button>
	<input id="pageInput"></input>
	<button id="nextPage">下一页</button>
	<table border="1" id="infoTable">
		<tr>
			<th>ID</th>
			<th>姓名</th>
			<th>是否可以提供服务</th>
			<th>电话</th>
			<th>地址</th>
			<th>学位认证</th>
			<th>邮箱认证</th>
			<th>身份认证</th>
			<th>手机认证</th>
			<th>工作认证</th>
			<th>创建时间</th>
			<th>邮箱</th>
			<th>简介</th>
			<th>级别</th>
			<th>详细信息</th>
		</tr>
	</table>
	<div id="sound"></div>
	<div id="modal-container-390573" class="modal hide fade" role="dialog"
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