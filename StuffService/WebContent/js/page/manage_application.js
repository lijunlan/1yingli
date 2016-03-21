//初始化
var page = 1;
var mid;
checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');


$(function () {
	myJson.method = "getApplicationFormList";
	myJson.page = page.toString();
	delete myJson.teacherName;
	myAjax(myJson, changeTable);
	if (page == 1)
		document.getElementById("lastPage").disabled = true;
	document.getElementById("pageInput").value = page;
});

function search(){
	var teacherName = $("#inputSearchTeacherName").val();
	myJson.method = "getApplicationFormList";
	delete myJson.page;
	myJson.teacherName = teacherName;
	myAjax(myJson, changeTable);
}

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
	myJson.method = "getApplicationFormList";
	delete myJson.teacherName;
	myJson.page = page.toString();
	myAjax(myJson, changeTable);
}

function get() {
	page = document.getElementById("pageInput").value;
	myJson.method = "getApplicationFormList";
	myJson.page = page.toString();
	delete myJson.teacherName;
	myAjax(myJson, changeTable);
	if (document.getElementById("pageInput").value > 1)
		document.getElementById("lastPage").disabled = false;
}

//将ajax返回的数据显示在页面上
var changeTable = function (json) {
	if (json.state == "success") {
		var html = "";
		if (typeof json.data == 'string') {
			json.data = JSON.parse(json.data);
		}
		$.each(json.data, function (index, data) {
			var userid = data.userId;
			var username = data.username
			var phone = data.phone;
			var name = data.name;
			var email = data.email;
			var contact =data.contact;
			var address = data.address;
			var contact = data.contact;
			var checkInfo = data.checkInfo;
			if (checkInfo == "undefined")
				checkInfo = "";
			var serviceReason = data.serviceReason;
			var serviceTitle = data.serviceTitle;
			var serviceAdvantage = data.serviceAdvantage;
			var serviceOnline = data.serviceOnline;
			var serviceContent = data.serviceContent;
			var servicePrice = data.servicePrice;
			var serviceTime = data.serviceTime;
			var endTime = data.endTime;
			var createTime = data.createTime;
			var state = data.state;
			var workExperience = data.workExperience;
			var schoolExperience = data.schoolExperience;
			var tips = data.tips;
			var afId = data.afId;
			html = html + "<tr>" + "	<td>" + afId + "	</td>" + "	<td>用户ID:"
			+ userid + "<br><br>用户名:<br>"+username+"<br><br>申请姓名:"+name+"<br><br>电话:"+phone+"<br><br>邮箱："+email+"<br><br>微信："+contact+"  </td><td>创建时间：<br>" + createTime
			+ "</br></br>审核时间：</br>" + endTime + "	</td>" + "	<td>" + state
			+ "	</td>" +  "	<td>"
			+ address + "	</td>" 
			+ "	<td>";
			if (typeof workExperience == 'string') {
				workExperience = JSON.parse(workExperience)
			}
			$.each(workExperience, function (index, content) {
				html = html + "公司名称:" + content.company + "</br>职位:"
				+ content.position + "</br>在职经历:"
				+ content.description + "</br>开始时间:"
				+ content.startTime + "</br>结束时间:"
				+ content.endTime + "</br></br>";
			});
			html = html + "	</td>" + "	<td>";
			if (typeof schoolExperience == 'string') {
				schoolExperience = JSON.parse(schoolExperience)
			}
			$.each(schoolExperience,
				function (index, content) {
					html = html + "学校名称:" + content.school
					+ "</br>专业:" + content.major
					+ "</br>学历:" + content.degree
					+ "</br>在校经历:" + content.description
					+ "</br>开始时间:" + content.startTime
					+ "</br>结束时间:" + content.endTime
					+ "</br></br>";
				});
			html = html + "	</td>";
			html = html + "	<td>"
			+ "	<textarea id=\"checkInfo_"
			+ afId
			+ "\" placeholder=\""
			+ ((state == "审核处理中") ? "审核意见(描述不通过的理由,通过可以不填)"
				: checkInfo)
			+ "\" rows=\"5\"></textarea></br></br>"
			+ "	<button onclick=\"accept("
			+ afId
			+ ")\" "
			+ ((state != "审核处理中") ? "style=\"display:none\""
				: "")
			+ ">通过</button></br></br>"
			+ "	<button onclick=\"refuse("
			+ afId
			+ ")\" "
			+ ((state != "审核处理中") ? "style=\"display:none\""
				: "") + ">拒绝</button>" + "	</td>"
			+ "	</tr>";
		});
		$("#application_list").html(html);
	}
}

function accept(afid) {
	var description = $("#checkInfo_" + afid).val();
	myJson.method = 'doneApplicationForm';
	myJson.afId = afid.toString();
	myJson.accept = 'true';
	myJson.description = description;
	myAjax(myJson, null)
	setTimeout('refresh()', 500);
	Messenger().post("操作成功");
}

function refuse(afid) {
	var description = $("#checkInfo_" + afid).val();
	if (description == null || description == "") {
		alert('请填写意见')
		return;
	}
	myJson.method = 'doneApplicationForm';
	myJson.afId = afid.toString();
	myJson.accept = 'false';
	myJson.description = description;
	myAjax(myJson, null);
	setTimeout('refresh()', 500);
	Messenger().post("操作成功");
} 
