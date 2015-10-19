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
	src="<%=request.getContextPath()%>/js/messenger.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/swfobject.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/StuffService-common.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/messenger.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/messenger-theme-future.css">
<script type="text/javascript">
	mid = $.cookie('mid');
	registNotify()
	var tid = window.location.href.split("?")[1].split("=")[1]
	var processWork = function(info) {
		var result = "<th>公司名称</th><th>职位</th><th>介绍</th><th>开始时间</th><th>结束时间</th></tr>";
		var list = eval("(" + info + ")")
		$.each(list, function(index, data) {
			result += "<tr><td>" + data.companyName + "</td>"
			result += "<td>" + data.position + "</td>"
			result += "<td>" + data.description + "</td>"
			result += "<td>" + data.startTime + "</td>"
			result += "<td>" + data.endTime + "</td></tr>"
		})
		return result;
	}
	var processStudy = function(info) {
		var result = "<th>学校名称</th><th>专业</th><th>学位</th><th>描述</th><th>开始时间</th><th>结束时间</th></tr>";
		var list = eval("(" + info + ")")
		$.each(list, function(index, data) {
			result += "<tr><td>" + data.schoolName + "</td>"
			result += "<td>" + data.major + "</td>"
			result += "<td>" + data.degree + "</td>"
			result += "<td>" + data.description + "</td>"
			result += "<td>" + data.startTime + "</td>"
			result += "<td>" + data.endTime + "</td></tr>"
		})
		return result;
	}
	var processTips = function(info) {
		var result = "<th>类型</th></tr>";
		var list = eval("(" + info + ")")
		$.each(list, function(index, data) {
			result += "<tr><td>" + data.id + "</td></tr>"
		})
		return result;
	}
	var changeTable = function(data) {
		$("#infoTable").empty();
		var workCount = eval("(" + data.workExperience + ")").length + 1
		var studyCount = eval("(" + data.studyExperience + ")").length + 1
		var tipsCount = eval("(" + data.tips + ")").length + 1
		var row = ""
		row += "<tr><th>teacherID</th><td colspan=6>" + tid + "</td></tr>";
		row += "<tr><th>姓名</th><td colspan=6>" + data.name + "</td></tr>";
		row += "<tr><th>头像链接</th><td colspan=6>" + data.iconUrl + "</td></tr>";
		row += "<tr><th>电话</th><td colspan=6>" + data.phone + "</td></tr>";
		row += "<tr><th>邮箱</th><td colspan=6>" + data.email + "</td></tr>";
		row += "<tr><th>简介</th><td colspan=6>" + data.introduce + "</td></tr>";
		row += "<tr><th>粉丝数</th><td colspan=6>" + data.likeNo + "</td></tr>";
		row += "<tr><th>地址</th><td colspan=6>" + data.address + "</td></tr>";
		row += "<tr><th>交流方式</th><td colspan=6>" + data.talkWay + "</td></tr>";
		row += "<tr><th>评论数量</th><td colspan=6>" + data.commentNo
				+ "</td></tr>";
		row += "<tr><th>每周咨询次数</th><td colspan=6>" + data.timeperweek
				+ "</td></tr>";
		row += "<tr><th>空闲时间</th><td colspan=6>" + data.freeTime + "</td></tr>";
		row += "<tr><th>服务价格</th><td colspan=6>" + data.price + "</td></tr>";
		row += "<tr><th>服务时间</th><td colspan=6>" + data.serviceTime
				+ "</td></tr>";
		row += "<tr><th>服务标题</th><td colspan=6>" + data.serviceTitle
				+ "</td></tr>";
		row += "<tr><th>服务内容</th><td colspan=6>" + data.serviceContent
				+ "</td></tr>";
		row += "<tr><th>邮箱认证</th><td colspan=6>" + data.checkEmail
				+ "</td></tr>";
		row += "<tr><th>手机认证</th><td colspan=6>" + data.checkPhone
				+ "</td></tr>";
		row += "<tr><th>学历认证</th><td colspan=6>" + data.checkDegree
				+ "</td></tr>";
		row += "<tr><th>身份认证</th><td colspan=6>" + data.checkIDCard
				+ "</td></tr>";
		row += "<tr><th>工作认证</th><td colspan=6>" + data.checkWork
				+ "</td></tr>";
		row += "<tr><th rowspan="+workCount+">工作经验</th>"
				+ processWork(data.workExperience);
		row += "<tr><th rowspan="+studyCount+">学习经验</th>"
				+ processStudy(data.studyExperience);
		row += "<tr><th rowspan="+tipsCount+">标签</th>" + processTips(data.tips);
		//d.toLocaleString()
		//时间
		$("#infoTable").append(row);

	};
	$(function() {
		$
				.ajax({
					type : "POST",
					url : "http://service.1yingli.cn/yiyingliService/manage",
					data : "{'style':'manager','method':'getTeacherAllInfo','teacherId': '"
							+ tid + "'}",
					success : function(data, status) {
						//alert(data);
						var result = eval("(" + data + ")");
						changeTable(result)
					}
				});

	})
</script>
<title>导师详情</title>
</head>
<body>
	<table id="infoTable" border="1"></table>
	<div id="sound">
</body>
</html>