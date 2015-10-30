var list,i,con,orderId,cval,datatime,teacherState;
var star = 0;
var teacherState;
var uid = $.cookie('uid');
var nickName = $.cookie('nickName');
var iconUrl = $.cookie('iconUrl');
var tid = $.cookie('tid');

$(document).ready(function(){  
    if(!uid || !nickName){ self.location='login.html'; return; }
    $("#nickName").html(nickName);
    $("#bigNickName").html(nickName);
    if(iconUrl){
        $("#iconUrl").attr("src",iconUrl);
        $("#bigIcon").attr("src",iconUrl);
    }

    //判断是不是导师:0导师,1不是,2已经成为
    judge();
    if(teacherState == "0"){
    }else  if(teacherState == "2"){ 
        self.location='bct5.html';return;
    }else{
        self.location='bct0.html';return;
    }

    //导师主页
    changePage(page);

    $(".Tutor-lists").on( "click", "a.see", function(){
        $(".mark").show();
        var oid = $(this).parent().parent().parent().find("#111").text();
        detail(oid);
    })
});

//判断是不是导师
function judge(){
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getTeacherState";
    toSend.uid = uid;
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
            teacherState = json.teacherState;
        }
    });
}

//导师主页
var totalPage = 2;
var page = 1;
function firstPage() {
    if(page != 1) { changePage(1); }
}
function nextPage(){
    if(page < totalPage) {
        page ++;
        changePage(page);
    }
}
function lastPage(){
    if(page > 1) {
        page --;
        changePage(page);
    }
}
function finalPage() {
    if(page != totalPage) { changePage(totalPage); }
}
function getTotalPage() {
    var total;
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : "{'style': 'teacher', 'method': 'getOrderCount', 'uid': '"+uid+"', 'teacherId': '"+tid+"'}",
        async : false,
        error : function(request) {},
        success : function(data, textStatu) {
            var json = eval("(" + data + ")"); 
            if(json.state == 'success') {
                total = json.page;
            }
        }
    });
    return total;
}
function changePage(p){
    var toSend = new Object();
    toSend.style = "order";
    toSend.method = "getListByTeacher";
    toSend.uid = uid;
    toSend.teacherId = tid;
    toSend.page = "" + p;
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
        async : false,
        error : function(request) {
                $(".mark").show();
                $("#erro").show();
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")"); 
            list = $.parseJSON(json.data);
            var html = '';
            if(json.state =='success') {
                page = p;
                $.each(list,function(index,content){
                    var tmp=parseInt(content.createTime,10);
                    var d = new Date(tmp);
                    i = content.state.split(",")[0];
                    con = content;
                    orderId = content.orderId
                    html = html + "<li class='Tutor-list'><div class='Tutor-id'><p>编号:<p id='111' style='float:left;'>"+content.orderId+"</p><p style='font-size: initial;padding-top: 12px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'><span>时间：</span>"+d.toLocaleString()+"</p></div><div class='Tutor-01'><img src=\""+(content.iconUrl==""?"http://image.1yingli.cn/img/img.png":content.iconUrl)+"\" alt=''/><div class='name'>"+content.name+"</div></div></div><div class='Tutor-03'><p>"+content.price+"元</p><p>/"+content.time+"分钟</p></div><div class='Tutor-04'><div class='order-details' style='color:#56bbe8; cursor: pointer;'><a class='see'><p>查看详情</p></a></div></div><div class='Tutor-05'>"+Situation(i,orderId)+"</div></li>";
                });
            $(".Tutor-lists").html(html);
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

//导师栏目最右侧
function Situation(i,orderId){
    switch (i){
        case "0200":
            return "<a class='states'><p>"+"未支付"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"学员放弃支付"+"</p></a>";
            break;
        case "0300":
            return "<a class='states'><p>"+"学员已支付"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"等待导师确认"+"</p></a>";
            break;
        case "0400":
            return "<a class='states'><p>"+"接受订单"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"等待确认时间"+"</p></a>";
            break;
        case "0500":
            return "<a class='states'><p>"+"确定时间"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"等待服务"+"</p></a>";
            break;
        case "0620":
            return "<a class='states'><p>"+"服务完毕"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"用户不满"+"</p></a>";
            break;
        case "0700":
            return "<a class='states'><p>"+"订单已取消"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"等待退款"+"</p></a>";
            break;
        case "0800":
            return "<a class='states'><p>"+"学员取消咨询"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"退款成功"+"</p></a>";
            break;
        case "0900":
            return "<a class='states'><p>"+"服务完成"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"等待收款"+"</p></a>";
            break;
        case "1000":
            return "<a class='states'><p>"+"已收款"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"等待用户评价"+"</p></a>";
            break;
        case "1010":
            return "<a class='states'><p>"+"用户已评价"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"等待导师评价"+"</p></a>";
            break;
        case "1100":
            return "<a class='states'><p>"+"已完成"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"订单成功完成"+"</p></a>";
            break;
        case "1200":
            return "<a class='states'><p>"+"学员放弃支付"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"学员已经放弃申请"+"</p></a>";
            break;
        case "1300":
            return "<a class='states'><p>"+"订单已取消"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"客服介入中"+"</p></a>";
            break;
        case "1400":
            return "<a class='states'><p>"+"已支付"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"订单状态不正确"+"</p></a>";
            break;
        case "1500":
            return "<a class='states'><p>"+"订单取消"+"</p></a><a class='state' href='javascript:openshow("+i+","+orderId+");'><p>"+"学员等待退款"+"</p></a>";
            break;
    }
}

//导师支付情况弹出框
function openshow(i,orderId) {
    switch (i){
        case 128:
            $(".mark").show();
            $(".no1").slideDown().html("<p>导师已收到提醒，等待导师确认</p><p>请耐心等待，导师会在24小时内确认</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='2'>取消服务</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 192:
            $(".mark").show();
            $(".no1").slideDown().html("<p>学员已支付，等待导师确认</p><p>请您在24小时内确认</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='tsev' style='margin: 30px 100px 30px 200px;'>确认服务</button><button id='back192' style='margin:0;'>拒绝服务</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){  
                $(".no1").slideUp();
                $(".mark").hide(); 
                $("#refuse").hide();
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
                $("#refuse").hide();
            });
            $("#tsev").click(function(){
                var order = orderId.toString();
                $(".no1").hide();
                agree(order);
            });
            $("#back192").click(function(){
                $("#refuse").slideDown().html("<div><img src='http://image.1yingli.cn/images/edge.png' style='width:20px; height:20px; position:absolute; top:440px; left:55%;z-index:300;'><div style='width:150px; height:160px; background:#56bbe8; position:absolute; top:420px; left:56%; border-top-left-radius:8px;border-top-right-radius:8px;z-index:300;'><div style='font-size:16px; font-family:lantingxihei; color:#FFF; margin:10px 16px'>请选择拒绝理由</div><ul class='##'><li><div class='##' style='margin:10px'><input name='choice' type='radio'  style='width:15px; height:15px;' value='学员信息不完整'><div style='font-size:14px; color:#FFF; position:absolute; top:40px;left:20%;'>学员信息不完整</div></div></li><li><div class='##' style='margin:10px'> <input name='choice' type='radio' placeholder='' style='width:15px; height:15px;' value='临时有事'><div style='font-size:14px; color:#FFF; position:absolute; top:70px;left:20%;'>临时有事</div></div></li><li><div class='##' style='margin:10px'><input name='choice' type='radio' placeholder='' style='width:15px; height:15px;' value='其他原因'><div style='font-size:14px; color:#FFF; position:absolute; top:100px;left:20%;'>其他原因</div></div></li></ul></div><div style='width:150px; height:40px; background:#FFF; position:absolute; top:580px; left:56%;border-bottom-right-radius:8px;border-bottom-left-radius:8px; z-index:300;'><div id='Tclose' style='width:40px; height:20px; font-size:14px; color:#FFF; background:#d3d3d3; position:absolute; top:5px; left:10%; padding:2px 8px; border-radius:8px; cursor: pointer;'>取 消</div><div id='tconfirm' style='width:40px; height:20px; font-size:14px; color:#FFF; background:#56bbe8; position:absolute; top:5px; left:50%; padding:2px 8px; border-radius:8px; cursor: pointer;'>确 认</div></div></div>")
                $("#Tclose").click(function(){
                    $("#refuse").slideUp();
                });
                $("#tconfirm").click(function(){
                    $("#refuse").slideUp();
                    cval = $("input[name='choice']:checked").val();
                    var order = orderId.toString(10);
                    disagree(order,cval);
                });
            });
            break;
        case 256:
            $(".mark").show();
            $(".no1").slideDown().html("<p>请先和学员协商后尽快确认时间</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><div style='width:100%;margin-top:10px;font-size:17px;'>协商时间：<input id='cd' type='date' style='margin-right: 10px;border: 1px solid rgba(0,0,0,0.3);border-radius: 5px;'/><input id='ct' type='time' style='border: 1px solid rgba(0,0,0,0.3);border-radius: 5px;'/></div><div style='width:100%;height:30px;margin-top:15px;'><button id='ttime' style='margin:0px 20px 0 0;'>确认时间</button><button id='back256' style='margin:0;'>返回</button></div><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/><div id='alt' style='color:red; display:none;'>日期与时间必填！</div></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#ttime").click(function(){
                var data = $("#cd").val();
                var time = $("#ct").val();
                if(!data == "" && !time == ""){
                    datatime = data +"-"+ time ;
                    var order = orderId.toString(10);
                    $(".no1").hide();
                    dtime(datatime,order);
                } else {
                    $("#alt").show();
                }
            });
            $("#back256").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 320:
            $(".mark").show();
            $(".mark").show();
            $(".no1").slideDown().html("<p>学员已收到提醒</p><p>请按时提供咨询</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='ture500'>确定</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#ture500").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 400:
            $(".mark").show();
            $(".no1").slideDown().html("<p>学员表示不满</p><p>是否同意退款</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='yes400' style='margin: 30px 100px 30px 200px;'>同意</button><button id='no400' style='margin:0;'>拒绝</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#yes400").click(function(){
                var order = orderId.toString(10);
                $(".no1").hide();
                yes(order);
            });
            $("#no400").click(function(){
                var order = orderId.toString(10);
                $(".no1").hide();
                no(order);
            });
            break;
        case 448:
            $(".mark").show();
            $(".no1").slideDown().html("<p>订单已取消</p><p></p><div class='order-step'><div class='yuan'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back700'>确认</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back700").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 800:
            $(".mark").show();
            $(".no1").slideDown().html("<p>学员已经申请退款</p><p>服务结束</p><div class='order-step'><div class='yuan'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back800'>确定</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back800").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 900:
            $(".mark").show();
            $(".no1").slideDown().html("<p>请您确认收款</p><p>请耐心等待</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back900'>确认</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back900").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 1000:
            $(".mark").show();
            $(".no1").slideDown().html("<p>请确认收款</p><p>请耐心等待学员的评价</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back1000'>确认</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back1000").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 1010:
            $(".mark").show();
            $(".no1").slideDown().html("<p>学员已经评价</p><p>等待您的评价</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='uass'>立即评价</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
                $("#assess").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
                $("#assess").hide(); 
            });
            $("#uass").click(function(){
                $("#assess").fadeIn();
                $(".star").click(function(){
                    star = $(this).attr("id");
                    for(var i = 1;i<=5;i++){
                        if(i<=star){
                            $("#" +i).attr("src","http://image.1yingli.cn/img/topic_stary.png");
                        }else{
                            $("#" +i).attr("src","http://image.1yingli.cn/img/topic_star.png");
                        }
                    }
                });
            });
            $("#usend").click(function(){
                var pl = $("#uassess").val();
                if(star != 0 && pl != ""){
                    ass = $("#uassess").val();
                    var order = orderId.toString(10);
                    $(".no1").hide();
                    $("#assess").hide();
                    tsend(ass,order);
                } else if(star != 0 && pl == ""){
                    $("#miss1").show();
                } else if(star == 0 && pl != ""){
                    $("#miss2").show();
                } else if(star == 0 && pl == ""){
                    $("#miss1").show();
                    $("#miss2").show();
                }
            });
            break;
        case 1100:
            $(".mark").show();
            $(".no1").slideDown().html("<p>订单成功完成</p><p>感谢您的支持</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back'>返回</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            })
            break;
        case 1200:
            $(".mark").show();
            $(".no1").slideDown().html("<p>学员已经放弃申请</p><p>感谢您的支持</p><div class='order-step'><div class='yuan'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back1200'>返回</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back1200").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 1300:
            $(".mark").show();
            $(".no1").slideDown().html("<p>客服介入中</p><p>请耐心等待，客服会尽快处理</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back1300'>确认</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back1300").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            break;
        case 1400:
            $(".mark").show();
            $(".no1").slideDown().html("<p>订单出现异常</p><p>请耐心等待，客服会在24小时内修正</p><div class='order-step'><div class='yuan'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back1400'>确定</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back1400").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 1500:
            $(".mark").show();
            $(".no1").slideDown().html("<p>学员取消咨询</p><p></p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back1500'>确认</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back1500").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
    }
}

function disagree(order,cval){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "refuseOrder";
        toSend.uid = uid;
        toSend.teacherId = tid;
        toSend.orderId = order;
        toSend.refuseReason = cval;
        $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
            async : false,
            error : function(request) {
                        $(".mark").show();
                        $("#erro").show();
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

function agree(order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "acceptOrder";
        toSend.uid = uid;
        toSend.teacherId = tid;
        toSend.orderId = order;
        $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
            async : false,
            error : function(request) {
                        $(".mark").show();
                        $("#erro").show();
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

function dtime(datatime,order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "ensureTime";
        toSend.uid = uid;
        toSend.teacherId = tid;
        toSend.orderId = order;
        toSend.okTime = datatime;
        $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
            async : false,
            error : function(request) {
                        $(".mark").show();
                        $("#erro").show();
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


function tsend(ass,order){
        var toSend = new Object();
        toSend.style= "teacher";
        toSend.method = "commentUser";
        toSend.orderId = order;
        toSend.teacherId = tid;
        toSend.score = star;
        toSend.content = ass;
        toSend.uid = uid;
        $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
            async : false,
            error : function(request) {
                        $(".mark").show();
                        $("#erro").show();
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

function no(order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "disagreeOrder";
        toSend.uid = uid;
        toSend.teacherId = tid;
        toSend.orderId = order;
        $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
            async : false,
            error : function(request) {
                        $(".mark").show();
                        $("#erro").show();
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

function yes(order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "agreeOrder";
        toSend.uid = uid;
        toSend.teacherId = tid;
        toSend.orderId = order;
        $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : $.toJSON(toSend),
            async : false,
            error : function(request) {
                        $(".mark").show();
                        $("#erro").show();
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

function detail(oid){
        var toSend = new Object();
        toSend.style = "order";
        toSend.method = "getByTeacher";
        toSend.teacherId = tid;
        toSend.uid = uid;
        toSend.orderId = oid;
        $.ajax({
            cache : true,
            type : "POST",
            url : config.base_url,
            data : $.toJSON(toSend),
                async : false,
                error : function(request) {
                        $(".mark").show();
                        $("#erro").show();
                        },
                success : function(data, textStatu) {
                    var json = eval("(" + data + ")"); 
                    $(".details").fadeIn().html("<img class='Tutor_icon' src='http://image.1yingli.cn/img/order_close_icon.png' alt=''/><div><div>"+json.title+"</div> </div><div><div><img src=\""+(json.iconUrl==""?"http://image.1yingli.cn/img/img.png":json.iconUrl)+"\"/></div><div>"+json.name+"</div><div>"+json.price+"元</div></div><div><p>学员提的问题:</p><p>"+json.question+"</p></div><div><p>学员目前状况:</p><p>"+json.userIntroduce+"</p></div><div><p>预约时间:</p><p>"+json.selectTimes+"</p></div><div><p>联系方式:</p><p>手机号码："+json.phone+"</p><p>微信："+json.contact+"</p><p>邮箱："+json.email+"</p></div><div><div>订单号："+json.orderId+"</div> </div>");
                    $(".Tutor_icon").click(function(){
                            $(".mark").hide();
                            $(".details").fadeOut();
                        });
                    $(".mark").click(function(){
                        $(".details").fadeOut();
                        $(".mark").hide(); 
                    });
                }
        });
}

