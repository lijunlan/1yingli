//初始化
checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');

$(document).ready(function () {
	refresh();
});

//重载页面的函数
function refresh() {
	//myJson的mid和style
	myJson.method = 'getApplicationFormList';
	myAjax(myJson, get);
}

//将ajax返回的数据显示在页面上
function get(json) {
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
			var address = data.address;
			var checkInfo = data.checkInfo;
			if (checkInfo == "undefined")
				checkInfo = "";
			var serviceReason = data.serviceReason;
			var serviceTitle = data.serviceTitle;
			var serviceAdvantage = data.serviceAdvantage;
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
			html = html + "<tr>" + "	<td>" + afId + "	</td>" + "	<td>"
			+ userid + "	</td><td>" + username + "</td><td>" + name + "	</td><td>" + createTime
			+ "/</br>" + endTime + "	</td>" + "	<td>" + state
			+ "	</td>" + "	<td>" + phone + "	</td>" + "	<td>"
			+ address + "	</td>" + "	<td>" + email + "	</td>"
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
			html = html + "	</td>" + "	<td>";
			if (typeof tips == 'string') {
				tips = JSON.parse(tips)
			}
			$.each(tips, function (index, content) {
				html = html + content.name + "</br></br>";
			});
			html = html
			+ "	</td>"
			+ "	<td>"
			+ "	服务价格:"
			+ servicePrice
			+ "</br>服务时间:"
			+ serviceTime
			+ "</br>名称:"
			+ serviceTitle
			+ "</br>服务原因:"
			+ serviceReason
			+ "</br>服务内容:"
			+ serviceContent
			+ "</br>优势:"
			+ serviceAdvantage
			+ "</br>"
			+ "	</td>"
			+ "	<td>"
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
