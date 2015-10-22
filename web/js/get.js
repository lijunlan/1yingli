function $_GET(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

//微博相关功能
function weibo(){
var code = $_GET('code');
$.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : "{'style':'user','method':'loginByWeibo','weibo_code':'"+code+"'}",
        async : false,
        error : function(request) {
                    self.location = 'login.html';
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
			if(json.state =="success" ){
				var uid = json.uid;
                var nickName = json.nickName;
                var iconUrl = json.iconUrl;
                $.cookie("uid",uid,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                $.cookie("nickName",nickName,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                $.cookie("iconUrl",iconUrl,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                var tid = json.teacherId;
                if(tid!=null&&tid!="undefined"&&tid!=""){
                    $.cookie("tid",tid,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                }
                var callback = $_GET("callback");
                if(callback!=null&&callback!=""){
                    callback = decodeURIComponent(callback);
                    self.location = callback;
                }else{
                    history.go(-2);
                }
			}else{
				self.location = 'login.html';
       }
        }
    });

}

//微信相关功能
function weixin(){
    var code = $_GET('code');
    $.ajax({
            cache : true,
            type : "POST",
            url : config.base_url,
            data : "{'style':'user','method':'loginByWeixin','weixin_code':'"+code+"'}",
            async : false,
            error : function(request) {
                        self.location = 'login.html';
                    },
            success : function(data, textStatu) {
                var json = eval("(" + data + ")");
                if(json.state =="success" ){
                    var uid = json.uid;
                    var nickName = json.nickName;
                    var iconUrl = json.iconUrl;
                    $.cookie("uid",uid,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("nickName",nickName,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("iconUrl",iconUrl,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    var tid = json.teacherId;
                    if(tid!=null&&tid!="undefined"&&tid!=""){
                        $.cookie("tid",tid,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    }
                    var callback = $_GET("callback");
                    if(callback!=null&&callback!=""){
                        callback = decodeURIComponent(callback);
                        self.location = callback;
                    }else{
                        history.go(-3);
                    }
                }else{
                    self.location = 'login.html';
           }
            }
        });

}

