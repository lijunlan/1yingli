<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>急急急急急急</title>
<script type="text/javascript" src="jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="ajax-pushlet-client.js"></script>
<script>
	

	$(document).ready(function() {

		$("button").click(function() {
			var info=$("textarea").text();
			alert(getText());
			$.ajax({
				type:"POST",
				url:"/NotificationService/noti", 
				data:"{'data': '"+encodeURIComponent($("textarea").text())+"','castType':'UNI','uid':'001'}",
				success:function(data, status) {
					alert("Data: " + data + "\nStatus: " + status);
				}
			});
		

		});
	});
	
	function getText(){
		return $("textarea").text();
	}
</script>
</head>
<body>
	<textarea id=input style="height: 60px">hehe</textarea>
	<button id=go>go</button>
</body>

</html>