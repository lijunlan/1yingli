//初始化
var mid;
var WorkExpList = new Array();
var StudyExpList = new Array();
checkLogin();
registNotify();
var editor = CKEDITOR.replace( 'introduce' );

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
	var url = window.location.href;
	var attri = url.split("?")[1];
	if(attri!=null){
		var key = attri.split("=")[0];
		var value = attri.split("=")[1];
		if(key=="username"&&value!=null){
			$("#inputSearchUsername").val(value);
			get();
		}
	}
	getBgList();
});

function getBgList(){
	myJson.method = "getBackgroundList";
	delete myJson.page;
	myAjax(myJson,listBg);
}

function listBg(bg){
	var bglist = bg.data;
	var container = $("#container");
	container.empty();
	$.each(bglist,function(index,content){
		var html  =  "<div><a href=\"javascript:selectPic('"+content.url+"');\"><img style=\"width:400px;\" src=\""+content.url+"\"/></a></div><br>";
		container.append(html);
	});
}

function selectBg(){
	$("#modalBgList").modal();
}

function selectPic(bgUrl){
	$("#bgUrl").val(bgUrl);
	$("#modalBgList").modal('close');
}

//注册导师
function regist() {
	//myJson中mid和style是不变的，因此在StuffService-common.js中统一赋值
	myJson.method = "registerUser";
	myJson.username = $("#inputRegistUsername").val();
	myJson.password = encryptedStr($("#inputRegistPassword").val(), publickey);
	myJson.nickName = $("#inputRegistNickname").val();
	myAjax(myJson, function(){});
	//Messenger().post("操作成功");
}

//获取导师信息
function get() {
	if ($("#inputSearchUsername").val() == "") {
		return;
	}
	myJson.method = "getTeacherInfo";
	myJson.username = $("#inputSearchUsername").val();
	myAjax(myJson, getAndParse);
}

//解析导师信息，并显示
function getAndParse(t) {
	if (t.teacher != null) {
		t = t.teacher;
	}
	$("#username").val($("#inputSearchUsername").val());
	$("#simpleinfo").val(t.simpleinfo);
	$("#name").val(t.name);
	$("#phone").val(t.phone);
	$("#address").val(t.address);
	$("#email").val(t.email);
	$("#iconUrl").val(t.iconUrl);
	$("#bgUrl").val(t.bgUrl);
	$("#littleIcon").attr('src',t.iconUrl);
	$("#serviceTitle").val(t.topic);
	$("#activityDes").val(t.activityDes);
	$("#mileValue").val(t.mile);
	//兼容不同版本api
	t.price == null ? $("#servicePrice").val(t.servicePrice) : $("#servicePrice").val(t.price);
	//
	//$("#introduce").val(t.introduce);
	editor.setData(t.introduce);
	WorkExpList = t.workExperience;
	$("#showWorkExp").val($.toJSON(WorkExpList));
	StudyExpList = t.studyExperience;
	$("#showStudyExp").val($.toJSON(StudyExpList));
	$("[name='checkbox']").uCheck('uncheck');
	$.each(t.tips, function (index, data) {
		$("#checkbox" + data.id).uCheck('check');
	});
	t.checkPhone == 'true' ? $("#checkPhone").uCheck('check') : $("#checkPhone").uCheck('uncheck');
	t.checkEmail == 'true' ? $("#checkEmail").uCheck('check') : $("#checkEmail").uCheck('uncheck');
	t.checkIDCard == 'true' ? $("#checkId").uCheck('check') : $("#checkId").uCheck('uncheck');
	t.checkWork == 'true' ? $("#checkJob").uCheck('check') : $("#checkJob").uCheck('uncheck');
	//兼容不同版本api
	t.checkDegree == null ? checkEducation = t.checkStudy : checkEducation = t.checkDegree;
	checkEducation == 'true' ? $("#checkEducation").uCheck('check') : $("#checkEducation").uCheck('uncheck');

	$("#showWeight1").val(t.showWeight1);
	$("#showWeight2").val(t.showWeight2);
	$("#showWeight4").val(t.showWeight4);
	$("#showWeight8").val(t.showWeight8);
	$("#showWeight16").val(t.showWeight16);
	$("#homeWeight").val(t.homeWeight);
	$("#saleWeight").val(t.saleWeight);
	$('#actionDiv').bootstrapSwitch('setState', false)
	t.onService == 'true' ? $('#onServiceDiv').bootstrapSwitch('setState', true) : $('#onServiceDiv').bootstrapSwitch('setState', false);
	t.onChat == 'true' ? $('#onChatDiv').bootstrapSwitch('setState', true) : $('#onChatDiv').bootstrapSwitch('setState', false);
	Messenger().post("加载完成");
}

