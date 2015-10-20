$(document).ready(function(){
	var mid = $.cookie("mid");
	var mname = $.cookie("mname");
	if(mid!=null)self.location = 'manage_application.html';
});

var publickey = "8959d2ced61bee338accd16794538ec0a49da0655ddca8fa2461d4cf419dafaf4d7c47813f6ac8c6e5646a2beb08cccf4184a831e683a631e3c528b908deecc57235d03935d0650fbe53d44f717da7f5d1622e7405a3b4f06377eb506880dae21e5065c878c03d85113e068ac82af6b29037d57163d9a311807bee654927d349";

function update(){
	var password = $("#password").val();
	password = encryptedStr(password,publickey);
	$.ajax({
                cache : true,
                type : "POST",
                url : "http://service.1yingli.cn/yiyingliService/manage",
                data : "{'style':'manager','method':'login','username':'"+$("#username").val()+"','password':'"+password+"'}",
                async : false,
                error : function(request) {
                            alert("Connection error");
                        },
                success : function(data, textStatu) {
                    var json = eval("(" + data + ")");
					if (json.state == "success") {
                    	$.cookie("mid",json.mid,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    	$.cookie("mname",json.mname,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                        self.location = 'manage_application.html';
                    } else {
                        alert(json.msg);
                    }
                }
            });
}