var mid;
checkLogin();
registNotify();
page = 1;
function getList() {
	myJson.method = "getBackgroundList";
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
}
function removePic(id) {
	myJson.method = "removeBackground";
	myJson.id = id.toString();
	myAjax(myJson, get);
}
function changeTable(result) {
	$("#backgroundTable").empty();
	$("#backgroundTable").append("<thead><tr><th>操作</th><th>图片</th></tr></thead><tbody>");
	var list = result.data;
	$.each(list, function (index, data) {
		var row = "<tr id='" + data.id + "'><td>";
		row += data.id
		+ "<br><button class='am-btn am-btn-secondary' onclick='removePic("
		+ data.id + ")'>删除</button></td><td>";
		row += "<img src='" + data.url + "' /></td></tr>";
		$("#backgroundTable").append(row);
	})
	$("#backgroundTable").append("</tbody>");
}
function addBackground(url) {
	myJson.method = "addBackground";
	myJson.url = url.toString();
	myAjax(myJson, get);
}

$(function () {
	getList();
	if (page == 1) {
		document.getElementById("lastPage").disabled = true;
	}
	document.getElementById("pageInput").value = page;
})
function changePage(action) {
	if (action == "last") {
		page--;
	} else if (action == "next") {
		page++;
	}
	document.getElementById("pageInput").value = page;
	if (page <= 1)
		document.getElementById("lastPage").disabled = true;
	else
		document.getElementById("lastPage").disabled = false;
	getList();
}
function get() {
	page = document.getElementById("pageInput").value;
	if (page <= 1)
		document.getElementById("lastPage").disabled = true;
	else
		document.getElementById("lastPage").disabled = false;
	getList();
}
$(document).ready(
	function () {
		$("#select-picture-src").uploadifive({
			'buttonText': '浏览',
			'buttonClass': 'file-box',
			'queueID': 'imagelist',
			'auto': false,
			'method': 'post',
			'fileType': 'image/*',
			'queueSizeLimit': 10,
			'multi': false,
			'uploadScript': 'http://service.1yingli.cn/yiyingliService/upbg',
			'formData': {

			},
			'onInit': function () {
				//alert('Add files to the queue to start uploading.');
			},
			'onFallback': function () {
				alert('Oops!  You have to use the non-HTML5 file uploader.');
			},
			'onSelect': function (queue) {
				//alert(queue.queued + ' files were added to the queue.');
				$("#imagelist").fadeIn();
			},
			'onUpload': function (
				filesToUpload) {
				// alert(filesToUpload + ' files will be uploaded.');
			},
			'onError': function (errorType) {
				//alert('The error was: ' + errorType);
			},
			'onUploadComplete': function (
				file, data) {
				//alert('The file ' + file.name + ' uploaded successfully.');
				var json = eval("(" + data + ")");
				if (json.state == "success") {
					$("#imagelist")
						.fadeOut(3000);
					addBackground(json.url);
				} else {
					$(".mark").show();
					$("#box").show();
					$("#bomb").html(
						"上传信息失败");
					$("#connect").attr(
						'href',
						'general.html');
				}
			}
		});
	})