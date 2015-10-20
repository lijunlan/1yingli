$(document).ready(function(){
    var u = $.cookie('uid');
    var n = $.cookie('nickName');
    var i = $.cookie('iconUrl');
    
    <!-- Login  -->
    if(u==null||n==null||i==null){
        self.location='login.html';
        return;
    }

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

    if(strWE!=null){
    	try{
            workExp = $.parseJSON(jw);
        }catch(e){
            //TODO
        }
    }
    if(strSE!=null){
    	try{
            schoolExp = $.parseJSON(js);
        }catch(e){
            //TODO
        }
    }

    refreshExp(); 

    //workExp[0]={companyName:'',position:'',description:'',startTime:'',endTime:''} 

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
    $("#schoolNameSE").mousedown(function(){
        $("#schoolNameTab").css("display", "none");
    });
    $("#degreeSE").mousedown(function(){
        $("#degreeTab").css("display", "none");
    });
    $("#majorSE").mousedown(function(){
         $("#majorTab").css("display", "none");
    });
    $("#companyNameWE").mousedown(function(){
        $("#companyNameTab").css("display", "none");
    });
    $("#positionWE").mousedown(function(){
         $("#positionTab").css("display", "none");
    });

    //时间格式约束
    $("#we_et_y").change(function(){
        if($(this).val() == "至今"){
            $("#we_et_m").val("");
            $("#we_et_m").attr("disabled", "true");
        }else {
            $("#we_et_m").removeAttr("disabled");
        }
        
    });
    $("#se_et_y").change(function(){
        if($(this).val() == "至今"){
            $("#se_et_m").val("");
            $("#se_et_m").attr("disabled", "true");
        }else {
            $("#se_et_m").removeAttr("disabled");
        }
    });

    if($("#we_et_y").val() == '至今') {
         $("#we_et_m").val("");
         $("#we_et_m").attr("disabled", "true");
    }
    if($("#se_et_y").val()=='至今') {
        $("#se_et_m").val("");
        $("#se_et_m").attr("disabled", "true");
    } 
});

function setupSelect(){
    var html="";
    for(i=0;i<=75;i++){
        html=html+"<option value=\""+(2015-i)+"\">"+(2015-i)+"</option>";
    }
    $(".mid").append(html);
}

var workExp=[];
var schoolExp=[];

Array.prototype.remove=function(dx) 
{ 
    if(isNaN(dx)||dx>this.length){return false;} 
    for(var i=0,n=0;i<this.length;i++) 
    { 
        if(this[i]!=this[dx]) 
        { 
            this[n++]=this[i] 
        } 
    } 
    this.length-=1 
}

function removeWE(index){
    workExp.remove(index);
    refreshExp();
}

function editWE(index){
    $("#workEdit"+index).css("display","");
}

function cancelEditWE(index){
    $("#workEdit"+index).css("display","none");
}

function doneWEEdit(index){
    if($("#companyName"+index).val()==""||$("#position"+index).val()==""||$("#wedescription"+index).val()==""){
        $(".mark").show();
        $("#box").show();
        $("#bomb").html("请将信息填写完整");
        $("#connect").attr('href','##');
        $("#connect").click(function(){
        	$(".mark").hide();
        	$("#box").hide();
        });
        return;
    }
    var obj = workExp.slice(index)[0];
    obj.companyName = $("#companyName"+index).val();
    obj.position = $("#position"+index).val();
    obj.description = $("#wedescription"+index).val();
    var ety = $("#we_et_y"+index).val();
    var etm = $("#we_et_m"+index).val();
    var stm = $("#we_st_m"+index).val();
    var sty = $("#we_st_y"+index).val();
    obj.startTime = sty + "," + stm;
    obj.endTime = ety +　"," + etm;
    $("#workEdit"+index).css("display","none");
    refreshExp();
}

function addWE(){
     $("#workAdd").css("display","");
}

