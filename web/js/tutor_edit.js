var schoolExp;
var workExp;

$(document).ready(function(){
    var price,a=11,b=22,c=33;
    var uid = $.cookie('uid');
    var nickName = $.cookie('nickName');
    var iconUrl = $.cookie('iconUrl');
    if(uid==null||nickName==null||iconUrl==null){
        self.location='login.html';
        return;
    }

    $("#revise").click(function(){
        $(".mark").show();
        $("#frame").show();
    });

    $("#close").click(function(){
        $(".mark").hide();
        $("#frame").hide();
    });

    $("#li1").click(function(){
        $("#li1>img").toggle();
        if($("#tel1").is(":hidden")){
            a = 11;     
        }else{
            a = 10;
        }
    });

    $("#li2").click(function(){
        $("#li2>img").toggle();
        if($("#tel2").is(":hidden")){
            b = 22;     
        }else{
            b = 20;
        }
    });

    $("#li3").click(function(){
        $("#li3>img").toggle();
        if($("#tel3").is(":hidden")){
            c = 33;     
        }else{
            c = 30;
        }
    });

    $("#confirm").click(function(){
        if($("#caddress").val() == "" && $("#ctime").val() == ""){
    		$("#must1").show();
    		$("#must2").show();
    	} else if($("#ctime").val() == "" && $("#caddress").val() != ""){
    		$("#must2").show();
    		$("#must1").hide();
    	} else if($("#caddress").val() == "" && $("#ctime").val() != ""){
    		$("#must1").show();
    		$("#must2").hide();
    	} else{
        	$(".mark").hide();
        	$("#frame").hide();
        	changemassage(a,b,c);
        }
    });

    //页面的头部
    $("#nav-right").html("<li class=\"nav-right\" id=\"nav-right-theme\"><a href=\"\">主题分类</a><ul><li><a href=\"theme.html\" target=\"_self\">留学领航</a></li><li><a href=\"theme_01.html\" target=\"_self\">求职就业</a></li><li><a href=\"theme_03.html\" target=\"_self\">创业助力</a></li><li><a href=\"theme_04.html\" target=\"_self\">校园生活</a></li>"
+"<li><a href=\"theme_05.html\" target=\"_self\">猎奇分享</a></li></ul></li><li class=\"nav-right\"><span >|</span></li><li class=\"nav-right\" style='overflow: hidden;max-width: 100px;'><a href =\"controlPanel.html\">"+nickName+"</a></li><li class=\"nav-right\"><span>|</span></li><li class=\"nav-right\" id=\"nav-right-beTutor\"><a href=\"bct1.html\">成为导师</a></li>"    );           
    $("#nav-right-theme").hover(function(){
        $("#nav-right-theme ul").css("display","block")
    });
    $("#nav-right-theme").mouseleave(function(){
        $("#nav-right-theme ul").css("display","none")
    });

    //获取个人信息
    refreshTeacherInfo();
    $("#contolShow").show();
    //获取评价
    changePage(page);

	//数据验证取消提示
	$("#title").mousedown(function(){
		$("#title").next().hide();					   
	});
	$("#price").mousedown(function(){
		$("#price").next().next().next().next().hide();					   
	});
	$("#simpleinfo").mousedown(function(){
		var p_message = $("#simpleinfo").next().next().next();
		p_message.html("(最多输入16个字)");
		p_message.css("color", "#34495e");					   
	});

    //修改目前显示职位或者学校。
    $("#edit_position").click(function(){
        $("#edit_position_block").show();
        $("#simpleinfo").val($("#tposition").html());
    });
    $("#cancel_simpleinfo").click(function(){
        $("#edit_position_block").hide();
    });
    $("#sure_simpleinfo").click(function(){
        var simpleinfo = $("#simpleinfo").val();
		if(!simpleinfo) {
			var p_message = $("#simpleinfo").next().next().next();
			p_message.html("此字段不能为空！");
			p_message.css("color", "#FF0000");
			return;
		}
		var tid = $.cookie('tid');
		var uid = $.cookie('uid');
		if(!tid||!uid){
			return;
		}
		
        var toSend = new Object();
        toSend.style = "teacher";
        toSend.method = "changeSimpleInfo";
        toSend.teacherId = tid;
        toSend.simpleinfo = simpleinfo;
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
                        if (json.state == "success") {
                            $("#tposition").html(simpleinfo);
                            $("#edit_position_block").hide();
                        }else {
							if (json.msg == "uid is not existed") {
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
    });

    //编辑话题和价格
    $("#edit_titlePrice").click(function(){
        $("#edit_titlePrice_block").show();
        $("#title").val($("#stitle").html());
        $("#price").val($("#oldPrice").html());
        $("#time").val($("#oldTime").html());
    });
    $("#cancel_titlePrice").click(function(){
        $("#edit_titlePrice_block").hide();
    });
    $("#sure_titlePrice").click(function(){
        var title = $("#title").val();
        var price = $("#price").val();
        var time = $("#time").val();
		var isTrue = true;
		if(!title) {
			isTrue = false;
			$("#title").next().show();
		}
		if(!price || price == 0 || isNaN(price)) {
			isTrue = false;
			$("#price").next().next().next().next().show();
		}
		if(!time || time == 0 || isNaN(time)) {
			isTrue = false;
			$("#price").next().next().next().next().show();
		}
		if(!isTrue) {
			return;
		}
		
		var tid = $.cookie('tid');
		var uid = $.cookie('uid');
		if(!tid||!uid){
			return;
		}

        var toSend = new Object();
        toSend.style = "teacher";
        toSend.method = "changeTitlePrice";
        toSend.teacherId = tid;
        toSend.title = title;
        toSend.price = price;
        toSend.time = time;
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
                        if (json.state == "success") {
                            $("#edit_titlePrice_block").hide();
                            $("#stitle").html(title);
                            $("#oldPrice").html(price);
                            $("#oldTime").html(time);
                        }
                    }
        });
    });
    //编辑关于他
    $("#edit_aboutTa").click(function(){
        $("#tintroduce").hide();
        $("#area_tintroduce").show();
        $("#sure_aboutTa").show();
        $("#cancel_aboutTa").show();
        $("#area_tintroduce").val($("#tintroduce").html());
    });
    $("#cancel_aboutTa").click(function(){
        $("#tintroduce").show();
        $("#area_tintroduce").hide();
        $("#sure_aboutTa").hide();
        $("#cancel_aboutTa").hide();
    });
    $("#sure_aboutTa").click(function(){
        var introduce  = $("#area_tintroduce").val();
		var tid = $.cookie('tid');
		var uid = $.cookie('uid');
		if(!tid||!uid){
			return;
		}

        var toSend = new Object();
        toSend.style = "teacher";
        toSend.method = "changeIntroduce";
        toSend.teacherId = tid;
        toSend.introduce = introduce;
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
                        if (json.state == "success") {
                            $("#tintroduce").show();
                            $("#area_tintroduce").hide();
                            $("#sure_aboutTa").hide();
                            $("#cancel_aboutTa").hide();
                            $("#tintroduce").html(introduce);
                        }
                    }
        });
    });
	
	//编辑个人履历
	$("#edit_img_resume").click(function(){
		if($("#edit_img_resume").html() == "编辑") {
			$("#edit_img_resume").html("保存");
			$("#edit_img_resume").css("background", "#56BBE8");
			$("#edit_img_resume").css("color", "#FFF");
			$("#explist").hide();						
			$(".edit_explist").show();
			$("#cancel_img_resume").show();
			refreshResume();
		} else {
			var tid = $.cookie('tid');
			var uid = $.cookie('uid');
			if(!tid||!uid){
				return;
			}

			var toSend = new Object();
			toSend.style = "teacher";
			toSend.method = "changeStudyExp";
			toSend.teacherId = tid;
			toSend.uid = uid;
			toSend.studyExperience = schoolExp;
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
							}
						}
			});
			
			var toSend1 = new Object();
			toSend1.style = "teacher";
			toSend1.method = "changeWorkExp";
			toSend1.teacherId = tid;
			toSend1.uid = uid;
			toSend1.workExperience = workExp;
			toSend1.desprition = '';
			$.ajax({
				cache : true,
				type : "POST",
				url : config.base_url,
				data : $.toJSON(toSend1),
				async : false,
				error : function(request) {
							$(".mark").show();
                        	$("#box").show();
						},
				success : function(data, textStatu) {
							var json = eval("(" + data + ")");
							if (json.state == "success") {
							}
						}
			});
			
			
			$("#explist").show();	
			$("#edit_img_resume").html("编辑");
			$("#edit_img_resume").css("background", "#E5E5E5");
			$("#edit_img_resume").css("color", "#34495e");
			$(".edit_explist").hide();
			$("#cancel_img_resume").hide();
			refreshTeacherInfo();
		}
	});
	//取消编辑个人履历
	$("#cancel_img_resume").click(function(){
		$("#edit_img_resume").html("编辑");
		$("#edit_img_resume").css("background", "#E5E5E5");
		$("#edit_img_resume").css("color", "#34495e");
		$("#cancel_img_resume").hide();
		$("#explist").show();
		$(".edit_explist").hide();
		refreshTeacherInfo();
	});

	//时间控件
	//$("#resume_starttime").calendar();
	setupSelect();
	$("#sc_ed_ye_time").change(function(){
		if($(this).val() == "至今"){
			$("#sc_ed_mo_time").val("");
			$("#sc_ed_mo_time").attr("disabled", "true");
		}else {
			$("#sc_ed_mo_time").removeAttr("disabled");
		}
		
	});
	$("#co_ed_ye_time").change(function(){
		if($(this).val() == "至今"){
			$("#co_ed_mo_time").val("");
			$("#co_ed_mo_time").attr("disabled", "true");
		}else {
			$("#co_ed_mo_time").removeAttr("disabled");
		}
	});
});

