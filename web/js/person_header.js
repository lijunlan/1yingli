$(document).ready(function(){

	<!-- header top-->
	$(".g-hd").html("<div class='left f-left'><a href='home.html' class='u-logo f-left'><img src='http://image.1yingli.cn/img/logo1.png' style='width:103px;height:32px;' alt='图片出错了....'></a><div class='m-search f-left'><input placeholder='搜索：学校 / 专业 / 导师' class='u-input' id='search-input' style='text-indent:7px;' onkeydown='KeyDown();'><a href='#' class='search' onClick='searchByInput();'><img src='http://image.1yingli.cn/img/search_blue.png' style='width:34px; height:29px; display:block'></a></div></div><div class='right f-right'><a href='controlPanel.html' class='m-thumb f-left' ><img id='iconUrl' src='http://image.1yingli.cn/img/img.png' style='width:30px;height:30px;border-radius:50%'></a><div class='m-name f-left' id='menu' style='max-width:140px;'><a href='javascript:;' id='nickName' class='' style='overflow:hidden;text-overflow:ellipsis;white-space: nowrap;display: block;max-width: 100px;height: 30px;'></a><ul><li id='onebtn' style='width:80px;background:#fff' align='center'><a style='color:#333' href='javascript:logout()'>退出</a></li></ul></div><a href='controlPanel.html' class='u-msg f-left'><!--<i id='noti_no'>0</i>--></a><a href='bct1.html' class='u-become f-left' style='position:relative;top:4px;' id='btn_teacher'>成为导师</a></div>");
	
    //header 退出滑动效果。
    $("#menu").hover(function(){
        $(".m-name ul").css("display","block");
    });
    $("#menu").mouseleave(function(){
        $(".m-name ul").css("display","none");
    });  

    $("#onebtn").hover(function(){
        $("#onebtn").css("background","#56bbe8");
    });
    $("#onebtn").mouseleave(function(){
        $("#onebtn").css("background","#fff");
    }); 
    
    $("#btn_teacher").hover(function(){
        $(this).css("color","#56bbe8");
    });
    $("#btn_teacher").mouseleave(function(){
        $(this).css("color","#000");
    }); 
    <!--header break -->
    //tutorSheet.html
    $(".top-nav").html("<div class='nav-center'><ul><li><a href='controlPanel.html'>消息中心</a></li><li><a href='yourTutor.html'>我的导师</a></li><li><a href='tutor.html'>导师中心</a></li><li><a href='general.html'>个人资料</a></li><li> <a href='likeList.html'>心愿名单</a> </li></ul></div>");

    //getEmailCount();

    //下拉菜单
    var hit = 10; // 显示的下拉提示列表的长度
    setTimeout(function() {
        $("#search-input").autocomplete({
            delay: 0,
            source: function(request, response) {
                var toSend = new Object();
                toSend.style = "function";
                toSend.method = "getSearchHint";
                toSend.word = $( "#search-input" ).val();
                $.ajax({
                    url: config.base_url,
                    type : "POST",
                    dataType: 'json',
                    data: $.toJSON(toSend),
                    success: function(data) {
                        if(data.status === 'OK' && data.result) {
                            if(data.result.length >= hit) {
                                response(data.result.slice(0, hit));
                            } else {
                                response(data.result);
                            }
                        } else if( data.status === 'FAIL' && data.errors ){
                            //alert(data.errors[0].message);
                        }
                    }
                });
            }
        }).bind("input.autocomplete", function () {
            $( "#search-input" ).autocomplete("search", $( "#search-input" ).val());
        }).focus();
    }, 0);
});

function logout(){
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : "{'style':'user','method':'logout','uid':'" + $.cookie('uid') + "'}",
        async : false,
        error : function(request) {
                    alert("Connection error");
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                $.cookie("uid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                $.cookie("nickName",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                $.cookie("iconUrl",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                $.cookie("tid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                self.location='home.html';
                return;
            } else {
                alert(json.msg);
            }
        }
    });
}

function getEmailCount() {
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : "{'style':'notification','method':'getNotiCount','uid':'" + $.cookie('uid') + "'}",
        async : false,
        error : function(request) {
                    alert("Connection error");
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                $("#noti_no").html(json.noti_count);
                return;
            } else {
                alert(json.msg);
            }
        }
    });
}
