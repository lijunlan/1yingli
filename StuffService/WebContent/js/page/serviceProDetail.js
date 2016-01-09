//初始化
mid = $.cookie('mid');
registNotify();
//获取参数
var serviceProId = window.location.href.split("?")[1].split("=")[1];


var changeTable = function (data) {
	$("#infoTable").empty();	
	var row = "";
	row += "<tr><th>serviceProId</th><td colspan=6>" + serviceProId + "</td></tr>";
	row += "<tr><th>服务标题</th><td colspan=6>" + data.title + "</td></tr>";


	row += "<tr><th>相关图片</th><td colspan=6>";
	var images = imageUrls.split(",");
	$.each(images,function(index,im){
		row += "<img id=\"littleIcon\" src=\"" + im + "\" class=\"am-img-thumbnail\">";
	});

	row += "</td></tr>";
	row += "<tr><th>服务简介</th><td colspan=6>" + data.summary + "</td></tr>";
	row += "<tr><th>服务内容</th><td colspan=6>" + data.content + "</td></tr>";
	row += "<tr><th>价格</th><td colspan=6>" + data.price +"/"+data.numeral+data.quantifier+ "</td></tr>";
	row += "<tr><th>折扣价格</th><td colspan=6>" + data.priceTemp + "</td></tr>";
	row += "<tr><th>是否开启折扣</th><td colspan=6>" + data.onsale + "</td></tr>";
	row += "<tr><th>空闲时间描述</th><td colspan=6>" + data.freeTime + "</td></tr>";
	row += "<tr><th>服务分类</th><td colspan=6>" + kind2zh(data.kind) + "</td></tr>";
	row += "<tr><th>上架</th><td colspan=6>" + data.onshow + "</td></tr>";
	row += "<tr><th>特色标签</th><td colspan=6>" + tip2zh(data.tip,data.kind) + "</td></tr>";
	row += "<tr><th>成交量</th><td colspan=6>" + data.finishNo + "</td></tr>";
	row += "<tr><th>应答率</th><td colspan=6>" + data.answerRatio + "</td></tr>";
	row += "<tr><th>应答时间</th><td colspan=6>" + data.answerTime + "</td></tr>";
	row += "<tr><th>评分</th><td colspan=6>" + data.score + "</td></tr>";
	row += "<tr><th>收藏量</th><td colspan=6>" + data.likeNo + "</td></tr>";
	row += "<tr><th>好评率</th><td colspan=6>" + data.praiseRatio + "</td></tr>";
	row += "<tr><th>服务状态</th><td>" + state2zh(data.serviceProState) + "</td></tr>";
	//时间
	$("#infoTable").append(row);

};

//根据前面获取的参数显示页面
$(function () {
	myJson.method = "getServicePro";
	myJson.serviceProId = serviceProId;
	myJson.mid = mid;
	myAjax(myJson, changeTable);
});