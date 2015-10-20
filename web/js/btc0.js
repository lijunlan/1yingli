$(document).ready(function(){
	var u = $.cookie('uid');
    var n = $.cookie('nickName');
    var i = $.cookie('iconUrl');
    
	if(u==null||n==null||i==null){self.location='login.html';return;}
 	$("#nickName").html(n);
    if(i!=""){
        $("#iconUrl").attr("src",i);
    }
});