//年份
function setupSelect(){
    //var html="<option value=\"至今\">至今</option>";
	var html="";
    for(i=0;i<=75;i++){
        html=html+"<option value=\""+(2015-i)+"\">"+(2015-i)+"</option>";
    }
    $(".mid").append(html);
}

//确定添加信息或修改信息
function sure_resume(resume){
	if(resume == "school") {
		var schoolName = $("#text_shcool").val();
		var degree = $("#text_degree").val();
		var major = $("#text_major").val();
		var endYearTime = $("#sc_ed_ye_time").val();
		var startYearTime = $("#sc_st_ye_time").val();
		var startMonthTime = $("#sc_st_mo_time").val();
		//数据验证
		if(!schoolName||!degree||!major||!endYearTime||!startYearTime||!startMonthTime){
			$(".mark").show();
            $("#box").show();
            $("#bomb").html("所有字段必须填写");
            $("#connect").attr('href','##');
            $("#connect").click(function(){
            	$(".mark").hide();
            	$("#box").hide();
            });
			return;
		}
		
		//数据流的添加和修改
		if($("#save_edit_school").attr("value") == "确定" ){
			var obj = new Object();
			obj.schoolName = schoolName;
			obj.degree = degree;
			obj.major = major;
			obj.description = '';
			if(endYearTime == "至今"){
				obj.endTime = endYearTime;
			} else {
				obj.endTime = endYearTime +","+ $("#sc_ed_mo_time").val();	
			}
			obj.startTime = startYearTime +","+ startMonthTime;
			schoolExp.push(obj);
		}else {
			var indexId = $("#index_schid").val();
			$.each(schoolExp,function(index,content){
				if(indexId == index){
					content.schoolName = schoolName;
					content.degree = degree;
					content.major = major;
					if($("#sc_ed_ye_time").val() == "至今"){
						content.endTime = $("#sc_ed_ye_time").val();
					} else {
						content.endTime = $("#sc_ed_ye_time").val()+","+ $("#sc_ed_mo_time").val();	
					}
					content.startTime = $("#sc_st_ye_time").val() +","+ $("#sc_st_mo_time").val();
				}
			});
		}
		cancel_edit("edit_Schblock");
	} else {
		var companyName = $("#text_company").val();
		var position = $("#text_position").val();
		var endYearTime = $("#sc_ed_ye_time").val();
		var startYearTime = $("#sc_st_ye_time").val();
		var startMonthTime = $("#co_st_mo_time").val();

		//数据验证
		if(!companyName||!position||!endYearTime||!startYearTime||!startMonthTime){
			$(".mark").show();
            $("#box").show();
            $("#bomb").html("所有字段必须填写");
            $("#connect").attr('href','##');
            $("#connect").click(function(){
            	$(".mark").hide();
            	$("#box").hide();
            });
			return;	
		}
		
		if($("#save_edit_company").attr("value") == "确定"){
			var obj = new Object();
			obj.companyName = companyName;
			obj.position = position;
			obj.description = '';
			if($("#co_ed_ye_time").val() == "至今") {
				obj.endTime = $("#co_ed_ye_time").val();	
			} else {
				obj.endTime = $("#co_ed_ye_time").val()+","+$("#co_ed_mo_time").val();
			}
			obj.startTime = $("#co_st_ye_time").val()+","+$("#co_st_mo_time").val();
			
			workExp.push(obj);
		} else {
			var indexId = $("#index_conid").val();
			$.each(workExp,function(index,content){
				if(indexId == index){
					content.companyName = companyName;
					content.position = position;
					if($("#co_ed_ye_time").val() == "至今") {
						content.endTime = $("#co_ed_ye_time").val();	
					} else {
						content.endTime = $("#co_ed_ye_time").val()+","+$("#co_ed_mo_time").val();	
					}
					content.startTime = $("#co_st_ye_time").val()+","+$("#co_st_mo_time").val();
				}
			});
		}
		cancel_edit("edit_Conpblock");
	}
}


