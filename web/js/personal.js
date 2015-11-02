$(document).ready(function(){
   
    var teacherId = $_GET("tid");
    if(teacherId==null||teacherId==""){
        self.location = 'home.html';
        return;
    }
    var uid = $.cookie('uid');
    var nickName = $.cookie('nickName');
    var iconUrl = $.cookie('iconUrl');
    
    $("#cancell").click(function(){
        $("#login").hide();
        $(".mark").hide();
    });
    $("#cancele").click(function(){
        $("#euid").hide();
        $(".mark").hide();
    });


    if(uid!=null&&nickName!=null&&iconUrl!=null){
        $("#nav-right").html("<li class=\"nav-right\" id=\"nav-right-theme\"><a href=\"\">主题分类</a><ul><li><a href=\"theme.html\" target=\"_self\">留学领航</a></li><li><a href=\"theme_01.html\" target=\"_self\">求职就业</a></li><li><a href=\"theme_03.html\" target=\"_self\">创业助力</a></li><li><a href=\"theme_04.html\" target=\"_self\">校园生活</a></li>"
+"                    <li><a href=\"theme_05.html\" target=\"_self\">猎奇分享</a></li></ul></li><li class=\"nav-right\"><span >|</span></li><li class=\"nav-right\" style='overflow: hidden;max-width: 100px;'><a href =\"controlPanel.html\">"+nickName+"</a></li><li class=\"nav-right\"><span>|</span></li><li class=\"nav-right\" id=\"nav-right-beTutor\"><a href=\"bct1.html\">成为导师</a></li>"    );           
     }else {
        $("#nav-right").html("<li class=\"nav-right\" id=\"nav-right-theme\"><a href=\"\">主题分类</a><ul><li><a href=\"theme.html\" target=\"_self\">留学领航</a></li><li><a href=\"theme_01.html\" target=\"_self\">求职就业</a></li><li><a href=\"theme_03.html\" target=\"_self\">创业助力</a></li><li><a href=\"theme_04.html\" target=\"_self\">校园生活</a></li>"
+"                    <li><a href=\"theme_05.html\" target=\"_self\">猎奇分享</a></li></ul></li><li class=\"nav-right\"><span >|</span></li><li class=\"nav-right\" id=\"nav-right-login\"><a href=\"login.html?callback=theme.html\">登录</a></li><li class=\"nav-right\"><span>|</span>"
+"</li><li class=\"nav-right\" id=\"nav-right-register\"><a href=\"login.html?kind=register\">注册</a></li><li class=\"nav-right\"><span>|</span></li><li class=\"nav-right\" id=\"nav-right-beTutor\"><a href=\"bct1.html\">成为导师</a></li>");
     }         

    $("#nav-right-theme").hover(function(){
        $("#nav-right-theme ul").css("display","block")
    });
    $("#nav-right-theme").mouseleave(function(){
        $("#nav-right-theme ul").css("display","none")
    });

    //-----------加入心愿单------------------------------------
    $(".add-like").click(function(){
        if($(".add-like-text").html()=="加入心愿单"){
            if(uid==null)  {
                $(".mark").show();
                $("#login").show();
                return;
            }
            likeTeacher();
            $(".add-like-img").attr("src","http://image.1yingli.cn/img/add_like_success.png");
            $(".add-like-text").html("已加入心愿单");
        }else if($(".add-like-text").html()=="已加入心愿单"){
            dislikeTeacher()
            $(".add-like-img").attr("src","http://image.1yingli.cn/img/contact_gray_heart.png");
            $(".add-like-text").html("加入心愿单");
        }
        refreshTeacherInfo();
    });

    //获取导师详细信息
    refreshTeacherInfo();
	
	$("#contolShow").show();
    //分页实现
    changePage(page);

    //判断当前用户是否喜欢这个导师
    islikeTeacher();

    //初始化手机微信分享
    setUpWeixinShare();

    //获取相关导师
    getAboutTeachers();

    $("#confirm").click(function(){
        Na = $("#Na").val(); 
        tel = $("#tel").val(); 
        weix = $("#weix").val(); 
        ema = $("#ema").val(); 
        qu = $("#qu").val();
        intr = $("#intr").val();
    });
});


function _getRandomString(len) {  
    len = len || 32;  
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1  
    var maxPos = $chars.length;  
    var pwd = '';  
    for (i = 0; i < len; i++) {  
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));  
    }  
    return pwd;  
}  

