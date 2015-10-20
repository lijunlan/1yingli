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

    //查看评论类型
    $("#assess-for-you").click(function(){
        $("#assess-for-you").css("border-bottom", "2px solid #34495e");
        $("#assess-for-others").css("border-bottom", "#fff");
        type = 2;
        changePage(page);
    });
    $("#assess-for-others").click(function(){
        $("#assess-for-others").css("border-bottom", "2px solid #34495e");
        $("#assess-for-you").css("border-bottom", "#fff");
        type = 1;
        changePage(page);
    });

    changePage(page);
});

var totalPage = 1;
var page = 1;
var type = 2;
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

function firstPage() {
    if(page == 1) {
        return;
    } else {
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

    //获取总数
    $("#assess-for-you").text("给您的评价("+ getTotalNumber(2) +")");
    $("#assess-for-others").text("您写的评价("+ getTotalNumber(1) +")");    

    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getCommentList";
    toSend.uid = $.cookie('uid');
    toSend.page= "" + p;
    toSend.kind = "" + type;
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
                var html = "";
                if(p == "max"){
                    page = totalPage;
                } else {
                    page = p;
                }
                $.each( data, function(index, content){
                    html = html + "<li class=\"assess-list\" id=\""+content.commentId+"\">"
                                + "<div class=\"assess-content-top\">"
                                + "<img class=\"assess-content-top-face\" src=\""+(content.url!=""?content.url:"http://image.1yingli.cn/img/img.png")+"\" />"
                                + "<p class=\"assess-content-top-name\">"+content.name+"</p>"
                                + "<div>";
                    var s = content.score;
                    for(var i = 1;i<=s;i++){
                        html = html + "<img src=\"http://image.1yingli.cn/img/yellow_star.png\" />";
                    }
                    html = html + "</div>"
                                + "    </div>"
                                + "    <div class=\"assess-content-content\">"
                                + "        <p class=\"assess-text\">"+content.content+"</p>"
                                + "    </div>"
                                + "    <p class=\"date-text\">"+content.createTime+"</p>"
                                + "</li>";
                });
                
                $("#commentList").html(html);

            } else {
                if(json.msg == "uid is not existed"){

                } else {
                    $(".mark").show();
                    $("#box").show();
                    $("#bomb").html("帐号信息已失效，请重新登录");
                    $("#connect").attr('href','login.html');
                    $.cookie("uid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("nickName",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("iconUrl",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                    $.cookie("tid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                }
            }
        }
    });

    //先删除后添加页数
    //页码设置
    totalPage = getTotalPage(type);
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
//获取总页数
function getTotalPage(type){
    var totalPage;
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getCommentCount";
    toSend.uid = $.cookie('uid');
    toSend.kind = "" + type;
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
                //alert(json.msg);
            }
        }
    });
    return totalPage;
}
//获取总数
function getTotalNumber(type){
    var totalNumber;
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getCommentCount";
    toSend.uid = $.cookie('uid');
    toSend.kind = "" + type;
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
                totalNumber = json.count;
            } else {
                //alert(json.msg);
            }
        }
    });
    return totalNumber;
} 