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
	var changeTable = function(info) {
		$("#infoTable").empty();
		$("#infoTable")
				.append(
						"<tr><th width=\"3%\">用户名</th><th width=\"3%\">真实姓名</th><th width=\"3%\">昵称</th>"
								+ "<th width=\"1%\">是否是老师</th><th width=\"4%\">电话</th><th width=\"4%\">地址</th>"
								+ "<th width=\"24%\">头像地址</th><th width=\"4%\">创建时间</th><th width=\"4%\">邮箱</th><th width=\"50%\">简历</th></tr>");
		var list = eval("(" + info + ")");
		$.each(list, function(index, data) {
			var row = "<tr><td>"
			row += data.username + "</td><td>";
			row += data.name + "</td><td>";
			row += data.nickName + "</td><td>"
			row += data.isTeacher + "</td><td>";
			row += data.phone + "</td><td>";
			row += data.address + "</td><td>";
			row += "<img src='"+data.iconUrl + "'/></td><td>";
			row += data.creatTime + "</td><td>";
			row += data.email + "</td><td>"
			row += data.resume + "</td>";
			//d.toLocaleString()
			$("#infoTable").append(row);
		})
	};

	$(function() {
		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style':'manager','method':'getUserList','mid': '" + mid
					+ "','page':'" + page + "'}",
			success : function(data, status) {
				var result = eval("(" + data + ")");
				if (result.msg == "manager is not existed") {
					$('#modal-container-390572').modal('show')
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
														data : "{'style':'manager','method':'getUserList','mid': '"
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
														data : "{'style':'manager','method':'getUserList','mid': '"
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
														data : "{'style':'manager','method':'getUserList','mid': '"
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
<title>客户管理</title>
</head>
<body>
	<button id="return">返回功能选择</button>
	<button id="get">获取用户</button>
	<br>
	<button id="lastPage">上一页</button>
	<input id="pageInput"></input>
	<button id="nextPage">下一页</button>
	<table border="1" id="infoTable" width="100%">
		<tr>
			<th>用户名</th>
			<th>真实姓名</th>
			<th>昵称</th>
			<th>是否是老师</th>
			<th>电话</th>
			<th>地址</th>
			<th>头像地址</th>
			<th>创建时间</th>
			<th>邮箱</th>
			<th>简历</th>
		</tr>
	</table>
	<div id="sound"></div>
	<div id="modal-container-390572" class="modal hide fade" role="dialog"
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