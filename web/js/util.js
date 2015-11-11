
/*检测手机号码格式*/ 
function isTel(str){  
    var pattern = /^[1][1-9][0-9]{9}$/; ;  
    return pattern.test(str);
}

/*检测邮箱合适*/
function isEmail( str ){  
	var myReg = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/; 
	if(myReg.test(str)) return true; 
	return false; 
} 