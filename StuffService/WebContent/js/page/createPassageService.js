//初始化
var mid;
checkLogin();
registNotify();
var editor = CKEDITOR.replace( 'content' );

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
				var temp = json.url.split("@!")[0]+"@!cover";
				$("#imagelist").fadeOut(3000);
				$("#iconUrl").val(temp);
				$("#littleIcon").attr('src',temp);
				Messenger().post("图片添加完成");
			} else {
				$(".mark").show();
				$("#box").show();
				$("#bomb").html("上传信息失败");
				$("#connect").attr('href', 'general.html');
			}
		}
	});
});


//获取导师信息
function get() {
	if ($("#inputSearchPassageId").val() == "") {
		return;
	}
	myJson.method = "getPassage";
	myJson.passageId = $("#inputSearchPassageId").val();
	myAjax(myJson, getAndParse);
}

//解析导师信息，并显示
function getAndParse(data) {
	var p = data.passage;
	$("#title").val(p.title);
	//$("#content").html(p.content);
	editor.setData(p.content);
	$("#summary").val(p.summary);
	$("#tag").val(p.tag);	
	$("#iconUrl").val(p.imageUrl);
	$("#passageId").val(p.passageId);
	p.onshow == 'true' ? $('#onShowDiv').bootstrapSwitch('setState', true) : $('#onShowDiv').bootstrapSwitch('setState', false);
	p.onReward == 'true' ? $('#onRewardDiv').bootstrapSwitch('setState', true) : $('#onRewardDiv').bootstrapSwitch('setState', false);
	Messenger().post("加载完成");
}

function edit() {
	var send = new Object();

	send.style = "manager";
	send.mid =mid;
	send.method ="editPassage";
	send.passageId=$("#passageId").val();
	send.title=$("#title").val();
	send.tag=$("#tag").val();
	send.content=CKEDITOR.instances.content.getData();
	//$("#content").val();
	send.imageUrl=$("#iconUrl").val();
	send.summary=$("#summary").val();
	send.onshow=document.getElementById('onShow').checked.toString();
	send.onReward = document.getElementById('onReward').checked.toString();

	myAjax(send, null)
	Messenger().post("操作成功");
}


//提交保存
function save() {
	if($("#username").val()==null){
		return;
	}
	var send = new Object();

	send.style = "manager";
	send.mid =mid;
	send.method ="createPassage";
	send.username=$("#username").val();
	send.title=$("#title").val();
	send.tag=$("#tag").val();
	send.content=CKEDITOR.instances.content.getData();
	//$("#content").val();
	send.imageUrl=$("#iconUrl").val();
	send.summary=$("#summary").val();
	
	myAjax(send, null)
	Messenger().post("操作成功");
}