$(document).ready(function(){
	var u = $.cookie('uid');
    var n = $.cookie('nickName');
    var i = $.cookie('iconUrl');
    if(u==null||n==null||i==null){self.location='login.html';return;}

 	$("#nickName").html(n);
    if(i!=""){
        $("#iconUrl").attr("src",i);
    }

 	var _name = $.cookie("name");
    var _phone = $.cookie("phone");
    var _address = $.cookie("address");
    var _mail = $.cookie("mail");

    if(_name==null||_phone==null||_address==null||_mail==null){
        self.location='bct1.html';
        return;
    }

    var strWE = $.cookie('workExp');
    var strSE = $.cookie('schoolExp');

    if(strWE==null||strSE==null){
    	self.location='bct2.html';
        return;
    }

  	var title = $.cookie("t_title");
	var time = $.cookie("t_time");
	var price = $.cookie("t_price");
	var content = $.cookie("t_content");
	var reason = $.cookie("t_reason");
	var advantage = $.cookie("t_advantage");

	$("#topic-title").val(title);
	$("#topic-time").val(time);
	$("#topic-price").val(price);
	$("#content").val(content);
	$("#reason").val(reason);
	$("#advantage").val(advantage);
  
    $(".icon_img_tip").bind("mouseenter", function(event) {
        // var $dropDownMenu = $(this).children(".dropDown");
        // $dropDownMenu.css("display", "block");
        var src = $(this).children("img").attr("src");
        var length = src.length;
        // src=src.substring(0,length-4)+"_active.png";
        src = src.substring(0, length - 4) + "_light.png";
        $(this).children("img").attr("src", src);
        $(this).children("a").css("color", "#e95e59");
        event.stopPropagation();
    });
    $(".icon_img_tip").bind("mouseleave", function(event) {
        // var $dropDownMenu = $(this).children(".dropDown");
        // $dropDownMenu.css("display", "none");
        var src = $(this).children("img").attr("src");
        var length = src.length;
        src = src.substring(0, length - 10) + ".png";
        $(this).children("img").attr("src", src);
        $(this).children("a").css("color", "white");
        event.stopPropagation();
    });

    $.ajax({
        cache : true,
        type : "POST",
        url : config.base_url,
        data : "{'style':'function','method':'getTips'}",
        async : false,
        error : function(request) {
                    $(".mark").show();
                    $("#box").show();
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                var tips = $.parseJSON(json.tips);
                var html = "";
                $.each(tips,function(index,content){
                	html = html + "<li class=\"small-label-item\" info=\""+content.tid+"\">"+content.name+"</li>";
                });
                $("#tip-list").html(html);
            } else {
                
            }
        }
    });

    $(".container").css('height', '');
     //鼠标移动事件，当input有焦点进来，使其提示消失。
    $("#topic-title").mousedown(function(){
        $("#titleTab").css("display", "none");
    });
    $("#topic-time").mousedown(function(){
        $("#timeTab").css("display", "none");
    });
    $("#topic-price").mousedown(function(){
         $("#priceTab").css("display", "none");
    });
    $("#content").mousedown(function(){
        $("#contentTab").css("display", "none");
    });
    $("#reason").mousedown(function(){
        $("#reasonTab").css("display", "none");
    });
    $("#advantage").mousedown(function(){
        $("#advantageTab").css("display", "none");
    });
});

function last(){
	var title = $("#topic-title").val();
	var time = $("#topic-time").val();
	var price = $("#topic-price").val();
	var content = $("#content").val();
	var reason = $("#reason").val();
	var advantage = $("#advantage").val();
	$.cookie("t_title",title,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("t_time",time,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("t_price",price,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("t_content",content,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("t_reason",reason,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("t_advantage",advantage,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	self.location = 'bct2.html';
}

function submitInfo(){
	var title = $("#topic-title").val();
	var time = $("#topic-time").val();
	var price = $("#topic-price").val();
	var content = $("#content").val();
	var reason = $("#reason").val();
	var advantage = $("#advantage").val();

    var isTrue = true;
    if(!title){
        $("#titleTab").css("display", "inline-block");
        isTrue = false;
    }
    if(!time){
        $("#timeTab").css("display", "inline-block");
        isTrue = false;
    }
    if(!price){
        $("#priceTab").css("display", "inline-block");
        isTrue = false;
    }
    if(!content){
        $("#contentTab").css("display", "inline-block");
        isTrue = false;
    }
    if(!reason){
        $("#reasonTab").css("display", "inline-block");
        isTrue = false;
    }
    if(!advantage){
        $("#advantageTab").css("display", "inline-block");
        isTrue = false;
    }
    if(!isTrue) {
        return;
    }

	var tids = [];
	var nt = $(".changeC");
	var st = $(".styles");
	st.each(function(){
		var tid = new Object();
		tid.id = $(this).attr("info");
		tids.push(tid);
	})
	nt.each(function(){
		var tid = new Object();
		tid.id = $(this).attr("info");
		tids.push(tid);
	})
	// $.each(st,function(index,content){
	// 	var tid = new Object();
	// 	tid.id = content.attr("info");
	// 	tids.push(tid);
	// });

	// $.each(nt,function(index,content){
	// 	var tid = new Object();
	// 	tid.id = content.attr("info");
	// 	tids.push(tid);
	// });

	var obj = new Object();
	obj.title = title;
	obj.time = time;
	obj.price = price;
	obj.content = content;
	obj.reason = reason;
	obj.advantage = advantage;
	obj.tips = tids;
	
	var strWE = $.cookie('workExp');
    var strSE = $.cookie('schoolExp');

    var _name = $.cookie("name");
    var _phone = $.cookie("phone");
    var _address = $.cookie("address");
    var _mail = $.cookie("mail");

    var data = new Object();
    data.service = obj;
    data.workExperience = $.parseJSON(strWE);
    data.studyExperience = $.parseJSON(strSE);
    data.name = _name;
    data.phone = _phone;
    data.address = _address;
    data.mail = _mail;

    var toSend = new Object();

    toSend.style = "function";
    toSend.method = "createApplication";
    toSend.uid = $.cookie('uid');
    toSend.application = data;

    $.ajax({
        cache : true,
        contentType:"application/x-www-form-urlencoded; charset=uft-8",
        type : "POST",
        url : config.base_url,
        data :  $.toJSON(toSend),
        async : false,
        error : function(request) {
                    $(".mark").show();
                    $("#box").show();
                },
        success : function(data, textStatu) {
            var json = eval("(" + data + ")");
            if (json.state == "success") {
            	//清理COOKIE
				$.cookie("t_title",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("t_time",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("t_price",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("t_content",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("t_reason",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("t_advantage",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("name",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("phone",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("address",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("mail",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("workExp",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
				$.cookie("schoolExp",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                self.location='bct4.html';
                return;
            } else {
                
            }
        }
    });
}
