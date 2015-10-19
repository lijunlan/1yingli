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
	src="<%=request.getContextPath()%>/js/StuffService-common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/messenger.min.js"></script>
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
	var mid;
	checkLogin()
	registNotify()
	$(document).ready(function() {
		$("button#orderQuery").click(function() {
			self.location = serverUrl + "StuffService/orderService.jsp";
		})
		$("button#teacherQuery").click(function() {
			self.location = serverUrl + "StuffService/teacherService.jsp";
		})
		$("button#customerQuery").click(function() {
			self.location = serverUrl + "StuffService/customerService.jsp";
		})
		$("button#teacherApplyProcess").click(function() {
			self.location = serverUrl + "StuffService/manage_application.html";
		})
		$("button#createVoucher").click(function() {
			self.location = serverUrl + "StuffService/voucherService.jsp";
		})
		$("button#backgroundService").click(function() {
			self.location = serverUrl + "StuffService/uploadBackgroundService.jsp";
		})

	})
</script>
<title>功能选择</title>
</head>
<body>
	<button id="orderQuery" class="button button-3d button-primary button-rounded">订单查询</button>
	<button id="teacherQuery" class="button button-3d button-caution">导师查询</button>
	<button id="customerQuery" class="button button-3d button-royal">客户查询</button>
	<button id="teacherApplyProcess" class="button button-3d button-action button-pill">导师申请处理</button>
	<button id="createVoucher" class="button button-3d button-primary button-rounded">生成优惠券</button>
	<button id="backgroundService" class="button button-3d button-caution">背景图片管理</button>
	<div id="sound"></div>
</body>
</html>