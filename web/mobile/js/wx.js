function _getRandomString(len) {
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

//微信菜单
$.ajax({
    cache: true,
    type: "GET",
    url: "http://service.1yingli.cn/GetACCESSTOKEN/getAT",
    data: "",
    async: false,
    error: function (request) {
        //alert("Connection error");
    },
    success: function (data, textStatu) {
        var json = eval("(" + data + ")");
        // var json = $.parseJSON(data);
        var ticket1 = json.ticket;
        var timestamp1 = parseInt((new Date()).valueOf() / 1000);
        var noncestr1 = _getRandomString(16);

        var str = "jsapi_ticket=" + ticket1 + "&noncestr=" + noncestr1 + "&timestamp=" + timestamp1 + "&url=" + location.href.split('#')[0];
        var signature1 = $.sha1(str);
        wx.config({
            debug: true,
            appId: 'wxd042cdef58e2e669',
            timestamp: timestamp1,
            nonceStr: noncestr1,
            signature: signature1,
            jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']
        });
    }
});


var onReady;
wx.ready(function () {
    //微信分享设置

    onReady = function (teacher) {
        wx.onMenuShareTimeline({
            title: "【一英里导师】" + teacher.name + "(" + teacher.simpleinfo + ")", // 分享标题
            link: window.location.href, // 分享链接
            imgUrl: teacher.iconUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                //alert("success1");
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                //alert("cancel1");
            }
        });

        wx.onMenuShareAppMessage({
            title: "【一英里导师】" + teacher.name + "(" + teacher.simpleinfo + ")", // 分享标题
            desc: teacher.introduce.substr(0, 50), // 分享描述  //TODO
            link: window.location.href, // 分享链接
            imgUrl: teacher.iconUrl, // 分享图标
            type: 'link', // 分享类型,music、video或link，不填默认为link
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
                //alert("success2");
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                //alert("cancel2");
            }
        });
    }
});