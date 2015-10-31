$(document).ready(function(){
	refresh(page);

	<!--人物模块动态效果-->
	$(".one_person").hover(function(){
        $(this).css("-moz-box-shadow","0px 0px 10px #D9D9D9");
        $(this).css("box-shadow","0px 0px 10px #D9D9D9");
    });
    $(".one_person").mouseleave(function(){
        $(this).css("-moz-box-shadow","0px 0px 0px #D9D9D9");
        $(this).css("box-shadow","0px 0px 0px #D9D9D9");
    });

    <!--排序颜色和事件-->
    $("#result-content-header-list-01").click(function(){
			findBySort("");
    });
    $("#result-content-header-list-02").click(function(){
		if($(this).find('span').text() == 1) {
			findBySort("likeno+");
		} else {
			findBySort("likeno-");
		}
    });
    $("#result-content-header-list-03").click(function(){
		if($(this).find('span').text() == 1) {
			findBySort("price+");
		} else {
			findBySort("price-");
		}
    });

	<!--闭当前标签-->
	$("#selected-condition-close-01").click(function(){
		var tips1 = encodeURIComponent($_GET('tip1'));
		var tips2 = encodeURIComponent($_GET('tip2'));
		var tips3 = encodeURIComponent($_GET('tip3'));
		var tips4 = encodeURIComponent($_GET('tip4'));
		var tips5 = encodeURIComponent($_GET('tip5'));
		self.location = 'search.html?tip1=&tip2='+tips2+'&tip3='+tips3+'&tip4='+tips4+'&tip5='+tips5;
    });
    $("#selected-condition-close-02").click(function(){
		var tips1 = encodeURIComponent($_GET('tip1'));
		var tips2 = encodeURIComponent($_GET('tip2'));
		var tips3 = encodeURIComponent($_GET('tip3'));
		var tips4 = encodeURIComponent($_GET('tip4'));
		var tips5 = encodeURIComponent($_GET('tip5'));
		self.location = 'search.html?tip1='+tips1+'&tip2=&tip3='+tips3+'&tip4='+tips4+'&tip5='+tips5;
    });
    $("#selected-condition-close-03").click(function(){
        var tips1 = encodeURIComponent($_GET('tip1'));
		var tips2 = encodeURIComponent($_GET('tip2'));
		var tips3 = encodeURIComponent($_GET('tip3'));
		var tips4 = encodeURIComponent($_GET('tip4'));
		var tips5 = encodeURIComponent($_GET('tip5'));
		self.location = 'search.html?tip1='+tips1+'&tip2='+tips2+'&tip3=&tip4='+tips4+'&tip5='+tips5;
    });
    $("#selected-condition-close-04").click(function(){
        var tips1 = encodeURIComponent($_GET('tip1'));
		var tips2 = encodeURIComponent($_GET('tip2'));
		var tips3 = encodeURIComponent($_GET('tip3'));
		var tips4 = encodeURIComponent($_GET('tip4'));
		var tips5 = encodeURIComponent($_GET('tip5'));
		self.location = 'search.html?tip1='+tips1+'&tip2='+tips2+'&tip3='+tips3+'&tip4=&tip5='+tips5;
    });
    $("#selected-condition-close-05").click(function(){
        var tips1 = encodeURIComponent($_GET('tip1'));
		var tips2 = encodeURIComponent($_GET('tip2'));
		var tips3 = encodeURIComponent($_GET('tip3'));
		var tips4 = encodeURIComponent($_GET('tip4'));
		var tips5 = encodeURIComponent($_GET('tip5'));
		self.location = 'search.html?tip1='+tips1+'&tip2='+tips2+'&tip3='+tips3+'&tip4='+tips4+'&tip5=';
    });
});


