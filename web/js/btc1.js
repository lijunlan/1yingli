$(document).ready(function(){
    var u = $.cookie('uid');
    var n = $.cookie('nickName');
    var i = $.cookie('iconUrl');

    if(u==null||n==null||i==null){
        self.location='login.html';
        return;
    }
    $("#nickName").html(n);
    if(i!=""){
        $("#iconUrl").attr("src",i);
    }

    var tid = $.cookie('tid');
    if(tid!=null){
        $(".mark").show();
        $("#confirm").show();
    }

    var _name = $.cookie('name');
    var _phone = $.cookie('phone');
    var _address = $.cookie('address');
    var _mail = $.cookie('mail');
    if(_name!=null){
    	$("#name").val(_name);
    }
    if(_phone!=null){
    	$("#phone").val(_phone);
    }
    if(_address!=null){
    	$("#address").val(_address);
    }
    if(_mail!=null){
    	$("#mail").val(_mail);
    }
   
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

    //鼠标移动事件，当input有焦点进来，使其提示消失。
    $("#name").mousedown(function(){
  		$("#nameTab").css("display", "none");
	});
	$("#phone").mousedown(function(){
  		$("#phoneTab").css("display", "none");
	});
	$("#address").mousedown(function(){
  		 $("#addressTab").css("display", "none");
	});
	$("#mail").mousedown(function(){
  		$("#mailTab").css("display", "none");
	});
});

function next(){
	var name = $("#name").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	var mail = $("#mail").val();

    var isTrue = true;
	if(!name){
		$("#nameTab").css("display", "block");
        $("#nameTab").text("姓名不能为空！");
		isTrue = false;
    }
    if(!phone){
        $("#phoneTab").css("display", "block");
        $("#phoneTab").text("手机号码不能为空！");
		isTrue = false;
    }
    if(!isTel(phone)){
        $("#phoneTab").css("display", "block");
        $("#phoneTab").text("手机号码格式不对！");
		isTrue = false;
	}
	if(!address){
        $("#addressTab").css("display", "block");
        $("#addressTab").text("常驻地址不能为空！");
		isTrue = false;
	}
    if(!mail){
        $("#mailTab").css("display", "block");
        $("#mailTab").text("邮箱不能为空！");
		isTrue = false;
	}
	if(!isEmail(mail)){
        $("#mailTab").css("display", "block");
        $("#mailTab").text("邮箱格式不对！");
		isTrue = false    ;
	}
    if(!isTrue) {
        return;
    }
    
	$.cookie("name",name,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("phone",phone,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("address",address,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("mail",mail,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	self.location='bct2.html';
}