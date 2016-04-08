/**
 * Created by RoRo on 2016/4/8.
 */
//初始化
var mid;
var oldAliPayNumber,oldPayPalNumber;
checkLogin();
registNotify();

//获取导师信息
function get() {
    if ($("#inputSearchTeacherID").val() == "") {
        return;
    }
    myJson.method = "getPayInformation";
    myJson.teacherId = $("#inputSearchTeacherID").val();
    myAjax(myJson, getAndParse);
}

//解析导师信息，并显示
function getAndParse(servicePro) {
    if (servicePro != null) {
        oldAliPayNumber = servicePro.alipayNo;
        oldPayPalNumber = servicePro.paypal;
        $("#userId").val($("#inputSearchTeacherID").val());
        $("#userName").val(servicePro.teacherName);
        $("#aliPayNumber").val(oldAliPayNumber);
        $("#payPalNumber").val(oldPayPalNumber);
    }
    
}

//提交导师信息
function change(){
    var teacherAccount = new Object();
    var send = new Object();
    
    var tempAliPayNumber = $("#aliPayNumber").val();
    var tempPayPalNumber = $("#payPalNumber").val();
    var pattern = /[^\x00-\xff]/g;
    if((pattern.test(tempAliPayNumber))||pattern.test(tempPayPalNumber)){
        alert("输入格式有误");
    }
    else{
        if((tempAliPayNumber!=oldAliPayNumber)||(tempPayPalNumber!=oldPayPalNumber)){
            teacherAccount.teacherId =  $("#userId").val();
            teacherAccount.paypal = tempPayPalNumber;
            teacherAccount.alipayNo = tempAliPayNumber;
            teacherAccount.style="manager";
            teacherAccount.method="editPayInformation";
            teacherAccount.mid=mid;
            //send.method = "editPayInformation";
            //send.style = "manager";
            //send.servicePro = teacherAccount;
            myAjax( teacherAccount, null);
            Messenger().post("操作成功");
        }
    }
}
