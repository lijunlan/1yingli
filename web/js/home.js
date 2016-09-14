$(document).ready(function(){
    var uid = $.cookie('uid');
    var nickName = $.cookie('nickName');
    var iconUrl = $.cookie('iconUrl');
    if(uid!=null && uid!='null'){
        $("#userconsole").html("<li style='max-width:150px;overflow:hidden;'><a href =\"controlPanel.html\">"+nickName+"</a></li>");           
    }else {
        $("#userconsole").html("<li><a href=\"login.html\">登录</a></li><span>|</span><li><a href=\"login.html?kind=register\">注册</a></li>");
    }
	
	//滚动效果
	$(window).scroll(function(){
		if($(document).scrollTop()>=600){
			$(".header-top").css("background","rgba(0,0,0,0.5)");
		}else {
			$(".header-top").css("background","");
		}
	});
	
	//主题分类动态效果
    $("#content-img1").hover(function(){
        $(".content-span1").css("z-index","-3");
        $("#img-shade01").css("z-index","99");
        $(".img-shade h1").css({"animation-name":"moveH1",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "font-family": "'Hiragino Sans GB','Microsoft YaHei','WenQuanYi Micro Hei',Arial,Helvetica,sans-serif",
            "animation-delay":"0s",
            "animation-iteration-count":"1"});
        $(".img-shade div").css({"animation-name":"moveP",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"})
    });
    $("#content-img1").mouseleave(function(){
        $(".content-span1").css("z-index","1");
        $("#img-shade01").css("z-index","-2");
        $(".img-shade h1").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
        $(".img-shade div").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
    });
    $("#content-img2").hover(function(){
        $(".content-span2").css("z-index","-3");
        $("#img-shade02").css("z-index","99");
        $(".img-shade h1").css({"animation-name":"moveH1",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"});
        $(".img-shade div").css({"animation-name":"moveP",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"})
    });
    $("#content-img2").mouseleave(function(){
        $(".content-span2").css("z-index","1");
        $("#img-shade02").css("z-index","-2");
        $(".img-shade h1").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
        $(".img-shade div").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
    });
    $("#content-img3").hover(function(){
        $(".content-span3").css("z-index","-3");
        $("#img-shade03").css("z-index","99");
        $(".img-shade h1").css({"animation-name":"moveH1",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"});
        $(".img-shade div").css({"animation-name":"moveP",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"});
    });
    $("#content-img3").mouseleave(function(){
        $(".content-span3").css("z-index","1");
        $("#img-shade03").css("z-index","-2");
        $(".img-shade h1").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
        $(".img-shade div").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
    });
    $("#content-img4").hover(function(){
        $(".content-span4").css("z-index","-3");
        $("#img-shade04").css("z-index","99");
        $(".img-shade h1").css({"animation-name":"moveH1",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"});
        $(".img-shade div").css({"animation-name":"moveP",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"});
    });
    $("#content-img4").mouseleave(function(){
        $(".content-span4").css("z-index","1");
        $("#img-shade04").css("z-index","-2");
        $(".img-shade h1").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
        $(".img-shade div").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
    });
    $("#content-img5").hover(function(){
        $(".content-span5").css("z-index","-3");
        $("#img-shade05").css("z-index","99");
        $(".img-shade h1").css({"animation-name":"moveH1",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"});
        $(".img-shade div").css({"animation-name":"moveP",
            "animation-duration": "1s",
            "animation-timing-function":"ease",
            "animation-delay":"0s",
            "animation-iteration-count":"1"});
    });
    $("#content-img5").mouseleave(function(){
        $(".content-span5").css("z-index","1");
        $("#img-shade05").css("z-index","-2");
        $(".img-shade h1").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
        $(".img-shade div").css({"animation-name":"",
            "animation-duration": "",
            "animation-timing-function":"",
            "animation-delay":"",
            "animation-iteration-count":""});
    });

    //主题下拉
	$("#nav-right-theme").hover(function(){
        $(".triangle-uup").css("display","block");
        $(".triangle-uup-ul").css("display","block")
    });
    $("#nav-right-theme").mouseleave(function(){
        $(".triangle-uup").css("display","none");
        $(".triangle-uup-ul").css("display","none");
    });

	//视频按钮显示和变换
    $(".green-little-title").hover(function(){
        $(".green-little-title").attr("src","http://image.1yingli.cn/img/play_start.png");
    });
    $(".green-little-title").mouseleave(function(){
        $(".green-little-title").attr("src","http://image.1yingli.cn/img/play_stop.png");
    });
});

//播放视频
function showPaly() {
	$("#player").show();
	var player = new Clappr.Player({source: "http://video.1yingli.cn/onemile.mp4", width:960, height:540, parentId:"#playerShow", autoPlay: true,mute:false, poster:"http://image.1yingli.cn/img/green.png"});	
}
function stopPlayer() {
	$("#player").hide();
	$("#playerShow").html(null);	
}

