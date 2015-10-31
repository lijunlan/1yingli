<!--for Baidu-->
var _hmt = _hmt || [];
(function () {
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?013a77379489bebfff6ac135ecb08395";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();
var left = 0;
var right = 0;
$(document).ready(function () {
    if($.cookie('tid') == null) {
        $('.i-am-a-teacher').css('display','none');
    }
    $("label.left").click(function () {
        if (left == 1) {
            console.log('have');
            $("html,body").removeClass("XY");
            $('.bg').removeClass('bg-show').off('click');
            left = 0;
        } else {
            console.log('else');
            $("html,body").addClass("XY");
            $('.bg').addClass('bg-show').click(function () {
                $(".left").click();
            });
            left = 1;
        }
    });
    $("label.right").click(function () {
        console.log("test");
        var u = $.cookie('uid');
        var n = $.cookie('nickName');
        var i = $.cookie('iconUrl');
        if (u == null || n == null || i == null) {
            $(".sidenav1").addClass("loginBox");
            $(".subject").addClass("loginY");
            $('#ri').click();
            self.location = 'login.html';
            return;
        } else {
            if ($("html,body").hasClass("XY")) {
                $("html,body").removeClass("XY");
                $('.bg').removeClass('bg-show').off('click');
            } else {
                $("html,body").addClass("XY");
                $('.bg').addClass('bg-show').click(function () {
                    $(".right").click();
                })
            }
        }
    });

    $('.sidenav a').click(function () {
        $(".left").click();
    });


    $('.sidenav1 a').click(function () {
        $(".right").click();
    });
    //$(".header").css("margin","10px");
    //1...10分别代表不同的id请求参数。
    //$(".contact").html("<div class='contact-content'><div class='contact-left'> <ul class='contact-left-top'><li><a href='joinus.html?id=1'>关于我们</a></li><li><span>/</span></li> <li><a href='joinus.html?id=1'>加入我们</a></li><li><span>/</span></li><li><a href='joinus.html?id=1'>联系我们</a></li></ul><ul class='contact-left-left'><li><a href='joinus.html?id=4'>团队故事</a></li><li><a href='joinus.html?id=5'>团队成员</a></li><li><a href='joinus.html?id=1'>媒体报道</a></li></ul><ul class='contact-left-center'><li><a href='joinus.html?id=1'>招聘岗位</a></li><li><a href='joinus.html?id=1'>公司福利</a></li></ul><ul class='contact-left-right'><li><a href='joinus.html?id=1'>网站反馈</a></li><li><a href='joinus.html?id=1'>商务合作</a></li></ul></div><div class='contact-right'><p class='contact-right-1'>客服电话：</p><p class='contact-right-2'>0571-86415101</p><p class='contact-right-3'>工作日：9:00-17:00</p><p class='contact-right-4'>客服邮箱：contact@1yingli.cn</p></div></div>");
    //$(".footer").html("<ul class='footer-share'> <li><a href='home.html'><img src='http://image.1yingli.cn/img/footer_icon.jpg' class='footer-icon'/></a></li><li><img src='http://image.1yingli.cn/img/footer_1.jpg'/></li><li><a class='icon_img_tip'  href=''><img id='footer-wechat' src='http://image.1yingli.cn/img/footer_wechat.png'/></a><!--<div class='icon_tip' id='wechat-tip'></div>--></li> <li><a class='icon_img_tip'  href=''><img id='footer-in' src='http://image.1yingli.cn/img/footer_in.png'/></a><!--<div class='icon_tip' id='in-tip' ></div>--></li><li><a class='icon_img_tip' href=''><img id='ooter-ren' src='http://image.1yingli.cn/img/footer_ren.png'/></a><!--<div class='icon_tip' id='ren-tip' ></div>--></li><li><a class='icon_img_tip'  href=''><img id='footer-weibo' src='http://image.1yingli.cn/img/footer_weibo.png'/></a><!--<div class='icon_tip' id='weibo-tip' ></div>--></li></ul> <p class='copyright'>Copyright@2014-2015 杭州千询科技有限公司 版权所有<a href='http://www.miitbeian.gov.cn/'  target='_Blank'>浙ICP备15023254号</a></p>");
    $(".footer").html("<div id='ustel'><div class='footerc' style='width:1024px;margin:0 auto; height:100%'><div id='wximg' style='width:200px;float:left;margin-top:50px;margin-left:100px'><img src='http://image.1yingli.cn/img/WeChat_1440487276.jpeg' width=150px; height=150px style='border-radius: 8px;'></img><p style='width:150px;color:#fff;font-size:16px; text-align:center;line-height:50px;'>微信公众账号</p></div><div id='company' style='width:350px;;float:left;margin-top:40px;margin-left:300px;font-size:18px;color:#fff;align:right;text-align: right;line-height: 40px;'> <p id='aboutUs'><a href='joinus.html?id=1' style='color:#fff;text-decoration:none;'>关于我们</a> / <a href='joinus.html?id=2' style='color:#fff;text-decoration:none;'>加入我们</a> / <a href='joinus.html?id=3' style='color:#fff;text-decoration:none;'>联系我们</a></p><p class='phonenum'>0571-86415101</p><p id='workday1'>工作日：9:30-17:30 (GMT+8)</p><p id='workday2'>工作日：9:30-17:30</p><p class='emailbt'>客服邮箱：contact@1yingli.cn</p><p id='companynu1' style='width:100%;height:60px;float:left;display:none;'><a style='color:#FFF;' href='http://www.miitbeian.gov.cn/'  target='_Blank'>©2015 杭州千询科技 浙ICP备15023254号</a></p></div></div></div>"
        + "<div id='botx' style='width:100%;height:3px;float:left;opacity: 0.3;border-top: 1px solid #ccc;'></div>"
        + "<div id='companynu2' style='width:100%;height:60px;float:left'><div class='number' style='width:1024px;margin:0 auto;'><p style='height:60px;line-height:60px;color:white;text-align: center;' ><a style='color:#FFF;' href='http://www.miitbeian.gov.cn/'  target='_Blank'>©2015 杭州千询科技 浙ICP备15023254号</a></p></div></div>");
    $(".footer").css("height", "333px");
    $(".footer").append("<div id='box' style='width: 400px;height: 200px;margin: auto;position: fixed;top: 50%;left: 50%;margin-top:-100px;margin-left:-200px;border-radius: 10px;z-index: 101;background: #fff; display:none;'><div style='width: 400px;height: 35px;background-color: #d2d2d2;border-top-left-radius: 10px;border-top-right-radius: 10px;text-align: center;'><div style='font-size: 16px;padding-top: 6px;font-weight: bold;color:#FFF;cursor: default;'>来自一英里的信息</div></div><div id='bomb' style='text-align: center;margin-top:45px;font-size: 20px;color: #b6b6b6;cursor: default;'>系统正在维护中，请稍后</div><a id='connect' href='home.html' style='text-decoration: none;width: 128px;position: absolute;top: 70%;left: 35%;font-size: 20px;color: #FFF;background-color: #56bbe8;text-align: center;border-radius: 14px;'>确定</a><a href='home.html' id='cancel' style='text-decoration: none;width: 128px;position: absolute;top: 70%;left: 55%;font-size: 20px;color: #fff;background-color: #ccc;text-align: center;border-radius: 14px;display:none;'>取消</a></div>");
    $(".footer").append("<div class='mark' style='display:none;'></div>");
    //分页页码图片转换
    $("#btnStart img").hover(function () {
        $("#btnStart img").attr("src", "http://image.1yingli.cn/img/firstPageBlue.png");
    });
    $("#btnStart img").mouseleave(function () {
        $("#btnStart img").attr("src", "http://image.1yingli.cn/img/firstPage.png");
    });
    $("#btnLast img").hover(function () {
        $("#btnLast img").attr("src", "http://image.1yingli.cn/img/lastPageBlue.png");
    });
    $("#btnLast img").mouseleave(function () {
        $("#btnLast img").attr("src", "http://image.1yingli.cn/img/lastPage.png");
    });
    $("#btnNext img").hover(function () {
        $("#btnNext img").attr("src", "http://image.1yingli.cn/img/nextPageBlue.png");
    });
    $("#btnNext img").mouseleave(function () {
        $("#btnNext img").attr("src", "http://image.1yingli.cn/img/nextPage.png");
    });
    $("#btnEnd img").hover(function () {
        $("#btnEnd img").attr("src", "http://image.1yingli.cn/img/finalPageBlue.png");
    });
    $("#btnEnd img").mouseleave(function () {
        $("#btnEnd img").attr("src", "http://image.1yingli.cn/img/finalPage.png");
    });

});

var hit = 10; // 显示的下拉提示列表的长度
setTimeout(function () {
    $("#mobile_search").autocomplete({
        delay: 0,
        source: function (request, response) {
            var toSend = new Object();
            toSend.style = "function";
            toSend.method = "getSearchHint";
            toSend.word = $("#mobile_search").val();
            $.ajax({
                url: config.base_url,
                type: "POST",
                dataType: 'json',
                data: $.toJSON(toSend),
                success: function (data) {
                    if (data.status === 'OK' && data.result) {
                        if (data.result.length >= hit) {
                            response(data.result.slice(0, hit));
                        } else {
                            response(data.result);
                        }
                    } else if (data.status === 'FAIL' && data.errors) {
                        //alert(data.errors[0].message);
                    }
                }
            });
        }
    }).bind("input.autocomplete", function () {
        $("#mobile_search").autocomplete("search", $("#mobile_search").val());
    }).bind('autocompleteselect', function (e, ui) {
        self.location = 'search.html?findName=' + encodeURIComponent(encodeURIComponent(ui.item.value));
    }).focus();
});

$('#mobile_search_button').click(function(){
    self.location = 'search.html?findName=' + encodeURIComponent(encodeURIComponent($("#mobile_search").val()));
});


//首页主题搜索
function search() {
    var findName = encodeURIComponent(encodeURIComponent($("#searchBox").val()));
    self.location = 'search.html?findName=' + findName;
}

//头部搜索
function searchByInput() {
    var findName = encodeURIComponent(encodeURIComponent($("#search-input").val()));
    self.location = 'search.html?findName=' + findName;
}

//头部搜索
function searchByInput1() {
    var findName1 = encodeURIComponent(encodeURIComponent($("#search-input1").val()));
    self.location = 'search.html?findName1=' + findName1;
}

function KeyDown() {
    if (event.keyCode == 13) {
        event.returnValue = false;
        event.cancel = true;
        searchByInput();
    }
}

function KeyDown1() {
    if (event.keyCode == 13) {
        event.returnValue = false;
        event.cancel = true;
        searchByInput1();
    }
}

//主题搜索
function findByTheme(themeName, id) {
    var w = encodeURIComponent(encodeURIComponent(themeName));
    self.location = 'search.html?word=' + w + '&id=' + id;
}

var config = {
    base_url: "http://test.1yingli.cn/yiyingliService/manage"
};

$('.subject').append(
    '<div id="bg" class="bg"/>'
);

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

