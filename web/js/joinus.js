
function ResetCSS() {
    $("#tab-01").css("color", '#56bbe8');
    $("#tab-01").css("background", '');
    $("#tab-02").css("color", '#56bbe8');
    $("#tab-02").css("background", '');
    $("#tab-03").css("color", '#56bbe8');
    $("#tab-03").css("background", '');
    $("#tab-04").css("color", '#56bbe8');
    $("#tab-04").css("background", '');
    $("#tab-05").css("color", '#56bbe8');
    $("#tab-05").css("background", '');
    $("#tab-06").css("color", '#56bbe8');
    $("#tab-06").css("background", '');

	$(".members").css("display","none");
    $(".jobs").css("display","none");
    $(".whoweare").css("display","none");
	$(".partner").css("display","none");
	$(".websiteFeedback").css("display", "none");
	$(".mediaReoprts").css("display", "none");
	
	$("#menu").hover(function(){
        $(".m-name ul").css("display","block");
    });
    $("#menu").mouseleave(function(){
        $(".m-name ul").css("display","none");
    });
}

ResetCSS();

function GetQueryString(name) {
   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
   var r = window.location.search.substr(1).match(reg);
   if (r!=null) return (r[2]); return null;
}

switch(parseInt(GetQueryString("id"))) {
	case 1:  
	    $(".whoweare").css("display","block");
		$("#tab-01").css("color", '#b9effd');
    	$("#tab-01").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
		break;
	
	case 2: 
		$(".jobs").css("display","block");
		$("#tab-04").css("color", '#b9effd');
   	    $("#tab-04").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
		break;
	
	case 3: 
		$(".websiteFeedback").css("display","block");
		$("#tab-05").css("color", '#b9effd');
   	    $("#tab-05").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
		break;
}

//添加事件
$("#tab-01").click(function(){
    ResetCSS();
    $(".whoweare").css("display","block");
    $("#tab-01").css("color", '#b9effd');
    $("#tab-01").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
});
$("#tab-02").click(function(){
    ResetCSS();
    $(".members").css("display","block");
    $("#tab-02").css("color", '#b9effd');
    $("#tab-02").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
});
$("#tab-03").click(function(){
    ResetCSS();
    $(".mediaReoprts").css("display","block");
	$("#tab-03").css("color", '#b9effd');
    $("#tab-03").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
});
$("#tab-04").click(function(){
    ResetCSS();
    $(".jobs").css("display","block");
	$("#tab-04").css("color", '#b9effd');
    $("#tab-04").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
});
$("#tab-05").click(function(){
    ResetCSS();
    $(".websiteFeedback").css("display","block");
	$("#tab-05").css("color", '#b9effd');
    $("#tab-05").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
});
$("#tab-06").click(function(){
    ResetCSS();
	$(".partner").css("display","block");
	$("#tab-06").css("color", '#b9effd');
    $("#tab-06").css("background", 'url(http://image.1yingli.cn/images/arrow.png) no-repeat');
});


//工作职位转换
$("#job-tab-01").click(function(){
    $(".ui-designer").css("display","none");
    $(".customer-service").css("display","none");
    $(".web-front").css("display","block");
    $("#job-list-01").css("border-bottom","4px solid #56bbe8");
    $("#job-list-02").css("border-bottom","0");
    $("#job-list-03").css("border-bottom","0");
});
$("#job-tab-02").click(function(){
    $(".web-front").css("display","none");
    $(".customer-service").css("display","none");
    $(".ui-designer").css("display","block");
    $("#job-list-02").css("border-bottom","4px solid #56bbe8");
    $("#job-list-01").css("border-bottom","0");
    $("#job-list-03").css("border-bottom","0");
});
$("#job-tab-03").click(function(){
    $(".ui-designer").css("display","none");
    $(".web-front").css("display","none");
    $(".customer-service").css("display","block");
    $("#job-list-03").css("border-bottom","4px solid #56bbe8");
    $("#job-list-02").css("border-bottom","0");
    $("#job-list-01").css("border-bottom","0");
});


/////////鼠标滚动条设置--------start
var oContainer = document.getElementById("container"), oDiv1 = document.getElementById("div1"), oDiv2 = document.getElementById("div2"), oDiv3 = document.getElementById("div3");
oDiv3.onmousedown = function(e) {
	e = e || event;

	var disY = e.clientY - this.offsetTop;
	
	if(oDiv3.setCapture){
		oDiv3.onmousemove=fnMove;
		oDiv3.onmouseup=fnUp;
		oDiv3.setCapture();
	}else{
		document.onmousemove=fnMove;
		document.onmouseup=fnUp;
	}
	
	function fnMove(ev) {
		ev = ev || event;

		var t = ev.clientY - disY;
		setTop(t);
	};

	function fnUp() {
		this.onmousemove = null;
		this.onmouseup = null;
		
		if(this.releaseCapture){
			this.releaseCapture();
		}
	};

	return false;
};

function setTop(t) {
	var down = oDiv2.offsetHeight - oDiv3.offsetHeight;

	if (t < 0) {
		t = 0;
	} else if (t > down) {
		t = down
	}

	oDiv3.style.top = t + 'px';

	var scale = t / down;
	oDiv1.style.top = -(oDiv1.offsetHeight - oContainer.offsetHeight) * scale + 'px';
}

addEvent(oDiv1, 'mousewheel', mousewheel);
addEvent(oDiv1, 'DOMMouseScroll', mousewheel);
addEvent(oDiv2, 'mousewheel', mousewheel);
addEvent(oDiv2, 'DOMMouseScroll', mousewheel);

function addEvent(obj, oEvent, fn) {
	if (obj.attachEvent) {
		obj.attachEvent('on' + oEvent, fn);
	} else {
		obj.addEventListener(oEvent, fn, false);
	}
}

function mousewheel(e) {
	var ev = e || event, bDown = false;

	bDown = ev.wheelDelta ? ev.wheelDelta < 0 : ev.detail > 0;
	
	if (bDown) {
		setTop(oDiv3.offsetTop+10);
	}else{
		setTop(oDiv3.offsetTop-10);
	}

	//FF,绑定事件，阻止默认事件
	if (e.preventDefault) {
		e.preventDefault();
	}

	return false;
}
/////////鼠标滚动条设置--------end