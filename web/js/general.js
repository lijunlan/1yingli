    
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
        $("#iconView").attr("src",i);
        $("#picture-src").val(i);
    }

    refreshUserInfo();

    $("#select-picture-src").uploadifive({
        'buttonText'   : '浏览',
        'buttonClass'  : 'file-box',
        'queueID'      : 'imagelist',
        'auto'         : false,
        'method'       : 'post',
        'fileType'     : 'image/*',
        'queueSizeLimit' : 1,
        'multi'        : false,
        'uploadScript' : 'http://service.1yingli.cn/yiyingliService/changeIcon',
        'formData'         : {'uid' : ''},
        'onInit'       : function() {
        },
        'onFallback' : function() {
        },
        'onSelect' : function(queue) {
        	$('#select-picture-src').data('uploadifive').settings.formData = { 'uid': $.cookie('uid')};
        	$('#select-picture-src').uploadifive('upload');
        },
        'onUpload' : function(filesToUpload) {
           
        },
        'onError' : function(errorType) {
        },
        'onUploadComplete' : function(file, data) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                $("#picture-src").val(json.iconUrl);
                $.cookie("iconUrl",json.iconUrl,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                location.reload();
            } else {
                $(".mark").show();
                $("#box").show();
                $("#bomb").html("上传信息失败");
                $("#connect").attr('href','general.html');
            }
        }
    });

    $("#intro").mousedown(function(){
        if($("#intro").val().length>500) {
            $("#intro_span").css("color","red");
        }else {
            $("#intro_span").css("color","#34495e");
        }
    });
});

function changeInfo(){
    var toSend = new Object();
    var isTrue = true;
    if(!$("#nickname").val()) {
        isTrue = false;
        $("#nickname_span").css("color","red");
    }
    if(!$("#name").val()) {
        isTrue = false;
        $("#name_span").css("color","red");
    }
    if($("#intro").val().length>500) {
        isTrue = false;
        $("#intro_span").css("color","red");
    }
    if(!isTrue) {
        return;
    }
    toSend.style = "user";
    toSend.method = "changeInfo";
    toSend.uid = $.cookie('uid');
    toSend.name= $("#name").val();
    toSend.nickName = $("#nickname").val();    
    toSend.phone = $("#phone").val();
    toSend.address = $("#address").val();
    toSend.email = $("#email").val();
    toSend.resume = $("#intro").val();
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
                    $(".mark").show();
                    $("#box").show();
                    $("#bomb").html("修改信息成功");
                    $("#connect").attr('href','general.html');
                    refreshUserInfo();
                } else {
                    $(".mark").show();
                    $("#box").show();
                    $("#bomb").html("修改信息错误");
                    $("#connect").attr('href','general.html');
                }
            }
    });
}

function refreshUserInfo(){
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getInfo";
    toSend.uid = $.cookie('uid');
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
            async : true,
            error : function(request) {
                        $(".mark").show();
                        $("#box").show();
                    },
            success : function(data, textStatu) {
                var json = eval("(" + data + ")");
                if (json.state == "success") {
                    $("#nickname").val(json.nickName);
                    $("#name").val(json.name);
                    $("#phone").val(json.phone);
                    $("#email").val(json.email);
                    $("#address").val(json.address);
                    $("#intro").val(json.resume);
                    $.cookie("nickName",$("#nickname").val(),{path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $("#nickName").html($("#nickname").val());
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
