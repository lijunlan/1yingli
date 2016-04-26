/**
 * 变量
 */
var myJson = new Object();
var URL = "http://120.26.83.33/yiyingliManagerService/";
//var SERVICE_URL= "http://service.1yingli.cn/yiyingliService/"
var SERVICE_URL= "http://service.1yingli.cn/yiyingliService/";
myJson.style = "manager";
var _Notification;
if(!window.webkitNotifications){
	;
} else {
	_Notification = Notification;
}
//RSA key
var publickey = "8959d2ced61bee338accd16794538ec0a49da0655ddca8fa2461d4cf419dafaf4d7c47813f6ac8c6e5646a2beb08cccf4184a831e683a631e3c528b908deecc57235d03935d0650fbe53d44f717da7f5d1622e7405a3b4f06377eb506880dae21e5065c878c03d85113e068ac82af6b29037d57163d9a311807bee654927d349";
//错误代码
var errorType = {
	//<!-- system 0 -->
	"00000": "内部错误",
	"00001": "参数不完整",
	"00002": "该功能尚未上线，请耐心等待",
	"00003": "style不存在",
	"00004": "未指明必要的style或method",
	//<!-- user 1-->
	"10001": "解密失败",
	"12001": "验证码不正确",
	"12002": "验证码过期",
	"12003": "电子邮件格式不正确",
	"12004": "旧密码或新密码不能为空",
	"12005": "密码至少8位，不能全数字",
	"12006": "原密码错误",
	"12007": "电话号码格式不正确",
	"12008": "电话号码或邮箱格式有误",
	"12009": "所请求的评论种类不正确",
	"12010": "所请求的页码解析错误",
	"12011": "notiId解析错误",
	"12012": "所请求的通知不存在",
	"12013": "用户名不正确，应为邮箱或者手机号码",
	"12014": "用户名和密码不能为空",
	"12015": "用户名不存在",
	"12016": "密码不正确",
	"12017": "用户名或密码不正确",
	"12018": "你的微博代码是错误的",
	"12019": "你的微信代码是错误的",
	"12020": "文章不存在",
	"12021": "您已经赞过了",
	"14001": "USERNAME不存在",
	"15001": "已申请",
	"15002": "已成为导师",
	"15003": "用户已注册",
	//<!-- teacher 2-->
	"21001": "输入数据错误",
	"22001": "导师不存在",
	"22002": "导师当前无法提供服务",
	"22003": "电话号码有误",
	"22004": "电话号码格式不正确",
	"22005": "所请求的页码解析错误",
	"22006": "文章不存在",
	"22007": "文章不属于你",
	"24001": "你尚未成为导师",
	"24002": "tid和uid不匹配",
	//<!-- manager 3-->
	"30001": "解密失败",
	"31001": "数据不正确",
	"32001": "密码不符合规范",
	"32002": "用户名不存在",
	"32003": "申请不存在",
	"32004": "申请状态不正确",
	"32005": "checkForm是不存在",
	"32006": "checkForm状态不正确",
	"32007": "checkForm认证类型不正确",
	"32008": "checkForm认证类型不正确",
	"32009": "所请求的页码解析错误",
	"32010": "用户名或密码不能为空",
	"32011": "用户名不存在",
	"32012": "密码错误",
	"32013": "background id不存在",
	"32014": "该用户不存在",
	"32015": "该导师不存在",
	"34001": "管理员不存在",
	//<!-- order 4-->
	"40001": "服务器错误",
	"41001": "输入数据错误",
	"42001": "订单不存在",
	"43001": "支付宝错误",
	"43002": "连接到Paypal失败，请再试一次",
	"44001": "这个订单不属于你",
	"44002": "订单状态异常",
	"44003": "deal字段有误",
	"44004": "订单支付导师状态不准确",
	"44005": "订单状态已经无法改变",
	"44006": "不要预约自己，你本可以对着镜子自言自语的~",
	"45001": "该优惠券不存在",
	"45002": "该优惠券已过期",
	"45003": "该优惠券已使用",
	"45004": "Paypal付款与订单不匹配",
	"45005": "支付宝已退款，状态已变化",
	//<!-- function 5-->
	//<!-- <error name="53001" value="recommendation engine error" /> -->
	"50001": "服务器错误",
	"51001": "请求页码解析出错",
	"52001": "用户名格式不正确",
	"52002": "这个电话或电子邮件没有验证码",
	"53002": "无法连接到搜索引擎",
	"53003": "搜索时出现错误",
	"53004": "向推荐引擎提交数据失败",
	"53005": "搜索引擎错误",
	"55001": "暂时无法再次获取，请稍后再试",
	//<!-- distributor 6-->
	"60001": "解密失败",
	"62001": "分销码不存在",
	"62002": "用户名或密码不能为空",
	"62003": "用户名不存在",
	"62004": "密码不正确",
	"64001": "分销ID不存在"
};