//通过标签搜索
function findByTips(tip01, tip02, tip03, tip04, tip05){
	var tips1 = encodeURIComponent(encodeURIComponent(tip01));
	var tips2 = encodeURIComponent(encodeURIComponent(tip02));
	var tips3 = encodeURIComponent(encodeURIComponent(tip03));
	var tips4 = encodeURIComponent(encodeURIComponent(tip04));
	var tips5 = encodeURIComponent(encodeURIComponent(tip05));
	if(!tip01){
		tips1 = encodeURIComponent($_GET('tip1'));
	}
	if(!tip02){
		tips2 = encodeURIComponent($_GET('tip2'));
	}
	if(!tip03){
		tips3 = encodeURIComponent($_GET('tip3'));
	}
	if(!tip04){
		tips4 = encodeURIComponent($_GET('tip4'));
	}
	if(!tip05){
		tips5 = encodeURIComponent($_GET('tip5'));
	}

	self.location = 'search.html?tip1='+tips1+'&tip2='+tips2+'&tip3='+tips3+'&tip4='+tips4+'&tip5='+tips5;
}

//搜索排序
function findBySort(sortName) {
	page = $_GET('page');//页码
	findName = encodeURIComponent($_GET('findName'));//搜索关键字
	findName1 = encodeURIComponent($_GET('findName1'));//搜索关键字
	id = $_GET('id');//所选这的主题
	word = encodeURIComponent($_GET('word'));//主题搜索字
	tips1 = encodeURIComponent($_GET('tip1'));//标签1
	tips2 = encodeURIComponent($_GET('tip2'));//标签2
	tips3 = encodeURIComponent($_GET('tip3'));//标签3
	tips4 = encodeURIComponent($_GET('tip4'));//标签4
	tips5 = encodeURIComponent($_GET('tip5'));//标签5
	self.location = 'search.html?tip1='+tips1+'&tip2='+tips2+'&tip3='+tips3+'&tip4='+tips4+'&tip5='+tips5+'&word='+word+'&page='+page+'&findName='+findName+'&findName1='+findName1+'&sort='+sortName+'&id='+id;
}

//页码跳转
function refreshPage(pageNumber) {
	page = pageNumber;//页码
	findName = encodeURIComponent($_GET('findName'));//搜索关键字
	findName1 = encodeURIComponent($_GET('findName1'));//搜索关键字
	id = $_GET('id');//所选这的主题
	word = encodeURIComponent($_GET('word'));//主题搜索字
	tips1 = encodeURIComponent($_GET('tip1'));//标签1
	tips2 = encodeURIComponent($_GET('tip2'));//标签2
	tips3 = encodeURIComponent($_GET('tip3'));//标签3
	tips4 = encodeURIComponent($_GET('tip4'));//标签4
	tips5 = encodeURIComponent($_GET('tip5'));//标签5
	sort = $_GET('sort');//排序
	self.location = 'search.html?tip1='+tips1+'&tip2='+tips2+'&tip3='+tips3+'&tip4='+tips4+'&tip5='+tips5+'&word='+word+'&page='+page+'&findName='+findName+'&findName1='+findName1+'&sort='+sort+'&id='+id;
}