function cancelAddWE(){
    $("#workAdd").css("display","none");
}

function doneAddWE(){
    var companyNameWE = $("#companyNameWE");
    var positionWE = $("#positionWE");
    var descriptionWE = $("#descriptionWE");
    var we_st_m = $("#we_st_m");
    var we_st_y = $("#we_st_y");
    var we_et_m = $("#we_et_m");
    var we_et_y = $("#we_et_y");

    var isTrue = true;
    if(!companyNameWE.val()){
        $("#companyNameTab").css("display", "block");
        isTrue = false;
    }
    if(!positionWE.val()){
        $("#positionTab").css("display", "block");
        isTrue = false;
    }
    if(!isTrue) {
        return;
    }


    var obj = new Object();
    obj.companyName = companyNameWE.val();
    obj.position = positionWE.val();
    obj.description = descriptionWE.val();
    obj.startTime = we_st_y.val() + "," + we_st_m.val();
    if(we_st_y.val() == '至今') {
        obj.endTime = we_et_y.val();
    } else {
        obj.endTime = we_et_y.val() +　"," + we_et_m.val();
    }
   
    companyNameWE.val("");
    positionWE.val("");
    descriptionWE.val("");
    we_st_y.val("2015");
    we_st_m.val("1");
    we_et_y.val("2015");
    we_et_m.val("1");

    workExp.push(obj);
    $("#workAdd").css("display","none");
    refreshExp();
}

function removeSE(index){
    schoolExp.remove(index);
    refreshExp();
}

function editSE(index){
    $("#schoolEdit"+index).css("display","");
}

function cancelEditSE(index){
    $("#schoolEdit"+index).css("display","none");
}

function doneSEEdit(index){
    if($("#schoolName"+index).val()==""||$("#degree"+index).val()==""||$("#major"+index).val()==""||$("#sedescription"+index).val()==""){
        $(".mark").show();
        $("#box").show();
        $("#bomb").html("请将信息填写完整");
        $("#connect").attr('href','##');
        $("#connect").click(function(){
        	$(".mark").hide();
        	$("#box").hide();
        });
        return;
    }
    var obj = schoolExp.slice(index)[0];
    obj.schoolName = $("#schoolName"+index).val();
    obj.degree = $("#degree"+index).val();
    obj.major = $("#major"+index).val();
    obj.description = $("#sedescription"+index).val();
    var ety = $("#se_et_y"+index).val();
    var etm = $("#se_et_m"+index).val();
    var stm = $("#se_st_m"+index).val();
    var sty = $("#se_st_y"+index).val();
    obj.startTime = sty + "," + stm;
    if(ety == '至今') {
        obj.endTime = ety;
    } else {
        obj.endTime = ety +　"," + etm;
    }
    
    $("#schoolEdit"+index).css("display","none");
    refreshExp();
}

function addSE(){
    $("#schoolAdd").css("display","");
}

function cancelAddSE(){
    $("#schoolAdd").css("display","none");
}

function doneAddSE(){
    var schoolNameSE = $("#schoolNameSE");
    var degreeSE = $("#degreeSE");
    var majorSE = $("#majorSE");
    var descriptionSE = $("#descriptionSE");
    var se_st_m = $("#se_st_m");
    var se_st_y = $("#se_st_y");
    var se_et_m = $("#se_et_m");
    var se_et_y = $("#se_et_y");

    var isTrue = true;
    if(!schoolNameSE.val()){
        $("#schoolNameTab").css("display", "block");
        isTrue = false;
    }
    if(!degreeSE.val()){
        $("#degreeTab").css("display", "block");
        isTrue = false;
    }
    if(!majorSE.val()){
        $("#majorTab").css("display", "block");
        isTrue = false;
    }
    if(!isTrue) {
        return;
    }

    var obj = new Object();
    obj.schoolName = schoolNameSE.val();
    obj.degree = degreeSE.val();
    obj.major = majorSE.val();
    obj.description = descriptionSE.val();
    obj.startTime = se_st_y.val() + "," + se_st_m.val();
    obj.endTime = se_et_y.val() +　"," + se_et_m.val();

    schoolNameSE.val("");
    degreeSE.val("");
    majorSE.val("");
    descriptionSE.val("");
    se_st_y.val("2015");
    se_st_m.val("1");
    se_et_y.val("2015");
    se_et_m.val("1");

    schoolExp.push(obj);
    $("#schoolAdd").css("display","none");
    refreshExp();
}

