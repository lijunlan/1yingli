//初始化
var mid;
var imageUrls="";
checkLogin();
registNotify();

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
				//TODO 
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


//获取导师信息
function get() {
	if ($("#inputSearchServiceID").val() == "") {
		return;
	}
	myJson.method = "getServicePro";
	myJson.serviceProId = $("#inputSearchServiceID").val();
	myAjax(myJson, getAndParse);
}

//解析导师信息，并显示
function getAndParse(servicePro) {
	if (servicePro.teacher != null) {
		servicePro = servicePro.teacher;
	}
	
	//TODO
	$("#iconUrl").val(servicePro.imageUrls);
	$("#littleIcon").attr('src',servicePro.imageUrls);
	imageUrls=servicePro.imageUrls;


	$("#title").val(servicePro.title);
	//兼容不同版本api
	$("#price").val(servicePro.price);
	$("#summary").val(servicePro.summary);
	$("#count").val(servicePro.count);
	$("#numeral").val(servicePro.numeral);
	$("#quantifier").val(servicePro.quantifier);
	$("#content").val(servicePro.content);
	
	$("[name='radio']").uCheck('uncheck');
	var kind = servicePro.kind;
	$("#radio"+kind).uCheck('check');

	$("[name='checkbox1']").uCheck('uncheck');
	$("[name='checkbox2']").uCheck('uncheck');
	$("[name='checkbox3']").uCheck('uncheck');
	$("[name='checkbox4']").uCheck('uncheck');
	$("[name='checkbox5']").uCheck('uncheck');
	var tip = servicePro.tip.split(",");
	$.each(tip, function (index, data) {
		$("#"+servicePro.kind+"checkbox" + data).uCheck('check');
	});
	
	
	$("#pricetemp").val(servicePro.priceTemp);
	$("#freeTime").val(servicePro.freeTime);
	
	
	
	$("#showWeight1").val(servicePro.showWeight1);
	$("#showWeight2").val(servicePro.showWeight2);
	$("#showWeight3").val(servicePro.showWeight3);
	$("#showWeight4").val(servicePro.showWeight4);
	$("#showWeight5").val(servicePro.showWeight5);
	$("#homeWeight").val(servicePro.homeWeight);
	$("#saleWeight").val(servicePro.saleWeight);
	$('#actionDiv').bootstrapSwitch('setState', false)
	servicePro.onshow == 'true' ? $('#onShowDiv').bootstrapSwitch('setState', true) : $('#onShowDiv').bootstrapSwitch('setState', false);
	servicePro.onsale == 'true' ? $('#onSaleDiv').bootstrapSwitch('setState', true) : $('#onSaleDiv').bootstrapSwitch('setState', false);
	Messenger().post("加载完成");
}

//提交保存
function submit() {
	var servicePro = new Object();
	var send = new Object();
	var tmp;

	servicePro.count=$("#count").val();
	servicePro.price=$("#price").val();
	servicePro.priceTemp=$("#pricetemp").val();
	servicePro.numeral=$("#numeral").val();

	servicePro.kind=$("[name='radio'][checked]").val();
	var tip="";
	$.each($("[name='checkbox"+servicePro.kind+"'][checked]"),function(index,data){
		if(index!=0){
			tip = tip+","+data.val();
		}else{
			tip = data.val();
		}
	});
	servicePro.tip=tip;
	

	servicePro.onshow=document.getElementById('onShow').checked.toString();
	servicePro.onsale=document.getElementById('onSale').checked.toString();
	servicePro.quantifier=$("#quantifier").val();
	servicePro.title=$("#title").val();
	servicePro.content=$("#content").val();
	servicePro.summary=$("#summary").val();
	servicePro.imageUrls=imageUrls;

	servicePro.saleWeight=$("#saleWeight").val();
	servicePro.homeWeight=$("#homeWeight").val();
	servicePro.freeTime=$("#freeTime").val();

	servicePro.showWeight1 = $("#showWeight1").val();
	servicePro.showWeight2 = $("#showWeight2").val();
	servicePro.showWeight3 = $("#showWeight3").val();
	servicePro.showWeight4 = $("#showWeight4").val();
	servicePro.showWeight5 = $("#showWeight5").val();
		
	send.style = "manager";
	send.servicePro= servicePro;

	if(document.getElementById('action').checked){
		send.method = "createServicePro";
		send.username = $("#username").val();
	}else{
		send.method = "editServicePro";
		send.serviceProId=$("#inputSearchServiceID").val();
	}
	
	send.mid = mid;

	myAjax(send, null)
	Messenger().post("操作成功");
}

function clean() {
	window.location.reload();
}