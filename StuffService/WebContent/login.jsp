<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.cookie.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/bootstrap-responsive.min.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/StuffService-common.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/buttons.css">
<script type="text/javascript">
	function go() {
		if (document.getElementById("username").value == ""
				|| document.getElementById("password").value == "") {
			$('#modalMsg').text("用戶名和密碼不能為空")
			$('#modal-container-390575').modal('show')
			return;
		}
		$.ajax({
			type : "POST",
			url : "/StuffService/manageLogin",
			data : "{'username': '" + document.getElementById("username").value
					+ "','password':'"
					+ document.getElementById("password").value + "'}",
			success : function(data, status) {
				if (data.indexOf("fail") >= 0) {
					result = eval("(" + data + ")")
					$("#username").val("")
					$("#password").val("")
					$('#modalMsg').text(result.data)
					$('#modal-container-390575').modal('show')
					return;
				} else {
					$.cookie('mid', data);
					self.location = serverUrl + "StuffService/index.jsp";
				}

			}
		});
	}
</script>
</head>
<body>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span4"></div>
			<div class="span4">
				<fieldset>
					<legend>管理员登录</legend>
					<input placeholder="用户名" id="username" type="text" /> <br> <input
						placeholder="密码" type="password" size="30" name="password"
						id="password" /><br>
					<button class="button button-glow button-border button-rounded button-primary" id="login" onclick="go()">提交</button>
				</fieldset>

				<div id="modal-container-390575" class="modal hide fade"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h3 id="myModalLabel">出错</h3>
					</div>
					<div class="modal-body">
						<p id="modalMsg"></p>
					</div>
					<div class="modal-footer">
						<button class="btn btn-danger" data-dismiss="modal" aria-hidden="true">关闭</button>
					</div>
				</div>
			</div>
			<div class="span4"></div>
		</div>
	</div>
</body>
</html>