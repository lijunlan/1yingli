/**
 * 
 */
var json;
var mid;
checkLogin();
registNotify();

var t1 = self.location.href;
console.log(t1);
var t2 = t1.toString().split("?");
if(t2.length>1){
 	var t3 = t2[1].split("=");
 	if(t3.length>1){
 		var t4 = t3[1];
 		var t5 = decodeURI(t4);
 		json = JSON.parse(t5);
 	}
}

//图片上传
$(document).ready(function () {
	$("#select-picture-src").uploadifive({
		'buttonText': '浏览',
		'buttonClass': 'file-box',
		'queueID': 'imagelist',
		'auto': false,
		'method': 'post',
		'fileType': 'image/*',
		'queueSizeLimit': 10,
		'multi': false,
		'uploadScript': 'http://service.1yingli.cn/yiyingliService/upimage',
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
		'onUploadComplete': function (file, data) {
			//alert('The file ' + file.name + ' uploaded successfully.');
			var json = eval("(" + data + ")");
			if (json.state == "success") {
				$("#imagelist").fadeOut(3000);
				$("#iconUrl").val(json.url);
				$("#littleIcon").attr('src',json.url);
				Messenger().post("背景图片添加完成");
			} else {
				$(".mark").show();
				$("#box").show();
				$("#bomb").html("上传信息失败");
				$("#connect").attr('href', 'general.html');
			}
		}
	});
});

var changeTable = function (result) {
	$("#pageKey").val(result.key);
	$("#teamName").val(result.description);
	$("#iconUrl").val(result.img);
	$("#contact").val(result.contact);
	$("#email").val(result.email);
	$("#content").val(result.content);
};

$(function(){
	if(json==null){
		alert("error");
	}else{
		changeTable(json);
	}
});


var edit = function (){
	var key =  $("#pageKey").val();
	var description = $("#teamName").val();
	var content = $("#iconUrl").val();
	var img = $("#contact").val();
	var email = $("#email").val();
	var contact = $("#content").val();
	myJson.pagesId = json.pagesId;
	myJson.key = key;
	myJson.description = description;
	myJson.content = content;
	myJson.img = img;
	myJson.email = email;
	myJson.contact = contact;
	myJson.method = "editActivity";
	myAjax(myJson, null);
	Messenger().post("修改成功");
}



