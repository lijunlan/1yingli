<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("button").click(function() {
			$.ajax({
				type : "POST",
				url : "/TimeTasker/taskReceive",
				data : "{'kind':'order','action':'remove','endTime':'001'}",
				success : function(data, status) {
					alert("Data: " + data + "\nStatus: " + status);
				}
			});
		})
	})
</script>
</head>
<body>
	<button>hehe</button>
</body>
</html>