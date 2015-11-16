$(document).ready(function(){
	var mid = $.cookie("mid");
	var mname = $.cookie("mname");
	if(mid==null)self.location = 'manage_login.html';
	refresh();
});

function refresh(){
	$.ajax({
                cache : true,
                type : "POST",
                url : config.base_url,
                data : "{'style':'manager','method':'getApplicationFormList','mid':'"+$.cookie("mid")+"'}",
                async : false,
                error : function(request) {
                            alert("Connection error");
                        },
                success : function(data, textStatu) {
                    var json = eval("(" + data + ")");
					if (json.state == "success") {
                       	var datalist = $.parseJSON(json.data);
                       	var html = "";
                       	$.each(datalist,function(index,data){
							var userid = data.userId;
							var phone = data.phone;
							var name = data.name;
							var email = data.email;
							var address = data.address;
							var checkInfo = data.checkInfo;
							if(checkInfo=="undefined")checkInfo="";
							var serviceReason = data.serviceReason;
							var serviceTitle = data.serviceTitle;
							var serviceAdvantage = data.serviceAdvantage;
							var serviceContent = data.serviceContent;
							var servicePrice = data.servicePrice;
							var serviceTime = data.serviceTime;
							var endTime = data.endTime;
							var createTime = data.createTime;
							var state = data.state;
							var workExperience = $.parseJSON(data.workExperience);
							var schoolExperience = $.parseJSON(data.schoolExperience);
							var tips = $.parseJSON(data.tips);
							var afId = data.afId;
							html = html +  "<tr>"
										+"	<td>"
										+	afId
										+"	</td>"
										+"	<td>"
										+	userid
										+"	</td>"
										+"	<td>"
										+	createTime+"/</br>"+endTime
										+"	</td>"
										+"	<td>"
										+	state
										+"	</td>"
										+"	<td>"
										+	phone
										+"	</td>"
										+"	<td>"
										+	address
										+"	</td>"
										+"	<td>"
										+	email
										+"	</td>"
										+"	<td>";
										$.each(workExperience,function(index,content){
											html = html + "公司名称:"+content.company+"</br>职位:"+content.position+"</br>在职经历:"+content.description+"</br>开始时间:"+content.startTime+"</br>结束时间:"+content.endTime+"</br></br>";
										});	
										html = html 
										+"	</td>"
										+"	<td>";
										$.each(schoolExperience,function(index,content){
											html = html + "学校名称:"+content.school+"</br>专业:"+content.major+"</br>学历:"+content.degree+"</br>在校经历:"+content.description+"</br>开始时间:"+content.startTime+"</br>结束时间:"+content.endTime+"</br></br>";
										});
										html = html 
										+"	</td>"
										+"	<td>";
										$.each(tips,function(index,content){
											html = html +　content.name+"</br></br>";
										});
										html = html 
										+"	</td>"
										+"	<td>"
										+"	服务价格:"+servicePrice+"</br>服务时间:"+serviceTime+"</br>名称:"+serviceTitle+"</br>服务原因:"+serviceReason+"</br>服务内容:"+serviceContent+"</br>优势:"+serviceAdvantage+"</br>"
										+"	</td>"
										+"	<td>"
										+"	<textarea id=\"checkInfo_"+afId+"\" placeholder=\""+((state=="审核处理中")?"审核意见(描述不通过的理由,通过可以不填)":checkInfo)+"\" rows=\"5\"></textarea></br></br>"
										+"	<button onclick=\"accept("+afId+")\" "+((state!="审核处理中")?"style=\"display:none\"":"")+">通过</button></br></br>"
										+"	<button onclick=\"refuse("+afId+")\" "+((state!="审核处理中")?"style=\"display:none\"":"")+">拒绝</button>"
										+"	</td>"
										+"	</tr>";
						});
						$("#application_list").html(html);
                    } else {
                        alert(json.msg);
                    }
                }
            });
}

function accept(afid){
	var description = $("#checkInfo_"+afid).val();
	$.ajax({
                cache : true,
                type : "POST",
                url : config.base_url,
                data : "{'style':'manager','method':'doneApplicationForm','mid':'"+$.cookie("mid")+"','afId':'"+afid+"','accept':'true','description':'"+description+"'}",
                async : false,
                error : function(request) {
                            alert("Connection error");
                        },
                success : function(data, textStatu) {
                    var json = eval("(" + data + ")");
					if (json.state == "success") {
                       	refresh();
                    } else {
                        alert(json.msg);
                    }
                }
            });
}

function refuse(afid){
	var description = $("#checkInfo_"+afid).val();
	if(description==null||description==""){
		return;
	}
	$.ajax({
                cache : true,
                type : "POST",
                url : config.base_url,
                data : "{'style':'manager','method':'doneApplicationForm','mid':'"+$.cookie("mid")+"','afId':'"+afid+"','accept':'false','description':'"+description+"'}",
                async : false,
                error : function(request) {
                            alert("Connection error");
                        },
                success : function(data, textStatu) {
                    var json = eval("(" + data + ")");
					if (json.state == "success") {
                        refresh();
                    } else {
                        alert(json.msg);
                    }
                }
            });
}


function mlogout(){
	$.ajax({
                cache : true,
                type : "POST",
                url : config.base_url,
                data : "{'style':'manager','method':'logout','mid':'" + $.cookie('mid') + "'}",
                async : false,
                error : function(request) {
                            alert("Connection error");
                        },
                success : function(data, textStatu) {
                    var json = eval("(" + data + ")");
                    if (json.state == "success") {
                        $.cookie("mid",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                        $.cookie("mname",'',{expires: -1,path:'/',domain:".1yingli.cn",secure:false,raw:false});
                        self.location='manage_login.html';
                        return;
                    } else {
                        alert(json.msg);
                    }
                }
            });
}