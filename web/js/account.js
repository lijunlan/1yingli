$(document).ready(function(){
    var u = $.cookie('uid');
    var n = $.cookie('nickName');
    var i = $.cookie('iconUrl');
    // if(u==null||n==null||i==null){self.location='login.html';return;}
    // $("#nickName").html(n);
    // $("#bigNickName").html(n);
    // if(i!=""){
    //     $("#iconUrl").attr("src",i);
    //     $("#bigIcon").attr("src",i);
    // }

    //获取电话
    refreshPhone();

    //获取邮箱信息
    refreshEmail();
});

//自动获取手机号码和邮箱号
function refreshPhone(){
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getPhone";
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
                $("#write-phNumber").val(json.phone);
            } else {
              
            }
        }
    });
}
function refreshEmail(){
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getEmail";
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
                $("#write-Email").val(json.email);
            } else {
                
            }
        }
    });
}

//获取手机号码倒计时
var countdown = 60;
function settime() { 
    var val = $("#send-identify");
    if (countdown == 0) { 
        val.removeAttr("disabled");    
        val.html("发送手机验证码");
        countdown = 60; 
        return;
    } else { 
        val.attr("disabled", "disabled"); 
        val.html ("(" + countdown + "S)后重新获取"); 
        countdown--; 
    } 
    setTimeout(function() { 
        settime() 
    },1000);
}
var countdown1 = 60;
function settime1() { 
    var val = $("#send-identify1");
    if (countdown1 == 0) { 
        val.removeAttr("disabled");    
        val.html("发送邮箱验证码");
        countdown1 = 60; 
        return;
    } else { 
        val.attr("disabled", "disabled"); 
        val.html ("(" + countdown1 + "S)后重新获取"); 
        countdown1--; 
    } 
    setTimeout(function() { 
        settime1() 
    },1000);
}

//修改手机号码提交
function changePhone(){
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "changePhone";
    toSend.uid = $.cookie('uid');
    var phone = $("#write-phNumber").val();
    var checkNo = $("#write-identify").val();
    if(phone==null||phone==""){
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请先填写手机号");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                             // alert("请先填写手机号");
        return;
    }
    if(checkNo==null||checkNo==""){
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请输入验证码");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                               // alert("请输入验证码");
        return;
    }
    toSend.checkNo = checkNo;
    toSend.phone = phone;
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
                $("#stel").show();
            } else { 
                //settime();
                $(".mark").show();
                $("#etel").show();
            }
        }
    });
}
//获取手机验证码。
function getCheckPhoneNo(){
    var toSend = new Object();
    toSend.style = "function";
    toSend.method = "getCheckNo";
    var phone = $("#write-phNumber").val();
    if(phone==null||phone==""){
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请先填写手机号");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                                   // alert("请先填写手机号");
        return;
    }
    if(!isTel(phone)){
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请先填写正确的手机号");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                                          // alert("请填写正确的手机号");
        return;
    }
    toSend.username = phone;
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
                settime();
            } else { 
                //settime();
                $(".mark").show();
                $("#box").show();
                $("#bomb").html("已经获取验证码，请勿重复获取");
                $("#connect").attr('href','##');
                $("#connect").click(function(){
                    $(".mark").hide();
                    $("#box").hide();
                })
            }
        }
    });
}
//修改邮箱号提交
function changeEmail(){
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "changeEmail";
    toSend.uid = $.cookie('uid');
    var email = $("#write-Email").val();
    var checkNo = $("#write-EMidentify").val();
    if(email==null||email==""){
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请先填写邮箱");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                                  // alert("请先填写邮箱");
        return;
    }
    if(!isEmail(email)) {
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请先填写正确的邮箱");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                                 // alert("请填写正确的邮箱");
        return;
    }
    if(checkNo==null||checkNo==""){
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请先填写验证码");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                                        // alert("请输入验证码");
        return;
    }
    toSend.checkNo = checkNo;
    toSend.email = email;
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
                $("#semil").show();
            } else { 
                $(".mark").show();
                $("#box").show(); 
                $("#bomb").html("验证码输入有误");
                $("#connect").attr('href','javascript:void(0);')
                $("#connect").click(function(){
                    $(".mark").hide();
                    $("#box").hide(); 
                });                                  
                return;
            }
        }
    });
}
//获取邮箱验证码。
function getCheckEmailNo(){
    var toSend = new Object();
    toSend.style = "function";
    toSend.method = "getCheckNo";
    var email = $("#write-Email").val();
    if(email==null||email==""){
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请先填写邮箱");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                                  // alert("请先填写邮箱");
        return;
    }
    if(!isEmail(email)) {
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("请先填写正确的邮箱");
        $("#connect").attr('href','javascript:void(0);')
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });                                  
        return;
    }
    toSend.username = email;
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
                    settime1();
                } else { 
                    $(".mark").show();
                    $("#box").show();
                    $("#bomb").html("已经获取验证码，请勿重复获取");
                    $("#connect").attr('href','##');
                    $("#connect").click(function(){
                        $(".mark").hide();
                        $("#box").hide();
                    })
                }
            }
    });
}

//修改密码
var publickey = "8959d2ced61bee338accd16794538ec0a49da0655ddca8fa2461d4cf419dafaf4d7c47813f6ac8c6e5646a2beb08cccf4184a831e683a631e3c528b908deecc57235d03935d0650fbe53d44f717da7f5d1622e7405a3b4f06377eb506880dae21e5065c878c03d85113e068ac82af6b29037d57163d9a311807bee654927d349";
function changePassword(){
    var oldpassword = $("#password").val();
    var newpassword = $("#new-password").val();
    var confirmpassword = $("#confirm-password").val();
    if(oldpassword==null||newpassword==null||confirmpassword==null||oldpassword==""||newpassword==""||confirmpassword==""){
        $(".mark").show();
        $("#mess").show();
        $("#hideMiss").click(function(){
            $(".mark").hide();
            $("#mess").hide();
        })
        return;
    }
    var reg=/^[0-9]+$/;
    if(reg.test(newpassword)||newpassword.length<6||newpassword.length>20)
    {
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("密码至少6位数，且不能全为数字。");
        $("#connect").attr('href','javascript:void(0);');
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        }); 
        return;   
    }
    if(newpassword != confirmpassword){
        $(".mark").show();
        $("#box").show(); 
        $("#bomb").html("输入新密码不一致");
        $("#connect").attr('href','javascript:void(0);');
        $("#connect").click(function(){
            $(".mark").hide();
            $("#box").hide(); 
        });   
        return;                             
    }
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "changePassword";
    toSend.uid = $.cookie('uid');
    toSend.oldpassword = encryptedStr(oldpassword,publickey);
    toSend.password = encryptedStr(newpassword,publickey);
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
                    $("#bomb").html("修改密码成功");
                    $("#connect").attr('href','login.html')
                    $.cookie("uid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("nickName",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("iconUrl",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("tid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});                                  
                } else {
                    if(json.msg=="old password is wrong"){
                        $(".mark").show();
                        $("#box").show(); 
                        $("#bomb").html("输入的旧密码有误");
                        $("#connect").attr('href','javascript:void(0);')
                        $("#connect").click(function(){
                            $(".mark").hide();
                            $("#box").hide(); 
                        }); 
                    }else{
                        $(".mark").show();
                        $("#box").show(); 
                        $("#bomb").html("密码修改失败");
                        $("#connect").attr('href','javascript:void(0);')
                        $("#connect").click(function(){
                            $(".mark").hide();
                            $("#box").hide(); 
                        });
                    }
                }
            }
    });
}
