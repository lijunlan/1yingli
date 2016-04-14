var url = window.location.href;
var mid;

checkLogin();
registNotify();
document.getElementById("admin_name").innerText = $.cookie('mname');

var t1 = self.location.href;
console.log(t1);
var t2 = t1.toString().split("?");
if (t2.length > 1) {
    var t3 = t2[1].split("=");
    if (t3.length > 1) {
        var t4 = t3[1];
        var t5 = decodeURI(t4);
        json = JSON.parse(t5);
    }
}

$(document).ready(function () {
    $("#select-picture-src").uploadifive({
        'buttonText': '浏览',
        'buttonClass': 'file-box',
        'queueID': 'imagelist',
        'auto': false,
        'method': 'post',
        'fileType': 'image/*',
        'queueSizeLimit': 10,
        'multi': false,
        'uploadScript': 'http://service.1yingli.cn/yiyingliService/upimage',
        'formData': {},
        'onInit': function () {
            //alert('Add files to the queue to start uploading.');
        },
        'onFallback': function () {
            alert('Oops!  You have to use the non-HTML5 file uploader.');
        },
        'onSelect': function (queue) {
            //alert(queue.queued + ' files were added to the queue.');
            $("#imagelist").fadeIn();
        },
        'onUpload': function (filesToUpload) {
            // alert(filesToUpload + ' files will be uploaded.');
        },
        'onError': function (errorType) {
            //alert('The error was: ' + errorType);
        },
        'onUploadComplete': function (file, data) {
            //alert('The file ' + file.name + ' uploaded successfully.');
            var json = eval("(" + data + ")");
            if (json.state == "success") {
                $("#imagelist").fadeOut(3000);
                $("#iconUrl").val(json.url.toString().split("@!")[0] + "@!icon");
                $("#littleIcon").attr('src', json.url.toString().split("@!")[0] + "@!icon");
                Messenger().post("图片添加完成");
            } else {
                $(".mark").show();
                $("#box").show();
                $("#bomb").html("上传信息失败");
                $("#connect").attr('href', 'general.html');
            }
        }
    });
});

var changeTable = function (result) {
    $("#activityDes").val(result.activityDes);
    $("#iconUrl").val(result.img);
    $("#activityWeight").val(result.weight);
};


$(function(){
    if(json==null){
        alert("error");
    }else{
        changeTable(json);
    }
});

var edit = function () {
    myJson.activityDes = $("#activityDes").val();
    myJson.img = $("#iconUrl").val();
    myJson.weight=$("#activityWeight").val();
    myJson.contentAndPageId = json.contentId;
    myJson.method = "editActivityContent";
    myAjax(myJson, null);
    Messenger().post("修改成功");
};