/**
 * 函数
 */
// 传递name-value pair的对象
// action 具体操作，

function myAjax(JsonStr, action) {
	$.ajax({
		type: "POST",
		url: URL + 'manage',
		data: $.toJSON(JsonStr),
		success: function (data, status) {
			var result = JSON.parse(data);
			//请求返回错误的处理
			if (result.state != "success") {
				//设置提示框显示的文字
				$('#modalMsg').text("详情:" + errorType[result.errCode]);
				//倘若错误为manager不存在，就让按钮显示重新登录，并有相应功能
				if (result.errCode == '34001') {
					$('#confirmSpan').html('重新登录');
					$('#my-confirm').modal({
						onConfirm: function (options) {
							self.location = "login.html";
						}
					})
				}
				//否则，显示关闭，点击没有任何功能 
				else {
					$('#confirmSpan').html('关闭');
					$('#my-confirm').modal({
						onConfirm: function (options) {
							$('#my-confirm').modal('close');
						}
					})
				}
			}
			//当返回成功
			else {
				//提前处理数据
				if(typeof result.data == 'string'){
					//因为有的时候，data并不是一个json数据，= =！
					try{
						result.data = JSON.parse(result.data);
					}catch(e){
						console.log(e);
					}

				}
				//如果传进来的是回调函数
				if (typeof action == 'function') {
					if (typeof result == 'string') {
						result = JSON.parse(result);
					}
					//一般不返回大量数据，仅返回“成功”的操作，回调函数不需要任何参数
					if (action.length == 0) {
						Messenger().post("操作成功");
						console.log(action);
						action();
					}
					//返回大量数据，需要更改页面
					if (action.length == 1) {
						action(result);
					}
				} else {
					return;
				}
			}
		}
	});
}

function returnIndex() {
	self.location = "index.html";
}

function logout() {
	mid = $.cookie('mid');
	$.ajax({
		type: "POST",
		url: "http://120.26.83.33/yiyingliManagerService/manage",
		data: "{'style': 'manager','method':'logout','mid':'" + mid + "'}",
		success: function (data, status) {
			$.cookie('mid', '', { expires: -1 });
			$.cookie('mname', '', { expires: -1 });
			window.location.reload();
		}
	});
}

//检查是否登录
function checkLogin() {
	mid = $.cookie('mid');
	if (mid == null) {
		$.cookie('mname', '', { expires: -1 });
		self.location = "login.html";
	}
	myJson.mid = mid;
	document.getElementById("admin_name").innerText = $.cookie('mname');
}

// 基于网页的弹窗存在时间
var time = 60 * 60 * 24;
// 基于网页的弹窗
function messenger(msg, url) {
	play('media/notify.mp3');
	Messenger().post({
		message: msg,
		type: 'error',
		showCloseButton: true,
		hideAfter: time,
		actions: {
			cancel: {
				label: '马上处理',
				action: function () {
					self.location = url;
				}
			}
		}
	});
}


// 基于浏览器的弹窗
function notifyMe(title, content, url) {
	// ie和某些未知的浏览器并不支持基于浏览器的弹窗，因此使用基于网页的弹窗
	if(!window.webkitNotifications)
		;
	else{
		try {
			//某些未知的浏览器
			if (!_Notification) {
				messenger(content, url);
				return;
			}
		} catch (e) {
			// ie
			messenger(content, url);
		}
		//对于支持的浏览器请求弹窗的权限失败
		if (_Notification.permission !== "granted") {
			_Notification.requestPermission();
			messenger(content, url);
			return;
		} else {
			//播放提示音
			play('media/notify.mp3');
			var notification = new _Notification(title, {
				icon: 'icon/notify.jpg',
				body: content,
			});
			notification.onclick = function () {
				self.location = url;
			};
		}
	}

}

