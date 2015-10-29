var list,i,con,orderId,cval,ppay,ptime,star,ass;
var uid = $.cookie('uid');
$(document).ready(function(){    
    var u = $.cookie('uid');
    var n = $.cookie('nickName');
    var i = $.cookie('iconUrl');
    if(u==null||n==null||i==null){
        self.location='login.html';
        return;
    }
    $("#nickName").html(n);
    $("#bigNickName").html(n);
    if(i!=""){
        $("#iconUrl").attr("src",i);
        $("#bigIcon").attr("src",i);
    }
    changePage(page);

    $(".yourTutor-lists").on( "click", "a.see", function(){
        $(".mark").show();
        var oid = $(this).parent().parent().parent().find("#111").text();
        detail(oid);
    });
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
    if(!uid){ return; }
    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : "{'style': 'user', 'method': 'getOrderCount', 'uid': '" + uid + "'}",
        async : false,
        error : function(request) {},
        success : function(data, textStatu) {
            var json = eval("(" + data + ")"); 
            if(json.state == 'success') {
                total = json.page;
            }
        }
    });
    return 6;
}

//导师主页
function changePage(p){
    if(!uid){ return; }
    var toSend = new Object();
    toSend.style = "order";
    toSend.method = "getListByUser";
    toSend.uid = uid;
    toSend.page = "" + p;
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
                if(json.state == 'success') {
                    list = $.parseJSON(json.data);
                    page = p;
                    var html="";
                    $.each(list,function(index,content){
                        var tmp=parseInt(content.createTime,10);
                        var d = new Date(tmp);
                        ppay = content.price;
                        ptime = content.time;
                        i = content.state.split(",")[0];
                        var tid = content.teacherId;
                        con = content;
                        orderId = content.orderId
                        html = html + "<li class='yourTutor-list'><div class='yourTutor-id'><p>编号:</p><p id='111' style='float:left;'>"+content.orderId+"</p><p style='font-size: initial;padding-top: 12px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'><span>时间：</span>"+d.toLocaleString()+"</p></div><div class='yourTutor-01'><img src=\""+(content.teacherUrl==""?"http://image.1yingli.cn/img/img.png":content.teacherUrl)+"\" alt=''/></div><div class='yourTutor-02'><p>咨询话题</p><h1 style='overflow:hidden; white-space: nowrap; text-overflow:ellipsis;'>"+content.title+"</h1><a style='color: #56bbe8;text-align:center;display: block;' href='personal.html?tid="+tid+"'>"+content.teacherName+"</a></div><div class='yourTutor-03'><p>"+content.price+"元"+"</p><p>/"+content.time+"小时"+"</p></div><div class='yourTutor-04'><div class='order-details' style='color:#56bbe8; cursor: pointer;'><a class='see'><p>查看详情</p></a></div></div><div class='yourTutor-05'>"+Situation(tid,i,orderId)+"</div></li>";
                        Situation(tid,i,orderId);
                    });
                    if(!html){
                        html="<li style='background:#FFF;line-height:40px;text-indent:10px;'>您还没有预约导师。</li>";
                    }
                    $(".yourTutor-lists").html(html);
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
    var html1="";
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
function Situation(tid,i,orderId){
    switch (i){
        case "0100":
            return "<a class='states'><p>"+"未支付"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"等待学员付款"+"</p></a>";
            break;
        case "0200":
            return "<a class='states'><p>"+"未支付"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"已放弃支付"+"</p></a>";
            break;
        case "0300":
            return "<a class='states'><p>"+"学员已支付"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"等待导师确认"+"</p></a>";
            break;
        case "0400":
            return "<a class='states'><p>"+"接受订单"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"等待确认时间"+"</p></a>";
            break;
        case "0500":
            return "<a class='states'><p>"+"确定时间"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"等待导师服务"+"</p></a>";
            break;
        case "0620":
            return "<a class='states'><p>"+"服务完毕"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"反馈提交"+"</p></a>";
            break;
        case "0700":
            return "<a class='states'><p>"+"订单已经取消"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"等待退款"+"</p></a>";
            break;
        case "0800":
            return "<a class='states'><p>"+"订单已经取消"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"退款成功"+"</p></a>";
            break;
        case "0900":
            return "<a class='states'><p>"+"服务完成"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"等待导师确认"+"</p></a>";
            break;
        case "1000":
            return "<a class='states'><p>"+"服务完成"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"评价导师"+"</p></a>";
            break;
        case "1010":
            return "<a class='states'><p>"+"用户已评价"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"等待导师评价"+"</p></a>";
            break;
        case "1100":
            return "<a class='states'><p>"+"已完成"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"订单成功完成"+"</p></a>";
            break;
        case "1200":
            return "<a class='states'><p>"+"未支付"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"订单已经取消"+"</p></a>";
            break;
        case "1300":
            return "<a class='states'><p>"+"订单已取消"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"客服介入中"+"</p></a>";
            
            break;
        case "1400":
            return "<a class='states'><p>"+"已支付"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"订单状态不正确"+"</p></a>";
            break;
        case "1500":
            return "<a class='states'><p>"+"订单取消"+"</p></a><a class='state' href='javascript:openshow("+tid+","+i+","+orderId+");'><p>"+"等待退款"+"</p></a>";
            break;
    }
}

//导师支付情况弹出框
function openshow(tid,i,orderId){
    switch (i){
        case 64:
            $(".mark").show();
            $(".no1").slideDown().html("<p>订单已生成，请确认付款</p><p>请耐心等待，付款后导师会在24小时内确认</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='fpay' style='float:left; margin:30px 0 0 210px'>确认付款</button><button id='fnopay' style='float:left; margin:30px 0 0 50px;'>放弃支付</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
                $("#pay").slideUp(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
                $("#pay").slideUp();
            });
            $("#fpay").click(function(){
                var tuid = $.cookie('uid');
                $(".no1").fadeOut();
                $("#pay").fadeIn().html("<img class='Tutor_icon1' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/><div style='width: 100%;height: 40px;font-size: 20px;text-align: center;margin-top: 40px;'>您确认要付款么</div><input size='30' name='oid' value="+orderId+" style='display:none' /><input size='30' name='uid' value="+tuid+" style='display:none' /><button type='submit' style='width:80px;height:20px;font-size:16px;color:#FFF;background:#56bbe8;border-radius:8px;text-align:center;float:right;margin-right: 10%;border:0px;margin-top: 30px;cursor: pointer;' id='fukuan'>确认支付")
                $(".Tutor_icon1").click(function(){
                    $(".no1").fadeOut();
                    $(".mark").hide();
                    $("#pay").fadeOut(); 
                });
                $("#fukuan").click(function(){
                    $(".no1").hide();
                    $(".mark").hide();
                    $("#pay").hide();
                });
            }) ;
            $("#fnopay").click(function(){
                var order = orderId.toString(10);
                $(".no1").hide();
                $("#pay").hide();
                nopay(order);
            });
            
            break;
        case 128:
            $(".mark").show();
            $(".no1").slideDown().html("<p>取消订单成功</p><p></p><div class='order-step'><div class='yuan'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back128'>确定</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            $("#back128").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 192:
            $(".mark").show();
            $(".no1").slideDown().html("<p>导师已收到提醒，等待导师确认</p><p>请耐心等待，导师会在24小时内确认</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='spay' style='float:left; margin:30px 0 0 210px'>确认</button><button id='nopayafter' style='float:left; margin:30px 0 0 50px;'>申请退款</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            $("#spay").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#nopayafter").click(function(){
                var order = orderId.toString(10);
                $(".no1").hide();
                nopayafter(order);
            });
            break;
        case 256:
            $(".mark").show();
            $(".no1").slideDown().html("<p>导师已收到提醒，等待导师与您协商时间</p><p>请耐心等待，导师会在24小时内确认</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='noserve'>取消咨询</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            $("#noserve").click(function(){
                var order = orderId.toString(10);
                $(".no1").hide();
                noserve(order);
            })
            break;
        case 320:
            $(".mark").show();
            $(".mark").show();
            $(".no1").slideDown().html("<p>导师已确认时间，等待导师服务</p><p></p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='tgd' style='margin:20px 70px 0px 200px;'>服务满意</button><button id='nogd' style='margin:0;'>服务不满意</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            $("#tgd").click(function(){
                var order = orderId.toString(10);
                $(".no1").hide();
                good(order);
            });
            $("#nogd").click(function(){
                var order = orderId.toString(10);
                $(".no1").hide();
                nogood(order);
            });
            break;
        case 400:
            $(".mark").show();
            $(".no1").slideDown().html("<p>反馈信息已经收到</p><p>请耐心等待</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back400'>确定</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back400").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            break;
        case 448:
            $(".mark").show();
            $(".no1").slideDown().html("<p>订单已取消</p><p></p><div class='order-step'><div class='yuan'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back448'>确定</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $("#back448").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            break;
        case 800:
            $(".mark").show();
            $(".no1").slideDown().html("<p>订单已经取消</p><p>请耐心等待退款到账</p><div class='order-step'><div class='yuan'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back800'>确定</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
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
            $(".no1").slideDown().html("<p>服务已经完成</p><p>请等待导师确认信息</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian xian-01'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='uass'>确认</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
                $("#assess").slideUp(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            $("#uass").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            break;
        case 1000:
            $(".mark").show();
            $(".no1").slideDown().html("<p>导师已确认收款</p><p>请评价导师</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='uass'>立即评价</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
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
                if(star != null && pl != ""){
                    ass = $("#uassess").val();
                    var order = orderId.toString(10);
                    var stid = tid.toString(10);
                    $(".no1").hide();
                    $("#assess").hide();
                    usend(stid,ass,order);
                } else if(star != null && pl == ""){
                    $("#miss1").show();
                } else if(star == null && pl != ""){
                    $("#miss2").show();
                } else if(star == null && pl == ""){
                    $("#miss1").show();
                    $("#miss2").show();
                }
            });
            break;
        case 1010:
            $(".mark").show();
            $(".no1").slideDown().html("<p>导师已收到提醒，等待导师评价</p><p>请耐心等待，导师会在24小时内评价</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>4</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back1010'>确认</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
            $(".Tutor_icon").click(function(){
                $(".no1").slideUp();
                $(".mark").hide(); 
            });
            $(".mark").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
            });
            $("#back1010").click(function(){
                $(".no1").slideUp();
                $(".mark").hide();
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
            $(".no1").slideDown().html("<p>服务已经取消</p><p>感谢您的支持</p><div class='order-step'><div class='yuan'><span>1</span></div><div class='xian'></div><div class='yuan'><span>2</span></div><div class='xian'></div><div class='yuan'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back1200'>返回</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
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
            }) ;
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
            $(".no1").slideDown().html("<p>退款请求已经提交</p><p>请耐心等待，退款会在24小时内达到</p><div class='order-step'><div class='yuan yuan-01'><span>1</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>2</span></div><div class='xian xian-01'></div><div class='yuan yuan-01'><span>3</span></div><div class='xian'></div><div class='yuan'><span>4</span></div><div class='xian'></div><div class='yuan'><span>5</span></div></div><ul class='order-step-text'><li>学员申请</li><li>导师确认</li><li>协商时间</li><li>服务进行</li><li>双方评价</li></ul><button id='back1500'>确认</button><img class='Tutor_icon' src='http://image.1yingli.cn/img/schedule_close.png' alt=''/></div>");
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

function good(order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "satisfyOrder";
        toSend.uid = $.cookie('uid');
        toSend.orderId = order;
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

function usend(stid,ass,order){
        var toSend = new Object();
        toSend.style= "user";
        toSend.method = "commentTeacher";
        toSend.orderId = order;
        toSend.teacherId = stid;
        toSend.score = star;
        toSend.content = ass;
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
                    $(".mark").show();
                    $("#succ").show();
                } else {
                    $(".mark").show();
                    $("#erro").show();
                }
            }
    });
}

function nopay(order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "cancelOrder";
        toSend.uid = $.cookie('uid');        
        toSend.orderId = order;
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

function nopayafter(order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "cancelOrderAfterPay";
        toSend.uid = $.cookie('uid');        
        toSend.orderId = order;
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

function noserve(order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "cancelOrderAfterAccept";
        toSend.uid = $.cookie('uid');        
        toSend.orderId = order;
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

function nogood(order){
        var toSend = new Object();
        toSend.style= "order";
        toSend.method = "dissatisfyOrder";
        toSend.uid = $.cookie('uid');        
        toSend.orderId = order;
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

function detail(oid){
    var toSend = new Object();
    toSend.style = "order";
    toSend.method = "getByUser";
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
                $("#box").show();
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")"); 
            $(".details").fadeIn().html("<img class='Tutor_icon' src='http://image.1yingli.cn/img/order_close_icon.png' alt=''/><div class='details_top'><div class='details_top_content'>"+json.title+"</div> </div><div class='details_content'><div class='details_person_img'><img src=\""+(json.teacherUrl==""?"http://image.1yingli.cn/img/img.png":json.teacherUrl)+"\" style='width:173px; height:173px;' alt=''/></div><div class='details_person_name'>"+json.teacherName+"</div><div class='details_person_price'>"+json.price+"元/1小时</div><div class='details_person_topic'> 话题 ："+json.title+"</div></div><div class='details_person_problem'><p class='quest1'>学员提的问题:</p><p class='text1'>"+json.question+"</p></div><div class='details_person_status'><p class='quest2'>学员目前状况:</p><p class='text2'>"+json.userIntroduce+"</p></div><div class='details_person_time'><p class='quest3'>预约时间:</p><p class='text3'>"+json.selectTimes+"</p></div><div class='details_person_contract'><p style='font-size:16px;font-weight: bolder;'>联系方式:</p><p style='width:150px;height:15px;font-size:13px;margin:5px 20px 0 0; float:left;'>手机号码："+json.phone+"</p><p style='width:150px;height:15px;font-size:13px;margin:5px 20px 0 0; float:left;'>微信："+json.contact+"</p><p style='width:180px;height:15px;font-size:13px;margin:5px 0 0 0; float:left;'>邮箱："+json.email+"</p></div><div class='details_bottom'><div style='height:20px;font-size: 14px; color: #000; padding-top: 8px;'>订单号："+json.orderId+"</div> </div>");
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