//增加工作经历并显示
function addWorkExp() {
	$("#workExpModal").modal({
		relatedTarget: this,
		onConfirm: function (e) {
			var workExp = new Object();
			workExp.companyName = $("#inputCompany").val();
			workExp.position = $("#inputPosition").val();
			workExp.startTime = $("#inputWorkStartTime").val();
			workExp.endTime = $("#inputWorkEndTime").val();
			workExp.description = $("#textWorkExp").val();
			WorkExpList.push(workExp);
			$("#showWorkExp").val($.toJSON(WorkExpList));
			$("#inputCompany").val("");
			$("#inputPosition").val("");
			$("#inputWorkStartTime").val("");
			$("#inputWorkEndTime").val("");
			$("#textWorkExp").val("");
		}
	})
}

//删除最后一条工作经历
function deleteWorkExp() {
	WorkExpList.pop();
	$("#showWorkExp").val($.toJSON(WorkExpList));
}

//增加学习经历并显示
function addStudyExp() {
	$("#studyExpModal").modal({
		relatedTarget: this,
		onConfirm: function (e) {
			var studyExp = new Object();
			studyExp.schoolName = $("#inputSchool").val();
			studyExp.degree = $("#inputDegree").val();
			studyExp.major = $("#inputMajor").val();
			studyExp.startTime = $("#inputStudyStartTime").val();
			studyExp.endTime = $("#inputStudyEndTime").val();
			studyExp.description = $("#textStudyExp").val();
			StudyExpList.push(studyExp);
			$("#showStudyExp").val($.toJSON(StudyExpList));
			$("#inputSchool").val("");
			$("#inputDegree").val("");
			$("#inputMajor").val("");
			$("#inputStudyStartTime").val("");
			$("#inputStudyEndTime").val("");
			$("#textStudyExp").val("");
		}
	})
}

//删除上一条工作经历
function deleteStudyExp() {
	StudyExpList.pop();
	$("#showStudyExp").val($.toJSON(StudyExpList));
}

//提交保存
function submit() {
	var teacher = new Object();
	var tips = new Array();
	var send = new Object();
	var tmp;

	teacher.name = $("#name").val();
	teacher.simpleinfo = $("#simpleinfo").val();
	teacher.phone = $("#phone").val();
	teacher.address = $("#address").val();
	teacher.email = $("#email").val();
	teacher.iconUrl = $("#iconUrl").val();
	teacher.bgUrl = $("#bgUrl").val();
	teacher.email = $("#email").val();
	teacher.introduce = CKEDITOR.instances.introduce.getData();
	teacher.workExperience = WorkExpList;
	teacher.studyExperience = StudyExpList;

	teacher.topic = $("#serviceTitle").val();
	teacher.price = $("#servicePrice").val();
	teacher.activityDes = $("#activityDes").val();

	var tmpId = 1 / 2;
	var i;
	//就当前标签数量而言，一共循环12次，如果需要再做修改
	for (i = 1; i < 13; i++) {
		tmpId = tmpId * 2;
		if ($("#checkbox" + tmpId).prop("checked")) {
			tmp = new Object();
			tmp.id = tmpId.toString();
			tips.push(tmp);
		}
	}

	teacher.tips = tips;

	teacher.checkEmail = $("#checkEmail").prop("checked") ? "true" : "false";
	teacher.checkPhone = $("#checkPhone").prop("checked") ? "true" : "false";
	teacher.checkWork = $("#checkJob").prop("checked") ? "true" : "false";
	teacher.checkStudy = $("#checkEducation").prop("checked") ? "true" : "false";
	teacher.checkIDCard = $("#checkId").prop("checked") ? "true" : "false";
	teacher.showWeight1 = $("#showWeight1").val();
	teacher.showWeight2 = $("#showWeight2").val();
	teacher.showWeight4 = $("#showWeight4").val();
	teacher.showWeight8 = $("#showWeight8").val();
	teacher.showWeight16 = $("#showWeight16").val();
	teacher.homeWeight = $("#homeWeight").val();
	teacher.saleWeight = $("#saleWeight").val();
	teacher.mile = $('#mileValue').val();
	teacher.onService = document.getElementById('onService').checked.toString();
	teacher.onChat = document.getElementById('onChat').checked.toString();
	
	send.teacher = teacher;
	send.style = "manager";

	if(document.getElementById('action').checked){
		send.method = "createTeacher";
	}else{
		send.method = "editTeacher";
	}
	
	send.mid = mid;
	send.username = $("#username").val();

	myAjax(send, null);
	Messenger().post("操作成功");
}

function changeUsername(){
	myJson.method = "changeUserName";
	myJson.oldusername = $("#oldusername").val();
	myJson.newusername = $("#newusername").val();
	myAjax(myJson,null);
	Messenger().post("修改用户名成功");
}

function clean() {
	window.location.reload();
}