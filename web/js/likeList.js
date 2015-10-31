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

    changePage(page);
});

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

    //根据具体页数获取数据
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getLikeTeachers";
    toSend.uid = $.cookie('uid');
    if(p == undefined) {
        p = 1;
    }
    toSend.page= ""+p;
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
                var data = $.parseJSON(json.data);
                if(p == "max"){
                    page = totalPage;
                } else {
                    page = p;
                }
                var html = "";
                $.each( data, function(index, content){
                    html = html + "<li class=\"right-list-0\">"
                                +"    <div class=\"like-list-left\">"
                                +"        <img src=\""+(content.iconUrl!=""?content.iconUrl:img/img.png)+"\" alt=\"\"/>"
                                +"        <p>"+content.name+"</p>"
                                +"    </div>"
                                +"    <div class=\"like-list-left-02\">"
                               // +"        <h1>"+content.companyName+" "+content.position+"</h1>"
                                +"        <p>"+content.title+"</p>"
                                +"    </div>"
                                +"    <div class=\"like-list-left-03\">"
                                +"        <p>"+content.price+"</p>"
                                +"        <p>元/"+content.time+"小时</p>"
                                +"    </div>"
                                +"    <div class=\"like-list-left-04\">"
                                +"        <a href=\"personal.html?tid="+content.teacherId+"\">预约</a>"
                                +"        <a href=\"personal.html?tid="+content.teacherId+"\">查看详情</a>"
                                +"        <a href='#' onclick='deleteTeacher("+content.teacherId+");'>删除</a>"
                                +"    </div>"
                                +"</li>";
                });
                if(html == "") {
                    $("#likeList").html("<li class='right-list-0' style='font-size:14px; height: 30px;'>还没有添加喜爱的导师。</li>");
                } else {
                    $("#likeList").html(html);
                }
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
            $("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
        };
    } else {
        for (var i = 1; i <=totalPage; i++) {
            if(page == 1) {
                $("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                if(i == 5) {
                    $("#btnNext").before("...");
                    $("#btnNext").before("<a class='pager' id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                    break;
                }
            } else if(page <=mostPage) {
                $("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                if(i == afterShowPage) {
                     $("#btnNext").before("...");
                     $("#btnNext").before("<a class='pager' id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                     break;
                }
            } else {
                if(i<=leastPage) {
                    $("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                    if(i == leastPage) { $("#btnNext").before("...");}
                }
                if(beforeShowPage <= i && i <= afterShowPage){
                    $("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                }
                if(i == afterShowPage) {
                    if(afterShowPage !=totalPage ) {
                        $("#btnNext").before("...");
                        $("#btnNext").before("<a class='pager' id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
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
    toSend.style = "user";
    toSend.method = "getLikeTeacherCount";
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
        success : function(data) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                totalPage = json.page;
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
    return totalPage;
} 

//删除喜欢的老师
function deleteTeacher(tid) {
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "dislikeTeacher";
    toSend.uid = $.cookie('uid');
    toSend.teacherId = tid + "";
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
                changePage(page);
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