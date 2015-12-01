//初始化
mid = $.cookie('mid');
registNotify();
//获取参数
var tid = window.location.href.split("?")[1].split("=")[1];
var processWork = function (list) {
	if (typeof list == 'string') {
		list = JSON.parse(list);
	}
	var result = "<th>公司名称</th><th>职位</th><th>介绍</th><th>开始时间</th><th>结束时间</th></tr>";
	$.each(list, function (index, data) {
		result += "<tr><td>" + data.companyName + "</td>";
		result += "<td>" + data.position + "</td>";
		result += "<td>" + data.description + "</td>";
		result += "<td>" + data.startTime + "</td>";
		result += "<td>" + data.endTime + "</td></tr>";
	})
	return result;
}
var processStudy = function (list) {
	if (typeof list == 'string') {
		list = JSON.parse(list);
	}
	var result = "<th>学校名称</th><th>专业</th><th>学位</th><th>开始时间</th><th>结束时间</th><th>描述</th></tr>";
	$.each(list, function (index, data) {
		result += "<tr><td>" + data.schoolName + "</td>";
		result += "<td>" + data.major + "</td>";
		result += "<td>" + data.degree + "</td>";
		result += "<td>" + data.startTime + "</td>";
		result += "<td>" + data.endTime + "</td>";
		result += "<td>" + data.description + "</td></tr>";
	})
	return result;
}
var processTips = function (list) {
	if (typeof list == 'string') {
		list = JSON.parse(list);
	}
	var result = "<th>类型</th></tr>";
	$.each(list, function (index, data) {
		result += "<tr><td>" + data.id + "</td></tr>";
	})
	return result;
}

var changeTable = function (data) {
	$("#infoTable").empty();
	if (typeof data.workExperience == 'string') {
		var workCount = JSON.parse(data.workExperience).length + 1;
		var studyCount = JSON.parse(data.studyExperience).length + 1;
		var tipsCount = JSON.parse(data.tips).length + 1;
	} else {
		var workCount = data.workExperience.length + 1;
		var studyCount = data.studyExperience.length + 1;
		var tipsCount = data.tips.length + 1;
	}
	var row = "";
	row += "<tr><th>teacherID</th><td colspan=6>" + tid + "</td></tr>";
	row += "<tr><th>姓名</th><td colspan=6>" + data.name + "</td></tr>";
	row += "<tr><th>头像链接</th><td colspan=6><img id=\"littleIcon\" src=\"" + data.iconUrl + "\" class=\"am-img-thumbnail\"></td></tr>";
	row += "<tr><th>电话</th><td colspan=6>" + data.phone + "</td></tr>";
	row += "<tr><th>邮箱</th><td colspan=6>" + data.email + "</td></tr>";
	row += "<tr><th>简介</th><td colspan=6>" + data.introduce + "</td></tr>";
	row += "<tr><th>粉丝数</th><td colspan=6>" + data.likeNo + "</td></tr>";
	row += "<tr><th>地址</th><td colspan=6>" + data.address + "</td></tr>";
	row += "<tr><th>交流方式</th><td colspan=6>" + data.talkWay + "</td></tr>";
	row += "<tr><th>评论数量</th><td colspan=6>" + data.commentNo + "</td></tr>";
	row += "<tr><th>每周咨询次数</th><td colspan=6>" + data.timeperweek + "</td></tr>";
	row += "<tr><th>空闲时间</th><td colspan=6>" + data.freeTime + "</td></tr>";
	row += "<tr><th>服务价格</th><td colspan=6>" + data.price + "</td></tr>";
	row += "<tr><th>服务时间</th><td colspan=6>" + data.serviceTime + "</td></tr>";
	row += "<tr><th>服务标题</th><td colspan=6>" + data.serviceTitle + "</td></tr>";
	row += "<tr><th>服务内容</th><td colspan=6>" + data.serviceContent + "</td></tr>";
	row += "<tr><th>邮箱认证</th><td colspan=6>" + data.checkEmail + "</td></tr>";
	row += "<tr><th>手机认证</th><td colspan=6>" + data.checkPhone + "</td></tr>";
	row += "<tr><th>学历认证</th><td colspan=6>" + data.checkDegree + "</td></tr>";
	row += "<tr><th>身份认证</th><td colspan=6>" + data.checkIDCard + "</td></tr>";
	row += "<tr><th>工作认证</th><td colspan=6>" + data.checkWork + "</td></tr>";
	row += "<tr><th rowspan=" + workCount + ">工作经验</th>" + processWork(data.workExperience);
	row += "<tr><th rowspan=" + studyCount + ">学习经验</th>" + processStudy(data.studyExperience);
	row += "<tr><th rowspan=" + tipsCount + ">标签</th>" + processTips(data.tips);
	row += "<tr><th>被审文章</th><td>" + data.checkPassageNumber + "</td></tr>";
	row += "<tr><th>通过文章</th><td>" + data.passageNumber + "</td></tr>";
	row += "<tr><th>拒绝文章</th><td>" + data.refusePassageNumber + "</td></tr>";
	//时间
	$("#infoTable").append(row);

};

//根据前面获取的参数显示页面
$(function () {
	myJson.method = "getTeacherAllInfo";
	myJson.teacherId = tid;
	myJson.mid = mid;
	myAjax(myJson, changeTable);
})