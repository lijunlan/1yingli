$(document).ready(function(){
            
	refreshTeacherList();	
        
            
});


var lastId = "max";

function refreshTeacherList(){
	var toSend = new Object();
	toSend.style = "teacher";
	toSend.method = "getTeacherSInfoList";
	toSend.tip = "2";
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
                        //alert(json.msg);
                    }
                }
        });
        $("#more a").attr("href", "search.html?word="+encodeURIComponent(encodeURIComponent("求职就业"))+"&id=hot-theme-list-03");

}