//手机微信分享
function setUpWeixinShare(){
    //二维码
    var qrcode = new QRCode(document.getElementById("qrcode"),{
        width:160,
        height:160
    });
    qrcode.makeCode(self.window.location+"");

    $("#wxshare").hover(function(){
        $("#qrcode").css("display","block");
    });
    $("#wxshare").mouseleave(function(){
        $("#qrcode").css("display","none");
    })

    //微信菜单
    $.ajax({
        cache : true,
        type : "GET",
        url : "http://service.1yingli.cn/GetACCESSTOKEN/getAT",
        data : "",
        async : false,
        error : function(request) {
                //alert("Connection error");
        },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            // var json = $.parseJSON(data);
            var ticket1 = json.ticket;
            var timestamp1 = parseInt((new Date()).valueOf()/1000); 
            var noncestr1 = _getRandomString(16);

            var str = "jsapi_ticket="+ticket1+"&noncestr="+noncestr1+"&timestamp="+timestamp1+"&url="+location.href.split('#')[0];
            var signature1 = $.sha1(str);
            wx.config({
                debug:false,
                appId:'wxd042cdef58e2e669',
                timestamp:timestamp1,
                nonceStr:noncestr1,
                signature:signature1,
                jsApiList:['onMenuShareTimeline','onMenuShareAppMessage']
            });       
        }
    });

    wx.ready(function(){
                    //微信分享设置
                    wx.onMenuShareTimeline({
                        title: "【一英里导师】"+ $("#tname").html()+"("+$("#tposition").html()+")", // 分享标题 
                        link: "http://www.1yingli.cn/personal.html?tid="+$_GET("tid"), // 分享链接
                        imgUrl: $("#ticonUrl").attr("src"), // 分享图标
                        success: function () { 
                            // 用户确认分享后执行的回调函数
                            //alert("success1");
                        },
                        cancel: function () { 
                            // 用户取消分享后执行的回调函数
                            //alert("cancel1");
                        }
                    });
                    
                    wx.onMenuShareAppMessage({
                        title: "【一英里导师】"+ $("#tname").html()+"("+$("#tposition").html()+")", // 分享标题
                        desc: $("#tintroduce").html().substr(0,50), // 分享描述  //TODO
                        link: "http://www.1yingli.cn/personal.html?tid="+$_GET("tid"), // 分享链接
                        imgUrl: $("#ticonUrl").attr("src"), // 分享图标
                        type: 'link', // 分享类型,music、video或link，不填默认为link
                        dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                        success: function () { 
                            // 用户确认分享后执行的回调函数
                            //alert("success2");
                        },
                        cancel: function () { 
                            // 用户取消分享后执行的回调函数
                            //alert("cancel2");
                        }
                    });
    });

    wx.error(function(res){
        //alert(res);
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

    });
            
}

//添加喜欢的老师
function likeTeacher(){
    if($.cookie('uid')==null||$.cookie('uid')=="")return;
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "likeTeacher";
    toSend.uid = $.cookie('uid');
    toSend.teacherId = $_GET("tid");
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
            } else {
                $("#euid").show();
                $(".mark").show();
            }
        }
    });
}

//取消喜欢的老师
function dislikeTeacher(){
    if($.cookie('uid')==null||$.cookie('uid')=="")return;
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "dislikeTeacher";
    toSend.uid = $.cookie('uid');
    toSend.teacherId = $_GET("tid");
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
            } else {
                $("#euid").show();
                $(".mark").show();
            }
        }
    });
}

//判断当前用户是否喜欢这个导师
function islikeTeacher(){
    if($.cookie('uid')==null||$.cookie('uid')=="")return;
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "isLikeTeacher";
    toSend.uid = $.cookie('uid');
    toSend.teacherId = $_GET("tid");
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

            if (json.likeTeacher == "true") {
                $(".add-like-img").attr("src","http://image.1yingli.cn/img/add_like_success.png");
                $(".add-like-text").html("已加入心愿单");
            } else {
                $(".add-like-img").attr("src","http://image.1yingli.cn/img/contact_gray_heart.png");
                $(".add-like-text").html("加入心愿单");
            }
        }
    });
}