//添加单个履历信息，弹出编辑窗体
function add_resume(id){
	$(id).show();
	if(id == "#edit_Schblock"){
		$(".school_input").val("");
		$("#edit_Schblock span .timebox select").val("");
		$("#save_edit_school").attr("value", "确定");
		refreshSchoolResume();
	}else {
		$(".company_input").val("");
		$("#edit_Conpblock span .timebox select").val("");
		$("#save_edit_company").attr("value", "确定");
		refreshCompanyResume();
	}
}

//删除单个履历信息
function delete_resume(resumeName){
	$.each(schoolExp,function(index,content){
		if(content.schoolName == resumeName){
			schoolExp.splice(index,1);
			refreshSchoolResume();
			$(".schoolInfo .edit_block").hide();
		}
	});
	$.each(workExp,function(index,content){
		if(content.companyName == resumeName){
			workExp.splice(index,1);
			refreshCompanyResume();
			$(".conpanyInfo .edit_block").hide();
		}
	});
};

//编辑单个履历信息,弹出信息框
function edit_resume(id, resumeName){
	$.each(schoolExp,function(index,content){
		if(content.schoolName == resumeName){
			$(".schoolInfo .edit_block").show();
			$("#text_shcool").val(content.schoolName);
			$("#text_degree").val(content.degree);
			$("#text_major").val(content.major);
			
			$("#sc_st_mo_time").val(content.startTime.split(",")[1]);
			$("#sc_st_ye_time").val(content.startTime.split(",")[0]);
			$("#sc_ed_mo_time").val(content.endTime.split(",")[1]);
			$("#sc_ed_ye_time").val(content.endTime.split(",")[0]);
			
			$("#save_edit_school").attr("value", "修改");
			$("#index_schid").val(index);
			refreshSchoolResume();
		}
	});
	$.each(workExp,function(index,content){
		if(content.companyName == resumeName){
			$(".conpanyInfo .edit_block").show();
			$("#text_company").val(content.companyName);
			$("#text_position").val(content.position);
			
			$("#co_st_mo_time").val(content.startTime.split(",")[1]);
			$("#co_st_ye_time").val(content.startTime.split(",")[0]);
			$("#co_ed_mo_time").val(content.endTime.split(",")[1]);
			$("#co_ed_ye_time").val(content.endTime.split(",")[0]);
			
			$("#save_edit_company").attr("value", "修改");
			$("#index_conid").val(index);
			refreshCompanyResume();
		}
	});
	$("#"+id).css("background-color","#E5E5E5");
}

