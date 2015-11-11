/**
 * 
 */

function setUpLoginLink() {
	if (getCookie("mark") == null) {
		jQuery("#loginlink").attr("href", "./login.html");
		jQuery("#loginlink").html("登录");
		jQuery("#registerlink").attr("href", "./register.html");
		jQuery("#registerlink").html("注册");
	} else {
		var mark = getCookie("mark");
		jQuery
				.ajax({
					cache : true,
					type : "POST",
					url : "/IvyProService/manage",
					data : "{'style':'user','method':'get_info','mark':'"
							+ mark + "'}",
					async : false,
					error : function(request) {
						alert("Connection error");
					},
					success : function(data, textStatu) {
						var json = eval("(" + data + ")");
						if (getValueFromJson(json, "state") == "success") {
							var email = getValueFromJson(json, "email");
							jQuery("#loginlink").attr("href",
									"./showorder.html");
							jQuery("#loginlink").html(email);
							jQuery("#registerlink").attr("href",
									"javascript:logout()");
							jQuery("#registerlink").html("退出");
						} else {
							// alert(getValueFromJson(json, "msg"));
							jQuery("#loginlink").attr("href", "./login.html");
							jQuery("#loginlink").html("登录");
							jQuery("#registerlink").attr("href",
									"./register.html");
							jQuery("#registerlink").html("注册");
							delCookie("mark");
						}
					}
				});
	}
}

function logout() {
	// TODO logout
	var mark = getCookie("mark");
	jQuery.ajax({
		cache : true,
		type : "POST",
		url : "/IvyProService/manage",
		data : "{'style':'user','method':'logout','mark':'" + mark + "'}",
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data, textStatu) {
			var json = eval("(" + data + ")");
			if (getValueFromJson(json, "state") == "success") {
				delCookie("mark");
				jQuery("#loginlink").attr("href", "./login.html");
				jQuery("#loginlink").html("登录");
				jQuery("#registerlink").attr("href", "./register.html");
				jQuery("#registerlink").html("注册");
			} else {
				alert(getValueFromJson(json, "msg"));

			}
		}
	});
}