//获取相关导师
var tids;
function getAboutTeachers() {
    var uid =  $.cookie('uid');
    var tid = $_GET("tid");//当前查看的导师
    var teacherId= $.cookie('tid');//个人信息Tid
    
    var toSend = new Object();
    toSend.style = "function";
    toSend.method = "getRecommendTeacherList";
    if(uid && uid!="null"){
         toSend.uid = uid;
    } else {
         toSend.teacherId = tid;        
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
        async : false,
        error : function(request) {
           // alert("Connection error");
        },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                var data = $.parseJSON(json.data);
                var html = "";
                tids = new Array();
                $.each( data, function(index, content){
                    tids.push(content.teacherId);
                    html = html + "<a href=\"#\" onclick=\"goToAboutTeacher("+content.teacherId+")\"><div class=\"about-block\"><div class=\"about-person-header\"><img src="+content.url+"><label>"+content.name+"</label></div><div class=\"about-person-content\"><p class=\"about-topic\">"+content.topic+"</p><p class=\"about-where\">"+content.simpleInfo+"</p><p class=\"about-like\"><img src=\"http://image.1yingli.cn/img/kelly.png\"> <span>"+content.likenumber+"人想见</span></p></div></div></a>";
                });
                 $("#about_list").html(html);
            }
        }
    });
}

//跳转链接到导师
function goToAboutTeacher(tid){
    var uid =  $.cookie('uid');
    if(uid && uid!="null"){ 
        var toSend = new Object();
        toSend.style = "function";
        toSend.method = "recordRecommend";
        toSend.now_tid = $_GET("tid");
        toSend.to_tid= tid + "";
        toSend.uid = uid;
        toSend.recommend_list = tids;
        $.ajax({
            cache : true,
            type : "POST",
            url : config.base_url,
            data : $.toJSON(toSend),
            async : false,
            error : function(request) {},
            success : function(data, textStatu) {
                }
        });
    }
    
    self.location = "personal.html?tid="+tid;
}

