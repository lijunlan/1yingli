$(document).ready(function(){
    var u = $.cookie('uid');
    var n = $.cookie('nickName');
    var i = $.cookie('iconUrl');
    if(u==null||n==null||i==null){self.location='login.html';return;}
    $("#nickName").html(n);
    $("#bigNickName").html(n);
    if(i!=""){
        $("#iconUrl").attr("src",i);
        $("#bigIcon").attr("src",i);
    } 

    $("#apliay").mousedown(function(){
        $("#apliayError").hide();
        $("#noEquialError").hide();
    });
    $("#reapliay").mousedown(function(){
        $("#reApliayError").hide();
        $("#noEquialError").hide();
    });

    getApliayNo();
});

function getApliayNo() {
	var toSend = new Object();
    toSend.style = "teacher";
    toSend.method = "getAlipayNo";
    toSend.uid = $.cookie('uid');
    toSend.teacherId = $.cookie('tid');
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
        async : false,
        error : function(request) {
                    $(".mark").show();
                    $("#box").show();
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                if(json.alipayNo) {
                    $("#binding_status").text("已经绑定");
                } else {
                    $("#binding_status").text("立即绑定");
                }
                $("#apliay").val(json.alipayNo);
            } else {
                $(".mark").show();
                $("#box").show();
                $("#bomb").html("帐号信息已失效，请重新登录");
                $("#connect").attr('href','login.html');
                $("#connect").css('left','15%');
                $("#cancel").show();
                $.cookie("uid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                $.cookie("nickName",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                $.cookie("iconUrl",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                $.cookie("tid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
            }
        }
    });
}

//绑定支付宝账号。
function sumbitAliayNo() {
    var apliay = $("#apliay").val();
    var reapliay = $("#reapliay").val();
    var isSubmit = true;
    if(!apliay){
        $("#apliayError").show();
        isSubmit = false;
    }
    if(!reapliay){
        $("#reApliayError").show();
        isSubmit = false;
    }
    if(!isSubmit){
        return;
    }
    if(apliay != reapliay) {
        $("#noEquialError").show();
        return;
    }

    //账号失效
    if(!$.cookie('uid')) {
        $(".mark").show();
        $("#login").show();
    }

    var toSend = new Object();
    toSend.style = "teacher";
    toSend.method = "editAlipayNo";
    toSend.uid = $.cookie('uid');
    toSend.teacherId = $.cookie('tid');
    toSend.alipayNo = apliay;

    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
        async : false,
        error : function(request) {
                    $(".mark").show();
                    $("#system").show();
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                $(".mark").show();
                $("#succ").show();
                $("#binding_status").text("已经绑定");
            } else {
                $(".mark").show();
                $("#erro").show();
            }
        }
    });
}