function refreshExp(){
    var html = "";
    $.each(workExp,function(index,content){
        var st = content.startTime.split(",");
        var et = content.endTime.split(",");
        var endDate = "至今";
        if(et[0]) {
            endDate = et[0];
        } else {
            endDate = et[0]+"年"+et[1]+"月";
        }
        html =html+"<div class=\"hd\" id=\"we"+index+"\">"
+"                        <span>已添加工作经历</span>"
+"                        <a href=\"javascript:removeWE("+index+")\" class=\"u-delete\"><img src=\"http://image.1yingli.cn/images/delete.png\"></a>"
+"                        <a href=\"javascript:editWE("+index+")\" class=\"u-editor\"><img src=\"http://image.1yingli.cn/images/editor.png\"></a>"
+"                    </div>"
+"                    <div class=\"con\">"
+"                        <div class=\"part\">"
+"                            <p>公司："+content.companyName+"</p>"
+"                            <p>职位："+content.position+"</p>"
+"                            <p>时间："+st[0]+"年"+st[1]+"月-" + endDate + "</p>"
+"                        </div>"
+"                        <div class=\"part\">"
+"                            <div class=\"left\">在职经历：</div>"
+"                            <div class=\"right\">"+content.description+"</div>"
+"                        </div>"
+"                    </div>"
+"                <div class=\"m-floatCon\" id=\"workEdit"+index+"\" style=\"display:none;\">"
+"                    <div class=\"con m-editor\" style='height:570px;'>"
+"                        <h3>添加工作经历＊</h3>"
+"                        <p>*为必填项，添加之后请保存</p>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"companyName"+index+"\">公司名称＊</label>"
+"                            <input id=\"companyName"+index+"\" type=\"text\"  class=\"u-input\" value=\""+content.companyName+"\"/>"
+"                        </div>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"position"+index+"\">所在职位＊</label>"
+"                            <input id=\"position"+index+"\" type=\"text\" class=\"u-input\" value=\""+content.position+"\" />"
+"                        </div>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"time\">起止时间＊</label>"
+"                            <div class=\"m-timebox f-cb\">"
+"                                <select id=\"we_st_m"+index+"\">"
+"                                    <option value=\""+st[1]+"\">"+st[1]+"</option>"
+"                                    <option value=\"1\">1</option>"
+"                                    <option value=\"2\">2</option>"
+"                                    <option value=\"3\">3</option>"
+"                                    <option value=\"4\">4</option>"
+"                                    <option value=\"5\">5</option>"
+"                                    <option value=\"6\">6</option>"
+"                                    <option value=\"7\">7</option>"
+"                                    <option value=\"8\">8</option>"
+"                                    <option value=\"9\">9</option>"
+"                                    <option value=\"10\">10</option>"
+"                                    <option value=\"11\">11</option>"
+"                                    <option value=\"12\">12</option>"
+"                                </select>月"
+"                                <select class=\"mid\" id=\"we_st_y"+index+"\">"
+"                                    <option value=\""+st[0]+"\">"+st[0]+"</option>"
+"                                </select>年"
+"                                至&nbsp;&nbsp;&nbsp;&nbsp;"
+"                                <select id=\"we_et_m"+index+"\">"
+"                                    <option value=\""+et[1]+"\">"+et[1]+"</option>"
+"                                    <option value=\"1\">1</option>"
+"                                    <option value=\"2\">2</option>"
+"                                    <option value=\"3\">3</option>"
+"                                    <option value=\"4\">4</option>"
+"                                    <option value=\"5\">5</option>"
+"                                    <option value=\"6\">6</option>"
+"                                    <option value=\"7\">7</option>"
+"                                    <option value=\"8\">8</option>"
+"                                    <option value=\"9\">9</option>"
+"                                    <option value=\"10\">10</option>"
+"                                    <option value=\"11\">11</option>"
+"                                    <option value=\"12\">12</option>"
+"                                </select>月"
+"                                <select class=\"mid\" id=\"we_et_y"+index+"\">"
+"                                    <option value=\""+et[0]+"\">"+et[0]+"</option>"
+"                                </select>年"
+"                           </div>"
+"                        </div>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"wedescription"+index+"\">在职经历</label>"
+"                            <textarea id=\"wedescription"+index+"\" maxlength=\"500\" onchange=\"this.value=this.value.substring(0, 500)\" onkeydown=\"this.value=this.value.substring(0, 500)\" onkeyup=\"this.value=this.value.substring(0, 500)\">"+content.description+"</textarea>"
+"                        </div>"
+"                        <div class=\"m-btn m-btn2\" style='margin-top: 120px;'>"
+"                            <a href=\"javascript:doneWEEdit("+index+")\" class=\"u-save\">保存</a>"
+"                            <a href=\"javascript:cancelEditWE("+index+")\" class=\"u-cancle\">取消</a>"
+"                        </div>"
+"                    </div>"
+"                </div>";
    });
    $("#we-list").html(html);
    html = "";
    $.each(schoolExp,function(index,content){
        var st = content.startTime.split(",");
        var et = content.endTime.split(",");
        var endDate = "至今";
        if(et[0]) {
            endDate = et[0];
        } else {
            endDate = et[0]+"年"+et[1]+"月";
        }
        html =html+"<div class=\"hd\" id=\"se"+index+"\">"
+"                      <span>已添加教育经历</span>"
+"                          <a href=\"javascript:removeSE("+index+")\" class=\"u-delete\"><img src=\"http://image.1yingli.cn/images/delete.png\"></a>"
+"                          <a href=\"javascript:editSE("+index+")\" class=\"u-editor\"><img src=\"http://image.1yingli.cn/images/editor.png\"></a>"
+"                    </div>"
+"                    <div class=\"con\">"
+"                        <div class=\"part\">"
+"                            <p>学校："+content.schoolName+"</p>"
+"                            <p>学历："+content.degree+"</p>"
+"                            <p>专业："+content.major+"</p>"
+"                            <p>时间："+st[0]+"年"+st[1]+"月-"+endDate+"</p>"
+"                        </div>"
+"                        <div class=\"part\">"
+"                            <div class=\"left\">在校经历：</div>"
+"                            <div class=\"right\">"+content.description+"</div>"
+"                        </div>"
+"                    </div>"
+"                <div class=\"m-floatCon\" id=\"schoolEdit"+index+"\" style=\"display:none;\">"
+"                    <div class=\"con m-editor\" style='height:570px;'>"
+"                        <h3>修改已添加教育经历<img src=\"http://image.1yingli.cn/images/editor.png\"></h3>"
+"                        <p>*为必填项，添加之后请保存</p>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"schoolName"+index+"\">学校名称＊</label>"
+"                            <input id=\"schoolName"+index+"\" type=\"text\"  class=\"u-input\" value=\""+content.schoolName+"\"/>"
+"                        </div>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"degree"+index+"\">学历＊</label>"
+"                            <input id=\"degree"+index+"\" type=\"text\"  class=\"u-input\" value=\""+content.degree+"\"/>"
+"                        </div>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"major"+index+"\">专业名称＊</label>"
+"                            <input id=\"major"+index+"\" type=\"text\" class=\"u-input\" value=\""+content.major+"\"/>"
+"                        </div>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"time\">起止时间＊</label>"
+"                            <div class=\"m-timebox f-cb\">"
+"                                <select id=\"se_st_m"+index+"\">"
+"                                    <option value=\""+st[1]+"\">"+st[1]+"</option>"
+"                                    <option value=\"1\">1</option>"
+"                                    <option value=\"2\">2</option>"
+"                                    <option value=\"3\">3</option>"
+"                                    <option value=\"4\">4</option>"
+"                                    <option value=\"5\">5</option>"
+"                                    <option value=\"6\">6</option>"
+"                                    <option value=\"7\">7</option>"
+"                                    <option value=\"8\">8</option>"
+"                                    <option value=\"9\">9</option>"
+"                                    <option value=\"10\">10</option>"
+"                                    <option value=\"11\">11</option>"
+"                                    <option value=\"12\">12</option>"
+"                                </select>月"
+"                                <select class=\"mid\" id=\"se_st_y"+index+"\">"
+"                                    <option value=\""+st[0]+"\">"+st[0]+"</option>"
+"                                </select>年"
+"                                至&nbsp;&nbsp;&nbsp;&nbsp;"
+"                                <select id=\"se_et_m"+index+"\">"
+"                                    <option value=\""+et[1]+"\">"+et[1]+"</option>"
+"                                    <option value=\"1\">1</option>"
+"                                    <option value=\"2\">2</option>"
+"                                    <option value=\"3\">3</option>"
+"                                    <option value=\"4\">4</option>"
+"                                    <option value=\"5\">5</option>"
+"                                    <option value=\"6\">6</option>"
+"                                    <option value=\"7\">7</option>"
+"                                    <option value=\"8\">8</option>"
+"                                    <option value=\"9\">9</option>"
+"                                    <option value=\"10\">10</option>"
+"                                    <option value=\"11\">11</option>"
+"                                    <option value=\"12\">12</option>"
+"                                </select>月"
+"                                <select class=\"mid\" id=\"se_et_y"+index+"\">"
+"                                    <option value=\""+et[0]+"\">"+et[0]+"</option>"
+"                                </select>年"
+"                            </div>"
+"                        </div>"
+"                        <div class=\"m-input m-input-2\">"
+"                            <label for=\"sedescription"+index+"\">在校经历</label>"
+"                            <textarea id=\"sedescription"+index+"\" maxlength=\"500\" onchange=\"this.value=this.value.substring(0, 500)\" onkeydown=\"this.value=this.value.substring(0, 500)\" onkeyup=\"this.value=this.value.substring(0, 500)\">"+content.description+"</textarea>"
+"                        </div>"
+"                        <div class=\"m-btn m-btn2\" style='margin-top: 120px;'>"
+"                            <a href=\"javascript:doneSEEdit("+index+")\" class=\"u-save\">保存</a>"
+"                            <a href=\"javascript:cancelEditSE("+index+")\" class=\"u-cancle\">取消</a>"
+"                        </div>"
+"                    </div>"
 +"               </div>";
    });
    $("#se-list").html(html);
    setupSelect();
}

function last(){
    var strWE = $.toJSON(workExp);
    var strSE = $.toJSON(schoolExp);
    // alert(strWE);
    // alert(strSE);
    $.cookie("workExp",strWE,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
    $.cookie("schoolExp",strSE,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
    self.location='bct1.html';
}

function next(){
	if(schoolExp=="" && workExp=="") {
		addSE();
		addWE();
		return;
	}
	if(schoolExp=="") {
		addSE();
		return;
	}
	if(workExp=="") {
		addWE();
		return;
	}
	
	var strWE = $.toJSON(workExp);
    var strSE = $.toJSON(schoolExp);
	$.cookie("workExp",strWE,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	$.cookie("schoolExp",strSE,{path:'/',domain:".1yingli.cn",secure:false,raw:false});
	self.location='bct3.html';
}
