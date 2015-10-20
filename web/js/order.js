﻿$(document).ready(function(){
    var uid = $.cookie('uid');
    info();
    $("#close").click(function(){
        $(".mark").fadeOut();
        $("#frame").fadeOut();
    });

    $("#confirm").click(function(){
        var pname = $("#pname").val();
        var ptel = $("#ptel").val();
        var pemil = $("#pemil").val();
        var pweixin = $("#pweixin").val();

        var isSubmit = true;
        if(pname == "" ){
            $("#warnname").show();
            isSubmit = false;
        }
        if(ptel == "" ){
            $("#warntel").show();
            isSubmit = false;
        }
         if(pemil == "" ){
            $("#warnemil").show();
            isSubmit = false;
        }
         if(pweixin == "" ){
            $("#warnweixin").show();
            isSubmit = false;
        }
        if(!isSubmit) {
            return;
        }

        $(".mark").fadeIn();
        $("#frame").fadeOut();
        $("#frame1").fadeIn();
        
    });

    $("#close1").click(function(){
        $(".mark").fadeOut();
        $("#frame1").fadeOut();
    });

    $("#psend").click(function(){
        var t = document.all.pquestion;
        var p = document.all.pIntroduce;
        if (t.value.length > 500)
        {
            $("#length1").show();
            if(p.value.length > 500){
                $("#length2").show();
            }
        }else if(p.value.length > 500){
            $("#length2").show();
        }else{
            $("#length1").hide();
            $("#length2").hide();
            $("#frame1").hide();
            $("#pquestion").val();
            $("#pIntroduce").val();
            $("#ptime").val();
            var discount = $("#discount").val();
            if(discount == ""){
                ordern();
            } else {
                ordery();
            }
            
        }
    });

    
});


function ordern(){
    if($.cookie('uid')==null||$.cookie('uid')=="")return;
    var toSend = new Object();
    toSend.style = "order";
    toSend.method = "createOrder";
    toSend.uid = $.cookie('uid');
    toSend.question = $("#pquestion").val();
    toSend.userIntroduce = $("#pIntroduce").val();
    toSend.teacherId = $_GET("tid");
    toSend.selectTime = $("#ptime").val();
    toSend.name = $("#pname").val();
    toSend.phone = $("#ptel").val();
    toSend.email = $("#pemil").val();
    toSend.contact = $("#pweixin").val();
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
                    $("#succ").show();
                } else {
                    $(".mark").show();
                    $("#erro").show();
                }
            }
    });
}


function ordery(){
    if($.cookie('uid')==null||$.cookie('uid')=="")return;
    var toSend = new Object();
    toSend.style = "order";
    toSend.method = "createOrder";
    toSend.uid = $.cookie('uid');
    toSend.question = $("#pquestion").val();
    toSend.userIntroduce = $("#pIntroduce").val();
    toSend.teacherId = $_GET("tid");
    toSend.selectTime = $("#ptime").val();
    toSend.name = $("#pname").val();
    toSend.phone = $("#ptel").val();
    toSend.email = $("#pemil").val();
    toSend.contact = $("#pweixin").val();
    toSend.voucher = $("#discount").val();
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
                    $("#succ").show();
                } else {
                    if(json.msg == "voucher is not existed"){
                        $("#disco").html("优惠码不存在");
                        $("#frame1").show();
                    } else if(json.msg == "voucher is overdue"){
                        $("#disco").html("优惠码已过期");
                        $("#frame1").show();
                    } else if(json.msg == "voucher has been used"){
                        $("#disco").html("优惠码被使用过了");
                        $("#frame1").show();
                    } else if(json.msg == "BAD PHONE NUMBER OR BAD EMAIL"){
                        $(".mark").show();
                        $("#box").show(); 
                        $("#bomb").html("输入的邮箱或电话号码有误，请重新修改");
                        $("#connect").attr('href','javascript:void(0);');
                        $("#connect").click(function(){
                            $("#box").hide();
                            $("#frame").show();
                        }); 
                    } else {
                        $(".mark").show();
                        $("#erro").show();
                    }
                }
            }
    });
}


function info(){
    if($.cookie('uid')==null||$.cookie('uid')=="")return;
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getIntroduce";
    toSend.uid = $.cookie('uid');
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
                    $("#pIntroduce").val(json.introduce);
                } else {
                    $(".mark").show();
                    $("#box").show();
                    $("#bomb").html("帐号已经失效，请重新登录");
                    $("#connect").attr('href','login.html');
                    $.cookie("uid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
 		            $.cookie("nickName",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("iconUrl",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("tid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                }
            }
    });
}