//注册通知
function registNotify() {
	PL.uid = mid;
	// 注册标签
	PL.joinListen("/yiyingli/manager");
	// 接收到服务器数据的回调函数
}

// 接收消息
function onData(event) {
	var json = event.get("dataJson");
	// 判断是否是Ping
	if (json != null) {
		// 处理返回的json
		// 先进行URLDECODE
		json = decodeURIComponent(json);
		result = eval("(" + json + ")");
		play('media/notify.mp3');
		if (result.type == "withdraw") {
			notifyMe("您有新的订单变化", "有订单需要退款", "orderService.html?state=0700");
		} else if (result.type == "managerIn") {
			notifyMe("您有新的订单变化", "有订单需要客服介入", "orderService.html?state=1300");
		} else if (result.type == "salary") {
			notifyMe("您有新的订单变化", "有订单需要平台打款", "orderService.html?state=2000");
		} else if (result.type == "waitConfirm") {
			notifyMe("您有新的订单变化", "已有用户支付订单", "orderService.html?state=0300");
		} else if (result.type == "checkServicePro") {
			notifyMe("有新的服务需要审核", "有新的服务需要审核", "listServiceProService.html?state=0");
		} 
	}
}

// 播放提示音
var flashvars = {};
var params = {
	wmode: "transparent"
};
var attributes = {};
swfobject.embedSWF("media/sound.swf", "sound", "1", "1", "9.0.0",
	"media/expressInstall.swf", flashvars, params, attributes);

function play(c) {
	var sound = swfobject.getObjectById("sound");
	if (sound) {
		sound.SetVariable("f", c);
		sound.GotoFrame(1);
	}
}




function tip2zh(tip,kind){
	var ts = tip.split(",");
	if(kind==1){
		var str = "";
		$.each(ts,function(index,data){
			if(data=="1"){
				str += "超时服务;";
			}else if(data=="2"){
				str += "可视频;";
			}else if(data=="3"){
				str += "可语音;";
			}else if(data=="4"){
				str += "时间灵活;";
			}else if(data=="5"){
				str += "支持多人;";
			}
		});
		return str;
	}else if(kind==2){
		var str = "";
		$.each(ts,function(index,data){
			if(data=="1"){
				str += "时间灵活;";
			}else if(data=="2"){
				str += "支持多人;";
			}else if(data=="3"){
				str += "轻装上阵;";
			}else if(data=="4"){
				str += "超时服务;";
			}else if(data=="5"){
				str += "接送服务;";
			}
		});
		return str;
	}else if(kind==3){
		var str = "";
		$.each(ts,function(index,data){
			if(data=="1"){
				str += "支持加急;";
			}else if(data=="2"){
				str += "量大优惠;";
			}else if(data=="3"){
				str += "5天内完成;";
			}
		});
		return str;
	}else if(kind==4){
		var str = "";
		$.each(ts,function(index,data){
			if(data=="1"){
				str += "时间灵活;";
			}else if(data=="2"){
				str += "支持多人;";
			}else if(data=="3"){
				str += "可长期服务;";
			}else if(data=="4"){
				str += "专业领域;";
			}
		});
		return str;
	}else if(kind==5){
		var str = "";
		$.each(ts,function(index,data){
			if(data=="1"){
				str += "超时服务;";
			}else if(data=="2"){
				str += "时间灵活;";
			}else if(data=="3"){
				str += "上门服务;";
			}
		});
		return str;
	}
}

function kind2zh(kind){
	if(kind==1){
		return "咨询";
	}else if(kind==2){
		return "体验";
	}else if(kind==3){
		return "批改";
	}else if(kind==4){
		return "技艺教授";
	}else if(kind==5){
		return "帮忙";
	}
}

function state2zh(state){
	if(state==1){
		return "通过审核";
	}
	if(state==0){
		return "等待审核";
	}
	if(state==2){
		return "未通过审核";
	}
}
