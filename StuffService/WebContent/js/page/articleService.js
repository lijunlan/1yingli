var mid;
checkLogin();
registNotify();

var checkPage = 1;
var auditPage = 1;
//两个部分页数分别记录
var page = 1;
//fun == function,用于区别两个部分，check和audit
var fun = 'check';
//记录当前打开的面板，用于后面关上
var collapse = '';

//at first
page = checkPage;
$("#pageInput").val(page);

//在页面开始的时候干的事情
$(function () {
	get();
	document.getElementById("lastPage").disabled = true;
	//设置toolbar
	high = window.screen.availHeight * 1 / 11;
	$('#toolBar').css('top', high);
})

//toolbar的检测滚动
$(window).scroll(function () {
	if ($(window).scrollTop() != 0) {
		$("#toolBar").css('display', 'block');
		$("#toolBar").addClass('am-animation-fade');
	} else {
		$("#toolBar").css('display', 'none');
	}
})

//点击不同tab时，触发事件
$('#doc-tab').find('a').on('opened.tabs.amui', function (e) {
	if ($(this).text() == "查看文章") {
		fun = 'check';
		auditPage = page;
		page = checkPage;
	} else {
		fun = 'audit';
		checkPage = page;
		page = auditPage;
	}
	//根据当前页数，判断按钮是否可按
	document.getElementById("pageInput").value = page;
	if (page <= 1)
		document.getElementById("lastPage").disabled = true;
	else
		document.getElementById("lastPage").disabled = false;
	//ajax请求数据
	get()
})

//当点击其他tab的时候，关闭当前的collapse
//如果不关闭，切换过来collapse还是开着的，然后ajax更新数据后，collapse立即关闭，用户体验很糟
$('#doc-tab').find('a').on('open.tabs.amui', function (e) {
	if (collapse != '') {
		closeTab();
	}
})
//
function closeTab() {
	$("#" + collapse).collapse('close');
	collapse = '';
}

//换页
function changePage(action) {
	if (action == 'last')
		page--;
	else if (action == 'next')
		page++;
	document.getElementById("pageInput").value = page;
	if (page <= 1)
		document.getElementById("lastPage").disabled = true;
	else
		document.getElementById("lastPage").disabled = false;
	get()
}


//获取页面内容
function get() {


	page = document.getElementById("pageInput").value;
	if (page <= 1)
		document.getElementById("lastPage").disabled = true;
	else
		document.getElementById("lastPage").disabled = false;
	//myJson中的其他数据（mid，style）并不需要改变，因此统一在StuffService-common.js中赋值
	myJson.page = page.toString();
	myJson.method = "getPassageList";
	//按类型获取
	if (fun == 'check') {
		if (document.getElementById("articleState").value != -1) {
			myJson.state = document.getElementById("articleState").value;
		} else {
			delete myJson.state;
		}
	}
	//按功能获取
	else if (fun == 'audit') {
		myJson.state = '0';
	}

	var keyword = $("#search").val();
	if (keyword == null || keyword == '') {
		delete myJson.username;
	}
	else {
		myJson.username = keyword;
		$("#all001").attr('selected',true);
		delete myJson.state;
	}

	//封装的ajax方法
	//参数action传入1表示ajax返回值包含data，并使用changeTable函数来处理
	myAjax(myJson, changeTable);
}

//文章审核通过
function pass(id) {
	myJson.passageId = id.toString();
	myJson.method = "validatePassage";
	myJson.validate = "true";
	myJson.refuseReason = $("#" + id + "Comment").val();
	myAjax(myJson, get);
}

//文章审核不通过
function fail(id) {
	if($("#" + id + "Comment").val()==''){
		alert('请填写意见')
		return;
	}
	myJson.passageId = id.toString();
	myJson.method = "validatePassage";
	myJson.validate = "false";
	myJson.refuseReason = $("#" + id + "Comment").val();
	myAjax(myJson, get);
}

function removePassage(passageId){
	myJson.passageId = passageId;
	myJson.method = "removePassage";
	myAjax(myJson, get);
}