//取消编辑
function cancel_edit(id){
	$("#"+id).hide();
	if(id=="edit_Schblock") {
		refreshSchoolResume();
	}else {
		refreshCompanyResume();
	}
	
}

//刷新全部个人履历
function refreshResume() {
	var html1= "";
	$.each(schoolExp,function(index,content){
		var st = content.startTime;
		var et = content.endTime;
		html1 = html1 + "<p id='school_"+index+"'><span id='con_schoolName'>"+content.schoolName+"</span>&nbsp;&nbsp;<span id='con_major'>"+content.major+"</span>&nbsp;&nbsp;<span id='con_degree'>"+content.degree + "</span><img src='http://image.1yingli.cn/img/delete_p.png' onclick=\"delete_resume('"+content.schoolName+"');\"><img src='http://image.1yingli.cn/img/edit_p.png' onclick=\"edit_resume('school_"+index+"','"+content.schoolName+"')\"><span id='con_time' class='con_time'>" + st+"-"+et+"</span></p>"; 
	});
	$(".schoolInfo .show_explist").html(html1);
	
	var html2 = "";
	$.each(workExp,function(index,content){
		var st = content.startTime;
		var et = content.endTime;
		html2 = html2 + "<p id='company_"+index+"'><span id='con_companyName'>"+content.companyName+"</span>&nbsp;&nbsp;<span id='con_position'>"+content.position + "</span><img src='http://image.1yingli.cn/img/delete_p.png' onclick=\"delete_resume('"+content.companyName+"');\"><img src='http://image.1yingli.cn/img/edit_p.png' onclick=\"edit_resume('company_"+index+"','"+content.companyName+"')\"><span id='con_time' class='con_time'>" + st+"-"+ et+"</span></p>"; 
	});
	$(".conpanyInfo .show_explist").html(html2);
}
//刷新学校个人履历
function refreshSchoolResume() {
	var html1= "";
	$.each(schoolExp,function(index,content){
		var st = content.startTime;
		var et = content.endTime;
		html1 = html1 + "<p id='school_"+index+"'><span id='con_schoolName'>"+content.schoolName+"</span>&nbsp;&nbsp;<span id='con_major'>"+content.major+"</span>&nbsp;&nbsp;<span id='con_degree'>"+content.degree + "</span><img src='http://image.1yingli.cn/img/delete_p.png' onclick=\"delete_resume('"+content.schoolName+"');\"><img src='http://image.1yingli.cn/img/edit_p.png' onclick=\"edit_resume('school_"+index+"','"+content.schoolName+"')\"><span id='con_time' class='con_time'>" + st+"-"+et+"</span></p>"; 
	});
	$(".schoolInfo .show_explist").html(html1);
}
//刷新公司个人履历
function refreshCompanyResume() {	
	var html2 = "";
	$.each(workExp,function(index,content){
		var st = content.startTime;
		var et = content.endTime;
		html2 = html2 + "<p id='company_"+index+"'><span id='con_companyName'>"+content.companyName+"</span>&nbsp;&nbsp;<span id='con_position'>"+content.position + "</span><img src='http://image.1yingli.cn/img/delete_p.png' onclick=\"delete_resume('"+content.companyName+"');\"><img src='http://image.1yingli.cn/img/edit_p.png' onclick=\"edit_resume('company_"+index+"','"+content.companyName+"')\"><span id='con_time' class='con_time'>" + st+"-"+ et+"</span></p>"; 
	});
	$(".conpanyInfo .show_explist").html(html2);
}

