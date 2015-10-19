<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ch">
<head>
<meta charset="utf-8">
<title>一英里</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,maximum-scale=1">
<meta name="description" content="1.0">
<meta name="author" content="sdll18">
<script type="text/javascript" src="jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="ajax-pushlet-client.js"></script>

<script type="text/javascript">
	//PL._init(); 
	//PL.join();
	//PL.listen();
	//PL._init();
	PL.uid="001";
	//注册标签
	PL.joinListen("/yiyingli/notifi");
	//接收到服务器数据的回调函数
	function onData(event) {
		var json = event.get("dataJson");
		//判断是否是Ping
		if (json != null) {
			//处理返回的json
			//先进行URLDECODE
			json = decodeURIComponent(json);
			//进行VIEW改变
			$("#text").html($("#text").html() + "\r\n" + json);
		}
		//alert(event.get("data")); 

	}
</script>
</head>
<body>
	<h1>my first pushlet</h1>
	<textarea id="text" style="height: 600px"></textarea>
</body>
</html>