//根据ajax返回的数据，改变页面
function changeTable(result) {
	$("#" + fun + "Accordion").empty();
	//显示在tab上的状态
	var tmpState;
	//生成的tab的代码
	var newTab;
	list = result.data;
	$.each(list, function (index, data) {
		if (data.state == 0) {
			tmpState = "审核中";
		} else if (data.state == 1) {
			tmpState = "未通过";
		} else {
			tmpState = "通过";
		}
		//转化1970的时间
		var createTime = parseInt(data.createTime, 10);
		createTime = new Date(createTime);
		//audit模式下显示的东西，state = 0
		if (fun == 'audit') {
			if(data.state != 0){
				return true;
			}
			newTab = "<div class='am-panel'><div class='am-panel-hd'>"
			+ "<h4 class='am-panel-title' data-am-collapse=\"{parent: '#" + fun + "Accordion', target: '#" + data.passageId + "Audit'}\"><button onclick=\"removePassage("+data.passageId+")\">删除</button>"
			+ "<font color ='red'>【ID:"+data.passageId+",状态:" + tmpState + "】</font>" + data.title + " <font color='#63B8FF'>" + data.editorName + "创作于"
			+ createTime.toLocaleString() + "</font>点赞：" + data.likeNumber + " 浏览量：" + data.lookNumber
			+ "</h4></div><div id='" + data.passageId + "Audit' class='am-panel-collapse am-collapse'>"
			+ "<div class='am-panel-bd bootstrap'>封面图片：<br><img src='" + data.imageUrl + "' class='am-img-thumbnail'><br>正文：<br>"
			+ data.content + "<input id='" + data.passageId + "Comment' class='am-form-field' type='text' placeholder='请填写一些评价'><div><button id='"
			+ data.passageId + "Pass' type='button' class='am-btn am-btn-danger' onclick='pass(" + data.passageId + ")'>通过</button><button id='"
			+ data.passageId + "Fail' type='button' class='am-btn am-btn-danger' onclick='fail(" + data.passageId + ")'>不通过</button></div></div></div></div>";
		}
		//check模式下
		else if (fun == 'check') {
			if (data.state == 0) {
				newTab = "<div class='am-panel'><div class='am-panel-hd'>";
			} else if (data.state == 1) {
				newTab = "<div class='am-panel am-panel-danger'><div class='am-panel-hd'>";
			} else {
				newTab = "<div class='am-panel am-panel-success'><div class='am-panel-hd'>";
			}
			newTab += "<h4 class='am-panel-title' data-am-collapse=\"{parent: '#" + fun + "Accordion', target: '#" + data.passageId + "'}\"><button onclick=\"removePassage("+data.passageId+")\">删除</button>"
			+ "<font color='red'>【ID:"+data.passageId+",状态:" + tmpState + "】</font>" + data.title + " <font color='#63B8FF'>" + data.editorName + "创作于"
			+ createTime.toLocaleString() + "</font>点赞：" + data.likeNumber + " 浏览量：" + data.lookNumber
			+ "</h4></div><div id='" + data.passageId + "' class='am-panel-collapse am-collapse'>" + "<div class='am-panel-bd bootstrap'>";
			if (data.state == 1) {
				newTab += "拒绝理由：<br>" + data.refuseReason + "<br>";
			}
			newTab += "封面图片：<br><img src='" + data.imageUrl + "' class='am-img-thumbnail'><br>正文：<br>"
			+ data.content + "</div></div></div>";
		}
		$("#" + fun + "Accordion").append(newTab);
	})
	//按照amaze，更改后对tab进行刷新
	$("#" + fun).tabs('refresh');
	//改变tab的style并添加方法，重新遍历
	$.each(list, function (index, data) {
		if (fun == 'audit') {
			$("#" + data.passageId + "Audit").css('width', '700px');
			$("#" + data.passageId + "Audit").css('margin', 'auto');
			$("#" + data.passageId + "Audit").on('open.collapse.amui',
				function () {
					//记录打开的tab
					collapse = data.passageId + "Audit";
				});
		} else {
			$("#" + data.passageId).css('width', '700px');
			$("#" + data.passageId).css('margin', 'auto');
			$("#" + data.passageId).on('open.collapse.amui',
				function () {
					collapse = data.passageId;
				});
		}
	})
}