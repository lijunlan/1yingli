﻿$(document).ready(function(){
    var uid = $.cookie('uid');
    var nickName = $.cookie('nickName');
    var iconUrl = $.cookie('iconUrl');

    $("#header").html("<div class='header-left' id='header-left'> <a href='home.html'><img src='http://image.1yingli.cn/img/logo_white.png' class='logo' id='logo' alt=''/></a> <form action='' id='search-box' method='get'> <input type='text' id='search-input' placeholder='搜索：学校 / 专业 / 导师' style='padding-left: 7px;border-radius:8px' onkeydown='KeyDown();'/> <img src='http://image.1yingli.cn/img/search_white.png' id='search-img' onClick='searchByInput();' style='cursor: pointer;margin-left:5px;'/> </form></div> <div class='header-left' id='header-right'> <ul class='nav-left-ul' id='nav-right'></ul></div>");
    if(uid!=null&&uid!="null"){
        $("#nav-right").html("<li class=\"nav-right\" id=\"nav-right-theme\"><a href=\"\">主题分类</a><ul><li><a href=\"theme.html\" target=\"_self\">留学领航</a></li><li><a href=\"theme_01.html\" target=\"_self\">求职就业</a></li><li><a href=\"theme_03.html\" target=\"_self\">创业助力</a></li><li><a href=\"theme_04.html\" target=\"_self\">校园生活</a></li>"
        +"                    <li><a href=\"theme_05.html\" target=\"_self\">猎奇分享</a></li></ul></li><li class=\"nav-right\"><span >|</span></li><li class=\"nav-right\" style='overflow: hidden;max-width: 100px;'><a href =\"controlPanel.html\">"+nickName+"</a></li><li class=\"nav-right\"><span>|</span></li><li class=\"nav-right\" id=\"nav-right-beTutor\"><a href=\"bct1.html\"><!--<img src='http://image.1yingli.cn/img/btn_teacher.png' alt='图片出错了....' width='72' height='27'>-->成为导师</a></li>"    );           
    }else {
        $("#nav-right").html("<li class=\"nav-right\" id=\"nav-right-theme\"><a href=\"\">主题分类</a><ul><li><a href=\"theme.html\" target=\"_self\">留学领航</a></li><li><a href=\"theme_01.html\" target=\"_self\">求职就业</a></li><li><a href=\"theme_03.html\" target=\"_self\">创业助力</a></li><li><a href=\"theme_04.html\" target=\"_self\">校园生活</a></li>"
        +"                    <li><a href=\"theme_05.html\" target=\"_self\">猎奇分享</a></li></ul></li><li class=\"nav-right\"><span >|</span></li><li class=\"nav-right\" id=\"nav-right-login\"><a href=\"login.html?callback=theme.html\">登录</a></li><li class=\"nav-right\"><span>|</span>"
        +"</li><li class=\"nav-right\" id=\"nav-right-register\"><a href=\"login.html?kind=register\">注册</a></li><li class=\"nav-right\"><span>|</span></li><li class=\"nav-right\" id=\"nav-right-beTutor\"><a href=\"bct1.html\"><!--<img src='http://image.1yingli.cn/img/btn_teacher.png' alt='图片出错了....' width='72' height='27'>-->成为导师</a></li>");
    }

    $("#nav-right-theme").hover(function(){
        $("#nav-right-theme ul").css("display","block")
    });
    $("#nav-right-theme").mouseleave(function(){
        $("#nav-right-theme ul").css("display","none")
    });

    $("#more").click(function(){
        $(".course-one").slideDown(500);
    });
});

