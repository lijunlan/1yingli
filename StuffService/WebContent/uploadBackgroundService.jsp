<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>上传背景图片</title>
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
	href="<%=request.getContextPath()%>/css/uploadifive.css">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.uploadifive.min.js"></script>
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

<script type="text/javascript">
	var mid;
	checkLogin()
	registNotify()
	page=1
	var getList=function(){
		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style':'manager','method':'getBackgroundList','mid':'" + mid
					+ "','page':'" + page + "'}",
			success : function(data, status) {
				var result = eval("(" + data + ")");
				if (result.msg == "manager is not existed") {
					$('#modal-container-390572').modal('show')
					return
				}
				if(result.state=="success"){
					changeTable(result.data)
				} 
			}
		})
	}
	var removePic =function(id){
		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style':'manager','method':'removeBackground','mid':'" + mid 
					+ "','id':'" + id + "'}",
			success : function(data, status) {
				alert(data)
				var result = eval("(" + data + ")");
				if (result.msg == "manager is not existed") {
					$('#modal-container-390572').modal('show')
					return
				}
				if(result.state=="success"){
					Messenger().post("背景图片删除完成")
					getList()
				} 
			}
		})
	}
	var changeTable=function(info){
		$("#backgroundTable").empty();
		$("#backgroundTable")
				.append(
						"<thead><tr><th>操作</th><th>图片</th></tr></thead><tbody>");
		var list = eval("(" + info + ")");
		$.each(list, function(index, data) {
			var row = "<tr><td>"
			row += data.id + "<br><button class=\"btn\" onclick=\" removePic("+data.id+")  \">删除</button></td><td>";
			row += "<img alt=\" \" \" src=\""+data.url+"\" /></td></tr>";
			$("#backgroundTable").append(row);
		})
		$("#backgroundTable").append("</tbody>")
	}
	var addBackground = function(url)
	{
		$.ajax({
			type : "POST",
			url : "http://service.1yingli.cn/yiyingliService/manage",
			data : "{'style':'manager','method':'addBackground','mid':'" + mid
					+ "','url':'" + url + "'}",
			success : function(data, status) {
				var result = eval("(" + data + ")");
				if (result.msg == "manager is not existed") {
					$('#modal-container-390572').modal('show')
					return
				}
				if(result.state=="success"){
					Messenger().post("背景图片添加完成")
				} 
			}
		})
	}
	
	$(function() {
		getList()
		if(page==1){
			document.getElementById("lastPage").disabled = true;
		}
		document
		.getElementById("pageInput").value = page;
	})
	
	$(document)
			.ready(
					function() {

						$("#get").click(function(){
							page=document.getElementById("pageInput").value
							getList()
						})
						$("button#lastPage")
						.click(
								function() {
									page--;
									document
									.getElementById("pageInput").value = page;
									if (page <= 1)
										document.getElementById("lastPage").disabled = true;
									getList()
									
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
											getList()
											
										});
						$("#return").click(
								function() {
									self.location = serverUrl
											+ "StuffService/index.jsp";
						})
						$("#select-picture-src")
								.uploadifive(
										{
											'buttonText' : '浏览',
											'buttonClass' : 'file-box',
											'queueID' : 'imagelist',
											'auto' : false,
											'method' : 'post',
											'fileType' : 'image/*',
											'queueSizeLimit' : 10,
											'multi' : false,
											'uploadScript' : 'http://service.1yingli.cn/yiyingliService/upbg',
											'formData' : {

											},
											'onInit' : function() {
												//alert('Add files to the queue to start uploading.');
											},
											'onFallback' : function() {
												alert('Oops!  You have to use the non-HTML5 file uploader.');
											},
											'onSelect' : function(queue) {
												//alert(queue.queued + ' files were added to the queue.');
												$("#imagelist").fadeIn()
											},
											'onUpload' : function(filesToUpload) {
												// alert(filesToUpload + ' files will be uploaded.');
											},
											'onError' : function(errorType) {
												//alert('The error was: ' + errorType);
											},
											'onUploadComplete' : function(file,
													data) {
												//alert('The file ' + file.name + ' uploaded successfully.');
												var json = eval("(" + data
														+ ")");
												if (json.state == "success") {
													$("#imagelist").fadeOut(
															3000);
													addBackground(json.url)
												} else {
													$(".mark").show();
													$("#box").show();
													$("#bomb").html("上传信息失败");
													$("#connect").attr('href',
															'general.html');
												}
											}
										});
					})
					
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<blockquote>
					<p>上传背景图片</p>
					<a id="select-picture-src" class="btn"></a> <a class="btn"
						id="submit"
						href="javascript:$('#select-picture-src').uploadifive('upload')">上传</a>
					<div id="imagelist" style="min-height: 10px; max-height: 55px"></div>
				</blockquote>
			</div>
			<div class="span10">
				<button id="return">返回功能选择</button>
				<button id="get">获取列表</button>
				<br>
				<button id="lastPage">上一页</button>
				<input id="pageInput"></input>
				<button id="nextPage">下一页</button>
				<table class="table" id="backgroundTable">
					<thead>
						<tr>
							<th>操作</th>
							<th>图片</th>
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