function refresh(){
	//界面主题设置颜色
	var id = $_GET('id');
	var selectObj = "#"+ id + " a";
	$(selectObj).css("color", "#34495E");
	$(selectObj).css("border-bottom", "4px solid #34495E");

	//获取查询关键字
	page = $_GET('page');if(!page||page=='null'){page=1;}
	sort = $_GET('sort');if(!sort||sort==null ){sort="";}
	var findName = $_GET('findName');
	decodeURIComponent(findName);
	var findName1 = $_GET('findName1');
	decodeURIComponent(findName1);
	var word = $_GET('word');
	decodeURIComponent(word);
	var tip1 = $_GET('tip1');
	decodeURIComponent(tip1);
	var tip2 = $_GET('tip2');
	decodeURIComponent(tip2);
	var tip3 = $_GET('tip3');
	decodeURIComponent(tip3);
	var tip4 = $_GET('tip4');
	decodeURIComponent(tip4);
	var tip5 = $_GET('tip5');
	decodeURIComponent(tip5);
	var tip = "";

	//默认为空，查找全部。
	if(!word||word=='null') {
		word="留学领航,求职就业,创业助力,校园生活,猎奇分享";
		$("#hot-theme-list-01").css("color", "#34495E");
		$("#hot-theme-list-01").css("border-bottom", "4px solid #34495E");
	}

	//标签的效果添加和保存。
	if(tip1&&tip1!='null'){
		$("#selected-list-01").css("display","inline-block");
		$("#selected-list-01 .selected-condition").find("p").html(decodeURIComponent(tip1));
		tip = tip1;
	}
	if(tip2&&tip2!='null'){
		$("#selected-list-02").css("display","inline-block");
		$("#selected-list-02 .selected-condition").find("p").html(decodeURIComponent(tip2));
		tip =  tip + ',' + tip2;
	}
	if(tip3&&tip3!='null'){
		$("#selected-list-03").css("display","inline-block");
		$("#selected-list-03 .selected-condition").find("p").html(decodeURIComponent(tip3));
		tip =  tip + ',' + tip3;
	}
	if(tip4&&tip4!='null'){
		$("#selected-list-04").css("display","inline-block");
		$("#selected-list-04 .selected-condition").find("p").html(decodeURIComponent(tip4));
		tip =  tip + ',' + tip4;
	}
	if(tip5&&tip5!='null'){
		$("#selected-list-05").css("display","inline-block");
		$("#selected-list-05 .selected-condition").find("p").html(decodeURIComponent(tip5));
		tip =  tip + ',' + tip5;
	}

	//搜索页面调整
	if(sort=='likeno+') {
		$("#result-content-header-list-02").css({"background":"#56bbe8","color":"#f8f8f8"});
		$("#triangle-up02").attr("src","http://image.1yingli.cn/img/triangle_white_down.png");
		$("#result-content-header-list-02").find('span').text('2');
	}
	if(sort=='likeno-') {
		$("#result-content-header-list-02").css({"background":"#56bbe8","color":"#f8f8f8"});
		$("#triangle-up02").attr("src","http://image.1yingli.cn/img/triangle_white_up.png");
		$("#result-content-header-list-02").find('span').text('1');
	}
	if(sort=='price+') {
		$("#result-content-header-list-03").css({"background":"#56bbe8","color":"#f8f8f8"});
		$("#triangle-up03").attr("src","http://image.1yingli.cn/img/triangle_white_down.png");
		$("#result-content-header-list-03").find('span').text('2');
	}
	if(sort=='price-') {
		$("#result-content-header-list-03").css({"background":"#56bbe8","color":"#f8f8f8"});
		$("#triangle-up03").attr("src","http://image.1yingli.cn/img/triangle_white_up.png");
		$("#result-content-header-list-03").find('span').text('1');
	}
	if(!sort){
       	$("#result-content-header-list-01").css({"background":"#56bbe8","color":"#f8f8f8"});
		$("#result-content-header-list-02").css({"background":"#f8f8f8","color":"#56bbe8"});
       	$("#result-content-header-list-03").css({"background":"#f8f8f8","color":"#56bbe8"});
	}

	//获取数据
	var toSend = new Object();
	toSend.style = "function";
    toSend.method = "search";
    toSend.page =  page + "";
	toSend.filter = sort;
    if(findName&&findName!='null') {
    	toSend.word = findName;
    	$("#search-input").val(decodeURIComponent(findName));
    	$("#hot-theme-list-01").css("color", "#34495E");
		$("#hot-theme-list-01").css("border-bottom", "4px solid #34495E");
		$("#search-input").focus();
    } else if(findName1&&findName1!='null') {
    	toSend.word = findName1;
    	$("#search-input1").val(decodeURIComponent(findName1));
    	$("#hot-theme-list-01").css("color", "#34495E");
		$("#hot-theme-list-01").css("border-bottom", "4px solid #34495E");
		$("#search-input1").blur();
    } else if(tip){
		toSend.word = tip;
	} else {
    	toSend.tips = word;
    }


	setTimeout($.ajax({
		cache: true,
		type: "POST",
		url: config.base_url,
		data: $.toJSON(toSend),
		async: true,
		error: function (request) {
			$(".mark").show();
			$("#box").show();
		},
		success: function (data, textStatu) {
			var json = eval("(" + data + ")");
			if (json.state == "success") {
				var result = $.parseJSON(json.result);
				html = "";
				$.each(result, function (index, content) {
					html = html + "<div class='one_person' ><a href='personal.html?tid=" + content.id + "' target='_blank'><img src='" + content.iconurl + "' ><div class='import_info'><p class='person_topic'>" + content.servicetitle + "</p><p class='person_info'>" + content.name + "<span>&nbsp;&nbsp;" + content.simpleinfo + "</span> </p><p class='person_likes'>" + content.servicecontent + "</p></div><div class='price'> <p class='money'>" + parseInt(content.serviceprice) + "元/" + parseInt(content.servicetime) + "时</p><p class='like'><img style='margin-left:0px;' src='http://image.1yingli.cn/img/heart.png' >" + content.likeno + " 人想见</p><p class='times'>本周可咨询" + content.timeperweek + "次</p></div></a></div>";
				});
				if (html == "") {
					html = "<h1>没有找到相关导师</h1>"
				}
				$("#sercher_result").html(html);
				totalPage = Math.ceil(json.viewtotal / 9);
				makePager();
				//totalPage = 30;
			} else {
				//alert(json.msg);
			}
		}
	}), 100);
}