//刷新导师个人信息
function refreshTeacherInfo(){
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getTeacherInfo";
    toSend.teacherId = $_GET("tid");
    toSend.uid = $.cookie('uid');
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
        async : false,
        error : function(request) {
           // alert("Connection error");
        },
        success : function(data, textStatu) {
                var json = eval("(" + data + ")");
                if (json.state == "success") {
                    var name = json.name;
                    var iconUrl = json.iconUrl;
                    var position = json.simpleShow2;
                    var companyName = json.simpleShow1;
                    var likeNo = json.likeNo;
                    if(json.bgUrl){
                        $("#personal-header").find(".personal-bg").attr("src", json.bgUrl); 
                    }
                    $("#tname").html(name);
                    $("#tposition").html(json.simpleinfo);
                    $("#tlikeNo").html(likeNo+"人想见");
                    $("#ticonUrl").attr("src",iconUrl==""?"http://image.1yingli.cn/img/img.png":iconUrl);
                    $("#ticonUrl1").attr("src",iconUrl==""?"http://image.1yingli.cn/img/img.png":iconUrl);
                    $("#name").html(name);
                    $("#companyName1").html(json.simpleinfo);
                    //微博分享按钮初始化
                    $("#wbshare").attr("default_text","【一英里导师】"+name+"("+$("#tposition").html()+")");
                    $("#wbshare1").attr("default_text","【一英里导师】"+name+"("+$("#tposition").html()+")");
                    var link = iconUrl.split("\@")[0];
                    $("#wbshare").attr("pic",encodeURIComponent(iconUrl==""?"http://image.1yingli.cn/img/img.png":link));
                    $("#wbshare1").attr("pic",encodeURIComponent(iconUrl==""?"http://image.1yingli.cn/img/img.png":link));
                    var weibojs = "<script src=\"http://tjs.sjs.sinajs.cn/open/api/js/wb.js\" type=\"text/javascript\" charset=\"utf-8\"></script>";

                    $("head").append(weibojs);
                    var tips = json.tips;
                    var td = $.parseJSON(tips);
                    $.each(td,function(index,content){
                        var tmp = $("#tip"+content.id);
                        if(tmp!=null){
                            tmp.css('background','#56bbe8');
                        }
                    });


                    var address = json.address;
                    var timeperweek = json.timeperweek;
                    var introduce = json.introduce;

                    $("#tintroduce").html(introduce);
                    $("#ttimeperweek").html(timeperweek+"次");
                    $("#taddress").html(address);

                    var talkWay = json.talkWay;
                    var commentNo = json.commentNo;
                    //总页数设置
                    totalPage = Math.ceil(commentNo/12);
                    $("#commentNo").html(commentNo+"条评价");

                    var ts = talkWay.split(",");
                    var html = "";
                    $.each(ts,function(index,content){
                        if(content=="1"){
                            html = html + "<li><a href=\"javascript:void(0)\"><img src=\"http://image.1yingli.cn/img/contact_her_skype.png\" alt=\"\"/></a></li>";
                        }else if(content=="2"){
                            html = html + "<li><a href=\"javascript:void(0)\"><img src=\"http://image.1yingli.cn/img/contact_her_texting.png\" alt=\"\"/></a></li>";
                        }else if(content=="3"){
                            html = html + "<li><a href=\"javascript:void(0)\"><img src=\"http://image.1yingli.cn/img/contact_her_wechat.png\" alt=\"\"/></a></li>";
                        }
                    });

                    $("#talkWay").html(html);

                    var freeTime = json.freeTime;
                    //TODO

                    var teacherId = json.teacherId;
                    if(timeperweek == "0") {
                        $("#order-him p").html("本周不可预约");
                    } else {
                        $("#order-him").click(function(){
                            var uid = $.cookie('uid');
                            if(uid == null){
                                $(".mark").show();
                                $("#login").show();
                            } else {
                                $(".mark").fadeIn();
                                $("#frame").fadeIn();
                            }
                        });
                    }
                    $("#order-him").attr('href','order('+teacherId+')');
                    $("#rent").attr('href','order('+teacherId+')');

                    var price = json.price;
                    var serviceTime = json.serviceTime;
                    var serviceTitle = json.serviceTitle;
                    var serviceContent = json.serviceContent;
                    $("#scontent").html(serviceContent);
                    $("#stitle").html(serviceTitle);
                    $("#sprice").html("￥"+price+"元/"+serviceTime+"小时");
                    $("#sprice1").html("￥"+price+"元/"+serviceTime+"小时");

                    var checkEmail = json.checkEmail;
                    var checkPhone = json.checkPhone;
                    var checkDegree = json.checkDegree;
                    var checkIDCard = json.checkIDCard;
                    var checkWork = json.checkWork;
                    if(checkIDCard=="true"){
                        $("#checkID").html("实名认证");
                    }else{
                        $("#checkID").html("未实名认证");
                    }
                    if(checkDegree=="true"){
                        $("#checkDegree").html("学位认证");
                    }else{
                        $("#checkDegree").html("未学位认证");
                    }
                    if(checkWork=="true"){
                        $("#checkWork").html("工作认证");
                    }else{
                        $("#checkWork").html("未工作认证");
                    }
                    if(checkPhone=="true"){
                        $("#checkPhone").html("手机认证");
                    }else{
                        $("#checkPhone").html("未手机认证");
                    }
                    if(checkEmail=="true"){
                        $("#checkMail").html("邮箱认证");
                    }else{
                        $("#checkMail").html("未邮箱认证");
                    }

                    var schoolExp = $.parseJSON(json.studyExperience);
                    var workExp = $.parseJSON(json.workExperience);
                    html = "";
                    $.each(schoolExp,function(index,content){
                        var st = content.startTime.split(",");
                        var et = content.endTime.split(",");
                        var showTime = "";
                        if(et[0]=="至今") {
                            showTime = st[0]+"年"+st[1]+"月-"+et[0];
                        }else {
                            showTime = st[0]+"年"+st[1]+"月-"+et[0]+"年"+et[1]+"月";
                        }

                        html = html + "<p><span style='width:500px;height:30px;overflow:hidden;display:block;float:left;'>"+content.schoolName +" "+content.major+" "+content.degree + "</span><lable id='schooltime' style='float:right;'>"+showTime+"</lable></p>";
                    });

                    $.each(workExp,function(index,content){
                        var st = content.startTime.split(",");
                        var et = content.endTime.split(",");
                        var showTime = "";
                        if(et[0]=="至今") {
                            showTime = st[0]+"年"+st[1]+"月-"+et[0];
                        }else {
                            showTime = st[0]+"年"+st[1]+"月-"+et[0]+"年"+et[1]+"月";
                        }
                        html = html + "<p><span style='width:500px;height:30px;overflow:hidden;display:block;float:left;'>"+content.companyName+" "+content.position+" " + "</span><lable id='worktime' style='float:right;'>"+showTime+"</lable></p>";
                    });
                    
                    $("#explist").html(html);

                    var talkWay = json.talkWay;
                    if(talkWay==""){
                        talkWay = "[]";
                    }
                    var li = $.parseJSON(talkWay);

                    $.each(li, 
                        function(index, content){
                            switch(content){
                                case 10:
                                $("#li11").html("<img src='http://image.1yingli.cn/images/tel01.png'></li>");
                                break;  
                                case 11:
                                $("#li11").html("<li id='' style='float:left; width:39px; height:35px;'><img src='http://image.1yingli.cn/images/tel11.png'></li>");
                                break;
                                case 20:
                                $("#li22").html("<img src='http://image.1yingli.cn/images/tel02.png'></li>");
                                break;
                                case 22:
                                $("#li22").html("<li id='' style='float:left; width:39px; height:35px; margin-left:5px;'><img src='http://image.1yingli.cn/images/tel22.png'></li>");
                                break;
                                case 30:
                                $("#li33").html("<li id='' style='float:left; width:39px; height:35px; margin-left:5px;'><img src='http://image.1yingli.cn/images/tel03.png'></li>");
                                break;
                                case 33:
                                $("#li33").html("<img src='http://image.1yingli.cn/images/tel33.png'></li>");
                                break;
                            }
                    });
                } else {
                    alert(json.msg);
                }
            }
        });

}

