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
//获取总页数
function getTotalPage(){
    var total;
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : "{'style': 'teacher', 'method': 'getCommentCount', 'teacherId': '"+$.cookie('tid')+"'}",
        async : false,
        error : function(request) {},
        success : function(data, textStatu) {
            var json = eval("(" + data + ")"); 
            if(json.state == 'success') {
                total = json.page;
            }else{
                total = 1;
            }
        }
    });
    return total;
}
function changePage(p) {
    var toSend = new Object();
    toSend.style = "teacher";
    toSend.method = "getCommentList";
    toSend.teacherId = $.cookie('tid');
    toSend.page= p + "";
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
                page = p;
                var data = $.parseJSON(json.data);
                var html = "";
                $.each( data, function(index, content){
                    html = html + "<li class=\"assess-list\" id=\""+content.commentId+"\">"
                                + "<div class=\"assess-content-top\">"
                                + "<img class=\"assess-content-top-face\" src=\""+(content.iconUrl!=""?content.iconUrl:"http://image.1yingli.cn/img/img.png")+"\" />"
                                + "<p class=\"assess-content-top-name\">"+content.nickName+"</p>"
                                + "<div>";
                    var s = content.score;
                    for(var i = 1;i<=s;i++){
                        html = html + "<img src='http://image.1yingli.cn/img/yellow_star.png'/>";
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
                //alert(json.msg);
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


   