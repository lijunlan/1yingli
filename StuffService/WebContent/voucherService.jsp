<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>优惠券管理</title>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/jquery.datetimepicker.css">

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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.datetimepicker.js"></script>


<script type="text/javascript">
	var mid;
	var page = 1
	checkLogin()
	registNotify()
	var changeTable = function(info) {
		$("#voucherTable").empty();
		$("#voucherTable")
				.append(
						"<thead><tr><th>ID</th><th>创建时间</th><th>优惠金额</th><th>优惠券验证码</th>"+
						"<th>开始时间</th><th>结束时间</th><th>订单ID</th>"+
						"<th>是否使用</th></tr></thead><tbody>");
		var list = eval("(" + info + ")");
		$.each(list, function(index, data) {
			var createTime = parseInt(data.createTime, 10)
			var startTime = parseInt(data.startTime, 10)
			var endTime = parseInt(data.endTime, 10)
			var createTime = new Date(createTime)
			var startTime = new Date(startTime)
			var endTime = new Date(endTime)
			var row = "<tr><td>"
			row += data.id + "</td><td>";
			row += createTime.toLocaleString() + "</td><td>";
			row += data.money + "</td><td>";
			row += data.number + "</td><td>"
			row += startTime.toLocaleString() + "</td><td>";
			row += endTime.toLocaleString() + "</td><td>";
			row += data.userId + "</td><td>";
			row += data.used + "</td>";
			$("#voucherTable").append(row);
		})
		$("#voucherTable").append("</tbody>")
	};
	var getList=function(){
		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style':'manager','method':'getVoucherLsit','mid': '" + mid
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
	}
	$(function() {
		$('#chooseBeginTime').datetimepicker({
			format : "Y,m,d,H,i", //格式化日期
			todayButton : true, //关闭选择今天按钮
			step : 1
		});
		$('#chooseEndTime').datetimepicker({
			format : "Y,m,d,H,i", //格式化日期
			todayButton : true, //关闭选择今天按钮
			step : 1
		});
		document.getElementById("lastPage").disabled = true;
		document.getElementById("pageInput").value = page;
		getList()
		if (page == 1)
			document.getElementById("lastPage").disabled = true;
		document.getElementById("pageInput").value = page;
	});
	$(document).ready(function() {
		$("#submit").click(function(){
			var beginDate=$('#chooseBeginTime').val()
			var endDate=$('#chooseEndTime').val()
			process1=beginDate.split(',')
			process2=endDate.split(',')
			var date1=new Date(process1[0],Number(process1[1])-1,process1[2],process1[3],process1[4],0)
			var date2=new Date(process1[0],Number(process2[1])-1,process2[2],process2[3],process2[4],0)
			var time1=date1.getTime();
			var time2=date2.getTime();
			var money=$('#inputMoney').val()
			var count=$('#inputCount').val()
			if(isNaN(money)||money<=0){
				alert("所填写金额不正确")
				return
			}
			if(isNaN(count)||count<=0){
				alert("所填写数量不正确")
				return
			}
			if(count.toString().indexOf('.')!=-1){
				alert("所填写数量不正确")
				return
			}
			if(time1>=time2||beginDate==""||endDate==""){
				alert("所填写时间不正确")
				return
			}
			$.ajax({
				type : "POST",
				url : "http://service.1yingli.cn/yiyingliService/manage",
				data : "{'style':'manager','method':'createVoucher','mid': '" + $.cookie('mid')
						+ "','money':'" + money + "','count':'"+count+"','endTime':'"+time2+"','startTime':'"+time1+"'}",
				success : function(data, status) {
					var result = eval("(" + data + ")");
					if (result.msg == "manager is not existed") {
						$('#modal-container-390572').modal('show')
						return
					}
					alert(data)
				}
			});
		})
		$("#return").click(
								function() {
									self.location = serverUrl
											+ "StuffService/index.jsp";
								})
		$("#lastPage").click(function(){
			page--
			document.getElementById("pageInput").value = page;
			if(page<=1){
				document.getElementById("lastPage").disabled = true;
			}
			getList()
		})
		$("#nextPage").click(function(){
			page++
			document.getElementById("pageInput").value = page;
			if(page>1){
				document.getElementById("lastPage").disabled = false;
			}
			getList()
		})
		$("#get").click(function(){
			page=document.getElementById("pageInput").value;
			if(page>1){
				document.getElementById("lastPage").disabled = false;
			}else{
				document.getElementById("lastPage").disabled = true;
			}
			getList()
		})
	});
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<fieldset>
					<legend>创建新的优惠券</legend>
					<span class="help-block">填写优惠的金额</span><input id="inputMoney"
						type="text" placeholder="优惠金额" /> <span class="help-block">填写优惠券数量</span><input
						id="inputCount" type="text" placeholder="优惠券数量" /> <span
						class="help-block">填写优惠券使用开始时间</span><input type="text"
						placeholder="优惠开始时间" id="chooseBeginTime" /> <span
						class="help-block">填写优惠券使用截止时间</span><input type="text"
						placeholder="优惠截止时间" id="chooseEndTime" />
					<button class="btn" id="submit">创建</button>
				</fieldset>
			</div>
			<div class="span10">
				<button id="return">返回功能选择</button>
				<button id="get">获取列表</button>
				<br>
				<button id="lastPage">上一页</button>
				<input id="pageInput"></input>
				<button id="nextPage">下一页</button>
				<table class="table" id="voucherTable">
					<thead>
						<tr>
							<th>ID</th>
							<th>创建时间</th>
							<th>优惠金额</th>
							<th>优惠券验证码</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>订单ID</th>
							<th>是否使用</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</div>
		</div>
	</div>

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