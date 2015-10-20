var teacherState;
$(document).ready(function(){  
    judge();
    if(teacherState == "0"){
    }else if(teacherState == "2"){
        self.location='bct5.html';
    }else{
        self.location='bct0.html';
    }
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
});
function judge(){
    var toSend = new Object();
    toSend.style = "user";
    toSend.method = "getTeacherState";
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
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            teacherState = json.teacherState;
        }
    });
}