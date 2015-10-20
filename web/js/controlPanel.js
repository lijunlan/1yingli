$(document).ready(function(){

    var u = $.cookie('uid');
    var n = $.cookie('nickName');
    var i = $.cookie('iconUrl');
    if(u==null && n==null && i==null){
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
    $("#nickName").html(n);
    $("#bigNickName").html(n);
    if(i!=""){
        $("#iconUrl").attr("src",i);
        $("#bigIcon").attr("src",i);
    }
    
    changePage(page);
    
});

var totalPage = 1;
var page = 1;
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
function finalPage(){
    if(page != totalPage) {
        changePage(totalPage);
    }
}
function firstPage(){
    if(page != 1) {
        changePage(1);
    }
}
function changePage(p){
    $.ajax({
        cache : true,
        type : "POST",
        url : "http://service.1yingli.cn/yiyingliService/manage",
        data : "{'style':'notification','method':'getNoti','uid':'" + $.cookie('uid') + "','page':'"+p+"'}",
        async : false,
        error : function(request) {
                    $(".mark").show();
                    $("#box").show();
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                var data = $.parseJSON(json.data);
                page = p;
                var html = "";
                $.each( data, function(index, content){
                    var tmp=parseInt(content.time,10);
                    var d = new Date(tmp);
                    html = html + "<li class=\"right-content-item\" style='display:block;min-height:90px;border-bottom: 1px solid #CCC;overflow: hidden;'><div style='margin-top:20px;margin-left: 8px; margin-bottom:10px; float:left;'>"+d.toLocaleString()+"</div><img class='show' style='cursor: pointer;width: 30px;height: 25px;float: right;margin-top: 15px;margin-right:15px;' src='http://image.1yingli.cn/img/show.png' alt=''/><img class='hide' style='width: 30px;height: 25px;float: right;margin-top: 10px;margin-right:15px;cursor: pointer;display:none;' src='http://image.1yingli.cn/img/hide.png' alt=''/><p class=\"right-content-item-p\" style='margin-bottom:10px;float:left;'>"+content.title+"</p></li>";
                });
                if(!html){
                    html="<li style='display:block;height:30px;line-height:30px;'>暂无通知。</li>";
                }
                $("#noti_list").html(html);
                $(".show").click(function(){
                    $(this).parent().find(".right-content-item-p").css('white-space','normal');
                    $(this).parent().find(".show").hide();
                    $(this).parent().find(".hide").show();
                })
                $(".hide").click(function(){
                    $(this).parent().find(".right-content-item-p").css('white-space','nowrap');
                    $(this).parent().find(".show").show();
                    $(this).parent().find(".hide").hide();
                })
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

    //先删除后添加页数
    //页码设置
    totalPage = getTotalPage();
    var basePage = 10;
    var leastPage = 2
    var showPage = 4;
    var mostPage = parseInt(leastPage) + parseInt(showPage);
    var afterShowPage = parseInt(page) + showPage;
    var beforeShowPage = parseInt(page) - showPage;
    $(".page").html("");
    html1 = "<a id='btnStart' href='javascript:firstPage()'><img src='http://image.1yingli.cn/img/firstPage.png'></a><a id='btnLast' href='javascript:lastPage();' style='padding: 0px;'><img src='http://image.1yingli.cn/img/lastPage.png'></a><a id='btnNext' href='javascript:nextPage();' style='padding: 0px;'><img src='http://image.1yingli.cn/img/nextPage.png'></a><a id='btnEnd' href='javascript:finalPage();'><img src='http://image.1yingli.cn/img/finalPage.png'></a>";
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
                    $("#btnNext").before("<span id='diandian'>...</span>");
                    $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                    break;
                }
            } else if(page <=mostPage) {
                $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                if(i == afterShowPage) {
                     $("#btnNext").before("<span id='diandian'>...</span>");
                     $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                     break;
                }
            } else {
                if(i<=leastPage) {
                    $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                    if(i == leastPage) { $("#btnNext").before("<span id='diandian'>...</span>");}
                }
                if(beforeShowPage <= i && i <= afterShowPage){
                    $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                }
                if(i == afterShowPage) {
                    if(afterShowPage !=totalPage ) {
                        $("#btnNext").before("<span id='diandian'>...</span>"); 
                        $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                        break;
                    }
                }
            }
        };
    }
    $("#btn" + page).attr("class", "active");
}

//获取总页数
function getTotalPage(){
    var totalPage;
    var toSend = new Object();
    toSend.style = "notification";
    toSend.method = "getNotiCount";
    toSend.uid = $.cookie('uid');
    $.ajax({
        cache : true,
        type : "POST",
        url : "http://service.1yingli.cn/yiyingliService/manage",
        data : $.toJSON(toSend),
        async : false,
        error : function(request) {
                    $(".mark").show();
                    $("#box").show();
                },
        success : function(data) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                totalPage = json.page;
            } else {
                totalPage = 0;
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
    return totalPage;
}