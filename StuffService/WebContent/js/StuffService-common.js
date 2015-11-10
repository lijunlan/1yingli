//var serverUrl = "http://localhost:8080/"
var serverUrl="http://120.26.83.33/"
	
function checkLogin() {
	mid = $.cookie('mid');
	if (mid == null) {
		self.location = serverUrl + "StuffService/login.jsp"
	}
}

function registNotify() {
	PL.uid = mid;
	// 注册标签
	PL.joinListen("/yiyingli/manager");
	// 接收到服务器数据的回调函数

}

//基于网页的弹窗存在时间
var time = 60 * 60 * 24
// 基于网页的弹窗
function messenger(msg, url) {
	play('media/notify.mp3')
	Messenger().post({
		message : msg,
		type : 'error',
		showCloseButton : true,
		hideAfter : time,
		actions : {
			cancel : {
				label : '马上处理',
				action : function() {
					self.location = url;
				}
			}
		}
	});
}


// 基于浏览器的弹窗
function notifyMe(title, content, url) {
	//ie区别对待
	try{
		if (!Notification) {
			messenger(content, url)
			return;
		}
	}catch(e){
		//ie
		messenger(content, url)
	} 
	if (Notification.permission !== "granted") {
		Notification.requestPermission();
		messenger(content, url)
		return
	} else {
		play('media/notify.mp3')
		var notification = new Notification(title, {
			icon : 'icon/notify.jpg',
			body : content,
		});
		notification.onclick = function() {
			// window.open(url)
			self.location = url
		};

	}

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
		play('media/notify.mp3')
		if (result.type == "withdraw") {
			notifyMe("您有新的订单变化", "有订单需要退款", serverUrl
					+ "StuffService/orderService.jsp?state=0700")
		} else if (result.type == "managerIn") {
			notifyMe("您有新的订单变化", "有订单需要客服介入", serverUrl
					+ "StuffService/orderService.jsp?state=1300")
		} else if (result.type == "salary") {
			notifyMe("您有新的订单变化", "有订单需要平台打款", serverUrl
					+ "StuffService/orderService.jsp?state=0900")
		}
	}
	// alert(event.get("data"));
}
// 播放提示音
var flashvars = {};
var params = {
	wmode : "transparent"
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