function makePager() {
	//先删除后添加页数
	//页码设置
	var basePage = 10;
	var leastPage = 2
	var showPage = 4;
	var mostPage = parseInt(leastPage) + parseInt(showPage);
	var afterShowPage = parseInt(page) + showPage;
	var beforeShowPage = parseInt(page) - showPage;
	for (var i = totalPage; i >= 1; i--) {
		$("#btn" + i).remove();
	}
	if (totalPage <= basePage) {
		for (var i = 1; i <= totalPage; i++) {
			$("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:refreshPage(" + i + ")' >" + i + "</a>");
		}
	} else {
		for (var i = 1; i <= totalPage; i++) {
			if (page == 1) {
				$("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:refreshPage(" + i + ")' >" + i + "</a>");
				if (i == 5) {
					$("#btnNext").before("<span class='pager' id='diandian'>...</span>");
					$("#btnNext").before("<a class='pager' id='btn" + totalPage + "' href='javascript:refreshPage(" + totalPage + ")' >" + totalPage + "</a>");
					break;
				}
			} else if (page <= mostPage) {
				$("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:refreshPage(" + i + ")' >" + i + "</a>");
				if (i == afterShowPage) {
					$("#btnNext").before("<span class='pager' id='diandian'>...</span>");
					$("#btnNext").before("<a class='pager' id='btn" + totalPage + "' href='javascript:refreshPage(" + totalPage + ")' >" + totalPage + "</a>");
					break;
				}
			} else {
				if (i <= leastPage) {
					$("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:refreshPage(" + i + ")' >" + i + "</a>");
					if (i == leastPage) {
						$("#btnNext").before("<span class='pager' id='diandian'>...</span>");
					}
				}
				if (beforeShowPage <= i && i <= afterShowPage) {
					$("#btnNext").before("<a class='pager' id='btn" + i + "' href='javascript:refreshPage(" + i + ")' >" + i + "</a>");
				}
				if (i == afterShowPage) {
					if (afterShowPage != totalPage) {
						$("#btnNext").before("<span class='pager' id='diandian'>...</span>");
						$("#btnNext").before("<a class='pager' id='btn" + totalPage + "' href='javascript:refreshPage(" + totalPage + ")' >" + totalPage + "</a>");
						break;
					}
				}
			}
		}
	}
	$("#btn" + page).attr("class", "active");
}

//分页排序
var totalPage = 1;
var page = 1;
var sort = '';
function nextPage(){
    if(page >= totalPage) {
        return;
    } else {
        page = parseInt(page)+ 1;
		refreshPage(page);
    }
}
function lastPage(){
    if(page <= 1) {
        return;
    } else {
        page = page - 1;
        refreshPage(page);
    }
}
function finalPage(){
    if(page != totalPage) {
   		refreshPage(totalPage);
    }
}
function firstPage(){
    if(page != 1) {
   		refreshPage(1);
    }
}

function mobile_search() {
    var findName1 = encodeURIComponent(encodeURIComponent($("#mobile_search").val()));
    self.location = 'search.html?findName1=' + findName1;
}