//获取个人信息
function refreshTeacherInfo(){
	var tid = $.cookie('tid');
	if(!tid){
		return;
	}
    var toSend = new Object();
    toSend.style = "teacher";
    toSend.method = "getAllInfo";
    toSend.teacherId = tid;
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
                        var name = json.name;
                        var iconUrl = json.iconUrl;
                        var likeNo = json.likeNo;
                        if(json.bgUrl){
                        	$("#personal-header").find(".personal-bg").attr("src", json.bgUrl);	
                        }
                        $("#tname").html(name);
                        $("#tposition").html(json.simpleinfo);
                        $("#tlikeNo").html(likeNo+"人想见");
                        $("#ticonUrl").attr("src",iconUrl==""?"http://image.1yingli.cn/img/img.png":iconUrl);
                        $("#ticonUrl1").attr("src",iconUrl==""?"http://image.1yingli.cn/img/img.png":iconUrl);
                        $("#name").html(name);
                        var tips = json.tips;
                        var td = $.parseJSON(tips);
                        $.each(td,function(index,content){
                            var tmp = $("#tip"+content.id);
                            if(tmp!=null){
                                tmp.css('background','#56bbe8');
                            }
                        });
                        
                        var address = json.address;
                        var timeperweek = json.timeperweek;
                        var introduce = json.introduce;

                        $("#tintroduce").html(introduce);
                        $("#ttimeperweek").html(timeperweek+"次");
                        $("#taddress").html(address);
                        $("#caddress").val(address);
                        $("#ctime").val(timeperweek);

                        var commentNo = json.commentNo;
                        $("#commentNo").html(commentNo+"条评价");
                        totalPage = Math.ceil(commentNo/12);

                        var freeTime = json.freeTime;
                        //TODO

                        var teacherId = json.teacherId;
                        $("#order-him").attr('href','order('+teacherId+')');
                        $("#rent").attr('href','order('+teacherId+')');

                             price = json.price;
                        var serviceTime = json.serviceTime;
                        var serviceTitle = json.serviceTitle;
                        var serviceContent = json.serviceContent;
                        $("#scontent").html(serviceContent);
                        $("#stitle").html(serviceTitle);
                        $("#sprice").html("￥<span id='oldPrice'>"+price+"</span>元/<span id='oldTime'>"+serviceTime+"</span>小时");
                        $("#sprice1").html("￥"+price+"元/"+serviceTime+"小时");

                        var checkEmail = json.checkEmail;
                        var checkPhone = json.checkPhone;
                        var checkDegree = json.checkDegree;
                        var checkIDCard = json.checkIDCard;
                        var checkWork = json.checkWork;
                        if(checkIDCard=="true"){
                            $("#checkID").html("实名认证");
                        }else{
                            $("#checkID").html("未实名认证");
                        }
                        if(checkDegree=="true"){
                            $("#checkDegree").html("学位认证");
                        }else{
                            $("#checkDegree").html("未学位认证");
                        }
                        if(checkWork=="true"){
                            $("#checkWork").html("工作认证");
                        }else{
                            $("#checkWork").html("未工作认证");
                        }
                        if(checkPhone=="true"){
                            $("#checkPhone").html("手机认证");
                        }else{
                            $("#checkPhone").html("未手机认证");
                        }
                        if(checkEmail=="true"){
                            $("#checkMail").html("邮箱认证");
                        }else{
                            $("#checkMail").html("未邮箱认证");
                        }

                        schoolExp = $.parseJSON(json.studyExperience);
                        workExp = $.parseJSON(json.workExperience);
                        html = "";
                        $.each(schoolExp,function(index,content){
                            var st = content.startTime.split(",");
                            var et = content.endTime.split(",");
                            var showTime = "";
                            if(et[0]=="至今") {
                            	showTime = st[0]+"年"+st[1]+"月-"+et[0];
                            }else {
                            	showTime = st[0]+"年"+st[1]+"月-"+et[0]+"年"+et[1]+"月";
                            }

                            html = html + "<p>"+content.schoolName +" "+content.major+" "+content.degree + "<lable style='float:right;'>"+showTime+"</lable></p>"; 
                        });

                        $.each(workExp,function(index,content){
                            var st = content.startTime.split(",");
                            var et = content.endTime.split(",");
                            var showTime = "";
                            if(et[0]=="至今") {
                            	showTime = st[0]+"年"+st[1]+"月-"+et[0];
                            }else {
                            	showTime = st[0]+"年"+st[1]+"月-"+et[0]+"年"+et[1]+"月";
                            }
                            html = html + "<p>"+content.companyName+" "+content.position+" " + "<lable style='float:right;'>"+showTime+"</lable></p>"; 
                        });
                        
                        $("#explist").html(html);

                        var talkWay = json.talkWay;
                        var li = $.parseJSON(talkWay);
                        $.each(li,function(index,content){
                            switch(content){
                                case 10:
                                $("#li11").html("<img src='http://image.1yingli.cn/images/tel01.png'>");
                                break;  
                                case 11:
                                $("#li11").html("<img src='http://image.1yingli.cn/images/tel11.png'>");
                                break;
                                case 20:
                                $("#li22").html("<img src='http://image.1yingli.cn/images/tel02.png'>");
                                break;
                                case 22:
                                $("#li22").html("<img src='http://image.1yingli.cn/images/tel22.png'>");
                                break;
                                case 30:
                                $("#li33").html("<img src='http://image.1yingli.cn/images/tel03.png'>");
                                break;
                                case 33:
                                $("#li33").html("<img src='http://image.1yingli.cn/images/tel33.png'>");
                                break;
                            }
                        });
                    } else {
						if (json.msg == "uid is not existed") {
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

}

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
	var tid = $.cookie("tid");
	if(!tid){
		return;
	}
    var toSend = new Object();
    toSend.style = "teacher";
    toSend.method = "getCommentList";
    toSend.teacherId = tid;
    toSend.page = p + "";
    
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
                page = p;
                $.each( data, function(index, content){
                    html = html + "<li><div class=\"assess-content\"><div class=\"assess-content-top\"><div class=\"assess-face\"><img src=\""+(content.iconUrl!=""?content.iconUrl:"http://image.1yingli.cn/img/img.png")+"\" style=\"border-radius:50%\" alt=\"\"/></div><h3>"+content.nickName+"</h3><p>&nbsp;&nbsp;&nbsp;"+content.createTime.replace(new RegExp("/","gm"), "-")+"</p><ul class=\"assess-star\">";
                    for(var i = 1;i<=content.score;i++){
                        html = html + "<li><img src=\"http://image.1yingli.cn/img/light_star_for_tutor_assess.png\" /></li>";
                    }                    
                    html = html +"</ul></div><div class=\"assess-content-center\"><p>"+content.content+"</p><p>参与话题“"+content.serviceTitle+"”</p></div></div></li>";
                });
                $("#commentList").html(html);
            } else {
                
            }
        }
    });
    
    //先删除后添加页数
    //页码设置
    var basePage = 10;
    var leastPage = 2
    var showPage = 4;
    var mostPage = parseInt(leastPage) + parseInt(showPage);
    var afterShowPage = parseInt(page) + showPage;
    var beforeShowPage = parseInt(page) - showPage;
    $(".page").html("");
    var html1 = "";
    if(totalPage !=0 ) {
         html1 = "<a id='btnStart' href='javascript:firstPage()'><img src='http://image.1yingli.cn/img/firstPage.png'></a><a id='btnLast' href='javascript:lastPage();' style='padding: 0px;'><img src='http://image.1yingli.cn/img/lastPage.png'></a><a id='btnNext' href='javascript:nextPage();' style='padding: 0px;'><img src='http://image.1yingli.cn/img/nextPage.png'></a><a id='btnEnd' href='javascript:finalPage();'><img src='http://image.1yingli.cn/img/finalPage.png'></a>";
    }
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
                    $("#btnNext").before("<span id='diandian'>...</span>");
                    $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                    break;
                }
            } else if(page <=mostPage) {
                $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                if(i == afterShowPage) {
                     $("#btnNext").before("<span id='diandian'>...</span>");
                     $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                     break;
                }
            } else {
                if(i<=leastPage) {
                    $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                    if(i == leastPage) { $("#btnNext").before("<span id='diandian'>...</span>");}
                }
                if(beforeShowPage <= i && i <= afterShowPage){
                    $("#btnNext").before("<a id='btn" + i + "' href='javascript:changePage(" + i + ")' >" + i + "</a>");
                }
                if(i == afterShowPage) {
                    if(afterShowPage !=totalPage ) {
                        $("#btnNext").before("<span id='diandian'>...</span>"); 
                        $("#btnNext").before("<a id='btn" + totalPage + "' href='javascript:changePage(" + totalPage + ")' >" + totalPage + "</a>");
                        break;
                    }
                }
            }
        };
    }
    $("#btn" + page).attr("class", "active");
}

