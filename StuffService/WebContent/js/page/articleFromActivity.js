/**
 * Created by RoRo on 2016/4/15.
 */
var page = 1;
var url = window.location.href;
var mid;

checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');

var tmp = url.split("?");
var key;//活动标示
if(tmp.length>1){
    var t2 = tmp[1].split("=");
    if(t2.length>1){
        if(t2[0]=="key"){
            key=t2[1];
        }else{
            self.location.href="index.html";
        }
    }else{
        self.location.href="index.html";
    }
}else{
    self.location.href="index.html";
}

$(function() {
    myJson.method = "getActivityTeacherPassage";
    myJson.key = key;
    myJson.state = 0;
    myJson.page = page;
    myAjax(myJson, changeTable);
})

var changeTable = function (result) {
    $("#articleTable").empty();
    $("#articleTable").append(
        "<tr><th>ID</th><th>文章标题</th><th>作者</th><th>状态</th></tr>");
    var list = result.data;
    $.each(list, function (index, data) {
        //var tmp = parseInt(data.createTime, 10);
        //var d = new Date(tmp);
        var row = "<tr><td>";
        row += data.passageId +"</td><td>";
        row += data.title + "</td><td>";
        row += data.editorName + "</td><td>";
        if(data.isStage==0)
            row += "未登入</td>";
        else
            row += "已登入</td>";
        $("#articleTable").append(row);
    });
};


//换页
function changePage(action) {
    if (action == 'last')
        page--;
    else if (action == 'next')
        page++;
    document.getElementById("pageInput").value = page;
    if (page <= 1)
        document.getElementById("lastPage").disabled = true;
    else
        document.getElementById("lastPage").disabled = false;
    get();
}

//获取页面内容
function get() {
    page = document.getElementById("pageInput").value;
    if (page <= 1)
        document.getElementById("lastPage").disabled = true;
    else
        document.getElementById("lastPage").disabled = false;
    //myJson中的其他数据（mid，style）并不需要改变，因此统一在StuffService-common.js中赋值
    myJson.page = page.toString();
    myJson.method = "getActivityTeacherPassage";
    //按状态获取
    myJson.state = document.getElementById("articleState").value;
    //封装的ajax方法
    //参数action传入1表示ajax返回值包含data，并使用changeTable函数来处理
    myAjax(myJson, changeTable);
}




