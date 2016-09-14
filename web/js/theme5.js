$(document).ready(function(){
                
	refreshTeacherList();	
   
    $("#menu").hover(function(){
        $(".m-name ul").css("display","block");
    });
    $("#menu").mouseleave(function(){
        $(".m-name ul").css("display","none");
    });  
    $("#onebtn").hover(function(){
        $("#onebtn").css("background","#56bbe8");
    });
    $("#onebtn").mouseleave(function(){
        $("#onebtn").css("background","#fff");
    });    
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
           
            
});


var lastId = "max";

function refreshTeacherList(){
	var toSend = new Object();
	toSend.style = "teacher";
	toSend.method = "getTeacherSInfoList";
	toSend.tip = "16";
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
                        var tlist = $.parseJSON(json.data);
                        lastId = (json.lastId=="none"?lastId:json.lastId);
                        var html = "";
                        $.each(tlist,function(index,content){
                        	html = html + "<li class=\"course-one\">"
                      					+" <a href=\"personal.html?tid="+content.teacherId+"\" target=\"_blank\">"
                        				+" <div class=\"result-content-results-top\">"
                            			+" <img class=\"face\" src=\""+(content.iconUrl==""?"img/img.png":content.iconUrl)+"\" alt=\"\"/>"
                            			+" <p class=\"people-want\" >"+content.likeNo+"人想见</p>"
                            			//+" <p class=\"label\">"+content.name+"</p>"
                        				+" </div>";
                         
                            html = html +" <p class='topic'>"+content.serviceTitle+"</p>";           
                        	html = html +" <div class='assess'><p class='label'>"+content.name+"</p>";
                        	for(var i = 1;i<=content.level;i++){
                        		html = html +"<img  class='assess-star' src='http://image.1yingli.cn/img/yellow_star.png' alt=''/>";
                            }
                            html = html +"<p style='float:right;margin-right:20px;font-size:12px;margin-top: 5px;color: #b6b6b6;'>本周可咨询"+content.timeperweek+"次</p>"
							html = html + "</div><div class='home-company'><img src='http://image.1yingli.cn/img/home-company.png'><label>"+content.simpleinfo+"</label></div>";
                        });
						$("#teacher_list").append(html);
                    } else {
                        $(".mark").show();
                        $("#erro").show();
                    }
                }
        });
        $("#more a").attr("href", "search.html?word="+encodeURIComponent(encodeURIComponent("猎奇分享"))+"&id=hot-theme-list-06");

}