function changemassage(a,b,c){	
    var talk = new Array();
    talk[0] = a;
    talk[1] = b;
    talk[2] = c;
    var toSend = new Object();
    toSend.style = "teacher";
    toSend.method = "editTService";
    toSend.teacherId = $.cookie('tid');
    toSend.timeperweek = $("#ctime").val(); 
    toSend.freetime = "1";
    toSend.talkWay = $.toJSON(talk);
    toSend.price = price;
    toSend.time = "1";
    toSend.uid = $.cookie('uid');
    toSend.address = $("#caddress").val();
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
                    $("#box").show();
                    $("#bomb").html("修改成功");
                    $("#connect").attr('href','tutor_edit.html');
                } else {
					if (json.msg == "uid is not existed") {
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
}

var changePiatureUrl = "";
//获取图片
function editBackGround() {
	$("#picturePanel").show();
	 
	var toSend = new Object();
	toSend.style = "teacher";
	toSend.method = "getBackgrounds";
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
				if(json.urls){
					var url = json.urls.replace("[","").replace("]","").split(",");
					var html = "";
					$.each( url, function(index, content){
					 	html = html + "<div class=\"pictureBlock\"><img src="+content+"><div class=\"changeBack\"></div><div class=\"pictureTrue\"><div></div></div></div>";
					});
					$("#pictureLists").html(html);
				}
			}
		}
	});
	$("#picturePanel").fadeIn(3000);
	//选择图片
	$(".pictureBlock").click(function(){
		changePiatureUrl = $(this).find("img").attr("src");
		$(".pictureTrue").hide();
		$(this).find(".pictureTrue").show();
		$(this).find(".changeBack").hide();
	});
	
	//鼠标移动事件
	$(".pictureBlock").mouseenter(function(){
		if($(this).find(".pictureTrue").css("display")=="none") {
			$(this).find(".changeBack").show();
		}
	});
	$(".pictureBlock").mouseleave(function(){
		$(this).find(".changeBack").hide();
	});
 }
 
 //关闭窗体
 function closeBackGround() {
 	$("#picturePanel").hide();
 }
 
 //修改图片数据提交
 function submitChangePicture() {
 	var uid = $.cookie("uid");
	var teacherId = $.cookie("tid");
	var bgurl = changePiatureUrl;
 	if(!uid || !teacherId || !bgurl) {
		return;
	}
	
 	var toSend = new Object();
	toSend.style = "teacher";
	toSend.method = "changeBG";
	toSend.uid = uid;
	toSend.teacherId = teacherId;
	toSend.bgurl = bgurl.replace("@!bg","");
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
				$("#picturePanel").hide();
				refreshTeacherInfo();
			}else {
				//alert(1);
			}
		}
	});
 }
 
 //图片事件的添加
 $(function() {
	 //关闭窗口
	 $(document).mousedown(function(e){  
		var x=e.clientX;  
		var y=e.clientY;
		var div = document.getElementById("picturePanel");
		var divx1 = div.offsetLeft;  
		var divy1 = div.offsetTop;  
		var divx2 = div.offsetLeft + div.offsetWidth;  
		var divy2 = div.offsetTop + div.offsetHeight;
		if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
				$("#picturePanel").hide();
		}
	});
 });