var totalPage = 1;
var page = 1;
function firstPage() {
    if(page == 1) {
        return;
    } else {
        changePage(1);
    }
}
function nextPage(){
    if(page >= totalPage) {
        return;
    } else {
        page = page + 1;
        changePage(page);
    }
}
function lastPage(){
    if(page <= 1) {
        return;
    } else {
        page = page - 1;
        changePage(page);
    }
}
function finalPage() {
    if(page == totalPage) {
        return;
    } else {
        changePage(totalPage);
    }
}
function changePage(p){
    var toSend = new Object();
    toSend.style = "teacher";
    toSend.method = "getCommentList";
    toSend.teacherId = $_GET("tid");
    toSend.page = p+"";

    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
        async : false,
        error : function(request) {
                    //alert("Connection error");
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                var data = $.parseJSON(json.data);
                var html = "";
                page = p;
                $.each( data, function(index, content){
                    html = html + "<li><div class=\"assess-content\"><div class=\"assess-content-top\"><div class=\"assess-face\"><img src=\""+(content.iconUrl!=""?content.iconUrl:"http://image.1yingli.cn/img/img.png")+"\" style=\"border-radius:50%\" alt=\"\"/></div><h3>"+content.nickName+"</h3><p>&nbsp;&nbsp;&nbsp;"+content.createTime.replace(new RegExp("/","gm"), "-")+"</p><ul class=\"assess-star\">";
                    for(var i = 1;i<=content.score;i++){
                        html = html + "<li><img src=\"http://image.1yingli.cn/img/light_star_for_tutor_assess.png\" /></li>";
                    }                    
                    html = html +"</ul></div><div class=\"assess-content-center\"><p>"+content.content+"</p><p>参与话题“"+content.serviceTitle+"”</p></div></div></li>";
                });
                $("#commentList").html(html);
            } else {
                //alert(json.msg);
            }
        }
    });
    
    //先删除后添加页数
    //页码设置  
    var basePage = 10;
    var leastPage = 2
    var showPage = 4;
    var mostPage = parseInt(leastPage) + parseInt(showPage);
    var afterShowPage = parseInt(page) + showPage;
    var beforeShowPage = parseInt(page) - showPage;
    $(".page").html("");
    var html1 = "";
    if(totalPage != 0){
        html1 = "<a id='btnStart' href='javascript:firstPage()'><img src='http://image.1yingli.cn/img/firstPage.png'></a><a id='btnLast' href='javascript:lastPage();' style='padding: 0px;'><img src='http://image.1yingli.cn/img/lastPage.png'></a><a id='btnNext' href='javascript:nextPage();' style='padding: 0px;'><img src='http://image.1yingli.cn/img/nextPage.png'></a><a id='btnEnd' href='javascript:finalPage();'><img src='http://image.1yingli.cn/img/finalPage.png'></a>";
    }
    $(".page").html(html1);
    if(totalPage <= basePage) {
        for (var i = 1; i <=totalPage; i++) {
            $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");   
        };
    } else {
        for (var i = 1; i <=totalPage; i++) {
            if(page == 1) {
                $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                if(i == 5) {
                    $("#btnNext").before("...");
                    $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                    break;
                }
            } else if(page <=mostPage) {
                $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                if(i == afterShowPage) {
                     $("#btnNext").before("...");
                     $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                     break;
                }
            } else {
                if(i<=leastPage) {
                    $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                    if(i == leastPage) { $("#btnNext").before("...");}
                }
                if(beforeShowPage <= i && i <= afterShowPage){
                    $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                }
                if(i == afterShowPage) {
                    if(afterShowPage !=totalPage ) {
                        $("#btnNext").before("..."); 
                        $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                        break;
                    }
                }
            }
        };
    }
    $("#btn" + page).attr("class", "active");
}
